package com.github.cunvoas.iam.persistance.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the iam_utilisateur database table.
 * 
 */
@Entity
@Table(name="iam_utilisateur")
@NamedQuery(name="Utilisateur.findAll", query="SELECT u FROM Utilisateur u")
public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String detail;

    public Utilisateur() {
    }


    @Id
    @SequenceGenerator(name="IAM_UTILISATEUR_ID_GENERATOR", sequenceName="seq_utilisateur")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IAM_UTILISATEUR_ID_GENERATOR")
    @Column(name="uti_id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Column(name="uti_code", length=30)
    public String getCode() {
        return this.code;
    }

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
        if (!(obj instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) obj;
        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }
        return true;
    }


	/**
	 * Getter for detail.
	 * @return the detail
	 */
    @Column(name="uti_detail", length=100)
	public String getDetail() {
		return detail;
	}


	/**
	 * Setter for detail.
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}


}