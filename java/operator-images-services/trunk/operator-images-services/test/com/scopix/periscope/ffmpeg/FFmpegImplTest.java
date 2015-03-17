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
 *  FFmpegImplTest.java
 * 
 *  Created on 07-05-2013, 09:59:31 AM
 * 
 */
package com.scopix.periscope.ffmpeg;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author nelson
 */
public class FFmpegImplTest {

    private static File imageTest;

    public FFmpegImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
        //se ejecuta al final de todos los test
        try {
            System.out.println("FFmpegImplTest borrando imageTest " + imageTest);
            FileUtils.forceDelete(imageTest);
        } catch (IOException e) {
            System.out.println("FFmpegImplTest tearDownClass error borrando file : " + e.toString());
        }
    }

    @Before
    public void setUp() {
        if (imageTest == null) {
            try {
                InputStream in = getClass().getClassLoader().getResourceAsStream("testImagen.jpg");
                imageTest = File.createTempFile("ffmpeg", ".jpg");//Aqui le dan el nombre y/o con la ruta del archivo salida
                imageTest.deleteOnExit();
                OutputStream salida = new FileOutputStream(imageTest);
                byte[] buf = new byte[1024];//Actualizado me olvide del 1024
                int len;
                while ((len = in.read(buf)) > 0) {
                    salida.write(buf, 0, len);
                }
                salida.close();
                in.close();
            } catch (IOException e) {
                System.out.println("Error generando imagen temporal");
            }
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetFfmpegProcessBuilder() {
        FFmpegImpl instance = new FFmpegImpl("");
        FFmpegProcessBuilder result = instance.getProcessBuilder();
        Assert.assertNotNull("Revisando que FFmpeg Process Builder no sea null", result);
    }

    @Test
    public void testSetFfmpegProcessBuilder() {
        FFmpegProcessBuilder ffmpegProcessBuilder = Mockito.mock(FFmpegProcessBuilder.class, "FFmpegProcessBuilder vacio");
        FFmpegImpl instance = new FFmpegImpl("");
        instance.setProcessBuilder(ffmpegProcessBuilder);
        FFmpegProcessBuilder result = instance.getProcessBuilder();
        Assert.assertEquals("Se espera que el resultado sea un FFmpegProcessBuilder MOCK", ffmpegProcessBuilder, result);

    }

    @Test
    public void testGetSnapshot() {
        try {
            String fileName = "test.avi";
            Double seconds = 1D;

            FFmpegImpl instance = new FFmpegImpl("");
            FFmpegProcessBuilder builder = Mockito.mock(FFmpegProcessBuilder.class, "FFmpegProcessBuilder vacio");

            Process p = Mockito.mock(Process.class, "Process Simulacion de Ejecucion ");
            InputStream is = Mockito.mock(InputStream.class, "InputStream vacio retorno de Process");

            Mockito.when(builder.start()).thenReturn(p);
            Mockito.when(p.getInputStream()).thenReturn(is);

            instance.setProcessBuilder(builder);

            BufferedImage expResult = null;

            BufferedImage result = instance.getSnapshot(fileName, seconds);
            Assert.fail("Se espera excepcion por no existir data");
        } catch (ScopixException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGetSnapshot2() {
        try {
            String fileName = "test.avi";
            Double seconds = 1D;
            FFmpegImpl instance = new FFmpegImpl("");

            FFmpegProcessBuilder builder = Mockito.mock(FFmpegProcessBuilder.class, "FFmpegProcessBuilder vacio");

            Process p = Mockito.mock(Process.class, "Process vacio retornado por builder");
            InputStream is = new ByteArrayInputStream("TEST \n sdasd".getBytes());

            Mockito.when(builder.start()).thenReturn(p);
            Mockito.when(p.getInputStream()).thenReturn(is);

            instance.setProcessBuilder(builder);

            BufferedImage expResult = null;
            BufferedImage result = instance.getSnapshot(fileName, seconds);
            Assert.assertEquals("Se espera null por no tener file valido", expResult, result);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGetSnapshot3() {
        try {
            String fileName = "test.avi";
            Double seconds = 1D;
            FFmpegImpl instance = new FFmpegImpl("");

            FFmpegProcessBuilder builder = Mockito.mock(FFmpegProcessBuilder.class);

            Mockito.when(builder.start()).thenReturn(null);
            instance.setProcessBuilder(builder);

            BufferedImage expResult = null;
            BufferedImage result = instance.getSnapshot(fileName, seconds);
            Assert.assertEquals("Se espera null por Process Null ", expResult, result);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGetPathExec() {
        FFmpegImpl instance = new FFmpegImpl("");
        String expResult = "";
        String result = instance.getPathExec();
        Assert.assertEquals("Se espera vacio por inicializacion de FFmpegImpl sin path de ffmpeg ", expResult, result);

    }

    @Test
    public void testSetProcessBuilder() {
        FFmpegImpl instance = new FFmpegImpl("");
        FFmpegProcessBuilder processBuilder = Mockito.mock(FFmpegProcessBuilder.class, "FFmpegProcessBuilder vacio para set");
        instance.setProcessBuilder(processBuilder);

        FFmpegProcessBuilder result = instance.getProcessBuilder();
        Assert.assertEquals("Se espera que el result sea igual a mock de FFmpegProcessBuilder", processBuilder, result);
    }

    @Test
    public void testReadTempFile() throws Exception {
        try {
            FFmpegImpl instance = new FFmpegImpl("");
            File fileMock = Mockito.mock(File.class);
            instance.setTmpFile(fileMock);
            Mockito.when(fileMock.getAbsolutePath()).thenReturn("");
            BufferedImage result = instance.readTempFile(fileMock.getAbsolutePath());
            Assert.fail("Debe generar excepcion");
        } catch (IOException e) {
            Assert.assertTrue("Se espera Exception IOException por que file no puede ser leido o no existe", true);
        }
    }

    @Test
    public void testReadTempFile2() throws Exception {
        try {
            FFmpegImpl instance = new FFmpegImpl("");
            instance.setTmpFile(imageTest);
            BufferedImage result = instance.readTempFile(imageTest.getAbsolutePath());
            Assert.assertNotNull("Se espera retorno de BufferedImage de archivo de test", result);
        } catch (IOException e) {
            Assert.fail("No debe generar Excepcion " + e);
        }
    }
}