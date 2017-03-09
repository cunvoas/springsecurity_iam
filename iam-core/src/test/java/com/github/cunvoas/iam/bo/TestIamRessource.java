package com.github.cunvoas.iam.bo;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestIamRessource {

    private IamRole iamRole;

    @Before
    public void setUp() throws Exception {
        
        int borne=0;

        IamRessource iamRessource = new IamRessource();
        iamRessource.setId(0);
        iamRessource.setCode("RESS_0");
        iamRessource.setBorneInf(borne++);
        
        for (int i=1;i<3;i++) {
            IamRessource enfant = new IamRessource();
            enfant.setId(i);
            enfant.setCode("RESS_"+i);
            enfant.setBorneInf(borne++);
            enfant.setBorneSup(borne++);
            iamRessource.addEnfant(enfant);
        }
        iamRessource.setBorneSup(borne++);
        

        iamRole = new IamRole();
        iamRole.setCode("ROLE");
        iamRole.setRessource(iamRessource);
    }

    @Test
    public void testGetParent() throws Exception {
        
        // Save customer to XML
        JAXBContext jc = JAXBContext.newInstance(IamRole.class);
        Marshaller marshaller = jc.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(iamRole, writer);
        
        Assert.assertTrue("XML serialization", writer.getBuffer().length()>0);
        
               
        // Load customer from XML 
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        StringReader reader = new StringReader(writer.toString());
        IamRole objectFromXml =  (IamRole) unmarshaller.unmarshal(reader);
        
        Assert.assertNotNull("deserialize object", objectFromXml);
        
        
        
//        try {
//            JAXBContext context = JAXBContext.newInstance(IamRessource.class);
//            Marshaller marshaller = context.createMarshaller();
//            marshaller.marshal(iamRessource, System.out);
//        } catch (JAXBException e) {
//            
//            Assert.fail(e.getMessage());
//        }

    }

}
