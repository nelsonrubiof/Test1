package com.scopix.periscope.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.zkoss.zk.ui.Session;

import com.scopix.periscope.command.GetNextEvidenceCommand;
import com.scopix.periscope.command.SendEvaluationCommand;
import com.scopix.periscope.enums.EnumEvaluationType;
import com.scopix.periscope.model.Client;
import com.scopix.periscope.model.Evidence;
import com.scopix.periscope.model.EvidenceProvider;
import com.scopix.periscope.model.Metric;
import com.scopix.periscope.model.Proof;
import com.scopix.periscope.model.Situation;
import com.scopix.periscope.operatorimages.MarksDTO;

/**
 * Clase de pruebas de com.scopix.periscope.manager.OperatorManager
 * 
 * @author Carlos
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class OperatorManagerTest {

    public OperatorManagerTest() {
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
    public void testGetNextEvidence() throws Exception {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock objects
        Long sessionId = new Long(1);
        Situation situation = Mockito.mock(Situation.class);
        GetNextEvidenceCommand command = Mockito.mock(GetNextEvidenceCommand.class);
        // Inyecta dependencias
        manager.setGetNextEvidenceCommand(command);
        // Define comportamiento
        Mockito.when(command.execute("queueName", "mallmarina", sessionId, "login")).thenReturn(situation);
        // Invoca método a probar
        // Assert.assertNull(manager.getNextEvidence("queueName", "mallmarina", sessionId, "login"));
    }

    @Test
    public void testGenerateMetricProofs2() throws Exception {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock objects
        Set entrySet = Mockito.mock(Set.class);
        Metric metrica = Mockito.mock(Metric.class);
        Client cliente = Mockito.mock(Client.class);
        List<Proof> proofs = Mockito.mock(List.class);
        Session mySession = Mockito.mock(Session.class);
        Evidence evidence = Mockito.mock(Evidence.class);
        List<Evidence> lstEvidencias = Mockito.mock(List.class);
        Iterator iterator = Mockito.mock(Iterator.class);
        Situation situation = Mockito.mock(Situation.class);
        Iterator<Evidence> iteratorEvidence = Mockito.mock(Iterator.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        EvidenceProvider evidenceProvider = Mockito.mock(EvidenceProvider.class);
        // Define comportamientos
        Mockito.when(situation.getId()).thenReturn(1);
        Mockito.when(metrica.isCantEval()).thenReturn(true);
        Mockito.when(metrica.getProofs()).thenReturn(proofs);
        Mockito.when(metrica.getMetricId()).thenReturn(1);
        Mockito.when(cliente.getName()).thenReturn("mallmarina");
        Mockito.when(metrica.getEvidencias()).thenReturn(lstEvidencias);
        Mockito.when(cliente.getEvidenceUrl()).thenReturn("/home/scopix.admin/data/periscope.data/evidence/mallmarina/");
        Mockito.when(cliente.getOperatorImgServicesURL()).thenReturn(
                "http://64.151.127.242:28080/operator-images-services/services/REST");
        Mockito.when(lstEvidencias.iterator()).thenReturn(iteratorEvidence);
        Mockito.when(iteratorEvidence.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iteratorEvidence.next()).thenReturn(evidence);
        Mockito.when(evidence.getEvidenceId()).thenReturn(1);
        Mockito.when(evidence.getEvidenceProvider()).thenReturn(evidenceProvider);
        Mockito.when(evidenceProvider.getId()).thenReturn(13145);
        Mockito.when(metrica.getDescription()).thenReturn("1");
        Mockito.when(metrica.getCirclesInfo()).thenReturn(circlesInfo);
        Mockito.when(metrica.getSquaresInfo()).thenReturn(squaresInfo);
        Mockito.when(metrica.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        Mockito.when(circlesInfo.entrySet()).thenReturn(entrySet);
        Mockito.when(entrySet.iterator()).thenReturn(iterator);
        Mockito.when(iterator.hasNext()).thenReturn(false);
        Mockito.when(evidence.getEvidencePath()).thenReturn(
                "127.0.0.1/periscope.data/evidence/mallmarina/002/2013/04/25/20130425_5109.jpg");
        Mockito.when(cliente.getName()).thenReturn("mallmarina");
        Mockito.when(cliente.getProofPath()).thenReturn("/home/scopix.admin/data/periscope.data/proof/mallmarina/");
        Mockito.when(evidence.getEvidenceProvider()).thenReturn(evidenceProvider);
        Mockito.when(evidenceProvider.getId()).thenReturn(1);
        // Invoca método a probar
        manager.generateMetricProofs(situation, cliente, metrica, "2013-06-06 10:10:10", "1", "login", mySession);
        // Verifica ejecución
        Mockito.verify(proofs).add(Matchers.any(Proof.class));
    }

    @Test
    public void testProcessBrandCircles() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock objects
        MarksDTO mark = Mockito.mock(MarksDTO.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Define comportamiento
        Mockito.when(circlesInfo.get(1)).thenReturn("371px:243px_30px:30px#407px:223px_30px:30px#");
        // Invoca método a probar
        manager.processMarkCircles(mark, circlesInfo, 1, "login");
        // Verifica ejecución
        Mockito.verify(mark).setCircles(Matchers.any(List.class));
    }

    @Test
    public void testProcessBrandCircles2() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock objects
        MarksDTO mark = Mockito.mock(MarksDTO.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Define comportamiento
        Mockito.when(circlesInfo.get(1)).thenReturn(null);
        // Invoca método a probar
        manager.processMarkCircles(mark, circlesInfo, 1, "login");
        // Verifica ejecución
        Mockito.verify(mark, Mockito.never()).setCircles(Matchers.any(List.class));
    }

    @Test
    public void testProcessBrandCircles3() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock objects
        MarksDTO mark = Mockito.mock(MarksDTO.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Define comportamiento
        Mockito.when(circlesInfo.get(1)).thenReturn("");
        // Invoca método a probar
        manager.processMarkCircles(mark, circlesInfo, 1, "login");
        // Verifica ejecución
        Mockito.verify(mark, Mockito.never()).setCircles(Matchers.any(List.class));
    }

    @Test
    public void testProcessBrandCircles4() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock objects
        MarksDTO mark = Mockito.mock(MarksDTO.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Define comportamiento
        Mockito.when(circlesInfo.get(1)).thenReturn("0");
        // Invoca método a probar
        manager.processMarkCircles(mark, circlesInfo, 1, "login");
        // Verifica ejecución
        Mockito.verify(mark, Mockito.never()).setCircles(Matchers.any(List.class));
    }

    @Test
    public void testProcessMarkSquares() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock objects
        MarksDTO mark = Mockito.mock(MarksDTO.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Define comportamiento
        Mockito.when(squaresInfo.get(1)).thenReturn("371px:243px_30px:30px#407px:223px_30px:30px#");
        // Invoca método a probar
        manager.processMarkSquares(mark, squaresInfo, 1, "FFAADD00", "login");
        // Verifica ejecución
        Mockito.verify(mark).setSquares(Matchers.any(List.class));
    }

    @Test
    public void testProcessMarkSquares2() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock objects
        MarksDTO mark = Mockito.mock(MarksDTO.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Define comportamiento
        Mockito.when(squaresInfo.get(1)).thenReturn(null);
        // Invoca método a probar
        manager.processMarkSquares(mark, squaresInfo, 1, "FFAADD00", "login");
        // Verifica ejecución
        Mockito.verify(mark, Mockito.never()).setSquares(Matchers.any(List.class));
    }

    @Test
    public void testProcessBrandSquares3() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock objects
        MarksDTO mark = Mockito.mock(MarksDTO.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Define comportamiento
        Mockito.when(squaresInfo.get(1)).thenReturn("");
        // Invoca método a probar
        manager.processMarkSquares(mark, squaresInfo, 1, "FFAADD00", "login");
        // Verifica ejecución
        Mockito.verify(mark, Mockito.never()).setSquares(Matchers.any(List.class));
    }

    @Test
    public void testProcessBrandSquares4() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock objects
        MarksDTO mark = Mockito.mock(MarksDTO.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Define comportamiento
        Mockito.when(squaresInfo.get(1)).thenReturn("0");
        // Invoca método a probar
        manager.processMarkSquares(mark, squaresInfo, 1, "FFAADD00", "login");
        // Verifica ejecución
        Mockito.verify(mark, Mockito.never()).setSquares(Matchers.any(List.class));
    }

    @Test
    public void testProcessMarkPaths() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock objects
        MarksDTO mark = Mockito.mock(MarksDTO.class);
        Client cliente = Mockito.mock(Client.class);
        Session mySession = Mockito.mock(Session.class);
        Evidence evidencia = Mockito.mock(Evidence.class);
        // Define comportamientos
        Mockito.when(evidencia.getEvidencePath()).thenReturn(
                "127.0.0.1/periscope.data/evidence/mallmarina/002/2013/04/25/20130425_5109.jpg");
        Mockito.when(cliente.getName()).thenReturn("mallmarina");
        Mockito.when(cliente.getProofPath()).thenReturn("/home/scopix.admin/data/periscope.data/proof/mallmarina/");
        // Invoca método a probar
        manager.processMarkPaths(mark, evidencia, cliente, "login", mySession);
        // Verifica ejecución
        Mockito.verify(mark).setPathOrigen(Matchers.anyString());
        Mockito.verify(mark).setPathDestino(Matchers.anyString());
    }

    @Test
    public void testProcessBrandPaths2() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock objects
        MarksDTO mark = Mockito.mock(MarksDTO.class);
        Client cliente = Mockito.mock(Client.class);
        Session mySession = Mockito.mock(Session.class);
        Evidence evidencia = Mockito.mock(Evidence.class);
        // Define comportamientos
        Mockito.when(evidencia.getEvidencePath()).thenReturn(
                "127.0.0.1/periscope.data/evidence/mallmarina/002/2013/04/25/20130425_5109.jpg");
        Mockito.when(cliente.getName()).thenReturn("harris");
        // Invoca método a probar
        manager.processMarkPaths(mark, evidencia, cliente, "login", mySession);
        // Verifica ejecución
        Mockito.verify(mark, Mockito.never()).setPathOrigen(Matchers.anyString());
        Mockito.verify(mark, Mockito.never()).setPathDestino(Matchers.anyString());
    }

    @Test
    public void testGenerateMarks() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Invoca método a probar
        String[] shapeArr = new String[] {};
        Assert.assertTrue(manager.generateMarks(shapeArr, "FFFFD700", "login").isEmpty());
    }

    @Test
    public void testGenerateMarks2() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Invoca método a probar
        // 371px:243px_30px:30px#407px:223px_30px:30px#
        String[] shapeArr = new String[] { "371px:243px_30px:30px" };
        // Invoca método a probar
        Assert.assertFalse(manager.generateMarks(shapeArr, "FFFFD700", "login").isEmpty());
    }

    // @Test
    // public void testGetClientQueues() throws Exception {
    // //Instancia clase a probar
    // OperatorManager manager = new OperatorManager();
    // //Crea mock objects
    // List<Queue> lstQueues = Mockito.mock(List.class);
    // Class queuesWebService = Mockito.mock(Class.class);
    // Map<String, Class> queuesWebServices = Mockito.mock(Map.class);
    // GetQueuesCommand command = Mockito.mock(GetQueuesCommand.class);
    // //Inyecta dependencia
    // manager.setQueuesCommand(command);
    // manager.setQueuesWebServices(queuesWebServices);
    // //Define comportamientos
    // Mockito.when(queuesWebServices.get("mallmarina")).thenReturn(queuesWebService);
    // Mockito.when(command.execute(queuesWebService)).thenReturn(lstQueues);
    // //Invoca método a probar
    // Assert.assertEquals(lstQueues, manager.getClientQueues("mallmarina"));
    // }

    @Test
    public void testGetQueuesWebService2() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock objects
        Map<String, Class> queuesWebServices = Mockito.mock(Map.class);
        // Inyecta dependencia
        manager.setQueuesWebServices(queuesWebServices);
        // Define comportamientos
        Mockito.when(queuesWebServices.get("mallmarina")).thenReturn(null);
        // Verifica ejecución
        // Assert.assertNotNull(manager.getQueuesWebService("mallmarina"));
    }

    @Test
    public void testGetQueuesCommand() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Verifica ejecución
        Assert.assertNotNull(manager.getQueuesCommand());
    }

    @Test
    public void testGetGetNextEvidenceCommand() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock object
        GetNextEvidenceCommand command = Mockito.mock(GetNextEvidenceCommand.class);
        // Inyecta dependencia
        manager.setGetNextEvidenceCommand(command);
        // Verifica ejecución
        Assert.assertEquals(manager.getGetNextEvidenceCommand(), command);
    }

    @Test
    public void testGetGetNextEvidenceCommand2() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Verifica ejecución
        Assert.assertNotNull(manager.getGetNextEvidenceCommand());
    }

    @Test
    public void testGetQueuesWebServices() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock object
        Map<String, Class> queuesWebServices = Mockito.mock(Map.class);
        // Inyecta dependencia
        manager.setQueuesWebServices(queuesWebServices);
        // Verifica ejecución
        Assert.assertEquals(manager.getQueuesWebServices(), queuesWebServices);
    }

    @Test
    public void testGetQueuesWebServices2() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Verifica ejecución
        Assert.assertNotNull(manager.getQueuesWebServices());
    }

    @Test
    public void testGetSendEvaluationCommand() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Crea mock object
        SendEvaluationCommand sendEvaluationCommand = Mockito.mock(SendEvaluationCommand.class);
        // Inyecta dependencia
        manager.setSendEvaluationCommand(sendEvaluationCommand);
        // Verifica ejecución
        Assert.assertEquals(manager.getSendEvaluationCommand(), sendEvaluationCommand);
    }

    @Test
    public void testGetSendEvaluationCommand2() {
        // Instancia clase a probar
        OperatorManager manager = new OperatorManager();
        // Verifica ejecución
        Assert.assertNotNull(manager.getSendEvaluationCommand());
    }
}