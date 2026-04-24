package com.telioarts.portfolio.bll;

import com.telioarts.portfolio.bo.Reservation;
import com.telioarts.portfolio.bo.Visiteur;
import com.telioarts.portfolio.dal.ReservationRepository;
import com.telioarts.portfolio.dal.VisiteurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private VisiteurRepository visiteurRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    void show_throw_exception_when_visiteur_not_found() {
        // Arrange : Préparation des données et des comportements simulés
        Visiteur visiteur = new Visiteur();
        visiteur.setIdVisiteur(99L);
        Reservation reservation = new Reservation();
        reservation.setVisiteur(visiteur);

        // Simulation : Lorsque findById(99) est appelé, on retourne un Optional vide
        when(visiteurRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert : Exécution de la méthode et vérification de l'exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservationService.createReservation(reservation);
        });

        // Vérification du message d'erreur
        assertEquals("Le visiteur n'existe pas", exception.getMessage());
    }

    @Test
    void show_save_reservation_successfully() {
        // Arrange : Préparation du visiteur existant et de la réservation
        Visiteur visiteur = new Visiteur();
        visiteur.setIdVisiteur(1L);
        Reservation reservation = new Reservation();
        reservation.setVisiteur(visiteur);
        reservation.setObjet("Test Stage");

        // Définition des comportements des mocks
        when(visiteurRepository.findById(1L)).thenReturn(Optional.of(visiteur));
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // Act : Appel de la méthode métier à tester
        Reservation savedReservation = reservationService.createReservation(reservation);

        // Assert : Vérification des résultats et de la logique métier
        assertNotNull(savedReservation);

        // Vérification de la règle métier : le statut doit être défini sur "PENDING" par défaut
        assertEquals("PENDING", savedReservation.getStatut());

        // Vérification que la méthode save du repository a bien été appelée une fois
        verify(reservationRepository, times(1)).save(reservation);

    }

}
