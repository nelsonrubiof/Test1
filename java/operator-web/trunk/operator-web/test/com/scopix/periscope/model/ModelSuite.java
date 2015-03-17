package com.scopix.periscope.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite de pruebas paquete com.scopix.periscope.model
 * 
 * @author Carlos
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({SituationTest.class, CorporateTest.class, ClientTest.class, CamaraTest.class, 
    QueueTest.class, EvidenceTest.class, MetricTest.class, EvidenceProviderTest.class, ProofTest.class})
public class ModelSuite {

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