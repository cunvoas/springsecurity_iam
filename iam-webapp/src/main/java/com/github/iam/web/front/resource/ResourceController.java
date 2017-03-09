package com.github.cunvoas.iam.web.front.resource;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.bo.IamRessource;
import com.github.cunvoas.iam.bo.IamRole;
import com.github.cunvoas.iam.exception.IamException;
import com.github.cunvoas.iam.security.annotation.SecuredIam;
import com.github.cunvoas.iam.service.ServiceIamApplication;
import com.github.cunvoas.iam.service.ServiceIamRessource;
import com.github.cunvoas.iam.service.ServiceIamRole;
import com.github.cunvoas.iam.web.IamKeys;
import com.github.cunvoas.iam.web.front.resource.tree.JsTreeResource;
import com.github.cunvoas.iam.web.front.resource.tree.JsTreeWrapper;
import com.github.cunvoas.iam.web.front.util.Constants;
import com.github.cunvoas.iam.web.front.validator.GenericFormValidator;

/**
 * Applicatives resources and resourceValues Controller.
 * 
 * @author CUNVOAS
 */
@Controller
@RequestMapping("/admin-iam/resource")
public class ResourceController {
    protected static final Logger LOGGER = LoggerFactory
            .getLogger(ResourceController.class);

    @Autowired
    private ServiceIamApplication serviceIamApplication;
    @Autowired
    private ServiceIamRessource serviceIamRessource;
    @Autowired
    private ServiceIamRole serviceIamRole;
    @Autowired
    private GenericFormValidator validator;

    /**
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/findResource", method = RequestMethod.GET)
    @SecuredIam(vectorKey = IamKeys.IAM_RESSOURCE_LISTER, vectorValue = IamKeys.VISIBLE)
    public ModelAndView findResources(HttpServletRequest request,
            HttpServletResponse response, ModelMap model) {
        request.getSession().setAttribute(Constants.UC_ACTIVE, "ressource");

        String codeApplication = request.getParameter(Constants.APP_ID);

        try {
            if (StringUtils.isNotEmpty(codeApplication)) {
                IamApplication iamApp = serviceIamApplication.findByCode(codeApplication);
                IamRessource ressRoot = serviceIamRessource.findResources(codeApplication);

                if (ressRoot == null) {
                    if (iamApp != null) {
                        ressRoot = new IamRessource();
                        ressRoot.setCode(iamApp.getCode());
                        ressRoot.setId(Constants.FALSE_ID);
                    } else {
                        throw new IamException("RULE_01_001");
                    }
                }

                model.put(Constants.MODEL_RESS, ressRoot);

                Map<Integer, String> mapValeur = serviceIamRessource.getReferenceResourceValue();
                model.put(Constants.MODEL_RES_VAL_REF, serviceIamRessource.getValues());
                
                List<JsTreeResource> jsTreeRess = JsTreeWrapper.map(ressRoot,mapValeur);
                ObjectMapper jsonMapper = new ObjectMapper();
                model.put(Constants.MODEL_RESS_JSTREE,jsonMapper.writeValueAsString(jsTreeRess));
                model.put(Constants.MODEL_APP, iamApp);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("SERIALIZE_JSON", e);
            model.put("errors", "json");
        }

        return new ModelAndView(Constants.VIEW_RESOURCE_TREE);
    }

    @RequestMapping(value = "/values", method = RequestMethod.GET)
    @SecuredIam(vectorKey = IamKeys.IAM_RESSOURCE_AFFECTER_ROLE, vectorValue = IamKeys.VISIBLE)
    public ModelAndView getValues(HttpServletRequest request, HttpServletResponse response, ModelMap model, @ModelAttribute SearchResValForm form, BindingResult bindingResult) {
        
        populate(model, request, null, bindingResult);

        return new ModelAndView(Constants.VIEW_RESOURCE_VALUE);
    }

    /**
     * Populate the model.
     * @param model
     * @param request
     * @param form
     */
    private void populate(ModelMap model, HttpServletRequest request,
            SearchResValForm form, BindingResult bindingResult) {
        IamApplication iamApplication = null;
        IamRole iamRole = null;

        // get request parameters
        String codeApplication = request.getParameter(Constants.APP_ID);
        String roleId = request.getParameter(Constants.ROLE_ID);
        boolean postPage = false;
        if (form != null && 
                StringUtils.isNotBlank(form.getAppCode()) && 
                StringUtils.isNotBlank(form.getRoleId())) {
            codeApplication = form.getAppCode();
            roleId = form.getRoleId();
            postPage = true;
        }

        
        // check form
        if (form==null) {
            form = new SearchResValForm();
        }
        model.put(Constants.FORM_RESOURCE_VALUE_SEARCH, form);
        
        try {

            // validate appCode
            if (StringUtils.isNotEmpty(codeApplication)) {

                iamApplication = serviceIamApplication.findByCode(codeApplication);
                if (iamApplication == null) {
                    throw new IamException("RULE_01_001");
                }
                
                form.setAppId(String.valueOf(iamApplication.getId()));
                form.setAppCode(iamApplication.getCode());
                form.setAppDesc(iamApplication.getDescription());
                model.put(Constants.MODEL_APP, iamApplication);

                List<IamRole> roles = serviceIamRole.findByApplication(iamApplication);
                model.put(Constants.MODEL_ROLES, roles);
                
                // validate role
                if (StringUtils.isNotEmpty(roleId)) {

                    Integer iRoleId = Integer.parseInt(roleId);
                    iamRole = serviceIamRole.findById(iRoleId);
                    
                    
                    form.setRoleId(String.valueOf(iamRole.getId()));
                    form.setRoleCode(iamRole.getCode());
                    form.setRoleDesc(iamRole.getDescription());
                    model.put(Constants.MODEL_ROLE, iamRole);

                    IamRessource ressRoot = serviceIamRessource.findResourcesAndValues(iamApplication, iamRole);

                    if (ressRoot == null) {
                        throw new IamException("RULE_03_006");
                    }

                    model.put(Constants.MODEL_RESS, ressRoot);

                    
                    Map<Integer, String> mapValeur = serviceIamRessource.getReferenceResourceValue();
                    model.put(Constants.MODEL_RES_VAL_REF, serviceIamRessource.getValues());
                    
                    List<JsTreeResource> jsTreeRess = JsTreeWrapper.map(ressRoot,mapValeur);
                    ObjectMapper jsonMapper = new ObjectMapper();
                    model.put(Constants.MODEL_RESS_JSTREE,jsonMapper.writeValueAsString(jsTreeRess));
                    
            
                } else {
                    if (postPage) {
                        throw new IamException("RULE_03_003");
                    }
                }
            } else {
                if (postPage) {
                    throw new IamException("RULE_03_003");
                }
            }
            
        } catch (IamException e) {
            LOGGER.error(IamException.BUSINESS_ERROR, e.getMessage());
            if (e.isBusinessRuleViolated()) {
                bindingResult.reject(IamException.BUSINESS_ERROR, e.getCode());
                model.put(Constants.FORM_ERRORS,bindingResult.getAllErrors());
            }
        } catch (NumberFormatException e) {
            LOGGER.error("ROLE_ID", e);
            model.put("errors", "ROLE_ID");
        } catch (JsonProcessingException e) {
            LOGGER.error("SERIALIZE_JSON", e);
            model.put("errors", "json");
        }
    }

    /**
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/values", method = RequestMethod.POST)
    @SecuredIam(vectorKey = IamKeys.IAM_RESSOURCE_AFFECTER_ROLE, vectorValue = IamKeys.VISIBLE)
    public ModelAndView searchValues(HttpServletRequest request,
            HttpServletResponse response, ModelMap model,
            @ModelAttribute SearchResValForm form, BindingResult bindingResult) {

        // valider le formulaire
        validator.validate(form, bindingResult);
        if (!bindingResult.hasErrors()) {
            populate(model, request, form, bindingResult);
        } else {
            model.put(Constants.FORM_ERRORS, bindingResult.getAllErrors());
        }

        return new ModelAndView(Constants.VIEW_RESOURCE_VALUE);
    }

}
