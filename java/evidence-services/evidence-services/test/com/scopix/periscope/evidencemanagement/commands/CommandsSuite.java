/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement.commands;

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
@Suite.SuiteClasses({com.scopix.periscope.evidencemanagement.commands.AcceptAutomaticNewEvidenceCommandTest.class, 
    com.scopix.periscope.evidencemanagement.commands.GetProcessIdByExtractionPlanProcessEESCommandTest.class, 
    com.scopix.periscope.evidencemanagement.commands.AcceptNewEvidenceCommandTest.class, 
    com.scopix.periscope.evidencemanagement.commands.DeleteEvidenceAutomaticCommandTest.class})
public class CommandsSuite {

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