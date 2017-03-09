package com.github.cunvoas.iam.persistance.entity;

import com.github.cunvoas.iam.persistance.entity.Application;
import com.github.cunvoas.iam.persistance.entity.RessourceValeur;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-12-29T14:58:17")
@StaticMetamodel(Ressource.class)
public class Ressource_ { 

    public static volatile SingularAttribute<Ressource, Integer> id;
    public static volatile SingularAttribute<Ressource, Application> application;
    public static volatile SingularAttribute<Ressource, Integer> borneInf;
    public static volatile SingularAttribute<Ressource, Integer> borneSup;
    public static volatile SingularAttribute<Ressource, String> code;
    public static volatile ListAttribute<Ressource, RessourceValeur> ressourceValues;

}