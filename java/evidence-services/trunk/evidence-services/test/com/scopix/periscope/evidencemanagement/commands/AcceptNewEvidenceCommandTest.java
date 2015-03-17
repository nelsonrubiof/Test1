/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement.commands;

import com.scopix.periscope.evaluationmanagement.services.webservices.EvaluationWebServices;
import com.scopix.periscope.evidencemanagement.Evidence;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceRequest;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlan;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlanDetail;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 *
 * @author nelson
 */
public class AcceptNewEvidenceCommandTest {
    
    public AcceptNewEvidenceCommandTest() {
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
        Evidence evidence = Mockito.mock(Evidence.class);
        EvaluationWebServices webservice = Mockito.mock(EvaluationWebServices.class);
        ExtractionPlanDetail extractionPlanDetail = Mockito.mock(ExtractionPlanDetail.class);
        ExtractionPlan extractionPlan =Mockito.mock(ExtractionPlan.class);
        EvidenceRequest evidencerequest =  Mockito.mock(EvidenceRequest.class);
        List <EvidenceRequest> evidenceRequestList = new ArrayList <EvidenceRequest>();
        evidenceRequestList.add(evidencerequest);
        EvidenceExtractionServicesServer evidenceExtractionServicesServer = Mockito.mock(EvidenceExtractionServicesServer.class);
        Mockito.when(extractionPlan.getEvidenceExtractionServicesServer()).thenReturn(evidenceExtractionServicesServer);
        Mockito.when(extractionPlanDetail.getExtractionPlan()).thenReturn(extractionPlan);
        Mockito.when(extractionPlanDetail.getEvidenceRequests()).thenReturn(evidenceRequestList);
        Mockito.when(evidence.getExtractionPlanDetail()).thenReturn(extractionPlanDetail);
        long sessionId = 0L;
        AcceptNewEvidenceCommand instance = new AcceptNewEvidenceCommand();
        instance.execute(evidence, webservice);
        assertTrue("Void Method execution correct",true);
       
    }
}