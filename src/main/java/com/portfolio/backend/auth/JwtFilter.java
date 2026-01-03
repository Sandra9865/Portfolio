package com.portfolio.backend.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                // On essaie juste de parser le token pour vérifier, mais
                // en cas d'erreur on NE JETTE PLUS d'exception
                jwtService.extractEmail(token);
            } catch (Exception e) {
                System.out.println("⚠️ JWT invalide, on ignore : " + e.getMessage());
                // on n'interrompt pas la requête
            }
        }

        // on continue la chaîne de filtres normalement
        filterChain.doFilter(request, response);
    }
}
