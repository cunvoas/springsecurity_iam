package com.github.cunvoas.iam.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.cunvoas.iam.persistance.entity.LdapMember;

/**
 * Repository for LdapMember.
 * @author CUNVOAS
 */
public interface LdapMemberRepository extends JpaRepository<LdapMember, Integer> {

}