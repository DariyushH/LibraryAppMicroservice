package com.book.gatewayservice.service;

import com.book.gatewayservice.model.User;
import com.book.gatewayservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User getUserByUsername(String username) {
        return userRepository.findFirstByUsername(username).orElse(null);
    }
    public UserDetailsService userDetailsService() {
        return this::getUserByUsername;
    }
}
