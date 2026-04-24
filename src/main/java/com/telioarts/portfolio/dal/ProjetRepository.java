package com.telioarts.portfolio.dal;

import com.telioarts.portfolio.bo.Categorie;
import com.telioarts.portfolio.bo.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    //Récupérer la liste des projets par catégorie
    public List<Projet> findByCategorie(Categorie categorie);
}
