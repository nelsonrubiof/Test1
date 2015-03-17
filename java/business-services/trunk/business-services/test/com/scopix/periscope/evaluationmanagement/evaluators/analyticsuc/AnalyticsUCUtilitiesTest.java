/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  AnalyticsUCUtilitiesTest.java
 * 
 *  Created on 12-02-2013, 05:14:36 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.evaluators.analyticsuc;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nelson
 */
public class AnalyticsUCUtilitiesTest {

    private static Logger log = Logger.getLogger(AnalyticsUCUtilitiesTest.class);

    public AnalyticsUCUtilitiesTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of parseXmlCounterSafewayAnalytics method, of class AnalyticsUCUtilities.
     */
    @Test
    public void testParseXmlCountingHTAnalytics() {
        log.info("start");
        System.out.println("parseXmlCountingHTAnalytics");
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><results open_counters=\"1\"><checkout id=\"6\" number=\"6\">"
                + "<checkout_state>closed</checkout_state><detected_number>-1</detected_number></checkout>"
                + "<checkout id=\"7\" number=\"7\"><checkout_state>open</checkout_state><detected_number>-1</detected_number>"
                + "</checkout><checkout id=\"8\" number=\"8\"><checkout_state>closed</checkout_state>"
                + "<detected_number>-1</detected_number></checkout></results>";
        CounterSafewayAnalyticsUCDetection expResult = new CounterSafewayAnalyticsUCDetection();
        expResult.setOpenCounters(1);
        AnalyticsUCUtilities instance = new AnalyticsUCUtilities();
        CounterSafewayAnalyticsUCDetection result = instance.parseXmlCounterSafewayAnalytics(xml);
        assertEquals(expResult.getOpenCounters(), result.getOpenCounters());
        log.info("end");
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
}
