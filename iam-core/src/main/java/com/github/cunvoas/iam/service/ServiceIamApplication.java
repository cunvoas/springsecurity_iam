package com.github.cunvoas.iam.service;

import java.util.List;

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.bo.IamDashboard;

public interface ServiceIamApplication {
    
    IamApplication findForExport(String codeApplication);
    
    IamApplication findByCode(String codeApplication);
    IamApplication findById(Integer idApplication);
    
    List<IamApplication> findAll();
    
    IamApplication save(IamApplication application);
    
    void delete(String codeApplication);
    
    void saveUpload(IamApplication application);
    
    List<IamApplication> search(String codeOrDescription);
    
    IamDashboard getIamDashboard();
    
}
