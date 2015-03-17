package com.scopix.periscope.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Clase de pruebas de com.scopix.periscope.model.Queue
 * 
 * @author Carlos
 */
public class QueueTest {
    
    public QueueTest() {
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
        //Crea instancia de la clase a probar
        Queue queue = new Queue();
        //Se inyecta dependencia
        String name = "name";
        queue.setName(name);

        Assert.assertNotNull(queue.getName());
        Assert.assertEquals(name, queue.getName());
    }
    
    @Test
    public void testGetId() {
        //Crea instancia de la clase a probar
        Queue queue = new Queue();
        //Se inyecta dependencia
        Integer id = 0;
        queue.setId(id);

        Assert.assertNotNull(queue.getId());
        Assert.assertEquals(id, queue.getId());
    }
}