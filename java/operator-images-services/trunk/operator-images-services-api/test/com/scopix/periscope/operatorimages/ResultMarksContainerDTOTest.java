package com.scopix.periscope.operatorimages;

import java.util.ArrayList;
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
public class ResultMarksContainerDTOTest {
    
    public ResultMarksContainerDTOTest() {
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
     * Test of getResults method, of class ResultMarksContainer.
     */
    @Test
    public void testGetResults() {
        //Instancia clase a probar
        ResultMarksContainerDTO resultMarksContainer = new ResultMarksContainerDTO();
        //Crea objeto para inyección de dependencia
        List<ResultMarksDTO> results = new ArrayList<ResultMarksDTO>();
        resultMarksContainer.setResults(results);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", resultMarksContainer.getResults());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", results, resultMarksContainer.getResults());
    }
}