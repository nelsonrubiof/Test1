package com.scopix.periscope.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Clase de pruebas de com.scopix.periscope.model.EvidenceProvider
 * 
 * @author Carlos
 */
public class EvidenceProviderTest {
    
    public EvidenceProviderTest() {
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
    public void testGetId() {
        //Crea instancia de la clase a probar
        EvidenceProvider provider = new EvidenceProvider();
        //Se inyecta dependencia
        Integer id = 0;
        provider.setId(id);

        Assert.assertNotNull(provider.getId());
        Assert.assertEquals(id, provider.getId());
    }

    @Test
    public void testGetDescripcion() {
        //Crea instancia de la clase a probar
        EvidenceProvider provider = new EvidenceProvider();
        //Se inyecta dependencia
        String description = "description";
        provider.setDescripcion(description);

        Assert.assertNotNull(provider.getDescripcion());
        Assert.assertEquals(description, provider.getDescripcion());
    }
    
    @Test
    public void testGetViewOrder() {
        //Crea instancia de la clase a probar
        EvidenceProvider provider = new EvidenceProvider();
        //Se inyecta dependencia
        Integer viewOrder = 0;
        provider.setViewOrder(viewOrder);

        Assert.assertNotNull(provider.getViewOrder());
        Assert.assertEquals(viewOrder, provider.getViewOrder());
    }
}