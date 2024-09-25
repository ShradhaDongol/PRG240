package com.example.demo.service;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) {
        log.warn("Adding user: {}");
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        log.debug("Getting all users");
        return userRepository.findAll();
    }

    public boolean authenticateUser(String email, String password) {
        log.error("Authenticating user with email: {}", email);
        return userRepository.findByEmail(email)
                .map(user -> BCrypt.checkpw(password, user.getPassword()))
                .orElse(false);
    }
}