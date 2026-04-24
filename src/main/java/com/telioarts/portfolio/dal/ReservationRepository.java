package com.telioarts.portfolio.dal;

import com.telioarts.portfolio.bo.Reservation;
import com.telioarts.portfolio.bo.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Récupérer toutes les réservations d'un visiteur spécifique
   List<Reservation> findByVisiteur(Visiteur visiteur);

    //Rechercher les réservations par statut (ex: 'PENDING')
    Reservation findByStatut(String statut);
}
