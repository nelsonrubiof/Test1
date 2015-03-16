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
public class StoreTimeTest {
    
    public StoreTimeTest() {
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
        StoreTime o = null;
        StoreTime instance = new StoreTime();
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDayOfWeek() {
        System.out.println("getDayOfWeek");
        StoreTime instance = new StoreTime();
        Integer expResult = null;
        Integer result = instance.getDayOfWeek();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDayOfWeek() {
        System.out.println("setDayOfWeek");
        Integer dayOfWeek = null;
        StoreTime instance = new StoreTime();
        instance.setDayOfWeek(dayOfWeek);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetOpenHour() {
        System.out.println("getOpenHour");
        StoreTime instance = new StoreTime();
        String expResult = "";
        String result = instance.getOpenHour();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetOpenHour() {
        System.out.println("setOpenHour");
        String openHour = "";
        StoreTime instance = new StoreTime();
        instance.setOpenHour(openHour);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEndHour() {
        System.out.println("getEndHour");
        StoreTime instance = new StoreTime();
        String expResult = "";
        String result = instance.getEndHour();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetEndHour() {
        System.out.println("setEndHour");
        String endHour = "";
        StoreTime instance = new StoreTime();
        instance.setEndHour(endHour);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetExtractionPlan() {
        System.out.println("getExtractionPlan");
        StoreTime instance = new StoreTime();
        ExtractionPlan expResult = null;
        ExtractionPlan result = instance.getExtractionPlan();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetExtractionPlan() {
        System.out.println("setExtractionPlan");
        ExtractionPlan extractionPlan = null;
        StoreTime instance = new StoreTime();
        instance.setExtractionPlan(extractionPlan);
        fail("The test case is a prototype.");
    }
}