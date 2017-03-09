package com.github.cunvoas.iam.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.cunvoas.iam.persistance.entity.Valeur;

/**
 * Repository for Valeur.
 * @author CUNVOAS
 */
public interface ValeurRepository extends JpaRepository<Valeur, Integer> {

}