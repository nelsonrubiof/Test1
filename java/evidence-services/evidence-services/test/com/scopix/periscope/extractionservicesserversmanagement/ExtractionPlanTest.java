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
public class ExtractionPlanTest {
    
    public ExtractionPlanTest() {
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
    public void testGetExpiration() {
        System.out.println("getExpiration");
        ExtractionPlan instance = new ExtractionPlan();
        Date expResult = null;
        Date result = instance.getExpiration();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetExpiration() {
        System.out.println("setExpiration");
        Date expiration = null;
        ExtractionPlan instance = new ExtractionPlan();
        instance.setExpiration(expiration);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetExtractionPlanDetails() {
        System.out.println("getExtractionPlanDetails");
        ExtractionPlan instance = new ExtractionPlan();
        List expResult = null;
        List result = instance.getExtractionPlanDetails();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetExtractionPlanDetails() {
        System.out.println("setExtractionPlanDetails");
        List<ExtractionPlanDetail> extractionPlanDetails = null;
        ExtractionPlan instance = new ExtractionPlan();
        instance.setExtractionPlanDetails(extractionPlanDetails);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEvidenceExtractionServicesServer() {
        System.out.println("getEvidenceExtractionServicesServer");
        ExtractionPlan instance = new ExtractionPlan();
        EvidenceExtractionServicesServer expResult = null;
        EvidenceExtractionServicesServer result = instance.getEvidenceExtractionServicesServer();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetEvidenceExtractionServicesServer() {
        System.out.println("setEvidenceExtractionServicesServer");
        EvidenceExtractionServicesServer evidenceExtractionServicesServer = null;
        ExtractionPlan instance = new ExtractionPlan();
        instance.setEvidenceExtractionServicesServer(evidenceExtractionServicesServer);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSituationRequests() {
        System.out.println("getSituationRequests");
        ExtractionPlan instance = new ExtractionPlan();
        List expResult = null;
        List result = instance.getSituationRequests();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSituationRequests() {
        System.out.println("setSituationRequests");
        List<SituationRequest> situationRequests = null;
        ExtractionPlan instance = new ExtractionPlan();
        instance.setSituationRequests(situationRequests);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetStoreTimes() {
        System.out.println("getStoreTimes");
        ExtractionPlan instance = new ExtractionPlan();
        List expResult = null;
        List result = instance.getStoreTimes();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetStoreTimes() {
        System.out.println("setStoreTimes");
        List<StoreTime> storeTimes = null;
        ExtractionPlan instance = new ExtractionPlan();
        instance.setStoreTimes(storeTimes);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetStoreName() {
        System.out.println("getStoreName");
        ExtractionPlan instance = new ExtractionPlan();
        String expResult = "";
        String result = instance.getStoreName();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetStoreName() {
        System.out.println("setStoreName");
        String storeName = "";
        ExtractionPlan instance = new ExtractionPlan();
        instance.setStoreName(storeName);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetTimeZoneId() {
        System.out.println("getTimeZoneId");
        ExtractionPlan instance = new ExtractionPlan();
        String expResult = "";
        String result = instance.getTimeZoneId();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetTimeZoneId() {
        System.out.println("setTimeZoneId");
        String timeZoneId = "";
        ExtractionPlan instance = new ExtractionPlan();
        instance.setTimeZoneId(timeZoneId);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetStoreId() {
        System.out.println("getStoreId");
        ExtractionPlan instance = new ExtractionPlan();
        Integer expResult = null;
        Integer result = instance.getStoreId();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetStoreId() {
        System.out.println("setStoreId");
        Integer storeId = null;
        ExtractionPlan instance = new ExtractionPlan();
        instance.setStoreId(storeId);
        fail("The test case is a prototype.");
    }
}