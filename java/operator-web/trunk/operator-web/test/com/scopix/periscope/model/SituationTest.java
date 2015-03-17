package com.scopix.periscope.model;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Clase para pruebas unitarias de la clase que almacena atributos de situaciones
 * 
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 * @date 12/02/2013
 * @copyright Scopix
 */
public class SituationTest {

    public SituationTest() {
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
    public void testGetArea() {
        String area = "area";
        Situation situacion = new Situation();
        situacion.setArea(area);
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(situacion.getArea());
        Assert.assertEquals(area, situacion.getArea());
    }

    @Test
    public void testGetLength() {
        Date length = new Date();
        Situation situacion = new Situation();
        situacion.setLength(length);
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(situacion.getLength());
        Assert.assertEquals(length, situacion.getLength());
    }

    @Test
    public void testGetStore() {
        String store = "store";
        Situation situacion = new Situation();
        situacion.setStoreName(store);
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(situacion.getStoreName());
        Assert.assertEquals(store, situacion.getStoreName());
    }

    @Test
    public void testGetClient() {
        String client = "client";
        Situation situacion = new Situation();
        situacion.setClient(client);
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(situacion.getClient());
        Assert.assertEquals(client, situacion.getClient());
    }

    @Test
    public void testIsRejected() {
        Boolean rejected = Boolean.TRUE;
        Situation situacion = new Situation();
        situacion.setRejected(rejected);
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(situacion.isRejected());
        Assert.assertEquals(rejected, situacion.isRejected());
    }

    @Test
    public void testGetProductName() {
        String productName = "productName";
        Situation situacion = new Situation();
        situacion.setProductName(productName);
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(situacion.getProductName());
        Assert.assertEquals(productName, situacion.getProductName());
    }

    @Test
    public void testGetEvidenceDateTime() {
        String evidenceDateTime = "2013-06-06 10:10:10";
        Situation situacion = new Situation();
        situacion.setEvidenceDateTime(evidenceDateTime);
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(situacion.getEvidenceDateTime());
        Assert.assertEquals(evidenceDateTime, situacion.getEvidenceDateTime());
    }

    @Test
    public void testGetPendingEvaluationId() {
        Integer pendingEvalId = 1;
        Situation situacion = new Situation();
        situacion.setPendingEvaluationId(pendingEvalId);
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(situacion.getPendingEvaluationId());
        Assert.assertEquals(pendingEvalId, situacion.getPendingEvaluationId());
    }

    @Test
    public void testGetProductDescription() {
        String productDesc = "productDesc";
        Situation situacion = new Situation();
        situacion.setProductDescription(productDesc);
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(situacion.getProductDescription());
        Assert.assertEquals(productDesc, situacion.getProductDescription());
    }

    @Test
    public void testGetRejectedObservation() {
        String rejectedObs = "rejectedObs";
        Situation situacion = new Situation();
        situacion.setRejectedObservation(rejectedObs);
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(situacion.getRejectedObservation());
        Assert.assertEquals(rejectedObs, situacion.getRejectedObservation());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetMetricas() {
        Situation situacion = new Situation();

        List<Metric> metricas = Mockito.mock(List.class);
        metricas.add(new Metric());
        // Inyecta dependencia
        situacion.setMetricas(metricas);
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(situacion.getMetricas());
        Assert.assertEquals(metricas, situacion.getMetricas());
    }

    @Test
    public void testGetId() {
        // Crea instancia de la clase a probar
        Situation situacion = new Situation();
        // Se inyecta dependencia
        Integer id = 1;
        situacion.setId(id);

        Assert.assertNotNull(situacion.getId());
        Assert.assertEquals(id, situacion.getId());
    }
}