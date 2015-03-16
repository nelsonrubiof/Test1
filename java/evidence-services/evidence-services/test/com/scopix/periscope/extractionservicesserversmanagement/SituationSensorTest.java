/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement;

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
public class SituationSensorTest {
    
    public SituationSensorTest() {
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
        SituationSensor o = null;
        SituationSensor instance = new SituationSensor();
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSensorId() {
        System.out.println("getSensorId");
        SituationSensor instance = new SituationSensor();
        Integer expResult = null;
        Integer result = instance.getSensorId();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSensorId() {
        System.out.println("setSensorId");
        Integer sensorId = null;
        SituationSensor instance = new SituationSensor();
        instance.setSensorId(sensorId);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetName() {
        System.out.println("getName");
        SituationSensor instance = new SituationSensor();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        SituationSensor instance = new SituationSensor();
        instance.setName(name);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        SituationSensor instance = new SituationSensor();
        String expResult = "";
        String result = instance.getDescription();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String description = "";
        SituationSensor instance = new SituationSensor();
        instance.setDescription(description);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSituationRequest() {
        System.out.println("getSituationRequest");
        SituationSensor instance = new SituationSensor();
        SituationRequest expResult = null;
        SituationRequest result = instance.getSituationRequest();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSituationRequest() {
        System.out.println("setSituationRequest");
        SituationRequest situationRequest = null;
        SituationSensor instance = new SituationSensor();
        instance.setSituationRequest(situationRequest);
        fail("The test case is a prototype.");
    }
}