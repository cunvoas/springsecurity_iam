package com.github.cunvoas.iam.web;

import com.github.cunvoas.iam.security.helper.IamHelper;

/**
 * Keys from IAM Security check.
 * Configuration from IAM application.
 * @author CUNVOAS
 */
public abstract class IamKeys {

    public static final String INVISIBLE = IamHelper.INVISIBLE;
    public static final String VISIBLE = IamHelper.VISIBLE;
    public static final String ACTION = IamHelper.ACTION;
    
    /**
     * Access keys to ACCUEIL items.
     */
    public static final String IAM_ACCUEIL = "IAM.ACCUEIL";

    /**
     * Access to APPLICATION items.
     */
    public static final String IAM_APPLICATION_LISTER = "IAM.APPLICATION.LISTER";
    public static final String IAM_APPLICATION_CREER = "IAM.APPLICATION.CREER";
    public static final String IAM_APPLICATION_MODIFIER = "IAM.APPLICATION.MODIFIER";
    public static final String IAM_APPLICATION_SUPPRIMER = "IAM.APPLICATION.SUPPRIMER";
    public static final String IAM_APPLICATION_UPLOAD = "IAM.APPLICATION.UPLOAD";

    /**
     * Access keys to ROLE items.
     */
    public static final String IAM_ROLE_LISTER = "IAM.ROLE.LISTER";
    public static final String IAM_ROLE_CREER = "IAM.ROLE.CREER";
    public static final String IAM_ROLE_MODIFIER = "IAM.ROLE.MODIFIER";
    public static final String IAM_ROLE_SUPPRIMER = "IAM.ROLE.SUPPRIMER";
    public static final String IAM_ROLE_AFFECTER = "IAM.ROLE.AFFECTER";

    /**
     * Access keys to RESSOURCE items.
     */
    public static final String IAM_RESSOURCE_LISTER = "IAM.RESSOURCE.LISTER";
    public static final String IAM_RESSOURCE_CREER = "IAM.RESSOURCE.CREER";
    public static final String IAM_RESSOURCE_MODIFIER = "IAM.RESSOURCE.MODIFIER";
    public static final String IAM_RESSOURCE_SUPPRIMER = "IAM.RESSOURCE.SUPPRIMER";
    public static final String IAM_RESSOURCE_AFFECTER_ROLE = "IAM.RESSOURCE.AFFECTER_ROLE";

    /**
     * Access keys to DELEGATION items.
     */
    public static final String IAM_DELEGATION_LISTER = "IAM.DELEGATION.LISTER";
    public static final String IAM_DELEGATION_CREER = "IAM.DELEGATION.CREER";
    public static final String IAM_DELEGATION_MODIFIER = "IAM.DELEGATION.MODIFIER";
}
