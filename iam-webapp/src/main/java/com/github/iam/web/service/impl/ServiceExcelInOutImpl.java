package com.github.cunvoas.iam.web.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.read.biff.WorkbookParser;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.bo.IamRessource;
import com.github.cunvoas.iam.bo.IamRole;
import com.github.cunvoas.iam.security.authority.IamUserDetails;
import com.github.cunvoas.iam.service.ServiceIamRessource;
import com.github.cunvoas.iam.service.mapper.MapperHelper;
import com.github.cunvoas.iam.web.service.ServiceExcelInOut;

/**
 * Service dedicated to XLS generation and parsing.
 * 
 * @author CUnvoas
 * 
 */
@Service("ServiceExcelInOut")
public class ServiceExcelInOutImpl implements ServiceExcelInOut {
    
    private static final String JAVA_ENCODING="UTF-8";
    private static final String XL_FILE_ENCODING="Cp1252";
            
    protected static final Logger LOGGER = LoggerFactory.getLogger(ServiceExcelInOutImpl.class);
    
    private static final String SHEET_APPLICATION = "Application";
    private static final String SHEET_RESSOURCE = "Ressource";

    @Autowired
    private MessageSource messages;
    
    @Autowired
    private ServiceIamRessource serviceIamRessource;

    
    /**
     * Full name and path of temp file.
     * @return
     */
    private String generateTempXlsFileName( ) {
        // generate temp file copy
        StringBuilder uidFileBuilder = new StringBuilder();
        uidFileBuilder.append(System.getProperty("java.io.tmpdir"));
        uidFileBuilder.append("iam-");
        uidFileBuilder.append(UUID.randomUUID().toString());
        uidFileBuilder.append(".xls");
        LOGGER.debug("generate temp file: {}", uidFileBuilder.toString()); 
        return uidFileBuilder.toString();
    }
    
    /**
     * @see com.github.cunvoas.iam.web.service.ServiceExcelInOut#getWritableTemplate(jxl.Workbook, java.io.OutputStream)
     */
    public WritableWorkbook getWritableTemplate(Workbook tplWkbk, OutputStream os) {
        
        WorkbookSettings ws = new WorkbookSettings();
        ws.setEncoding(XL_FILE_ENCODING);
        
        WritableWorkbook workbook=null;
        try {
            
            if (os!=null) {
                workbook = Workbook.createWorkbook(new File(generateTempXlsFileName()), tplWkbk, ws);
                
            } else {
                workbook = Workbook.createWorkbook(os, tplWkbk, ws);
            }
            
            // initialise fonts
            
            
        } catch (IOException e) {
            LOGGER.error("JXL IO", e);
        }
        
        return workbook;
    }
    
    /**
     * @see com.github.cunvoas.iam.web.service.ServiceExcelInOut#clearTemplate(jxl.write.WritableWorkbook)
     */
    public void clearTemplate(WritableWorkbook workbook) {
        try {
                        
            // erase sample cells
            // APPLICATION!B2
            append(workbook.getSheet(SHEET_APPLICATION), 1, 1, "", null);
            // APPLICATION!B5
            append(workbook.getSheet(SHEET_APPLICATION), 1, 4, "", null);

            // RESSOURCE!A2:E11
            for (int idxCol=0;idxCol<5;idxCol++) {
                for (int idxRow=1;idxRow<11;idxRow++) {
                    append(workbook.getSheet(SHEET_RESSOURCE), idxCol, idxRow, "", null);
                }
            }
            
            // RESSOURCE!AE2:AE31
            for (int idxRow=1;idxRow<11;idxRow++) {
                append(workbook.getSheet(SHEET_RESSOURCE), 29, idxRow, "", null);
            }
            
        } catch (WriteException e) {
            LOGGER.error("JXL Write", e);
        }
        
    }

    /**
     * @see com.github.cunvoas.iam.web.service.impl.ServiceExcelInOut#getSampleTemplate()
     */
    @Override
    public ByteArrayOutputStream getSampleTemplate() {
        URL url = Thread.currentThread().getContextClassLoader()
                .getResource("xls_template/IAM_ConfigTemplate.xls");

        ByteArrayOutputStream baos=null;
        FileInputStream fin = null;
        try {
            baos = new ByteArrayOutputStream();
            // 16k buffer
            byte readBuf[] = new byte[16 * 1024];
            
            fin = new FileInputStream(url.getPath());
            int readCnt = fin.read(readBuf);
            while (0 < readCnt) {
                baos.write(readBuf, 0, readCnt);
                readCnt = fin.read(readBuf);
            }

        } catch (FileNotFoundException e) {
            LOGGER.error("template not found", e);
        } catch (IOException e) {
            LOGGER.error("template not found", e);
        } finally {
            if (fin!=null) {
                try {
                    fin.close();
                } catch (IOException ignore) {
                    LOGGER.info("close", ignore);
                }
            }
        }

        return baos;
    }

    
    /**
     * @see com.github.cunvoas.iam.web.service.ServiceExcelInOut#generate(com.github.cunvoas.iam.bo.IamApplication, jxl.write.WritableWorkbook)
     */
    public void generate(IamApplication application, WritableWorkbook workbook) {
        try {
            append(workbook.getSheet(SHEET_APPLICATION), 1, 1, application.getCode(), null);
            append(workbook.getSheet(SHEET_APPLICATION), 2, 1, application.getDescription(), null);
            append(workbook.getSheet(SHEET_APPLICATION), 3, 1, application.getUrl(), null);
            
            int idxRowRole=4;
            for (IamRole role : application.getRoles()) {
                append(workbook.getSheet(SHEET_APPLICATION), 1, idxRowRole, role.getCode(), null);
                append(workbook.getSheet(SHEET_APPLICATION), 2, idxRowRole, role.getDescription(), null);
                append(workbook.getSheet(SHEET_APPLICATION), 3, idxRowRole, role.getCommentaire(), null);
                idxRowRole++;
            }
            
            int idxRowRess=1;
            List<IamRessource> ress = MapperHelper.asList(application.getRessources());
            // for each resource
            for (IamRessource iamRessource : ress) {
                append(workbook.getSheet(SHEET_RESSOURCE), iamRessource.getProfondeur(), idxRowRess, iamRessource.getCode(), null);
                
                // id node :wet values
                if (!iamRessource.isNode()) {
                    int idxColRole = 30;
                    // for each role
                    for (IamRole role : application.getRoles()) {
                        if (role.getRessourceValues()!=null && role.getRessourceValues().containsKey(iamRessource.getVectorCode())) {
                            append(workbook.getSheet(SHEET_RESSOURCE), idxColRole, idxRowRess, role.getRessourceValues().get(iamRessource.getVectorCode()), null);
                            idxColRole++;
                        }
                    }
                    
                }
                idxRowRess++;
            }
            
            
            
            
        } catch (RowsExceededException e) {
            LOGGER.error("JXL Row OutOfBound", e);
        } catch (WriteException e) {
            LOGGER.error("JXL Write data", e);
        }
        
    }
    /**
     * 
     * @see com.github.cunvoas.iam.web.service.ServiceExcelInOut#generate(com.github.cunvoas.iam.bo.IamApplication, java.io.OutputStream)
     */
    @Override
    public void generate(IamApplication application, OutputStream os) {
        // get clean template
        Workbook tplWkbk = getTemplateSource();
        WritableWorkbook workbook = getWritableTemplate(tplWkbk, os);
        
        generate(application, workbook);

    }

    // constants for XL Sheet
    private static final int RESVAL_COL_IDX=30;
    private static final int ROLE_START_COL_IDX=5;
    private static final int ROLE_END_COL_IDX=10;
    
    /**
     * @see com.github.cunvoas.iam.web.service.impl.ServiceExcelInOut#parse(java.io.InputStream)
     */
    @Override
    public IamApplication parse(InputStream is) throws Exception {

        IamApplication iamApp = null;
    
        Workbook workbook = WorkbookParser.getWorkbook(is);
        Sheet sheetApp = workbook.getSheet(SHEET_APPLICATION);

        String appCode = sheetApp.getCell("B2").getContents();
        String appDesc = sheetApp.getCell("C2").getContents();
        String appUrl = sheetApp.getCell("D2").getContents();

        if (!StringUtils.isBlank(appCode)) {
            iamApp = new IamApplication();
            iamApp.setCode(clean(appCode));
            iamApp.setDescription(appDesc);
            iamApp.setUrl(appUrl);
            iamApp.setRoles(new ArrayList<IamRole>());

            // get resource tree
            Sheet sheetRess = workbook.getSheet(SHEET_RESSOURCE);
            IamRessource ressource = getRessource(sheetRess, appCode);
            iamApp.setRessources(ressource);

            List<IamRessource> listRessources = MapperHelper.asList(ressource);
            
            
            int roleRessValIdx=RESVAL_COL_IDX;
            // for each role from B5 to B10
            for (int lig = ROLE_START_COL_IDX; lig <= ROLE_END_COL_IDX; lig++) {
                String rolCode = sheetApp.getCell("B" + lig).getContents();
                String rolDesc = sheetApp.getCell("C" + lig).getContents();
                String rolComm = sheetApp.getCell("D" + lig).getContents();

                
                if (!StringUtils.isBlank(rolCode)) {
                    IamRole role = new IamRole();
                    role.setCode(clean(rolCode));
                    role.setDescription(rolDesc);
                    role.setCommentaire(rolComm);
                    
                    // WARNING same instance than application and all roles
                    // the ressources values can't be used for persistance
                    role.setRessource(ressource);
                    iamApp.getRoles().add(role);

                    // search resourceValues for the role
                    try {
                        // init Map for resourceValues
                        role.setRessourceValues(new HashMap<String, String>());
                        int resLigId=0;
                        for (IamRessource ressVal : listRessources) {
                            if (!ressVal.isNode()) {
                                // starts in AE30
                                String valeur = sheetRess.getCell(roleRessValIdx, resLigId).getContents();
                                if (LOGGER.isInfoEnabled()) {
                                    LOGGER.info("RESVAL ({}, {}) {}: {}={})", new Object[] { roleRessValIdx, resLigId, rolCode, ressVal.getVectorCode(), valeur});                                }
                                if (!StringUtils.isBlank(valeur)) {
                                    // affection role.getRessource() inutilie (même instance)
                                    role.getRessourceValues().put(ressVal.getVectorCode(), clean(valeur));
                                }
                            }
                            resLigId++;
                        }
                    } catch (ArrayIndexOutOfBoundsException aiobe) {
                        LOGGER.info("XLS Parse limits resourceValue", aiobe.getMessage());
                    }
                            
                    roleRessValIdx++;
                }
            }
            

            // Check validity
            serviceIamRessource.validate(iamApp, iamApp.getRessources());
        }

        return iamApp;
    }

    private IamRessource getRessource(Sheet sheetRol, String appCode) {
        IamRessource rootRess = new IamRessource();
        rootRess.setCode(appCode);

        // sauvegarde de l'avancée des ressources.
        IamRessource[] prevRessSave = new IamRessource[30];
        prevRessSave[0] = rootRess;

        try {
            for (int lig = 2; lig < 103; lig++) {

                boolean foundInLig = false;
                for (int col = 1; col <= 30; col++) {
                    String ressCode = sheetRol.getCell(col - 1, lig - 1)
                            .getContents();

                    if (!StringUtils.isBlank(ressCode)) {
                        LOGGER.debug("do node {} depth {}", ressCode, col);
                        IamRessource tmpRess = new IamRessource();
                        tmpRess.setCode(clean(ressCode));
                        foundInLig = true;

                        prevRessSave[col] = tmpRess;
                        prevRessSave[col - 1].addEnfant(tmpRess);
                        break;
                    }
                }

                if (!foundInLig) {
                    // dernière ligne entièrement vide
                    LOGGER.debug("empty line {}", lig);
                    break;
                }

            }
            
        } catch (ArrayIndexOutOfBoundsException ignore) {
            // end of XLS rows
            // JXL limite Excel array
            LOGGER.debug("end of XLS rows", ignore.getMessage());
        }
        
        // add intervals
        serviceIamRessource.computeInterval(rootRess);
        return rootRess;
    }

    /**
     * Ajout une cellule texte à la feuille.
     * 
     * @param sheet
     * @param idxCol
     * @param idxRow
     * @param value
     * @param cellFormat
     * @throws RowsExceededException
     * @throws WriteException
     */
    protected static void append(WritableSheet sheet, int idxCol, int idxRow,
            String value, WritableCellFormat cellFormat)
            throws WriteException {
        
        Label lbl = new Label(idxCol, idxRow, getCp1252(value));
        putCell(sheet, lbl, cellFormat);
    }

    /**
     * Ajout une cellule.
     * 
     * @param sheet
     * @param cell
     * @param cellFormat
     * @throws RowsExceededException
     * @throws WriteException
     */
    private static void putCell(WritableSheet sheet, WritableCell cell,
            WritableCellFormat cellFormat) throws WriteException {
        if (cellFormat != null) {
            cell.setCellFormat(cellFormat);
        }
        sheet.addCell(cell);
    }

    /**
     * Add a formula.
     * 
     * @param sheet
     * @param idxCol
     * @param idxRow
     * @param strFormula
     * @param cellFormat
     * @throws RowsExceededException
     * @throws WriteException
     */
    protected static void appendFormula(WritableSheet sheet, int idxCol,
            int idxRow, String strFormula, WritableCellFormat cellFormat)
            throws WriteException {
        Formula formula = new Formula(idxCol, idxRow, getCp1252(strFormula));
        sheet.addCell(formula);
    }

    /**
     * Add a numeric cell without comment.
     * 
     * @param sheet
     * @param idxCol
     * @param idxRow
     * @param value
     * @param cellFormat
     * @throws RowsExceededException
     * @throws WriteException
     */
    protected static void append(WritableSheet sheet, int idxCol, int idxRow,
            int value, WritableCellFormat cellFormat)
            throws WriteException {
        jxl.write.Number lbl = new Number(idxCol, idxRow, value);
        putCell(sheet, lbl, cellFormat);
    }

    /**
     * Add a numeric cell without comment.
     * 
     * @param sheet
     * @param idxCol
     * @param idxRow
     * @param value
     * @param cellFormat
     * @throws RowsExceededException
     * @throws WriteException
     */
    protected static void append(WritableSheet sheet, int idxCol, int idxRow,
            double value, WritableCellFormat cellFormat)
            throws WriteException {
        jxl.write.Number lbl = new Number(idxCol, idxRow, value);
        putCell(sheet, lbl, cellFormat);
    }

    /**
     * Add a numeric cell with comment.
     * 
     * @param sheet
     * @param idxCol
     * @param idxRow
     * @param value
     * @param cellFormat
     * @param comment
     * @throws RowsExceededException
     * @throws WriteException
     */
    protected static void append(WritableSheet sheet, int idxCol, int idxRow,
            int value, WritableCellFormat cellFormat, String comment)
            throws WriteException {
        jxl.write.Number lbl = new Number(idxCol, idxRow, value);
        WritableCellFeatures cellFeatures = new WritableCellFeatures();
        cellFeatures.setComment(getCp1252(comment), 4, 5);
        lbl.setCellFeatures(cellFeatures);
        putCell(sheet, lbl, cellFormat);
    }

    /**
     * Get text from message ressources.
     * 
     * @param key
     * @param prms
     * @return
     */
    protected String getI18nText(String key, Object[] prms,
            HttpServletRequest request) {
        String msg = null;
        Locale locale = getLocale(request);
        try {
            msg = messages.getMessage(key, prms, locale);

        } catch (NoSuchMessageException e) {
            StringBuffer sb = new StringBuffer();
            sb.append(key);
            sb.append(" not found, Locale=");
            sb.append(locale.getLanguage());
            msg = sb.toString();
        }
        return msg;
    }

    /**
     * @param request
     * @return
     */
    protected static final Locale getLocale(HttpServletRequest request) {
        String codeLangue = null;
        
        // find user language
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof IamUserDetails) {
            IamUserDetails userDetails = (IamUserDetails)principal;
            if (userDetails.getLangage()!=null) {
                codeLangue = userDetails.getLangage();
            }
        }
        
        Locale locale = null;
        if (codeLangue == null) {
            locale = Locale.getDefault();
        } else {
            locale = new Locale(codeLangue);
        }
        return locale;
    }

    /**
     * @see com.github.cunvoas.iam.web.service.ServiceExcelInOut#getTemplateSource()
     */
    @Override
    public Workbook getTemplateSource() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("xls_template/IAM_BlankTemplate.xls");
        
        
        Workbook tplWkbk=null;
        try {
            tplWkbk = Workbook.getWorkbook(new File(url.getPath()));
        } catch (BiffException | IOException e) {
            LOGGER.error("JXL Template read error", e);
        }
        return tplWkbk;
    }
    
    private static String getCp1252(String utf8String) {
        String retString=utf8String;
        try {
            if (JAVA_ENCODING.equals(System.getProperty("file.encoding"))) {

                byte[] bytes = utf8String.getBytes(JAVA_ENCODING);  
                retString =  new String(bytes,XL_FILE_ENCODING);
            }
            
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("", e);
        }  
        return retString;
    }

    /**
     * Setter for  serviceIamRessource.
     * @param serviceIamRessource the serviceIamRessource to set
     */
    public void setServiceIamRessource(ServiceIamRessource serviceIamRessource) {
        this.serviceIamRessource = serviceIamRessource;
    }
    
    private static String clean(String xlValue) {
        String retString = null;
        if (xlValue!=null) {
            retString = xlValue.toUpperCase().trim();
        }
        return retString;
    }

}
