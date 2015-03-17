/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

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
@Suite.SuiteClasses({
    GetAreaListCommandTest.class,
    GetStoreCommandTest.class,
    SendExtractionPlanCommandTest.class,
    GetStoreListCommandTest.class,
    GetEvidenceExtractionServicesServerCommandTest.class,
    GetEvidenceProviderListCommandTest.class,
    GetSensorListCommandTest.class
})
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
