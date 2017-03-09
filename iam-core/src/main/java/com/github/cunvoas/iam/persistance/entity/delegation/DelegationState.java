package com.github.cunvoas.iam.persistance.entity.delegation;

import java.io.Serializable;


/**
 * @author cunvoas
 */
public interface DelegationState extends Serializable {
	
    Integer ID_DEMAND=1;    // start status
    Integer ID_READ=2;        // the delegated person have read de demand
    Integer ID_ACCEPTED=3;  //the delegated person have accepted de demand
    
    // End states
    Integer ID_REFUSED=4;   //the delegated person have refused de demand
    Integer ID_EXPIRED=5;   //the delegation has expired
    Integer ID_CANCELED=6;   //the delegation is canceled
    Integer ID_TERMINATED=7;   //the delegation is finished
	
	
    String STATE_DEMAND="DMD";    // start status
    String STATE_READ="READD";        // the delegated person have read de demand
    String STATE_ACCEPTED="ACCP";  //the delegated person have accepted de demand
    
    // End states
    String STATE_REFUSED="REFU";   //the delegated person have refused de demand
    String STATE_EXPIRED="EXPR";   //the delegation has expired
    String STATE_CANCELED="CANC";   //the delegation is canceled
    String STATE_TERMINATED="TERM";   //the delegation is finished
    
    // accessors
    Integer getId();
    void setId(Integer id);
    String getName();
    void setName(String name);
    String getDiscriminator();
    void setDiscriminator(String disc);
    

}
