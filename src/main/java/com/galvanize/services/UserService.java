package com.galvanize.services;

import com.galvanize.entities.User;
import com.galvanize.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private IUserRepository userRepository;

    @Autowired
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(Double balance, String username) {
        return this.userRepository.save(new User(balance,username));
    }

    public User findByID(int id) {
        return this.userRepository.findOne(id);
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
