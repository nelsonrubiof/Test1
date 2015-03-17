package com.scopix.periscope.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import com.scopix.periscope.common.ShowMessage;
import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.enums.EnumEvaluationType;
import com.scopix.periscope.interfaces.CenterEvidence;
import com.scopix.periscope.interfaces.GlobalEvaluation;
import com.scopix.periscope.interfaces.RightMetricDetail;
import com.scopix.periscope.model.Evidence;
import com.scopix.periscope.model.EvidenceProvider;
import com.scopix.periscope.model.Metric;
import com.scopix.periscope.model.Situation;

/**
 * Clase de pruebas de com.scopix.periscope.bean.LeftMetricBean
 * 
 * @author Carlos
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class LeftMetricBeanTest {

    public LeftMetricBeanTest() {
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
    public void testDoAfterCompose() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Label lblNoEvaluar = Mockito.mock(Label.class);
        Session mySession = Mockito.mock(Session.class);
        Metric currentMetric = Mockito.mock(Metric.class);
        Component component = Mockito.mock(Component.class);
        Auxheader auxHeadMetrica = Mockito.mock(Auxheader.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        UserCredentialManager usrManager = Mockito.mock(UserCredentialManager.class);
        // Inyecta dependencias
        leftMetricBean.setMySession(mySession);
        leftMetricBean.setLblNoEvaluar(lblNoEvaluar);
        leftMetricBean.setEvaluacionBean(evalBean);
        leftMetricBean.setMetricasModel(metricasModel);
        leftMetricBean.setAuxHeadMetrica(auxHeadMetrica);
        leftMetricBean.setUserCredentialManager(usrManager);
        // Define comportamientos
        Mockito.when(usrManager.isAuthenticated()).thenReturn(true);
        Mockito.when(metricasModel.get(0)).thenReturn(currentMetric);
        Mockito.when(mySession.getAttribute("LOGIN")).thenReturn("login");
        Mockito.when(currentMetric.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        // Invoca método a probar
        leftMetricBean.doAfterCompose(component);
    }

    @Test
    public void testDoAfterCompose2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Label lblNoEvaluar = Mockito.mock(Label.class);
        Session mySession = Mockito.mock(Session.class);
        Metric currentMetric = Mockito.mock(Metric.class);
        Component component = Mockito.mock(Component.class);
        Auxheader auxHeadMetrica = Mockito.mock(Auxheader.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        UserCredentialManager usrManager = Mockito.mock(UserCredentialManager.class);
        // Inyecta dependencias
        leftMetricBean.setMySession(mySession);
        leftMetricBean.setLblNoEvaluar(lblNoEvaluar);
        leftMetricBean.setEvaluacionBean(evalBean);
        leftMetricBean.setMetricasModel(metricasModel);
        leftMetricBean.setAuxHeadMetrica(auxHeadMetrica);
        leftMetricBean.setUserCredentialManager(usrManager);
        // Define comportamientos
        Mockito.when(usrManager.isAuthenticated()).thenReturn(true);
        Mockito.when(metricasModel.get(0)).thenReturn(currentMetric);
        Mockito.when(mySession.getAttribute("LOGIN")).thenReturn("login");
        Mockito.when(currentMetric.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        Mockito.when(metricasModel.size()).thenReturn(1);
        // Invoca método a probar
        leftMetricBean.doAfterCompose(component);
    }

    /**
     * Define página a ser mostrada en la parte derecha para crear una instancia del correspondiente bean dependiendo del tipo de
     * métrica
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 21/02/2013
     */
    @Test
    public void testPrepareRightMetric() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();

        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        String metricType = EnumEvaluationType.COUNTING.toString();

        Label lblPlayerTime = Mockito.mock(Label.class);
        leftMetricBean.setLblPlayerTime(lblPlayerTime);
        // Define comportamiento
        Mockito.when(lblPlayerTime.getValue()).thenReturn("-1");

        leftMetricBean.setEvaluacionBean(evalBean);
        leftMetricBean.prepareRightMetric(metricType);
    }

    @Test
    public void testPrepareRightMetric2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();

        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        String metricType = EnumEvaluationType.MEASURE_TIME.toString();

        Label lblPlayerTime = Mockito.mock(Label.class);
        leftMetricBean.setLblPlayerTime(lblPlayerTime);
        // Define comportamiento
        Mockito.when(lblPlayerTime.getValue()).thenReturn("-1");

        leftMetricBean.setEvaluacionBean(evalBean);
        leftMetricBean.prepareRightMetric(metricType);
    }

    @Test
    public void testPrepareRightMetric3() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();

        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        String metricType = EnumEvaluationType.YES_NO.toString();

        Label lblPlayerTime = Mockito.mock(Label.class);
        leftMetricBean.setLblPlayerTime(lblPlayerTime);
        // Define comportamiento
        Mockito.when(lblPlayerTime.getValue()).thenReturn("-1");

        leftMetricBean.setEvaluacionBean(evalBean);
        leftMetricBean.prepareRightMetric(metricType);
    }

    @Test
    public void testPrepareRightMetric4() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();

        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        String metricType = EnumEvaluationType.NUMBER_INPUT.toString();

        Label lblPlayerTime = Mockito.mock(Label.class);
        leftMetricBean.setLblPlayerTime(lblPlayerTime);
        // Define comportamiento
        Mockito.when(lblPlayerTime.getValue()).thenReturn("-1");

        leftMetricBean.setEvaluacionBean(evalBean);
        leftMetricBean.prepareRightMetric(metricType);
    }

    @Test(expected = NullPointerException.class)
    public void testPrepareRightMetric5() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();

        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        String metricType = " ";

        Label lblPlayerTime = Mockito.mock(Label.class);
        leftMetricBean.setLblPlayerTime(lblPlayerTime);
        // Define comportamiento
        Mockito.when(lblPlayerTime.getValue()).thenReturn("-1");

        leftMetricBean.setEvaluacionBean(evalBean);
        leftMetricBean.prepareRightMetric(metricType);
    }

    @Test
    public void testCreateRightMetricBean() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        String metricType = EnumEvaluationType.COUNTING.toString();

        RightMetricDetail rightMetricDetail = leftMetricBean.createRightMetricBean(metricType);
        Assert.assertNotNull(rightMetricDetail);
    }

    @Test
    public void testCreateRightMetricBean2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        String metricType = EnumEvaluationType.MEASURE_TIME.toString();

        RightMetricDetail rightMetricDetail = leftMetricBean.createRightMetricBean(metricType);
        Assert.assertNotNull(rightMetricDetail);
    }

    @Test
    public void testCreateRightMetricBean3() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        String metricType = EnumEvaluationType.YES_NO.toString();

        RightMetricDetail rightMetricDetail = leftMetricBean.createRightMetricBean(metricType);
        Assert.assertNotNull(rightMetricDetail);
    }

    @Test
    public void testCreateRightMetricBean4() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        String metricType = EnumEvaluationType.NUMBER_INPUT.toString();

        RightMetricDetail rightMetricDetail = leftMetricBean.createRightMetricBean(metricType);
        Assert.assertNotNull(rightMetricDetail);
    }

    @Test
    public void testCreateRightMetricBean5() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        String metricType = " ";

        RightMetricDetail rightMetricDetail = leftMetricBean.createRightMetricBean(metricType);
        Assert.assertNull(rightMetricDetail);
    }

    @Test
    public void testPrepareRightMetricDetail() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Include include = Mockito.mock(Include.class);
        Label lblPlayerTime = Mockito.mock(Label.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencia
        leftMetricBean.setEvaluacionBean(evalBean);
        leftMetricBean.setLblPlayerTime(lblPlayerTime);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamiento
        Mockito.when(lblPlayerTime.getValue()).thenReturn("-1");
        // Invoca método a probar
        leftMetricBean.prepareRightMetricDetail(include);
        // Verifica ejecución
        Mockito.verify(rightMetricDetail).setMetric(Matchers.any(Metric.class));
        Mockito.verify(rightMetricDetail).bindComponentAndParent(include, leftMetricBean);
        Mockito.verify(evalBean).setRightInclude(include);
    }

    @Test
    public void testPrepareRightMetricDetail2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Include include = Mockito.mock(Include.class);
        Label lblPlayerTime = Mockito.mock(Label.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencia
        leftMetricBean.setEvaluacionBean(evalBean);
        leftMetricBean.setLblPlayerTime(lblPlayerTime);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamiento
        Mockito.when(lblPlayerTime.getValue()).thenReturn("1");
        // Invoca método a probar
        leftMetricBean.prepareRightMetricDetail(include);
        // Verifica ejecución
        Mockito.verify(rightMetricDetail).setMetric(Matchers.any(Metric.class));
        Mockito.verify(rightMetricDetail).bindComponentAndParent(include, leftMetricBean);
        Mockito.verify(evalBean).setRightInclude(include);
    }

    /**
     * Valida si la métrica fue evaluada
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param selectedIndex
     * @param onClickFilaMetrica
     * @since 6.0
     * @date 21/02/2013
     */
    @Test(expected = NullPointerException.class)
    public void testValidateMetricEval() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        // Inyecta dependencia
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamiento
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(Matchers.anyInt())).thenReturn(metric);
        Mockito.when(metric.isEvaluada()).thenReturn(true);
        Mockito.when(metric.isCantEval()).thenReturn(true);
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        // Invoca método a probar
        leftMetricBean.validateMetricEval(1);
    }

    /**
     * Valida si la métrica fue evaluada
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param selectedIndex
     * @param onClickFilaMetrica
     * @since 6.0
     * @date 21/02/2013
     */
    @Test(expected = NullPointerException.class)
    public void testValidateMetricEval2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        // Inyecta dependencia
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamiento
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.getSize()).thenReturn(1);
        Mockito.when(metricasModel.get(Matchers.anyInt())).thenReturn(metric);
        Mockito.when(metric.isEvaluada()).thenReturn(true);
        Mockito.when(metric.isCantEval()).thenReturn(false);
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        // Invoca método a probar
        leftMetricBean.validateMetricEval(1);
    }

    @Test(expected = NullPointerException.class)
    public void testValidateMetricEval3() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        // Inyecta dependencia
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamiento
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.getSize()).thenReturn(1);
        Mockito.when(metricasModel.get(Matchers.anyInt())).thenReturn(metric);
        Mockito.when(metric.isEvaluada()).thenReturn(false);
        Mockito.when(metric.isCantEval()).thenReturn(true);
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        // Invoca método a probar
        leftMetricBean.validateMetricEval(1);
    }

    @Test(expected = NullPointerException.class)
    public void testValidateMetricEval4() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        // Inyecta dependencia
        leftMetricBean.setShowMessage(showMessage);
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamiento
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.getSize()).thenReturn(1);
        Mockito.when(metricasModel.get(Matchers.anyInt())).thenReturn(metric);
        Mockito.when(metric.isEvaluada()).thenReturn(false);
        Mockito.when(metric.isCantEval()).thenReturn(false);
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        // Invoca método a probar
        leftMetricBean.validateMetricEval(1);
        // Verifica ejecución
        Mockito.verify(showMessage).mostrarMensaje(Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    @Test(expected = NullPointerException.class)
    public void testValidateMetricEval5() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Integer currentIndex = 0;
        Metric metric = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        // Inyecta dependencia
        leftMetricBean.setCurrentIndex(currentIndex);
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamiento
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(Matchers.anyInt())).thenReturn(metric);
        Mockito.when(metric.isEvaluada()).thenReturn(false);
        Mockito.when(metric.isCantEval()).thenReturn(true);
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        // Invoca método a probar
        leftMetricBean.validateMetricEval(0);
    }

    @Test(expected = NullPointerException.class)
    public void testValidateMetricEval6() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Integer currentIndex = 1;
        Metric metric = Mockito.mock(Metric.class);
        Auxheader auxHeader = Mockito.mock(Auxheader.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        GlobalEvaluation evaluacionBean = Mockito.mock(GlobalEvaluation.class);
        // Inyecta dependencia
        leftMetricBean.setAuxHeadMetrica(auxHeader);
        leftMetricBean.setCurrentIndex(currentIndex);
        leftMetricBean.setMetricasModel(metricasModel);
        leftMetricBean.setEvaluacionBean(evaluacionBean);
        // Define comportamiento
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(Matchers.anyInt())).thenReturn(metric);
        Mockito.when(metric.isEvaluada()).thenReturn(false);
        Mockito.when(metric.isCantEval()).thenReturn(true);
        Mockito.when(metric.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        Mockito.when(metric.getNumMetrica()).thenReturn("0");
        // Invoca método a probar
        leftMetricBean.validateMetricEval(0);
    }

    @Test(expected = NullPointerException.class)
    public void testValidateOneEvaluation() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Auxheader auxHeader = Mockito.mock(Auxheader.class);
        Metric metricaActual = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        // Inyecta dependencia
        leftMetricBean.setCurrentIndex(0);
        leftMetricBean.setTextoMetrica("Texto");
        leftMetricBean.setEvaluacionBean(evalBean);
        leftMetricBean.setAuxHeadMetrica(auxHeader);
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamiento
        Mockito.when(metricasModel.get(Matchers.anyInt())).thenReturn(metricaActual);
        Mockito.when(metricaActual.isEvaluada()).thenReturn(true);
        Mockito.when(metricaActual.getName()).thenReturn("current metric");
        Mockito.when(metricaActual.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        Mockito.when(metricaActual.getNumMetrica()).thenReturn("0");
        // Invoca método a probar
        leftMetricBean.validateOneEvaluation(0);
    }

    @Test(expected = NullPointerException.class)
    public void testValidateOneEvaluation2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Auxheader auxHeader = Mockito.mock(Auxheader.class);
        Metric metricaActual = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        // Inyecta dependencia
        leftMetricBean.setCurrentIndex(0);
        leftMetricBean.setTextoMetrica("Texto");
        leftMetricBean.setEvaluacionBean(evalBean);
        leftMetricBean.setAuxHeadMetrica(auxHeader);
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamiento
        Mockito.when(metricasModel.get(0)).thenReturn(metricaActual);
        Mockito.when(metricaActual.isEvaluada()).thenReturn(false);
        Mockito.when(metricaActual.isCantEval()).thenReturn(true);
        Mockito.when(metricaActual.getName()).thenReturn("current metric");
        Mockito.when(metricaActual.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        Mockito.when(metricaActual.getNumMetrica()).thenReturn("0");
        // Invoca método a probar
        leftMetricBean.validateOneEvaluation(0);
    }

    @Test(expected = NullPointerException.class)
    public void testValidateOneEvaluation3() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Listbox listBox = Mockito.mock(Listbox.class);
        Auxheader auxHeader = Mockito.mock(Auxheader.class);
        Metric metricaActual = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        // Inyecta dependencia
        leftMetricBean.setCurrentIndex(0);
        leftMetricBean.setTextoMetrica("Texto");
        leftMetricBean.setMetricasListBox(listBox);
        leftMetricBean.setEvaluacionBean(evalBean);
        leftMetricBean.setAuxHeadMetrica(auxHeader);
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamiento
        Mockito.when(metricasModel.get(0)).thenReturn(metricaActual);
        Mockito.when(metricaActual.isEvaluada()).thenReturn(false);
        Mockito.when(metricaActual.isCantEval()).thenReturn(false);
        Mockito.when(metricaActual.getName()).thenReturn("current metric");
        Mockito.when(metricaActual.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        Mockito.when(metricaActual.getNumMetrica()).thenReturn("0");
        // Invoca método a probar
        leftMetricBean.validateOneEvaluation(0);
        // Espera NullPointerException por Messagebox.show();
    }

    @Test(expected = NullPointerException.class)
    public void testSetCurrentRow() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Metric metricaActual = Mockito.mock(Metric.class);
        Listbox listBox = Mockito.mock(Listbox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasListBox(listBox);
        leftMetricBean.setShowMessage(showMessage);
        // Invoca método a probar
        leftMetricBean.setCurrentRow(metricaActual);
        // Verifica ejecución
        Mockito.verify(showMessage).mostrarMensaje(Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    @Test(expected = NullPointerException.class)
    public void testSetCountMetricsToZero() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencia
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamiento
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(metrica.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        Mockito.when(metrica.getCirclesInfo()).thenReturn(circlesInfo);
        Mockito.when(metrica.getSquaresInfo()).thenReturn(squaresInfo);
        // Invoca método a probar
        leftMetricBean.setCountMetricsToZero("0");
        // Verifica ejecución
        Mockito.verify(metricasModel).set(0, metrica);
    }

    @Test
    public void testSetCountMetricsToZero2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        Button btnEnviarEval = Mockito.mock(Button.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencia
        leftMetricBean.setMetricasModel(metricasModel);
        leftMetricBean.setBtnEnviarEval(btnEnviarEval);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamiento
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(rightMetricDetail.getBtnEnviarFinalizar()).thenReturn(btnEnviarEval);
        Mockito.when(metrica.getType()).thenReturn(EnumEvaluationType.YES_NO.toString());
        // Invoca método a probar
        leftMetricBean.setCountMetricsToZero("0");
    }

    @Test
    public void testSetCountMetricsToZero3() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        Button btnEnviarEval = Mockito.mock(Button.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencia
        leftMetricBean.setBtnEnviarEval(btnEnviarEval);
        leftMetricBean.setMetricasModel(metricasModel);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamiento
        Mockito.when(metricasModel.size()).thenReturn(0);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(metrica.getType()).thenReturn(EnumEvaluationType.YES_NO.toString());
        Mockito.when(rightMetricDetail.getBtnEnviarFinalizar()).thenReturn(btnEnviarEval);
        // Invoca método a probar
        leftMetricBean.setCountMetricsToZero("0");
    }

    @Test(expected = NullPointerException.class)
    public void testSetCountMetricsToZero4() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        Evidence evidence = Mockito.mock(Evidence.class);
        List<Evidence> lstEvidencias = Mockito.mock(List.class);
        Iterator<Evidence> iterator = Mockito.mock(Iterator.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        EvidenceProvider evidenceProvider = Mockito.mock(EvidenceProvider.class);
        // Inyecta dependencia
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamiento
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metrica.isMultiple()).thenReturn(true);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(metrica.getCirclesInfo()).thenReturn(circlesInfo);
        Mockito.when(metrica.getSquaresInfo()).thenReturn(squaresInfo);
        Mockito.when(metrica.getStartEvaluationTime()).thenReturn(new Date());
        Mockito.when(metrica.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        Mockito.when(lstEvidencias.iterator()).thenReturn(iterator);
        Mockito.when(metrica.getEvidencias()).thenReturn(lstEvidencias);
        Mockito.when(iterator.hasNext()).thenReturn(true, false);
        Mockito.when(iterator.next()).thenReturn(evidence);
        Mockito.when(evidenceProvider.getId()).thenReturn(1);
        Mockito.when(evidence.getEvidenceProvider()).thenReturn(evidenceProvider);
        // Invoca método a probar
        leftMetricBean.setCountMetricsToZero("0");
        // Verifica ejecución
        Mockito.verify(metricasModel).set(0, metrica);
    }

    /**
     * Actualiza el valor de la métrica
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param value
     * @since 6.0
     * @date 21/02/2013
     */
    @Test
    public void testUpdateMetricValue() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Button btnEnviarEval = Mockito.mock(Button.class);
        Metric currentMetric = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        leftMetricBean.setBtnEnviarEval(btnEnviarEval);
        leftMetricBean.setMetricasModel(metricasModel);
        leftMetricBean.setCurrentMetric(currentMetric);
        leftMetricBean.setBtnEnviarEval(btnEnviarEval);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(0);
        Mockito.when(rightMetricDetail.getBtnEnviarFinalizar()).thenReturn(btnEnviarEval);
        // Invoca método a probar
        leftMetricBean.updateMetricValue("0");
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateMetricValue2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        Button btnEnviarFinalizar = Mockito.mock(Button.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasModel(metricasModel);
        leftMetricBean.setCurrentMetric(metrica);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(Matchers.anyInt())).thenReturn(metrica);
        Mockito.when(rightMetricDetail.getBtnEnviarFinalizar()).thenReturn(btnEnviarFinalizar);
        // Invoca método a probar
        leftMetricBean.updateMetricValue("No definido");
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateMetricValue3() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);

        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasModel(metricasModel);
        leftMetricBean.setCurrentMetric(metrica);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(Matchers.anyInt())).thenReturn(metrica);
        // Invoca método a probar
        leftMetricBean.updateMetricValue("0");
    }

    @Test
    public void testUpdateMetricValue4() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        Metric currentMetric = Mockito.mock(Metric.class);
        Button btnEnviarEval = Mockito.mock(Button.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        leftMetricBean.setBtnEnviarEval(btnEnviarEval);
        leftMetricBean.setMetricasModel(metricasModel);
        leftMetricBean.setCurrentMetric(currentMetric);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(Matchers.anyInt())).thenReturn(metrica);
        Mockito.when(rightMetricDetail.getBtnEnviarFinalizar()).thenReturn(btnEnviarEval);
        // Invoca método a probar
        leftMetricBean.updateMetricValue("0");
    }

    @Test(expected = NullPointerException.class)
    public void testOnClickNoEvalGuardar() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Label lblNoEvaluar = Mockito.mock(Label.class);
        Radio selectedRadio = Mockito.mock(Radio.class);
        Label lblValorMetrica = Mockito.mock(Label.class);
        Metric currentMetric = Mockito.mock(Metric.class);
        Radiogroup myRadioGroup = Mockito.mock(Radiogroup.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        PopLeftMetricBean popLeftMetricBean = Mockito.mock(PopLeftMetricBean.class);
        // Inyecta dependencias
        leftMetricBean.setCurrentIndex(0);
        leftMetricBean.setLblNoEvaluar(lblNoEvaluar);
        leftMetricBean.setMetricasModel(metricasModel);
        leftMetricBean.setCurrentMetric(currentMetric);
        leftMetricBean.setPopLeftMetricBean(popLeftMetricBean);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamiento
        Mockito.when(lblNoEvaluar.getValue()).thenReturn("NO_EVAL");
        Mockito.when(selectedRadio.getLabel()).thenReturn("label");
        Mockito.when(popLeftMetricBean.getMyRadioGroup()).thenReturn(myRadioGroup);
        Mockito.when(myRadioGroup.getSelectedItem()).thenReturn(selectedRadio);
        Mockito.when(rightMetricDetail.getLblValorMetrica()).thenReturn(lblValorMetrica);
        Mockito.when(currentMetric.getNumMetrica()).thenReturn("0");
        // Invoca método a probar
        leftMetricBean.onClickNoEvalGuardar(null);
        // Verifica ejecución
        Mockito.verify(lblValorMetrica).setValue("label");
    }

    @Test(expected = NullPointerException.class)
    public void testOnClickNoEvalGuardar2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        Label lblNoEvaluar = Mockito.mock(Label.class);
        Radio selectedRadio = Mockito.mock(Radio.class);
        Metric currentMetric = Mockito.mock(Metric.class);
        Label lblValorMetrica = Mockito.mock(Label.class);
        Radiogroup myRadioGroup = Mockito.mock(Radiogroup.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        PopLeftMetricBean popLeftMetricBean = Mockito.mock(PopLeftMetricBean.class);
        // Inyecta dependencias
        leftMetricBean.setLblNoEvaluar(lblNoEvaluar);
        leftMetricBean.setCurrentMetric(currentMetric);
        leftMetricBean.setMetricasModel(metricasModel);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        leftMetricBean.setPopLeftMetricBean(popLeftMetricBean);
        // Define comportamiento
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(lblNoEvaluar.getValue()).thenReturn("NO_EVAL_ALL");
        Mockito.when(selectedRadio.getLabel()).thenReturn("label");
        Mockito.when(popLeftMetricBean.getMyRadioGroup()).thenReturn(myRadioGroup);
        Mockito.when(myRadioGroup.getSelectedItem()).thenReturn(selectedRadio);
        Mockito.when(rightMetricDetail.getLblValorMetrica()).thenReturn(lblValorMetrica);
        Mockito.when(currentMetric.getNumMetrica()).thenReturn("0");
        // Invoca método a probar
        leftMetricBean.onClickNoEvalGuardar(null);
        // Verifica ejecución
        Mockito.verify(lblValorMetrica).setValue("label");
    }

    @Test
    public void testOnClickNoEvalGuardar3() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        Label lblNoEvaluar = Mockito.mock(Label.class);
        Radio selectedRadio = Mockito.mock(Radio.class);
        Label lblValorMetrica = Mockito.mock(Label.class);
        Radiogroup myRadioGroup = Mockito.mock(Radiogroup.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        PopLeftMetricBean popLeftBean = Mockito.mock(PopLeftMetricBean.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        leftMetricBean.setLblNoEvaluar(lblNoEvaluar);
        leftMetricBean.setPopLeftMetricBean(popLeftBean);
        // leftMetricBean.setMyRadioGroup(myRadioGroup);
        leftMetricBean.setMetricasModel(metricasModel);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamiento
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(lblNoEvaluar.getValue()).thenReturn("X");
        Mockito.when(selectedRadio.getLabel()).thenReturn("label");
        Mockito.when(popLeftBean.getMyRadioGroup()).thenReturn(myRadioGroup);
        Mockito.when(myRadioGroup.getSelectedItem()).thenReturn(selectedRadio);
        Mockito.when(rightMetricDetail.getLblValorMetrica()).thenReturn(lblValorMetrica);
        // Invoca método a probar
        leftMetricBean.onClickNoEvalGuardar(null);
        // Verifica ejecución
        Mockito.verify(lblValorMetrica).setValue("label");
    }

    @Test
    public void testOnClickNoEvalGuardar4() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Label lblNoEvaluar = Mockito.mock(Label.class);
        Radio selectedRadio = Mockito.mock(Radio.class);
        Label lblValorMetrica = Mockito.mock(Label.class);
        Radiogroup myRadioGroup = Mockito.mock(Radiogroup.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        PopLeftMetricBean popLeftBean = Mockito.mock(PopLeftMetricBean.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        leftMetricBean.setLblNoEvaluar(lblNoEvaluar);
        leftMetricBean.setPopLeftMetricBean(popLeftBean);
        // leftMetricBean.setMyRadioGroup(myRadioGroup);
        leftMetricBean.setMetricasModel(metricasModel);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamiento
        Mockito.when(metricasModel.size()).thenReturn(0);
        Mockito.when(lblNoEvaluar.getValue()).thenReturn("X");
        Mockito.when(selectedRadio.getLabel()).thenReturn("label");
        Mockito.when(popLeftBean.getMyRadioGroup()).thenReturn(myRadioGroup);
        Mockito.when(myRadioGroup.getSelectedItem()).thenReturn(selectedRadio);
        Mockito.when(rightMetricDetail.getLblValorMetrica()).thenReturn(lblValorMetrica);
        // Invoca método a probar
        leftMetricBean.onClickNoEvalGuardar(null);
        // Verifica ejecución
        Mockito.verify(lblValorMetrica).setValue("label");
    }

    @Test
    public void testGetMetricasListBox() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Listbox metricasListBox = Mockito.mock(Listbox.class);
        // Se inyecta dependencia
        leftMetricBean.setMetricasListBox(metricasListBox);

        Assert.assertNotNull(leftMetricBean.getMetricasListBox());
        Assert.assertEquals(metricasListBox, leftMetricBean.getMetricasListBox());
    }

    @Test
    public void testGetMetricasModel() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        // Se inyecta dependencia
        leftMetricBean.setMetricasModel(metricasModel);

        Assert.assertNotNull(leftMetricBean.getMetricasModel());
        Assert.assertEquals(metricasModel, leftMetricBean.getMetricasModel());
    }

    @Test
    public void testGetTxtObsCalidad() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Textbox txtObsCalidad = Mockito.mock(Textbox.class);
        // Se inyecta dependencia
        leftMetricBean.setTxtObsCalidad(txtObsCalidad);

        Assert.assertNotNull(leftMetricBean.getTxtObsCalidad());
        Assert.assertEquals(txtObsCalidad, leftMetricBean.getTxtObsCalidad());
    }

    @Test
    public void testGetEvaluacionBean() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        // Se inyecta dependencia
        leftMetricBean.setEvaluacionBean(evalBean);

        Assert.assertNotNull(leftMetricBean.getGlobalEvaluation());
        Assert.assertEquals(evalBean, leftMetricBean.getGlobalEvaluation());
    }

    @Test
    public void testGetRightMetricDetail() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        RightMetricDetail rightMetric = Mockito.mock(RightMetricDetail.class);
        // Se inyecta dependencia
        leftMetricBean.setRightMetricDetail(rightMetric);

        Assert.assertNotNull(leftMetricBean.getRightMetricDetail());
        Assert.assertEquals(rightMetric, leftMetricBean.getRightMetricDetail());
    }

    @Test
    public void testGetAuxHeadMetrica() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Auxheader auxHeader = Mockito.mock(Auxheader.class);
        // Se inyecta dependencia
        leftMetricBean.setAuxHeadMetrica(auxHeader);

        Assert.assertNotNull(leftMetricBean.getAuxHeadMetrica());
        Assert.assertEquals(auxHeader, leftMetricBean.getAuxHeadMetrica());
    }

    @Test
    public void testGetQualityObservation() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Textbox txtObsCalidad = Mockito.mock(Textbox.class);
        // Se inyecta dependencia
        leftMetricBean.setTxtObsCalidad(txtObsCalidad);
        // Define comportamiento
        Mockito.when(txtObsCalidad.getValue()).thenReturn("Obs");
        // Verifica ejecución
        Assert.assertEquals("Obs", leftMetricBean.getQualityObservation());
    }

    @Test
    public void testGetTextoMetrica() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Se inyecta dependencia
        String textoMetrica = "texto";
        leftMetricBean.setTextoMetrica(textoMetrica);

        Assert.assertNotNull(leftMetricBean.getTextoMetrica());
        Assert.assertEquals(textoMetrica, leftMetricBean.getTextoMetrica());
    }

    @Test
    public void testGetCurrentMetric() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Metric currentMetric = Mockito.mock(Metric.class);
        // Se inyecta dependencia
        leftMetricBean.setCurrentMetric(currentMetric);

        Assert.assertNotNull(leftMetricBean.getCurrentMetric());
        Assert.assertEquals(currentMetric, leftMetricBean.getCurrentMetric());
    }

    @Test
    public void testGetShowMessage() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Se inyecta dependencia
        leftMetricBean.setShowMessage(showMessage);

        Assert.assertNotNull(leftMetricBean.getShowMessage());
        Assert.assertEquals(showMessage, leftMetricBean.getShowMessage());
    }

    @Test
    public void testGetShowMessage2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        Assert.assertNotNull(leftMetricBean.getShowMessage());
    }

    @Test
    public void testGetCurrentIndex() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        int currentIndex = 1;
        // Se inyecta dependencia
        leftMetricBean.setCurrentIndex(currentIndex);

        Assert.assertNotNull(leftMetricBean.getCurrentIndex());
        Assert.assertEquals(currentIndex, leftMetricBean.getCurrentIndex());
    }

    @Test
    public void testGetLblNoEvaluar() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Label lblNoEvaluar = Mockito.mock(Label.class);
        // Se inyecta dependencia
        leftMetricBean.setLblNoEvaluar(lblNoEvaluar);

        Assert.assertNotNull(leftMetricBean.getLblNoEvaluar());
        Assert.assertEquals(lblNoEvaluar, leftMetricBean.getLblNoEvaluar());
    }

    @Test
    public void testGetUserCredentialManager() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        UserCredentialManager userCredentialMgr = Mockito.mock(UserCredentialManager.class);
        // Se inyecta dependencia
        leftMetricBean.setUserCredentialManager(userCredentialMgr);

        Assert.assertNotNull(leftMetricBean.getUserCredentialManager());
        Assert.assertEquals(userCredentialMgr, leftMetricBean.getUserCredentialManager());
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserCredentialManager2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        Assert.assertNotNull(leftMetricBean.getUserCredentialManager());
    }

    @Test
    public void testGetExecution() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        Execution execution = Mockito.mock(Execution.class);
        // Se inyecta dependencia
        leftMetricBean.setExecution(execution);

        Assert.assertNotNull(leftMetricBean.getExecution());
        Assert.assertEquals(execution, leftMetricBean.getExecution());
    }

    @Test(expected = NullPointerException.class)
    public void testProcessNoEvalAllGuardar() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Date fechaActual = new Date();
        String cantEvalObs = "falla de sistema";
        Metric metrica = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(metrica.getCirclesInfo()).thenReturn(circlesInfo);
        Mockito.when(metrica.getSquaresInfo()).thenReturn(squaresInfo);
        // Invoca método a probar, lanza nullPointer por Clients.evalJavaScript
        leftMetricBean.processNoEvalAllGuardar(cantEvalObs, fechaActual);
    }

    @Test(expected = NullPointerException.class)
    public void testProcessNoEvalAllGuardar2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Date fechaActual = new Date();
        String cantEvalObs = "falla de sistema";
        Metric metrica = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(metrica.getCirclesInfo()).thenReturn(circlesInfo);
        Mockito.when(metrica.getSquaresInfo()).thenReturn(squaresInfo);
        Mockito.when(metrica.getStartEvaluationTime()).thenReturn(new Date());
        // Invoca método a probar, lanza nullPointer por Clients.evalJavaScript
        leftMetricBean.processNoEvalAllGuardar(cantEvalObs, fechaActual);
    }

    @Test
    public void testProcessNoEvalAllGuardar3() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Date fechaActual = new Date();
        String cantEvalObs = "falla de sistema";
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(0);
        // Invoca método a probar, lanza nullPointer por Clients.evalJavaScript
        leftMetricBean.processNoEvalAllGuardar(cantEvalObs, fechaActual);
    }

    @Test(expected = NullPointerException.class)
    public void testProcessNoEvalGuardar() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Date fechaActual = new Date();
        String cantEvalObs = "falla de sistema";
        Metric currentMetric = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        leftMetricBean.setCurrentIndex(0);
        leftMetricBean.setCurrentMetric(currentMetric);
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamientos
        Mockito.when(currentMetric.getNumMetrica()).thenReturn("1");
        Mockito.when(currentMetric.getCirclesInfo()).thenReturn(circlesInfo);
        Mockito.when(currentMetric.getSquaresInfo()).thenReturn(squaresInfo);
        // Invoca método a probar, lanza nullPointer por Clients.evalJavaScript
        leftMetricBean.processNoEvalGuardar(cantEvalObs, fechaActual);
    }

    @Test
    public void testGetOperatorManager() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", leftMetricBean.getOperatorManager());
    }

    @Test
    public void testIsAllEvaluated() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Inyecta dependencia
        leftMetricBean.setAllEvaluated(true);
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", leftMetricBean.isAllEvaluated());
    }

    @Test
    public void testGetLblCurrentEvidenceType() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Se crea mock object
        Label lblCurrentEvidenceType = Mockito.mock(Label.class);
        // Se inyecta dependencia
        leftMetricBean.setLblCurrentEvidenceType(lblCurrentEvidenceType);
        // Valida asignación
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", leftMetricBean.getLblCurrentEvidenceType());
        Assert.assertEquals("Se espera que el atributo sea el mismo objeto asignado", lblCurrentEvidenceType,
                leftMetricBean.getLblCurrentEvidenceType());
    }

    @Test
    public void testSetAllCounting() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Inyecta dependencia
        leftMetricBean.setAllCounting(true);
        Assert.assertNotNull("Se espera que el objeto retornado no sea nulo", leftMetricBean.isAllCounting());
    }

    @Test(expected = NullPointerException.class)
    public void testSetCountMetricsToZeroCamera() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        int cameraId = 1;
        Metric metrica = Mockito.mock(Metric.class);
        Evidence evidence = Mockito.mock(Evidence.class);
        List<Evidence> lstEvidencias = Mockito.mock(List.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        EvidenceProvider evidenceProvider = Mockito.mock(EvidenceProvider.class);
        // Inyecta dependencia
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(lstEvidencias.get(0)).thenReturn(evidence);
        Mockito.when(evidenceProvider.getId()).thenReturn(cameraId);
        Mockito.when(metrica.getCirclesInfo()).thenReturn(circlesInfo);
        Mockito.when(metrica.getEvidencias()).thenReturn(lstEvidencias);
        Mockito.when(evidence.getEvidenceProvider()).thenReturn(evidenceProvider);
        Mockito.when(metrica.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        // Invoca método a probar, lanza nullPointer por Clients.evalJavaScript
        leftMetricBean.setCountMetricsToZeroCamera(cameraId);
    }

    @Test
    public void testSetCountMetricsToZeroCamera2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        int cameraId = 1;
        Metric metrica = Mockito.mock(Metric.class);
        Button btnEnviarEval = Mockito.mock(Button.class);
        Button btnEnviarFinalizar = Mockito.mock(Button.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencia
        leftMetricBean.setMetricasModel(metricasModel);
        // Inyecta dependencias
        leftMetricBean.setBtnEnviarEval(btnEnviarEval);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(0);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(rightMetricDetail.getBtnEnviarFinalizar()).thenReturn(btnEnviarFinalizar);
        // Invoca método a probar
        leftMetricBean.setCountMetricsToZeroCamera(cameraId);
        // Verifica ejecución
        Mockito.verify(btnEnviarEval).setDisabled(false);
        Mockito.verify(btnEnviarFinalizar).setDisabled(false);
    }

    @Test
    public void testSetCountMetricsToZeroCamera3() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        int cameraId = 1;
        Metric metrica = Mockito.mock(Metric.class);
        Button btnEnviarEval = Mockito.mock(Button.class);
        Button btnEnviarFinalizar = Mockito.mock(Button.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencia
        leftMetricBean.setMetricasModel(metricasModel);
        // Inyecta dependencias
        leftMetricBean.setBtnEnviarEval(btnEnviarEval);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(metrica.getType()).thenReturn(EnumEvaluationType.YES_NO.toString());
        Mockito.when(rightMetricDetail.getBtnEnviarFinalizar()).thenReturn(btnEnviarFinalizar);
        // Invoca método a probar
        leftMetricBean.setCountMetricsToZeroCamera(cameraId);
        // Verifica ejecución
        Mockito.verify(btnEnviarEval, Mockito.never()).setDisabled(false);
        Mockito.verify(btnEnviarFinalizar, Mockito.never()).setDisabled(false);
    }

    @Test
    public void testSetCountMetricsToZeroCamera4() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        int cameraId = 1;
        Metric metrica = Mockito.mock(Metric.class);
        Evidence evidence = Mockito.mock(Evidence.class);
        Button btnEnviarEval = Mockito.mock(Button.class);
        Button btnEnviarFinalizar = Mockito.mock(Button.class);
        List<Evidence> lstEvidencias = Mockito.mock(List.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        EvidenceProvider evidenceProvider = Mockito.mock(EvidenceProvider.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencia
        leftMetricBean.setMetricasModel(metricasModel);
        leftMetricBean.setBtnEnviarEval(btnEnviarEval);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(lstEvidencias.get(0)).thenReturn(evidence);
        Mockito.when(evidenceProvider.getId()).thenReturn(2);
        Mockito.when(metrica.getCirclesInfo()).thenReturn(circlesInfo);
        Mockito.when(metrica.getEvidencias()).thenReturn(lstEvidencias);
        Mockito.when(evidence.getEvidenceProvider()).thenReturn(evidenceProvider);
        Mockito.when(metrica.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        Mockito.when(rightMetricDetail.getBtnEnviarFinalizar()).thenReturn(btnEnviarFinalizar);
        // Invoca método a probar
        leftMetricBean.setCountMetricsToZeroCamera(cameraId);
        // Verifica ejecución
        Mockito.verify(btnEnviarEval, Mockito.never()).setDisabled(false);
        Mockito.verify(btnEnviarFinalizar, Mockito.never()).setDisabled(false);
    }

    @Test
    public void testSetCountMetricsToZeroCamera5() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        int cameraId = 1;
        Metric metrica = Mockito.mock(Metric.class);
        Button btnEnviarEval = Mockito.mock(Button.class);
        Button btnEnviarFinalizar = Mockito.mock(Button.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencia
        leftMetricBean.setMetricasModel(metricasModel);
        // Inyecta dependencias
        leftMetricBean.setBtnEnviarEval(btnEnviarEval);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metrica.isEvaluada()).thenReturn(false);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(metrica.getType()).thenReturn(EnumEvaluationType.YES_NO.toString());
        Mockito.when(rightMetricDetail.getBtnEnviarFinalizar()).thenReturn(btnEnviarFinalizar);
        // Invoca método a probar
        leftMetricBean.setCountMetricsToZeroCamera(cameraId);
        // Verifica ejecución
        Mockito.verify(btnEnviarEval, Mockito.never()).setDisabled(false);
        Mockito.verify(btnEnviarFinalizar, Mockito.never()).setDisabled(false);
    }

    @Test(expected = NullPointerException.class)
    public void testSetCountMetricsToZeroCamera6() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        int cameraId = 1;
        Metric metrica = Mockito.mock(Metric.class);
        Evidence evidence = Mockito.mock(Evidence.class);
        List<Evidence> lstEvidencias = Mockito.mock(List.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        EvidenceProvider evidenceProvider = Mockito.mock(EvidenceProvider.class);
        // Inyecta dependencia
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(lstEvidencias.get(0)).thenReturn(evidence);
        Mockito.when(evidenceProvider.getId()).thenReturn(cameraId);
        Mockito.when(metrica.getCirclesInfo()).thenReturn(circlesInfo);
        Mockito.when(metrica.getEvidencias()).thenReturn(lstEvidencias);
        Mockito.when(metrica.getStartEvaluationTime()).thenReturn(new Date());
        Mockito.when(evidence.getEvidenceProvider()).thenReturn(evidenceProvider);
        Mockito.when(metrica.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        // Invoca método a probar, lanza nullPointer por Clients.evalJavaScript
        leftMetricBean.setCountMetricsToZeroCamera(cameraId);
    }

    @Test
    public void testSetQualityObservation() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock object
        String observation = "observation";
        Textbox txtObsCalidad = Mockito.mock(Textbox.class);
        // Se inyecta dependencia
        leftMetricBean.setTxtObsCalidad(txtObsCalidad);
        // Invoca método a probar
        leftMetricBean.setQualityObservation(observation);
        // Valida asignación
        Mockito.verify(txtObsCalidad).setValue(observation);
    }

    @Test
    public void testUpdateSituationMetrics() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Situation situation = Mockito.mock(Situation.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        // Inyecta dependencias
        leftMetricBean.setSituation(situation);
        leftMetricBean.setMetricasModel(metricasModel);
        // Invoca método a probar
        leftMetricBean.updateSituationMetrics();
        // Verifica ejecución
        Mockito.verify(situation).setMetricas(metricasModel);
    }

    @Test
    public void testProcessSendEvalPageLoad() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Session mySession = Mockito.mock(Session.class);
        GlobalEvaluation evaluationBean = Mockito.mock(GlobalEvaluation.class);
        // Inyecta dependencias
        leftMetricBean.setMySession(mySession);
        leftMetricBean.setEvaluacionBean(evaluationBean);
        // Invoca método a probar
        leftMetricBean.processSendEvalPageLoad(false);
        // Verifica ejecución
        Mockito.verify(evaluationBean).setSituation();
    }

    @Test
    public void testValidateAllEvalNoNotify() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(metrica.isEvaluada()).thenReturn(true);
        Mockito.when(metrica.isCantEval()).thenReturn(true);
        // Invoca método a probar
        Assert.assertTrue("Se espera que objeto retornado sea true", leftMetricBean.validateAllEvalNoNotify());
    }

    @Test
    public void testValidateAllEvalNoNotify2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(metrica.isEvaluada()).thenReturn(true);
        Mockito.when(metrica.isCantEval()).thenReturn(false);
        // Invoca método a probar
        Assert.assertTrue("Se espera que objeto retornado sea true", leftMetricBean.validateAllEvalNoNotify());
    }

    @Test
    public void testValidateAllEvalNoNotify3() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(metrica.isEvaluada()).thenReturn(false);
        Mockito.when(metrica.isCantEval()).thenReturn(true);
        // Invoca método a probar
        Assert.assertTrue("Se espera que objeto retornado sea true", leftMetricBean.validateAllEvalNoNotify());
    }

    @Test
    public void testUpdateLeftMetric() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metric = Mockito.mock(Metric.class);
        Label lblTiempoMarcas = Mockito.mock(Label.class);
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasModel(metricasModel);
        leftMetricBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamientos
        Mockito.when(rightMetricDetail.getMetric()).thenReturn(metric);
        Mockito.when(metric.getNumMetrica()).thenReturn("0");
        Mockito.when(metricasModel.get(0)).thenReturn(metric);
        Mockito.when(rightMetricDetail.getCenterEvidence()).thenReturn(centerEvidence);
        Mockito.when(centerEvidence.getLblTiempoMarcas()).thenReturn(lblTiempoMarcas);
        Mockito.when(lblTiempoMarcas.getValue()).thenReturn("1");
        // Invoca método a probar
        leftMetricBean.updateLeftMetric("1", true);
        // Verifica ejecución
        Mockito.verify(metricasModel).set(0, metric);
    }

    @Test
    public void testSetMetricSortedModel() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        List<Integer> sortNumbers = new ArrayList<Integer>();
        sortNumbers.add(0);
        sortNumbers.add(1);

        Metric metric = Mockito.mock(Metric.class);
        Evidence evidence = Mockito.mock(Evidence.class);
        List<Evidence> lstEvidencias = Mockito.mock(List.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        EvidenceProvider evProvider = Mockito.mock(EvidenceProvider.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metric);
        Mockito.when(metric.getEvidencias()).thenReturn(lstEvidencias);
        Mockito.when(lstEvidencias.get(0)).thenReturn(evidence);
        Mockito.when(evidence.getEvidenceProvider()).thenReturn(evProvider);
        Mockito.when(evProvider.getId()).thenReturn(1);
        Mockito.when(evProvider.getViewOrder()).thenReturn(1);
        // Invoca método a probar
        Assert.assertFalse("Se espera que la lista no se encuentre vacía", leftMetricBean.setMetricSortedModel(sortNumbers)
                .isEmpty());
    }

    @Test
    public void testSetMetricSortedModel2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        List<Integer> sortNumbers = new ArrayList<Integer>();
        sortNumbers.add(0);
        sortNumbers.add(1);

        Metric metric = Mockito.mock(Metric.class);
        Evidence evidence = Mockito.mock(Evidence.class);
        List<Evidence> lstEvidencias = Mockito.mock(List.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        EvidenceProvider evProvider = Mockito.mock(EvidenceProvider.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metric);
        Mockito.when(metric.getEvidencias()).thenReturn(lstEvidencias);
        Mockito.when(lstEvidencias.get(0)).thenReturn(evidence);
        Mockito.when(evidence.getEvidenceProvider()).thenReturn(evProvider);
        Mockito.when(evProvider.getId()).thenReturn(1);
        Mockito.when(evProvider.getViewOrder()).thenReturn(null);
        // Invoca método a probar
        Assert.assertFalse("Se espera que la lista no se encuentre vacía", leftMetricBean.setMetricSortedModel(sortNumbers)
                .isEmpty());
    }

    @Test
    public void testSetMetricSortedModel3() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        List<Integer> sortNumbers = new ArrayList<Integer>();
        sortNumbers.add(0);
        sortNumbers.add(1);

        Metric metric = Mockito.mock(Metric.class);
        Evidence evidence = Mockito.mock(Evidence.class);
        List<Evidence> lstEvidencias = Mockito.mock(List.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        EvidenceProvider evProvider = Mockito.mock(EvidenceProvider.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metric);
        Mockito.when(metric.getEvidencias()).thenReturn(lstEvidencias);
        Mockito.when(lstEvidencias.get(0)).thenReturn(evidence);
        Mockito.when(evidence.getEvidenceProvider()).thenReturn(evProvider);
        Mockito.when(evProvider.getId()).thenReturn(1);
        Mockito.when(evProvider.getViewOrder()).thenReturn(2);
        // Invoca método a probar
        Assert.assertFalse("Se espera que la lista no se encuentre vacía", leftMetricBean.setMetricSortedModel(sortNumbers)
                .isEmpty());
    }

    @Test
    public void testValidateAllCounting() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(metrica.getType()).thenReturn(EnumEvaluationType.NUMBER_INPUT.toString());
        // Invoca método a probar
        Assert.assertFalse("Se espera que retorne false dado que no todas las métricas son de conteo",
                leftMetricBean.validateAllCounting());
    }

    @Test
    public void testValidateAllCounting2() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        Evidence evidence = Mockito.mock(Evidence.class);
        List<Evidence> lstEvidencias = Mockito.mock(List.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        EvidenceProvider evProvider = Mockito.mock(EvidenceProvider.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(metrica.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        Mockito.when(metrica.getEvidencias()).thenReturn(lstEvidencias);
        Mockito.when(lstEvidencias.get(0)).thenReturn(evidence);
        Mockito.when(evidence.getEvidenceProvider()).thenReturn(evProvider);
        Mockito.when(evProvider.getId()).thenReturn(1);
        Mockito.when(evProvider.getViewOrder()).thenReturn(2);
        // Invoca método a probar
        Assert.assertTrue("Se espera que retorne true dado que todas las métricas son de conteo",
                leftMetricBean.validateAllCounting());
    }

    @Test
    public void testValidateAllCounting3() {
        // Crea instancia de la clase a probar
        LeftMetricBean leftMetricBean = new LeftMetricBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        Evidence evidence = Mockito.mock(Evidence.class);
        List<Evidence> lstEvidencias = Mockito.mock(List.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        EvidenceProvider evProvider = Mockito.mock(EvidenceProvider.class);
        // Inyecta dependencias
        leftMetricBean.setMetricasModel(metricasModel);
        // Define comportamientos
        Mockito.when(metricasModel.size()).thenReturn(1);
        Mockito.when(metricasModel.get(0)).thenReturn(metrica);
        Mockito.when(metrica.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        Mockito.when(metrica.getEvidencias()).thenReturn(lstEvidencias);
        Mockito.when(lstEvidencias.get(0)).thenReturn(evidence);
        Mockito.when(evidence.getEvidenceProvider()).thenReturn(evProvider);
        Mockito.when(evProvider.getId()).thenReturn(1);
        Mockito.when(evProvider.getViewOrder()).thenReturn(null);
        // Invoca método a probar
        Assert.assertTrue("Se espera que retorne true dado que todas las métricas son de conteo",
                leftMetricBean.validateAllCounting());
    }
}