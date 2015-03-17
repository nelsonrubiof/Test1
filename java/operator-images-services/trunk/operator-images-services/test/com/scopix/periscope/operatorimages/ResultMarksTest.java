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
 *  ResultMarksTest.java
 * 
 *  Created on 07-06-2013, 12:14:46 PM
 * 
 */
package com.scopix.periscope.operatorimages;

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
public class ResultMarksTest {
    
    public ResultMarksTest() {
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
    public void testGetEvidenceId() {
        ResultMarks instance = new ResultMarks();
        Integer expResult = null;
        instance.setEvidenceId(expResult);
        Integer result = instance.getEvidenceId();
        assertEquals(expResult, result);
    }


    @Test
    public void testGetMetricId() {
        ResultMarks instance = new ResultMarks();
        Integer expResult = null;
        instance.setMetricId(expResult);
        Integer result = instance.getMetricId();
        assertEquals(expResult, result);
    }


    @Test
    public void testGetFileName() {
        ResultMarks instance = new ResultMarks();
        String expResult = "";
        instance.setFileName(expResult);
        String result = instance.getFileName();
        assertEquals(expResult, result);
    }

}