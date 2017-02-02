package com.galvanize.controllers;

import com.galvanize.entities.User;
import com.galvanize.models.Statistics;
import com.galvanize.services.GameService;
import com.galvanize.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/users")
public class UserController {
    private UserService userService;
    private GameService gameService;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public User create(@RequestBody User user){
        return this.userService.create(user.getBalance(),user.getUsername());
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public User findById(@PathVariable int id){
        return this.userService.findByID(id);
    }

    @RequestMapping(value = {"/user/{username}"}, method = RequestMethod.GET)
    public User findByUser(@PathVariable String username){
        return this.userService.findByUsername(username);
    }

    @RequestMapping(value = {"/{id}/statistics"}, method = RequestMethod.GET)
    public Statistics statistics(@PathVariable int id) {
        return this.gameService.findStatistics(id);
    }
}
