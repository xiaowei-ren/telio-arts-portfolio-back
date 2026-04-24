package com.telioarts.portfolio.dal;

import com.telioarts.portfolio.bo.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Administrateur, Long> {
    Optional<Administrateur> findByLogin(String login);
}
