package com.github.cunvoas.iam.persistance.entity;

import com.github.cunvoas.iam.persistance.entity.Ressource;
import com.github.cunvoas.iam.persistance.entity.RessourceValeurPK;
import com.github.cunvoas.iam.persistance.entity.Role;
import com.github.cunvoas.iam.persistance.entity.Valeur;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-12-29T14:58:17")
@StaticMetamodel(RessourceValeur.class)
public class RessourceValeur_ { 

    public static volatile SingularAttribute<RessourceValeur, RessourceValeurPK> id;
    public static volatile SingularAttribute<RessourceValeur, Ressource> ressource;
    public static volatile SingularAttribute<RessourceValeur, Valeur> value;
    public static volatile SingularAttribute<RessourceValeur, Role> role;

}