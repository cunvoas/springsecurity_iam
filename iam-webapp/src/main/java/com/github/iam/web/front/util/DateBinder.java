package com.github.cunvoas.iam.web.front.util;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Formateur de date pour formulaire spring mvc.
 * @author cunvoas
 *
 */
public class DateBinder extends PropertyEditorSupport{
    
    private static final String DATE_PATTERN="dd/MM/yyyy"; 

     /**
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
     public void setAsText(String text) {
         DateFormat df= new SimpleDateFormat(DATE_PATTERN);
         if (text!=null) {
             try {
                setValue(df.parse(text));
            } catch (ParseException ignore) {
            }
         }
      
     }

    /**
     * @see java.beans.PropertyEditorSupport#getAsText()
     */
    @Override
     public String getAsText() {
         DateFormat df= new SimpleDateFormat(DATE_PATTERN);
         if (getValue() instanceof Date) {
             return df.format((Date)getValue());
         }
         return "";
     }

}
