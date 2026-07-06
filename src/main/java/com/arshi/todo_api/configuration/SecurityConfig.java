package com.arshi.todo_api.configuration;

import com.arshi.todo_api.config.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /* csrf.disable()--> CSRF protection is for browser-based apps with sessions/cookies. We're building a stateless
        REST API, so this isn't needed — it's standard to disable it for REST APIs */
                 http.csrf(csrf -> csrf.disable())
        /* sessionCreationPolicy(STATELESS) --> Tells Spring "don't create login sessions" — because we're using
        JWT tokens instead (each request proves itself with a token, server doesn't need to "remember" you between requests)*/
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
        /* .requestMatchers("/api/auth/**").permitAll() --> Anyone can hit /api/auth/register and /api/auth/login without
        being logged in — makes sense,you need to be ABLE to log in*/
                        .requestMatchers("/api/auth/**").permitAll()
        /* .anyRequest().authenticated() --> Every other endpoint (your /api/students, /api/attendance, future /api/todos)
        now requires a valid login — we'll wire up JWT to satisfy this next*/
                        .anyRequest().authenticated())
                        .exceptionHandling(ex -> ex
                                 .authenticationEntryPoint((request, response, authException) -> {
                                     response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                                 })
                         )
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
