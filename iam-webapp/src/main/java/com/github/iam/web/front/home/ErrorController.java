package com.github.cunvoas.iam.web.front.home;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.cunvoas.iam.web.front.util.Constants;

@Controller
@RequestMapping("/admin-iam/errors")
public class ErrorController {
    
    private static final String STATUS_MESSAGE_KEY = "statusMessageKey";
    private static final String TYPE_EXCEPTION = "typeException";
    private static final String TRACE_EXCEPTION = "traceException";

    private boolean showStackTrace = false;

    /**
     * retourne vers la page d'erreur (403).
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/403")
    public ModelAndView routToErrorHandler403(ModelMap model,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        String viewName = Constants.VIEW_ERROR;
        // display a stylized page only for entroler entry.
        if (request.getRequestURI().indexOf(".do") > 0) {

            model.put(STATUS_MESSAGE_KEY, "accueil.erreur.message");
            model.put(TYPE_EXCEPTION, "accueil.erreur.type403");

            model.put(TRACE_EXCEPTION, "");

        } else {
            viewName = Constants.VIEW_BLANK;
        }

        return new ModelAndView(viewName);
    }

    /**
     * retourne vers la page d'erreur (404).
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/404")
    public ModelAndView routToErrorHandler404(ModelMap model,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        String viewName = Constants.VIEW_ERROR;
        // display a stylized page only for entroler entry.
        if (request.getRequestURI().indexOf(".do") > 0) {

            model.put(STATUS_MESSAGE_KEY, "accueil.erreur.message");

            model.put(TYPE_EXCEPTION, "accueil.erreur.type404");

            model.put(TRACE_EXCEPTION, "");
        } else {
            viewName = Constants.VIEW_BLANK;

        }

        return new ModelAndView(viewName);

    }

    /**
     * retourne vers la page d'erreur (500).
     * 
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(Exception.class)
    @RequestMapping(value = "/500")
    public ModelAndView routToErrorHandler500(Exception ex, ModelMap model,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        model.put(STATUS_MESSAGE_KEY, "accueil.erreur.message");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        model.put(TYPE_EXCEPTION, "accueil.erreur.type500");

        if (showStackTrace) {
            StringBuffer sb = new StringBuffer();

            if (ex.getStackTrace() != null && ex.getStackTrace().length > 0) {
                for (StackTraceElement elt : ex.getStackTrace()) {
                    sb.append(elt.toString());
                    sb.append("<br/>");
                }
            }

            if (ex instanceof SQLException) {
                SQLException sqle = (SQLException) ex;
                SQLException sqlen = sqle.getNextException();

                if (sqlen.getStackTrace() != null
                        && sqlen.getStackTrace().length > 0) {
                    for (StackTraceElement elt : sqlen.getStackTrace()) {
                        sb.append(elt.toString());
                        sb.append("<br/>");
                    }
                }

            }
            model.put(TRACE_EXCEPTION, sb.toString());
        } else {
            model.put(TRACE_EXCEPTION, "");
        }

        return new ModelAndView(Constants.VIEW_ERROR);

    }
}