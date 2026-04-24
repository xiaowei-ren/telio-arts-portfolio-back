package com.telioarts.portfolio.bo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "PROJET", schema = "dbo")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROJET")
    private Long idProjet;

    @Column(name = "TITRE",  nullable = false, length = 100)
    private String titre;

    @Column(name = "DESCRIPTION",  nullable = false, length = 1000)
    private String description;

    @Column(name = "IMAGE_URL",  nullable = false, length = 500)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "CATEGORIE_ID_CATEGORIE")
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "ADMINISTRATEUR_ID_ADMIN")
    private Administrateur administrateur;
}
