/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.operatorimages;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author nelson
 */
public class DeleteContainerDTOTest {

    public DeleteContainerDTOTest() {
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

    @Test
    public void testGetPathOrigen() {
        DeleteContainerDTO instance = new DeleteContainerDTO();
        String expResult = "";
        instance.setPathOrigen("");
        String result = instance.getPathOrigen();
        Assert.assertEquals("Se espera null ya que no se ha inicialido", expResult, result);
    }

    @Test
    public void testGetList() {
        DeleteContainerDTO instance = new DeleteContainerDTO();
        List<String> expResult = new ArrayList<String>();
        instance.setList(expResult);
        List result = instance.getList();
        Assert.assertEquals("Se espera que sea una lista vacia", expResult, result);
    }

    @Test
    public void testGetList2() {
        DeleteContainerDTO instance = new DeleteContainerDTO();
        List result = instance.getList();
        Assert.assertNotNull("Se espera que sea una lista vacia", result);
    }
}