package com.scopix.periscope.model;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Clase para pruebas unitarias de la clase que almacena atributos de Client
 * 
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 * @date 06/02/2013
 * @copyright Scopix
 */
public class ClientTest {

    public ClientTest() {
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
    public void testGetName() {
        String name = "nombre";
        Client cliente = new Client();
        cliente.setName(name);
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(cliente.getName());
        Assert.assertEquals(name, cliente.getName());
    }

    @Test
    public void testGetUniqueCorporateId() {
        String uniqueCorporateId = "10";
        Client cliente = new Client();
        cliente.setUniqueCorporateId(uniqueCorporateId);
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(cliente.getUniqueCorporateId());
        Assert.assertEquals(uniqueCorporateId, cliente.getUniqueCorporateId());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetQueues() {
        Client cliente = new Client();
        // Creación mock
        List<Queue> colas = Mockito.mock(List.class);
        colas.add(new Queue());
        // Inyecta dependencia
        cliente.setQueues(colas);
        // Valida que el atributo sea el mismo asignado
        Assert.assertNotNull(cliente.getQueues());
        Assert.assertEquals(colas, cliente.getQueues());
    }

    @Test
    public void testGetEvaluationWebService() {
        // Crea instancia de la clase a probar
        Client cliente = new Client();
        // Se inyecta dependencia
        String evalWebService = "Ws";
        cliente.setEvaluationWebService(evalWebService);

        Assert.assertNotNull(cliente.getEvaluationWebService());
        Assert.assertEquals(evalWebService, cliente.getEvaluationWebService());
    }

    @Test
    public void testGetEvidenceUrl() {
        // Crea instancia de la clase a probar
        Client cliente = new Client();
        // Se inyecta dependencia
        String evidenceURL = "URL";
        cliente.setEvidenceUrl(evidenceURL);

        Assert.assertNotNull(cliente.getEvidenceUrl());
        Assert.assertEquals(evidenceURL, cliente.getEvidenceUrl());
    }

    @Test
    public void testGetTemplateUrl() {
        // Crea instancia de la clase a probar
        Client cliente = new Client();
        // Se inyecta dependencia
        String templateURL = "URL";
        cliente.setTemplateUrl(templateURL);

        Assert.assertNotNull(cliente.getTemplateUrl());
        Assert.assertEquals(templateURL, cliente.getTemplateUrl());
    }

    @Test
    public void testGetOperatorImgServicesURL() {
        // Crea instancia de la clase a probar
        Client cliente = new Client();
        // Se inyecta dependencia
        String operatorImgSrvUrl = "URL";
        cliente.setOperatorImgServicesURL(operatorImgSrvUrl);

        Assert.assertNotNull(cliente.getOperatorImgServicesURL());
        Assert.assertEquals(operatorImgSrvUrl, cliente.getOperatorImgServicesURL());
    }

    @Test
    public void testGetDescription() {
        // Crea instancia de la clase a probar
        Client cliente = new Client();
        // Se inyecta dependencia
        String description = "description";
        cliente.setDescription(description);

        Assert.assertNotNull(cliente.getDescription());
        Assert.assertEquals(description, cliente.getDescription());
    }

    @Test
    public void testGetProofPath() {
        // Crea instancia de la clase a probar
        Client cliente = new Client();
        // Se inyecta dependencia
        String proofPath = "path";
        cliente.setProofPath(proofPath);

        Assert.assertNotNull(cliente.getProofPath());
        Assert.assertEquals(proofPath, cliente.getProofPath());
    }
}