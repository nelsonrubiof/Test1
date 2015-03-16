/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement;

import com.scopix.periscope.corporatestructuremanagement.services.webservices.CorporateWebServices;
import com.scopix.periscope.evaluationmanagement.services.webservices.EvaluationWebServices;
import com.scopix.periscope.evidencemanagement.commands.AcceptAutomaticNewEvidenceCommand;
import com.scopix.periscope.evidencemanagement.commands.DeleteEvidenceAutomaticCommand;
import com.scopix.periscope.evidencemanagement.commands.GetProcessIdByExtractionPlanProcessEESCommand;
import com.scopix.periscope.evidencemanagement.dto.NewAutomaticEvidenceDTO;
import com.scopix.periscope.evidencemanagement.dto.SituationMetricsDTO;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlan;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlanDetail;
import com.scopix.periscope.extractionservicesserversmanagement.dao.ExtractionPlanDetailDAO;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.securitymanagement.services.webservices.SecurityWebServices;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author nelson
 */
public class EvidenceManagerTest {

    public EvidenceManagerTest() {
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
        EvidenceManager instance = new EvidenceManager();
        instance.afterPropertiesSet();
    }

    @Test
    public void testAcceptNewEvidence() throws Exception {
        Evidence evidence = null;
        EvidenceManager instance = new EvidenceManager();
        GenericDAO dao = Mockito.mock(GenericDAO.class, "Dao mock");
        EvaluationWebServices service = Mockito.mock(EvaluationWebServices.class, "servicio evaluation de test");
        SecurityWebServices securityWebServices = Mockito.mock(SecurityWebServices.class, "servicio de security test");
        CorporateWebServices corporateWebServices = Mockito.mock(CorporateWebServices.class, "servicio de security test");

        instance.setGenericDAO(dao);
        instance.setEvaluationWebService(service);
        instance.setSecurityWebServices(securityWebServices);
        instance.setCorporateWebServices(corporateWebServices);

        Mockito.when(securityWebServices.login(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(0L);
        Mockito.when(corporateWebServices.getCorporateId()).thenReturn(0);

        instance.acceptNewEvidence(evidence);

    }

    @Test
    public void testAcceptAutomaticNewEvidence() throws Exception {
        EvidenceManager instance = new EvidenceManager();
        NewAutomaticEvidenceDTO newAutomaticEvidenceDTO = null;
        Evidence expResult = null;
        Evidence result = instance.acceptAutomaticNewEvidence(newAutomaticEvidenceDTO);
        Assert.assertEquals("Se espera null ya que no existe newAutomaticEvidenceDTO", expResult, result);
    }

    @Test
    public void testAcceptAutomaticNewEvidence2() throws Exception {
        EvidenceManager instance = new EvidenceManager();
        NewAutomaticEvidenceDTO newAutomaticEvidenceDTO = Mockito.mock(NewAutomaticEvidenceDTO.class, "Dto de Test");
        ExtractionPlanDetailDAO dao = Mockito.mock(ExtractionPlanDetailDAO.class, "DAO de Test");
        GenericDAO genericDao = Mockito.mock(GenericDAO.class, "DAO generico de Test");
        ExtractionPlan plan = Mockito.mock(ExtractionPlan.class, "EP de Test");
        GetProcessIdByExtractionPlanProcessEESCommand command1 = Mockito.mock(GetProcessIdByExtractionPlanProcessEESCommand.class,
                "Commando 1 de Test");
        ProcessEES processEES = Mockito.mock(ProcessEES.class, "ProcessEES de Test");

        instance.setExtractionPlanDetailDAO(dao);
        instance.setProcessIdByExtractionPlanProcessEESCommand(command1);
        instance.setGenericDAO(genericDao);

        Mockito.when(newAutomaticEvidenceDTO.getEvidenceDate()).thenReturn("2013-04-24 13:00:00");
        Mockito.when(newAutomaticEvidenceDTO.getExtractionPlanServerId()).thenReturn(1);
        Mockito.when(newAutomaticEvidenceDTO.getProcessId()).thenReturn(1);
        Mockito.when(newAutomaticEvidenceDTO.getEvidenceProviderId()).thenReturn(1);
        Mockito.when(newAutomaticEvidenceDTO.getFileName()).thenReturn("");
        Mockito.when(dao.getExtractionPlanByEvidenceExtractionServicesServer(Mockito.anyInt())).thenReturn(plan);
        Mockito.when(command1.execute(Mockito.any(ExtractionPlan.class), Mockito.anyInt())).thenReturn(processEES);
        Mockito.when(processEES.getProcessIdLocal()).thenReturn(1);

        Evidence result = instance.acceptAutomaticNewEvidence(newAutomaticEvidenceDTO);
        Assert.assertNotNull("Se espera que retorne un evidence", result);
    }

    @Test
    public void testGetEvidenceRequestsByProvider() {
        String dateEvidenceStart = "";
        String dateEvidenceEnd = "";
        Set<Integer> providerIds = null;
        String storeName = "";
        EvidenceManager instance = new EvidenceManager();
        Map result = instance.getEvidenceRequestsByProvider(dateEvidenceStart, dateEvidenceEnd, providerIds, storeName);
        Assert.assertNotNull("Se espera que siempre retorne un map", result);
    }

    @Test
    public void testGetEvidenceRequestsByProvider2() {
        String dateEvidenceStart = "2013-01-01 10:00:00";
        String dateEvidenceEnd = "2013-01-01 10:00:01";
        Set<Integer> providerIds = null;
        String storeName = "";
        EvidenceManager instance = new EvidenceManager();

        ExtractionPlanDetailDAO dao = Mockito.mock(ExtractionPlanDetailDAO.class, "Dao de test");

        instance.setExtractionPlanDetailDAO(dao);

        Mockito.when(dao.getEvidenceRequestByProviderIds(Mockito.anySet(), Mockito.anyString(), Mockito.any(Date.class),
                Mockito.any(Date.class))).thenReturn(new HashMap<Integer, Integer>());

        Map result = instance.getEvidenceRequestsByProvider(dateEvidenceStart, dateEvidenceEnd, providerIds, storeName);
        Assert.assertNotNull("Se espera que siempre retorne un map", result);
    }

    @Test
    public void testSendNewAutomaticEvidenceToBusinessServices() throws Exception {
        EvidenceManager instance = new EvidenceManager();
        Evidence evidence = null;
        NewAutomaticEvidenceDTO newAutomaticEvidenceDTO = null;

        instance.sendNewAutomaticEvidenceToBusinessServices(evidence, newAutomaticEvidenceDTO);
    }

    @Test
    public void testSendNewAutomaticEvidenceToBusinessServices2() throws Exception {
        EvidenceManager instance = new EvidenceManager();
        Evidence evidence = Mockito.mock(Evidence.class, "Evidencia de Test");
        NewAutomaticEvidenceDTO newAutomaticEvidenceDTO = Mockito.mock(NewAutomaticEvidenceDTO.class, "Dto de test");
        AcceptAutomaticNewEvidenceCommand command = Mockito.mock(AcceptAutomaticNewEvidenceCommand.class, "Commando de Test");

        EvaluationWebServices service = Mockito.mock(EvaluationWebServices.class, "servicio evaluation de test");
        SecurityWebServices securityWebServices = Mockito.mock(SecurityWebServices.class, "servicio de security test");
        CorporateWebServices corporateWebServices = Mockito.mock(CorporateWebServices.class, "servicio de security test");

        instance.setEvaluationWebService(service);
        instance.setSecurityWebServices(securityWebServices);
        instance.setCorporateWebServices(corporateWebServices);
        instance.setAcceptAutomaticNewEvidenceCommand(command);

        Mockito.when(newAutomaticEvidenceDTO.getSituationMetrics()).thenReturn(
                new ArrayList<SituationMetricsDTO>());

        instance.sendNewAutomaticEvidenceToBusinessServices(evidence, newAutomaticEvidenceDTO);
    }

    @Test
    public void testDeleteEvidence() throws Exception {
        EvidenceManager instance = new EvidenceManager();
        Evidence evidence = Mockito.mock(Evidence.class, "Evidencia de Test");
        ExtractionPlanDetail extractionPlanDetail = Mockito.mock(ExtractionPlanDetail.class, "Plan Detail de Test");
        DeleteEvidenceAutomaticCommand deleteEvidenceAutomaticCommand = Mockito.mock(DeleteEvidenceAutomaticCommand.class,
                "Comando de test");
        ExtractionPlanDetailDAO dao = Mockito.mock(ExtractionPlanDetailDAO.class, "Dao de Test");

        instance.setDeleteEvidenceAutomaticCommand(deleteEvidenceAutomaticCommand);

        Mockito.when(deleteEvidenceAutomaticCommand.getDao()).thenReturn(dao);
        Mockito.when(evidence.getId()).thenReturn(1);
        Mockito.when(evidence.getExtractionPlanDetail()).thenReturn(extractionPlanDetail);
        Mockito.when(extractionPlanDetail.getId()).thenReturn(1);

        instance.deleteEvidence(evidence);

    }

    @Test
    public void testGetGenericDAO() {
        EvidenceManager instance = new EvidenceManager();
        GenericDAO result = instance.getGenericDAO();
        Assert.assertNotNull("Se espera que retorne un DAO Generico", result);
    }

    @Test
    public void testGetEvaluationWebService() throws Exception {
        EvidenceManager instance = new EvidenceManager();
        EvaluationWebServices result = instance.getEvaluationWebService();
        Assert.assertNotNull("Se espera que retorne un Servicio", result);
    }

    @Test
    public void testGetCorporateWebServices() throws Exception {
        EvidenceManager instance = new EvidenceManager();
        CorporateWebServices result = instance.getCorporateWebServices();
        Assert.assertNotNull("Se espera que retorne un servicio", result);
    }

    @Test
    public void testGetDeleteEvidenceAutomaticCommand() {
        EvidenceManager instance = new EvidenceManager();
        DeleteEvidenceAutomaticCommand result = instance.getDeleteEvidenceAutomaticCommand();
        Assert.assertNotNull("Se espera que retorne un comando", result);
    }

    @Test
    public void testGetExtractionPlanDetailDAO() {
        EvidenceManager instance = new EvidenceManager();
        ExtractionPlanDetailDAO result = instance.getExtractionPlanDetailDAO();
        Assert.assertNotNull("Se espera que retorne un Dao", result);
    }

    @Test
    public void testGetProcessIdByExtractionPlanProcessEESCommand() {
        EvidenceManager instance = new EvidenceManager();
        GetProcessIdByExtractionPlanProcessEESCommand result = instance.getProcessIdByExtractionPlanProcessEESCommand();
        Assert.assertNotNull("Se espera que retorne un comando", result);
    }

    @Test
    public void testGetAcceptAutomaticNewEvidenceCommand() {
        EvidenceManager instance = new EvidenceManager();
        AcceptAutomaticNewEvidenceCommand result = instance.getAcceptAutomaticNewEvidenceCommand();
        Assert.assertNotNull("Se espera que retorne un comando", result);
    }

    @Test
    public void testGetSecurityWebServices() throws Exception {
        EvidenceManager instance = new EvidenceManager();
        SecurityWebServices result = instance.getSecurityWebServices();
        Assert.assertNotNull("Se espera que retorne un servicio", result);
    }

}
