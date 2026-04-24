package com.telioarts.portfolio.controller;

import com.telioarts.portfolio.bll.AuthService;
import com.telioarts.portfolio.dto.LoginRequest;
import com.telioarts.portfolio.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Contrôleur gérant les requêtes d'authentification.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Authentifie un utilisateur et génère un jeton JWT en cas de succès.
     * * @param request Objet contenant l'identifiant et le mot de passe.
     * @return ResponseEntity contenant le jeton ou un message d'erreur.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest request) {

        // 1. Appel de la couche métier (BLL) pour vérifier les identifiants
        boolean isAuthenticated = authService.login(request.getLogin(), request.getPassword());

        if (isAuthenticated) {
            // 2. Génération du jeton JWT
            String token = jwtUtil.generateToken(request.getLogin());

            // 3. Encapsulation dans une Map pour conversion automatique en JSON {"token": "...", "username": "..."}
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", request.getLogin()); // Optionnel : renvoie le nom d'utilisateur au front-end

            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token)
                    .body(response);
        } else {
            // 4. Retourne une erreur 401 Unauthorized en cas d'échec d'authentification
            Map<String, String> error = new HashMap<>();
            error.put("message", "Identifiants invalides");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
}