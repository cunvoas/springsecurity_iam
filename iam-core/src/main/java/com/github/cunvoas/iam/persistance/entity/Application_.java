package com.github.cunvoas.iam.persistance.entity;

import com.github.cunvoas.iam.persistance.entity.Ressource;
import com.github.cunvoas.iam.persistance.entity.Role;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-12-29T14:58:17")
@StaticMetamodel(Application.class)
public class Application_ { 

    public static volatile SingularAttribute<Application, Integer> id;
    public static volatile SingularAttribute<Application, String> description;
    public static volatile ListAttribute<Application, Role> roles;
    public static volatile ListAttribute<Application, Ressource> ressources;
    public static volatile SingularAttribute<Application, String> code;
    public static volatile SingularAttribute<Application, String> url;

}