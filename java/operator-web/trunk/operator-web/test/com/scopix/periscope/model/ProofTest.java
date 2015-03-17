package com.scopix.periscope.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Clase para pruebas unitarias de la clase que almacena atributos de Proof
 * 
 * @author    carlos polo
 * @version   1.0.0
 * @since     6.0
 * @date      09/05/2013
 * @copyright Scopix
 */
public class ProofTest {
    
    public ProofTest() {
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
        Integer evidenceId = 0;
        Proof proof = new Proof();
        proof.setEvidenceId(evidenceId);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(proof.getEvidenceId());
        Assert.assertEquals(evidenceId, proof.getEvidenceId());
    }

    @Test
    public void testGetPathWithMarks() {
        String pathWithMarks = "path";
        Proof proof = new Proof();
        proof.setPathWithMarks(pathWithMarks);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(proof.getPathWithMarks());
        Assert.assertEquals(pathWithMarks, proof.getPathWithMarks());
    }

    @Test
    public void testGetPathWithOutMarks() {
        String pathWithoutMarks = "path";
        Proof proof = new Proof();
        proof.setPathWithOutMarks(pathWithoutMarks);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(proof.getPathWithOutMarks());
        Assert.assertEquals(pathWithoutMarks, proof.getPathWithOutMarks());
    }
}