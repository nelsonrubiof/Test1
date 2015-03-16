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
 *  AddMetricCommandTest.java
 * 
 *  Created on 08-09-2010, 12:24:53 PM
 * 
 */

package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.commands.AddMetricCommand;
import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import mockup.GenericDAOMockup;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nelson
 */
public class AddMetricCommandTest {

    public AddMetricCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("AddMetricCommandTest");
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
    public void testExecute() throws Exception {
        System.out.println("execute");
        Metric metric = new Metric();
        metric.setId(1);
        AddMetricCommand instance = new AddMetricCommand();
        GenericDAOMockup mock = new GenericDAOMockup();
        instance.setDao(mock);
        instance.execute(metric);
        assertEquals(mock.getBusinessObject().getId(), metric.getId());
    }

}