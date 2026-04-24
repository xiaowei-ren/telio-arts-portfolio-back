package com.telioarts.portfolio.dal;

import com.telioarts.portfolio.bo.Visiteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisiteurRepository extends JpaRepository<Visiteur, Long> {
    Optional<Visiteur> findByEmail(String email);
}
