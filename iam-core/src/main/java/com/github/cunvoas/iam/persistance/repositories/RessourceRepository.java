package com.github.cunvoas.iam.persistance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.cunvoas.iam.persistance.entity.Ressource;

/**
 * Repository for Ressource.
 * @author CUNVOAS
 */
public interface RessourceRepository extends JpaRepository<Ressource, Integer> {

    /**
     * Get Ressource and RessourceValeur from one Application and one Role.
     * @param appId
     * @param rolId
     * @return list of Ressource and RessourceValeur 
     */
    @Query("SELECT r, rv FROM Ressource r left join r.ressourceValues rv where r.application.id=:appId and coalesce(rv.id.rolId, :rolId)=:rolId order by r.borneInf asc")
    List<Object[]> findApplicationAllRessourceValeurs(@Param("appId") Integer appId, @Param("rolId") Integer rolId);
    
    /**
     * Get Ressource and RessourceValeur from one Application and one Role.
     * @param appId
     * @param rolId
     * @return list of Ressource and RessourceValeur 
     */
    @Query("SELECT r, rv FROM Ressource r inner join r.ressourceValues rv where r.application.id=:appId and rv.id.rolId=:rolId order by r.borneInf asc")
    List<Object[]> findApplicatioRessourceValeurs(@Param("appId") Integer appId, @Param("rolId") Integer rolId);
    
    /**
     * Get Ressource from one Application.
     * @param appId
     * @return
     */
    @Query("SELECT r FROM Ressource r where r.application.id=:appId order by r.borneInf asc")
    List<Ressource> findApplicationRessource(@Param("appId") Integer appId);
    
    @Modifying
    @Query("delete from Ressource r where r.application.id=:appId")
    void deleteByApplicationId(@Param("appId") Integer appId);
    
}
