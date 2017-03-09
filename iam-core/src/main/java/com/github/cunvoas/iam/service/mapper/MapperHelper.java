/**
 * 
 */
package com.github.cunvoas.iam.service.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.CompareToBuilder;

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.bo.IamRessource;
import com.github.cunvoas.iam.bo.IamRole;
import com.github.cunvoas.iam.bo.delegation.IamDelegation;
import com.github.cunvoas.iam.bo.delegation.IamDelegationState;
import com.github.cunvoas.iam.persistance.entity.Application;
import com.github.cunvoas.iam.persistance.entity.Ressource;
import com.github.cunvoas.iam.persistance.entity.RessourceValeur;
import com.github.cunvoas.iam.persistance.entity.Role;
import com.github.cunvoas.iam.persistance.entity.delegation.Delegation;
import com.github.cunvoas.iam.persistance.entity.delegation.DelegationRole;
import com.github.cunvoas.iam.persistance.entity.delegation.DelegationState;
import com.github.cunvoas.iam.persistance.entity.delegation.state.AbstractState;
import com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateHelper;

/**
 * Mapper from/to Entity/Business.
 * author cunvoas
 */
public abstract class MapperHelper {
	
	private static IamDelegationState map(DelegationState bo) {
		IamDelegationState dto = null;
		if (bo!=null) {
			dto = new IamDelegationState();
			dto.setId(bo.getId());
			dto.setName(bo.getName());
			dto.setDiscriminator(bo.getDiscriminator());
		}
		return dto;
	}
    
    public static IamRole map(Role role) {
        IamRole iamRole = null;
        if (role!=null) {
            iamRole = new IamRole();
            iamRole.setCode(role.getCode());
            iamRole.setCommentaire(role.getCommentaire());
            iamRole.setDescription(role.getDescription());
            iamRole.setId(role.getId());
        }
        return iamRole;
    }
    
    public static IamApplication map(Application application) {
        IamApplication iamApp = null;
        if (application!=null) {
            iamApp = new IamApplication();
            iamApp.setId(application.getId());
            iamApp.setCode(application.getCode());
            iamApp.setDescription(application.getDescription());
            iamApp.setUrl(application.getUrl());
        }
        return iamApp;
    }
    
    public static Application merge(IamApplication iamApp, Application application) {
        if (application==null) {
            application = new Application();
        }
        application.setCode(iamApp.getCode());
        application.setDescription(iamApp.getDescription());
        application.setUrl(iamApp.getUrl());
        return application;
    }
    
    public static Role merge(IamRole iamRole, Role role, IamApplication iamApp) {
        if (role==null) {
            role = new Role();
        }
        
        if (iamApp!=null) {
            Application application = new Application();
            application.setId(iamApp.getId());
            role.setApplication(application);
        }
        
        role.setCode(iamRole.getCode());
        role.setDescription(iamRole.getDescription());
        role.setCommentaire(iamRole.getCommentaire());
        return role;
    }
    
    
    /**
     * Map from entity to business.
     * @param ressource
     * @param valeur
     * @return
     */
    public static IamRessource map(Ressource ressource, RessourceValeur valeur) {
        IamRessource iamRess = new IamRessource();
        iamRess.setId(ressource.getId());
        iamRess.setCode(ressource.getCode());
        iamRess.setBorneInf(ressource.getBorneInf());
        iamRess.setBorneSup(ressource.getBorneSup());

        if (valeur != null && valeur.getValue() != null) {
            iamRess.setValeur(valeur.getValue().getId());
        }
        iamRess.setId(ressource.getId());
        return iamRess;
    }
    
    /**
     * Map from business to entity.
     * @param ressource
     * @param application
     * @param iamRess
     * @return
     */
    public static Ressource merge(IamRessource ressource, Application application, Ressource iamRess) {
        if (iamRess==null) {
            iamRess = new Ressource();
            iamRess.setApplication(application);
        } else {
            iamRess.setId(ressource.getId());
            // valeurs préservées
        }
        
        iamRess.setCode(ressource.getCode());
        iamRess.setBorneInf(ressource.getBorneInf());
        iamRess.setBorneSup(ressource.getBorneSup());
        
        return iamRess;
    }
    
    /**
     * Convert tree in list.
     * @param iamRessource
     * @return
     */
    public static List<IamRessource> asList(IamRessource iamRessource) {
        List<IamRessource> liste = new ArrayList<IamRessource>();
        if (iamRessource!=null) {
            liste.add(iamRessource);
            
            if (iamRessource.getEnfants()!=null ) {
                for (IamRessource child : iamRessource.getEnfants()) {
                    child.setProfondeur(iamRessource.getProfondeur()+1);
                    liste.addAll(asList(child));
                }
            }
        }
        return liste;
    }
    
    /**
     * @param bo
     * @return
     */
    public static IamDelegation map(Delegation bo) {
    	IamDelegation dto = null;
    	if (bo!=null) {
    		dto = new IamDelegation();
    		dto.setId(bo.getId());
	    	dto.setApplication(map(bo.getApplication()));
	    	dto.setState(map(bo.getStatus()));
	    	
	    	dto.setUidDelegate(bo.getDelege());
	    	dto.setUidDelegator(bo.getDelegateur());
	    	
	    	dto.setBegin(bo.getBegin());
	    	dto.setEnd(bo.getEnd());
	    	
	    	if (CollectionUtils.isNotEmpty(bo.getDelegationRoles())) {
	    		dto.setRoles(new ArrayList<IamRole>());
	    		
	    		for (DelegationRole delegationRole : bo.getDelegationRoles()) {
	    			
	    			dto.getRoles().add(map(delegationRole.getRole()));
				}
	    	}
    	}
    	return dto;
    }
    
    public static Delegation merge(IamDelegation dto, Delegation bo) {
    	
    	if (dto!=null) {
	    	if (bo==null) {
	    		bo = new Delegation();
	    	}
	    	bo.setId(dto.getId());

			// state 
	    	if (bo.getStatus()!=null && dto.getState()!=null && dto.getState().getId()!=null) {
	    		DelegationState state = DelegationStateHelper.getState(dto.getState().getId());
    			bo.changeTo((AbstractState)state);
	    	}
	    	
	    	//application
	    	bo.setApplication(merge(dto.getApplication(), bo.getApplication()));
	    	
	    	// basic fields
	    	bo.setBegin(dto.getBegin());
	    	bo.setEnd(dto.getEnd());
	    	bo.setDelegateur(dto.getUidDelegator());
	    	bo.setDelege(dto.getUidDelegate());

			// role 
	    	if (CollectionUtils.isNotEmpty(dto.getRoles())) {
	    		// sort for fast search
	    		Collections.sort(bo.getDelegationRoles(), DelegationRoleCompatator.INSTANCE);
	    		Collections.sort(dto.getRoles(), IamRoleCompatator.INSTANCE);

	    		DelegationRole searchKey = new DelegationRole();
	    		searchKey.setDelegation(bo);
	    		searchKey.setRole(new Role());
	    		

    			// role 
	    		// get role from client
	    		List<DelegationRole> newDelegRole = new ArrayList<DelegationRole>();
	    		List<DelegationRole> delDelegRole = new ArrayList<DelegationRole>();
	    		delDelegRole.addAll(bo.getDelegationRoles());
	    		
	    		for (IamRole iamRole : dto.getRoles()) {
	    			searchKey.getRole().setId(iamRole.getId());
	    			int idx = Collections.binarySearch(bo.getDelegationRoles(), searchKey, DelegationRoleCompatator.INSTANCE);
	    			Role role = null;
	    			boolean existingRole = idx>=0;
	    			if (existingRole) {
	    				// roles already exist in DelegationRoles
	    				role = bo.getDelegationRoles().get(idx).getRole();
	    			}
	    			role = merge(iamRole, role, dto.getApplication());
	    			
    				DelegationRole dr = new DelegationRole();
    				dr.setDelegation(bo);
    				dr.setRole(role);
	    			if (!existingRole) {
	    				// new Delegation Role
	    				newDelegRole.add(dr);
	    			}
	    			// remove add ou update from memory list
	    			delDelegRole.remove(dr);
	    			
				}
	    		
	    		// Delegation Role to delete
	    		if (!delDelegRole.isEmpty()) {
	    			bo.getDelegationRoles().removeAll(delDelegRole);
	    		}
	    	}
    	}
    	return bo;
    }
    
    /**
     * @author CUNVOAS
     */
    static class DelegationRoleCompatator implements Comparator<DelegationRole> {
    	private static final DelegationRoleCompatator INSTANCE = new DelegationRoleCompatator();
    	

		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(DelegationRole o1, DelegationRole o2) {
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(o1.getId(), o2.getId());
			builder.append(o1.getRole().getId(), o2.getRole().getId());
			return builder.toComparison();
		}
    }

    
    /**
     * @author CUNVOAS
     */
    static class IamRoleCompatator implements Comparator<IamRole> {
    	private static final IamRoleCompatator INSTANCE = new IamRoleCompatator();

		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(IamRole o1, IamRole o2) {
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(o1.getId(), o2.getId());
			return builder.toComparison();
		}
    }
    
}
