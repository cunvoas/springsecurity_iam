package com.github.cunvoas.iam.web.front.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * @author CUNVOAS
 */
@Component
public class GenericFormValidator extends AbstractValidator {

    /**
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.getName().matches(".*Form");
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object target, Errors errors) {
        super.validateAnnotation(target, errors);
    }

}
