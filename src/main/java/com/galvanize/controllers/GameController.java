package com.galvanize.controllers;

import com.galvanize.entities.Game;
import com.galvanize.exceptions.GameException;
import com.galvanize.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/games")
public class GameController {
    private GameService gameService;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(value = "/{id}/{bet}", method = RequestMethod.POST)
    public Game deal(@PathVariable int id, @PathVariable int bet) throws GameException {
        return this.gameService.deal(id,bet);
    }

    @RequestMapping(value = "/{gameID}/hit", method = RequestMethod.PUT)
    public Game hit(@PathVariable int gameID) throws GameException {
        return this.gameService.hit(gameID);
    }

    @RequestMapping(value = "/{gameID}/stand", method = RequestMethod.PUT)
    public Game stand(@PathVariable int gameID) throws GameException {
        return this.gameService.stand(gameID);
    }
}
