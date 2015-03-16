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
 *  GetEvidenceExtractionServicesServerCommandTest.java
 * 
 *  Created on 24-09-2010, 04:08:29 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import mockup.GenericDAOMockup;
import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import mockup.GenericDAONullGetMockup;
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
public class GetEvidenceExtractionServicesServerCommandTest {

    public GetEvidenceExtractionServicesServerCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("GetEvidenceExtractionServicesServerCommandTest");
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
        Integer id = null;
        GetEvidenceExtractionServicesServerCommand instance = new GetEvidenceExtractionServicesServerCommand();
        instance.setDao(new GenericDAONullGetMockup());
        EvidenceExtractionServicesServer expResult = null;
        EvidenceExtractionServicesServer result = instance.execute(id);
        assertEquals(expResult, result);

    }

    @Test
    public void testExecute2() throws Exception {
        System.out.println("execute2");
        Integer id = 1;
        GetEvidenceExtractionServicesServerCommand instance = new GetEvidenceExtractionServicesServerCommand();
        instance.setDao(new GenericDAOMockup());
        EvidenceExtractionServicesServer expResult = new EvidenceExtractionServicesServer();
        expResult.setId(1);
        EvidenceExtractionServicesServer result = instance.execute(id);
        assertEquals(expResult.getId(), result.getId());

    }
}
