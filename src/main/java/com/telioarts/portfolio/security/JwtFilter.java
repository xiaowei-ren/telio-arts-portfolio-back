package com.telioarts.portfolio.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/api/auth/") ||
                path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs")) {

            filterChain.doFilter(request, response);
            return;
        }
        // 2. Extraire l'en-tête "Authorization" de la requête HTTP
        String header = request.getHeader("Authorization");

        // 3. Vérifier si l'en-tête est présent et commence par le préfixe standard "Bearer "
        if (header != null && header.startsWith("Bearer ")) {
            // Extraire le jeton en supprimant les 7 premiers caractères ("Bearer ")
            String token = header.substring(7);

            // 4. Valider l'intégrité et l'expiration du jeton via JwtUtil
            if (jwtUtil.isTokenValid(token)) {
                // 1. Extraire le login du token
                String login = jwtUtil.extractLogin(token);

                // 2. Créer l'objet d'authentification pour Spring Security
                // On passe le login, null pour le password, et une liste vide d'autorités (Collections.emptyList())
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        login,
                        null,
                        new ArrayList<>()
                );

                // 3. INJECTER l'authentification dans le contexte de sécurité de Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 4. (Optionnel) Garder le setAttribute si en as besoin dans tes contrôleurs
                request.setAttribute("login", login);

                // Poursuivre la chaîne de filtres (accès autorisé)
                filterChain.doFilter(request, response);
                return;
            }
        }

            // 5. Si le jeton est absent ou invalide, bloquer la requête
            // Retourne un code d'erreur HTTP 403 (Forbidden)
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("Accès refusé : Jeton JWT invalide ou manquant.");
    }
}