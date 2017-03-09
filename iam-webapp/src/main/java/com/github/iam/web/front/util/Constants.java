package com.github.cunvoas.iam.web.front.util;

/**
 * MVC Constants
 * @author CUNVOAS
 */
public interface Constants {
    
    int FALSE_ID = -10000;
    
    
    String FORM_ERRORS = "errors";
    String REDIRECT_PREFIX = "redirect:";
    
    String SESSION_APP_VERSION="SESSION_APP_VERSION";
    String SESSION_LOCALES="SESSION_LOCALES";
    String SESSION_LOCALE="SESSION_LOCALE";
    
    String SESSION_WORK_APPLICATION="SESSION_APPLICATION";
    
    String UC_ACTIVE="UC_ACTIVE";
    
    // error page
    String VIEW_ERROR="error";
    String VIEW_BLANK="blank";
    
    // home page
    String VIEW_HOME="home";
    
    
    // application creation
    String VIEW_APPLICATION_LIST="applicationList";
    String VIEW_APPLICATION_ADD="applicationAdd";
    String VIEW_APPLICATION_EDIT="applicationEdit";
    String VIEW_APPLICATION_UPLOAD="applicationUpload";
    String VIEW_APPLICATION_DWD_XLS="exportIamExcelView";
    String FORM_APPLICATION="applicationForm";
    String FORM_APPLICATION_UPLOAD="applicationFormUpload";
        
    String VIEW_RESOURCE_TREE="resourceTree";
    String VIEW_RESOURCE_VALUE="resourceValue";
    String FORM_RESOURCE_VALUE_SEARCH="SearchResValForm";
    
    
    String APP_ID = "appId";
    String ROLE_ID = "roleId";
    String MODEL_DASHBOARD = "dashboard";
    String MODEL_DASHBOARD_CHART = "dashboardChart";
    String MODEL_APP = "application";
    String MODEL_APPS = "applications";
    String MODEL_RESS = "resource";
    String MODEL_RES_VAL_REF = "resValRef";
    String MODEL_RESS_JSTREE = "resourceTree";


    // role creation
    String MODEL_ROLE = "role";
    String MODEL_ROLES = "roles";
    String FORM_ROLE="roleForm";
    String VIEW_ROLE_ADD="roleAdd";
    String VIEW_ROLE_EDIT="roleEdit";
    String VIEW_ROLE_LIST = "roleList";
    
    // DELEGATION
    String MODEL_DELEGATION = "delegation";
    String MODEL_DELEGATION_STATE = "delegationState";
    String MODEL_DELEGATIONS = "delegations";
    String MODEL_DELEGATIONS_AFFECTED = "delegationsAffected";
    String FORM_DELEGATION="delegationForm";
    String VIEW_DELEGATION_ADD="delegationAdd";
    String VIEW_DELEGATION_EDIT="delegationEdit";
    String VIEW_DELEGATION_LIST = "delegationList";
    String VIEW_DELEGATION_WKF = "delegationWkf";
    
    
}
