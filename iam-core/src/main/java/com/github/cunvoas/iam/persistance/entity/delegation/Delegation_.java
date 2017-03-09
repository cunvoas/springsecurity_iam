package com.github.cunvoas.iam.persistance.entity.delegation;

import com.github.cunvoas.iam.persistance.entity.Application;
import com.github.cunvoas.iam.persistance.entity.delegation.DelegationHistory;
import com.github.cunvoas.iam.persistance.entity.delegation.DelegationRole;
import com.github.cunvoas.iam.persistance.entity.delegation.state.AbstractState;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-12-29T14:58:17")
@StaticMetamodel(Delegation.class)
public class Delegation_ { 

    public static volatile SingularAttribute<Delegation, Integer> id;
    public static volatile ListAttribute<Delegation, DelegationHistory> historique;
    public static volatile SingularAttribute<Delegation, Application> application;
    public static volatile SingularAttribute<Delegation, String> delege;
    public static volatile SingularAttribute<Delegation, String> delegateur;
    public static volatile SingularAttribute<Delegation, AbstractState> status;
    public static volatile ListAttribute<Delegation, DelegationRole> delegationRoles;
    public static volatile SingularAttribute<Delegation, Date> statusDate;
    public static volatile SingularAttribute<Delegation, Date> end;
    public static volatile SingularAttribute<Delegation, Date> begin;

}