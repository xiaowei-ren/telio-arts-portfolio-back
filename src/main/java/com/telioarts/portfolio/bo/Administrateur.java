package com.telioarts.portfolio.bo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "ADMINISTRATEUR", schema = "dbo")
public class Administrateur {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ADMIN")
    private Long idAdmin;

    @Column(name = "ID_VISITEUR")
    private Long idVisiteur;

    @Column(name = "LOGIN", nullable = false, length = 50)
    private String login;

    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;
}
