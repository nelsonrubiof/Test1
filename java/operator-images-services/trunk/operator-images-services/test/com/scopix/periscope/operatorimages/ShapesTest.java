/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  ShapesTest.java
 * 
 *  Created on 07-06-2013, 12:14:48 PM
 * 
 */
package com.scopix.periscope.operatorimages;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nelson
 */
public class ShapesTest {

    public ShapesTest() {
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
    public void testGetPositionX() {
        Shapes instance = new Shapes();
        Integer expResult = null;
        instance.setPositionX(expResult);
        Integer result = instance.getPositionX();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetPositionY() {
        Shapes instance = new Shapes();
        Integer expResult = null;
        instance.setPositionY(expResult);
        Integer result = instance.getPositionY();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetWidth() {
        Shapes instance = new Shapes();
        Integer expResult = null;
        instance.setWidth(expResult);
        Integer result = instance.getWidth();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetHeight() {
        Shapes instance = new Shapes();
        Integer expResult = null;
        instance.setHeight(expResult);
        Integer result = instance.getHeight();
        assertEquals(expResult, result);
    }


    @Test
    public void testGetColor() {
        Shapes instance = new Shapes();
        String expResult = "";
        instance.setColor(expResult);
        String result = instance.getColor();
        assertEquals(expResult, result);
    }

}