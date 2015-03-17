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
 *  XugglerImplTest.java
 * 
 *  Created on 07-05-2013, 09:59:32 AM
 * 
 */
package com.scopix.periscope.xuggler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
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
public class XugglerImplTest {

    private static File videoTest;
    private static File imageTest;

    public XugglerImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
        //se ejecuta al final de todos los test
        try {
            System.out.println("XugglerImplTest borrando videoTest " + videoTest);
            FileUtils.forceDelete(videoTest);
            System.out.println("XugglerImplTest borrando imageTest " + imageTest);
            FileUtils.forceDelete(imageTest);
        } catch (IOException e) {
            System.out.println("XugglerImplTest tearDownClass error borrando file : " + e.toString());
        }
    }

    @Before
    public void setUp() {
        //se ejecuta por cada test
        if (videoTest == null) {
            try {
                InputStream in = getClass().getClassLoader().getResourceAsStream("testVideo.avi");
                videoTest = File.createTempFile("ffmpeg", ".avi");//Aqui le dan el nombre y/o con la ruta del archivo salida
                videoTest.deleteOnExit();
                OutputStream salida = new FileOutputStream(videoTest);
                byte[] buf = new byte[1024];//Actualizado me olvide del 1024
                int len;
                while ((len = in.read(buf)) > 0) {
                    salida.write(buf, 0, len);
                }
                salida.close();
                in.close();

                in = getClass().getClassLoader().getResourceAsStream("testImagen.jpg");
                imageTest = File.createTempFile("ffmpeg", ".jpg");//Aqui le dan el nombre y/o con la ruta del archivo salida
                imageTest.deleteOnExit();
                salida = new FileOutputStream(imageTest);

                buf = new byte[1024];//Actualizado me olvide del 1024
                while ((len = in.read(buf)) > 0) {
                    salida.write(buf, 0, len);
                }
                salida.close();
                in.close();

            } catch (IOException e) {
                System.out.println("Se produjo el error : " + e.toString());
            }
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetSprite() {
        String inputFilename = "";
        int spriteNum = 0;
        XugglerImpl instance = new XugglerImpl();
        BufferedImage result = instance.getSprite(inputFilename, spriteNum);
        Assert.assertNull("Se espera null, parametros no validos [inputFilename:" + inputFilename + "]"
                + "[spriteNum:" + spriteNum + "]", result);
    }

    @Test
    public void testGetSprite2() {
        String inputFilename = videoTest.getAbsolutePath();
        int spriteNum = 1;
        XugglerImpl instance = new XugglerImpl();
        BufferedImage result = instance.getSprite(inputFilename, spriteNum);
        Assert.assertNull("Se espera null, parametros no validos [inputFilename:" + inputFilename + "]"
                + "[spriteNum:" + spriteNum + "]", result);
    }

    @Test
    public void testGetVideoDuration() {
        String fileName = "";
        XugglerImpl instance = new XugglerImpl();

        Long result = instance.getVideoDuration(fileName);
        Assert.assertEquals("Se espera retorno 0 para video null", new Long(0), result);
    }

    @Test
    public void testGetVideoDuration2() {
        String fileName = videoTest.getAbsolutePath();
        XugglerImpl instance = new XugglerImpl();

        Long result = instance.getVideoDuration(fileName);
        Assert.assertEquals("Se espera respuesta igual al largo de video de test", new Long(1), result);
    }

    @Test
    public void testDumpImage() {
        try {
            XugglerImpl instance = new XugglerImpl();
            BufferedImage originalImage = Mockito.mock(BufferedImage.class, "BufferedImage vacio");
            Mockito.when(originalImage.getType()).thenReturn(0);

            BufferedImage result = instance.dumpImage(originalImage);
            Assert.fail("debe generar una excepcion originalImage imcompleta");
        } catch (Exception e) {
            Assert.assertTrue("Se espera excepcion por mock incompleto", true);
        }
    }

    @Test
    public void testDumpImage2() {
        try {
            XugglerImpl instance = new XugglerImpl();
            BufferedImage originalImage = Mockito.mock(BufferedImage.class, "BufferedImage vacio");
            Mockito.when(originalImage.getType()).thenReturn(BufferedImage.TYPE_INT_RGB);

            BufferedImage result = instance.dumpImage(originalImage);
            Assert.fail("debe generar una excepcion originalImage imcompleta");
        } catch (Exception e) {
            Assert.assertTrue("Se espera excepcion por mock incompleto", true);
        }
    }

    @Test
    public void testDumpImage3() {
        XugglerImpl instance = new XugglerImpl();
        BufferedImage originalImage = new BufferedImage(640, 480, BufferedImage.TYPE_INT_BGR);

        BufferedImage result = instance.dumpImage(originalImage);
        Assert.assertNotNull("Se espera BufferedImage valido para una imagen de test", result);
    }

    @Test
    public void testCreateSpriteImages() {
        XugglerImpl instance = new XugglerImpl();
        List<BufferedImage> snapshots = Mockito.mock(List.class, "Lista de BufferedImage");
        BufferedImage originalImage = new BufferedImage(640, 480, BufferedImage.TYPE_INT_BGR);
        Iterator<BufferedImage> iteratorSnapshot = Mockito.mock(Iterator.class, "Iterator para lista");


        Mockito.when(snapshots.isEmpty()).thenReturn(Boolean.FALSE);
        Mockito.when(snapshots.iterator()).thenReturn(iteratorSnapshot);
        Mockito.when(iteratorSnapshot.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iteratorSnapshot.next()).thenReturn(originalImage);

        List result = instance.createSpriteImages(snapshots);
        Assert.assertNotNull("Se espera que retorne no null con lista de snapshot distinta de vacia", result);
        Assert.assertEquals("Se espera que lista de retorno contenga 1 elemento", 1, result.size());

    }

    @Test
    public void testCreateSpriteImages2() {
        XugglerImpl instance = new XugglerImpl();
        List<BufferedImage> snapshots = Mockito.mock(List.class, "Lista de BufferedImage");
        BufferedImage originalImage = new BufferedImage(640, 480, BufferedImage.TYPE_INT_BGR);
        Iterator<BufferedImage> iteratorSnapshot = Mockito.mock(Iterator.class, "Iterator para lista");

        Mockito.when(snapshots.isEmpty()).thenReturn(Boolean.FALSE);
        Mockito.when(snapshots.iterator()).thenReturn(iteratorSnapshot);
        Mockito.when(iteratorSnapshot.hasNext()).thenReturn(true, true, true, true, true, true, false);
        Mockito.when(iteratorSnapshot.next()).thenReturn(originalImage);//

        List result = instance.createSpriteImages(snapshots);
        Assert.assertNotNull("Se espera que retorne no null con lista de snapshot distinta de vacia", result);
        Assert.assertEquals("Se espera que lista de retorno contenga 1 elemento", 1, result.size());

    }

    @Test
    public void testCreateSpriteImages3() {
        XugglerImpl instance = new XugglerImpl();
        List<BufferedImage> snapshots = Mockito.mock(List.class, "Lista de BufferedImage");
        BufferedImage originalImage = new BufferedImage(640, 480, BufferedImage.TYPE_INT_BGR);
        Iterator<BufferedImage> iteratorSnapshot = Mockito.mock(Iterator.class, "Iterator para lista");

        Mockito.when(snapshots.isEmpty()).thenReturn(Boolean.FALSE);
        Mockito.when(snapshots.iterator()).thenReturn(iteratorSnapshot);
        Mockito.when(iteratorSnapshot.hasNext()).thenReturn(true, true, true, true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
                false);
        Mockito.when(iteratorSnapshot.next()).thenReturn(originalImage);//

        List result = instance.createSpriteImages(snapshots);
        Assert.assertNotNull("Se espera que retorne no null con lista de snapshot distinta de vacia", result);
        Assert.assertEquals("Se espera que lista de retorno contenga 2 elementos", 2, result.size());

    }

    @Test
    public void testCreateSpriteImages4() {
        XugglerImpl instance = new XugglerImpl();
        List<BufferedImage> snapshots = Mockito.mock(List.class, "Lista de BufferedImage");
        BufferedImage originalImage = Mockito.mock(BufferedImage.class, "BufferedImage vacio");
        Iterator<BufferedImage> iteratorSnapshot = Mockito.mock(Iterator.class, "Iterator para lista");

        Mockito.when(snapshots.isEmpty()).thenReturn(Boolean.FALSE);
        Mockito.when(snapshots.iterator()).thenReturn(iteratorSnapshot);
        Mockito.when(iteratorSnapshot.hasNext()).thenReturn(true, true, true, true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
                false);
        Mockito.when(iteratorSnapshot.next()).thenReturn(originalImage);//

        List result = instance.createSpriteImages(snapshots);
        Assert.assertNotNull("Se espera que retorne no null con lista de snapshot distinta de vacia", result);
        Assert.assertEquals("Se espera que lista de retorno no contenga elementos", 0, result.size());
    }
}