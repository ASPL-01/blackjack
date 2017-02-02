package com.galvanize.services;

import com.galvanize.entities.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(value = {"/sql/seed.sql"})
public class UserServiceTest {

    User user = new User();

    @Before
    public void setUp() throws Exception {
        user.setBalance(10000.00);
        user.setUsername("agzukoff");
    }

    @Autowired
    private UserService userService;

    @Test
    public void shouldCreateUser() throws Exception {
        User after = this.userService.create(user.getBalance(),user.getUsername());
        assertEquals(2, after.getId());
        assertEquals(10000.00, after.getBalance(), 0);
        assertEquals("agzukoff", after.getUsername());
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void shouldNotCreateUserNotUnique() throws Exception {
        User user1 = this.userService.create(1000.00,"agzukoff");
        User user2 = this.userService.create(500.00,"agzukoff");
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void shouldNotCreateUserUsernameTooSmall() throws Exception {
        User user1 = this.userService.create(1000.00,"nzu");
    }

    @Test
    public void shouldReturnUserFromId() {
        User after = this.userService.create(user.getBalance(),user.getUsername());
        assertEquals("agzukoff", this.userService.findByID(2).getUsername());
    }

    @Test
    public void shouldNotReturnUserFromBadId() {
        User user1 = this.userService.findByID(4);
        assertNull(user1);
    }

    @Test
    public void shouldReturnUserFromUsername() {
        User after = this.userService.create(user.getBalance(),user.getUsername());
        assertEquals(2, this.userService.findByUsername("agzukoff").getId());

    }




}