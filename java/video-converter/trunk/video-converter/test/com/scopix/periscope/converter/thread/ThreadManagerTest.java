/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * 
 * ThreadManagerTest.java
 * 
 * Created on 16-08-2013, 12:38:09 PM
 */
package com.scopix.periscope.converter.thread;

import java.util.concurrent.ExecutorService;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.scopix.periscope.converter.ffmpeg.FFMPEGImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 *
 * @author carlos polo
 */
public class ThreadManagerTest {

    public ThreadManagerTest() {
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
     * Test of convertVideo method, of class ThreadManager.
     */
    @Test
    public void testConvertVideo() throws ScopixException {
        // Crea instancia de la clase a probar
        ThreadManager threadManager = new ThreadManager();
        // Crea mock objects
        ExecutorService executor = Mockito.mock(ExecutorService.class);
        PropertiesConfiguration systConfiguration = Mockito.mock(PropertiesConfiguration.class);
        // Inyecta dependencias
        threadManager.setExecutor(executor);
        threadManager.setSystConfiguration(systConfiguration);
        // Define comportamientos
        Mockito.when(systConfiguration.getString("ffmpeg.path")).thenReturn("ffmpef.exe");
        Mockito.when(systConfiguration.getString("originales.path")).thenReturn("D:/prueba");
        Mockito.when(systConfiguration.getString("convertidos.path")).thenReturn("D:/prueba2");
        Mockito.when(systConfiguration.getString("framerate.value")).thenReturn("4");
        Mockito.when(systConfiguration.getString("keyframes.value")).thenReturn("10");
        Mockito.when(systConfiguration.getString("bitrate.value")).thenReturn("160000");
        Mockito.when(systConfiguration.getString("conversion.format")).thenReturn(".mp4");
        Mockito.when(systConfiguration.getString("pixelformat.value")).thenReturn("yuv420p");
        // Invoca método a probar
        threadManager.convertVideo(null, "video.avi", "", 1);
        // Verifica ejecución
        Mockito.verify(executor).execute(Mockito.any(FFMPEGImpl.class));
        // Mockito.verify(executor).shutdown();
    }

    /**
     * Test of getSystConfiguration method, of class ThreadManager.
     */
    @Test
    public void testGetSystConfiguration() {
        // Crea instancia de la clase a probar
        ThreadManager threadManager = new ThreadManager();
        // Crea mock objects
        PropertiesConfiguration systConfiguration = Mockito.mock(PropertiesConfiguration.class);
        // Inyecta dependencias
        threadManager.setSystConfiguration(systConfiguration);
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", threadManager.getSystConfiguration());
        Assert.assertEquals("Se espera que el atributo sea el mismo " + "objeto asignado", systConfiguration,
                threadManager.getSystConfiguration());
    }

    /**
     * Test of getSystConfiguration method, of class ThreadManager.
     */
    @Test
    public void testGetSystConfiguration2() {
        // Crea instancia de la clase a probar
        ThreadManager threadManager = new ThreadManager();
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", threadManager.getSystConfiguration());
    }
}