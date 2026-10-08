package com.jobtrack.jobtrack.security;

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
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {

        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");

        System.out.println("================================");
        System.out.println(
                "Request: "
                        + request.getMethod()
                        + " "
                        + request.getRequestURI()
        );

        System.out.println(
                "Authorization Header Present: "
                        + (authHeader != null)
        );
        System.out.println("================================");

        // No token
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            System.out.println(
                    "NO VALID BEARER TOKEN FOUND"
            );

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        String token =
                authHeader.substring(7);

        try {

            if (jwtService.isTokenValid(token)) {

                String email =
                        jwtService.extractEmail(token);

                System.out.println("================================");
                System.out.println("JWT VALID");
                System.out.println(
                        "Logged-in email: "
                                + email
                );
                System.out.println("================================");

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                Collections.emptyList()
                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(
                                authentication
                        );

            } else {

                System.out.println("================================");
                System.out.println("JWT INVALID");
                System.out.println("================================");
            }

        } catch (Exception e) {

            System.out.println("================================");
            System.out.println("JWT FILTER ERROR");
            System.out.println(
                    e.getMessage()
            );
            System.out.println("================================");
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}