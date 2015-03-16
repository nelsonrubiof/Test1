/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement;

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
public class SituationRequestRangeTest {
    
    public SituationRequestRangeTest() {
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
    public void testGetInitialTime() {
        System.out.println("getInitialTime");
        SituationRequestRange instance = new SituationRequestRange();
        Date expResult = null;
        Date result = instance.getInitialTime();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetInitialTime() {
        System.out.println("setInitialTime");
        Date initialTime = null;
        SituationRequestRange instance = new SituationRequestRange();
        instance.setInitialTime(initialTime);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEndTime() {
        System.out.println("getEndTime");
        SituationRequestRange instance = new SituationRequestRange();
        Date expResult = null;
        Date result = instance.getEndTime();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetEndTime() {
        System.out.println("setEndTime");
        Date endTime = null;
        SituationRequestRange instance = new SituationRequestRange();
        instance.setEndTime(endTime);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetFrecuency() {
        System.out.println("getFrecuency");
        SituationRequestRange instance = new SituationRequestRange();
        Integer expResult = null;
        Integer result = instance.getFrecuency();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetFrecuency() {
        System.out.println("setFrecuency");
        Integer frecuency = null;
        SituationRequestRange instance = new SituationRequestRange();
        instance.setFrecuency(frecuency);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDuration() {
        System.out.println("getDuration");
        SituationRequestRange instance = new SituationRequestRange();
        Integer expResult = null;
        Integer result = instance.getDuration();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDuration() {
        System.out.println("setDuration");
        Integer duration = null;
        SituationRequestRange instance = new SituationRequestRange();
        instance.setDuration(duration);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSituationExtractionRequests() {
        System.out.println("getSituationExtractionRequests");
        SituationRequestRange instance = new SituationRequestRange();
        List expResult = null;
        List result = instance.getSituationExtractionRequests();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSituationExtractionRequests() {
        System.out.println("setSituationExtractionRequests");
        List<SituationExtractionRequest> situationExtractionRequests = null;
        SituationRequestRange instance = new SituationRequestRange();
        instance.setSituationExtractionRequests(situationExtractionRequests);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDayOfWeek() {
        System.out.println("getDayOfWeek");
        SituationRequestRange instance = new SituationRequestRange();
        Integer expResult = null;
        Integer result = instance.getDayOfWeek();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDayOfWeek() {
        System.out.println("setDayOfWeek");
        Integer dayOfWeek = null;
        SituationRequestRange instance = new SituationRequestRange();
        instance.setDayOfWeek(dayOfWeek);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSituationRequest() {
        System.out.println("getSituationRequest");
        SituationRequestRange instance = new SituationRequestRange();
        SituationRequest expResult = null;
        SituationRequest result = instance.getSituationRequest();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSituationRequest() {
        System.out.println("setSituationRequest");
        SituationRequest situationRequest = null;
        SituationRequestRange instance = new SituationRequestRange();
        instance.setSituationRequest(situationRequest);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetRangeType() {
        System.out.println("getRangeType");
        SituationRequestRange instance = new SituationRequestRange();
        String expResult = "";
        String result = instance.getRangeType();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetRangeType() {
        System.out.println("setRangeType");
        String rangeType = "";
        SituationRequestRange instance = new SituationRequestRange();
        instance.setRangeType(rangeType);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSamples() {
        System.out.println("getSamples");
        SituationRequestRange instance = new SituationRequestRange();
        Integer expResult = null;
        Integer result = instance.getSamples();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSamples() {
        System.out.println("setSamples");
        Integer samples = null;
        SituationRequestRange instance = new SituationRequestRange();
        instance.setSamples(samples);
        fail("The test case is a prototype.");
    }
}