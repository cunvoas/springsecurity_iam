package com.github.cunvoas.iam.web.aop;

import java.sql.SQLException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loggeur AOP.
 * 
 * @author Empeiria
 */
public class MethodLogger {

  /**
   * Loggeur d'entr�e.
   * 
   * @param jpoint
   */
  public void logMethodEntry(final JoinPoint jpoint) {
    final Class<?> clazz = jpoint.getTarget().getClass();
    final Logger log = LoggerFactory.getLogger(clazz);
    if (log.isDebugEnabled()) {
      final String methodName = jpoint.getSignature().getName();
      log.debug("\t entry [ " + clazz.getSimpleName() + " - " + methodName);
    }
  }

  /**
   * Loggeur de sortie.
   * @param staticPart
   * @param result
   */
  public void logMethodExit(final StaticPart staticPart, final Object result) {
    final Class<?> clazz = staticPart.getSignature().getDeclaringType();
    final Logger log = LoggerFactory.getLogger(clazz);
    if (log.isDebugEnabled()) {
      final String methodName = staticPart.getSignature().getName();
      log.debug("\t  exit   " + clazz.getSimpleName() + " - " + methodName+" ]") ;
    }
  }

  private static final int MAX_TRACE_DEPTH = 5;
  /**
   * Exception m�tier non tra�t�e.
   * @param exception l'exception m�tier
   */
  public void logMethodThrow(final JoinPoint jpoint, final Throwable exception) {
    final Class<?> clazz = jpoint.getTarget().getClass();
    final Logger log = LoggerFactory.getLogger(clazz);
    
    if (log.isErrorEnabled()) {
      StringBuffer sb = new StringBuffer();
      sb.append(exception.toString());
      
      StackTraceElement[] trace = exception.getStackTrace();
      
      
      if (trace != null) {
        sb.append("\r\nTRACE :");
        int maxTrace = MAX_TRACE_DEPTH;
        for (StackTraceElement e : trace) {
          sb.append("\t");
          sb.append(e);
          sb.append("\r\n");
          if (maxTrace-- == 0){
            break;
          }
        }
      }

      if (exception instanceof SQLException) {
        SQLException sqle = (SQLException) exception;
        if (sqle.getNextException() != null) {
          Exception next = sqle.getNextException();
          sb.append("\r\nERREUR SQL : ");
          sb.append(next.toString());
          StackTraceElement[] nextTrace = next.getStackTrace();
          if (nextTrace != null) {
            sb.append("\r\nTRACE SQL :");
            for (StackTraceElement e : trace) {
              sb.append("\t");
              sb.append(e);
              sb.append("\r\n");
            }
          }
        }
      }

      log.error(sb.toString());
    }
  }

}
