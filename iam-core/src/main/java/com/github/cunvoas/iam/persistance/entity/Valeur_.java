package com.github.cunvoas.iam.persistance.entity;

import com.github.cunvoas.iam.persistance.entity.RessourceValeur;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-12-29T14:58:17")
@StaticMetamodel(Valeur.class)
public class Valeur_ { 

    public static volatile SingularAttribute<Valeur, Integer> id;
    public static volatile SingularAttribute<Valeur, String> valeur;
    public static volatile ListAttribute<Valeur, RessourceValeur> ressourceValues;

}