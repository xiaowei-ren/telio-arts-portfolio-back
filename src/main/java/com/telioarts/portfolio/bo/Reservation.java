package com.telioarts.portfolio.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVATION", schema = "dbo")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Schema(description = "Représente une réservation de rendez-vous")
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVATION")
    @Schema(description = "Identifiant unique de la réservation", example = "1")
    private Long idReservation;

    @Column(name = "DATE_RESERVATION",  nullable = false)
    @NotNull(message = "La date de réservation est obligatoire")
    @Future(message = "La date de réservation doit être dans le futur")
    @Schema(description = "Date et heure du rendez-vous", example = "2026-05-24T10:00:00")
    private LocalDateTime dateReservation;

    @Column(name = "OBJET",  nullable = false, length = 200)
    @Schema(description = "Sujet de la demande", example = "Création de Logo")
    private String objet;

    @NotBlank(message = "L'objet de la réservation est obligatoire")
    @Column(name = "MESSAGE",  nullable = false, length = 1000)
    private String message;

    @ManyToOne
    @JoinColumn(name = "ID_VISITEUR",  nullable = false)
    private Visiteur visiteur;

    @Column(name = "STATUT",  nullable = false, length = 20)
    @Schema(description = "Statut actuel", allowableValues = {"PENDING", "CONFIRMED", "CANCELLED"})
    private String statut = "PENDING";
}
