package com.telioarts.portfolio.bo;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "VISITEUR",  schema = "dbo")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Visiteur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VISITEUR")
    private Long  idVisiteur;

    @Column(name = "NOM", nullable = false, length = 50)
    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;

    @Column(name = "PRENOM",  nullable = false, length = 50)
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    @Email(message = "Format de l'e-mail invalide")
    @NotBlank(message = "L'adresse e-mail est obligatoire")
    private String email;

    @Column(name = "TELEPHONE",  length = 20)
    private String telephone;


}
