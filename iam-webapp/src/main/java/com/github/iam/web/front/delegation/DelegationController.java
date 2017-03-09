package com.github.cunvoas.iam.web.front.delegation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.bo.IamRole;
import com.github.cunvoas.iam.bo.delegation.IamDelegation;
import com.github.cunvoas.iam.bo.delegation.IamDelegationSearchCriteria;
import com.github.cunvoas.iam.bo.delegation.IamDelegationState;
import com.github.cunvoas.iam.exception.IamException;
import com.github.cunvoas.iam.ldap.IamLdapService;
import com.github.cunvoas.iam.ldap.IamLdapUser;
import com.github.cunvoas.iam.persistance.entity.delegation.Delegation;
import com.github.cunvoas.iam.persistance.entity.delegation.DelegationState;
import com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateHelper;
import com.github.cunvoas.iam.persistance.entity.delegation.state.DelegationStateTransition;
import com.github.cunvoas.iam.security.annotation.SecuredIam;
import com.github.cunvoas.iam.security.authority.IamUserDetails;
import com.github.cunvoas.iam.service.ServiceIamApplication;
import com.github.cunvoas.iam.service.ServiceIamDelegation;
import com.github.cunvoas.iam.web.IamKeys;
import com.github.cunvoas.iam.web.front.application.ApplicationForm;
import com.github.cunvoas.iam.web.front.util.Constants;
import com.github.cunvoas.iam.web.front.validator.GenericFormValidator;

/**
 * Controler for application screen.
 * @author CUNVOAS
 */
@Controller
@RequestMapping("/admin-iam/delegation")
public class DelegationController {
    protected static final Logger LOGGER = LoggerFactory.getLogger(DelegationController.class);

    @Autowired
    private ServiceIamApplication serviceIamApplication;
    
    @Autowired
    private ServiceIamDelegation serviceIamDelegation;

    @Autowired
    private GenericFormValidator validator;

    @Autowired
    private IamLdapService iamLdapService;
    

    /**
     * Liste les applications.
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @SecuredIam(vectorKey = IamKeys.IAM_DELEGATION_LISTER, vectorValue = IamKeys.VISIBLE)
    public ModelAndView findList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
    	
        try {
			request.getSession().setAttribute(Constants.UC_ACTIVE, "deleg");
			
			List<IamApplication> iamApplications = serviceIamApplication.findAll();
			model.put(Constants.MODEL_DELEGATION, iamApplications);
			
			
			IamUserDetails util = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication!=null && authentication.getPrincipal() instanceof IamUserDetails) {
			    util = (IamUserDetails) authentication.getPrincipal();
			    
			    IamDelegationSearchCriteria criteria=new IamDelegationSearchCriteria();
			    criteria.setAlive(Boolean.TRUE);
			    criteria.setSearchDate(new Date());
			    
			    criteria.setDelegatorUid(util.getUsername());
			    criteria.setDelegateUid(null);
			    List<IamDelegation> myDelegations = serviceIamDelegation.find(criteria);
			    model.put(Constants.MODEL_DELEGATIONS, map(myDelegations));
			    
			    criteria.setDelegatorUid(null);
			    criteria.setDelegateUid(util.getUsername());
			    List<IamDelegation> toMeDelegations = serviceIamDelegation.find(criteria);
			    model.put(Constants.MODEL_DELEGATIONS_AFFECTED, map(toMeDelegations));
			    
			}
		} catch (Exception e) {
			LOGGER.error("controler", e);
			throw e;
		}
        

        return new ModelAndView(Constants.VIEW_DELEGATION_LIST);
    }
    
    
    @RequestMapping(value = "/workflow", method = RequestMethod.GET)
    @SecuredIam(vectorKey = IamKeys.IAM_DELEGATION_MODIFIER, vectorValue = IamKeys.VISIBLE)
    public ModelAndView getWkfValid(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        request.getSession().setAttribute(Constants.UC_ACTIVE, "deleg");
        
        IamUserDetails util = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null && authentication.getPrincipal() instanceof IamUserDetails) {
            util = (IamUserDetails) authentication.getPrincipal();
            
	        String strId =  request.getParameter("dlgId");
	        if (StringUtils.isNumeric(strId)) {
	        	IamDelegation iamdeleg = serviceIamDelegation.findById(Integer.valueOf(strId));
	        	
	        	if (DelegationStateHelper.getState(DelegationState.ID_DEMAND).equals(iamdeleg.getState())) {
	        		serviceIamDelegation.read(iamdeleg);
	        		iamdeleg = serviceIamDelegation.findById(Integer.valueOf(strId));
	        	}
	        	
	        	model.put(Constants.FORM_DELEGATION, map(iamdeleg));
	        	
	        	Delegation deleg = serviceIamDelegation.findPersistedById(Integer.valueOf(strId));
	        	
	        	
	        	DelegationStateDto dto = this.map(deleg, util);
	        	model.put(Constants.MODEL_DELEGATION_STATE, dto);
	        }
        }
        
        return new ModelAndView(Constants.VIEW_DELEGATION_WKF);
    }
    
	/**
   	 * Save the delegation.
	 * @param request
	 * @param response
	 * @param model
	 * @param form
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/workflow", method = RequestMethod.POST)
	@SecuredIam(vectorKey = IamKeys.IAM_DELEGATION_MODIFIER, vectorValue = IamKeys.ACTION)
	public ModelAndView saveWkfValid(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,
			@ModelAttribute DelegationForm form, BindingResult bindingResult) {

		try {
			// valider le formulaire
			//FIXME validator.validate(form, bindingResult);
			if (true || !bindingResult.hasErrors()) {
				try {
					String status = request.getParameter("BTN_ACT");
					
					IamDelegation delegation = serviceIamDelegation.findById(form.getId());
					DelegationState newState = DelegationStateHelper.getState(status);

					// change the state
					if (DelegationStateHelper.getState(DelegationState.ID_ACCEPTED).equals(newState)) {
						serviceIamDelegation.accept(delegation);

					} else if (DelegationStateHelper.getState(DelegationState.ID_REFUSED).equals(newState)) {
						serviceIamDelegation.refuse(delegation);

					} else if (DelegationStateHelper.getState(DelegationState.ID_CANCELED).equals(newState)) {
						serviceIamDelegation.cancel(delegation);
					}
					
					form = map(serviceIamDelegation.findById(form.getId()));
					
				} catch (IamException e) {
					LOGGER.error(IamException.BUSINESS_ERROR, e.getMessage());
					if (e.isBusinessRuleViolated()) {
						bindingResult.reject(IamException.BUSINESS_ERROR, e.getCode());
						model.put(Constants.FORM_ERRORS, bindingResult.getAllErrors());
					}
				}

			} else {
				model.put(Constants.FORM_ERRORS, bindingResult.getAllErrors());
			}
			
			model.put(Constants.MODEL_DELEGATION, form);
			
			IamUserDetails util = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication!=null && authentication.getPrincipal() instanceof IamUserDetails) {
			    util = (IamUserDetails) authentication.getPrincipal();
			}
			Delegation deleg = serviceIamDelegation.findPersistedById(form.getId());
			DelegationStateDto dto = this.map(deleg, util);
			model.put(Constants.MODEL_DELEGATION_STATE, dto);
			
		} catch (Exception e) {
			LOGGER.error("controler", e);
			throw e;
		}

		return new ModelAndView(Constants.VIEW_DELEGATION_WKF);
	}
    
    /**
     * @param bos
     * @return
     */
    private List<DelegationDto> map(List<IamDelegation> bos) {
    	List<DelegationDto> dtos = new ArrayList<DelegationDto>();
    	if (bos!=null ) {
    		Map<String, String> usersMap = new HashMap<String, String>();
    		for (IamDelegation delegation : bos) {
    			if (delegation.getRoles()!=null ) {
	    			for (IamRole role : delegation.getRoles()) {
	        			DelegationDto dto = new DelegationDto();
	        			
	        			dto.setId(delegation.getId());
	        			dto.setAppCode(delegation.getApplication().getCode());
	        			dto.setAppName(delegation.getApplication().getDescriptionShort());
	        			dto.setRoleCode(role.getCode());
	        			dto.setRoleName(role.getDescriptionShort());
	
	        			dto.setStartDate(delegation.getBegin());
	        			dto.setEndDate(delegation.getEnd());
	        			dto.setStatus(delegation.getState().getName());
	        			
	        			dto.setDelegate(getUserName(delegation.getUidDelegate(), usersMap));
	        			dto.setDelegator(getUserName(delegation.getUidDelegator(), usersMap));
	        			
	        			dtos.add(dto);
					}
    			}
			}
    	}
    	
    	return dtos;
    }
    
    private String getUserName(String userUid, Map<String, String> usersMap) {
    	String username = null;
    	
    	if (userUid!=null) {
        	if (usersMap.containsKey(userUid)) {
        		username = usersMap.get(userUid);
        	} else {
        		IamLdapUser ldapUser = iamLdapService.find(userUid);
        		username = ldapUser.getFullName();
        		usersMap.put(userUid, username);
        	}
    	}
    	
    	return username;
    }
    

    /**
     * Liste les applications.
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @SecuredIam(vectorKey = IamKeys.IAM_DELEGATION_CREER, vectorValue = IamKeys.ACTION)
    public ModelAndView getAdd(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        request.getSession().setAttribute(Constants.UC_ACTIVE, "delegationAdd");
        model.put(Constants.FORM_DELEGATION, new DelegationForm());
        
        
        return new ModelAndView(Constants.VIEW_DELEGATION_ADD);
    }

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@SecuredIam(vectorKey = IamKeys.IAM_DELEGATION_MODIFIER, vectorValue = IamKeys.ACTION)
	public ModelAndView saveAdd(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,
			@ModelAttribute DelegationForm form, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			try {
				// A TESTER
				IamDelegation iamDeleg = map(form);
				serviceIamDelegation.save(iamDeleg);
				
			} catch (IamException e) {
				LOGGER.error(IamException.BUSINESS_ERROR, e.getMessage());
				if (e.isBusinessRuleViolated()) {
					bindingResult.reject(IamException.BUSINESS_ERROR, e.getCode());
					model.put(Constants.FORM_ERRORS, bindingResult.getAllErrors());
				}
			}
		} else {
			model.put(Constants.FORM_ERRORS, bindingResult.getAllErrors());
		}

		return new ModelAndView(Constants.VIEW_DELEGATION_ADD);
	}
    
    /**
     * @param form
     * @return
     */
    private IamDelegation map(DelegationForm form) {
    	IamDelegation dto = new IamDelegation();
    	dto.setId(form.getId());
    	IamApplication application = new IamApplication();
    	application.setId(form.getIdApplication());
    	dto.setApplication(application);
    	
    	dto.setBegin(form.getDebut());
    	dto.setEnd(form.getFin());
    	dto.setUidDelegator(form.getDelegator());
    	dto.setUidDelegate(form.getDelegate());
    	
    	dto.setState(new IamDelegationState(DelegationStateHelper.getState(form.getStatusId())));
    	
    	//roles TODO
    	
    	
    	return dto;
    }
    
    
    /**
     * @param dto
     * @return
     */
    private DelegationForm map(IamDelegation dto) {
    	DelegationForm form = new DelegationForm();
    	form.setId(dto.getId());
    	form.setIdApplication(dto.getApplication().getId());
    	form.setApplicationLabel(dto.getApplication().getDescription());
    	
    	form.setDebut(dto.getBegin());
    	form.setFin(dto.getEnd());
    	
    	form.setDelegator(dto.getUidDelegator());
    	if (StringUtils.isNotBlank(dto.getUidDelegator())) {
    		IamLdapUser lapUser = iamLdapService.find(dto.getUidDelegator());
    		if (lapUser!=null) {
    			form.setDelegatorLabel(lapUser.getFullName());
    		}
    	}
    	
    	form.setDelegate(dto.getUidDelegate());
    	if (StringUtils.isNotBlank(dto.getUidDelegate())) {
    		IamLdapUser lapUser = iamLdapService.find(dto.getUidDelegate());
    		if (lapUser!=null) {
    			form.setDelegateLabel(lapUser.getFullName());
    		}
    	}
    	
    	form.setStatusId(dto.getState().getId());
    	form.setStatusLabel(dto.getState().getName());
    	
    	//roles
    	form.setRoles(new ArrayList<DelegationRoleForm>());
    	for (IamRole iamRole : dto.getRoles()) {
    		DelegationRoleForm rf = new DelegationRoleForm();
    		rf.setId(iamRole.getId());
    		rf.setCode(iamRole.getCode());
    		rf.setLibelle(iamRole.getDescription());
		}
    	return form;
    }
    
    public DelegationStateDto map (Delegation delegation, IamUserDetails activeUser) {
    	DelegationStateDto dto = new DelegationStateDto();
    	
    	boolean isDelegator = activeUser.getUsername().equals(delegation.getDelegateur());
    	DelegationState bo = delegation.getStatus();
    	if (bo instanceof DelegationStateTransition) {
    		DelegationStateTransition transition = (DelegationStateTransition)bo;
    		// evict LazyInitializationEx
    		//String disc = transition.getDiscriminator();
    		if (isDelegator) {
    			dto.setChangeToCancel(transition.couldBeCancelable());
    		} else {
	    		dto.setChangeToAccepted(transition.couldBeAcceptable());
	    		dto.setChangeToRefused(transition.couldBeRefusable());
    		}
    	}
    	return dto;
    }
  
    /**
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Integer.class,null,new CustomNumberEditor(Integer.class,null,true));
        binder.registerCustomEditor(Long.class,null,new CustomNumberEditor(Long.class,null,true));
        binder.registerCustomEditor(Date.class,null,new CustomDateEditor(dateFormat,true));
    }
    
}
