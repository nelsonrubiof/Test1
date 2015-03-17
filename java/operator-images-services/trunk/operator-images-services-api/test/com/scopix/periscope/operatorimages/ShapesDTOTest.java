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
public class ShapesDTOTest {
    
    public ShapesDTOTest() {
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
     * Test of getPositionX method, of class Shapes.
     */
    @Test
    public void testGetPositionX() {
        //Instancia clase a probar
        ShapesDTO shapes = new ShapesDTO();
        //Crea objeto para inyección de dependencia
        Integer positionX = 1;
        shapes.setPositionX(positionX);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", shapes.getPositionX());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", positionX, shapes.getPositionX());
    }

    /**
     * Test of getPositionY method, of class Shapes.
     */
    @Test
    public void testGetPositionY() {
        //Instancia clase a probar
        ShapesDTO shapes = new ShapesDTO();
        //Crea objeto para inyección de dependencia
        Integer positionY = 1;
        shapes.setPositionY(positionY);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", shapes.getPositionY());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", positionY, shapes.getPositionY());
    }

    /**
     * Test of getWidth method, of class Shapes.
     */
    @Test
    public void testGetWidth() {
        //Instancia clase a probar
        ShapesDTO shapes = new ShapesDTO();
        //Crea objeto para inyección de dependencia
        Integer width = 1;
        shapes.setWidth(width);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", shapes.getWidth());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", width, shapes.getWidth());
    }

    /**
     * Test of getHeight method, of class Shapes.
     */
    @Test
    public void testGetHeight() {
        //Instancia clase a probar
        ShapesDTO shapes = new ShapesDTO();
        //Crea objeto para inyección de dependencia
        Integer height = 1;
        shapes.setHeight(height);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", shapes.getHeight());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", height, shapes.getHeight());
    }

    /**
     * Test of getColor method, of class Shapes.
     */
    @Test
    public void testGetColor() {
        //Instancia clase a probar
        ShapesDTO shapes = new ShapesDTO();
        //Crea objeto para inyección de dependencia
        String color = "color";
        shapes.setColor(color);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", shapes.getColor());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", color, shapes.getColor());
    }
}