package com.github.cunvoas.iam.web.front.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.LocaleResolver;

import com.github.cunvoas.iam.security.authority.IamUserDetails;


/**
 * Classe permettant de factoriser les fonctionnalités de navigation.
 * @author Empeiria
 *
 */
public class AbstractController implements Constants {
    
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);
    
    private static final String REGEX_ID="[0-9]+";
    
    /**
     * Récupération des traduction dans les controleurs.
     */
    @Autowired
    @Qualifier("messageSource")
    protected MessageSource messageSource = null;
    
    /**
     * Resolver de variable de localisation i18n.
     */
//    @Autowired
//    @Qualifier("localeResolver")
    protected LocaleResolver localeResolver;
    
    private static final String REDIRECT_PREFIX = "redirect:";
    
    /**
     * donne controleur vers lequel il faut rediriger.
     * @param controleurName
     * @return
     */
    protected static final String redirect(String controleurName) {
        return REDIRECT_PREFIX + controleurName;
    }
    

    /**
     * Retourne l'utilisateur.
     * @return
     */
    protected IamUserDetails getUtilisateur() {
        IamUserDetails util = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null && authentication.getPrincipal() instanceof IamUserDetails) {
            util = (IamUserDetails) authentication.getPrincipal();
            
        }
        
        return util;
    }
    
    /**
     * Controle de validation des identifiants numeriques.
     * @param id
     * @return
     */
    protected boolean checkRequest(String id) {
        return id!=null && id.matches(REGEX_ID);
    }
    

    /**
     * Fourni le message du bundle.
     * @param key
     * @param args
     * @return
     */
    protected String getMessage(String key, Object[] args) {
        String message = null;
        Authentication auth =SecurityContextHolder.getContext().getAuthentication();
        IamUserDetails user = (IamUserDetails)auth.getPrincipal();
        
        Locale locale =Locale.forLanguageTag(user.getLangage());
        
        message = messageSource.getMessage(key, args, locale);
        return message;
    }
    
}
