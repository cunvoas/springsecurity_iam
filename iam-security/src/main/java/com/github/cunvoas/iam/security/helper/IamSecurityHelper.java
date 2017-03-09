package com.github.cunvoas.iam.security.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.cunvoas.iam.bo.IamRawRessource;
import com.github.cunvoas.iam.bo.IamRawRole;
import com.github.cunvoas.iam.security.authority.IamGrantedAuthority;
import com.github.cunvoas.iam.security.authority.IamUserDetails;

/**
 * @author cunvoas
 */
public abstract class IamSecurityHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IamSecurityHelper.class);

    public static Integer getMergedSecurityLevel(String key) {
        Integer ret  = IamHelper.INT_NON_AFFECTE;
        IamUserDetails userDetail = (IamUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        IamRawRessource searchKey = new IamRawRessource();
        searchKey.setVectorCode(key);
        IamRawRessource ressource = findInList(userDetail.getMergeRessources(), searchKey);
        if (ressource!=null) {
            ret = ressource.getValeur();
        }
        return ret;
    }
    
    public static Integer getRoleSecurityLevel(String key) {
        Integer ret = IamHelper.INT_NON_AFFECTE;
        
        IamUserDetails userDetail = (IamUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetail!=null &&  userDetail.getCurrentRole()!=null ) {
        	ret = findVectorInRole( userDetail.getCurrentRole().getRole(), key);
        } else {
        	LOGGER.warn("no current role for user '{}' on key :{}", userDetail.getUsername(), key);
        }
        
        return ret;
    }
    
    
    private static Integer findVectorInRole(IamRawRole role, String key) {
        Integer ret = IamHelper.INT_NON_AFFECTE;
        if (role!=null) {
	        List<IamRawRessource> ress = role.getRessources();
	        IamRawRessource searchKey = new IamRawRessource();
	        searchKey.setVectorCode(key);
	        IamRawRessource found = findInList(ress, searchKey);
	        if (found!=null) {
	            ret = found.getValeur();
	        }
        } else {
        	LOGGER.warn("no role for key :{}", key);
        }
        return ret;
    }
    


    /**
     * Sort Roles an resources for fast search.
     */
    public static void sortCredentail() {
        List<IamRawRessource> mergeRessources = new ArrayList<IamRawRessource>();
        IamUserDetails userDetail = (IamUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<IamGrantedAuthority> auths = (List<IamGrantedAuthority>)userDetail.getAuthorities();
        Collections.sort(auths, IamGrantedAuthorityComparator.INSTANCE);
        
        for (IamGrantedAuthority iamGrantedAuthority : auths) {
            List<IamRawRessource> iamRess = iamGrantedAuthority.getRole().getRessources();
            Collections.sort(iamRess, IamRawRessourceComparator.INSTANCE);
            
            merge(iamRess, mergeRessources);
        }
        
        userDetail.setMergeRessources(mergeRessources);
    }
    
    /**
     * Merge All ressources by keep the most powerfull right.
     * @param iamRess
     * @param mergeRessources
     */
    private static void merge(List<IamRawRessource> iamRess, List<IamRawRessource> mergeRessources) {
        
        if (mergeRessources.isEmpty()) {
            mergeRessources.addAll(iamRess);
            
        } else {
            for (IamRawRessource iamRawRessource : mergeRessources) {
                IamRawRessource mergedRessource = findInList(mergeRessources, iamRawRessource);
                if (mergedRessource!=null && mergedRessource.getValeur()<iamRawRessource.getValeur()) {
                    mergeRessources.remove(mergedRessource);
                    mergeRessources.add(iamRawRessource);
                    Collections.sort(mergeRessources, IamRawRessourceComparator.INSTANCE);
                }
            }
            
        }
    }
    
    /**
     * Find the item in merged list. 
     * @param mergeRessources
     * @param iamRawRessource
     * @return
     */
    private static IamRawRessource findInList(List<IamRawRessource> mergeRessources, IamRawRessource iamRawRessource) {
        IamRawRessource ret = null;
        int index = Collections.binarySearch(mergeRessources, iamRawRessource, IamRawRessourceComparator.INSTANCE);
        if (index>=0) {
            ret = mergeRessources.get(index);
        }
        return ret;
    }
    
}
