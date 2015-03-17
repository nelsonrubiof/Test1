package com.scopix.periscope.common;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite de pruebas paquete com.scopix.periscope.common
 * 
 * @author Carlos
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({WebServicesUtilTest.class, ShowMessageTest.class, 
    UserCredentialManagerTest.class, XMLParserTest.class})
public class CommonSuite {

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