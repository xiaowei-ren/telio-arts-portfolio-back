package com.telioarts.portfolio.bll;

import com.telioarts.portfolio.bo.Reservation;
import com.telioarts.portfolio.bo.Visiteur;
import com.telioarts.portfolio.dal.ReservationRepository;
import com.telioarts.portfolio.dal.VisiteurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReservationServiceImpl implements ReservationService{
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private VisiteurRepository  visiteurRepository;

    @Override
    @Transactional
    public Reservation createReservation(Reservation reservation) {
        // 1. Validation métier : s'assurer que le visiteur est bien spécifié
        if (reservation.getVisiteur() == null || reservation.getVisiteur().getIdVisiteur() == null) {
            throw new RuntimeException("Un visiteur valide est requis pour la réservation.");
        }

        // 2. Vérification de l'existence du visiteur dans la base de données
        Visiteur visiteur = visiteurRepository.findById(reservation.getVisiteur().getIdVisiteur())
                .orElseThrow(() -> new RuntimeException("Le visiteur n'existe pas."));

        // 3. Liaison de l'objet Visiteur récupéré à la réservation
        reservation.setVisiteur(visiteur);

        // 4. Définition du statut par défaut si non renseigné
        if (reservation.getStatut() == null) {
            reservation.setStatut("PENDING");
        }

        // 5. Appel à la couche DAL pour la persistance
        return reservationRepository.save(reservation);

    }

    @Override
    public List<Reservation> listerToutesLesReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> listerReservationsParVisiteur(Long idVisiteur) {
        Visiteur visiteur = visiteurRepository.findById(idVisiteur).orElse(null);
        return reservationRepository.findByVisiteur(visiteur);
    }

    @Override
    @Transactional
    public Reservation updateReservation(Long id, Reservation details) {
        return reservationRepository.findById(id).map(existingRdv -> {
            existingRdv.setDateReservation(details.getDateReservation());
            existingRdv.setObjet(details.getObjet());
            existingRdv.setMessage(details.getMessage());
            existingRdv.setStatut(details.getStatut());
            return reservationRepository.save(existingRdv);
        }).orElseThrow(() -> new RuntimeException("Réservation introuvable avec l'id : " + id));
    }
    @Override
    @Transactional
    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : Réservation introuvable.");
        }
        reservationRepository.deleteById(id);
    }
}
