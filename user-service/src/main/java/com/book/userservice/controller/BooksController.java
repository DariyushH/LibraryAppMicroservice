package com.book.userservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BooksController {
    @GetMapping("/admin")
    @PreAuthorize("hasRole(T(com.book.userservice.enums.UserRole).ROLE_ADMIN)")
    public String admin() {
        return "ADMIN PAGE";
    }
}