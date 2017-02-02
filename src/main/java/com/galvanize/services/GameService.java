package com.galvanize.services;

import com.galvanize.entities.Game;
import com.galvanize.entities.User;
import com.galvanize.enums.Outcome;
import com.galvanize.exceptions.GameException;
import com.galvanize.models.Card;
import com.galvanize.models.Statistics;
import com.galvanize.repositories.IGameRepository;
import com.galvanize.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private IGameRepository gameRepository;
    private CardService cardService;
    private IUserRepository userRepository;
    private UserService userService;

    @Autowired
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setGameRepository(IGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Autowired
    public void setCardService(CardService cardService) {
        this.cardService = cardService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public int getCardValue(Card card) {
        if (card.getCard().matches("A")) {
            return 1;
        }
        else if (card.getCard().matches("[JQK]")) {
            return 10;

        }
        return Integer.parseInt(card.getCard());
    }

    public Game deal(int id, int bet) throws GameException {
        User user = this.userRepository.findOne(id);
        if (user.getBalance() > bet) {
            Double balance = user.getBalance() - bet;
            user.setBalance(balance);
            Game game = new Game(bet, user);
            Card dealerCard = this.cardService.getApiCard();
            game.setDealerCards(dealerCard.getCard());
            game.setDealerValue(getCardValue(dealerCard));
            Card playerCard1 = this.cardService.getApiCard();
            Card playerCard2 = this.cardService.getApiCard();
            game.setPlayerCards(playerCard1.getCard()+":"+playerCard2.getCard());
            game.setPlayerValue(getCardValue(playerCard1)+getCardValue(playerCard2));
            if ((game.getPlayerValue() == 11 && playerCard1.getCard().equals("A")) || (game.getPlayerValue() == 11 && playerCard2.getCard().equals("A"))) {
                System.out.println("Player has blackjack!");
                user.setBalance(user.getBalance()+(bet+bet*1.5));
                game.setOutcome(Outcome.WON);
                game.setPlayerValue(21);
            }
            this.userRepository.save(user);
            this.gameRepository.save(game);
            return game;
        } else {
            throw new GameException("Not enough funds to place bet.");
        }
    }

    public Game hit(int gameID) throws GameException {
        Game game = this.gameRepository.findOne(gameID);
        Card newCard = this.cardService.getApiCard();
        game.setPlayerCards(game.getPlayerCards()+":"+newCard.getCard());
        game.setPlayerValue((game.getPlayerValue()+getCardValue(newCard)));
        if (game.getPlayerValue() > 21) {
            game.setOutcome(Outcome.LOST);
            this.gameRepository.save(game);
            throw new GameException("Bust!");
        } else if (game.getPlayerValue()<=11 && newCard.getCard().equals("A")) {
            game.setPlayerValue(game.getPlayerValue()+10);
            this.gameRepository.save(game);
            return this.gameRepository.findOne(gameID);
        } else {
            this.gameRepository.save(game);
            return this.gameRepository.findOne(gameID);
        }
    }

    public Game stand(int gameID) throws GameException {
        Game game = this.gameRepository.findOne(gameID);
        if (game.getPlayerValue()<=11 && game.getPlayerCards().contains("A")) {
            game.setPlayerValue(game.getPlayerValue()+10);
        }
        Card dealerCard = this.cardService.getApiCard();
        game.setDealerCards(game.getDealerCards() + ":" + dealerCard.getCard());
        game.setDealerValue(game.getDealerValue() + getCardValue(dealerCard));
        if (game.getDealerValue() <= 11 && game.getDealerCards().contains("A")) {
            game.setDealerValue(game.getDealerValue()+10);
        }
        if (game.getDealerValue() < game.getPlayerValue()) {
            game.getUser().setBalance(game.getUser().getBalance()+(game.getBet()*2));
            game.setOutcome(Outcome.WON);
        } else {
            game.setOutcome(Outcome.LOST);
        }
        this.userRepository.save(game.getUser());
        this.gameRepository.save(game);
        return this.gameRepository.findOne(gameID);
    }

    public Statistics findStatistics(int id) {
        int numWon = findNumWon();
        int numLost = findNumLost();
        Double balance = this.userService.findByID(id).getBalance();

        Statistics statistics = new Statistics(numWon,numLost,balance);

        return statistics;
    }

    public int findNumWon() {
        return this.gameRepository.findNumOfOutcomes(Outcome.WON.toString());
    }

    public int findNumLost() {
        return this.gameRepository.findNumOfOutcomes(Outcome.LOST.toString());
    }

}
