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
 *  ImageUtilisTest.java
 * 
 *  Created on 07-05-2013, 09:59:37 AM
 * 
 */
package com.scopix.periscope.operatorimages;

import java.awt.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nelson
 */
public class ImageUtilisTest {

    public ImageUtilisTest() {
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
    public void testInitClase() {
        ImageUtilis imageUtilis = new ImageUtilis();
        Assert.assertNotNull("Se espera que ImageUtilis este inicialado ", imageUtilis);
    }

    @Test
    public void testGetColorFromName() {
        String colorName = "YELLOW";
        Color expResult = Color.YELLOW;
        Color result = ImageUtilis.getColorFromName(colorName);
        Assert.assertEquals("Se espera que el color resultante sea YELLOW por solicitud de ese color ", expResult.getRGB(),
                result.getRGB());
    }

    @Test
    public void testGetColorFromName2() {
        String colorName = "RED";
        Color expResult = Color.RED;
        Color result = ImageUtilis.getColorFromName(colorName);
        Assert.assertEquals("e espera que el color resultante sea RED por solicitud de ese color ", expResult.getRGB(),
                result.getRGB());
    }

    @Test
    public void testGetColorFromName3() {
        String colorName = "Morado";
        Color result = ImageUtilis.getColorFromName(colorName);
        Assert.assertNull("Se espera null debido a que colo Morado no existe como field de clase Color", result);
    }

    @Test
    public void testGetColorFromRGB() {
        String argb = "FFFFD800";
        Color expResult = new Color(255, 216, 0, 255);
        Color result = ImageUtilis.getColorFromRGB(argb);
        Assert.assertEquals("Se espera Color FFFFD800 en formato rgb -10240", expResult.getRGB(), result.getRGB());
    }

    @Test
    public void testGetColorFromRGB2() {
        String argb = "FFFF4FB8";
        Color expResult = new Color(255, 79, 184, 255);
        Color result = ImageUtilis.getColorFromRGB(argb);
        Assert.assertEquals("Se espera Color FFFFD800 en formato rgb -45128", expResult.getRGB(), result.getRGB());
    }
}