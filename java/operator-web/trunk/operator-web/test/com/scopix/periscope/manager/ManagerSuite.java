package com.scopix.periscope.manager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite de pruebas paquete com.scopix.periscope.manager
 * 
 * @author Carlos
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({SecurityManagerTest.class, OperatorManagerTest.class})
public class ManagerSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}