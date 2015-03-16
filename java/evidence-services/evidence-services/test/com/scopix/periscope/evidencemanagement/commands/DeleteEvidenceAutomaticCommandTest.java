/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement.commands;

import com.scopix.periscope.evidencemanagement.Evidence;
import com.scopix.periscope.extractionservicesserversmanagement.dao.ExtractionPlanDetailDAO;
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
public class DeleteEvidenceAutomaticCommandTest {
    
    public DeleteEvidenceAutomaticCommandTest() {
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

    @Test
    public void testGetDao() {
        System.out.println("getDao");
        DeleteEvidenceAutomaticCommand instance = new DeleteEvidenceAutomaticCommand();
        ExtractionPlanDetailDAO expResult = null;
        ExtractionPlanDetailDAO result = instance.getDao();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDao() {
        System.out.println("setDao");
        ExtractionPlanDetailDAO dao = null;
        DeleteEvidenceAutomaticCommand instance = new DeleteEvidenceAutomaticCommand();
        instance.setDao(dao);
        fail("The test case is a prototype.");
    }

    @Test
    public void testExecute() throws Exception {
        System.out.println("execute");
        Evidence evidence = null;
        DeleteEvidenceAutomaticCommand instance = new DeleteEvidenceAutomaticCommand();
        instance.execute(evidence);
        fail("The test case is a prototype.");
    }
}