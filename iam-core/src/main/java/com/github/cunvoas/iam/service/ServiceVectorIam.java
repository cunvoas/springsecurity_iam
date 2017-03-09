package com.github.cunvoas.iam.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.github.cunvoas.iam.bo.IamRawUser;
import com.github.cunvoas.iam.bo.IamRessource;
import com.github.cunvoas.iam.bo.IamRole;

/**
 * WebService interface for IAM Consumers.
 * @author CUNVOAS
 */
@WebService
public interface ServiceVectorIam {
    
    /**
     * Method used by IAM Client.
     * @param userId
     * @param applicationCode
     * @return
     */
    @WebMethod
    IamRawUser findIamRawVector(
            @WebParam(name="userId") String userId, 
            @WebParam(name="applicationId") String applicationCode
        );
    
    /**
     * Method used by IAM Constants tool.
     * @param applicationCode
     * @return
     */
    @WebMethod
    List<IamRessource> findForConstants(
            @WebParam(name="applicationId") String applicationCode
        );

    /**
     * Get de tree for user/appli/role.
     * @param userId
     * @param applicationCode
     * @param roleCode
     * @return
     */
    @WebMethod
    IamRessource findIamRessourceByRole(
            @WebParam(name="userId") String userId, 
            @WebParam(name="applicationId") String applicationCode,
            @WebParam(name="roleId") String roleCode
            );
    
    /**
     * Get de tree for user/appli.
     * @param userId
     * @param applicationCode
     * @return
     */
    @WebMethod
    List<IamRole> findIamRessource(
            @WebParam(name="userId") String userId, 
            @WebParam(name="applicationId") String applicationCode
            );
    

}