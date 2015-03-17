package com.scopix.periscope;

import com.scopix.periscope.bean.BeanSuite;
import com.scopix.periscope.command.CommandSuite;
import com.scopix.periscope.common.CommonSuite;
import com.scopix.periscope.manager.ManagerSuite;
import com.scopix.periscope.model.ModelSuite;
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
@Suite.SuiteClasses({ModelSuite.class, CommandSuite.class, 
    CommonSuite.class, ManagerSuite.class, BeanSuite.class})
public class PeriscopeSuite {

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