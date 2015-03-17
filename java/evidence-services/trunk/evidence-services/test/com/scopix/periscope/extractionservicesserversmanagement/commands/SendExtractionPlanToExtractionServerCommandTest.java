/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.services.webservices.ExtractionPlanWebService;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceExtractionServicesServer;
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
public class SendExtractionPlanToExtractionServerCommandTest {
    
    public SendExtractionPlanToExtractionServerCommandTest() {
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
    public void testExecute() throws Exception {
        System.out.println("execute");
        EvidenceExtractionServicesServer evidenceExtractionServicesServer = null;
        ExtractionPlanWebService extractionPlanWebService = null;
        SendExtractionPlanToExtractionServerCommand instance = new SendExtractionPlanToExtractionServerCommand();
        instance.execute(evidenceExtractionServicesServer, extractionPlanWebService);
        fail("The test case is a prototype.");
    }
}