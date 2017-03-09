package com.github.cunvoas.iam.persistance.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the iam_role database table.
 * 
 */
@Entity
@Table(name="iam_role")

@NamedQueries({
    @NamedQuery(name="Role.findAll", query="SELECT r FROM Role r"),
    @NamedQuery(name="Role.findByApplicationCode", query="SELECT r FROM Role r where r.application.code=?1")
})

public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String commentaire;
    private String description;
    private List<LdapMember> ldapMemberQuery;
    private List<RessourceValeur> ressourceValues;
    private Application application;

    @Id
    @SequenceGenerator(name="IAM_ROLE_ID_GENERATOR", sequenceName="seq_role")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IAM_ROLE_ID_GENERATOR")
    @Column(name="rol_id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Column(name="rol_commentaire", length=1000)
    public String getCommentaire() {
        return this.commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }


    @Column(name="rol_description", length=200)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    //bi-directional many-to-one association to LdapMember
    @OneToMany(mappedBy="role")
    public List<LdapMember> getLdapMemberQuery() {
        return this.ldapMemberQuery;
    }

    public void setLdapMemberQuery(List<LdapMember> ldapMemberQuery) {
        this.ldapMemberQuery = ldapMemberQuery;
    }

    public LdapMember addLdapMemberQuery(LdapMember ldapMemberQuery) {
        getLdapMemberQuery().add(ldapMemberQuery);
        ldapMemberQuery.setRole(this);

        return ldapMemberQuery;
    }

    public LdapMember removeLdapMemberQuery(LdapMember ldapMemberQuery) {
        getLdapMemberQuery().remove(ldapMemberQuery);
        ldapMemberQuery.setRole(null);

        return ldapMemberQuery;
    }


    //bi-directional many-to-one association to RessourceValeur
    @OneToMany(mappedBy="role")
    public List<RessourceValeur> getRessourceValues() {
        return this.ressourceValues;
    }

    public void setRessourceValues(List<RessourceValeur> ressourceValues) {
        this.ressourceValues = ressourceValues;
    }

    public RessourceValeur addRessourceValue(RessourceValeur ressourceValue) {
        getRessourceValues().add(ressourceValue);
        ressourceValue.setRole(this);

        return ressourceValue;
    }

    public RessourceValeur removeRessourceValue(RessourceValeur ressourceValue) {
        getRessourceValues().remove(ressourceValue);
        ressourceValue.setRole(null);

        return ressourceValue;
    }


    //bi-directional many-to-one association to Application
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="rol_app_id")
    public Application getApplication() {
        return this.application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }



    /**
     * Getter for code.
     * @return the code
     */
    @Column(name="rol_code", length=50)
    public String getCode() {
        return code;
    }


    /**
     * Setter for code.
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }


    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        if (!(obj instanceof Role)) {
            return false;
        }
        Role other = (Role) obj;
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