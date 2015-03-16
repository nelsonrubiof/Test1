/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
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
public class SelectWorkTest {
    
    public SelectWorkTest() {
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
        SelectWork instance = new SelectWork();
        instance.execute(connection);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetResultSet() {
        System.out.println("getResultSet");
        SelectWork instance = new SelectWork();
        ResultSet expResult = null;
        ResultSet result = instance.getResultSet();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSql() {
        System.out.println("setSql");
        String sql = "";
        SelectWork instance = new SelectWork();
        instance.setSql(sql);
        fail("The test case is a prototype.");
    }
}