package com.book.userservice.service.implementation;

import com.book.userservice.enums.UserRole;
import com.book.userservice.model.User;
import com.book.userservice.model.authorization.AuthRequest;
import com.book.userservice.model.authorization.JwtTokenResponse;
import com.book.userservice.service.AuthorizationService;
import com.book.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthorizationServiceImpl(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public JwtTokenResponse signUp(AuthRequest req) {
        User user = new User(
                req.getUsername(),
                passwordEncoder.encode(req.getPassword()),
                UserRole.ROLE_USER);

        userService.saveUser(user);

        String jwt = jwtService.generateToken(user);
        return new JwtTokenResponse(jwt);
    }

    @Override
    public JwtTokenResponse signIn(AuthRequest req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                req.getUsername(),
                req.getPassword()
        ));

        UserDetails user = userService
                .userDetailsService()
                .loadUserByUsername(req.getUsername());

        String jwt = jwtService.generateToken(user);
        return new JwtTokenResponse(jwt);
    }
}
