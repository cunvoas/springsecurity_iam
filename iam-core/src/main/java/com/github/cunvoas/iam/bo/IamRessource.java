package com.github.cunvoas.iam.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.cunvoas.iam.exception.IamException;

@XmlRootElement
public class IamRessource implements Serializable {
    private static final long serialVersionUID = 6659170248081663920L;
    
    public static final Integer NON_AFFECTE = 0;
    public static final Integer INVISIBLE = 1;
    public static final Integer VISIBLE = 2;
    public static final Integer MODIFICATION = 3;

    private Integer id;
    private String code;
    @XmlID
    private String vectorCode;
    private Integer borneInf;
    private Integer borneSup;
    private Integer valeur = NON_AFFECTE;
    
    private transient int profondeur;
    
    private List<IamRessource> enfants;
    
    private IamRessource parent;
    
    public IamRessource(){}
    public IamRessource(String vectorCode) {
        this.vectorCode=vectorCode;
    }
    
    /**
     * @return
     */
    public boolean isNode() {
        boolean node=false;
        if (borneSup!=null && borneInf!=null && borneSup!=0 && borneInf!=0) {
            node = borneSup-borneInf>1;
        } else {
            node = enfants==null || !enfants.isEmpty();
        }
        
        return node;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("[").append(id).append("] ");
        sBuilder.append(code);
        sBuilder.append(" - {").append(borneInf).append(";").append(borneSup).append("} ");
        sBuilder.append("VECTEUR: ").append(getVectorCode());
        return sBuilder.toString();
    }
    

    /**
     * Getter for id.
     * 
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter for id.
     * 
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for code.
     * 
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter for code.
     * 
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        if (code!=null) {
             this.code = code.trim().toUpperCase();
        }
       
    }

    /**
     * Getter for valeur.
     * 
     * @return the valeur
     */
    public Integer getValeur() {
        return valeur;
    }

    /**
     * Setter for valeur.
     * 
     * @param valeur
     *            the valeur to set
     */
    public void setValeur(Integer valeur) {
        this.valeur = valeur;
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
        if (!(obj instanceof IamRessource)) {
            return false;
        }
        IamRessource other = (IamRessource) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * Getter for borneInf.
     * @return the borneInf
     */
    public Integer getBorneInf() {
        return borneInf;
    }

    /**
     * Setter for borneInf.
     * @param borneInf the borneInf to set
     */
    public void setBorneInf(Integer borneInf) {
        this.borneInf = borneInf;
    }

    /**
     * Getter for borneSup.
     * @return the borneSup
     */
    public Integer getBorneSup() {
        return borneSup;
    }

    /**
     * Setter for borneSup.
     * @param borneSup the borneSup to set
     */
    public void setBorneSup(Integer borneSup) {
        this.borneSup = borneSup;
    }

    /**
     * Getter for parent.
     * special anotation for SOAP or JSON serialisation.
     * @return the parent
     */
    @XmlIDREF
    @JsonIgnore
    public IamRessource getParent() {
        return parent;
    }

    /**
     * Setter for parent.
     * @param parent the parent to set
     */
    public void setParent(IamRessource parent) {
        this.parent = parent;
    }

    /**
     * Getter for enfants.
     * @return the enfants
     */
    public List<IamRessource> getEnfants() {
        return enfants;
    }
    
    /**
     * @return
     */
    public boolean avecEnfant() {
        return enfants!=null && !enfants.isEmpty();
    }

    /**
     * Setter for enfants.
     * @param enfants the enfants to set
     */
    public void setEnfants(List<IamRessource> enfants) {
        this.enfants = enfants;
    }

    /**
     * @param enfant
     */
    public void addEnfant(IamRessource enfant) {
        if (this.enfants == null ) {
            this.enfants = new ArrayList<IamRessource>();
        }
        validateChild(enfant);
        enfant.setParent(this);
        if (this.vectorCode==null && this.getParent()==null) {
            this.vectorCode=this.code;
        }
        enfant.setVectorCode(this.vectorCode + "." + enfant.getCode());
        this.enfants.add(enfant);
    }
    
    /**
     * validate business rules.
     * @param enfant
     */
    private void validateChild(IamRessource enfant) {
         String childCode = enfant.getCode();
         if (childCode==null || "".equals(childCode.trim())) {
             throw new IamException("RULE_03_005");
         }
         for (IamRessource child : this.enfants) {
             if (childCode.equals(child.getCode())) {
                 throw new IamException("RULE_03_004");
             }
         }
    }

    /**
     * @param enfant
     */
    public void addEnfant(Integer newIndex, IamRessource enfant) {
        if (this.enfants == null ) {
            this.enfants = new ArrayList<IamRessource>();
        }
        validateChild(enfant);
        
        enfant.setParent(this);
        if (this.vectorCode==null && this.getParent()==null) {
            this.vectorCode=this.code;
        }
        enfant.setVectorCode(this.vectorCode + "." + enfant.getCode());
        
        if (newIndex!=null && newIndex>=0) {
            this.enfants.add(newIndex, enfant);
        } else {
            this.enfants.add(enfant);
        }
        
    }

    /**
     * Getter for vectorCode.
     * @return the vectorCode
     */
    public String getVectorCode() {
        return vectorCode;
        
    }

    /**
     * Setter for vectorCode.
     * @param vectorCode the vectorCode to set
     */
    private void setVectorCode(String vectorCode) {
        this.vectorCode = vectorCode;
    }

    /**
     * Getter for profondeur.
     * @return the profondeur
     */
    public int getProfondeur() {
        return profondeur;
    }

    /**
     * Setter for profondeur.
     * @param profondeur the profondeur to set
     */
    public void setProfondeur(int profondeur) {
        this.profondeur = profondeur;
    }
    
}
