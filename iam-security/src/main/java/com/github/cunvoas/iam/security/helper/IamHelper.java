package com.github.cunvoas.iam.security.helper;

import java.util.Collections;

import com.github.cunvoas.iam.bo.IamRawRessource;
import com.github.cunvoas.iam.bo.IamRawRole;

public abstract class IamHelper {
    
    public static final Integer INT_NON_AFFECTE = 0;
    public static final Integer INT_INVISIBLE = 1;
    public static final Integer INT_VISIBLE = 2;
    public static final Integer INT_ACTION = 3;

    public static final String INVISIBLE = "INVISIBLE";
    public static final String VISIBLE = "VISIBLE";
    public static final String ACTION = "ACTION";
    
    /**
     * 
     * @param authenticationLevel
     * @param securityRequired
     * @return
     */
    public static boolean isSufficent(Integer authenticationLevel, String securityRequired) {
        
        boolean inverse=false;
        if (securityRequired.startsWith("!")) {
            inverse=true;
        }
        
        int user = INT_NON_AFFECTE;
        if (authenticationLevel!=null) {
            user = authenticationLevel;
        }
        
        int required = INT_NON_AFFECTE;
        if (INVISIBLE.equals(securityRequired)) {
            required = INT_INVISIBLE;
            
        } else if (VISIBLE.equals(securityRequired)) {
            required = INT_VISIBLE;
            
        } else if (ACTION.equals(securityRequired)) {
            required = INT_ACTION;
        }
        
        boolean sufficent=false;
        if (!inverse) {
            sufficent =  user>=required && required>INT_INVISIBLE;
        } else {
            // en invertion, si l'utilisateur le pouvoir, on interdit
            sufficent = !(user==required);
        }
        
        return sufficent;
    }
    
    /**
     * @param vectorCode
     * @param role
     * @return
     */
    public static Integer getPermission(String vectorCode, IamRawRole role) {
        //NON_AFFECTE;
        Integer ret = null;
        Collections.sort(role.getRessources(), IamRawRessourceComparator.INSTANCE);
        
        IamRawRessource key = new IamRawRessource();
        key.setVectorCode(vectorCode);
        int pos = Collections.binarySearch(role.getRessources(), key, IamRawRessourceComparator.INSTANCE);
        if (pos>=0 ) {
            key = role.getRessources().get(pos);
            ret = key.getValeur();
        }
        return ret;
    }
    
    

}
