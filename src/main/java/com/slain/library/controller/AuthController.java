package com.slain.library.controller;

import com.slain.library.dto.LoginRequest;
import com.slain.library.dto.RegistrationRequest;
import com.slain.library.model.Reader;
import com.slain.library.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) {
        this.authService.login(request, httpRequest, httpResponse);
    }

    @GetMapping("/me")
    public Reader me(Authentication authentication) { return this.authService.me(authentication); }

    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Reader register(@RequestBody RegistrationRequest request) {
        return this.authService.register(request);
    }
}
