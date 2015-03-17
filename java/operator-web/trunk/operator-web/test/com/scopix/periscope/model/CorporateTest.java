package com.scopix.periscope.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Clase de pruebas de com.scopix.periscope.model.Corporate
 * 
 * @author Carlos
 */
public class CorporateTest {
    
    public CorporateTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetId() {
        //Crea instancia de la clase a probar
        Corporate corporate = new Corporate();
        //Se inyecta dependencia
        String id = "id";
        corporate.setId(id);

        Assert.assertNotNull(corporate.getId());
        Assert.assertEquals(id, corporate.getId());
    }

    @Test
    public void testGetName() {
        //Crea instancia de la clase a probar
        Corporate corporate = new Corporate();
        //Se inyecta dependencia
        String name = "name";
        corporate.setName(name);

        Assert.assertNotNull(corporate.getName());
        Assert.assertEquals(name, corporate.getName());
    }

    @Test
    public void testGetDescription() {
        //Crea instancia de la clase a probar
        Corporate corporate = new Corporate();
        //Se inyecta dependencia
        String description = "description";
        corporate.setDescription(description);

        Assert.assertNotNull(corporate.getDescription());
        Assert.assertEquals(description, corporate.getDescription());
    }

    @Test
    public void testGetEvidenceUrl() {
        //Crea instancia de la clase a probar
        Corporate corporate = new Corporate();
        //Se inyecta dependencia
        String evidenceURL = "URL";
        corporate.setEvidenceUrl(evidenceURL);

        Assert.assertNotNull(corporate.getEvidenceUrl());
        Assert.assertEquals(evidenceURL, corporate.getEvidenceUrl());
    }

    @Test
    public void testGetTemplateUrl() {
        //Crea instancia de la clase a probar
        Corporate corporate = new Corporate();
        //Se inyecta dependencia
        String templateURL = "URL";
        corporate.setTemplateUrl(templateURL);

        Assert.assertNotNull(corporate.getTemplateUrl());
        Assert.assertEquals(templateURL, corporate.getTemplateUrl());
    }
    
    @Test
    public void testGetOperatorImgServicesURL() {
        //Crea instancia de la clase a probar
        Corporate corporate = new Corporate();
        //Se inyecta dependencia
        String operatorSrvImgUrl = "URL";
        corporate.setOperatorImgServicesURL(operatorSrvImgUrl);

        Assert.assertNotNull(corporate.getOperatorImgServicesURL());
        Assert.assertEquals(operatorSrvImgUrl, corporate.getOperatorImgServicesURL());
    }
    
    @Test
    public void testGetProofPath() {
        //Crea instancia de la clase a probar
        Corporate corporate = new Corporate();
        //Se inyecta dependencia
        String proofPath = "path";
        corporate.setProofPath(proofPath);

        Assert.assertNotNull(corporate.getProofPath());
        Assert.assertEquals(proofPath, corporate.getProofPath());
    }
}