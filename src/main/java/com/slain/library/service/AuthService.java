package com.slain.library.service;

import com.slain.library.dto.LoginRequest;
import com.slain.library.dto.RegistrationRequest;
import com.slain.library.model.Reader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class AuthService {
    private final ReaderService readerService;
    private final AuthenticationManager authenticationManager;
    private final SessionAuthenticationStrategy sessionAuthenticationStrategy;

    public AuthService(
            ReaderService readerService,
            AuthenticationManager authenticationManager,
            SessionAuthenticationStrategy sessionAuthenticationStrategy
    ) {
        this.readerService = readerService;
        this.authenticationManager = authenticationManager;
        this.sessionAuthenticationStrategy = sessionAuthenticationStrategy;
    }

    public void login(
            LoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) {
        try {
            var authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(request.email(), request.password()));

            sessionAuthenticationStrategy.onAuthentication(authentication, httpRequest, httpResponse);

            var context = SecurityContextHolder.createEmptyContext();

            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            httpRequest.getSession(true).setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
        } catch (AuthenticationException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

    public Reader me(Authentication authentication) {
        return this.readerService.getCurrent(authentication);
    }

    public Reader register(RegistrationRequest request) {
        var reader = new Reader();
        reader.setEmail(request.email());
        reader.setPassword(request.password());
        reader.setFirstName(request.firstName());
        reader.setLastName(request.lastName());
        reader.setGender(request.gender());
        return this.readerService.create(reader);
    }
}
