package com.arshi.todo_api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
//Tells Spring "run this logic exactly once per incoming request"
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //Reads the Authorization header from the incoming request
        String authHeader = request.getHeader("Authorization");

        //JWT tokens are conventionally sent as Bearer <token> — we check for this format
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            //Strips off "Bearer " (7 characters) to get just the raw token
            String token = authHeader.substring(7); // remove "Bearer " prefix

            if (jwtUtil.isTokenValid(token)) { //to check if the token is genuine and not expired
                String username = jwtUtil.extractUsername(token);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

                //below is the key line — it tells Spring Security "this request IS authenticated, here's who it is"
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        /* Always called at the end — lets the request continue to your Controller
        (or get blocked later if setAuthentication was never called) */
        filterChain.doFilter(request, response);
    }
}