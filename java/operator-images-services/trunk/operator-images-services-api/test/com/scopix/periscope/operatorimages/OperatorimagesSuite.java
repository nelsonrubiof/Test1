/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.operatorimages;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Carlos
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ResultMarksDTOTest.class,
    ShapesDTOTest.class,
    MarksDTOTest.class,
    ResultMarksContainerDTOTest.class,
    MarksContainerDTOTest.class,
    DeleteContainerDTOTest.class})
public class OperatorimagesSuite {

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