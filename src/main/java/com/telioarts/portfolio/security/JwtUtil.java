package com.telioarts.portfolio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // La clé secrète doit être conservée en toute sécurité
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // Durée de validité du jeton 24 heures (en millisecondes)
    private static final long EXPIRATION_TIME = 86400000;

    /**
     * Génère un jeton JWT pour un utilisateur donné.
     * @param login L'identifiant de l'utilisateur
     * @return Le jeton JWT compacté sous forme de chaîne de caractères
     */
    public String generateToken(String login) {
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * Extrait l'identifiant (login) contenu dans le jeton.
     * @param token Le jeton JWT
     * @return Le login de l'utilisateur
     */
    public String extractLogin(String token) {
        return getClaims(token).getSubject();
    }
    /**
     * Vérifie si le jeton est encore valide (non expiré).
     * @param token Le jeton JWT
     * @return true si le jeton est valide, false sinon
     */
    public boolean isTokenValid(String token) {
        try {
            // Vérifie si la date d'expiration est postérieure à la date actuelle
            return getClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            // En cas d'erreur (jeton malformé ou expiré), on considère qu'il est invalide
            return false;
        }
    }

        /**
         * Récupère le corps (Claims) du jeton en utilisant la clé secrète.
         */
        private Claims getClaims(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
}

