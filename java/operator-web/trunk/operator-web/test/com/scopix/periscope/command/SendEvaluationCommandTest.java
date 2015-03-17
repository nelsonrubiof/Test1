package com.scopix.periscope.command;

import com.scopix.periscope.evaluationmanagement.dto.EvidenceEvaluationDTO;
import com.scopix.periscope.evaluationroutingmanagement.services.webservices.EvaluationRoutingWebServices;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Clase de pruebas de com.scopix.periscope.command.SendEvaluationCommand
 * 
 * @author Carlos
 */
public class SendEvaluationCommandTest {
    
    public SendEvaluationCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test//(expected=ScopixException.class)
    public void testExecute() throws Exception {
        //Instancia clase a probar
        Long sessionId = new Long(1);
        SendEvaluationCommand command = new SendEvaluationCommand();
        //Crea mock objects
        List<EvidenceEvaluationDTO> lstEvidenceEvaluationDTO = Mockito.mock(List.class);
        EvaluationRoutingWebServices evaluationRoutingService = Mockito.mock(EvaluationRoutingWebServices.class);
        command.setEvaluationRoutingService(evaluationRoutingService);
        //Invoca método a probar
        command.execute(lstEvidenceEvaluationDTO, "harris", sessionId, "login");
    }
}