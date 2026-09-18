package com.slain.library.dto;

import com.slain.library.enums.GenderType;

public record RegistrationRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        GenderType gender
) {}
