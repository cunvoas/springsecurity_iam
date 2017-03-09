package com.github.cunvoas.iam.persistance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.cunvoas.iam.persistance.entity.Application;
import com.github.cunvoas.iam.persistance.entity.Role;

/**
 * Repository for Role.
 * @author CUNVOAS
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * Get Ressource from one Application.
     * @param appId
     * @return
     */
    @Query("SELECT r FROM Role r where r.application.id=:appId and r.code=:roleCode)")
    Role findRoleByApplicationAndCode(@Param("appId") Integer appId, @Param("roleCode") String roleCode);
    
    List<Role> findByApplication(Application application);
    
    @Modifying
    @Query("delete from Role r where r.application.id=:appId")
    void deleteByApplicationId(@Param("appId") Integer appId);
    
    @Query("SELECT r FROM Application a, Role r WHERE r.application.id=a.id AND a.code=:codeApp AND (r.code LIKE %:searchTerm% or upper(r.description) LIKE %:searchTerm%)")
    List<Role> searchRole(@Param("searchTerm") String searchTerm, @Param("codeApp") String codeApp);
}
