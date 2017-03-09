package com.github.cunvoas.iam.persistance.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the iam_ldap_member database table.
 * 
 */
@Entity
@Table(name="iam_ldap_member")
@NamedQuery(name="LdapMember.findAll", query="SELECT l FROM LdapMember l")
public class LdapMember implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String ldapQuery;
    private Role role;

    @Id
    @SequenceGenerator(name="IAM_LDAP_MEMBER_ID_GENERATOR", sequenceName="seq_ldap_member")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IAM_LDAP_MEMBER_ID_GENERATOR")
    @Column(name="ldap_id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Column(name="ldap_query", length=500)
    public String getLdapQuery() {
        return this.ldapQuery;
    }

    public void setLdapQuery(String ldapQuery) {
        this.ldapQuery = ldapQuery;
    }


    //bi-directional many-to-one association to Role
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ldap_rol_id")
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }


    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof LdapMember)) {
            return false;
        }
        LdapMember other = (LdapMember) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        return true;
    }

}