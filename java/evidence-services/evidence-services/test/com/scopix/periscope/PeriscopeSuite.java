/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope;

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
@Suite.SuiteClasses({com.scopix.periscope.extractionservicesserversmanagement.ExtractionservicesserversmanagementSuite.class,
    com.scopix.periscope.corporatestructuremanagement.CorporatestructuremanagementSuite.class, 
    com.scopix.periscope.extractionplanmanagement.ExtractionplanmanagementSuite.class, 
    com.scopix.periscope.evidencemanagement.EvidencemanagementSuite.class, 
    com.scopix.periscope.admin.AdminSuite.class})
public class PeriscopeSuite {

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