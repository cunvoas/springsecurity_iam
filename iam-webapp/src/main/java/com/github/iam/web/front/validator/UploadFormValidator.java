package com.github.cunvoas.iam.web.front.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.cunvoas.iam.web.front.application.ApplicationUploadForm;

/**
 * @author CUNVOAS
 */
@Component
public class UploadFormValidator extends AbstractValidator {

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
        
        ApplicationUploadForm form = (ApplicationUploadForm) target;
        
        
        ValidationUtils.rejectIfEmpty(errors, "file", "errors.file1",
                new Object[] { "nom" },
                getMessage("valid.application.upload.file.empty", new Object[] {}));
        
        if (form.getFile()!=null) {
            MultipartFile file = form.getFile();
            String fileName = form.getFile().getName();
            int dernierPoint = fileName.lastIndexOf('.');
            String extensionFichier = fileName.substring(dernierPoint + 1, fileName.length());
            extensionFichier = extensionFichier.toLowerCase();
            
            if (!(
                    "application/vnd.ms-excel".equals(file.getContentType()) ||
                    "application/octet-stream".equals(file.getContentType()) && "xls".equals(extensionFichier)
                )
                ) {
                
                errors.rejectValue(
                        "file",
                        "errors.file2",
                        new Object[] { "file" },
                        getMessage("valid.application.upload.file.format",
                                new Object[] {}));
            }
        }
    }

}
