/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement.commands;

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
    AddEvidenceRequestCommandTest.class,
    AddMetricCommandTest.class,
    AddSituationCommandTest.class,
    GetExtractionPlanCustomizingCommandTest.class,
    GetExtractionPlanCustomizingListCommandTest.class,
    CreateExtractionPlanCustomizingCommandTest.class,
    GetExtractionPlanRangeListByIdEPCCommandTest.class,
    GetExtractionPlanRangeDetailListByIdEPRCommandTest.class,
    SaveSensorsCommandTest.class,
    CleanExtractionPlanMetricsCommandTest.class,
    SaveExtractionPlanMetricCommandTest.class,
    SaveExtractionPlanCustomizingCommandTest.class,
    SaveExtractionPlanRangesCommnadTest.class,
    RemoveExtractionPlanRangesDaysCommandTest.class,
    CopyExtractionPlanRangeInDaysCommandTest.class
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
