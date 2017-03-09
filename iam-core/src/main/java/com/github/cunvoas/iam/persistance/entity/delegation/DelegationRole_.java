package com.github.cunvoas.iam.persistance.entity.delegation;

import com.github.cunvoas.iam.persistance.entity.Role;
import com.github.cunvoas.iam.persistance.entity.delegation.Delegation;
import com.github.cunvoas.iam.persistance.entity.delegation.DelegationRolePK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-12-29T14:58:17")
@StaticMetamodel(DelegationRole.class)
public class DelegationRole_ { 

    public static volatile SingularAttribute<DelegationRole, DelegationRolePK> id;
    public static volatile SingularAttribute<DelegationRole, Delegation> delegation;
    public static volatile SingularAttribute<DelegationRole, Role> role;

}