package com.github.cunvoas.iam.persistance.entity;

import com.github.cunvoas.iam.persistance.entity.Application;
import com.github.cunvoas.iam.persistance.entity.LdapMember;
import com.github.cunvoas.iam.persistance.entity.RessourceValeur;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-12-29T14:58:17")
@StaticMetamodel(Role.class)
public class Role_ { 

    public static volatile SingularAttribute<Role, Integer> id;
    public static volatile ListAttribute<Role, LdapMember> ldapMemberQuery;
    public static volatile SingularAttribute<Role, Application> application;
    public static volatile SingularAttribute<Role, String> description;
    public static volatile SingularAttribute<Role, String> code;
    public static volatile ListAttribute<Role, RessourceValeur> ressourceValues;
    public static volatile SingularAttribute<Role, String> commentaire;

}