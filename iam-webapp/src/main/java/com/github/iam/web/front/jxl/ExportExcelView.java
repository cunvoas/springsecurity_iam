package com.github.cunvoas.iam.web.front.jxl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WritableWorkbook;

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.web.service.ServiceExcelInOut;

/**
 * @author CUNVOAS
 */
public class ExportExcelView extends AbstractIamExcelView {
    
    private ServiceExcelInOut serviceExcelInOut;

    /**
     * @see com.github.cunvoas.iam.web.front.jxl.AbstractIamExcelView#getFileName()
     */
    @Override
    protected String getFileName() {
        return "iam-application.xls";
    }
    
    /**
     * @see com.github.cunvoas.iam.web.front.jxl.AbstractIamExcelView#buildIamExcelDocument(java.util.Map, jxl.write.WritableWorkbook, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void buildIamExcelDocument(Map<String, Object> model, WritableWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IamApplication iamApplication = (IamApplication)model.get(MODEL_APP);
        serviceExcelInOut.generate(iamApplication, workbook);
    }


    /**
     * Setter for serviceExcelInOut.
     * @param serviceExcelInOut the serviceExcelInOut to set
     */
    public void setServiceExcelInOut(ServiceExcelInOut serviceExcelInOut) {
        this.serviceExcelInOut = serviceExcelInOut;
    }

}
