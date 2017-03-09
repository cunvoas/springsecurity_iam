package com.github.cunvoas.iam.web.front.json;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.bo.IamRessource;
import com.github.cunvoas.iam.bo.IamValue;
import com.github.cunvoas.iam.security.annotation.SecuredIam;
import com.github.cunvoas.iam.service.ServiceIamApplication;
import com.github.cunvoas.iam.service.ServiceIamRessource;
import com.github.cunvoas.iam.web.IamKeys;
import com.github.cunvoas.iam.web.front.resource.tree.JsTreeResource;
import com.github.cunvoas.iam.web.front.resource.tree.JsTreeWrapper;

/**
 * Controler for JSon REST services.
 * @author C.UNVOAS
 * 
 * in case of HTTP 406 (Not acceptable), jackson lib is not on classpath.
 */
@Controller
@RequestMapping(value = "/admin-iam/json")
public class JSonJsTreeController {
    protected static final Logger LOGGER = LoggerFactory.getLogger(JSonJsTreeController.class);

    @Autowired
    private ServiceIamRessource serviceIamRessource;
    
    @Autowired
    private ServiceIamApplication serviceIamApplication;
    
    

    /**
     * Controleur JSon pour les ressources.
     * @param request
     * @param query
     * @return
     */
    @RequestMapping(value = "/resource/tree", method = RequestMethod.GET, headers = "Accept=*/*")
    @SecuredIam(vectorKey = IamKeys.IAM_RESSOURCE_LISTER, vectorValue = IamKeys.VISIBLE)
    public @ResponseBody
    IamRessource getIamRessource(HttpServletRequest request, 
            @RequestParam("applicationCode") String applicationCode
            ) {

        IamRessource ret = serviceIamRessource.findResources(applicationCode);
        
        return ret;
    }
    
    /**
     * add or rename a node.
     * @param request
     * @param applicationCode
     * @param newTreeRes
     * @return
     */
    @RequestMapping(value = "/resource/renameTreeNode", method = RequestMethod.POST, 
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecuredIam(vectorKey = IamKeys.IAM_RESSOURCE_MODIFIER, vectorValue = IamKeys.ACTION)
    public @ResponseBody JsTreeResource saveRenameTreeNode(HttpServletRequest request, 
            @RequestParam("appCode") String applicationCode, 
            @RequestBody JsTreeResource newTreeRes
            ) {

        IamApplication application = new IamApplication();
        application.setCode(applicationCode);
        
        IamRessource newRes = JsTreeWrapper.map(newTreeRes);
        IamRessource ret = null;
        if (newTreeRes.getId().startsWith("-")) {
            int pos = -1;
            try {
                pos = Integer.parseInt(newTreeRes.getPosition());
            } catch (NumberFormatException ignore) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("unset position in children list");
                }
            }
            ret = serviceIamRessource.add(application, newRes, pos);
        } else {
            ret = serviceIamRessource.rename(newRes);
        }
        
        JsTreeResource treeRet = JsTreeWrapper.mapOne(ret, null);
        return treeRet;
    }
    

    @RequestMapping(value = "/resource/moveTreeNode", method = RequestMethod.POST, 
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecuredIam(vectorKey = IamKeys.IAM_RESSOURCE_CREER, vectorValue = IamKeys.ACTION)
    public @ResponseBody JsTreeResource saveMoveTreeNode(HttpServletRequest request, 
            @RequestParam("appCode") String applicationCode, 
            @RequestBody JsTreeResource newTreeRes
            ) {

        IamApplication application = new IamApplication();
        application.setCode(applicationCode);
        
        int pos = -1;
        try {
            pos = Integer.parseInt(newTreeRes.getPosition());
           
            
        } catch (NumberFormatException ignore) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("unset position in children list");
            }
        }
        

        Integer ressId = Integer.parseInt(newTreeRes.getId());
        Integer oldParentId = Integer.parseInt(newTreeRes.getParentOrg());
        Integer newParentId = Integer.parseInt(newTreeRes.getParent());
        
        IamRessource ret  = serviceIamRessource.move(application, ressId, oldParentId, newParentId, pos);

        JsTreeResource treeRet = JsTreeWrapper.mapOne(ret, null);
        return treeRet;
    }
    
    @RequestMapping(value = "/resource/deleteTreeNode", method = RequestMethod.POST, 
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecuredIam(vectorKey = IamKeys.IAM_RESSOURCE_SUPPRIMER, vectorValue = IamKeys.ACTION)
    public @ResponseBody JsTreeResource saveDeleteTreeNode(HttpServletRequest request, 
            @RequestParam("appCode") String applicationCode, 
            @RequestBody JsTreeResource newTreeRes
            ) {

        IamApplication application = new IamApplication();
        application.setCode(applicationCode);
        
        IamRessource newRes = JsTreeWrapper.map(newTreeRes);
        serviceIamRessource.remove(application, newRes);
        
        return newTreeRes;
    }

    
    @RequestMapping(value = "/resource/changeNodeValue", method = RequestMethod.GET, 
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecuredIam(vectorKey = IamKeys.IAM_RESSOURCE_AFFECTER_ROLE, vectorValue = IamKeys.ACTION)
    public @ResponseBody IamValue saveChangeNodeValue(HttpServletRequest request, 
            @RequestParam("appCode") String applicationCode, 
            @RequestParam("idRes") String resId, 
            @RequestParam("idRole") String roleId, 
            @RequestParam("idValue") String valueId
            ) {
        
        Integer idRes = Integer.parseInt(resId);
        Integer idRole = Integer.parseInt(roleId);
        Integer idValue = Integer.parseInt(valueId);
        
        IamValue ret = serviceIamRessource.saveResourceValue(applicationCode, idRes, idRole, idValue);
        
        return ret;
    }
    
    
    

}
