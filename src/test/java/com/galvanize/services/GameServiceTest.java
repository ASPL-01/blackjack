package com.galvanize.services;

import com.galvanize.entities.Game;
import com.galvanize.entities.User;
import com.galvanize.enums.Outcome;
import com.galvanize.exceptions.GameException;
import com.galvanize.models.Card;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(value = {"/sql/seed.sql"})
public class GameServiceTest {
    private Card card1;
    private Card card2;
    private Card card3;
    private Card card4;
    private Card card5;
    private Card card6;
    private Card card7;
    private Card card8;
    private Card card9;
    private Card card10;
    private Card card11;
    private Card card12;
    private Card card13;
    private Card card14;

    @Autowired
    private GameService gameService;

    @MockBean
    private CardService cardService;

    @Before
    public void setUp() throws Exception {
        card1 = new Card();
        card1.setCard("A");
        card2 = new Card();
        card2.setCard("J");
        card3 = new Card();
        card3.setCard("A");
        card4 = new Card();
        card4.setCard("2");
        card5 = new Card();
        card5.setCard("Q");
        card6 = new Card();
        card6.setCard("5");
        card7 = new Card();
        card7.setCard("A");
        card8 = new Card();
        card8.setCard("A");
        card9 = new Card();
        card9.setCard("4");
        card10 = new Card();
        card10.setCard("A");
        card11 = new Card();
        card11.setCard("5");
        card12 = new Card();
        card12.setCard("8");
        card13 = new Card();
        card13.setCard("3");
        card14 = new Card();
        card14.setCard("10");
        when(this.cardService.getApiCard()).thenReturn(card1,card2,card3,card4,card5,card6,card7,card8,card9,card10,card11,card12,card13,card14);
    }

    @Test
    public void shouldGetCardValue() {
        int dealerValue = this.gameService.getCardValue(card1);
        assertEquals(1,dealerValue);
    }

    @Test
    public void shouldDealCards() throws Exception {
        Game game1 = this.gameService.deal(1,100);
        assertEquals(5,game1.getId());
        assertEquals("A",game1.getDealerCards());
        assertEquals((Integer) 1,game1.getDealerValue());
        assertEquals(1,game1.getUser().getId());
        assertEquals(10150.00,game1.getUser().getBalance(),0.01);
        assertEquals("J:A",game1.getPlayerCards());
        assertEquals(Outcome.WON, game1.getOutcome());
    }
//
//    @Test
//    public void shouldNotDealCardsBadId() throws Exception {
//        Game game1 = this.gameService.deal(10,100);
//    }

    @Test (expected = com.galvanize.exceptions.GameException.class)
    public void shouldNotDealCardsNotEnoughFunds() throws Exception {
        Game game1 = this.gameService.deal(1,20000);
    }

    @Test
    public void hitShouldAddCard() throws Exception {
        Game game1 = this.gameService.deal(1,1000);
        Game game2 = this.gameService.deal(1,2000);
        assertEquals(0,game2.getVersion());
        Game game2Hit = this.gameService.hit(6);
        assertEquals(1,game2Hit.getVersion());
        assertEquals("Q:5:A",game2Hit.getPlayerCards());
        assertEquals((Integer) 16,game2Hit.getPlayerValue());
        Game game2Hit2 = this.gameService.hit(6);
        assertEquals(2,game2Hit2.getVersion());
        assertEquals("Q:5:A:A",game2Hit2.getPlayerCards());
        assertEquals((Integer) 17,game2Hit2.getPlayerValue());
        assertEquals((Double) 9500.00,game2.getUser().getBalance());
    }



    @Test (expected = com.galvanize.exceptions.GameException.class)
    public void shouldBustIfHitExceeds21() throws Exception {
        Game game1 = this.gameService.deal(1,100);
        Game game2 = this.gameService.deal(1,100);
        this.gameService.hit(6);
        this.gameService.hit(6);
        this.gameService.hit(6);
        Game game2Hit4 = this.gameService.hit(6);
    }

    @Test
    public void shouldStayAndDecideWinnerLOST() throws Exception {
        Game game1 = this.gameService.deal(1,1000);
        Game game2 = this.gameService.deal(1,100);
        this.gameService.stand(6);
        Game game3 = this.gameService.deal(1,1000);
        Game game3stand = this.gameService.stand(7);
        assertEquals((Integer) 15,game3stand.getPlayerValue());
        assertEquals((Integer) 16,game3stand.getDealerValue());
        assertEquals("4:A",game3stand.getPlayerCards());
        assertEquals("A:5",game3stand.getDealerCards());
        assertEquals(Outcome.LOST,game3stand.getOutcome());
        assertEquals((Double) 11400.00, game2.getUser().getBalance());
    }

    @Test
    public void shouldStayandDecideWinnerLostDealerBJ() throws Exception {
        Game game1 = this.gameService.deal(1,1000);
        Game game2 = this.gameService.deal(1,2000);
        Game game3 = this.gameService.deal(1,100);
        Game game4 = this.gameService.deal(1,1000);
        this.gameService.hit(8);
        Game game4stand = this.gameService.stand(8);
        assertEquals(Outcome.LOST,game4stand.getOutcome());
        assertEquals("A:10",game4stand.getDealerCards());
        assertEquals("5:8:3",game4stand.getPlayerCards());
    }

    @Test
    public void shouldStayAndDecideWinnerWON() throws Exception {
        Game game1 = this.gameService.deal(1,1000);
        Game game2 = this.gameService.deal(1,1000);
        Game game3 = this.gameService.deal(1,2000);
        this.gameService.hit(7);
        this.gameService.hit(7);
        Game game3stand = this.gameService.stand(7);
        assertEquals((Integer) 21,game3stand.getPlayerValue());
        assertEquals("A:4:A:5",game3stand.getPlayerCards());
        assertEquals((Integer) 19,game3stand.getDealerValue());
        assertEquals("A:8",game3stand.getDealerCards());
        assertEquals(Outcome.WON,game3stand.getOutcome());
        assertEquals((Double) 12500.00,game3stand.getUser().getBalance());
    }

    @Test
    public void shouldReturnNumGamesWon() throws Exception {
        Integer numWon = this.gameService.findNumWon();
        assertEquals((Integer) 2,numWon);
    }

    @Test
    public void shouldReturnNumGamesLost() throws Exception {
        Integer numLost = this.gameService.findNumLost();
        assertEquals((Integer) 2,numLost);
    }
}

