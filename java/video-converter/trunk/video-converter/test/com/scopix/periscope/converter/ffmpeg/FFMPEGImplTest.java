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
 *  FFMPEGImplTest.java
 * 
 *  Created on 16-08-2013, 12:38:07 PM
 * 
 */
package com.scopix.periscope.converter.ffmpeg;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author carlos polo
 */
public class FFMPEGImplTest {
    
    public FFMPEGImplTest() {
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
     * Test of run method, of class FFMPEGImpl.
     */
    @Test
    public void testRun() throws Exception {
        //Crea instancia de la clase a probar
        FFMPEGImpl ffmpegImpl = new FFMPEGImpl();
        //Crea mock objects
        FFMPEGProcessBuilder ffmpegProcBuilder = Mockito.mock(FFMPEGProcessBuilder.class);
        //Inyecta dependencias
        ffmpegImpl.setFrameRateValue("4");
        ffmpegImpl.setKeyFramesValue("10");
        ffmpegImpl.setBitRateValue("160000");
        ffmpegImpl.setVideoOriginal("xxxx.avi");
        ffmpegImpl.setComandoEjecucion("ffmpeg");
        ffmpegImpl.setVideoConvertido("xxx.mp4");
        ffmpegImpl.setPixelFormatValue("yuv420p");
        ffmpegImpl.setFfmpegProcessBuilder(ffmpegProcBuilder);
        //Define comportamientos
        Mockito.when(ffmpegProcBuilder.start()).thenReturn(null);
        //Invoca método a probar
        ffmpegImpl.run();
        //Verifica ejecución
        Mockito.verify(ffmpegProcBuilder).start();
    }

    /**
     * Test of convertVideo method, of class FFMPEGImpl.
     */
    @Test
    public void testConvertVideo() throws Exception {
        //Crea instancia de la clase a probar
        FFMPEGImpl ffmpegImpl = new FFMPEGImpl();
        //Crea mock objects
        FFMPEGProcessBuilder ffmpegProcBuilder = Mockito.mock(FFMPEGProcessBuilder.class);
        //Inyecta dependencias
        ffmpegImpl.setFrameRateValue("4");
        ffmpegImpl.setKeyFramesValue("10");
        ffmpegImpl.setBitRateValue("160000");
        ffmpegImpl.setVideoOriginal("xxxx.avi");
        ffmpegImpl.setComandoEjecucion("ffmpeg");
        ffmpegImpl.setVideoConvertido("xxx.mp4");
        ffmpegImpl.setPixelFormatValue("yuv420p");
        ffmpegImpl.setFfmpegProcessBuilder(ffmpegProcBuilder);
        //Define comportamientos
        Mockito.when(ffmpegProcBuilder.start()).thenReturn(null);
        //Invoca método a probar
        ffmpegImpl.convertVideo();
        //Verifica ejecución
        Mockito.verify(ffmpegProcBuilder).start();
    }
    
    /**
     * Test of convertVideo method, of class FFMPEGImpl.
     */
    @Test
    public void testConvertVideo2() {
        //Crea instancia de la clase a probar
        FFMPEGImpl ffmpegImpl = new FFMPEGImpl();
        //Crea mock objects
        Process process = Mockito.mock(Process.class);
        InputStream errorStream = Mockito.mock(InputStream.class);
        OutputStream outputStream = Mockito.mock(OutputStream.class);
        InputStream inputStream = new ByteArrayInputStream("TEST \n sdasd".getBytes());
        FFMPEGProcessBuilder ffmpegProcBuilder = Mockito.mock(FFMPEGProcessBuilder.class);
        //Inyecta dependencias
        ffmpegImpl.setFrameRateValue("4");
        ffmpegImpl.setKeyFramesValue("10");
        ffmpegImpl.setBitRateValue("160000");
        ffmpegImpl.setVideoOriginal("xxxx.avi");
        ffmpegImpl.setComandoEjecucion("ffmpeg");
        ffmpegImpl.setVideoConvertido("xxx.mp4");
        ffmpegImpl.setPixelFormatValue("yuv420p");
        ffmpegImpl.setFfmpegProcessBuilder(ffmpegProcBuilder);
        try {
            //Define comportamientos
            Mockito.when(ffmpegProcBuilder.start()).thenReturn(process);
            Mockito.when(process.getInputStream()).thenReturn(inputStream);
            Mockito.when(process.getErrorStream()).thenReturn(errorStream);
            Mockito.when(process.getOutputStream()).thenReturn(outputStream);
            //Invoca método a probar
            //ffmpegImpl.convertVideo();
            //Verifica ejecución
//            Mockito.verify(process, Mockito.atLeastOnce()).getInputStream();
//            Mockito.verify(process).getErrorStream();
//            Mockito.verify(ffmpegProcBuilder).start();
//            Mockito.verify(process).getOutputStream();
        } catch (ScopixException ex) {
            Assert.assertNotNull(ex);
        }
    }
    
    /**
     * Test of executeProcess method, of class FFMPEGImpl.
     */
    @Test
    public void testExecuteProcess() throws ScopixException {
        //Crea instancia de la clase a probar
        FFMPEGImpl ffmpegImpl = new FFMPEGImpl();
        //Crea mock objects
        FFMPEGProcessBuilder ffmpegProcBuilder = Mockito.mock(FFMPEGProcessBuilder.class);
        //Inyecta dependencias
        ffmpegImpl.setFfmpegProcessBuilder(ffmpegProcBuilder);
        //Define comportamientos
        Mockito.when(ffmpegProcBuilder.start()).thenReturn(null);
        //Verifica ejecución
        Assert.assertNull("Se espera que el objeto retornado sea nulo", ffmpegImpl.executeProcess());
    }
    
    /**
     * Test of executeProcess method, of class FFMPEGImpl.
     */
    @Test
    public void testExecuteProcess2() {
        //Crea instancia de la clase a probar
        FFMPEGImpl ffmpegImpl = new FFMPEGImpl();
        //Crea mock objects
        Process process = Mockito.mock(Process.class);
        InputStream inputStream = new ByteArrayInputStream("TEST \n sdasd".getBytes());
        FFMPEGProcessBuilder ffmpegProcBuilder = Mockito.mock(FFMPEGProcessBuilder.class);
        //Inyecta dependencias
        ffmpegImpl.setVideoConvertido("xxx.mp4");
        ffmpegImpl.setFfmpegProcessBuilder(ffmpegProcBuilder);
        try {
            //Define comportamientos
            Mockito.when(ffmpegProcBuilder.start()).thenReturn(process);
            Mockito.when(process.getInputStream()).thenReturn(inputStream);
            //Verifica ejecución
            //Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", ffmpegImpl.executeProcess());
        } catch (ScopixException ex) {
            Assert.assertNotNull(ex);
        }
    }

    /**
     * Test of getBitRateValue method, of class FFMPEGImpl.
     */
    @Test
    public void testGetBitRateValue() {
        //Crea instancia de la clase a probar
        FFMPEGImpl ffmpegImpl = new FFMPEGImpl();
        //Crea objeto para inyección de dependencia
        String bitRateValue = "value";
        ffmpegImpl.setBitRateValue(bitRateValue);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", ffmpegImpl.getBitRateValue());
        Assert.assertEquals("Se espera que el atributo sea el mismo "
                + "objeto asignado", bitRateValue, ffmpegImpl.getBitRateValue());
    }

    /**
     * Test of getFrameRateValue method, of class FFMPEGImpl.
     */
    @Test
    public void testGetFrameRateValue() {
        //Instancia clase a probar
        FFMPEGImpl ffmpegImpl = new FFMPEGImpl();
        //Crea objeto para inyección de dependencia
        String frameRateValue = "value";
        ffmpegImpl.setFrameRateValue(frameRateValue);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", ffmpegImpl.getFrameRateValue());
        Assert.assertEquals("Se espera que el atributo sea el mismo "
                + "objeto asignado", frameRateValue, ffmpegImpl.getFrameRateValue());
    }

    /**
     * Test of getKeyFramesValue method, of class FFMPEGImpl.
     */
    @Test
    public void testGetKeyFramesValue() {
        //Instancia clase a probar
        FFMPEGImpl ffmpegImpl = new FFMPEGImpl();
        //Crea objeto para inyección de dependencia
        String keyFramesValue = "value";
        ffmpegImpl.setKeyFramesValue(keyFramesValue);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", ffmpegImpl.getKeyFramesValue());
        Assert.assertEquals("Se espera que el atributo sea el mismo "
                + "objeto asignado", keyFramesValue, ffmpegImpl.getKeyFramesValue());
    }

    /**
     * Test of getPixelFormatValue method, of class FFMPEGImpl.
     */
    @Test
    public void testGetPixelFormatValue() {
        //Instancia clase a probar
        FFMPEGImpl ffmpegImpl = new FFMPEGImpl();
        //Crea objeto para inyección de dependencia
        String pixelFormatValue = "value";
        ffmpegImpl.setPixelFormatValue(pixelFormatValue);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", ffmpegImpl.getPixelFormatValue());
        Assert.assertEquals("Se espera que el atributo sea el mismo "
                + "objeto asignado", pixelFormatValue, ffmpegImpl.getPixelFormatValue());
    }

    /**
     * Test of getFfmpegProcessBuilder method, of class FFMPEGImpl.
     */
    @Test
    public void testGetFfmpegProcessBuilder() {
        //Instancia clase a probar
        FFMPEGImpl ffmpegImpl = new FFMPEGImpl();
        //Crea objeto para inyección de dependencia
        FFMPEGProcessBuilder ffmpegProcBuilder = new FFMPEGProcessBuilder();
        ffmpegImpl.setFfmpegProcessBuilder(ffmpegProcBuilder);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", ffmpegImpl.getFfmpegProcessBuilder());
        Assert.assertEquals("Se espera que el atributo sea el mismo "
                + "objeto asignado", ffmpegProcBuilder, ffmpegImpl.getFfmpegProcessBuilder());
    }
    
    /**
     * Test of getFfmpegProcessBuilder method, of class FFMPEGImpl.
     */
    @Test
    public void testGetFfmpegProcessBuilder2() {
        //Instancia clase a probar
        FFMPEGImpl ffmpegImpl = new FFMPEGImpl();
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", ffmpegImpl.getFfmpegProcessBuilder());
    }

    /**
     * Test of getComandoEjecucion method, of class FFMPEGImpl.
     */
    @Test
    public void testGetComandoEjecucion() {
        //Instancia clase a probar
        FFMPEGImpl ffmpegImpl = new FFMPEGImpl();
        //Crea objeto para inyección de dependencia
        String comandoEjecucion = "comando";
        ffmpegImpl.setComandoEjecucion(comandoEjecucion);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", ffmpegImpl.getComandoEjecucion());
        Assert.assertEquals("Se espera que el atributo sea el mismo "
                + "objeto asignado", comandoEjecucion, ffmpegImpl.getComandoEjecucion());
    }
    
    /**
     * Test of getVideoConvertido method, of class FFMPEGImpl.
     */
    @Test
    public void testGetVideoConvertido() {
        //Instancia clase a probar
        FFMPEGImpl ffmpegImpl = new FFMPEGImpl();
        //Crea objeto para inyección de dependencia
        String videoConvertido = "xxx.mp4";
        ffmpegImpl.setVideoConvertido(videoConvertido);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", ffmpegImpl.getVideoConvertido());
        Assert.assertEquals("Se espera que el atributo sea el mismo "
                + "objeto asignado", videoConvertido, ffmpegImpl.getVideoConvertido());
    }
    
    /**
     * Test of getVideoOriginal method, of class FFMPEGImpl.
     */
    @Test
    public void testGetVideoOriginal() {
        //Instancia clase a probar
        FFMPEGImpl ffmpegImpl = new FFMPEGImpl();
        //Crea objeto para inyección de dependencia
        String videoOriginal = "xxx.avi";
        ffmpegImpl.setVideoOriginal(videoOriginal);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", ffmpegImpl.getVideoOriginal());
        Assert.assertEquals("Se espera que el atributo sea el mismo "
                + "objeto asignado", videoOriginal, ffmpegImpl.getVideoOriginal());
    }
}