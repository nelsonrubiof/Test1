package com.scopix.periscope.operatorimages;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Carlos
 */
public class ResultMarksDTOTest {
    
    public ResultMarksDTOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getEvidenceId method, of class ResultMarks.
     */
    @Test
    public void testGetEvidenceId() {
        //Instancia clase a probar
        ResultMarksDTO resultMarks = new ResultMarksDTO();
        //Crea objeto para inyección de dependencia
        Integer evidenceId = 1;
        resultMarks.setEvidenceId(evidenceId);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", resultMarks.getEvidenceId());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", evidenceId, resultMarks.getEvidenceId());
    }

    /**
     * Test of getMetricId method, of class ResultMarks.
     */
    @Test
    public void testGetMetricId() {
        //Instancia clase a probar
        ResultMarksDTO resultMarks = new ResultMarksDTO();
        //Crea objeto para inyección de dependencia
        Integer metricId = 1;
        resultMarks.setMetricId(metricId);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", resultMarks.getMetricId());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", metricId, resultMarks.getMetricId());
    }

    /**
     * Test of getFileName method, of class ResultMarks.
     */
    @Test
    public void testGetFileName() {
        //Instancia clase a probar
        ResultMarksDTO resultMarks = new ResultMarksDTO();
        //Crea objeto para inyección de dependencia
        String fileName = "fileName";
        resultMarks.setFileName(fileName);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", resultMarks.getFileName());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", fileName, resultMarks.getFileName());
    }
}