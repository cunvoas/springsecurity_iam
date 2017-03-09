package com.github.cunvoas.iam.web.front.role;

import java.util.ArrayList;
import java.util.List;

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

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.bo.IamRole;
import com.github.cunvoas.iam.exception.IamException;
import com.github.cunvoas.iam.security.annotation.SecuredIam;
import com.github.cunvoas.iam.service.ServiceIamApplication;
import com.github.cunvoas.iam.service.ServiceIamRole;
import com.github.cunvoas.iam.web.IamKeys;
import com.github.cunvoas.iam.web.front.util.Constants;
import com.github.cunvoas.iam.web.front.validator.GenericFormValidator;

@Controller
@RequestMapping("/admin-iam/role")
public class RoleController {
    protected static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private ServiceIamApplication serviceIamApplication;
    
    @Autowired
    private ServiceIamRole serviceIamRole;
    
    @Autowired
    private GenericFormValidator validator;
    
    /**
     * Liste les roles.
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @SecuredIam(vectorKey = IamKeys.IAM_ROLE_LISTER, vectorValue = IamKeys.VISIBLE)
    public ModelAndView findAll(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        request.getSession().setAttribute(Constants.UC_ACTIVE, "roleList");
        
        // preset application
        IamApplication iamApplication = null;
        String codeApplication = request.getParameter(Constants.APP_ID);
        if (StringUtils.isNotEmpty(codeApplication)) {
            iamApplication = serviceIamApplication.findByCode(codeApplication);
            request.getSession().setAttribute(Constants.SESSION_WORK_APPLICATION, iamApplication);
        } else {
            iamApplication = (IamApplication)request.getSession().getAttribute(Constants.SESSION_WORK_APPLICATION);
        }
        
        List<IamRole> iamRoles = null;
        if (iamApplication!=null) {
            iamRoles = serviceIamRole.findByApplication(iamApplication);
        } else {
            iamRoles = new ArrayList<IamRole>();
            model.put(Constants.FORM_ERRORS, "role.application.unset");
            
        }
        model.put(Constants.MODEL_APP, iamApplication);
        model.put(Constants.MODEL_ROLES, iamRoles);
        return new ModelAndView(Constants.VIEW_ROLE_LIST);
    }
    

    /**
     * Liste les roles.
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @SecuredIam(vectorKey = IamKeys.IAM_ROLE_SUPPRIMER, vectorValue = IamKeys.ACTION)
    public String saveDelete(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        request.getSession().setAttribute(Constants.UC_ACTIVE, "roleList");
        
        // preset application
        String codeRole = request.getParameter(Constants.ROLE_ID);
        if (StringUtils.isNotEmpty(codeRole)) {
            Integer id = Integer.parseInt(codeRole);
            serviceIamRole.delete(id);
        }
        return Constants.REDIRECT_PREFIX + "/admin-iam/role/list.do";
    }
    
    
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @SecuredIam(vectorKey = IamKeys.IAM_ROLE_CREER, vectorValue = IamKeys.VISIBLE)
    public ModelAndView getEdit(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        request.getSession().setAttribute(Constants.UC_ACTIVE, "role");
        
        IamApplication app = (IamApplication)request.getSession().getAttribute(Constants.SESSION_WORK_APPLICATION);
        
        String roleId= request.getParameter(Constants.ROLE_ID);
        if (StringUtils.isNotBlank(roleId)) {
            Integer idRole = Integer.valueOf(roleId);
            
            IamRole iamRole = serviceIamRole.findById(idRole);
            
            model.put(Constants.FORM_ROLE, this.map(iamRole, app));
                
            
        } else {
            model.put(Constants.FORM_ROLE, new RoleForm());
        }
        

        return new ModelAndView(Constants.VIEW_ROLE_EDIT);
    }
    
    /**
     * provides add role page.
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @SecuredIam(vectorKey = IamKeys.IAM_ROLE_CREER, vectorValue = IamKeys.ACTION)
    public ModelAndView getAdd(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        request.getSession().setAttribute(Constants.UC_ACTIVE, "roleAdd");
        
        IamApplication iamApp = (IamApplication)request.getSession().getAttribute(Constants.SESSION_WORK_APPLICATION);
        if (iamApp!=null) {
            model.put(Constants.MODEL_APP, iamApp);
        }
        RoleForm form = map(null, iamApp);
        model.put(Constants.FORM_ROLE,form);
        
        return new ModelAndView(Constants.VIEW_ROLE_ADD);
    }

    
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @SecuredIam(vectorKey = IamKeys.IAM_ROLE_CREER, vectorValue = IamKeys.ACTION)
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response, 
            ModelMap model, @ModelAttribute RoleForm form, BindingResult bindingResult) {
        

        // valider le formulaire
        validator.validate(form, bindingResult);
              
        if (!bindingResult.hasErrors()) {

            try {
                IamRole iamRole = null;
                IamApplication app = (IamApplication)request.getSession().getAttribute(Constants.SESSION_WORK_APPLICATION);
                if (app==null && form.getIdApp()!=null) {
                    app = serviceIamApplication.findById(Integer.parseInt(form.getIdApp()));
                    request.getSession().setAttribute(Constants.SESSION_WORK_APPLICATION, app);
                }
                
                if (StringUtils.isNotBlank(form.getId()) ) {
                    iamRole = serviceIamRole.findById(Integer.parseInt(form.getId()));
                }
                iamRole = map(iamRole, form);
                iamRole = serviceIamRole.save(iamRole, app);

                IamApplication iamApp = new IamApplication();
                iamApp.setId(Integer.parseInt(form.getIdApp()));
                form = map(iamRole, iamApp);
                
            } catch (IamException e) {
                LOGGER.error(IamException.BUSINESS_ERROR, e.getMessage());
                if (e.isBusinessRuleViolated()) {
                    bindingResult.reject(IamException.BUSINESS_ERROR, e.getCode());
                    model.put(Constants.FORM_ERRORS,bindingResult.getAllErrors());
                }
            }
        } else {
            model.put(Constants.FORM_ERRORS, bindingResult.getAllErrors());
        }
        
        model.put(Constants.FORM_APPLICATION, form);

        return new ModelAndView(Constants.VIEW_ROLE_EDIT);
    }
    
    
    
    
    /**
     * Map the BO to form.
     * @param iamRole
     * @param iamApp
     * @return
     */
    private RoleForm map(IamRole iamRole, IamApplication iamApp) {
        RoleForm form = new RoleForm();
        if (iamApp!=null) {
            form.setIdApp(String.valueOf(iamApp.getId()));
        }
        if (iamRole!=null) {
            form.setCode(iamRole.getCode());
            form.setCommentaire(iamRole.getCommentaire());
            form.setDescription(iamRole.getDescription());
            form.setId(String.valueOf(iamRole.getId()));
        }
        return form;
    }
    
    /**
     * Map the form to BO.
     * @param iamRole
     * @param form
     * @return
     */
    private IamRole map(IamRole iamRole, RoleForm form) {
        if (iamRole==null) {
            iamRole = new IamRole();
        }
        if (StringUtils.isNotBlank(form.getId())) {
            iamRole.setId(Integer.parseInt(form.getId()));
        }
        if (StringUtils.isNotBlank(form.getCode())) {
            iamRole.setCode(form.getCode().trim().toUpperCase());
        }
        
        iamRole.setDescription(form.getDescription());
        iamRole.setCommentaire(form.getCommentaire());
        return iamRole;
    }
    
    
}
