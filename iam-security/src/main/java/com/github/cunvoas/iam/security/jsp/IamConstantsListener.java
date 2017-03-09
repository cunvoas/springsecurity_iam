package com.github.cunvoas.iam.security.jsp;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class IamConstantsListener implements ServletContextListener {
    
    private static final String CONTEXT_PARAM_KEY="IamKeys-ClassName";
    public static final String DEFAULT_IAM_CLASS="com.github.cunvoas.iam.security.helper.IamHelper";

    /**
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // default class use
        String iamKeyClass = DEFAULT_IAM_CLASS;
        
        // read web.xml context-param value
        ServletContext sc = sce.getServletContext();
        if (sc != null && sc.getInitParameter(CONTEXT_PARAM_KEY) != null) {
            iamKeyClass = sc.getInitParameter(CONTEXT_PARAM_KEY);
        }

        sce.getServletContext().setAttribute("IamKeys", new JspConstants(iamKeyClass));

    }

    /**
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("IamKeys");
    }

}
