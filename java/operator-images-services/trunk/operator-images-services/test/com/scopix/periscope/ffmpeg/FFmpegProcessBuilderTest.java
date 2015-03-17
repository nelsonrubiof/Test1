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
 *  FFmpegProcessBuilderTest.java
 * 
 *  Created on 14-05-2013, 10:19:35 AM
 * 
 */
package com.scopix.periscope.ffmpeg;

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
public class FFmpegProcessBuilderTest {

    public FFmpegProcessBuilderTest() {
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
    public void testStart() {
        try {
            FFmpegProcessBuilder instance = new FFmpegProcessBuilder();
            Process result = instance.start();
            Assert.fail("No debe continar no tiene parametros");
        } catch (Exception e) {
            Assert.assertTrue("Se espera excepcion por tener los parametros de comando y ejecucion en null",true);
        }
    }

    @Test
    public void testStart2() {
        FFmpegProcessBuilder instance = new FFmpegProcessBuilder();
        String[] param = new String[]{""};
        instance.setParam(param);
        Process result = instance.start();
        Assert.assertNull("Se espera null por tener los parametros de comando y ejecucion vacios" ,result);
    }

    @Test
    public void testGetParam() {
        FFmpegProcessBuilder instance = new FFmpegProcessBuilder();
        String[] result = instance.getParam();
        Assert.assertNull("Se espera null por inicializacion de paramtros nulos", result);
    }

    @Test
    public void testSetParam() {
        String[] param = null;
        FFmpegProcessBuilder instance = new FFmpegProcessBuilder();
        instance.setParam(param);
        String[] result = instance.getParam();
        Assert.assertArrayEquals("Se espera null por setear parametros null a FFmpegProcessBuilder",param, result);
    }

    @Test
    public void testSetParam2() {        
        String[] param = new String[]{""};
        FFmpegProcessBuilder instance = new FFmpegProcessBuilder();
        instance.setParam(param);
        String[] result = instance.getParam();
        Assert.assertArrayEquals("Se espera un arreglo con un parametro en blanco ", param, result);
    }
}