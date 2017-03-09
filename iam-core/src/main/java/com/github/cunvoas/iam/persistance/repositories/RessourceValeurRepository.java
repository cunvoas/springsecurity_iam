package com.github.cunvoas.iam.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.cunvoas.iam.persistance.entity.RessourceValeur;
import com.github.cunvoas.iam.persistance.entity.RessourceValeurPK;

/**
 * Repository for RessourceValeur.
 * @author CUNVOAS
 */
public interface RessourceValeurRepository extends JpaRepository<RessourceValeur, Integer> {
    
    @Modifying
    @Query("delete from RessourceValeur rv where rv.id.rolId=:roleId")
    void deleteByRoleId(@Param("roleId") Integer roleId);

    @Modifying
    @Query("delete from RessourceValeur rv where rv.id.resId=:resId")
    void deleteByResourceId(@Param("resId") Integer roleId);
    
    RessourceValeur findById(RessourceValeurPK id);
    
}
