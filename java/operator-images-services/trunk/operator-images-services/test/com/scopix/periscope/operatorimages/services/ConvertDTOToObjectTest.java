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
 *  ConvertDTOToObjectTest.java
 * 
 *  Created on 07-06-2013, 12:00:17 PM
 * 
 */
package com.scopix.periscope.operatorimages.services;

import com.scopix.periscope.operatorimages.Marks;
import com.scopix.periscope.operatorimages.MarksDTO;
import com.scopix.periscope.operatorimages.Shapes;
import com.scopix.periscope.operatorimages.ShapesDTO;
import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author nelson
 */
public class ConvertDTOToObjectTest {

    public ConvertDTOToObjectTest() {
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
    public void testConvert() {
        MarksDTO origen = new MarksDTO();
        origen.setElapsedTime(1D);
        origen.setPathOrigen("2");
        origen.setPathDestino("3");
        origen.setMetricId(4);
        origen.setEvidenceId(5);
        origen.setWithNumber(Boolean.TRUE);
        origen.setEvidenceDate("2013-06-07 10:00:00");
        origen.setResult(7);
        origen.setCorporateName("harris");
        origen.setStoreName("306");
        origen.setAreaName("checkout");
        origen.setSituationId(1);
        origen.setCameraId(1);
        origen.setCameraName("camara1");
        origen.setMetricName("metrica1");

        ShapesDTO dto = new ShapesDTO();
        dto.setPositionX(1);
        dto.setPositionY(2);
        dto.setWidth(3);
        dto.setHeight(4);
        dto.setColor("5");
        
        origen.getSquares().add(dto);

        Marks destino = new Marks();
        destino = (Marks) ConvertDTOToObject.convert(origen, destino);
        Shapes result = destino.getSquares().get(0);
        Assert.assertEquals("Se espera que el contenido del shape sea 1", result.getPositionX(), new Integer(1));
        Assert.assertEquals("Se espera que el contenido del shape sea 2 ", result.getPositionY(), new Integer(2));
        Assert.assertEquals("Se espera que el contenido del shape sea 3 ", result.getWidth(), new Integer(3));
        Assert.assertEquals("Se espera que el contenido del shape sea 4 ", result.getHeight(), new Integer(4));
        Assert.assertEquals("Se espera que el contenido del shape sea 5 ", result.getColor(), "5");
    }

    @Test
    public void testConvert2() {
        MarksDTO origen = new MarksDTO();
        origen.setElapsedTime(1D);
        origen.setPathOrigen("2");
        origen.setPathDestino("3");
        origen.setMetricId(4);
        origen.setEvidenceId(5);
        origen.setWithNumber(Boolean.TRUE);
        origen.setEvidenceDate("2013-06-07 10:00:00");
        origen.setResult(7);
        origen.setCorporateName("harris");
        origen.setStoreName("306");
        origen.setAreaName("checkout");
        origen.setSituationId(1);
        origen.setCameraId(1);
        origen.setCameraName("camara1");
        origen.setMetricName("metrica1");
        origen.setCircles(new ArrayList<ShapesDTO>());

        Marks destino = new Marks();
        destino = (Marks) ConvertDTOToObject.convert(origen, destino);
//        Shapes result = destino.getSquares().get(0);
//        Assert.assertEquals("Se espera que el contenido del shape sea 1", result.getPositionX(), new Integer(1));
//        Assert.assertEquals("Se espera que el contenido del shape sea 2 ", result.getPositionY(), new Integer(2));
//        Assert.assertEquals("Se espera que el contenido del shape sea 3 ", result.getWidth(), new Integer(3));
//        Assert.assertEquals("Se espera que el contenido del shape sea 4 ", result.getHeight(), new Integer(4));
//        Assert.assertEquals("Se espera que el contenido del shape sea 5 ", result.getColor(), "5");
    }

    @Test
    public void testConvertToDate() {
        String value = "";
        Date expResult = null;
        Date result = ConvertDTOToObject.convertToDate(value);
        Assert.assertEquals("Se espera que no se pueda convertir ", expResult, result);

    }

    @Test
    public void testConvertToDate2() {
        Integer value = new Integer(1);
        Date expResult = null;
        Date result = ConvertDTOToObject.convertToDate(value);
        Assert.assertEquals("Se espera que no se pueda convertir ", expResult, result);

    }
}