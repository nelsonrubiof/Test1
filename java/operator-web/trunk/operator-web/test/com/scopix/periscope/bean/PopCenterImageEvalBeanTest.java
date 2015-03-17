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
public class PopCenterImageEvalBeanTest {
    
    public PopCenterImageEvalBeanTest() {
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
        PopCenterImageEvalBean popCenterImageBean = new PopCenterImageEvalBean();
        //Crea mock object
        CenterImageEvalBean centerImageBean = Mockito.mock(CenterImageEvalBean.class);
        //Se inyecta dependencia
        popCenterImageBean.setCenterImageEvalBean(centerImageBean);
        //Verifica ejecución
        Assert.assertNotNull(popCenterImageBean.getCenterImageEvalBean());
        Assert.assertEquals(centerImageBean, popCenterImageBean.getCenterImageEvalBean());
    }
}