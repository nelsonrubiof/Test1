package com.scopix.periscope.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Clase para pruebas unitarias de la clase que almacena atributos de evidencias
 * 
 * @author    carlos polo
 * @version   1.0.0
 * @since     6.0
 * @date      12/02/2013
 * @copyright Scopix
 */
public class EvidenceTest {
    
    public EvidenceTest() {
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
    public void testGetEvidenceId() {
        int evidenceId = 1;
        Evidence evidence = new Evidence();
        evidence.setEvidenceId(evidenceId);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(evidence.getEvidenceId());
        Assert.assertEquals(evidenceId, evidence.getEvidenceId());
    }

    @Test
    public void testGetProofPath() {
        String proofPath = "x/xx";
        Evidence evidence = new Evidence();
        evidence.setProofPath(proofPath);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(evidence.getProofPath());
        Assert.assertEquals(proofPath, evidence.getProofPath());
    }

    @Test
    public void testGetEvidencePath() {
        String evidencePath = "x/xx";
        Evidence evidence = new Evidence();
        evidence.setEvidencePath(evidencePath);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(evidence.getEvidencePath());
        Assert.assertEquals(evidencePath, evidence.getEvidencePath());
    }

    @Test
    public void testGetTemplatePath() {
        String templatePath = "x/xx";
        Evidence evidence = new Evidence();
        evidence.setTemplatePath(templatePath);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(evidence.getTemplatePath());
        Assert.assertEquals(templatePath, evidence.getTemplatePath());
    }

    @Test
    public void testGetEvidenceType() {
        Evidence evidence = new Evidence();
        String evidenceType = "IMAGE";
        //Inyecta dependencia
        evidence.setEvidenceType(evidenceType);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(evidence.getEvidenceType());
        Assert.assertEquals(evidenceType, evidence.getEvidenceType());
    }

    @Test
    public void testGetEvidenceProvider() {
        Evidence evidence = new Evidence();
        //Crea mock object
        EvidenceProvider provider = Mockito.mock(EvidenceProvider.class);
        //Inyecta dependencia
        evidence.setEvidenceProvider(provider);
        Assert.assertNotNull(evidence.getEvidenceProvider());
        Assert.assertEquals(provider, evidence.getEvidenceProvider());
    }
}