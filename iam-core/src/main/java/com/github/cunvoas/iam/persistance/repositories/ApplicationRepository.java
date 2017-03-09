package com.github.cunvoas.iam.persistance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.cunvoas.iam.persistance.entity.Application;

/**
 * Repository for Application.
 * @author CUNVOAS
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    
    Application findByCode(String code);
    
    @Query("SELECT a FROM Application a WHERE a.code LIKE %:searchTerm% or upper(a.description) LIKE %:searchTerm%")
    List<Application> searchApplication(@Param("searchTerm") String searchTerm);
    
}
