package com.scopix.periscope.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Clase para pruebas unitarias de la clase que almacena atributos de métricas
 * 
 * @author    carlos polo
 * @version   1.0.0
 * @since     6.0
 * @date      12/02/2013
 * @copyright Scopix
 */
public class MetricTest {
    
    public MetricTest() {
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
    
    /* pruebas getters y setters */

    @Test
    public void testGetName() {
        String name = "nombre";
        Metric metric = new Metric();
        metric.setName(name);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(metric.getName());
        Assert.assertEquals(name, metric.getName());
    }

    @Test
    public void testGetDescription() {
        String description = "desc";
        Metric metric = new Metric();
        metric.setDescription(description);
        //Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(metric.getDescription());
        Assert.assertEquals(description, metric.getDescription());
    }

    @Test
    public void testGetMetricId() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        Integer id = 1;
        metric.setMetricId(id);

        Assert.assertNotNull(metric.getMetricId());
        Assert.assertEquals(id, metric.getMetricId());
    }

    @Test
    public void testGetEvalInstruction() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        String evalInstr = "instr";
        metric.setEvalInstruction(evalInstr);

        Assert.assertNotNull(metric.getEvalInstruction());
        Assert.assertEquals(evalInstr, metric.getEvalInstruction());
    }

    @Test
    public void testGetEvidencias() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        List<Evidence> lstEvidencias = new ArrayList<Evidence>();
        metric.setEvidencias(lstEvidencias);

        Assert.assertNotNull(metric.getEvidencias());
        Assert.assertEquals(lstEvidencias, metric.getEvidencias());
    }

    @Test
    public void testGetCurrentEvidenceId() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        String id = "id";
        metric.setCurrentEvidenceId(id);

        Assert.assertNotNull(metric.getCurrentEvidenceId());
        Assert.assertEquals(id, metric.getCurrentEvidenceId());
    }

    @Test
    public void testGetType() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        String type = "type";
        metric.setType(type);

        Assert.assertNotNull(metric.getType());
        Assert.assertEquals(type, metric.getType());
    }

    @Test
    public void testGetNumMetrica() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        String numMetrica = "num";
        metric.setNumMetrica(numMetrica);

        Assert.assertNotNull(metric.getNumMetrica());
        Assert.assertEquals(numMetrica, metric.getNumMetrica());
    }

    @Test
    public void testIsMultiple() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        boolean multiple = true;
        metric.setMultiple(multiple);

        Assert.assertNotNull(metric.isMultiple());
        Assert.assertEquals(multiple, metric.isMultiple());
    }

    @Test
    public void testIsEvaluada() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        boolean evaluada = true;
        metric.setEvaluada(evaluada);

        Assert.assertNotNull(metric.isEvaluada());
        Assert.assertEquals(evaluada, metric.isEvaluada());
    }

    @Test
    public void testGetTiempoMarcas() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        HashMap<Integer, String> tiempoMarcas = new HashMap<Integer, String>();
        metric.setTiempoMarcas(tiempoMarcas);

        Assert.assertNotNull(metric.getTiempoMarcas());
        Assert.assertEquals(tiempoMarcas, metric.getTiempoMarcas());
    }

    @Test
    public void testGettFinal() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        String tFinal = "1";
        metric.settFinal(tFinal);

        Assert.assertNotNull(metric.gettFinal());
        Assert.assertEquals(tFinal, metric.gettFinal());
    }

    @Test
    public void testGettInicial() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        String tInicial = "1";
        metric.settInicial(tInicial);

        Assert.assertNotNull(metric.gettInicial());
        Assert.assertEquals(tInicial, metric.gettInicial());
    }

    @Test
    public void testGetUrlTFinal() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        String urlTFinal = "URL";
        metric.setUrlTFinal(urlTFinal);

        Assert.assertNotNull(metric.getUrlTFinal());
        Assert.assertEquals(urlTFinal, metric.getUrlTFinal());
    }

    @Test
    public void testGetUrlTInicial() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        String urlTInicial = "URL";
        metric.setUrlTInicial(urlTInicial);

        Assert.assertNotNull(metric.getUrlTInicial());
        Assert.assertEquals(urlTInicial, metric.getUrlTInicial());
    }

    @Test
    public void testIsCantEval() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        boolean urlTInicial = true;
        metric.setCantEval(urlTInicial);

        Assert.assertNotNull(metric.isCantEval());
        Assert.assertEquals(urlTInicial, metric.isCantEval());
    }

    @Test
    public void testGetCantEvalObs() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        String obs = "Obs";
        metric.setCantEvalObs(obs);

        Assert.assertNotNull(metric.getCantEvalObs());
        Assert.assertEquals(obs, metric.getCantEvalObs());
    }

    @Test
    public void testGetCirclesInfo() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        HashMap<Integer, String> circlesInfo = new HashMap<Integer, String>();
        metric.setCirclesInfo(circlesInfo);

        Assert.assertNotNull(metric.getCirclesInfo());
        Assert.assertEquals(circlesInfo, metric.getCirclesInfo());
    }

    @Test
    public void testGetSquaresInfo() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        HashMap<Integer, String> squaresInfo = new HashMap<Integer, String>();
        metric.setSquaresInfo(squaresInfo);

        Assert.assertNotNull(metric.getSquaresInfo());
        Assert.assertEquals(squaresInfo, metric.getSquaresInfo());
    }

    @Test
    public void testGetNoSquaresInfo() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        HashMap<Integer, String> noSquaresInfo = new HashMap<Integer, String>();
        metric.setNoSquaresInfo(noSquaresInfo);

        Assert.assertNotNull(metric.getNoSquaresInfo());
        Assert.assertEquals(noSquaresInfo, metric.getNoSquaresInfo());
    }

    @Test
    public void testGetYesSquaresInfo() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        HashMap<Integer, String> yesSquaresInfo = new HashMap<Integer, String>();
        metric.setYesSquaresInfo(yesSquaresInfo);

        Assert.assertNotNull(metric.getYesSquaresInfo());
        Assert.assertEquals(yesSquaresInfo, metric.getYesSquaresInfo());
    }

    @Test
    public void testGetCurrentTime() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        String currentTime = "time";
        metric.setCurrentTime(currentTime);

        Assert.assertNotNull(metric.getCurrentTime());
        Assert.assertEquals(currentTime, metric.getCurrentTime());
    }
    
    @Test
    public void testGetCurrentCameraId() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        int currentCameraId = 0;
        metric.setCurrentCameraId(currentCameraId);

        Assert.assertNotNull(metric.getCurrentCameraId());
        Assert.assertEquals(currentCameraId, metric.getCurrentCameraId());
    }
    
    @Test
    public void testGetOrder() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        Integer order = 0;
        metric.setOrder(order);

        Assert.assertNotNull(metric.getOrder());
        Assert.assertEquals(order, metric.getOrder());
    }
    
    @Test
    public void testGetStartEvaluationTime() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        Date startEvalTime = new Date();
        metric.setStartEvaluationTime(startEvalTime);

        Assert.assertNotNull(metric.getStartEvaluationTime());
        Assert.assertEquals(startEvalTime, metric.getStartEvaluationTime());
    }
    
    @Test
    public void testGetEndEvaluationTime() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        Date endEvalTime = new Date();
        metric.setEndEvaluationTime(endEvalTime);

        Assert.assertNotNull(metric.getEndEvaluationTime());
        Assert.assertEquals(endEvalTime, metric.getEndEvaluationTime());
    }
    
    @Test
    public void testGetProofs() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Se inyecta dependencia
        List<Proof> proofs = new ArrayList<Proof>();
        metric.setProofs(proofs);

        Assert.assertNotNull(metric.getProofs());
        Assert.assertEquals(proofs, metric.getProofs());
    }
    
    @Test
    public void testCompareTo() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Crea mock object
        Metric metric2 = Mockito.mock(Metric.class);
        //Invoca método a probar
        Integer result = metric.compareTo(metric2);
        //Verifica ejecución
        Assert.assertEquals(result, new Integer(0));
    }
    
    @Test
    public void testCompareTo2() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Crea mock object
        Metric metric2 = Mockito.mock(Metric.class);
        //Inyecta dependencia
        metric.setOrder(0);
        //Define comportamiento
        Mockito.when(metric2.getOrder()).thenReturn(null);
        //Invoca método a probar
        Integer result = metric.compareTo(metric2);
        //Verifica ejecución
        Assert.assertEquals(result, new Integer(0));
    }
    
    @Test
    public void testCompareTo3() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Crea mock object
        Metric metric2 = Mockito.mock(Metric.class);
        //Inyecta dependencia
        metric.setOrder(0);
        //Define comportamiento
        Mockito.when(metric2.getOrder()).thenReturn(1);
        //Invoca método a probar
        Integer result = metric.compareTo(metric2);
        //Verifica ejecución
        Assert.assertEquals(result, new Integer(-1));
    }
    
    @Test
    public void testCompareTo4() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Crea mock object
        Metric metric2 = Mockito.mock(Metric.class);
        //Inyecta dependencia
        metric.setOrder(1);
        //Define comportamiento
        Mockito.when(metric2.getOrder()).thenReturn(0);
        //Invoca método a probar
        Integer result = metric.compareTo(metric2);
        //Verifica ejecución
        Assert.assertEquals(result, new Integer(1));
    }
    
    @Test
    public void testCompareTo5() {
        //Crea instancia de la clase a probar
        Metric metric = new Metric();
        //Crea mock object
        Metric metric2 = Mockito.mock(Metric.class);
        //Inyecta dependencia
        metric.setOrder(1);
        //Define comportamiento
        Mockito.when(metric2.getOrder()).thenReturn(1);
        //Invoca método a probar
        Integer result = metric.compareTo(metric2);
        //Verifica ejecución
        Assert.assertEquals(result, new Integer(0));
    }
}