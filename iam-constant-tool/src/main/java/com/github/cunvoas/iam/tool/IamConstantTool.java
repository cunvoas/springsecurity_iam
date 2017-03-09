/**
 * 
 */
package com.github.cunvoas.iam.tool;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.github.cunvoas.iam.bo.IamRessource;
import com.github.cunvoas.iam.service.ServiceVectorIam;

/**
 * @author CUNVOAS
 */
public class IamConstantTool {
    
    private static String tplClass;
    private static String tplComment;
    private static String tplVariable;

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            usage();
        } else {
            
            PrintStream out = System.out;
            String iamLocation = args[0];
            String packageName = args[1];
            String applicationId = args[2];
            
            
            try {
                tplClass = getTemplate("IamKeys.tpl");
                tplComment = getTemplate("IamKeys-sub1.tpl");
                tplVariable = getTemplate("IamKeys-sub2.tpl");
                
                
                URL wsdlLocation = new URL(iamLocation + "/services/iam/soap?wsdl");
                QName serviceName = new QName("http://impl.service.iam.cunvoas.github.com/", "ServiceVectorIamImplService");
                Service service = Service.create(wsdlLocation, serviceName);
                
                ServiceVectorIam iamSvc = service.getPort(ServiceVectorIam.class);
                
                List<IamRessource> ress = iamSvc.findForConstants(applicationId);
                
                
                
                
                StringBuilder sbContent = new StringBuilder();
                String ruptureString ="@@@@";
                for (IamRessource iamRessource : ress) {
                    String vector = iamRessource.getVectorCode();
                    String[] keys = vector.split("\\.");
                    
                    String level2 = keys[1];
                    
                    if (!ruptureString.equals(level2)) {
                        ruptureString = level2;
                        sbContent.append(tplComment.replace("{0}", level2));
                    }
                    
                    String keyVar = vector.replaceAll("\\.", "_");
                    String variableLineString = tplVariable.replace("{0}", keyVar);
                    variableLineString = variableLineString.replace("{1}", vector);
                    
                    sbContent.append(variableLineString);
                }
                
                String classCodeString = tplClass.replace("{0}", packageName);
                classCodeString = classCodeString.replace("{1}", sbContent.toString());
                
                out.println(classCodeString);
                
                saveConstantsClass(classCodeString);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            
            
            
            
        }

    }

    /**
     * usage.
     */
    private static void usage() {
        PrintStream out = System.out;
        out.println("Usage : java -jar IamConstantTool.jar [IAM Server] [Project Package] [Application CODE]");
        out.println("\tIAM Server / URL of the IAM Server");
        out.println("\tProject Package : is the package for your project");
        out.println("\tApplication CODE : is your application code in IAM");

    }

    private static String getTemplate(String tpl) throws IOException {
        
        URL url = Thread.currentThread().getContextClassLoader().getResource(tpl);
        
        String filePath=null;
        // remplacement pour windows
        if (url.getFile().matches("^/[A-Za-z]:(.*)")) {
            filePath = url.getFile().replaceAll("^/","");
        } else {
            filePath = url.getFile();
        }
        Path path = FileSystems.getDefault().getPath(filePath);
        byte[] content = Files.readAllBytes(path);
        return new String(content);
    }
    
    /**
     * @param content
     * @throws IOException
     * @see http://docs.oracle.com/javase/tutorial/essential/io/file.html
     */
    private static void saveConstantsClass(String content) throws IOException {
        Path path = FileSystems.getDefault().getPath("./IamKeys.java");
        
        Files.write(path, content.getBytes(), StandardOpenOption.CREATE);
    }

}
