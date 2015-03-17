package com.scopix.periscope.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Clase de pruebas de com.scopix.periscope.model.Camara
 * 
 * @author Carlos
 */
public class CamaraTest {
    
    public CamaraTest() {
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
    public void testGetName() {
        //Crea instancia de la clase a probar
        Camara camara = new Camara();
        //Se inyecta dependencia
        String name = "name";
        camara.setName(name);

        Assert.assertNotNull(camara.getName());
        Assert.assertEquals(name, camara.getName());
    }

    @Test
    public void testGetEvidencePath() {
        //Crea instancia de la clase a probar
        Camara camara = new Camara();
        //Se inyecta dependencia
        String evidencePath = "http://domain/evidence";
        camara.setEvidencePath(evidencePath);

        Assert.assertNotNull(camara.getEvidencePath());
        Assert.assertEquals(evidencePath, camara.getEvidencePath());
    }

    @Test
    public void testGetEvidenceType() {
        //Crea instancia de la clase a probar
        Camara camara = new Camara();
        //Se inyecta dependencia
        String evidenceTipe = "http://domain/evidence";
        camara.setEvidenceType(evidenceTipe);

        Assert.assertNotNull(camara.getEvidenceType());
        Assert.assertEquals(evidenceTipe, camara.getEvidenceType());
    }

    @Test
    public void testGetTemplatePath() {
        //Crea instancia de la clase a probar
        Camara camara = new Camara();
        //Se inyecta dependencia
        String templatePath = "http://domain/evidence";
        camara.setTemplatePath(templatePath);

        Assert.assertNotNull(camara.getTemplatePath());
        Assert.assertEquals(templatePath, camara.getTemplatePath());
    }
    
    @Test
    public void testGetId() {
        //Crea instancia de la clase a probar
        Camara camara = new Camara();
        //Se inyecta dependencia
        Integer id = 0;
        camara.setId(id);

        Assert.assertNotNull(camara.getId());
        Assert.assertEquals(id, camara.getId());
    }
}