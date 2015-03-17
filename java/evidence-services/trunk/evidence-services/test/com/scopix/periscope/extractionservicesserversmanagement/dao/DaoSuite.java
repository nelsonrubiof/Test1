/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement.dao;

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
@Suite.SuiteClasses({com.scopix.periscope.extractionservicesserversmanagement.dao.ExtractionPlanDetailDAOTest.class, 
    com.scopix.periscope.extractionservicesserversmanagement.dao.SelectWorkTest.class,
    com.scopix.periscope.extractionservicesserversmanagement.dao.EvidenceExtractionServicesServerDAOTest.class, 
    com.scopix.periscope.extractionservicesserversmanagement.dao.UpdateWorkTest.class})
public class DaoSuite {

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