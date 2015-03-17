package com.scopix.periscope.bean;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zul.Radiogroup;

/**
 *
 * @author Carlos
 */
public class PopLeftMetricBeanTest {
    
    public PopLeftMetricBeanTest() {
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
    public void testOnClickNoEvalGuardar(){
        //Instancia clase a probar
        PopLeftMetricBean popLeftMetricBean = new PopLeftMetricBean();
        //Crea mock objects
        ForwardEvent forwardEvent = Mockito.mock(ForwardEvent.class);
        LeftMetricBean leftMetricBean = Mockito.mock(LeftMetricBean.class);
        //Inyecta dependencia
        popLeftMetricBean.setLeftMetricBean(leftMetricBean);
        //Ejecuta método a probar
        popLeftMetricBean.onClickNoEvalGuardar(forwardEvent);
        //Verifica ejecución
        Mockito.verify(leftMetricBean).onClickNoEvalGuardar(forwardEvent);
    }
        
    @Test
    public void testGetLeftMetricBean() {
        //Crea instancia de la clase a probar
        PopLeftMetricBean popLeftMetricBean = new PopLeftMetricBean();
        //Crea mock object
        LeftMetricBean leftMetricBean = Mockito.mock(LeftMetricBean.class);
        //Se inyecta dependencia
        popLeftMetricBean.setLeftMetricBean(leftMetricBean);
        //Verifica ejecución
        Assert.assertNotNull(popLeftMetricBean.getLeftMetricBean());
        Assert.assertEquals(leftMetricBean, popLeftMetricBean.getLeftMetricBean());
    }
    
    @Test
    public void testGetMyRadioGroup() {
        //Crea instancia de la clase a probar
        PopLeftMetricBean popLeftMetricBean = new PopLeftMetricBean();
        //Crea mock object
        Radiogroup myRadioGroup = Mockito.mock(Radiogroup.class);
        //Se inyecta dependencia
        popLeftMetricBean.setMyRadioGroup(myRadioGroup);
        //Verifica ejecución
        Assert.assertNotNull(popLeftMetricBean.getMyRadioGroup());
        Assert.assertEquals(myRadioGroup, popLeftMetricBean.getMyRadioGroup());
    }
}