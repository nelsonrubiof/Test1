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
 *  FFMPEGProcessBuilderTest.java
 * 
 *  Created on 16-08-2013, 12:38:07 PM
 * 
 */
package com.scopix.periscope.converter.ffmpeg;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author carlos polo
 */
public class FFMPEGProcessBuilderTest {
    
    public FFMPEGProcessBuilderTest() {
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
     * Test of start method, of class FFMPEGProcessBuilder.
     */
    @Test
    public void testStart() {
        //Instancia clase a probar
        FFMPEGProcessBuilder ffmpegProcBuilder = new FFMPEGProcessBuilder();
        //Crea objeto para inyección de dependencia
        String[] params = new String[]{"ffmpeg.exe", "-i", "video1.flv", "-b:v", 
            "160000", "-r", "4", "-g", "1", "-pix_fmt", "yuv420p", "video1.mp4"};

        ffmpegProcBuilder.setParams(params);
        try {
            //Invoca método a probar
            ffmpegProcBuilder.start();
        } catch (ScopixException ex) {
            Assert.assertNotNull(ex);
        }
    }

    /**
     * Test of getParams method, of class FFMPEGProcessBuilder.
     */
    @Test
    public void testGetParams() {
        //Instancia clase a probar
        FFMPEGProcessBuilder ffmpegProcBuilder = new FFMPEGProcessBuilder();
        //Crea objeto para inyección de dependencia
        String[] params = new String[]{"ffmpeg", "-i", "video1.flv", "-b:v", 
            "160000", "-r", "4", "-g", "1", "-pix_fmt", "yuv420p", "video1.mp4"};

        ffmpegProcBuilder.setParams(params);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", ffmpegProcBuilder.getParams());
        Assert.assertEquals("Se espera que el atributo sea el mismo "
                + "objeto asignado", params, ffmpegProcBuilder.getParams());
    }
}