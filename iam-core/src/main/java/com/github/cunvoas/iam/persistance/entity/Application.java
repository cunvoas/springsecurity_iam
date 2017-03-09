package com.github.cunvoas.iam.persistance.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the iam_application database table.
 * 
 */
@Entity
@Table(name="iam_application")
@NamedQueries({
    @NamedQuery(name="Application.findAll", query="SELECT a FROM Application a")
    //,    @NamedQuery(name="Application.findByCode", query="SELECT a FROM Application a where a.code=?1")
})
public class Application implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String description;
    private String url;
    private List<Ressource> ressources;
    private List<Role> roles;

    @Id
    @SequenceGenerator(name="IAM_APPLICATION_ID_GENERATOR", sequenceName="seq_application")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IAM_APPLICATION_ID_GENERATOR")
    @Column(name="app_id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Column(name="app_code", length=50)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Column(name="app_description", length=200)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Column(name="app_url", length=300)
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    //bi-directional many-to-one association to Ressource
    @OneToMany(mappedBy="application")
    public List<Ressource> getRessources() {
        return this.ressources;
    }

    public void setRessources(List<Ressource> ressources) {
        this.ressources = ressources;
    }

    public Ressource addRessource(Ressource ressource) {
        getRessources().add(ressource);
        ressource.setApplication(this);

        return ressource;
    }

    public Ressource removeRessource(Ressource ressource) {
        getRessources().remove(ressource);
        ressource.setApplication(null);

        return ressource;
    }


    //bi-directional many-to-one association to Role
    @OneToMany(mappedBy="application", cascade={CascadeType.ALL})
    public List<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Role addRole(Role role) {
        getRoles().add(role);
        role.setApplication(this);

        return role;
    }

    public Role removeRole(Role role) {
        getRoles().remove(role);
        role.setApplication(null);

        return role;
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
        if (!(obj instanceof Application)) {
            return false;
        }
        Application other = (Application) obj;
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