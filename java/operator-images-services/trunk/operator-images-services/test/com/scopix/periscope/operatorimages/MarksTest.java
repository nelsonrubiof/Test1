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
 *  MarksTest.java
 * 
 *  Created on 07-06-2013, 12:14:45 PM
 * 
 */
package com.scopix.periscope.operatorimages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nelson
 */
public class MarksTest {

    public MarksTest() {
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
    public void testGetCircles() {
        Marks instance = new Marks();
        List<Shapes> expResult = new ArrayList<Shapes>();
        List<Shapes> result = instance.getCircles();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetSquares() {
        Marks instance = new Marks();
        List<Shapes> expResult = new ArrayList<Shapes>();
        List<Shapes> result = instance.getSquares();
        assertEquals(expResult, result);

    }

    @Test
    public void testGetMetricId() {
        Marks instance = new Marks();
        Integer expResult = null;
        instance.setMetricId(expResult);
        Integer result = instance.getMetricId();
        assertEquals(expResult, result);

    }

    @Test
    public void testGetEvidenceId() {
        Marks instance = new Marks();
        Integer expResult = null;
        instance.setEvidenceId(expResult);
        Integer result = instance.getEvidenceId();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetEvidenceDate() {
        Marks instance = new Marks();
        Date expResult = null;
        instance.setEvidenceDate(expResult);
        Date result = instance.getEvidenceDate();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetPathOrigen() {
        Marks instance = new Marks();
        String expResult = "";
        instance.setPathOrigen(expResult);
        String result = instance.getPathOrigen();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetPathDestino() {
        Marks instance = new Marks();
        String expResult = "";
        instance.setPathDestino(expResult);
        String result = instance.getPathDestino();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetElapsedTime() {
        Marks instance = new Marks();
        Double expResult = null;
        instance.setElapsedTime(expResult);
        Double result = instance.getElapsedTime();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetResult() {
        Marks instance = new Marks();
        Integer expResult = null;
        instance.setResult(expResult);
        Integer result = instance.getResult();
        assertEquals(expResult, result);
    }
}