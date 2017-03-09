package com.github.cunvoas.front;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.method.GlobalMethodSecurityBeanDefinitionParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.cunvoas.iam.security.helper.IamHelper;
import com.github.cunvoas.iam.security.helper.IamSecurityHelper;
import com.github.cunvoas.iam.security.annotation.SecuredIam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
    
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    // Mauvaise partique car nons Thead safe mais uniquement dans le but de simuller une sauvegarde pour le POC
    private Date datePersistedDate = new Date();
    
    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        
        logger.info("Welcome home! The client locale is {}.", locale);
        
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        
        String formattedDate = dateFormat.format(date);
        model.addAttribute("serverTime", formattedDate );
        
        String formattedDateSave = dateFormat.format(datePersistedDate);
        model.addAttribute("persistedTime", formattedDateSave );

        model.addAttribute("securized", false );
        return "home";
    }
    
    @SecuredIam(vectorKey="IAM.APPLICATION.LISTER", vectorValue=IamHelper.VISIBLE)
    @RequestMapping(value = "/secured/", method = RequestMethod.GET)
    public String secure(Locale locale, Model model) {
        logger.info("Welcome secure! The client locale is {}.", locale);
        
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        
        String formattedDate = dateFormat.format(date);
        model.addAttribute("serverTime", formattedDate );
            
        model.addAttribute("securized", true );
        
        return "secure";
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String save(Locale locale, Model model) {
        logger.info("Welcome home! The client locale is {}.", locale);
        
        Date date = new Date();
        // Simule la sauvegarde
        this.datePersistedDate = date;
        
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        
        String formattedDate = dateFormat.format(date);
        model.addAttribute("serverTime", formattedDate );
        
        String formattedDateSave = dateFormat.format(datePersistedDate);
        model.addAttribute("persistedTime", formattedDateSave );
        
        return "home";
    }
    
    
    
}
