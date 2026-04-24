package com.telioarts.portfolio.bll;

import com.telioarts.portfolio.bo.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationService {
    // Créer une nouvelle réservation
    Reservation createReservation(Reservation reservation);

    // Récupérer toutes les réservations (accès administrateur)
    List<Reservation> listerToutesLesReservations();

    // Récupérer les réservations par l'identifiant du visiteur
    List<Reservation> listerReservationsParVisiteur(Long idVisiteur);

    Reservation updateReservation(Long id, Reservation reservation);

    void deleteReservation(Long id);
}
