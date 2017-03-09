package com.github.cunvoas.iam.web.service;

import java.io.InputStream;
import java.io.OutputStream;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.github.cunvoas.iam.bo.IamApplication;

public interface ServiceExcelInOut {

    /**
     * Get de BAOS to write on the servlet. outputStream =
     * response.getOutputStream() 
     * baos.writeTo(outputStream)
     * outputStream.flush()
     * 
     * @return
     */
    ByteArrayOutputStream getSampleTemplate();
    Workbook getTemplateSource();
    WritableWorkbook getWritableTemplate(Workbook tplWkbk, OutputStream os);
    void clearTemplate(WritableWorkbook workbook);

    /**
     * 
     * @param workbook
     * @param application
     */
    void generate(IamApplication application, OutputStream os);
    void generate(IamApplication application, WritableWorkbook workbook);

    /**
     * Parse the XLS ConfigFile.
     * 
     * @param is
     * @return
     * @throws Exception
     */
    IamApplication parse(InputStream is) throws Exception;

}