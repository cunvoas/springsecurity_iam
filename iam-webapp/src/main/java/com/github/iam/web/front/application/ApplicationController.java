package com.github.cunvoas.iam.web.front.application;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.exception.IamException;
import com.github.cunvoas.iam.security.annotation.SecuredIam;
import com.github.cunvoas.iam.security.authority.IamUserDetails;
import com.github.cunvoas.iam.service.ServiceIamApplication;
import com.github.cunvoas.iam.web.IamKeys;
import com.github.cunvoas.iam.web.front.util.Constants;
import com.github.cunvoas.iam.web.front.validator.GenericFormValidator;
import com.github.cunvoas.iam.web.front.validator.UploadFormValidator;
import com.github.cunvoas.iam.web.service.ServiceExcelInOut;

/**
 * Controler for application screen.
 * @author CUNVOAS
 */
@Controller
@RequestMapping("/admin-iam/application")
public class ApplicationController {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);

    @Value("${upload.path:/tmp}")
    private String uploadPath;
    
    @Autowired
    private ServiceIamApplication serviceIamApplication;
    
    @Autowired
    private ServiceExcelInOut serviceExcelInOut;
    
    @Autowired
    private GenericFormValidator validator;
    
    @Autowired
    private UploadFormValidator validatorUpload;


    /**
     * Liste les applications.
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @SecuredIam(vectorKey = IamKeys.IAM_APPLICATION_LISTER, vectorValue = IamKeys.VISIBLE)
    public ModelAndView findAll(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        request.getSession().setAttribute(Constants.UC_ACTIVE, "app");
        
        List<IamApplication> iamApplications = serviceIamApplication.findAll();
        model.put(Constants.MODEL_APPS, iamApplications);

        return new ModelAndView(Constants.VIEW_APPLICATION_LIST);
    }
    

    /**
     * Liste les applications.
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @SecuredIam(vectorKey = IamKeys.IAM_APPLICATION_CREER, vectorValue = IamKeys.ACTION)
    public ModelAndView getAdd(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        request.getSession().setAttribute(Constants.UC_ACTIVE, "applicationAdd");
        model.put(Constants.FORM_APPLICATION, new ApplicationForm());
        return new ModelAndView(Constants.VIEW_APPLICATION_ADD);
    }
    
    /**
     * Liste les applications.
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @SecuredIam(vectorKey = IamKeys.IAM_APPLICATION_CREER, vectorValue = IamKeys.VISIBLE)
    public ModelAndView getEdit(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        request.getSession().setAttribute(Constants.UC_ACTIVE, "application");
        
        String codeApplication = request.getParameter(Constants.APP_ID);
        if (StringUtils.isNotEmpty(codeApplication)) {
            IamApplication iamApplication = serviceIamApplication.findByCode(codeApplication);
            model.put(Constants.FORM_APPLICATION, this.map(iamApplication));
        } else {
            model.put(Constants.FORM_APPLICATION, new ApplicationForm());
        }
        
        return new ModelAndView(Constants.VIEW_APPLICATION_EDIT);
    }
    
    
    /**
     * Supprime une application.
     * @param request
     * @param response
     * @param model
     * @return
     * @see https://www.owasp.org/index.php/Captchas_in_Java
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @SecuredIam(vectorKey = IamKeys.IAM_APPLICATION_SUPPRIMER, vectorValue = IamKeys.ACTION)
    public String delete(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        
        String codeApplication = request.getParameter(Constants.APP_ID);
        if (StringUtils.isNotEmpty(codeApplication)) {
            
            serviceIamApplication.delete(codeApplication);
            
        } else {
            model.put(Constants.FORM_APPLICATION, new ApplicationForm());
        }
        
        return Constants.REDIRECT_PREFIX+"/admin-iam/application/list.do";
    }
    
    

    /**
     * Sauve l'application.
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @SecuredIam(vectorKey = IamKeys.IAM_APPLICATION_CREER, vectorValue = IamKeys.ACTION)
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response, 
            ModelMap model, @ModelAttribute ApplicationForm form, BindingResult bindingResult) {
        
        // valider le formulaire
        validator.validate(form, bindingResult);
        
        if (!bindingResult.hasErrors()) {
            try {
                IamApplication iamApplication = null;
                if (form.getId()!=null) {
                    iamApplication = serviceIamApplication.findById(form.getId());
                }
                iamApplication = map(iamApplication, form);
                iamApplication = serviceIamApplication.save(iamApplication);
                form= map(iamApplication);
            } catch (IamException e) {
                LOGGER.error(IamException.BUSINESS_ERROR, e.getMessage());
                if (e.isBusinessRuleViolated()) {
                    bindingResult.reject(IamException.BUSINESS_ERROR, e.getCode());
                    model.put(Constants.FORM_ERRORS,bindingResult.getAllErrors());
                }
            }
        } else {
             model.put(Constants.FORM_ERRORS,bindingResult.getAllErrors());
        }
        
        model.put(Constants.FORM_APPLICATION, form);

        return new ModelAndView(Constants.VIEW_APPLICATION_EDIT);
    }
    
    @RequestMapping(value = "dwdXlsFile", method = RequestMethod.GET)
    @SecuredIam(vectorKey = IamKeys.IAM_APPLICATION_UPLOAD, vectorValue = IamKeys.ACTION)
    public ModelAndView getXlsFile(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String codeApplication = request.getParameter(Constants.APP_ID);
        
        if (StringUtils.isNotEmpty(codeApplication)) {
            model.put(Constants.APP_ID, codeApplication);
            IamApplication iamApplication = serviceIamApplication.findForExport(codeApplication);
            model.put(Constants.MODEL_APP, iamApplication);
                
        }

        // JXL View
        return new ModelAndView(Constants.VIEW_APPLICATION_DWD_XLS);
    }
    
    @RequestMapping(value = "upload", method = RequestMethod.GET)
    @SecuredIam(vectorKey = IamKeys.IAM_APPLICATION_UPLOAD, vectorValue = IamKeys.VISIBLE)
    public ModelAndView getUpload(HttpServletRequest request, HttpServletResponse response, 
            ModelMap model, @ModelAttribute ApplicationUploadForm form, BindingResult bindingResult) {
        
        if (form==null) {
            form = new ApplicationUploadForm();
        }
        model.put(Constants.FORM_APPLICATION_UPLOAD, form);
        
        return new ModelAndView(Constants.VIEW_APPLICATION_UPLOAD);
    }
    
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @SecuredIam(vectorKey = IamKeys.IAM_APPLICATION_UPLOAD, vectorValue = IamKeys.ACTION)
    public ModelAndView saveUpload(HttpServletRequest request, HttpServletResponse response, 
            ModelMap model, @ModelAttribute ApplicationUploadForm form, BindingResult bindingResult) {
        
        validatorUpload.validate(form, bindingResult);
        
        if (!bindingResult.hasErrors()) {
            MultipartFile file = form.getFile();
            
            try {
                IamApplication iamApplication = serviceExcelInOut.parse(file.getInputStream());
                
                serviceIamApplication.saveUpload(iamApplication);
                
            } catch (IamException e) {
                LOGGER.error(IamException.BUSINESS_ERROR, e.getMessage());
                if (e.isBusinessRuleViolated()) {
                    bindingResult.reject(IamException.BUSINESS_ERROR, e.getCode());
                    model.put(Constants.FORM_ERRORS,bindingResult.getAllErrors());
                }
                
            } catch (Exception e) {
                Authentication auth =SecurityContextHolder.getContext().getAuthentication();
                if (auth!=null && auth.getPrincipal() instanceof IamUserDetails) {
                    IamUserDetails userDetail = (IamUserDetails)  auth.getPrincipal();
                    LOGGER.error("The user {} publish bad XLS document", userDetail.getUsername());
                }
                LOGGER.error("XLS UPLOAD", e);
                
                // send error to the page
                bindingResult.reject(Constants.FORM_ERRORS, "valid.application.upload.error");
                
                model.put(Constants.FORM_ERRORS,bindingResult.getAllErrors());
            }
            
        } else {
            model.put(Constants.FORM_ERRORS,bindingResult.getAllErrors());
            
        }
        
        if (form==null) {
            form = new ApplicationUploadForm();
        }
        model.put(Constants.FORM_APPLICATION_UPLOAD, form);
        
        return new ModelAndView(Constants.VIEW_APPLICATION_UPLOAD);
    }
    
    
    
    

    

    /**
     * Map Form from BO.
     * @param iamApp
     * @return
     */
    private ApplicationForm map(IamApplication iamApp) {
        ApplicationForm ret = new ApplicationForm();
        if (iamApp!=null) {
            ret.setId(iamApp.getId());
            ret.setCode(iamApp.getCode());
            ret.setDescription(iamApp.getDescription());
            ret.setUrl(iamApp.getUrl());
        }
        return ret;
    }
    
    /**
     *  Map BO from Form.
     *  @param iamApp
     * @param form
     * @return
     */
    private IamApplication map(IamApplication iamApp, ApplicationForm form) {
        if (iamApp==null) {
            iamApp = new IamApplication();
        }
        iamApp.setId(form.getId());
        iamApp.setCode(form.getCode());
        iamApp.setDescription(form.getDescription());
        iamApp.setUrl(form.getUrl());
        return iamApp;
    }
}
