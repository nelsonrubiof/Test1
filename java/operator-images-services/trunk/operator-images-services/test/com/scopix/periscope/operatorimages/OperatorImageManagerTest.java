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
 *  OperatorImageManagerTest.java
 * 
 *  Created on 07-05-2013, 09:59:34 AM
 * 
 */
package com.scopix.periscope.operatorimages;

import com.scopix.periscope.ffmpeg.FFmpegImpl;
import com.scopix.periscope.ffmpeg.FFmpegProcessBuilder;
import static com.scopix.periscope.operatorimages.OperatorImageManager.SEPARATOR_NAME;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
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
public class OperatorImageManagerTest {

    private static File videoTest;
    private static File imageTest;

    public OperatorImageManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        //se ejecuta al comienzo de todos los test
    }

    @AfterClass
    public static void tearDownClass() {
        //se ejecuta al final de todos los test
        try {
            System.out.println("OperatorImageManagerTest borrando videoTest " + videoTest);
            FileUtils.forceDelete(videoTest);
            System.out.println("OperatorImageManagerTest borrando imageTest " + imageTest);
            FileUtils.forceDelete(imageTest);
        } catch (IOException e) {
            System.out.println("OperatorImageManagerTest tearDownClass error borrando file : " + e.toString());
        }
    }

    @Before
    public void setUp() {
        //se ejecuta por cada test
        if (videoTest == null) {
            try {
                InputStream in = getClass().getClassLoader().getResourceAsStream("testVideo.avi");
                videoTest = File.createTempFile("ffmpeg", ".avi");
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
                imageTest = File.createTempFile("ffmpeg", ".jpg");
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
        //se ejecuta por cada test
    }

    @Test
    public void testGetSnapshot_3args() {
        try {
            OperatorImageManager instance = new OperatorImageManager();
            Double elapsedTime = 1D;
            String fileName = videoTest.getAbsolutePath();
            HttpServletResponse response = Mockito.mock(HttpServletResponse.class, "HttpServletResponse vacio");
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class, "ServletOutputStream retorno para response");
            FFmpegImpl ffmpegImpl = Mockito.mock(FFmpegImpl.class, "FFmpegImpl vacio");
            FFmpegProcessBuilder processBuilder = Mockito.mock(FFmpegProcessBuilder.class, "FFmpegProcessBuilder vacio");
            Process p = Mockito.mock(Process.class, "Process vacio");
            Mockito.when(ffmpegImpl.getProcessBuilder()).thenReturn(processBuilder);
            Mockito.when(processBuilder.start()).thenReturn(p);
            InputStream is = new ByteArrayInputStream("TEST \n sdasd".getBytes());
            Mockito.when(p.getInputStream()).thenReturn(is);

            instance.setfFmpeg(ffmpegImpl);

            Mockito.when(response.getOutputStream()).thenReturn(out);

            OutputStream result = instance.getSnapshot(elapsedTime, fileName, response);

            Assert.assertNotNull("Se espera un OutputStream con datos para los parametros [elapsedTime:" + elapsedTime + "]"
                    + "[fileName:" + fileName + "][response:" + response + "]", result);
        } catch (IOException e) {
            Assert.fail("ERROR." + e);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGetSnapshot_3args2() {
        try {
            OperatorImageManager instance = new OperatorImageManager();
            Double elapsedTime = 0D;
            String fileName = "";
            HttpServletResponse response = Mockito.mock(HttpServletResponse.class, "HttpServletResponse vacio");
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class, "ServletOutputStream retorno para response");

            Mockito.when(response.getOutputStream()).thenReturn(out);

            OutputStream result = instance.getSnapshot(elapsedTime, fileName, response);

            Assert.assertNotNull("Se espera que retorne un OutputStream no nulo en cualquier caso, "
                    + "se prueba la no existencia de archivo avi para recuperar imagen", result);
        } catch (IOException e) {
            Assert.fail("ERROR." + e);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGetSnapshot_3args3() {
        try {
            OperatorImageManager instance = new OperatorImageManager();
            Double elapsedTime = 1D;
            String fileName = videoTest.getAbsolutePath();
            HttpServletResponse response = Mockito.mock(HttpServletResponse.class, "HttpServletResponse vacio");
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class, "ServletOutputStream retorno para response");

            Mockito.when(response.getOutputStream()).thenReturn(out);
            /**
             * para poder generara un snapshot
             */
            FFmpegImpl ffmpegImpl = new FFmpegImpl("");
            FFmpegImpl ffmpegImplMock = Mockito.spy(ffmpegImpl);
            FFmpegProcessBuilder processBuilder = Mockito.mock(FFmpegProcessBuilder.class, "FFmpegProcessBuilder vacio");
            Process p = Mockito.mock(Process.class, "Process vacio para ejecucion de FFmpegProcessBuilder");
            Mockito.when(ffmpegImplMock.getProcessBuilder()).thenReturn(processBuilder);
            Mockito.when(processBuilder.start()).thenReturn(p);
            InputStream is = new ByteArrayInputStream("TEST \n sdasd".getBytes());
            Mockito.when(p.getInputStream()).thenReturn(is);
            File f2 = File.createTempFile("test", ".jpeg");
            f2.deleteOnExit();
            FileUtils.copyFile(imageTest, f2);
            Mockito.when(ffmpegImplMock.getTmpFile()).thenReturn(f2);

            instance.setfFmpeg(ffmpegImplMock);

            OutputStream result = instance.getSnapshot(elapsedTime, fileName, response);

            Assert.assertNotNull("Se espera que retorne un OutputStream no nulo en cualquier caso, "
                    + "se prueba que la generacion del snapshot sea para una copia de archivo jpg de test", result);
        } catch (IOException e) {
            Assert.fail("ERROR." + e);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGetSnapshot_String_HttpServletResponse() {
        try {
            OperatorImageManager instance = new OperatorImageManager();
            String fileName = "";
            HttpServletResponse response = Mockito.mock(HttpServletResponse.class, "HttpServletResponse vacio");
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class, "ServletOutputStream retorno para response");

            Mockito.when(response.getOutputStream()).thenReturn(out);

            OutputStream result = instance.getSnapshot(fileName, response);
            Assert.assertNotNull("Se espera que retorne un OutputStream no nulo en cualquier caso, "
                    + "se prueba que los parametros esten vacios ", result);
        } catch (IOException e) {
            Assert.fail("ERROR." + e);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGetTemplate() {
        String fileName = "";
        OperatorImageManager instance = new OperatorImageManager();
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        OutputStream result = instance.getTemplate(fileName, response);
        Assert.assertNull("Se espera Null para caso de no enviar archivo a mostrar", result);
    }

    @Test
    public void testGetTemplate2() {
        try {
            String fileName = imageTest.getAbsolutePath();
            OperatorImageManager instance = new OperatorImageManager();
            HttpServletResponse response = Mockito.mock(HttpServletResponse.class, "HttpServletResponse vacio");
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class, "ServletOutputStream retorno para response");

            Mockito.when(response.getOutputStream()).thenReturn(out);

            OutputStream result = instance.getTemplate(fileName, response);
            Assert.assertNotNull("Se espera un outputstrean que representa a archivo de test", result);
        } catch (IOException e) {
            Assert.fail("ERROR." + e);
        }
    }

    @Test
    public void testGetImage() {
        String fileName = "";

        OperatorImageManager instance = new OperatorImageManager();
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class, "HttpServletResponse vacio");

        OutputStream result = instance.getMediaFile(fileName, response);
        Assert.assertNull("Se espera Null para caso de no enviar archivo a mostrar", result);
    }

    @Test
    public void testGetImage2() {
        try {
            String fileName = imageTest.getAbsolutePath();

            OperatorImageManager instance = new OperatorImageManager();
            HttpServletResponse response = Mockito.mock(HttpServletResponse.class, "HttpServletResponse vacio");
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class, "ServletOutputStream retorno para response");

            Mockito.when(response.getOutputStream()).thenReturn(out);

            OutputStream result = instance.getMediaFile(fileName, response);
            Assert.assertNotNull("Se espera un outputstrean que representa a archivo de test", result);
        } catch (IOException e) {
            Assert.fail("ERROR." + e);
        }
    }

    @Test
    public void testGenerateMarksInImage() {
        OperatorImageManager instance = new OperatorImageManager();
        BufferedImage snapshot = null;
        Marks marcas = null;
        String pathBase = "";
        String result = null;
        try {
            result = instance.generateMarksInImage(snapshot, marcas, pathBase);
            Assert.assertNull("Se espera name null si no se envian los datos necesarios", result);
        } catch (ScopixException e) {
            Assert.fail("Se esperaba null");
        }
        
    }

    @Test
    public void testGenerateMarksInImage2() {
        OperatorImageManager instance = new OperatorImageManager();
        String pathBase = "";
        BufferedImage snapshot = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);

        Marks marcas = Mockito.mock(Marks.class, "Marks vacias solo retorna la fecha");
        Mockito.when(marcas.getEvidenceDate()).thenReturn(new Date());

        try {
            String result = instance.generateMarksInImage(snapshot, marcas, pathBase);
            Assert.assertNotNull("Se espera generacion un nombre dados los parametros entregados [snapshot:" + snapshot + "]"
                    + "[marcas:" + marcas + "][pathBase:" + pathBase + "]", result);
        } catch (ScopixException e) {
            Assert.fail("Se esperaba name");
        }
        try {
            FileUtils.forceDelete(new File(pathBase + "/proofs"));
            FileUtils.forceDelete(new File(pathBase + "/proofs_with_marks"));
        } catch (IOException e) {
            System.out.println("Error borrando archivos test " + e);
        }
    }

    @Test
    public void testGenerateMarksInImage3() {
        OperatorImageManager instance = new OperatorImageManager();
        String pathBase = "";
        BufferedImage snapshot = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);

        Marks marcas = Mockito.mock(Marks.class, "Marks vacio");
        List<Shapes> circles = Mockito.mock(List.class, "Lista de Shapes vacia");
        Iterator<Shapes> iterator = Mockito.mock(Iterator.class, "Iterator para Lista de Shapes");
        Shapes circle = Mockito.mock(Shapes.class, "Shape de circulo para ser retornado por la lista");

        Mockito.when(circles.iterator()).thenReturn(iterator);
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(circle);

        Mockito.when(circle.getColor()).thenReturn("FF000000");
        Mockito.when(circle.getPositionX()).thenReturn(1);
        Mockito.when(circle.getPositionY()).thenReturn(1);
        Mockito.when(circle.getWidth()).thenReturn(10);
        Mockito.when(circle.getWidth()).thenReturn(10);
        Mockito.when(marcas.getEvidenceDate()).thenReturn(new Date());
        Mockito.when(marcas.getCircles()).thenReturn(circles);

        try {
            String result = instance.generateMarksInImage(snapshot, marcas, pathBase);
            Assert.assertNotNull("Se espera la generacion un nombre dados los parametros entregados [snapshot:" + snapshot + "]"
                    + "[marcas:" + marcas + "][pathBase:" + pathBase + "]", result);
        } catch (ScopixException e) {
            Assert.fail("Se esperaba name");
        }
        try {
            FileUtils.forceDelete(new File(pathBase + "/proofs"));
            FileUtils.forceDelete(new File(pathBase + "/proofs_with_marks"));
        } catch (IOException e) {
            System.out.println("Error borrando archivos test " + e);
        }
    }

    @Test
    public void testGenerateMarksInImage4() {
        OperatorImageManager instance = new OperatorImageManager();
        String pathBase = "";
        BufferedImage snapshot = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);

        Marks marcas = Mockito.mock(Marks.class, "Marks vacio");
        List<Shapes> squares = Mockito.mock(List.class, "Lista de Shapes");
        Iterator<Shapes> iterator = Mockito.mock(Iterator.class, "Iterator para recorrer lista de Shapes");
        Shapes square = Mockito.mock(Shapes.class, "Shapes de cuadrao para ser retornado por la lista de shapes");

        Mockito.when(squares.iterator()).thenReturn(iterator);
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(square);
        //agregamos un cuadrado
        Mockito.when(square.getColor()).thenReturn("FF000000");
        Mockito.when(square.getPositionX()).thenReturn(1);
        Mockito.when(square.getPositionY()).thenReturn(1);
        Mockito.when(square.getWidth()).thenReturn(10);
        Mockito.when(square.getWidth()).thenReturn(10);
        Mockito.when(marcas.getEvidenceDate()).thenReturn(new Date());
        Mockito.when(marcas.getSquares()).thenReturn(squares);

        try {
            String result = instance.generateMarksInImage(snapshot, marcas, pathBase);
            Assert.assertNotNull("Se espera la generacion un nombre dados los parametros entregados [snapshot:" + snapshot + "]"
                    + "[marcas:" + marcas + "][pathBase:" + pathBase + "]", result);
        } catch (ScopixException e) {
            Assert.fail("Se esperaba name");
        }
        try {
            FileUtils.forceDelete(new File(pathBase + "/proofs"));
            FileUtils.forceDelete(new File(pathBase + "/proofs_with_marks"));
        } catch (IOException e) {
            System.out.println("Error borrando archivos test " + e);
        }
    }

    @Test
    public void testGenerateProofFromMovie() {
        try {
            String fileName = "";
            Double elapsedTime = null;
            Marks marcas = null;
            String pathBase = "";
            OperatorImageManager instance = new OperatorImageManager();
            FFmpegImpl ffmpegImpl = Mockito.mock(FFmpegImpl.class, "FFmpegImpl vacio");
            FFmpegProcessBuilder processBuilder = Mockito.mock(FFmpegProcessBuilder.class, "FFmpegProcessBuilder vacio");
            Process p = Mockito.mock(Process.class, "Process vacio devuelto por FFmpegProcessBuilder");
            Mockito.when(ffmpegImpl.getProcessBuilder()).thenReturn(processBuilder);
            Mockito.when(processBuilder.start()).thenReturn(p);
            InputStream is = new ByteArrayInputStream("TEST \n sdasd".getBytes());
            Mockito.when(p.getInputStream()).thenReturn(is);
            Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
            instance.setfFmpeg(ffmpegImpl);

            String result = instance.generateProofFromMovie(fileName, elapsedTime, marcas, pathBase, map);
            Assert.assertNull("Se espera null, por no porder crear snapshot asociado", result);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGenerateProofFromMovie2() {
        try {
            String fileName = videoTest.getAbsolutePath();
            Double elapsedTime = 1D;
            Marks marcas = null;
            String pathBase = "";
            OperatorImageManager instance = new OperatorImageManager();
            FFmpegImpl ffmpegImpl = Mockito.mock(FFmpegImpl.class, "FFmpegImpl vacio");
            FFmpegProcessBuilder processBuilder = Mockito.mock(FFmpegProcessBuilder.class, "FFmpegProcessBuilder vacio ");
            Process p = Mockito.mock(Process.class, "Process vacio para ser retornado por FFmpegProcessBuilder");
            Mockito.when(ffmpegImpl.getProcessBuilder()).thenReturn(processBuilder);
            Mockito.when(processBuilder.start()).thenReturn(p);
            InputStream is = new ByteArrayInputStream("TEST \n sdasd".getBytes());
            Mockito.when(p.getInputStream()).thenReturn(is);
            Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
            instance.setfFmpeg(ffmpegImpl);

            String result = instance.generateProofFromMovie(fileName, elapsedTime, marcas, pathBase, map);
            Assert.assertNull("Se espera null ya que no existen marcas ", result);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGenerateProofFromMovie3() {
        try {
            String fileName = videoTest.getAbsolutePath();
            Double elapsedTime = 1D;
            Marks marcas = Mockito.mock(Marks.class, "Marks vacio solo con fecha de evidencia");
            Mockito.when(marcas.getEvidenceDate()).thenReturn(new Date());
            String pathBase = "";
            OperatorImageManager instance = new OperatorImageManager();
            //se espera un resultados ya que existen marcas con almenos la fecha
            FFmpegImpl ffmpegImpl = new FFmpegImpl("");
            FFmpegProcessBuilder processBuilder = Mockito.mock(FFmpegProcessBuilder.class,
                    "FFmpegProcessBuilder vacio para ser injectado a FFmpegImpl");
            InputStream is = new ByteArrayInputStream("TEST \n sdasd".getBytes());
            Process p = Mockito.mock(Process.class, "Process vacio para ser devuelto por FFmpegProcessBuilder");
            ffmpegImpl.setProcessBuilder(processBuilder);
            Mockito.when(processBuilder.start()).thenReturn(p);
            Mockito.when(p.getInputStream()).thenReturn(is);
            instance.setfFmpeg(ffmpegImpl);
            Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();

            String result = instance.generateProofFromMovie(fileName, elapsedTime, marcas, pathBase, map);
            Assert.assertNull("Se espera null ya que no se tiene acceso al comando de snapshot", result);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGenerateProofFromMovie4() {
        try {
            String fileName = videoTest.getAbsolutePath();
            Double elapsedTime = 1D;
            Marks marcas = Mockito.mock(Marks.class, "Marks vacio solo con fecha de evidencia");
            Mockito.when(marcas.getEvidenceDate()).thenReturn(new Date());
            String pathBase = "";
            OperatorImageManager instance = new OperatorImageManager();
            /**
             * para poder generara un snapshot
             */
            FFmpegImpl ffmpegImpl = new FFmpegImpl("");
            FFmpegImpl ffmpegImplMock = Mockito.spy(ffmpegImpl);
            FFmpegProcessBuilder processBuilder = Mockito.mock(FFmpegProcessBuilder.class, "FFmpegProcessBuilder vacio");
            Process p = Mockito.mock(Process.class, "Process vacio para ser retornado por FFmpegProcessBuilder");
            Mockito.when(ffmpegImplMock.getProcessBuilder()).thenReturn(processBuilder);
            Mockito.when(processBuilder.start()).thenReturn(p);
            InputStream is = new ByteArrayInputStream("TEST \n sdasd".getBytes());
            Mockito.when(p.getInputStream()).thenReturn(is);
            File f2 = File.createTempFile("test", ".jpeg");
            f2.deleteOnExit();
            FileUtils.copyFile(imageTest, f2);
            Mockito.when(ffmpegImplMock.getTmpFile()).thenReturn(f2);

            instance.setfFmpeg(ffmpegImplMock);

            Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();

            String result = instance.generateProofFromMovie(fileName, elapsedTime, marcas, pathBase, map);
            Assert.assertNotNull("Se espera un resultados ya que existen marcas con almenos la fecha "
                    + "y una archivo de test en la generacion ", result);
            try {
                FileUtils.forceDelete(new File(pathBase + "/proofs"));
                FileUtils.forceDelete(new File(pathBase + "/proofs_with_marks"));
            } catch (IOException e) {
                System.out.println("Error borrando archivos test " + e);
            }
        } catch (IOException e) {
            Assert.fail("no debe generar excepcion " + e);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGenerateProofFromMovie5() {
        try {
            String fileName = videoTest.getAbsolutePath();
            Double elapsedTime = 1D;
            Marks marcas = Mockito.mock(Marks.class, "Marks vacio solo con fecha de evidencia");
            Mockito.when(marcas.getEvidenceDate()).thenReturn(new Date());
            String pathBase = "";
            OperatorImageManager instance = new OperatorImageManager();
            /**
             * para poder generara un snapshot
             */
            FFmpegImpl ffmpegImpl = new FFmpegImpl("");
            FFmpegImpl ffmpegImplMock = Mockito.spy(ffmpegImpl);
            FFmpegProcessBuilder processBuilder = Mockito.mock(FFmpegProcessBuilder.class, "FFmpegProcessBuilder vacio");
            Process p = Mockito.mock(Process.class, "Process vacio para ser retornado por FFmpegProcessBuilder");
            Mockito.when(ffmpegImplMock.getProcessBuilder()).thenReturn(processBuilder);
            Mockito.when(processBuilder.start()).thenReturn(p);
            InputStream is = new ByteArrayInputStream("TEST \n sdasd".getBytes());
            Mockito.when(p.getInputStream()).thenReturn(is);
            File f2 = File.createTempFile("test", ".jpeg");
            f2.deleteOnExit();
            FileUtils.copyFile(imageTest, f2);

            Mockito.when(ffmpegImplMock.getTmpFile()).thenReturn(f2);

            instance.setfFmpeg(ffmpegImplMock);

            Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
            BufferedImage img = ImageIO.read(f2);
            map.put(FilenameUtils.getName(fileName), img);

            String result = instance.generateProofFromMovie(fileName, elapsedTime, marcas, pathBase, map);
            Assert.assertNotNull("Se espera un resultados ya que existen marcas con almenos la fecha "
                    + "y una archivo de test en la generacion, con una imagen de snapshot generada ", result);
            try {
                FileUtils.forceDelete(new File(pathBase + "/proofs"));
                FileUtils.forceDelete(new File(pathBase + "/proofs_with_marks"));
            } catch (IOException e) {
                System.out.println("Error borrando archivos test " + e);
            }
        } catch (IOException e) {
            Assert.fail("no debe generar excepcion " + e);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGenerateProofFromImage() {
        String fileName = "";
        Marks marcas = null;
        String pathBase = "";
        Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
        OperatorImageManager instance = new OperatorImageManager();
        try {
            String result = instance.generateProofFromImage(fileName, marcas, pathBase, map);
            Assert.assertNull("Se espera null ya que los parametros entregados estan vacios o nulos", result);
        } catch (ScopixException e) {
            Assert.fail("Se esperaba null");
        }
    }

    @Test
    public void testGenerateProofFromImage2() {
        String fileName = imageTest.getAbsolutePath();
        Marks marcas = null;
        String pathBase = "";
        Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
        OperatorImageManager instance = new OperatorImageManager();

        try {
            String result = instance.generateProofFromImage(fileName, marcas, pathBase, map);
            Assert.assertNull("Se espera null, ya que las marcas se envian en null, aunque se le pase un archivo origen", result);
        } catch (ScopixException e) {
            Assert.fail("Se esperaba null");
        }
    }

    @Test
    public void testGenerateProofFromImage3() {
        try {
            String fileName = imageTest.getAbsolutePath();
            Marks marcas = Mockito.mock(Marks.class, "Marks vacio solo con fecha de evidencia");
            Mockito.when(marcas.getEvidenceDate()).thenReturn(new Date());
            String pathBase = "";

            OperatorImageManager instance = new OperatorImageManager();

            // para generar un snapshot fictisio
            File f2 = File.createTempFile("test", ".jpeg");
            f2.deleteOnExit();
            FileUtils.copyFile(imageTest, f2);
            Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
            BufferedImage img = ImageIO.read(f2);
            map.put(FilenameUtils.getName(fileName), img);

            try {
                String result = instance.generateProofFromImage(fileName, marcas, pathBase, map);
                Assert.assertNotNull("Se espera un nombre de file ya que existe archivo y marcas con almenos la fecha", result);
            } catch (ScopixException e) {
                Assert.fail("Se esperaba name");
            }
            try {
                FileUtils.forceDelete(new File(pathBase + "/proofs"));
                FileUtils.forceDelete(new File(pathBase + "/proofs_with_marks"));
            } catch (IOException e) {
                System.out.println("Error borrando archivos test " + e);
            }
        } catch (IOException e) {
            Assert.fail("NO puede tener excepcion " + e);
        }
    }

    @Test
    public void testGenerateProofFromImage4() {
        String fileName = imageTest.getAbsolutePath();
        Marks marcas = Mockito.mock(Marks.class, "Marks vacio solo con fecha de evidencia");
        Mockito.when(marcas.getEvidenceDate()).thenReturn(new Date());
        String pathBase = "";
        Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();

        OperatorImageManager instance = new OperatorImageManager();

        try {
            String result = instance.generateProofFromImage(fileName, marcas, pathBase, map);
            Assert.assertNotNull("Se espera un nombre de file ya que existe archivo y marcas con almenos la fecha", result);
        } catch (ScopixException e) {
            Assert.fail("Se esperaba name");
        }
        try {
            FileUtils.forceDelete(new File(pathBase + "/proofs"));
            FileUtils.forceDelete(new File(pathBase + "/proofs_with_marks"));
        } catch (IOException e) {
            System.out.println("Error borrando archivos test " + e);
        }
    }

    @Test
    public void testGenerateProof_4args() {
        try {
            Double elapsedTime = 1D;
            String fileName = imageTest.getAbsolutePath();
            String pathBase = "";
            Marks marcas = Mockito.mock(Marks.class, "Marks vacio solo con fecha de evidencia");
            Mockito.when(marcas.getEvidenceDate()).thenReturn(new Date());
            Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
            OperatorImageManager instance = new OperatorImageManager();

            String result = instance.generateProof(elapsedTime, fileName, pathBase, marcas, map);

            Assert.assertNotNull("Se espera un nombre de file generado sin marcas", result);
            //se deben borrar los archivos y carpetas generadas por el test
            try {
                FileUtils.forceDelete(new File(pathBase + "/proofs"));
                FileUtils.forceDelete(new File(pathBase + "/proofs_with_marks"));
            } catch (IOException e) {
                System.out.println("Error borrando archivos test");
            }
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGenerateProof_4args2() {
        try {
            Double elapsedTime = null;
            String fileName = "";
            String pathBase = "";
            Marks marcas = null;
            OperatorImageManager instance = new OperatorImageManager();
            FFmpegImpl ffmpegImpl = new FFmpegImpl("");
            FFmpegProcessBuilder processBuilder = Mockito.mock(FFmpegProcessBuilder.class, "FFmpegProcessBuilder vacio");
            InputStream is = new ByteArrayInputStream("TEST \n sdasd".getBytes());
            Process p = Mockito.mock(Process.class, "Process retorno de FFmpegProcessBuilder");
            ffmpegImpl.setProcessBuilder(processBuilder);
            Mockito.when(processBuilder.start()).thenReturn(p);
            Mockito.when(p.getInputStream()).thenReturn(is);
            instance.setfFmpeg(ffmpegImpl);
            Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();

            String result = instance.generateProof(elapsedTime, fileName, pathBase, marcas, map);
            Assert.assertNull("Se espera null, ya que no envian parametros completos [elapsedTime:" + elapsedTime + "]"
                    + "[fileName:" + fileName + "][pathBase:" + pathBase + "][marcas:" + marcas + "]", result);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGenerateProof_List() {
        try {
            OperatorImageManager instance = new OperatorImageManager();

            List<ResultMarks> result = instance.generateProof(new ArrayList<Marks>());
            Assert.assertNotNull("Se espera una lista sin datos, Lista vacia marcas enviadas", result);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGenerateProof_List2() {
        try {
            OperatorImageManager instance = new OperatorImageManager();
            List<Marks> listMarks = Mockito.mock(List.class, "Lista de Marcas");
            Iterator<Marks> iterator = Mockito.mock(Iterator.class, "Iterator para Lista de Marcas");
            Marks marcas = Mockito.mock(Marks.class, "Marca para ser retornado por la lista");

            Mockito.when(listMarks.iterator()).thenReturn(iterator);
            Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
            Mockito.when(iterator.next()).thenReturn(marcas);
            Mockito.when(marcas.getElapsedTime()).thenReturn(1D);

            List<ResultMarks> result = instance.generateProof(listMarks);
//            Assert.assertEquals("Se espera lista con 1 resultado, marcas enviadas solo con segundo para snapshot", 1, 
            //result.size());
//            Assert.assertNull("Se espera null para el nombre del archivo", result.get(0).getFileName());
        } catch (ScopixException e) {
            Assert.assertEquals("Se espera excepcion ", e.getMessage(), "No se puede generar proof:null");
        }
    }

    @Test
    public void testGenerateProof_List3() {
        try {
            OperatorImageManager instance = new OperatorImageManager();
            List<Marks> listMarks = Mockito.mock(List.class, "Lista de Marcas");
            Iterator<Marks> iterator = Mockito.mock(Iterator.class, "Iterator para Lista de Marcas");

            Marks marcas = Mockito.mock(Marks.class, "Marks con video de test");
            Mockito.when(listMarks.iterator()).thenReturn(iterator);
            Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
            Mockito.when(iterator.next()).thenReturn(marcas);
            Mockito.when(marcas.getElapsedTime()).thenReturn(null);
            Mockito.when(marcas.getPathOrigen()).thenReturn(videoTest.getAbsolutePath());
            List<ResultMarks> result = instance.generateProof(listMarks);
//            Assert.assertEquals("Se espera lista con 1 resultado, marcas enviadas solo con segundo para snapshot", 1, 
            //result.size());
//            Assert.assertNull("Se espera null para el nombre del archivo, para video de 1 segundo", result.get(0).
            //getFileName());
        } catch (ScopixException e) {
            Assert.assertEquals("Se espera excepcion ", e.getMessage(), "No se puede generar proof:"
                    + videoTest.getAbsolutePath());
        }
    }

    @Test
    public void testGenerateProof_List4() {
        try {
            OperatorImageManager instance = new OperatorImageManager();
            List<Marks> listMarks = Mockito.mock(List.class, "Lista de Marcas");
            Iterator<Marks> iterator = Mockito.mock(Iterator.class, "Iterator para Lista de Marcas");

            Marks marcas = Mockito.mock(Marks.class, "Marks con imagen y fecha de evidencia");

            Mockito.when(listMarks.iterator()).thenReturn(iterator);
            Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
            Mockito.when(iterator.next()).thenReturn(marcas);

            Mockito.when(marcas.getElapsedTime()).thenReturn(null);
            Mockito.when(marcas.getPathOrigen()).thenReturn(imageTest.getAbsolutePath());
            Mockito.when(marcas.getEvidenceDate()).thenReturn(new Date());
            Mockito.when(marcas.getPathDestino()).thenReturn("");


            List<ResultMarks> result = instance.generateProof(listMarks);
            Assert.assertEquals("Se espera lista con 1 resultado, marcas enviadas solo con segundo para snapshot", 1, result.size());
            Assert.assertNotNull("Se espera nombre de proof, marcas con imagen y fecha de evidencia", result.get(0).getFileName());
            try {
                FileUtils.forceDelete(new File("" + "/proofs"));
                FileUtils.forceDelete(new File("" + "/proofs_with_marks"));
            } catch (IOException e) {
                System.out.println("Error borrando archivos test");
            }
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGenerateUniqueFile() {
        String absolutePath = "";
        String fileName = "";
        String extension = "";
        Integer iCurrent = null;
        String result = OperatorImageManager.generateUniqueFile(absolutePath, fileName, extension, iCurrent);
        Assert.assertNotNull("Se espera un nombre de file, sin envio de datos", result);
    }

    @Test
    public void testGenerateUniqueFile2() {
        String absolutePath = "";
        String fileName = "";
        String extension = "";
        Integer iCurrent = 1;

        String result = OperatorImageManager.generateUniqueFile(absolutePath, fileName, extension, iCurrent);
        Assert.assertNotNull("Se espera un nombre de file, con solo el current actual para un file", result);
    }

    @Test
    public void testGenerateUniqueFile3() {
        try {
            String absolutePath = FilenameUtils.getFullPath(imageTest.getAbsolutePath());
            String fileName = "test";
            String extension = "jpg";
            Integer iCurrent = 0;

            File copy = new File(absolutePath, fileName + SEPARATOR_NAME + 1 + "." + extension);
            FileUtils.copyFile(imageTest, copy);
            String result = OperatorImageManager.generateUniqueFile(absolutePath, fileName, extension, iCurrent);
            Assert.assertNotNull("Se espera un nombre de file, pasando un archivo y generando un file test en la ruta de destino",
                    result);
            try {
                FileUtils.forceDelete(copy);
            } catch (IOException e) {
                System.out.println("Error borrando archivo de copia");
            }
        } catch (IOException e) {
            Assert.fail("No debe generar excepcion");
        }
    }

    @Test
    public void testSetSystConfiguration() {
        OperatorImageManager instance = new OperatorImageManager();
        PropertiesConfiguration systConfiguration = Mockito.mock(PropertiesConfiguration.class, "PropertiesConfiguration vacio");

        instance.setSystConfiguration(systConfiguration);
        PropertiesConfiguration result = instance.getSystConfiguration();
        Assert.assertEquals("Se espera que configuracion sea igual a mock injectado", systConfiguration, result);
    }

    @Test
    public void testSetfFmpeg() {
        OperatorImageManager instance = new OperatorImageManager();
        FFmpegImpl fFmpeg = Mockito.mock(FFmpegImpl.class, "FFmpegImpl vacio");
        instance.setfFmpeg(fFmpeg);
        FFmpegImpl result = instance.getfFmpeg();
        Assert.assertEquals("Se espera que FFmpegImpl sea igual a mock injectado", fFmpeg, result);
    }

    @Test
    public void testMarkInGraphic() {
        try {
            Marks marcas = null;
            Graphics g = null;
            BufferedImage combinadas = null;
            OperatorImageManager instance = new OperatorImageManager();
            instance.markInGraphic(marcas, g, combinadas, true);
            Assert.fail("Se esperaba una excepcion ");
        } catch (Exception e) {
            Assert.assertTrue("Se espera Excepción, se mandan datos en null", true);
        }
    }

    @Test
    public void testMarkInGraphic2() {

        OperatorImageManager instance = new OperatorImageManager();
        Marks marcas = Mockito.mock(Marks.class, "Marks vacio");
        BufferedImage combinadas = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        Graphics g = combinadas.getGraphics();
        instance.markInGraphic(marcas, g, combinadas, true);
        Assert.assertTrue("Se espera que se generen las marcas en un Graphics", true);
    }

    @Test
    public void testGenerateSnapShot() {
        try {
            Double elapsedTime = null;
            String fileName = "";
            OperatorImageManager instance = new OperatorImageManager();

            File result = instance.generateSnapShot(elapsedTime, fileName);
            Assert.assertNull("Se espera null, se le pasan los datos en null", result);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);;
        }
    }

    @Test
    public void testGenerateSnapShot2() {
        try {
            Double elapsedTime = 1D;
            String fileName = "test.jpeg";
            OperatorImageManager instance = new OperatorImageManager();

            File result = instance.generateSnapShot(elapsedTime, fileName);
            Assert.assertNull("Se espera null, parametros no validos para generar snapshot [elapsedTime:" + elapsedTime + "]"
                    + "[fileName:" + fileName + "]", result);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGenerateSnapShot3() {
        try {
            Double elapsedTime = 1D;
            String fileName = videoTest.getAbsolutePath();
            OperatorImageManager instance = new OperatorImageManager();
            FFmpegImpl ffmpegImpl = new FFmpegImpl("");
            FFmpegProcessBuilder processBuilder = Mockito.mock(FFmpegProcessBuilder.class, "FFmpegProcessBuilder vacio");
            InputStream is = new ByteArrayInputStream("TEST \n sdasd".getBytes());
            Process p = Mockito.mock(Process.class, "Process retorno de FFmpegProcessBuilder");
            ffmpegImpl.setProcessBuilder(processBuilder);
            Mockito.when(processBuilder.start()).thenReturn(p);
            Mockito.when(p.getInputStream()).thenReturn(is);
            instance.setfFmpeg(ffmpegImpl);
            File result = instance.generateSnapShot(elapsedTime, fileName);
            Assert.assertNull("Se espera null, ya que no se genera archivo temporal correctamente por mock", result);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGenerateSnapShot4() {
        try {
            Double elapsedTime = null;
            String fileName = imageTest.getAbsolutePath();
            OperatorImageManager instance = new OperatorImageManager();
            File result = instance.generateSnapShot(elapsedTime, fileName);
            Assert.assertNull("Se espera null, parametros no validos para generar snapshot [elapsedTime:" + elapsedTime + "]"
                    + "[fileName:" + fileName + "]", result);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGenerateSnapShot5() {
        try {
            Double elapsedTime = 1D;
            String fileName = videoTest.getAbsolutePath();
            OperatorImageManager instance = new OperatorImageManager();

            FFmpegImpl ffmpegImpl = new FFmpegImpl("");
            FFmpegImpl ffmpegImplMock = Mockito.spy(ffmpegImpl);
            FFmpegProcessBuilder processBuilder = Mockito.mock(FFmpegProcessBuilder.class, "FFmpegProcessBuilder vacio");
            Process p = Mockito.mock(Process.class, "Process retorno de FFmpegProcessBuilder");
            InputStream is = new ByteArrayInputStream("TEST \n sdasd".getBytes());

            instance.setfFmpeg(ffmpegImplMock);

            Mockito.when(ffmpegImplMock.getProcessBuilder()).thenReturn(processBuilder);
            Mockito.when(processBuilder.start()).thenReturn(p);
            Mockito.when(p.getInputStream()).thenReturn(is);
            File f2 = File.createTempFile("test", ".jpeg");
            f2.deleteOnExit();
            FileUtils.copyFile(imageTest, f2);

            Mockito.when(ffmpegImplMock.getTmpFile()).thenReturn(f2);
            Mockito.when(ffmpegImplMock.readTempFile(f2.getAbsolutePath())).
                    thenReturn(new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB));

            File result = instance.generateSnapShot(elapsedTime, fileName);
            Assert.assertNotNull("Se espera la genracion de un file, parametros de mock compeltos "
                    + "y parametros de metodos correctos [elapsedTime:" + elapsedTime + "][fileName:" + fileName + "]", result);
        } catch (IOException e) {
            Assert.fail("No debe generar Exception " + e);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGenerateSnapShot6() {
        try {
            Double elapsedTime = 1D;
            String fileName = null;
            OperatorImageManager instance = new OperatorImageManager();

            File result = instance.generateSnapShot(elapsedTime, fileName);
            Assert.assertNull("Se espera null, parametros no validos para generar snapshot [elapsedTime:" + elapsedTime + "]"
                    + "[fileName:" + fileName + "]", result);
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGetfFmpeg() {
        OperatorImageManager instance = new OperatorImageManager();
        try {
            PropertiesConfiguration con = new PropertiesConfiguration("system.properties");
            String expResult = con.getString("path.ffmpeg");
            FFmpegImpl result = instance.getfFmpeg();
            Assert.assertNotNull("Se espera que se retorne un objeto valido", result);
            Assert.assertEquals("Se espera que FFmpegImpl este con el valor de ruta de ejecucion igual al del system.properties",
                    expResult, result.getPathExec());
        } catch (ConfigurationException e) {
            System.out.println("No se puede recuperar system.properties");
        }
    }

    @Test
    public void testGenerateProof_Marks() {
        try {
            Marks marcas = null;
            OperatorImageManager instance = new OperatorImageManager();
            ResultMarks expResult = new ResultMarks();
            expResult.setFileName(null);
            Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
            ResultMarks result = instance.generateProof(marcas, map);
            Assert.assertNotNull("Se espera un objeto no nulo", result);
            Assert.assertEquals("Se espera un objeto que como nombre de file retorne null", expResult.getFileName(),
                    result.getFileName());
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGenerateProof_Marks2() {

        try {
            OperatorImageManager instance = new OperatorImageManager();
            Marks marcas = Mockito.mock(Marks.class, "Marks con imagen y fecha de evidencia");

            Mockito.when(marcas.getElapsedTime()).thenReturn(null);
            Mockito.when(marcas.getPathOrigen()).thenReturn(imageTest.getAbsolutePath());
            Mockito.when(marcas.getEvidenceDate()).thenReturn(new Date());
            Mockito.when(marcas.getPathDestino()).thenReturn("");
            Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
            ResultMarks result = instance.generateProof(marcas, map);
            Assert.assertNotNull("Se espera un objeto no nulo", result);
            Assert.assertNotNull("Se espera un objeto que como nombre de file retorne no nulo", result.getFileName());

            try {
                FileUtils.forceDelete(new File("" + "/proofs"));
                FileUtils.forceDelete(new File("" + "/proofs_with_marks"));
            } catch (IOException e) {
                System.out.println("Error borrando archivos test");
            }
        } catch (ScopixException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testDeleteProofs() {
        OperatorImageManager instance = new OperatorImageManager();
        DeleteContainer container = Mockito.mock(DeleteContainer.class, "Container Vacio");
        Mockito.when(container.getPathOrigen()).thenReturn("");
        instance.deleteProofs(container);
        Assert.assertTrue("Se esepra que no ocurra excepcion", Boolean.TRUE);
    }

    @Test
    public void testDeleteProofs2() {
        OperatorImageManager instance = new OperatorImageManager();
        DeleteContainer container = Mockito.mock(DeleteContainer.class, "Container Vacio");
        Mockito.when(container.getPathOrigen()).thenReturn("");

        List<String> listString = Mockito.mock(List.class, "Lista de String vacia");
        Iterator<String> iterator = Mockito.mock(Iterator.class, "Iterator para Lista de String");
        String s = "img1";

        Mockito.when(listString.iterator()).thenReturn(iterator);
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(s);

        Mockito.when(container.getList()).thenReturn(listString);
        
        instance.deleteProofs(container);
        Assert.assertTrue("Se espera que no ocurra excepcion", Boolean.TRUE);
    }

    @Test
    public void testVerifyFileExistence() {
        //Crea instancia de la clase a probar
        OperatorImageManager opImageManager = new OperatorImageManager();

        try {
            File file = File.createTempFile("prueba", ".jpg");
            String filePath = FilenameUtils.separatorsToUnix(file.getAbsolutePath());
            //Invoca método a probar y verifica resultados
            Assert.assertTrue("Se espera TRUE dado que el archivo sí existe", opImageManager.verifyFileExistence(filePath));
        } catch (IOException ex) {
            Assert.fail("Error en verificación de archivo: " + ex.getMessage());
        } catch (ScopixException ex) {
            Assert.fail("Error en verificación de archivo: " + ex.getMessage());
        }
    }
    
    @Test
    public void testVerifyFileExistence2() {
        //Crea instancia de la clase a probar
        OperatorImageManager opImageManager = new OperatorImageManager();

        try {
            File file = File.createTempFile("prueba", ".jpg");
            //Cambia el nombre del archivo por buscar
            String filePath = FilenameUtils.separatorsToUnix(file.getAbsolutePath())+"x";
            //Invoca método a probar y verifica resultados
            opImageManager.verifyFileExistence(filePath);
        } catch (IOException ex) {
            Assert.fail("Error en verificación de archivo: " + ex.getMessage());
        } catch (ScopixException ex) {
            Assert.assertNotNull(ex);
        }
    }
    
    @Test
    public void testVerifyFileExistence3() {
        //Crea instancia de la clase a probar
        OperatorImageManager opImageManager = new OperatorImageManager();

        try {
            File file = File.createTempFile("prueba", ".jpg");
            String filePath = FilenameUtils.separatorsToUnix(file.getAbsolutePath());
            //Elimina el archivo
            FileUtils.forceDelete(file);
            //Invoca método a probar y verifica resultados
            opImageManager.verifyFileExistence(filePath);
        } catch (IOException ex) {
            Assert.fail("Error en verificación de archivo: " + ex.getMessage());
        } catch (ScopixException ex) {
            Assert.assertNotNull(ex);
        }
    }
}