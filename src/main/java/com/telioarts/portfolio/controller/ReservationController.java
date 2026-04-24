package com.telioarts.portfolio.controller;

import com.telioarts.portfolio.bll.ReservationService;
import com.telioarts.portfolio.bo.Reservation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Gestion des Réservations", description = "API pour gérer le cycle de vie des réservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // Gérer la création d'une nouvelle réservation (Méthode POST)
    @Operation(summary = "Créer une réservation", description = "Enregistre une nouvelle réservation et la lie à un visiteur existant.")
    @PostMapping
    public ResponseEntity<Reservation> creer(@RequestBody Reservation reservation){
        // Appel de la couche BLL pour traiter la logique métier
        Reservation nouvelleReservation = reservationService.createReservation(reservation);
        // Retourne un code 200 OK avec l'objet sauvegardé
        return ResponseEntity.ok(nouvelleReservation);
    }

    // Récupérer la liste de toutes les réservations (Méthode GET)
    @Operation(summary = "Lister toutes les réservations", description = "Récupère l'intégralité des réservations présentes en base de données.")
    @GetMapping
    public List<Reservation> listerToutesLesReservations(){
        System.out.println("Récupération de toutes les réservations...");
        return reservationService.listerToutesLesReservations();
    }

    // Rechercher les réservations par l'identifiant du visiteur (Méthode GET)
    @Operation(summary = "Lister les réservations d'un visiteur", description = "Récupère toutes les réservations associées à un identifiant de visiteur spécifique.")
    @GetMapping("/visiteur/{idVisiteur}")
    public List<Reservation> listerReservationsParVisiteur(
            @io.swagger.v3.oas.annotations.Parameter(description = "ID du visiteur", example = "1")
            @PathVariable Long idVisiteur
    ){
        return reservationService.listerReservationsParVisiteur(idVisiteur);
    }

    @Operation(summary = "Modifier une réservation", description = "Met à jour les détails (date, objet, message) d'une réservation via son ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> modifier(@PathVariable Long id, @RequestBody Reservation detailsModifiees) {
        Reservation reservationMiseAJour = reservationService.updateReservation(id, detailsModifiees);
        return ResponseEntity.ok(reservationMiseAJour);
    }

    @Operation(summary = "Supprimer une réservation", description = "Supprime définitivement une réservation de la base de données.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
