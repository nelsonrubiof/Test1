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
public class MarksContainerDTOTest {
    
    public MarksContainerDTOTest() {
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
     * Test of getMarks method, of class MarksContainer.
     */
    @Test
    public void testGetMarks() {
        //Instancia clase a probar
        MarksContainerDTO marksContainer = new MarksContainerDTO();
        //Crea objeto para inyección de dependencia
        List<MarksDTO> marks = new ArrayList<MarksDTO>();
        marksContainer.setMarks(marks);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", marksContainer.getMarks());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", marks, marksContainer.getMarks());
    }
}