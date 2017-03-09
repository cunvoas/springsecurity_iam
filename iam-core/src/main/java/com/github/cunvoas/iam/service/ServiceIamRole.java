/**
 * 
 */
package com.github.cunvoas.iam.service;

import java.util.List;

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.bo.IamRole;

/**
 * @author cunvoas
 */
public interface ServiceIamRole {

    IamRole findById(Integer id);
    
    List<IamRole> findByApplication(IamApplication applicaton);
    
    IamRole save(IamRole role, IamApplication applicaton);

    void delete(Integer id);
    
    List<IamRole> search(String codeOrDescription, String codeApplication);

}
