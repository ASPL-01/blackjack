package com.galvanize.controllers;

import com.galvanize.entities.Game;
import com.galvanize.entities.User;
import com.galvanize.enums.Outcome;
import com.galvanize.models.Statistics;
import com.galvanize.services.GameService;
import com.galvanize.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    private User user;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private GameService gameService;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setId(1);
        user.setUsername("nzukoff");
        user.setBalance(10000.00);
    }

    @Test
    public void shouldCreateUser() throws Exception {
        // stub
        when(this.userService.create(user.getBalance(),user.getUsername())).thenReturn(user);

        // request
        MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"balance\" : \"10000.00\",\"username\" : \"nzukoff\"}");

        // assertion
        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.balance", is(10000.00)))
                .andExpect(jsonPath("$.username", is("nzukoff")));
    }

    @Test
    public void shouldNotCreateUserBadURL() throws Exception {
        MockHttpServletRequestBuilder request = post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"balance\" : \"10000.00\",\"username\" : \"nzukoff\"}");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnUserFromID() throws Exception {
        when(this.userService.findByID(user.getId())).thenReturn(user);

        MockHttpServletRequestBuilder request = get("/users/1");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("nzukoff")));

    }

    @Test
    public void shouldReturnUserFromUsername() throws Exception {
        when(this.userService.findByUsername(user.getUsername())).thenReturn(user);

        MockHttpServletRequestBuilder request = get("/users/user/nzukoff");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void shouldReturnUserStatistics() throws Exception {
        Statistics statistics = new Statistics(2,1,5000.00);
        when(this.gameService.findStatistics(1)).thenReturn(statistics);

        MockHttpServletRequestBuilder request = get("/users/1/statistics");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.won", is(2)))
                .andExpect(jsonPath("$.balance",is(5000.00)));
    }
}