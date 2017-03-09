package com.github.cunvoas.iam.web.front.json;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.bo.IamRole;
import com.github.cunvoas.iam.exception.IamException;
import com.github.cunvoas.iam.ldap.IamLdapService;
import com.github.cunvoas.iam.ldap.IamLdapUser;
import com.github.cunvoas.iam.security.annotation.SecuredIam;
import com.github.cunvoas.iam.service.ServiceIamApplication;
import com.github.cunvoas.iam.service.ServiceIamRessource;
import com.github.cunvoas.iam.service.ServiceIamRole;
import com.github.cunvoas.iam.web.IamKeys;

/**
 * Controler for JSon REST services.
 * @author C.UNVOAS
 * 
 * in case of HTTP 406 (Not acceptable), jackson lib is not on classpath.
 */
@Controller
@RequestMapping(value = "/admin-iam/json")
public class JSonController {
    protected static final Logger LOGGER = LoggerFactory.getLogger(JSonController.class);

    @Autowired
    private ServiceIamApplication serviceIamApplication;
    @Autowired
    private ServiceIamRole serviceIamRole;
    @Autowired
    private ServiceIamRessource serviceIamRessource;
    @Autowired
    private IamLdapService iamLdapService;
    
    @Autowired
    @Qualifier("messageSource")
    protected MessageSource messageSource;
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value=HttpStatus.PRECONDITION_FAILED, reason="Business Rule")
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        ResponseEntity<String> msg = null;
        
        HttpStatus status = null;
        String code=null;
        String message = null;
        
        if (exception instanceof IamException) {
            status = HttpStatus.PRECONDITION_FAILED;
            IamException ex = (IamException)exception;
            code=ex.getCode();
            Locale locale = Locale.getDefault();
            try {
                message = messageSource.getMessage(ex.getCode(), ex.getDetails(), locale);
            } catch (NoSuchMessageException e) {
                LOGGER.debug("can't read property file", e);
            }
        } else {
            status = HttpStatus.SERVICE_UNAVAILABLE;
            code="ERROR_OCCURS";
            message=exception.getMessage();
        }
        
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("code", code);
        map.add("message", message);
        
        msg = new ResponseEntity<String>(map, status);
        
        return msg;
    }
    
    @RequestMapping(value = "/application/find", method = RequestMethod.GET, headers = "Accept=application/json")
    @SecuredIam(vectorKey = IamKeys.IAM_APPLICATION_LISTER, vectorValue = IamKeys.VISIBLE)
    public @ResponseBody 
        List<IamApplication> findApplication(HttpServletRequest request, 
            @RequestParam("search") String searchString
            ) {
        List<IamApplication>  ret = serviceIamApplication.search(searchString);
        return ret;
    }
    
    @RequestMapping(value = "/role/find", method = RequestMethod.GET, headers = "Accept=application/json")
    @SecuredIam(vectorKey = IamKeys.IAM_ROLE_LISTER, vectorValue = IamKeys.VISIBLE)
    public @ResponseBody 
        List<IamRole> findRole(HttpServletRequest request, 
            @RequestParam("search") String searchString,
            @RequestParam("appCode") String appCode
            ) {
        List<IamRole>  ret = serviceIamRole.search(searchString, appCode);
        return ret;
    }
    
    @RequestMapping(value = "/delegation/findUser", method = RequestMethod.GET, headers = "Accept=application/json")
    @SecuredIam(vectorKey = IamKeys.IAM_DELEGATION_CREER, vectorValue = IamKeys.VISIBLE)
    public @ResponseBody 
        List<IamLdapUser> findRole(HttpServletRequest request, 
            @RequestParam("fistname") String fistname,
            @RequestParam("lastname") String lastname,
            @RequestParam("appCode") String appCode
            ) {
    	
        List<IamLdapUser>  ret = iamLdapService.find(fistname, lastname, appCode);
        return ret;
    }
    
    

}
