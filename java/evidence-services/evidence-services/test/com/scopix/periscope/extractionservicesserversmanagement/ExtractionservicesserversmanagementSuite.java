/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author nelson
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({MetricRequestTest.class, EvidenceExtractionServicesServerTest.class, 
    SituationRequestTest.class, 
    com.scopix.periscope.extractionservicesserversmanagement.dao.DaoSuite.class, 
    StoreTimeTest.class,
    ExtractionServicesServersManagerTest.class, 
    SituationRequestRangeTest.class, 
    EvidenceProviderTest.class, 
    EvidenceProviderRequestTest.class, 
    ExtractionPlanDetailTest.class, 
    ExtractionPlanTest.class,
    SituationExtractionRequestTest.class, 
    com.scopix.periscope.extractionservicesserversmanagement.commands.CommandsSuite.class, 
    SituationSensorTest.class, 
    EvidenceRequestTest.class})
public class ExtractionservicesserversmanagementSuite {

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