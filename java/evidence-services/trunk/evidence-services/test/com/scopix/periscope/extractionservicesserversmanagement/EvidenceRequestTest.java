/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
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
public class EvidenceRequestTest {
    
    public EvidenceRequestTest() {
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
    public void testGetRequestType() {
        System.out.println("getRequestType");
        EvidenceRequest instance = new EvidenceRequest();
        String expResult = "";
        String result = instance.getRequestType();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetRequestType() {
        System.out.println("setRequestType");
        String requestType = "";
        EvidenceRequest instance = new EvidenceRequest();
        instance.setRequestType(requestType);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetRequestedTime() {
        System.out.println("getRequestedTime");
        EvidenceRequest instance = new EvidenceRequest();
        Date expResult = null;
        Date result = instance.getRequestedTime();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetRequestedTime() {
        System.out.println("setRequestedTime");
        Date requestedTime = null;
        EvidenceRequest instance = new EvidenceRequest();
        instance.setRequestedTime(requestedTime);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDuration() {
        System.out.println("getDuration");
        EvidenceRequest instance = new EvidenceRequest();
        Integer expResult = null;
        Integer result = instance.getDuration();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDuration() {
        System.out.println("setDuration");
        Integer duration = null;
        EvidenceRequest instance = new EvidenceRequest();
        instance.setDuration(duration);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDeviceId() {
        System.out.println("getDeviceId");
        EvidenceRequest instance = new EvidenceRequest();
        Integer expResult = null;
        Integer result = instance.getDeviceId();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDeviceId() {
        System.out.println("setDeviceId");
        Integer deviceId = null;
        EvidenceRequest instance = new EvidenceRequest();
        instance.setDeviceId(deviceId);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetBusinessServicesRequestId() {
        System.out.println("getBusinessServicesRequestId");
        EvidenceRequest instance = new EvidenceRequest();
        Integer expResult = null;
        Integer result = instance.getBusinessServicesRequestId();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetBusinessServicesRequestId() {
        System.out.println("setBusinessServicesRequestId");
        Integer businessServicesRequestId = null;
        EvidenceRequest instance = new EvidenceRequest();
        instance.setBusinessServicesRequestId(businessServicesRequestId);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetExtractionPlanDetail() {
        System.out.println("getExtractionPlanDetail");
        EvidenceRequest instance = new EvidenceRequest();
        ExtractionPlanDetail expResult = null;
        ExtractionPlanDetail result = instance.getExtractionPlanDetail();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetExtractionPlanDetail() {
        System.out.println("setExtractionPlanDetail");
        ExtractionPlanDetail extractionPlanDetail = null;
        EvidenceRequest instance = new EvidenceRequest();
        instance.setExtractionPlanDetail(extractionPlanDetail);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDayOfWeek() {
        System.out.println("getDayOfWeek");
        EvidenceRequest instance = new EvidenceRequest();
        Integer expResult = null;
        Integer result = instance.getDayOfWeek();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDayOfWeek() {
        System.out.println("setDayOfWeek");
        Integer dayOfWeek = null;
        EvidenceRequest instance = new EvidenceRequest();
        instance.setDayOfWeek(dayOfWeek);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        EvidenceRequest o = null;
        EvidenceRequest instance = new EvidenceRequest();
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetType() {
        System.out.println("getType");
        EvidenceRequest instance = new EvidenceRequest();
        EvidenceRequestType expResult = null;
        EvidenceRequestType result = instance.getType();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetType() {
        System.out.println("setType");
        EvidenceRequestType type = null;
        EvidenceRequest instance = new EvidenceRequest();
        instance.setType(type);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetProcessId() {
        System.out.println("getProcessId");
        EvidenceRequest instance = new EvidenceRequest();
        Integer expResult = null;
        Integer result = instance.getProcessId();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetProcessId() {
        System.out.println("setProcessId");
        Integer processId = null;
        EvidenceRequest instance = new EvidenceRequest();
        instance.setProcessId(processId);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetPriorization() {
        System.out.println("getPriorization");
        EvidenceRequest instance = new EvidenceRequest();
        Integer expResult = null;
        Integer result = instance.getPriorization();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetPriorization() {
        System.out.println("setPriorization");
        Integer priorization = null;
        EvidenceRequest instance = new EvidenceRequest();
        instance.setPriorization(priorization);
        fail("The test case is a prototype.");
    }
}