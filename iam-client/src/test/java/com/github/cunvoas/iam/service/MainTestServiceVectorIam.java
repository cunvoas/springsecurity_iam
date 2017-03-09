package com.github.cunvoas.iam.service;

import com.github.cunvoas.iam.bo.IamRawUser;
import com.github.cunvoas.iam.service.impl.ServiceVectorIamImplService;


public class MainTestServiceVectorIam {

    public static void main(String[] args) {
        ServiceVectorIam service = (new ServiceVectorIamImplService()).getServiceVectorIamImplPort();
        
            
        IamRawUser users = service.findIamRawVector("ADMIN_IAM", "IAM");
        System.out.println(users);
        

    }

}
