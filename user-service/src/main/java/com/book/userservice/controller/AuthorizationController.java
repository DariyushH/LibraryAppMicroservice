package com.book.userservice.controller;

import com.book.userservice.model.authorization.AuthRequest;
import com.book.userservice.model.authorization.JwtTokenResponse;
import com.book.userservice.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthorizationService authService;
    @Autowired
    public AuthorizationController(AuthorizationService authService) {
        this.authService = authService;
    }

    @PostMapping("/signIn")
    public JwtTokenResponse signIn(@RequestBody AuthRequest request) {
        return authService.signIn(request);
    }

    @PostMapping("/signUp")
    public JwtTokenResponse signUp(@RequestBody AuthRequest request) {
        return authService.signUp(request);
    }
}
