package com.github.cunvoas.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet de recherche de classe au classPath.
 * Copiée du code d'IBM.
 * @author Christophe UNVOAS
 * @author com.ibm.servlet.ClassFinderServlet
 */
public class ClassFinderServlet extends HttpServlet {

    public ClassFinderServlet()
    {
    }

    protected void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
    {
        processRequest(httpservletrequest, httpservletresponse);
    }

    protected void doPost(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
    {
        processRequest(httpservletrequest, httpservletresponse);
    }

    protected void processRequest(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
    {
        String s = null;
        String s1 = null;
        String s2 = httpservletrequest.getParameter("className");
        if(s2 != null && (s2 = s2.trim()).length() != 0)
            try
            {
                ProtectionDomain protectiondomain = Class.forName(s2).getProtectionDomain();
                if(protectiondomain != null)
                {
                    CodeSource codesource = protectiondomain.getCodeSource();
                    if(codesource != null)
                        s1 = codesource.toString();
                    else
                        s = "No CodeSource found!";
                } else
                {
                    s = "No ProtectionDomain found!";
                }
            }
            catch(Throwable throwable)
            {
                s = throwable.toString();
                log("error=" + throwable, throwable);
            }
        httpservletresponse.setContentType("text/html");
        PrintWriter printwriter = httpservletresponse.getWriter();
        printwriter.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        printwriter.println("<html>");
        printwriter.println("<head>");
        printwriter.println("<title>Servlet Container Class Finder</title>");
        printwriter.println("</head>");
        printwriter.println("<body bgcolor=\"#d1d7b3\">");
        printwriter.println("<h2><font color=\"#0000a0\">Servlet Container Class Finder</font></h2>");
        printwriter.println("<p><font color=\"#000000\">Enter the fully-qualified name of a Java class");
        printwriter.println("(e.g. org.apache.oro.text.regex.Perl5Compiler) in the field below. The");
        printwriter.println("servlet will attempt to load the class and, if successful, query the");
        printwriter.println("classes' <em>java.security.CodeSource</em> for the location of the class");
        printwriter.println("using the following methods: <pre>");
        printwriter.println("Class.forName(className).getProtectionDomain().getCodeSource()");
        printwriter.println("</pre> </font></p>");
        printwriter.println("<form method=\"post\" action=\"" + httpservletrequest.getRequestURI() + "\">");
        printwriter.println("<p>Class Name: <input type=\"text\" name=\"className\"");
        printwriter.println("\tvalue=\"" + (s2 == null ? "" : s2) + "\" size=\"40\" /> <input type=\"submit\"");
        printwriter.println("\tvalue=\"Submit\" /></p>");
        printwriter.println("</form>");
        if(s1 != null || s != null)
        {
            printwriter.println("<table border=\"1\" bgcolor=\"#8080c0\">");
            printwriter.println("\t<caption align=\"top\"><font color=\"#000000\">Search Results</font></caption>");
            printwriter.println("\t<tbody>");
            printwriter.println("\t\t<tr>");
            printwriter.println("\t\t\t<td align=\"right\"><font color=\"#000000\">Class Name:</font></td>");
            printwriter.println("\t\t\t<td><font color=\"#000000\">" + s2 + "</font></td>");
            printwriter.println("\t\t</tr>");
            printwriter.println("\t\t<tr>");
            if(s != null)
            {
                printwriter.println("\t\t\t<td align=\"right\"><font color=\"#a00000\">Error:</font></td>");
                printwriter.println("\t\t\t<td><font color=\"#a00000\">" + s + "</font></td>");
            } else
            {
                printwriter.println("\t\t\t<td align=\"right\"><font color=\"#000000\">Class Location:</font></td>");
                printwriter.println("\t\t\t<td><font color=\"#000000\">" + s1 + "</font></td>");
            }
            printwriter.println("\t\t</tr>");
            printwriter.println("\t</tbody>");
            printwriter.println("</table>");
        }
        printwriter.println("</body>");
        printwriter.println("</html>");
        printwriter.flush();
        printwriter.close();
    }

    private static final String CLASS_NAME = "className";

}
