package com.scopix.periscope.bean;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Carlos
 */
public class PopCenterVideoEvalBeanTest {
    
    public PopCenterVideoEvalBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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
    public void testGetCenterImageEvalBean() {
        //Crea instancia de la clase a probar
        PopCenterVideoEvalBean popCenterVideoBean = new PopCenterVideoEvalBean();
        //Crea mock object
        CenterVideoEvalBean centerVideoBean = Mockito.mock(CenterVideoEvalBean.class);
        //Se inyecta dependencia
        popCenterVideoBean.setCenterVideoEvalBean(centerVideoBean);
        //Verifica ejecución
        Assert.assertNotNull(popCenterVideoBean.getCenterVideoEvalBean());
        Assert.assertEquals(centerVideoBean, popCenterVideoBean.getCenterVideoEvalBean());
    }
}