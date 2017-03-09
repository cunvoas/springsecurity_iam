package com.github.cunvoas.iam.web.front.home;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cunvoas.iam.bo.IamDashboard;
import com.github.cunvoas.iam.bo.IamDashboardItem;
import com.github.cunvoas.iam.security.annotation.SecuredIam;
import com.github.cunvoas.iam.security.authority.IamGrantedAuthority;
import com.github.cunvoas.iam.security.authority.IamUserDetails;
import com.github.cunvoas.iam.service.DashBoardMonitor;
import com.github.cunvoas.iam.service.ServiceIamApplication;
import com.github.cunvoas.iam.web.IamKeys;
import com.github.cunvoas.iam.web.front.home.chart.JsChartData;
import com.github.cunvoas.iam.web.front.home.chart.JsChartWrapper;
import com.github.cunvoas.iam.web.front.util.Constants;

/**
 * Contorler for home page and basic ops.
 * @author CUNVOAS
 */
@Controller
@RequestMapping("/admin-iam")
public class HomeController {
    
    protected static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    
    @Value("${default.locale}")
    private String defaultLocale;
    
    @Autowired
    @Qualifier("appVersion")
    private String appVersion;
    
    /**
     * Resolver de variable de localisation i18n.
     */
    @Autowired
    @Qualifier("localeResolver")
    protected LocaleResolver localeResolver;
    
    @Autowired
    private ServiceIamApplication serviceIamApplication;
    
    @Autowired
    private DashBoardMonitor dashBoardMonitor;

    /**
     * Home page.
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/home", method = RequestMethod.GET)
    @SecuredIam(vectorKey=IamKeys.IAM_ACCUEIL, vectorValue=IamKeys.VISIBLE)
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        request.getSession().setAttribute(Constants.SESSION_APP_VERSION, appVersion);
        request.getSession().setAttribute(Constants.UC_ACTIVE, "home");
        
        IamDashboard dashboard= serviceIamApplication.getIamDashboard();
        model.put(Constants.MODEL_DASHBOARD, dashboard);
        
        try {
            List<IamDashboardItem> items = dashBoardMonitor.getActivityByHour();
            List<JsChartData> jsChartData = JsChartWrapper.wrap(items);
            ObjectMapper jsonMapper = new ObjectMapper();
            model.put(Constants.MODEL_DASHBOARD_CHART,  jsonMapper.writeValueAsString(jsChartData));
            
        } catch (JsonProcessingException e) {
            LOGGER.error("SERIALIZE_JSON", e);
            model.put("errors", "json");
        }
        
        checkLocale(request, response);
        
        return new ModelAndView(Constants.VIEW_HOME);
    }
    
    /**
     * Change the current role.
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/changeRole", method = RequestMethod.GET)
    @SecuredIam(vectorKey=IamKeys.IAM_ACCUEIL, vectorValue=IamKeys.VISIBLE)
    public ModelAndView changeRole(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        
        // positionnement de la locale par défault
        Authentication auth =SecurityContextHolder.getContext().getAuthentication();
        if (auth!=null && auth.getPrincipal() instanceof IamUserDetails) {
            IamUserDetails userDetail = (IamUserDetails)  auth.getPrincipal();

            String roleId = request.getParameter("role");
            
            if (!StringUtils.isEmpty(roleId)) {
                for (IamGrantedAuthority autority : userDetail.getAuthorities()) {
                    if (roleId.equals(autority.getRole().getCode())) {
                        userDetail.setCurrentRole(autority);
                        break;
                    }
                    
                }
            
            }
        }
        
        return new ModelAndView(Constants.VIEW_HOME);
    }
    
    /**
     * Change the current locale.
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/changeLanguage", method = RequestMethod.GET)
    @SecuredIam(vectorKey=IamKeys.IAM_ACCUEIL, vectorValue=IamKeys.VISIBLE)
    public ModelAndView changeLanguage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        
        checkLocale(request, response);
        
        return new ModelAndView(Constants.VIEW_HOME);
    }


    /**
     * Positionnement de la local IHM.
     * @param request
     * @param response
     */
    private void checkLocale(HttpServletRequest request, HttpServletResponse response) {
        String lang = "fr";
                
        // positionnement de la locale par défault
        Authentication auth =SecurityContextHolder.getContext().getAuthentication();
        if (auth!=null && auth.getPrincipal() instanceof IamUserDetails) {
            IamUserDetails userDetail = (IamUserDetails)  auth.getPrincipal();
            
            //userDetail.currentRole
            //userDetail.authorities
            lang = userDetail.getLangage();
            
            if (lang==null) {
                if ( userDetail.getLangages()==null) {
                    lang = defaultLocale;
                    userDetail.setLangages(new ArrayList<String>());
                } else if (userDetail.getLangages().isEmpty() ) {
                    lang = defaultLocale;
                } else {
                    lang = userDetail.getLangages().get(0);
                }
            } 
            
            List<String> sessionLangues = new ArrayList<String>();
            sessionLangues.add(lang);
            for (String lng : userDetail.getLangages()) {
                if (!sessionLangues.contains(lng)) {
                    sessionLangues.add(lng);
                }
            }

            request.getSession().setAttribute(Constants.SESSION_LOCALES, sessionLangues);
            
            String lng = request.getParameter("locale");
            String currentLocale=lang;
            if (lng!=null) {
                // changement de langue demandée
                LOGGER.info("change locale="+lng);
                
                boolean found=false;
                if (sessionLangues!=null) {
                    for (String langue : sessionLangues) {
                        if (lng.equals(langue)) {
                            found=true;
                            currentLocale = langue;
                            break;
                        }
                    }
                }
                if (!found) {
                    currentLocale = lang;
                }
                
                
            }
            request.getSession().setAttribute(Constants.SESSION_LOCALE, currentLocale);
            
            try {
                Locale locale = getLocale(currentLocale);
                
                // contorle de validé de lu code de Locale
                Locale[] tLocs = DateFormat.getAvailableLocales();
                List<Locale> lLocs = Arrays.asList(tLocs);
                if (!lLocs.contains(locale)) {
                    LOGGER.error("locale unknown "+lng);
                }
                
                localeResolver.setLocale(request, response, locale);
                request.setAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE, localeResolver);
                
            } catch (Exception ignore) {
                LOGGER.info(ignore.getMessage());
            }
        
        } else {
            request.getSession().setAttribute(Constants.SESSION_LOCALE, lang);
            request.getSession().removeAttribute(Constants.SESSION_LOCALES);
        }
        
    }
    
    /**
     * retourne une Locale.
     * @param currentLocale
     * @return
     */
    private Locale getLocale(String nomLocale) {
        Locale locale = null;
        if (nomLocale!=null) {
            String split="_";
            if (nomLocale.indexOf(split)>0) {
                String[] tlng = nomLocale.split(split);
                locale = new Locale(tlng[0], tlng[1]);
            } else {
                locale = new Locale(nomLocale);
            }
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

}
