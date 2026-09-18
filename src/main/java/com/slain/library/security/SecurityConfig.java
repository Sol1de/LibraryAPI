package com.slain.library.security;

import com.slain.library.repository.ReaderRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public UserDetailsService userDetailsService(ReaderRepository repository) {
        return email -> {
            var reader = repository.findByEmail(email)
                    .filter(r -> r.getPassword() != null)
                    .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
            return User.withUsername(reader.getEmail()).password(reader.getPassword()).roles("READER").build();
        };
    }

    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new ChangeSessionIdAuthenticationStrategy();
    }

    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .dispatcherTypeMatchers(jakarta.servlet.DispatcherType.ERROR).permitAll()
                .requestMatchers(HttpMethod.GET, "/me").authenticated()
                .anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(logout -> logout.logoutUrl("/auth/logout")
                .logoutSuccessHandler((request, response, authentication) -> response.setStatus(204)))
            .exceptionHandling(errors -> errors
                .authenticationEntryPoint((request, response, exception) -> response.sendError(401))
                .accessDeniedHandler((request, response, exception) -> response.sendError(403)))
            .requestCache(RequestCacheConfigurer::disable);
        return http.build();
    }
}
