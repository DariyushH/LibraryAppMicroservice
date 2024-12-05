package com.book.userservice.service;


import com.book.userservice.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    void saveUser( User user);

     User getUserByUsername(String username) throws Exception;

    UserDetailsService userDetailsService();
}
