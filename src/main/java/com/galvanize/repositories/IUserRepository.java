package com.galvanize.repositories;

import com.galvanize.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepository extends CrudRepository<User, Integer> {
    public User findByUsername(String username);
}
