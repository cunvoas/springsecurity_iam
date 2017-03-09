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
 * The persistent class for the iam_ressource database table.
 * @see http://sqlpro.developpez.com/cours/arborescence/
 */
@Entity
@Table(name="iam_ressource")
@NamedQueries({
    @NamedQuery(name="Ressource.findByApplication", query="SELECT r FROM Ressource r where r.application.id=?1")
    //, @NamedQuery(name="Ressource.findApplicationRessourceValeurs", query="SELECT r, rv FROM Ressource r right outer join RessourceValeur rv where rv.application.id=?1 order by r.borneInf")
})
public class Ressource implements Serializable, Comparable<Ressource> {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer borneInf;
    private Integer borneSup;
    private String code;
    private Application application;
    private List<RessourceValeur> ressourceValues;


    @Id
    @SequenceGenerator(name="IAM_RESSOURCE_ID_GENERATOR", sequenceName="seq_ressource")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IAM_RESSOURCE_ID_GENERATOR")
    @Column(name="res_id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Column(name="res_borne_inf")
    public Integer getBorneInf() {
        return this.borneInf;
    }

    public void setBorneInf(Integer borneInf) {
        this.borneInf = borneInf;
    }


    @Column(name="res_borne_sup")
    public Integer getBorneSup() {
        return this.borneSup;
    }

    public void setBorneSup(Integer borneSup) {
        this.borneSup = borneSup;
    }


    @Column(name="res_code", length=100)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    //bi-directional many-to-one association to Application
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="res_app_id")
    public Application getApplication() {
        return this.application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }


    //bi-directional many-to-one association to RessourceValeur
    @OneToMany(mappedBy="ressource")
    public List<RessourceValeur> getRessourceValues() {
        return this.ressourceValues;
    }

    public void setRessourceValues(List<RessourceValeur> ressourceValues) {
        this.ressourceValues = ressourceValues;
    }

    public RessourceValeur addRessourceValue(RessourceValeur ressourceValue) {
        getRessourceValues().add(ressourceValue);
        ressourceValue.setRessource(this);

        return ressourceValue;
    }

    public RessourceValeur removeRessourceValue(RessourceValeur ressourceValue) {
        getRessourceValues().remove(ressourceValue);
        ressourceValue.setRessource(null);

        return ressourceValue;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Ressource o) {
        return  this.getBorneInf()!=null?this.getBorneInf().compareTo(o.getBorneInf()):-1;
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
        if (!(obj instanceof Ressource)) {
            return false;
        }
        Ressource other = (Ressource) obj;
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