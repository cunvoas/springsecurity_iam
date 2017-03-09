package com.github.cunvoas.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SampleApplicationContext implements ApplicationContextAware {
    
    private static ApplicationContext ctx=null;
    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        ctx = arg0;
    }
    /**
     * Getter for ctx.
     * @return the ctx
     */
    public static Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

}
