package com.github.cunvoas.iam.web.front.resource.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.cunvoas.iam.bo.IamRessource;
import com.github.cunvoas.iam.service.mapper.MapperHelper;
import com.github.cunvoas.iam.web.front.util.Constants;

public abstract class JsTreeWrapper {
    
    /**
     * @param iamRessourceRoot
     * @return
     */
    public static List<JsTreeResource> map(IamRessource iamRessourceRoot, Map<Integer, String> mapValeur) {
        List<JsTreeResource> wrappedList = new ArrayList<JsTreeResource>();
        
        List<IamRessource> list = MapperHelper.asList(iamRessourceRoot);
        int tpsIds = Constants.FALSE_ID;
        for (IamRessource iamRessource : list) {
            if (iamRessource.getId()==null) {
                iamRessource.setId(tpsIds);
            }
            wrappedList.add(new JsTreeResource(iamRessource, mapValeur));
            tpsIds++;
        }
        
        return wrappedList;
    }
    
    
    public static JsTreeResource mapOne(IamRessource iamRessource, Map<Integer, String> mapValeur) {
         int tpsIds = Constants.FALSE_ID;
       if (iamRessource.getId()==null) {
           iamRessource.setId(tpsIds);
       }
         JsTreeResource ret= new JsTreeResource(iamRessource, mapValeur);
         return ret;
    }
    
    public static IamRessource map(JsTreeResource wrappedRess) {
        IamRessource ret = new IamRessource();
        
        Integer id = Integer.valueOf(wrappedRess.getId());
        if (id<0) {
            id=null;
        }
        ret.setId(id);
        
        ret.setCode(wrappedRess.getText());
        
        Integer val = null;
        if (wrappedRess.getAttr()!=null && wrappedRess.getAttr().getResourceValue()!=null) {
            val = Integer.parseInt(wrappedRess.getAttr().getResourceValue());
        }
        ret.setValeur(val);
        
        Integer idParent = null;
        if ( wrappedRess.getParent()!=null && !"#".equals(wrappedRess.getParent())) {
            idParent = Integer.parseInt(wrappedRess.getParent());
            IamRessource parentRes = new IamRessource();
            parentRes.setId(idParent);
            ret.setParent(parentRes);
        }
        
        return ret;
    }
    
}
