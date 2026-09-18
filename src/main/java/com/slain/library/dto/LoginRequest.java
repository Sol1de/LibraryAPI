package com.slain.library.dto;

public record LoginRequest(
        String email,
        String password
) {}
