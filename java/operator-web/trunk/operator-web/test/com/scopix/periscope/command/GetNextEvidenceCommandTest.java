package com.scopix.periscope.command;

import com.scopix.periscope.evaluationmanagement.dto.MetricSendDTO;
import com.scopix.periscope.evaluationmanagement.dto.SituationSendDTO;
import com.scopix.periscope.evaluationroutingmanagement.services.webservices.EvaluationRoutingWebServices;
import com.scopix.periscope.model.Situation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * Clase de pruebas de com.scopix.periscope.command.GetNextEvidenceCommand
 * 
 * @author Carlos
 */
public class GetNextEvidenceCommandTest {
    
    public GetNextEvidenceCommandTest() {
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
    
    @Test
    public void testExecute() throws Exception {
        //Instancia clase a probar
        GetNextEvidenceCommand command = new GetNextEvidenceCommand();
        //Crea mock objects
        EvaluationRoutingWebServices evalWs = Mockito.mock(EvaluationRoutingWebServices.class);
        SituationSendDTO situationSendDTO = Mockito.mock(SituationSendDTO.class);
        //Define comportamiento
        Mockito.when(evalWs.getNextEvidence(Matchers.anyString(), 
                Matchers.anyString(), Matchers.anyLong())).thenReturn(situationSendDTO);
        Mockito.when(situationSendDTO.getPendingEvaluationId()).thenReturn(1);
        Mockito.when(situationSendDTO.getCorporate()).thenReturn("corporate");
        Mockito.when(situationSendDTO.getStoreName()).thenReturn("store");
        Mockito.when(situationSendDTO.getArea()).thenReturn("area");
        Mockito.when(situationSendDTO.getEvidenceDateTime()).thenReturn("");
        Mockito.when(situationSendDTO.getProductName()).thenReturn("productName");
        Mockito.when(situationSendDTO.getProductDescription()).thenReturn("productDescription");
        Mockito.when(situationSendDTO.getSituationId()).thenReturn(1);
        Mockito.when(situationSendDTO.getRejectedObservation()).thenReturn("");
        Mockito.when(situationSendDTO.getMetrics()).thenReturn(new ArrayList<MetricSendDTO>());
        //Invoca método a probar
        Situation situation = command.execute("", "", new Long(0), "login");
        //Verifica ejecución
        Assert.assertNull(situation);
    }
    
    @Test
    public void testExecute2() throws Exception {
        //Instancia clase a probar
        GetNextEvidenceCommand command = new GetNextEvidenceCommand();
        //Crea mock objects
        EvaluationRoutingWebServices evalWs = Mockito.mock(EvaluationRoutingWebServices.class);
        //Define comportamiento
        Mockito.when(evalWs.getNextEvidence(Matchers.anyString(), 
                Matchers.anyString(), Matchers.anyLong())).thenReturn(null);
        //Invoca método a probar
        Assert.assertNull(command.execute("", "", new Long(0), "login"));
    }
    
    @Test
    public void testGetNextEvidenceDTO() {
        //Instancia clase a probar
        GetNextEvidenceCommand command = new GetNextEvidenceCommand();
        //Crea mock objects
        EvaluationRoutingWebServices evalWs = Mockito.mock(EvaluationRoutingWebServices.class);
        SituationSendDTO situationSendDTO = Mockito.mock(SituationSendDTO.class);
        try {
            //Define comportamiento
            Mockito.when(evalWs.getNextEvidence(Matchers.anyString(), 
                Matchers.anyString(), Matchers.anyLong())).thenReturn(situationSendDTO);
            //Invoca método a probar y verifica ejecución
            //Assert.assertEquals(command.getNextEvidenceDTO("", "", new Long(0), "login"), situationSendDTO);

        } catch (ScopixException ex) {
            Assert.fail(ex.getMessage());
        }
    }
    
    @Test
    public void testGetNextEvidenceDTO2() {
        //Instancia clase a probar
        GetNextEvidenceCommand command = new GetNextEvidenceCommand();
        //Crea mock objects
        EvaluationRoutingWebServices evalWs = Mockito.mock(EvaluationRoutingWebServices.class);
        SituationSendDTO situationSendDTO = Mockito.mock(SituationSendDTO.class);
        ScopixWebServiceException exception = Mockito.mock(ScopixWebServiceException.class);
        
        try {
            //Define comportamiento
            Mockito.when(exception.getMessage()).thenReturn("message");
            Mockito.when(evalWs.getNextEvidence(Matchers.anyString(), 
                Matchers.anyString(), Matchers.anyLong())).thenThrow(exception);
            //Invoca método a probar y verifica ejecución
            //Assert.assertEquals(command.getNextEvidenceDTO("", "", new Long(0), "login"), situationSendDTO);

        } catch (ScopixException ex) {
            Assert.assertEquals(ex.getMessage(), "message");
        }
    }
    
    @Test
    public void testGetNextEvidenceDTO3() {
        //Instancia clase a probar
        GetNextEvidenceCommand command = new GetNextEvidenceCommand();
        //Crea mock objects
        EvaluationRoutingWebServices evalWs = Mockito.mock(EvaluationRoutingWebServices.class);
        SituationSendDTO situationSendDTO = Mockito.mock(SituationSendDTO.class);
        ScopixWebServiceException exception = Mockito.mock(ScopixWebServiceException.class);
        
        try {
            //Define comportamiento
            Mockito.when(exception.getMessage()).thenReturn("message");
            Mockito.when(evalWs.getNextEvidence(Matchers.anyString(), 
                Matchers.anyString(), Matchers.anyLong())).thenThrow(exception);
            //Invoca método a probar y verifica ejecución
            //Assert.assertEquals(command.getNextEvidenceDTO("", "", new Long(0), "login"), situationSendDTO);

        } catch (ScopixException ex) {
            Assert.assertEquals(ex.getMessage(), "message");
        }
    }
}