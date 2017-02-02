package com.galvanize.controllers;

import com.galvanize.entities.Game;
import com.galvanize.entities.User;
import com.galvanize.services.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {
    private Game game1;
    private User player;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GameService gameService;

    @Before
    public void setUp() throws Exception {
        player = new User(10000.00,"nzukoff");
        game1 = new Game(100,player);
        game1.setPlayerCards("6:7:7");
        game1.setPlayerValue(20);
        game1.setDealerCards("Q:9");
        game1.setDealerValue(19);
    }

    @Test
    public void shouldDealCards() throws Exception {
        when(this.gameService.deal(1,100)).thenReturn(game1);

        MockHttpServletRequestBuilder request = post("/games/1/100");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerCards", is("6:7:7")))
                .andExpect(jsonPath("$.dealerValue", is(19)));
    }

    @Test
    public void shouldUseHitService() throws Exception {
        when(this.gameService.hit(1)).thenReturn(game1);

        MockHttpServletRequestBuilder request = put("/games/1/hit");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerValue", is(20)));
    }

    @Test
    public void shouldUseStandService() throws Exception {
        when(this.gameService.stand(1)).thenReturn(game1);

        MockHttpServletRequestBuilder request = put("/games/1/stand");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerValue", is(20)));
    }


}