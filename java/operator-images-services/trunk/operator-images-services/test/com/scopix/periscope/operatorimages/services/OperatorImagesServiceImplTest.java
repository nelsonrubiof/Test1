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
 *  OperatorImagesServiceImplTest.java
 * 
 *  Created on 07-05-2013, 09:59:38 AM
 * 
 */
package com.scopix.periscope.operatorimages.services;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.scopix.periscope.operatorimages.MarksContainerDTO;
import com.scopix.periscope.operatorimages.MarksDTO;
import com.scopix.periscope.operatorimages.OperatorImageManager;
import com.scopix.periscope.operatorimages.ResultMarksContainerDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 *
 * @author nelson
 */
public class OperatorImagesServiceImplTest {

    public OperatorImagesServiceImplTest() {
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
    public void testGetSnapshot() {
        try {
            Double elapsedTime = 1D;
            String fileName = "";
            OperatorImagesServiceImpl instance = new OperatorImagesServiceImpl();
            HttpServletUtils utils = Mockito.mock(HttpServletUtils.class);
            instance.setServletUtils(utils);

            HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);

            Mockito.when(utils.getResponse()).thenReturn(response);
            Mockito.when(response.getOutputStream()).thenReturn(out);


//            OutputStream result = instance.getSnapshot(elapsedTime, fileName);
//            Assert.assertNotNull("Se espera respuesta, OutputStream nunca puede ser null [elapsedTime:" + elapsedTime + "]"
//                    + "[fileName:" + fileName + "]", result);
        } catch (IOException e) {
            Assert.fail("Error " + e);
        }
    }

    @Test
    public void testGetSnapshotTMedio() {
        try {
            String fileName = "";
            OperatorImagesServiceImpl instance = new OperatorImagesServiceImpl();
            HttpServletUtils utils = Mockito.mock(HttpServletUtils.class);
            instance.setServletUtils(utils);
            HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);

            Mockito.when(utils.getResponse()).thenReturn(response);
            Mockito.when(response.getOutputStream()).thenReturn(out);

//            OutputStream result = instance.getSnapshotTMedio(fileName);
//            Assert.assertNotNull("Se espera respuesta, OutputStream nunca puede ser null [fileName:" + fileName + "]", result);
        } catch (IOException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGetTemplate() {
        try {
            String fileName = "";
            OperatorImagesServiceImpl instance = new OperatorImagesServiceImpl();
            HttpServletUtils utils = Mockito.mock(HttpServletUtils.class);
            instance.setServletUtils(utils);

            HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);

            Mockito.when(utils.getResponse()).thenReturn(response);
            Mockito.when(response.getOutputStream()).thenReturn(out);

//            OutputStream result = instance.getTemplate(fileName);
//            Assert.assertNotNull("Se espera respuesta, OutputStream nunca puede ser null [fileName:" + fileName + "]", result);
        } catch (IOException e) {
            Assert.fail("ERROR");
        }
    }

    @Test
    public void testGetImage() {
        try {
            String fileName = "";
            OperatorImagesServiceImpl instance = new OperatorImagesServiceImpl();
            HttpServletUtils utils = Mockito.mock(HttpServletUtils.class);
            instance.setServletUtils(utils);

            HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
            ServletOutputStream out = Mockito.mock(ServletOutputStream.class);

            Mockito.when(utils.getResponse()).thenReturn(response);
            Mockito.when(response.getOutputStream()).thenReturn(out);

//            OutputStream result = instance.getImage(fileName);
//            Assert.assertNotNull("Se espera respuesta, OutputStream nunca puede ser null [fileName:" + fileName + "]", result);
        } catch (IOException e) {
            Assert.fail("ERROR " + e);
        }
    }

    @Test
    public void testGetImageManager() {
        OperatorImagesServiceImpl instance = new OperatorImagesServiceImpl();
        OperatorImageManager result = instance.getImageManager();
        Assert.assertNotNull("Se espera una instancia de OperatorImageManager", result);
    }

    @Test
    public void testGenerateProof() {
        try {
            OperatorImagesServiceImpl instance = new OperatorImagesServiceImpl();
            MarksContainerDTO request = Mockito.mock(MarksContainerDTO.class, "MarksContainer vacio");
            Mockito.when(request.getMarks()).thenReturn(null);
            ResultMarksContainerDTO result = instance.generateProof(request);

            Assert.assertNotNull("Se espera una lista sin datos, Lista vacia marcas enviadas", result);
        } catch (ScopixException e) {
            Assert.fail("Error " + e);
        }
    }

    @Test
    public void testGenerateProof2() {
        try {
            OperatorImagesServiceImpl instance = new OperatorImagesServiceImpl();

            List<MarksDTO> listMarks = Mockito.mock(List.class, "Lista de Marcas");
            Iterator<MarksDTO> iterator = Mockito.mock(Iterator.class, "Iterator para Lista de Marcas");
            MarksDTO marcas = Mockito.mock(MarksDTO.class, "Marca para ser retornado por la lista");

            Mockito.when(listMarks.iterator()).thenReturn(iterator);
            Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
            Mockito.when(iterator.next()).thenReturn(marcas);
            Mockito.when(marcas.getElapsedTime()).thenReturn(1D);

            MarksContainerDTO request = Mockito.mock(MarksContainerDTO.class, "MarksContainer vacio");
            Mockito.when(request.getMarks()).thenReturn(listMarks);


            ResultMarksContainerDTO result = instance.generateProof(request);
        } catch (ScopixException e) {
            Assert.assertEquals("Se espera excepcion", e.getMessage(), "No se puede generar proof:null");
        }
    }
    
    
    @Test
    public void testGenerateProof3() {
        try {
            OperatorImagesServiceImpl instance = new OperatorImagesServiceImpl();

            List<MarksDTO> listMarks = Mockito.mock(List.class, "Lista de Marcas");
            Iterator<MarksDTO> iterator = Mockito.mock(Iterator.class, "Iterator para Lista de Marcas");

            Mockito.when(listMarks.iterator()).thenReturn(iterator);
            Mockito.when(iterator.hasNext()).thenReturn(false);

            MarksContainerDTO request = Mockito.mock(MarksContainerDTO.class, "MarksContainer vacio");
            Mockito.when(request.getMarks()).thenReturn(listMarks);


            ResultMarksContainerDTO result = instance.generateProof(request);
        } catch (ScopixException e) {
            Assert.assertEquals("Se espera excepcion", e.getMessage(), "No se puede generar proof:null");
        }
    }

    @Test
    public void testGenerateProofTMedio() {
        try {
            OperatorImagesServiceImpl instance = new OperatorImagesServiceImpl();
            MarksContainerDTO request = Mockito.mock(MarksContainerDTO.class, "MarksContainer vacio");
            ResultMarksContainerDTO result = instance.generateProofTMedio(request);
            Assert.assertNotNull("Se espera una lista sin datos, Lista vacia marcas enviadas", result.getResults());
        } catch (ScopixException e) {
            Assert.fail("Error " + e);
        }
    }

    @Test
    public void testGenerateProofTMedio2() {
        try {
            OperatorImagesServiceImpl instance = new OperatorImagesServiceImpl();
            List<MarksDTO> listMarks = Mockito.mock(List.class, "Lista de Marcas");
            Iterator<MarksDTO> iterator = Mockito.mock(Iterator.class, "Iterator para Lista de Marcas");
            MarksDTO marcas = Mockito.mock(MarksDTO.class, "Marca para ser retornado por la lista");

            Mockito.when(listMarks.iterator()).thenReturn(iterator);
            Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
            Mockito.when(iterator.next()).thenReturn(marcas);
            Mockito.when(marcas.getElapsedTime()).thenReturn(1D);

            MarksContainerDTO request = Mockito.mock(MarksContainerDTO.class, "MarksContainer vacio");
            Mockito.when(request.getMarks()).thenReturn(listMarks);


            ResultMarksContainerDTO result = instance.generateProofTMedio(request);
        } catch (ScopixException e) {
            Assert.assertEquals("Se espera excepcion", e.getMessage(), "No se puede generar proof:null");
        }
    }

    @Test
    public void testSetImageManager() {
        OperatorImagesServiceImpl instance = new OperatorImagesServiceImpl();
        OperatorImageManager imageManager = Mockito.mock(OperatorImageManager.class);
        instance.setImageManager(imageManager);

        //probamos que el imageManager sea el que seteamos
        OperatorImageManager result = instance.getImageManager();
        Assert.assertEquals("Se espera que el OperatorImageManager devuelto sea igual al mock injectado", imageManager, result);
    }

    @Test
    public void testGetServletUtils() {

        OperatorImagesServiceImpl instance = new OperatorImagesServiceImpl();
        HttpServletUtils result = instance.getServletUtils();
        Assert.assertNotNull("Se espera que simpre retorne un objeto ", result);
    }

    @Test
    public void testGenerateProofSimple() {
        try {
            OperatorImagesServiceImpl instance = new OperatorImagesServiceImpl();            
            MarksDTO marcas = null;
            String result = instance.generateProofSimple(marcas);            
        } catch (ScopixException e) {
            Assert.assertTrue("Se espera Error ya que no existe marcas", Boolean.TRUE);
        }
    }
}