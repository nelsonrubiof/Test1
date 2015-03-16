/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement;

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
public class SituationRequestTest {
    
    public SituationRequestTest() {
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
    public void testCompareTo() {
        System.out.println("compareTo");
        SituationRequest o = null;
        SituationRequest instance = new SituationRequest();
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSituationTemplateId() {
        System.out.println("getSituationTemplateId");
        SituationRequest instance = new SituationRequest();
        Integer expResult = null;
        Integer result = instance.getSituationTemplateId();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSituationTemplateId() {
        System.out.println("setSituationTemplateId");
        Integer situationTemplateId = null;
        SituationRequest instance = new SituationRequest();
        instance.setSituationTemplateId(situationTemplateId);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetFrecuency() {
        System.out.println("getFrecuency");
        SituationRequest instance = new SituationRequest();
        Integer expResult = null;
        Integer result = instance.getFrecuency();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetFrecuency() {
        System.out.println("setFrecuency");
        Integer frecuency = null;
        SituationRequest instance = new SituationRequest();
        instance.setFrecuency(frecuency);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDuration() {
        System.out.println("getDuration");
        SituationRequest instance = new SituationRequest();
        Integer expResult = null;
        Integer result = instance.getDuration();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDuration() {
        System.out.println("setDuration");
        Integer duration = null;
        SituationRequest instance = new SituationRequest();
        instance.setDuration(duration);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetMetricRequests() {
        System.out.println("getMetricRequests");
        SituationRequest instance = new SituationRequest();
        List expResult = null;
        List result = instance.getMetricRequests();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetMetricRequests() {
        System.out.println("setMetricRequests");
        List<MetricRequest> metricRequests = null;
        SituationRequest instance = new SituationRequest();
        instance.setMetricRequests(metricRequests);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetExtractionPlan() {
        System.out.println("getExtractionPlan");
        SituationRequest instance = new SituationRequest();
        ExtractionPlan expResult = null;
        ExtractionPlan result = instance.getExtractionPlan();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetExtractionPlan() {
        System.out.println("setExtractionPlan");
        ExtractionPlan extractionPlan = null;
        SituationRequest instance = new SituationRequest();
        instance.setExtractionPlan(extractionPlan);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSituationSensors() {
        System.out.println("getSituationSensors");
        SituationRequest instance = new SituationRequest();
        List expResult = null;
        List result = instance.getSituationSensors();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSituationSensors() {
        System.out.println("setSituationSensors");
        List<SituationSensor> situationSensors = null;
        SituationRequest instance = new SituationRequest();
        instance.setSituationSensors(situationSensors);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetRandomCamera() {
        System.out.println("getRandomCamera");
        SituationRequest instance = new SituationRequest();
        Boolean expResult = null;
        Boolean result = instance.getRandomCamera();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetRandomCamera() {
        System.out.println("setRandomCamera");
        Boolean randomCamera = null;
        SituationRequest instance = new SituationRequest();
        instance.setRandomCamera(randomCamera);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSituationRequestRanges() {
        System.out.println("getSituationRequestRanges");
        SituationRequest instance = new SituationRequest();
        List expResult = null;
        List result = instance.getSituationRequestRanges();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSituationRequestRanges() {
        System.out.println("setSituationRequestRanges");
        List<SituationRequestRange> situationRequestRanges = null;
        SituationRequest instance = new SituationRequest();
        instance.setSituationRequestRanges(situationRequestRanges);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetPriorization() {
        System.out.println("getPriorization");
        SituationRequest instance = new SituationRequest();
        Integer expResult = null;
        Integer result = instance.getPriorization();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetPriorization() {
        System.out.println("setPriorization");
        Integer priorization = null;
        SituationRequest instance = new SituationRequest();
        instance.setPriorization(priorization);
        fail("The test case is a prototype.");
    }
}