package com.scopix.periscope.bean;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite de pruebas paquete com.scopix.periscope.bean
 * 
 * @author Carlos
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({RightNumberInputMetricBeanTest.class, LoginBeanTest.class, RightCountMetricBeanTest.class, 
    PopCenterImageEvalBeanTest.class, RightYesNoMetricBeanTest.class, NorthHeaderBeanTest.class, CenterVideoEvalBeanTest.class, 
    LeftMetricBeanTest.class, RightTimeMetricBeanTest.class, InicioBeanTest.class, CenterImageEvalBeanTest.class, 
    PopLeftMetricBeanTest.class, EvaluationBeanTest.class, PopCenterVideoEvalBeanTest.class})
public class BeanSuite {

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