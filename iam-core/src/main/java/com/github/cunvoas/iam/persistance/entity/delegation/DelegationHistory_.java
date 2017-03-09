package com.github.cunvoas.iam.persistance.entity.delegation;

import com.github.cunvoas.iam.persistance.entity.delegation.Delegation;
import com.github.cunvoas.iam.persistance.entity.delegation.state.AbstractState;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-12-29T14:58:17")
@StaticMetamodel(DelegationHistory.class)
public class DelegationHistory_ { 

    public static volatile SingularAttribute<DelegationHistory, Integer> id;
    public static volatile SingularAttribute<DelegationHistory, Date> dateStatus;
    public static volatile SingularAttribute<DelegationHistory, Delegation> delegation;
    public static volatile SingularAttribute<DelegationHistory, AbstractState> status;

}