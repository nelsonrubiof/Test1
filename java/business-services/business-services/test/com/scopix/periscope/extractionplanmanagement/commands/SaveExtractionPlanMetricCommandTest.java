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
 *  SaveExtractionPlanMetricCommandTest.java
 * 
 *  Created on 04-10-2010, 11:25:43 AM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceProviderDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanMetricDTO;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import java.util.ArrayList;
import mockup.GenericDAOMockup;
import mockup.ManagerMock;
import org.easymock.EasyMock;
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
public class SaveExtractionPlanMetricCommandTest {

    public SaveExtractionPlanMetricCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("SaveExtractionPlanMetricCommandTest");
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
    public void testExecute() {
        System.out.println("execute");
        //ExtractionPlanMetric planMetric = ManagerMock.genExtractionPlanMetricSinMetricTemplate(1, 1, "AS");
        GenericDAOMockup dao = new GenericDAOMockup();
        SaveExtractionPlanMetricCommand instance = new SaveExtractionPlanMetricCommand();
        instance.setDao(dao);

        ExtractionPlanCustomizing epc = EasyMock.createMock(ExtractionPlanCustomizing.class);
        ExtractionPlanMetricDTO dTO = EasyMock.createMock(ExtractionPlanMetricDTO.class);
        EasyMock.expect(dTO.getMetricVariableName()).andReturn("");
        EasyMock.expect(dTO.getEvaluationOrder()).andReturn(1);
        EasyMock.expect(dTO.getMetricTemplateId()).andReturn(1);
        EasyMock.expect(dTO.getEvidenceProviderDTOs()).andReturn(new ArrayList<EvidenceProviderDTO>());
        EasyMock.replay(dTO);
        instance.execute(epc, dTO); 
        assertEquals(dao.getBusinessObject().getId(), null);

    }
}
