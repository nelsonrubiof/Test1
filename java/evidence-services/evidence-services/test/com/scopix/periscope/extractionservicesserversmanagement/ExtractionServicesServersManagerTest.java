/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement;

import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
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
public class ExtractionServicesServersManagerTest {
    
    public ExtractionServicesServersManagerTest() {
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
    public void testAfterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
        ExtractionServicesServersManager instance = new ExtractionServicesServersManager();
        instance.afterPropertiesSet();
        fail("The test case is a prototype.");
    }

    @Test
    public void testInitializeWebserviceClient() {
        System.out.println("initializeWebserviceClient");
        ExtractionServicesServersManager instance = new ExtractionServicesServersManager();
        instance.initializeWebserviceClient();
        fail("The test case is a prototype.");
    }

    @Test
    public void testNewExtractionPlan() throws Exception {
        System.out.println("newExtractionPlan");
        List<EvidenceExtractionServicesServer> evidenceExtractionServicesServers = null;
        ExtractionServicesServersManager instance = new ExtractionServicesServersManager();
        instance.newExtractionPlan(evidenceExtractionServicesServers);
        fail("The test case is a prototype.");
    }

    @Test
    public void testExtractionPlanToPast() throws Exception {
        System.out.println("extractionPlanToPast");
        String date = "";
        Integer extractionServicesServerId = null;
        String storeName = "";
        ExtractionServicesServersManager instance = new ExtractionServicesServersManager();
        List expResult = null;
        List result = instance.extractionPlanToPast(date, extractionServicesServerId, storeName);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDao() {
        System.out.println("getDao");
        ExtractionServicesServersManager instance = new ExtractionServicesServersManager();
        GenericDAO expResult = null;
        GenericDAO result = instance.getDao();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDao() {
        System.out.println("setDao");
        GenericDAO dao = null;
        ExtractionServicesServersManager instance = new ExtractionServicesServersManager();
        instance.setDao(dao);
        fail("The test case is a prototype.");
    }
}