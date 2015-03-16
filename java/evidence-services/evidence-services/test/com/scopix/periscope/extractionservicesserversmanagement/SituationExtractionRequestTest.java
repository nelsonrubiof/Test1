/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement;

import java.util.Date;
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
public class SituationExtractionRequestTest {
    
    public SituationExtractionRequestTest() {
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
    public void testGetSituationRequestRange() {
        System.out.println("getSituationRequestRange");
        SituationExtractionRequest instance = new SituationExtractionRequest();
        SituationRequestRange expResult = null;
        SituationRequestRange result = instance.getSituationRequestRange();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSituationRequestRange() {
        System.out.println("setSituationRequestRange");
        SituationRequestRange situationRequestRange = null;
        SituationExtractionRequest instance = new SituationExtractionRequest();
        instance.setSituationRequestRange(situationRequestRange);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetTimeSample() {
        System.out.println("getTimeSample");
        SituationExtractionRequest instance = new SituationExtractionRequest();
        Date expResult = null;
        Date result = instance.getTimeSample();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetTimeSample() {
        System.out.println("setTimeSample");
        Date timeSample = null;
        SituationExtractionRequest instance = new SituationExtractionRequest();
        instance.setTimeSample(timeSample);
        fail("The test case is a prototype.");
    }
}