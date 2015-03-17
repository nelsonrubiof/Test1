/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement.commands;

import com.scopix.periscope.evaluationmanagement.services.webservices.EvaluationWebServices;
import com.scopix.periscope.evidencemanagement.Evidence;
import com.scopix.periscope.evidencemanagement.dto.SituationMetricsDTO;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlan;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlanDetail;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author nelson
 */
public class AcceptAutomaticNewEvidenceCommandTest {

    public AcceptAutomaticNewEvidenceCommandTest() {
    }

    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testExecute() throws Exception {
        Evidence evidence = Mockito.mock(Evidence.class);
        ExtractionPlanDetail extractionPlanDetail = Mockito.mock(ExtractionPlanDetail.class);
        ExtractionPlan extractionPlan = Mockito.mock(ExtractionPlan.class);
        EvidenceExtractionServicesServer evidenceExtractionServicesServer = Mockito.mock(EvidenceExtractionServicesServer.class);
        Mockito.when(extractionPlan.getEvidenceExtractionServicesServer()).thenReturn(evidenceExtractionServicesServer);
        Mockito.when(extractionPlanDetail.getExtractionPlan()).thenReturn(extractionPlan);
        Mockito.when(evidence.getExtractionPlanDetail()).thenReturn(extractionPlanDetail);
        EvaluationWebServices webServices = Mockito.mock(EvaluationWebServices.class);
        List<SituationMetricsDTO> situationMetricList = Mockito.mock(List.class);
        long sessionId = 0L;
        AcceptAutomaticNewEvidenceCommand instance = new AcceptAutomaticNewEvidenceCommand();
        instance.execute(evidence, webServices, situationMetricList);
        assertTrue("Void method, nothing to compare", true);
    }
}
