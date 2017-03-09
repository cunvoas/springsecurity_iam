package com.github.cunvoas.iam.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.cunvoas.iam.persistance.entity.Utilisateur;

/**
 * Repository for Utilisateur.
 * @author CUNVOAS
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findByCode(String code);
}
