package com.scopix.periscope.operatorimages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class MarksDTOTest {
    
    public MarksDTOTest() {
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
     * Test of getCircles method, of class Marks.
     */
    @Test
    public void testGetCircles() {
        //Instancia clase a probar
        MarksDTO marks = new MarksDTO();
        //Crea objeto para inyección de dependencia
        List<ShapesDTO> circles = new ArrayList<ShapesDTO>();
        marks.setCircles(circles);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", marks.getCircles());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", circles, marks.getCircles());
    }
    
    /**
     * Test of getCircles method, of class Marks.
     */
    @Test
    public void testGetCircles2() {
        //Instancia clase a probar
        MarksDTO marks = new MarksDTO();
        //Valida que retorne instancia del atributo
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", marks.getCircles());
    }

    /**
     * Test of getSquares method, of class Marks.
     */
    @Test
    public void testGetSquares() {
        //Instancia clase a probar
        MarksDTO marks = new MarksDTO();
        //Crea objeto para inyección de dependencia
        List<ShapesDTO> squares = new ArrayList<ShapesDTO>();
        marks.setSquares(squares);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", marks.getSquares());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", squares, marks.getSquares());
    }
    
    /**
     * Test of getSquares method, of class Marks.
     */
    @Test
    public void testGetSquares2() {
        //Instancia clase a probar
        MarksDTO marks = new MarksDTO();
        //Valida que retorne instancia del atributo
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", marks.getSquares());
    }

    /**
     * Test of getMetricId method, of class Marks.
     */
    @Test
    public void testGetMetricId() {
        //Instancia clase a probar
        MarksDTO marks = new MarksDTO();
        //Crea objeto para inyección de dependencia
        Integer metricId = 1;
        marks.setMetricId(metricId);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", marks.getMetricId());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", metricId, marks.getMetricId());
    }

    /**
     * Test of getEvidenceId method, of class Marks.
     */
    @Test
    public void testGetEvidenceId() {
        //Instancia clase a probar
        MarksDTO marks = new MarksDTO();
        //Crea objeto para inyección de dependencia
        Integer evidenceId = 1;
        marks.setEvidenceId(evidenceId);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", marks.getEvidenceId());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", evidenceId, marks.getEvidenceId());
    }

    /**
     * Test of getEvidenceDate method, of class Marks.
     */
    @Test
    public void testGetEvidenceDate() {
        //Instancia clase a probar
        MarksDTO marks = new MarksDTO();
        //Crea objeto para inyección de dependencia
        String evidenceDate = "2013-01-01 10:00:00";
        marks.setEvidenceDate(evidenceDate);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", marks.getEvidenceDate());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", evidenceDate, marks.getEvidenceDate());
    }

    /**
     * Test of getPathOrigen method, of class Marks.
     */
    @Test
    public void testGetPathOrigen() {
        //Instancia clase a probar
        MarksDTO marks = new MarksDTO();
        //Crea objeto para inyección de dependencia
        String pathOrigen = "path";
        marks.setPathOrigen(pathOrigen);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", marks.getPathOrigen());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", pathOrigen, marks.getPathOrigen());
    }

    /**
     * Test of getPathDestino method, of class Marks.
     */
    @Test
    public void testGetPathDestino() {
        //Instancia clase a probar
        MarksDTO marks = new MarksDTO();
        //Crea objeto para inyección de dependencia
        String pathDestino = "path";
        marks.setPathDestino(pathDestino);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", marks.getPathDestino());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", pathDestino, marks.getPathDestino());
    }

    /**
     * Test of getElapsedTime method, of class Marks.
     */
    @Test
    public void testGetElapsedTime() {
        //Instancia clase a probar
        MarksDTO marks = new MarksDTO();
        //Crea objeto para inyección de dependencia
        Double elapsedTime = 1D;
        marks.setElapsedTime(elapsedTime);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", marks.getElapsedTime());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", elapsedTime, marks.getElapsedTime());
    }

    /**
     * Test of getResult method, of class Marks.
     */
    @Test
    public void testGetResult() {
        //Instancia clase a probar
        MarksDTO marks = new MarksDTO();
        //Crea objeto para inyección de dependencia
        Integer result = 1;
        marks.setResult(result);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", marks.getResult());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", result, marks.getResult());
    }
}