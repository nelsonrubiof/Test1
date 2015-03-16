/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement.dao;

import java.sql.Connection;
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
public class UpdateWorkTest {
    
    public UpdateWorkTest() {
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
    public void testExecute() throws Exception {
        System.out.println("execute");
        Connection connection = null;
        UpdateWork instance = new UpdateWork();
        instance.execute(connection);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSql() {
        System.out.println("setSql");
        String sql = "";
        UpdateWork instance = new UpdateWork();
        instance.setSql(sql);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetRowsAffected() {
        System.out.println("getRowsAffected");
        UpdateWork instance = new UpdateWork();
        Integer expResult = null;
        Integer result = instance.getRowsAffected();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
}