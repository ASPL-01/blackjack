package com.galvanize.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void shouldCreateUserWithBalanceAndUsername() {
        User user1 = new User(1000.00, "nzukoff");
        assertEquals("nzukoff",user1.getUsername());
        assertEquals(1000.00,user1.getBalance(),0);
    }

}