package com.scopix.periscope.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.scopix.periscope.enums.EnumEvidenceType;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceProviderSendDTO;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceSendDTO;
import com.scopix.periscope.evaluationmanagement.dto.MetricSendDTO;
import com.scopix.periscope.evaluationmanagement.dto.ProofDTO;
import com.scopix.periscope.evaluationmanagement.dto.SituationSendDTO;
import com.scopix.periscope.model.Evidence;
import com.scopix.periscope.model.Metric;
import com.scopix.periscope.model.Proof;
import com.scopix.periscope.model.Situation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 * Clase de pruebas de com.scopix.periscope.common.WebServicesUtil
 * 
 * @author Carlos
 */
public class WebServicesUtilTest {
    
    public WebServicesUtilTest() {
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
    public void testConstructor() throws Exception {
        //Método solo para marcar la clase en el informe de cobertura
        //dado que se usan métodos estáticos nunca se instancia un objeto de la misma
        WebServicesUtil webServicesUtil = new WebServicesUtil();
        Assert.assertNotNull(webServicesUtil);
    }    

    @Test
    public void testToSituation() throws Exception {
        //Crea mock objects
        SituationSendDTO situationSendDTO = Mockito.mock(SituationSendDTO.class);
        //Define comportamiento
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
        Situation situation = WebServicesUtil.toSituation(situationSendDTO, "login");
        //Verifica ejecución
        Assert.assertNotNull(situation);
    }
    
    @Test
    public void testToSituation2() throws Exception {
        //Crea mock objects
        List<MetricSendDTO> lstMetricas = Mockito.mock(List.class);
        SituationSendDTO situationSendDTO = Mockito.mock(SituationSendDTO.class);
        //Define comportamiento
        Mockito.when(situationSendDTO.getPendingEvaluationId()).thenReturn(1);
        Mockito.when(situationSendDTO.getCorporate()).thenReturn("corporate");
        Mockito.when(situationSendDTO.getStoreName()).thenReturn("store");
        Mockito.when(situationSendDTO.getArea()).thenReturn("area");
        Mockito.when(situationSendDTO.getEvidenceDateTime()).thenReturn("");
        Mockito.when(situationSendDTO.getProductName()).thenReturn("productName");
        Mockito.when(situationSendDTO.getProductDescription()).thenReturn("productDescription");
        Mockito.when(situationSendDTO.getSituationId()).thenReturn(1);
        Mockito.when(situationSendDTO.getRejectedObservation()).thenReturn("");

        Mockito.when(situationSendDTO.getMetrics()).thenReturn(lstMetricas);
        Mockito.when(lstMetricas.isEmpty()).thenReturn(true);
        //Invoca método a probar
        Situation situation = WebServicesUtil.toSituation(situationSendDTO, "login");
        //Verifica ejecución
        Assert.assertNotNull(situation);
    }
    
    @Test
    public void testToSituation3() throws Exception {
        //Crea mock objects
        List<Metric> metricas = Mockito.mock(List.class);
        Situation situation = Mockito.mock(Situation.class);
        MetricSendDTO metricDTO = Mockito.mock(MetricSendDTO.class);
        List<MetricSendDTO> lstMetricas = Mockito.mock(List.class);
        Iterator<MetricSendDTO> iterator = Mockito.mock(Iterator.class);
        SituationSendDTO situationSendDTO = Mockito.mock(SituationSendDTO.class);
        //Define comportamientos
        Mockito.when(situationSendDTO.getPendingEvaluationId()).thenReturn(1);
        Mockito.when(situationSendDTO.getCorporate()).thenReturn("corporate");
        Mockito.when(situationSendDTO.getStoreName()).thenReturn("store");
        Mockito.when(situationSendDTO.getArea()).thenReturn("area");
        Mockito.when(situationSendDTO.getEvidenceDateTime()).thenReturn("");
        Mockito.when(situationSendDTO.getProductName()).thenReturn("productName");
        Mockito.when(situationSendDTO.getProductDescription()).thenReturn("productDescription");
        Mockito.when(situationSendDTO.getSituationId()).thenReturn(1);
        Mockito.when(situationSendDTO.getRejectedObservation()).thenReturn("");
        Mockito.when(situationSendDTO.getMetrics()).thenReturn(lstMetricas);
        Mockito.when(lstMetricas.isEmpty()).thenReturn(false);

        Mockito.when(lstMetricas.iterator()).thenReturn(iterator); 
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(metricDTO);
        Mockito.when(metricDTO.getMetricId()).thenReturn(1);
        Mockito.when(metricDTO.isMultiple()).thenReturn(true); //multicámara
        
        //Prepara mock objects para procesamiento multicámara
        prepareProcessMulticameraMetric(metricDTO, situation, metricas);
        //Invoca método a probar
        Situation situation2 = WebServicesUtil.toSituation(situationSendDTO, "login");
        //Verifica ejecución
        Assert.assertNotNull(situation2);
    }
    
    @Test
    public void testToSituation4() throws Exception {
        //Crea mock objects
        MetricSendDTO metricDTO = Mockito.mock(MetricSendDTO.class);
        List<MetricSendDTO> lstMetricas = Mockito.mock(List.class);
        Iterator<MetricSendDTO> iterator = Mockito.mock(Iterator.class);
        SituationSendDTO situationSendDTO = Mockito.mock(SituationSendDTO.class);
        //Define comportamientos
        Mockito.when(situationSendDTO.getPendingEvaluationId()).thenReturn(1);
        Mockito.when(situationSendDTO.getCorporate()).thenReturn("corporate");
        Mockito.when(situationSendDTO.getStoreName()).thenReturn("store");
        Mockito.when(situationSendDTO.getArea()).thenReturn("area");
        Mockito.when(situationSendDTO.getEvidenceDateTime()).thenReturn("");
        Mockito.when(situationSendDTO.getProductName()).thenReturn("");
        Mockito.when(situationSendDTO.getProductDescription()).thenReturn("productDescription");
        Mockito.when(situationSendDTO.getSituationId()).thenReturn(1);
        Mockito.when(situationSendDTO.getRejectedObservation()).thenReturn("");
        Mockito.when(situationSendDTO.getMetrics()).thenReturn(lstMetricas);
        Mockito.when(lstMetricas.isEmpty()).thenReturn(false);

        Mockito.when(lstMetricas.iterator()).thenReturn(iterator); 
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(metricDTO); 
        Mockito.when(metricDTO.getMetricId()).thenReturn(1);
        Mockito.when(metricDTO.isMultiple()).thenReturn(false); //single camera

        //Invoca método a probar
        Situation situation2 = WebServicesUtil.toSituation(situationSendDTO, "login");
        //Verifica ejecución
        Assert.assertNotNull(situation2);
    }
    
    @Test
    public void testToSituation5() throws Exception {
        //Crea mock objects
        NullPointerException exception = new NullPointerException("message");
        SituationSendDTO situationSendDTO = Mockito.mock(SituationSendDTO.class);
        //Define comportamientos
        Mockito.when(situationSendDTO.getPendingEvaluationId()).thenThrow(exception);
        
        try{
            //Invoca método a probar
            WebServicesUtil.toSituation(situationSendDTO, "login");
        }catch(ScopixException ex){
            Assert.assertEquals(ex.getMessage(), "message");
        }
    }

    @Test
    public void testProcessMulticameraMetric() {
        //Crea mock objects
        List<Metric> metricas = Mockito.mock(List.class);
        Situation situation = Mockito.mock(Situation.class);
        MetricSendDTO metricDTO = Mockito.mock(MetricSendDTO.class);

        //Prepara mock objects para procesamiento multicámara
        prepareProcessMulticameraMetric(metricDTO, situation, metricas);
        //Invoca método a probar
        WebServicesUtil.processMulticameraMetric(0, metricDTO, situation, "login", null);
        //Verifica ejecución
        Mockito.verify(metricas).add(Matchers.any(Metric.class));
    }

    /**
     * Prepara mock objects para procesamiento multicámara
     * 
     * @param metricDTO
     * @param situation 
     * @param metricas
     */
    private void prepareProcessMulticameraMetric(MetricSendDTO metricDTO, Situation situation, List<Metric> metricas){
        //Define comportamientos
        Mockito.when(metricDTO.getEvidences()).thenReturn(null);
        Mockito.when(metricDTO.getOrder()).thenReturn(0);
        Mockito.when(metricDTO.getEvalInstruction()).thenReturn("");
        Mockito.when(metricDTO.getMetricId()).thenReturn(1);
        Mockito.when(metricDTO.getName()).thenReturn("name");
        Mockito.when(metricDTO.getType()).thenReturn("type");
        Mockito.when(situation.getMetricas()).thenReturn(metricas);
    }
    
    @Test
    public void testProcessMulticameraMetric2() {
        //Crea mock objects
        List<Metric> metricas = Mockito.mock(List.class);
        Situation situation = Mockito.mock(Situation.class);
        MetricSendDTO metricDTO = Mockito.mock(MetricSendDTO.class);
        List<EvidenceSendDTO> lstEvidences = Mockito.mock(List.class);
        //Define comportamientos
        Mockito.when(metricDTO.getEvidences()).thenReturn(lstEvidences);
        Mockito.when(metricDTO.getOrder()).thenReturn(0);
        Mockito.when(metricDTO.getEvalInstruction()).thenReturn("");
        Mockito.when(metricDTO.getMetricId()).thenReturn(1);
        Mockito.when(metricDTO.getName()).thenReturn("name");
        Mockito.when(metricDTO.getType()).thenReturn("type");
        Mockito.when(situation.getMetricas()).thenReturn(metricas);
        Mockito.when(lstEvidences.isEmpty()).thenReturn(true);
        //Invoca método a probar
        WebServicesUtil.processMulticameraMetric(0, metricDTO, situation, "login", null);
        //Verifica ejecución
        Mockito.verify(metricas).add(Matchers.any(Metric.class));
    }
    
    @Test
    public void testProcessMulticameraMetric3() {
        //Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        List<Metric> metricas = Mockito.mock(List.class);
        Situation situation = Mockito.mock(Situation.class);
        List<Evidence> evidencias = Mockito.mock(List.class);
        MetricSendDTO metricDTO = Mockito.mock(MetricSendDTO.class);
        List<EvidenceSendDTO> lstEvidences = Mockito.mock(List.class);
        EvidenceSendDTO evidenceSendDTO = Mockito.mock(EvidenceSendDTO.class);
        EvidenceProviderSendDTO evidenceProvider = Mockito.mock(EvidenceProviderSendDTO.class);
        //Define comportamientos
        Mockito.when(metricDTO.getEvidences()).thenReturn(lstEvidences);
        Mockito.when(metricDTO.getOrder()).thenReturn(0);
        Mockito.when(metricDTO.getEvalInstruction()).thenReturn("");
        Mockito.when(metricDTO.getMetricId()).thenReturn(1);
        Mockito.when(metricDTO.getName()).thenReturn("");
        Mockito.when(metricDTO.getType()).thenReturn("");
        Mockito.when(situation.getMetricas()).thenReturn(metricas);
        Mockito.when(lstEvidences.isEmpty()).thenReturn(false);
        Mockito.when(lstEvidences.size()).thenReturn(2);
        Mockito.when(lstEvidences.get(0)).thenReturn(evidenceSendDTO);
        Mockito.when(evidenceSendDTO.getEvidenceProvider()).thenReturn(evidenceProvider);
        Mockito.when(evidenceProvider.getId()).thenReturn(1);

        //Preparación de mocks de processMulticamera
        prepareSubProcessMulticameraObjects(metrica, lstEvidences, evidencias);

        //Invoca método a probar
        WebServicesUtil.processMulticameraMetric(0, metricDTO, situation, "login", null);
        //Verifica ejecución
        Mockito.verify(metricas).add(Matchers.any(Metric.class));
    }
    
    @Test
    public void testProcessMulticameraMetric4() {
        //Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        List<Metric> metricas = Mockito.mock(List.class);
        Situation situation = Mockito.mock(Situation.class);
        List<Evidence> evidencias = Mockito.mock(List.class);
        MetricSendDTO metricDTO = Mockito.mock(MetricSendDTO.class);
        List<EvidenceSendDTO> lstEvidences = Mockito.mock(List.class);
        EvidenceSendDTO evidenceSendDTO = Mockito.mock(EvidenceSendDTO.class);
        EvidenceProviderSendDTO evidenceProvider = Mockito.mock(EvidenceProviderSendDTO.class);
        //Define comportamientos
        Mockito.when(metricDTO.getEvidences()).thenReturn(lstEvidences);
        Mockito.when(metricDTO.getOrder()).thenReturn(0);
        Mockito.when(metricDTO.getEvalInstruction()).thenReturn("");
        Mockito.when(metricDTO.getMetricId()).thenReturn(1);
        Mockito.when(metricDTO.getName()).thenReturn("");
        Mockito.when(metricDTO.getType()).thenReturn("");
        Mockito.when(situation.getMetricas()).thenReturn(metricas);
        Mockito.when(lstEvidences.isEmpty()).thenReturn(false);
        Mockito.when(lstEvidences.size()).thenReturn(1);
        Mockito.when(lstEvidences.get(0)).thenReturn(evidenceSendDTO);
        Mockito.when(evidenceSendDTO.getEvidenceProvider()).thenReturn(evidenceProvider);
        Mockito.when(evidenceProvider.getId()).thenReturn(1);

        //Preparación de mocks de processMulticamera
        prepareSubProcessMulticameraObjects(metrica, lstEvidences, evidencias);

        //Invoca método a probar
        WebServicesUtil.processMulticameraMetric(0, metricDTO, situation, "login", null);
        //Verifica ejecución
        Mockito.verify(metricas).add(Matchers.any(Metric.class));
    }
    
    @Test
    public void testSubProcessMulticameraMetric() {
        //Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        List<Evidence> evidencias = Mockito.mock(List.class);
        List<EvidenceSendDTO> lstEvidences = Mockito.mock(List.class);
        //Preparación de mocks para reutilizar en los testing que correspondan
        prepareSubProcessMulticameraObjects(metrica, lstEvidences, evidencias);
        //Invoca método a probar
        WebServicesUtil.subProcessMulticameraMetric(lstEvidences, metrica, "login", null);
        //Verifica ejecución
        Mockito.verify(evidencias).add(Matchers.any(Evidence.class));
    }
    
    @Test
    public void testSubProcessMulticameraMetric2() {
        //Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        List<Evidence> evidencias = Mockito.mock(List.class);
        List<EvidenceSendDTO> lstEvidences = Mockito.mock(List.class);
        //Preparación de mocks para reutilizar en los testing que correspondan
        prepareSubProcessMulticameraObjects2(metrica, lstEvidences, evidencias);
        //Invoca método a probar
        WebServicesUtil.subProcessMulticameraMetric(lstEvidences, metrica, "login", null);
        //Verifica ejecución
        Mockito.verify(evidencias).add(Matchers.any(Evidence.class));
    }
    
    @Test
    public void testSubProcessMulticameraMetric3() {
        //Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        List<Evidence> evidencias = Mockito.mock(List.class);
        List<EvidenceSendDTO> lstEvidences = Mockito.mock(List.class);
        //Preparación de mocks para reutilizar en los testing que correspondan
        prepareSubProcessMulticameraObjects3(metrica, lstEvidences, evidencias);
        //Invoca método a probar
        WebServicesUtil.subProcessMulticameraMetric(lstEvidences, metrica, "login", null);
        //Verifica ejecución
        Mockito.verify(evidencias).add(Matchers.any(Evidence.class));
    }

    /**
     * Preparación de mocks para reutilizar en los testing que correspondan
     * 
     * @param metrica
     * @param lstEvidences
     * @param evidencias 
     */
    private void prepareSubProcessMulticameraObjects(Metric metrica, 
            List<EvidenceSendDTO> lstEvidences, List<Evidence> evidencias){
        //Crea mock objects
        Evidence evidence = Mockito.mock(Evidence.class);
        Iterator<EvidenceSendDTO> iterator = Mockito.mock(Iterator.class);
        EvidenceSendDTO evidenceDTO = Mockito.mock(EvidenceSendDTO.class);
        EvidenceProviderSendDTO evidenceProviderDTO = Mockito.mock(EvidenceProviderSendDTO.class);
        //Define comportamientos
        Mockito.when(lstEvidences.iterator()).thenReturn(iterator); 
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(evidenceDTO); 
        Mockito.when(evidenceDTO.getEvidenceId()).thenReturn(1);

        Mockito.when(evidenceDTO.getEvidencePath()).thenReturn("");
        Mockito.when(evidenceDTO.getProofPath()).thenReturn("");
        Mockito.when(evidenceDTO.getEvidenceType()).thenReturn(EnumEvidenceType.IMAGE.toString());
        Mockito.when(evidenceDTO.getEvidenceProvider()).thenReturn(evidenceProviderDTO);
        Mockito.when(evidenceProviderDTO.getId()).thenReturn(1);
        Mockito.when(evidenceProviderDTO.getDescription()).thenReturn("");
        Mockito.when(evidenceProviderDTO.getViewOrder()).thenReturn(0);
        Mockito.when(evidenceDTO.getTemplatePath()).thenReturn(null);
        Mockito.when(metrica.getEvidencias()).thenReturn(evidencias);
        Mockito.when(evidencias.get(0)).thenReturn(evidence);
        Mockito.when(evidence.getEvidenceId()).thenReturn(1);
    }
    
    /**
     * Preparación de mocks para reutilizar en los testing que correspondan
     * 
     * @param metrica
     * @param lstEvidences
     * @param evidencias 
     */
    private void prepareSubProcessMulticameraObjects2(Metric metrica, 
            List<EvidenceSendDTO> lstEvidences, List<Evidence> evidencias){
        //Crea mock objects
        Iterator<EvidenceSendDTO> iterator = Mockito.mock(Iterator.class);
        EvidenceSendDTO evidenceDTO = Mockito.mock(EvidenceSendDTO.class);
        EvidenceProviderSendDTO evidenceProviderDTO = Mockito.mock(EvidenceProviderSendDTO.class);
        //Define comportamientos
        Mockito.when(lstEvidences.iterator()).thenReturn(iterator); 
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(evidenceDTO); 
        
        Mockito.when(evidenceDTO.getEvidenceId()).thenReturn(1);

        Mockito.when(evidenceDTO.getEvidencePath()).thenReturn("");
        Mockito.when(evidenceDTO.getProofPath()).thenReturn("");
        Mockito.when(evidenceDTO.getEvidenceType()).thenReturn(EnumEvidenceType.VIDEO.toString());
        Mockito.when(evidenceDTO.getEvidenceProvider()).thenReturn(evidenceProviderDTO);
        Mockito.when(evidenceProviderDTO.getId()).thenReturn(1);
        Mockito.when(evidenceProviderDTO.getDescription()).thenReturn("");
        Mockito.when(evidenceProviderDTO.getViewOrder()).thenReturn(0);
        Mockito.when(metrica.getEvidencias()).thenReturn(evidencias);

        Mockito.when(evidenceDTO.getTemplatePath()).thenReturn("");
    }
    
    /**
     * Preparación de mocks para reutilizar en los testing que correspondan
     * 
     * @param metrica
     * @param lstEvidences
     * @param evidencias 
     */
    private void prepareSubProcessMulticameraObjects3(Metric metrica, 
            List<EvidenceSendDTO> lstEvidences, List<Evidence> evidencias){
        //Crea mock objects
        Iterator<EvidenceSendDTO> iterator = Mockito.mock(Iterator.class);
        EvidenceSendDTO evidenceDTO = Mockito.mock(EvidenceSendDTO.class);
        EvidenceProviderSendDTO evidenceProviderDTO = Mockito.mock(EvidenceProviderSendDTO.class);
        //Define comportamientos
        Mockito.when(lstEvidences.iterator()).thenReturn(iterator); 
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(evidenceDTO); 
        
        Mockito.when(evidenceDTO.getEvidenceId()).thenReturn(1);

        Mockito.when(evidenceDTO.getEvidencePath()).thenReturn("");
        Mockito.when(evidenceDTO.getProofPath()).thenReturn("");
        Mockito.when(evidenceDTO.getEvidenceType()).thenReturn(EnumEvidenceType.VIDEO.toString());
        Mockito.when(evidenceDTO.getEvidenceProvider()).thenReturn(evidenceProviderDTO);
        Mockito.when(evidenceProviderDTO.getId()).thenReturn(1);
        Mockito.when(evidenceProviderDTO.getDescription()).thenReturn("");
        Mockito.when(evidenceProviderDTO.getViewOrder()).thenReturn(0);
        Mockito.when(metrica.getEvidencias()).thenReturn(evidencias);

        Mockito.when(evidenceDTO.getTemplatePath()).thenReturn("");
    }

    @Test
    public void testProcessSingleCameraMetric() {
        //Crea mock objects
        Situation situation = Mockito.mock(Situation.class);
        MetricSendDTO metricDTO = Mockito.mock(MetricSendDTO.class);
        
        //Crea mock objects
        //Define comportamientos
        Mockito.when(metricDTO.getEvidences()).thenReturn(null);
        //Invoca método a probar
        WebServicesUtil.processSingleCameraMetric(0, metricDTO, situation, "login", null);
        //Verifica ejecución
        Mockito.verify(situation, Mockito.never()).getMetricas();
    }
    
    @Test
    public void testProcessSingleCameraMetric2() {
        //Crea mock objects
        Situation situation = Mockito.mock(Situation.class);
        MetricSendDTO metricDTO = Mockito.mock(MetricSendDTO.class);
        List<EvidenceSendDTO> lstEvidences = Mockito.mock(List.class);
        //Define comportamientos
        Mockito.when(metricDTO.getEvidences()).thenReturn(lstEvidences);
        Mockito.when(lstEvidences.isEmpty()).thenReturn(true);
        //Invoca método a probar
        WebServicesUtil.processSingleCameraMetric(0, metricDTO, situation, "login", null);
        //Verifica ejecución
        Mockito.verify(situation, Mockito.never()).getMetricas();
    }
    
    @Test
    public void testProcessSingleCameraMetric3() {
        //Crea mock objects
        List<Metric> metricas = Mockito.mock(List.class);
        Situation situation = Mockito.mock(Situation.class);
        MetricSendDTO metricDTO = Mockito.mock(MetricSendDTO.class);
        List<EvidenceSendDTO> lstEvidences = Mockito.mock(List.class);
        Iterator<EvidenceSendDTO> iterator = Mockito.mock(Iterator.class);
        EvidenceSendDTO evidenceSendDTO = Mockito.mock(EvidenceSendDTO.class);
        EvidenceProviderSendDTO evidenceProvider = Mockito.mock(EvidenceProviderSendDTO.class);
        //Define comportamientos
        Mockito.when(metricDTO.getEvidences()).thenReturn(lstEvidences);
        Mockito.when(lstEvidences.isEmpty()).thenReturn(false);
        Mockito.when(lstEvidences.iterator()).thenReturn(iterator); 
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(evidenceSendDTO);
        
        Mockito.when(metricDTO.getOrder()).thenReturn(0);
        Mockito.when(metricDTO.getEvalInstruction()).thenReturn("");
        Mockito.when(metricDTO.getMetricId()).thenReturn(1);
        Mockito.when(metricDTO.getName()).thenReturn("");
        Mockito.when(metricDTO.getType()).thenReturn("");
        Mockito.when(evidenceSendDTO.getEvidenceProvider()).thenReturn(evidenceProvider);
        Mockito.when(evidenceProvider.getId()).thenReturn(1);
        Mockito.when(evidenceSendDTO.getEvidenceId()).thenReturn(1);
        Mockito.when(evidenceSendDTO.getEvidencePath()).thenReturn("");
        Mockito.when(evidenceSendDTO.getProofPath()).thenReturn("");
        Mockito.when(evidenceSendDTO.getEvidenceType()).thenReturn(EnumEvidenceType.IMAGE.toString());
        Mockito.when(evidenceProvider.getDescription()).thenReturn("");
        Mockito.when(evidenceProvider.getViewOrder()).thenReturn(0);
        Mockito.when(evidenceSendDTO.getTemplatePath()).thenReturn(null);
        Mockito.when(situation.getMetricas()).thenReturn(metricas);
        //Invoca método a probar
        WebServicesUtil.processSingleCameraMetric(0, metricDTO, situation, "login", null);
        //Verifica ejecución
        Mockito.verify(situation).getMetricas();
    }
    
    @Test
    public void testProcessSingleCameraMetric4() {
        //Crea mock objects
        List<Metric> metricas = Mockito.mock(List.class);
        Situation situation = Mockito.mock(Situation.class);
        MetricSendDTO metricDTO = Mockito.mock(MetricSendDTO.class);
        List<EvidenceSendDTO> lstEvidences = Mockito.mock(List.class);
        Iterator<EvidenceSendDTO> iterator = Mockito.mock(Iterator.class);
        EvidenceSendDTO evidenceSendDTO = Mockito.mock(EvidenceSendDTO.class);
        EvidenceProviderSendDTO evidenceProvider = Mockito.mock(EvidenceProviderSendDTO.class);
        //Define comportamientos
        Mockito.when(metricDTO.getEvidences()).thenReturn(lstEvidences);
        Mockito.when(lstEvidences.isEmpty()).thenReturn(false);
        Mockito.when(lstEvidences.iterator()).thenReturn(iterator); 
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(evidenceSendDTO);
        
        Mockito.when(metricDTO.getOrder()).thenReturn(0);
        Mockito.when(metricDTO.getEvalInstruction()).thenReturn("");
        Mockito.when(metricDTO.getMetricId()).thenReturn(1);
        Mockito.when(metricDTO.getName()).thenReturn("");
        Mockito.when(metricDTO.getType()).thenReturn("");
        Mockito.when(evidenceSendDTO.getEvidenceProvider()).thenReturn(evidenceProvider);
        Mockito.when(evidenceProvider.getId()).thenReturn(1);
        Mockito.when(evidenceSendDTO.getEvidenceId()).thenReturn(1);
        Mockito.when(evidenceSendDTO.getEvidencePath()).thenReturn("");
        Mockito.when(evidenceSendDTO.getProofPath()).thenReturn("");
        Mockito.when(evidenceSendDTO.getEvidenceType()).thenReturn(EnumEvidenceType.VIDEO.toString());
        Mockito.when(evidenceProvider.getDescription()).thenReturn("");
        Mockito.when(evidenceProvider.getViewOrder()).thenReturn(0);
        Mockito.when(evidenceSendDTO.getTemplatePath()).thenReturn(null);
        Mockito.when(situation.getMetricas()).thenReturn(metricas);
        Mockito.when(evidenceSendDTO.getTemplatePath()).thenReturn("");
        //Invoca método a probar
        WebServicesUtil.processSingleCameraMetric(0, metricDTO, situation, "login", null);
        //Verifica ejecución
        Mockito.verify(situation).getMetricas();
    }
    
    @Test
    public void testProcessSingleCameraMetric5() {
        //Crea mock objects
        List<Metric> metricas = Mockito.mock(List.class);
        Situation situation = Mockito.mock(Situation.class);
        MetricSendDTO metricDTO = Mockito.mock(MetricSendDTO.class);
        List<EvidenceSendDTO> lstEvidences = Mockito.mock(List.class);
        Iterator<EvidenceSendDTO> iterator = Mockito.mock(Iterator.class);
        EvidenceSendDTO evidenceSendDTO = Mockito.mock(EvidenceSendDTO.class);
        EvidenceProviderSendDTO evidenceProvider = Mockito.mock(EvidenceProviderSendDTO.class);
        //Define comportamientos
        Mockito.when(metricDTO.getEvidences()).thenReturn(lstEvidences);
        Mockito.when(lstEvidences.isEmpty()).thenReturn(false);
        Mockito.when(lstEvidences.iterator()).thenReturn(iterator);
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(evidenceSendDTO);
        
        Mockito.when(metricDTO.getOrder()).thenReturn(0);
        Mockito.when(metricDTO.getEvalInstruction()).thenReturn("");
        Mockito.when(metricDTO.getMetricId()).thenReturn(1);
        Mockito.when(metricDTO.getName()).thenReturn("name");
        Mockito.when(metricDTO.getType()).thenReturn("type");
        Mockito.when(evidenceSendDTO.getEvidenceProvider()).thenReturn(evidenceProvider);
        Mockito.when(evidenceProvider.getId()).thenReturn(1);
        Mockito.when(evidenceSendDTO.getEvidenceId()).thenReturn(1);
        Mockito.when(evidenceSendDTO.getEvidencePath()).thenReturn("");
        Mockito.when(evidenceSendDTO.getProofPath()).thenReturn("");
        Mockito.when(evidenceSendDTO.getEvidenceType()).thenReturn(EnumEvidenceType.VIDEO.toString());
        Mockito.when(evidenceProvider.getDescription()).thenReturn("");
        Mockito.when(evidenceProvider.getViewOrder()).thenReturn(0);
        Mockito.when(evidenceSendDTO.getTemplatePath()).thenReturn(null);
        Mockito.when(situation.getMetricas()).thenReturn(metricas);
        Mockito.when(evidenceSendDTO.getTemplatePath()).thenReturn("");
        //Invoca método a probar
        WebServicesUtil.processSingleCameraMetric(0, metricDTO, situation, "login", null);
        //Verifica ejecución
        Mockito.verify(situation).getMetricas();
    }

    @Test
    public void testToSituationDTO() throws Exception {
        Assert.assertTrue(WebServicesUtil.toSituationDTO(null, "login").isEmpty());
    }
    
    @Test
    public void testToSituationDTO2() throws Exception {
        //Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        Situation situation = Mockito.mock(Situation.class);
        List<Metric> lstMetricas = Mockito.mock(List.class);
        Iterator<Metric> iterator = Mockito.mock(Iterator.class);
        //Define comportamientos
        Mockito.when(situation.getMetricas()).thenReturn(lstMetricas);
        Mockito.when(lstMetricas.iterator()).thenReturn(iterator);
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(metrica);
        Mockito.when(metrica.getMetricId()).thenReturn(1);
        Mockito.when(metrica.getStartEvaluationTime()).thenReturn(new Date());
        Mockito.when(metrica.getEndEvaluationTime()).thenReturn(new Date());
        Mockito.when(metrica.getDescription()).thenReturn("description");
        Mockito.when(metrica.isCantEval()).thenReturn(true);
        Mockito.when(metrica.getCantEvalObs()).thenReturn("obs");
        Mockito.when(metrica.getProofs()).thenReturn(null);
        //Invoca método a probar
        WebServicesUtil.toSituationDTO(situation, "login");
    }
    
//    @Test
//    public void testToSituationDTO3() throws Exception {
//        //Crea mock objects
//        Metric metrica = Mockito.mock(Metric.class);
//        Situation situation = Mockito.mock(Situation.class);
//        List<Metric> lstMetricas = Mockito.mock(List.class);
//        Iterator<Metric> iterator = Mockito.mock(Iterator.class);
//        //Define comportamientos
//        Mockito.when(situation.getMetricas()).thenReturn(lstMetricas);
//        Mockito.when(lstMetricas.iterator()).thenReturn(iterator);
//        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
//        Mockito.when(iterator.next()).thenReturn(metrica);
//        Mockito.when(metrica.getMetricId()).thenReturn(1);
//        Mockito.when(metrica.getStartEvaluationTime()).thenReturn(new Date());
//        Mockito.when(metrica.getEndEvaluationTime()).thenReturn(new Date());
//        Mockito.when(metrica.getDescription()).thenReturn("1");
//        Mockito.when(metrica.isCantEval()).thenReturn(false);
//        Mockito.when(metrica.getCantEvalObs()).thenReturn("obs");
//        Mockito.when(metrica.getProofs()).thenReturn(null);
//        //Invoca método a probar
//        WebServicesUtil.toSituationDTO(situation, "login");
//    }
    
    @Test
    public void testToSituationDTO4() throws Exception {
        //Crea mock objects
        Situation situation = Mockito.mock(Situation.class);
        //Define comportamientos
        Mockito.when(situation.getMetricas()).thenReturn(null);
        //Invoca método a probar
        WebServicesUtil.toSituationDTO(situation, "login");
    }
    
    @Test
    public void testToSituationDTO5() throws Exception {
        //Crea mock objects
        Situation situation = Mockito.mock(Situation.class);
        List<Metric> lstMetricas = Mockito.mock(List.class);
        //Define comportamientos
        Mockito.when(situation.getMetricas()).thenReturn(lstMetricas);
        Mockito.when(lstMetricas.isEmpty()).thenReturn(true);
        //Invoca método a probar
        WebServicesUtil.toSituationDTO(situation, "login");
    }
    
//    @Test
//    public void testToSituationDTO6() throws Exception {
//        //Crea mock objects
//        Metric metrica = Mockito.mock(Metric.class);
//        Situation situation = Mockito.mock(Situation.class);
//        List<Metric> lstMetricas = Mockito.mock(List.class);
//        Iterator<Metric> iterator = Mockito.mock(Iterator.class);
//        //Define comportamientos
//        Mockito.when(situation.getMetricas()).thenReturn(lstMetricas);
//        Mockito.when(lstMetricas.iterator()).thenReturn(iterator);
//        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
//        Mockito.when(iterator.next()).thenReturn(metrica);
//        Mockito.when(metrica.getMetricId()).thenReturn(1);
//        Mockito.when(metrica.getStartEvaluationTime()).thenReturn(new Date());
//        Mockito.when(metrica.getEndEvaluationTime()).thenReturn(null);
//        Mockito.when(metrica.getDescription()).thenReturn("1");
//        Mockito.when(metrica.isCantEval()).thenReturn(false);
//        Mockito.when(metrica.getCantEvalObs()).thenReturn("obs");
//        Mockito.when(metrica.getProofs()).thenReturn(null);
//        //Invoca método a probar
//        WebServicesUtil.toSituationDTO(situation, "login");
//    }

    @Test
    public void testProcessMetricProofs() {
        //Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        List<ProofDTO> proofsDTOList = Mockito.mock(List.class);

        Mockito.when(metrica.getProofs()).thenReturn(null);
        //Invoca método a probar
        WebServicesUtil.processMetricProofs(metrica, proofsDTOList, "login");
    }
    
    @Test
    public void testProcessMetricProofs2() {
        //Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        List<Proof> metricProofs = Mockito.mock(List.class);
        List<ProofDTO> proofsDTOList = Mockito.mock(List.class);

        Mockito.when(metrica.getProofs()).thenReturn(metricProofs);
        Mockito.when(metricProofs.isEmpty()).thenReturn(true);
        
        //Invoca método a probar
        WebServicesUtil.processMetricProofs(metrica, proofsDTOList, "login");
    }
    
    @Test
    public void testProcessMetricProofs3() {
        //Crea mock objects
        Proof proof = Mockito.mock(Proof.class);
        Metric metrica = Mockito.mock(Metric.class);
        List<Integer> ints = Mockito.mock(List.class);
        List<Proof> metricProofs = Mockito.mock(List.class);
        List<ProofDTO> proofsDTOList = Mockito.mock(List.class);

        Mockito.when(metrica.getProofs()).thenReturn(metricProofs);
        Mockito.when(metricProofs.isEmpty()).thenReturn(false);
        
        Iterator<Proof> iterator = Mockito.mock(Iterator.class);
	
        Mockito.when(metricProofs.iterator()).thenReturn(iterator); 
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(proof);
        Mockito.when(proof.getPathWithMarks()).thenReturn("path");
        Mockito.when(proof.getPathWithOutMarks()).thenReturn("path");
        Mockito.when(proof.getEvidenceId()).thenReturn(1);
        Mockito.when(metrica.isCantEval()).thenReturn(false);
        Mockito.when(metrica.getDescription()).thenReturn("1");

        //Invoca método a probar
        WebServicesUtil.processMetricProofs(metrica, proofsDTOList, "login");
    }
    
    @Test
    public void testProcessMetricProofs4() {
        //Crea mock objects
        Proof proof = Mockito.mock(Proof.class);
        Metric metrica = Mockito.mock(Metric.class);
        List<Integer> ints = Mockito.mock(List.class);
        List<Proof> metricProofs = Mockito.mock(List.class);
        List<ProofDTO> proofsDTOList = Mockito.mock(List.class);

        Mockito.when(metrica.getProofs()).thenReturn(metricProofs);
        Mockito.when(metricProofs.isEmpty()).thenReturn(false);
        
        Iterator<Proof> iterator = Mockito.mock(Iterator.class);
	
        Mockito.when(metricProofs.iterator()).thenReturn(iterator); 
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(proof);
        Mockito.when(proof.getPathWithMarks()).thenReturn("path");
        Mockito.when(proof.getPathWithOutMarks()).thenReturn("path");
        Mockito.when(proof.getEvidenceId()).thenReturn(1);
        Mockito.when(metrica.isCantEval()).thenReturn(true);

        //Invoca método a probar
        WebServicesUtil.processMetricProofs(metrica, proofsDTOList, "login");
    }

    @Test
    public void testGetTimeDifferenceInSeconds() {
        Date startEvalTime = new Date();
        Date endEvalTime = new Date();
        WebServicesUtil.getTimeDifferenceInSeconds(startEvalTime, endEvalTime, "login");
    }
}