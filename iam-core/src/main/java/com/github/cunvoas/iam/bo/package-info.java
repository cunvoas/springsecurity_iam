/**
 * Package for business objects.
 * @author Christophe UNVOAS
 */
@XmlSchema(
        namespace = "http://bo.iam.ds.vh.com/",
        elementFormDefault = XmlNsForm.UNQUALIFIED,
        xmlns={@XmlNs(
                prefix="nsBo", 
                namespaceURI="http://bo.iam.ds.vh.com/")
        })  
package com.github.cunvoas.iam.bo;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;

