package com.github.cunvoas.iam.web.service.impl;

import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.WritableWorkbook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.service.impl.ServiceIamRessourceImpl;
import com.github.cunvoas.iam.web.front.resource.tree.JsTreeResource;
import com.github.cunvoas.iam.web.front.resource.tree.JsTreeWrapper;
import com.github.cunvoas.iam.web.service.ServiceExcelInOut;

@RunWith(PowerMockRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({
//    "/spring/spring-test-web.xml"
//})
@PrepareForTest(ServiceExcelInOutImpl.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
// http://www.jayway.com/2010/12/28/using-powermock-with-spring-integration-testing/
public class TestExcelImportExport {
    
    private InputStream is=null;
    
    @Autowired
    private ServiceExcelInOut tested;
    private ServiceExcelInOutImpl mocked;
    
    private String jsonResult="{\"id\":null,\"code\":\"IAM\",\"ressources\":{\"id\":null,\"code\":\"IAM\",\"vectorCode\":\"IAM\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"ACCESS\",\"vectorCode\":\"IAM.ACCESS\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":1,\"node\":true},{\"id\":null,\"code\":\"APPLICATION\",\"vectorCode\":\"IAM.APPLICATION\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"LISTER\",\"vectorCode\":\"IAM.APPLICATION.LISTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"CREER\",\"vectorCode\":\"IAM.APPLICATION.CREER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"MODIFIER\",\"vectorCode\":\"IAM.APPLICATION.MODIFIER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"SUPPRIMER\",\"vectorCode\":\"IAM.APPLICATION.SUPPRIMER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"UPLOADER\",\"vectorCode\":\"IAM.APPLICATION.UPLOADER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true}],\"profondeur\":1,\"node\":true},{\"id\":null,\"code\":\"ROLE\",\"vectorCode\":\"IAM.ROLE\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"LISTER\",\"vectorCode\":\"IAM.ROLE.LISTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"CREER\",\"vectorCode\":\"IAM.ROLE.CREER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"MODIFIER\",\"vectorCode\":\"IAM.ROLE.MODIFIER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"SUPPRIMER\",\"vectorCode\":\"IAM.ROLE.SUPPRIMER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"AFFECTER\",\"vectorCode\":\"IAM.ROLE.AFFECTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true}],\"profondeur\":1,\"node\":true},{\"id\":null,\"code\":\"RESSOURCE\",\"vectorCode\":\"IAM.RESSOURCE\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"LISTER\",\"vectorCode\":\"IAM.RESSOURCE.LISTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"CREER\",\"vectorCode\":\"IAM.RESSOURCE.CREER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"MODIFIER\",\"vectorCode\":\"IAM.RESSOURCE.MODIFIER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"SUPPRIMER\",\"vectorCode\":\"IAM.RESSOURCE.SUPPRIMER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true}],\"profondeur\":1,\"node\":true}],\"profondeur\":0,\"node\":true},\"description\":\"Gestion des Habilitation\",\"url\":\"https://iam.vaubanhumanis.gie.prov/iam/\",\"roles\":[{\"id\":null,\"code\":\"ADMIN\",\"commentaire\":\"Tous les droits\",\"description\":\"Administrateur Informatique\",\"ressource\":{\"id\":null,\"code\":\"IAM\",\"vectorCode\":\"IAM\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"ACCESS\",\"vectorCode\":\"IAM.ACCESS\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":1,\"node\":true},{\"id\":null,\"code\":\"APPLICATION\",\"vectorCode\":\"IAM.APPLICATION\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"LISTER\",\"vectorCode\":\"IAM.APPLICATION.LISTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"CREER\",\"vectorCode\":\"IAM.APPLICATION.CREER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"MODIFIER\",\"vectorCode\":\"IAM.APPLICATION.MODIFIER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"SUPPRIMER\",\"vectorCode\":\"IAM.APPLICATION.SUPPRIMER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"UPLOADER\",\"vectorCode\":\"IAM.APPLICATION.UPLOADER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true}],\"profondeur\":1,\"node\":true},{\"id\":null,\"code\":\"ROLE\",\"vectorCode\":\"IAM.ROLE\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"LISTER\",\"vectorCode\":\"IAM.ROLE.LISTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"CREER\",\"vectorCode\":\"IAM.ROLE.CREER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"MODIFIER\",\"vectorCode\":\"IAM.ROLE.MODIFIER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"SUPPRIMER\",\"vectorCode\":\"IAM.ROLE.SUPPRIMER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"AFFECTER\",\"vectorCode\":\"IAM.ROLE.AFFECTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true}],\"profondeur\":1,\"node\":true},{\"id\":null,\"code\":\"RESSOURCE\",\"vectorCode\":\"IAM.RESSOURCE\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"LISTER\",\"vectorCode\":\"IAM.RESSOURCE.LISTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"CREER\",\"vectorCode\":\"IAM.RESSOURCE.CREER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"MODIFIER\",\"vectorCode\":\"IAM.RESSOURCE.MODIFIER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"SUPPRIMER\",\"vectorCode\":\"IAM.RESSOURCE.SUPPRIMER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true}],\"profondeur\":1,\"node\":true}],\"profondeur\":0,\"node\":true},\"ressourceValues\":{}},{\"id\":null,\"code\":\"APP_MANAGER\",\"commentaire\":\"Droits de gestion des application, et de leur ressources,\",\"description\":\"Manager d'application\",\"ressource\":{\"id\":null,\"code\":\"IAM\",\"vectorCode\":\"IAM\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"ACCESS\",\"vectorCode\":\"IAM.ACCESS\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":1,\"node\":true},{\"id\":null,\"code\":\"APPLICATION\",\"vectorCode\":\"IAM.APPLICATION\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"LISTER\",\"vectorCode\":\"IAM.APPLICATION.LISTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"CREER\",\"vectorCode\":\"IAM.APPLICATION.CREER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"MODIFIER\",\"vectorCode\":\"IAM.APPLICATION.MODIFIER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"SUPPRIMER\",\"vectorCode\":\"IAM.APPLICATION.SUPPRIMER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"UPLOADER\",\"vectorCode\":\"IAM.APPLICATION.UPLOADER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true}],\"profondeur\":1,\"node\":true},{\"id\":null,\"code\":\"ROLE\",\"vectorCode\":\"IAM.ROLE\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"LISTER\",\"vectorCode\":\"IAM.ROLE.LISTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"CREER\",\"vectorCode\":\"IAM.ROLE.CREER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"MODIFIER\",\"vectorCode\":\"IAM.ROLE.MODIFIER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"SUPPRIMER\",\"vectorCode\":\"IAM.ROLE.SUPPRIMER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"AFFECTER\",\"vectorCode\":\"IAM.ROLE.AFFECTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true}],\"profondeur\":1,\"node\":true},{\"id\":null,\"code\":\"RESSOURCE\",\"vectorCode\":\"IAM.RESSOURCE\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"LISTER\",\"vectorCode\":\"IAM.RESSOURCE.LISTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"CREER\",\"vectorCode\":\"IAM.RESSOURCE.CREER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"MODIFIER\",\"vectorCode\":\"IAM.RESSOURCE.MODIFIER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"SUPPRIMER\",\"vectorCode\":\"IAM.RESSOURCE.SUPPRIMER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true}],\"profondeur\":1,\"node\":true}],\"profondeur\":0,\"node\":true},\"ressourceValues\":{}},{\"id\":null,\"code\":\"ROLE_MANAGER\",\"commentaire\":\"Droits d'affection des droits sur les ressources\",\"description\":\"Manager de r�le (MOA)\",\"ressource\":{\"id\":null,\"code\":\"IAM\",\"vectorCode\":\"IAM\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"ACCESS\",\"vectorCode\":\"IAM.ACCESS\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":1,\"node\":true},{\"id\":null,\"code\":\"APPLICATION\",\"vectorCode\":\"IAM.APPLICATION\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"LISTER\",\"vectorCode\":\"IAM.APPLICATION.LISTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"CREER\",\"vectorCode\":\"IAM.APPLICATION.CREER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"MODIFIER\",\"vectorCode\":\"IAM.APPLICATION.MODIFIER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"SUPPRIMER\",\"vectorCode\":\"IAM.APPLICATION.SUPPRIMER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"UPLOADER\",\"vectorCode\":\"IAM.APPLICATION.UPLOADER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true}],\"profondeur\":1,\"node\":true},{\"id\":null,\"code\":\"ROLE\",\"vectorCode\":\"IAM.ROLE\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"LISTER\",\"vectorCode\":\"IAM.ROLE.LISTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"CREER\",\"vectorCode\":\"IAM.ROLE.CREER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"MODIFIER\",\"vectorCode\":\"IAM.ROLE.MODIFIER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"SUPPRIMER\",\"vectorCode\":\"IAM.ROLE.SUPPRIMER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"AFFECTER\",\"vectorCode\":\"IAM.ROLE.AFFECTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true}],\"profondeur\":1,\"node\":true},{\"id\":null,\"code\":\"RESSOURCE\",\"vectorCode\":\"IAM.RESSOURCE\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"LISTER\",\"vectorCode\":\"IAM.RESSOURCE.LISTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"CREER\",\"vectorCode\":\"IAM.RESSOURCE.CREER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"MODIFIER\",\"vectorCode\":\"IAM.RESSOURCE.MODIFIER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"SUPPRIMER\",\"vectorCode\":\"IAM.RESSOURCE.SUPPRIMER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true}],\"profondeur\":1,\"node\":true}],\"profondeur\":0,\"node\":true},\"ressourceValues\":{}},{\"id\":null,\"code\":\"READER\",\"commentaire\":\"Droits de consultation uniquement\",\"description\":\"Lecteur du param�trages\",\"ressource\":{\"id\":null,\"code\":\"IAM\",\"vectorCode\":\"IAM\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"ACCESS\",\"vectorCode\":\"IAM.ACCESS\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":1,\"node\":true},{\"id\":null,\"code\":\"APPLICATION\",\"vectorCode\":\"IAM.APPLICATION\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"LISTER\",\"vectorCode\":\"IAM.APPLICATION.LISTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"CREER\",\"vectorCode\":\"IAM.APPLICATION.CREER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"MODIFIER\",\"vectorCode\":\"IAM.APPLICATION.MODIFIER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"SUPPRIMER\",\"vectorCode\":\"IAM.APPLICATION.SUPPRIMER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"UPLOADER\",\"vectorCode\":\"IAM.APPLICATION.UPLOADER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true}],\"profondeur\":1,\"node\":true},{\"id\":null,\"code\":\"ROLE\",\"vectorCode\":\"IAM.ROLE\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"LISTER\",\"vectorCode\":\"IAM.ROLE.LISTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"CREER\",\"vectorCode\":\"IAM.ROLE.CREER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"MODIFIER\",\"vectorCode\":\"IAM.ROLE.MODIFIER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"SUPPRIMER\",\"vectorCode\":\"IAM.ROLE.SUPPRIMER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"AFFECTER\",\"vectorCode\":\"IAM.ROLE.AFFECTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true}],\"profondeur\":1,\"node\":true},{\"id\":null,\"code\":\"RESSOURCE\",\"vectorCode\":\"IAM.RESSOURCE\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":[{\"id\":null,\"code\":\"LISTER\",\"vectorCode\":\"IAM.RESSOURCE.LISTER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"CREER\",\"vectorCode\":\"IAM.RESSOURCE.CREER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"MODIFIER\",\"vectorCode\":\"IAM.RESSOURCE.MODIFIER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true},{\"id\":null,\"code\":\"SUPPRIMER\",\"vectorCode\":\"IAM.RESSOURCE.SUPPRIMER\",\"borneInf\":null,\"borneSup\":null,\"valeur\":0,\"enfants\":null,\"profondeur\":2,\"node\":true}],\"profondeur\":1,\"node\":true}],\"profondeur\":0,\"node\":true},\"ressourceValues\":{}}],\"descriptionShort\":\"Gestion des Habilitation\"}";
    private String jsonTreeResult="[{\"id\":\"-10000\",\"parent\":\"#\",\"text\":\"IAM\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9999\",\"parent\":\"-10000\",\"text\":\"ACCESS\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9998\",\"parent\":\"-10000\",\"text\":\"APPLICATION\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9997\",\"parent\":\"-9998\",\"text\":\"LISTER\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9996\",\"parent\":\"-9998\",\"text\":\"CREER\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9995\",\"parent\":\"-9998\",\"text\":\"MODIFIER\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9994\",\"parent\":\"-9998\",\"text\":\"SUPPRIMER\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9993\",\"parent\":\"-9998\",\"text\":\"UPLOADER\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9992\",\"parent\":\"-10000\",\"text\":\"ROLE\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9991\",\"parent\":\"-9992\",\"text\":\"LISTER\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9990\",\"parent\":\"-9992\",\"text\":\"CREER\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9989\",\"parent\":\"-9992\",\"text\":\"MODIFIER\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9988\",\"parent\":\"-9992\",\"text\":\"SUPPRIMER\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9987\",\"parent\":\"-9992\",\"text\":\"AFFECTER\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9986\",\"parent\":\"-10000\",\"text\":\"RESSOURCE\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9985\",\"parent\":\"-9986\",\"text\":\"LISTER\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9984\",\"parent\":\"-9986\",\"text\":\"CREER\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9983\",\"parent\":\"-9986\",\"text\":\"MODIFIER\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}},{\"id\":\"-9982\",\"parent\":\"-9986\",\"text\":\"SUPPRIMER\",\"icon\":\"fa fa-folder\",\"state\":{\"opened\":true,\"selected\":false,\"disabled\":false}}]";
    
    @Before
    public void setUp() throws Exception {
        is = Thread.currentThread().getContextClassLoader().getResourceAsStream("xls_template/IAM_Rescue.xls");
        tested = new ServiceExcelInOutImpl();
        ((ServiceExcelInOutImpl)tested).setServiceIamRessource(new ServiceIamRessourceImpl());
        
        mocked = PowerMockito.spy(new ServiceExcelInOutImpl());
    }

    
    @Test
    public void testGenerate() {
        try {
            //load existing config
            IamApplication iamApp = tested.parse(is);
            Assert.assertNotNull("Application", iamApp);
            
            PowerMockito.when(mocked, 
                        MemberMatcher.method(ServiceExcelInOutImpl.class, "generateTempXlsFileName"))
                        .withNoArguments()
                        .thenReturn("d:/tmp/IamTestFile.xls");

            Workbook template = mocked.getTemplateSource();
            Assert.assertNotNull("template", template);
            
            WritableWorkbook workbook = mocked.getWritableTemplate(template, null);
            Assert.assertNotNull("workbook", workbook);
            tested.generate(iamApp, workbook);
        
            
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testParse() {
        try {
            IamApplication iamApp = null;
            iamApp = tested.parse(is);
            Assert.assertNotNull("Application", iamApp);
            
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream("net-expat.xls");
            iamApp = tested.parse(is);
            Assert.assertNotNull("Application", iamApp);
             
            
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
    }
    
    @Test 
    public void serialiseTest() {
        try {
            IamApplication iamApp = tested.parse(is);
            Assert.assertNotNull("Application", iamApp);
            
            
            ObjectMapper om = new ObjectMapper();

            /*
            JaxbAnnotationModule module = new JaxbAnnotationModule();
            om.registerModule(module);
            */
            
            String jsonTested = om.writeValueAsString(iamApp); 
            Assert.assertNotNull("Application", jsonTested);
            Assert.assertEquals("serialisation", jsonResult, jsonTested);
            
            List<JsTreeResource> jsTreeRess = JsTreeWrapper.map(iamApp.getRessources(), null);
            String jsonTreeTested = om.writeValueAsString(jsTreeRess);
            Assert.assertEquals("serialisation", jsonTreeResult, jsonTreeTested);
            
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
    }

}
