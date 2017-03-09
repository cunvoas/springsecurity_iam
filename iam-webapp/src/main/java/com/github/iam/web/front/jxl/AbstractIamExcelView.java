package com.github.cunvoas.iam.web.front.jxl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WritableWorkbook;

import com.github.cunvoas.iam.web.front.util.Constants;


/**
 * Vue de production de fichier XL.
 * 
 * @author cunvoas
 * @see http://jexcelapi.sourceforge.net/resources/javadocs/2_6_10/docs/
 * @see http://r4r.co.in/java/apis/jexcel/basic/example/jexcel_basic_examples.php?id=759&option=Jexcel%20Example
 * @see http://www.developpez.net/forums/d426675/java/general-java/apis/documents/mise-forme-cellules-sous-jxl/
 */
public abstract class AbstractIamExcelView extends AbstractJxlView implements Constants {
    
    /**
     * @return name on XLS file.
     */
    protected abstract String getFileName();

    /**
     * @see org.springframework.web.servlet.view.document.AbstractJExcelView#buildExcelDocument(java.util.Map,
     *      jxl.write.WritableWorkbook, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected final void buildExcelDocument(Map<String, Object> model,
            WritableWorkbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        long time = 0;
        long memory = 0;
        if (LOGGER.isInfoEnabled()) {
            time = System.currentTimeMillis();
            memory = Runtime.getRuntime().freeMemory();
        }
        try {
            // appel de l'implémentation à proprement parler
            this.buildIamExcelDocument(model, workbook, request, response);

            // force le nom du fichier visible par le client
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\";", this.getFileName()) );
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            // write & close r�aliser par AbstractJExcelView.renderMergedOutputModel()

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("production XLS :");
                LOGGER.info("\t{} ms", Long.valueOf(Math.round(System.currentTimeMillis() - time)).intValue());
                LOGGER.info("\t{} ko", Math.round((Runtime.getRuntime().freeMemory() - memory) / 1024));
            }
        }
    }

    
    
    /**
     * Surcharge pour IAM.
     * 
     * @param model
     * @param workbook
     * @param request
     * @param response
     * @throws Exception
     * @see org.springframework.web.servlet.view.document.AbstractJExcelView#buildExcelDocument(java.util.Map,
     *      jxl.write.WritableWorkbook, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected abstract void buildIamExcelDocument(Map<String, Object> model,
            WritableWorkbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception;

}