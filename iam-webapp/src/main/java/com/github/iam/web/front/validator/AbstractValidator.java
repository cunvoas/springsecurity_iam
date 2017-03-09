package com.github.cunvoas.iam.web.front.validator;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

import com.github.cunvoas.iam.security.authority.IamUserDetails;

/**
 * Abstract Validator.
 * @author C.UNVOAS
 */
@Component
public abstract class AbstractValidator implements Validator, InitializingBean{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractValidator.class);
    
    @Autowired
    @Qualifier("messageSource")
    protected MessageSource messageSource;
    
    // Annotation de validation
    protected javax.validation.Validator validator;
    
    /**
     * Valide les annnotations de controle. 
     * @param target
     * @param errors
     */
    public void validateAnnotation(Object target, org.springframework.validation.Errors errors) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target);
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String keyMessage = constraintViolation.getMessage();
            String message = getMessage(keyMessage, null);
            errors.rejectValue(propertyPath, "annotedError", message);
        }
    }
    
    /**
     * Fourni le message du bundle.
     * @param key
     * @param args
     * @return
     */
    public String getMessage(String key, Object[] args) {
        String message = null;
        Authentication auth =SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        
        Locale locale = Locale.getDefault();
        if (principal instanceof IamUserDetails)  {
            IamUserDetails secUser = (IamUserDetails)principal;
            if (secUser.getLangage()!=null) {
                locale = Locale.forLanguageTag(secUser.getLangage());
            }
        }
        
        try {
            message = messageSource.getMessage(key, args, locale);
        } catch (NoSuchMessageException e) {
            LOGGER.warn("No message found for key={} locale={}", key, locale.toString());
            // affiche la clé et la locale au lieu de lerer une exception
            message=key+"_"+locale.toString();
        }
        return message;
    }


    /**
     * @throws Exception
     * @see http://blog.trifork.nl/2009/08/04/bean-validation-integrating-jsr-303-with-spring/
     */
    @Override
    public void afterPropertiesSet() throws Exception {
         ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
         validator = validatorFactory.usingContext().getValidator();
    }
    
}
