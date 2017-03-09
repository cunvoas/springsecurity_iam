package com.github.cunvoas.iam.service;

import java.util.List;
import java.util.Map;

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.bo.IamRessource;
import com.github.cunvoas.iam.bo.IamRole;
import com.github.cunvoas.iam.bo.IamValue;
import com.github.cunvoas.iam.exception.IamException;

public interface ServiceIamRessource {

    /**
     * @param iamRessource
     * @param applicationCode
     * @return
     */
    IamRessource saveRoot(IamRessource iamRessource, String applicationCode);
    
    /**
     * Get possible values for mapping.
     * @return
     */
    Map<Integer, String> getReferenceResourceValue();
    
    /**
     * Get possible values for reference.
     * @return
     */
    List<IamValue> getValues();
    
    
    /**
     * Get all ressource leaf only.
     * @param applicationCode
     * @return
     */
    List<IamRessource> findRessourceLeaf(String applicationCode);
    
    /**
     * Get tree of all resouces for the application.
     * @param applicationId
     * @return
     */
    IamRessource findResources(String applicationId);
    
    /**
     * Get tree of all resouces and values for the application.
     * @param application
     * @param role
     * @return
     */
    IamRessource findResourcesAndValues(IamApplication application, IamRole role);
    
    /**
     * Get tree of all resouces and valuated values for the application.
     * @param application
     * @param role
     * @return
     */
    IamRessource getRessourceValueTree(IamApplication application, IamRole role);

    /**
     * ReCompute Interval values.
     * @param iamRessource
     */
    void computeInterval(IamRessource iamRessource);
    
    /**
     * Validate business rules.
     * @param iamApplication
     * @param iamRessource
     * @throws IamException
     */
    void validate(IamApplication iamApplication, IamRessource iamRessource) throws IamException;
    
    /**
     * atomic action dedicated for REST services.
     * @param renamed
     */
    IamRessource rename(IamRessource renamed);
    
    /**
     * atomic action dedicated for REST services.
     * @param application application of resources
     * @param itemId resouce to be moved
     * @param newParentId resouce to be moved
     * @param newIdx position under new parent
     * @return
     */
    IamRessource move(IamApplication application, Integer itemId, Integer oldParentId, Integer newParentId, int newIdx);
    
    /**
     * atomic action dedicated for REST services.
     * @param application application od resources
     * @param add resouce to add
     * @param newIdx position under new parent
     */
    IamRessource add(IamApplication application, IamRessource add, int newIdx);
    
    /**
     * atomic action dedicated for REST services.
     * @param application application od resources
     * @param removed resouce to be removed
     */
    void remove(IamApplication application, IamRessource removed);
    
    
    /**
     * save a resourceValue.
     * @param codeApplication
     * @param resId
     * @param roleId
     * @param valId
     * @return
     */
    IamValue saveResourceValue(String codeApplication, Integer resId, Integer roleId, Integer valId);
}