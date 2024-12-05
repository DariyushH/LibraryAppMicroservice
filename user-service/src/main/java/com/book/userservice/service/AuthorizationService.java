package com.book.userservice.service;

import com.book.userservice.model.authorization.AuthRequest;
import com.book.userservice.model.authorization.JwtTokenResponse;

public interface AuthorizationService {
    JwtTokenResponse signUp(AuthRequest req);

    JwtTokenResponse signIn(AuthRequest req);
}
