package com.scopix.periscope.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;

import com.scopix.periscope.common.ShowMessage;
import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.enums.EnumEvidenceType;
import com.scopix.periscope.interfaces.CenterEvidence;
import com.scopix.periscope.interfaces.GlobalEvaluation;
import com.scopix.periscope.interfaces.LeftMetricList;
import com.scopix.periscope.model.Camara;
import com.scopix.periscope.model.Metric;

/**
 * Clase de pruebas de com.scopix.periscope.bean.RightNumberInputMetricBean
 * 
 * @author Carlos
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class RightNumberInputMetricBeanTest {

    public RightNumberInputMetricBeanTest() {
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
    public void testCreateCenterEvidenceBean() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Invoca método a probar
        CenterEvidence centerEvidence = rightNumberInputBean.createCenterEvidenceBean(EnumEvidenceType.IMAGE.toString());
        Assert.assertNotNull(centerEvidence);
    }

    @Test
    public void testCreateCenterEvidenceBean2() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Invoca método a probar
        CenterEvidence centerEvidence = rightNumberInputBean.createCenterEvidenceBean(EnumEvidenceType.VIDEO.toString());
        Assert.assertNotNull(centerEvidence);
    }

    @Test
    public void testOnAfterRenderMulticamara() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        Combobox cmbMulticamara = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        rightNumberInputBean.setMetric(metric);
        rightNumberInputBean.setCmbMultiCamaras(cmbMulticamara);
        // Define comportamiento
        Mockito.when(metric.isMultiple()).thenReturn(false);
        // Invoca método a probar
        rightNumberInputBean.onAfterRenderMulticamara(null);
        // Verifica ejecución
        Mockito.verify(cmbMulticamara, Mockito.never()).setSelectedIndex(0);
    }

    @Test
    public void testOnAfterRenderMulticamara2() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        Combobox cmbMulticamara = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        rightNumberInputBean.setMetric(metric);
        rightNumberInputBean.setCmbMultiCamaras(cmbMulticamara);
        // Define comportamiento
        Mockito.when(metric.isMultiple()).thenReturn(true);
        // Invoca método a probar
        rightNumberInputBean.onAfterRenderMulticamara(null);
        // Verifica ejecución
        Mockito.verify(cmbMulticamara).setSelectedIndex(0);
    }

    @Test
    public void testGetMetric() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        // Se inyecta dependencia
        rightNumberInputBean.setMetric(metric);

        Assert.assertNotNull(rightNumberInputBean.getMetric());
        Assert.assertEquals(metric, rightNumberInputBean.getMetric());
    }

    @Test
    public void testGetLeftMetricBean() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        // Se inyecta dependencia
        rightNumberInputBean.setLeftMetricBean(leftMetricBean);

        Assert.assertNotNull(rightNumberInputBean.getLeftMetricBean());
        Assert.assertEquals(leftMetricBean, rightNumberInputBean.getLeftMetricBean());
    }

    @Test
    public void testSetLeftMetricBean() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        // Se inyecta dependencia
        rightNumberInputBean.setLeftMetricBean(leftMetricBean);

        Assert.assertNotNull(rightNumberInputBean.getLeftMetricBean());
        Assert.assertEquals(leftMetricBean, rightNumberInputBean.getLeftMetricBean());
    }

    @Test
    public void testGetCenterEvidence() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Se inyecta dependencia
        rightNumberInputBean.setCenterEvidence(centerEvidence);

        Assert.assertNotNull(rightNumberInputBean.getCenterEvidence());
        Assert.assertEquals(centerEvidence, rightNumberInputBean.getCenterEvidence());
    }

    @Test
    public void testSetCenterEvidence() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Se inyecta dependencia
        rightNumberInputBean.setCenterEvidence(centerEvidence);

        Assert.assertNotNull(rightNumberInputBean.getCenterEvidence());
        Assert.assertEquals(centerEvidence, rightNumberInputBean.getCenterEvidence());
    }

    @Test
    public void testBindComponentAndParent() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        Include rightInclude = Mockito.mock(Include.class);
        LeftMetricBean leftMetricBean = Mockito.mock(LeftMetricBean.class);
        // Invoca método a probar
        rightNumberInputBean.bindComponentAndParent(rightInclude, leftMetricBean);

        Assert.assertNotNull(rightNumberInputBean.getLeftMetricBean());
    }

    @Test
    public void testSetMetric() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        // Se inyecta dependencia
        rightNumberInputBean.setMetric(metric);

        Assert.assertNotNull(rightNumberInputBean.getMetric());
        Assert.assertEquals(metric, rightNumberInputBean.getMetric());
    }

    @Test
    public void testGetMetricResult() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        Assert.assertNull(rightNumberInputBean.getMetricResult());
    }

    @Test
    public void testGetEvidenceType() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        String evidenceType = "1";
        // Se inyecta dependencia
        rightNumberInputBean.setEvidenceType(evidenceType);

        Assert.assertNotNull(rightNumberInputBean.getEvidenceType());
        Assert.assertEquals(evidenceType, rightNumberInputBean.getEvidenceType());
    }

    @Test
    public void testSetEvidenceType() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        String evidenceType = "1";
        // Se inyecta dependencia
        rightNumberInputBean.setEvidenceType(evidenceType);

        Assert.assertNotNull(rightNumberInputBean.getEvidenceType());
        Assert.assertEquals(evidenceType, rightNumberInputBean.getEvidenceType());
    }

    @Test
    public void testGetCmbMultiCamaras() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        rightNumberInputBean.setCmbMultiCamaras(cmbMulticamaras);

        Assert.assertNotNull(rightNumberInputBean.getCmbMultiCamaras());
        Assert.assertEquals(cmbMulticamaras, rightNumberInputBean.getCmbMultiCamaras());
    }

    @Test
    public void testSetCmbMultiCamaras() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        rightNumberInputBean.setCmbMultiCamaras(cmbMulticamaras);

        Assert.assertNotNull(rightNumberInputBean.getCmbMultiCamaras());
        Assert.assertEquals(cmbMulticamaras, rightNumberInputBean.getCmbMultiCamaras());
    }

    @Test
    public void testGetLstCamaras() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        List<Camara> lstCamaras = new ArrayList<Camara>();
        // Se inyecta dependencia
        rightNumberInputBean.setLstCamaras(lstCamaras);

        Assert.assertNotNull(rightNumberInputBean.getLstCamaras());
        Assert.assertEquals(lstCamaras, rightNumberInputBean.getLstCamaras());
    }

    @Test
    public void testSetLstCamaras() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        List<Camara> lstCamaras = new ArrayList<Camara>();
        // Se inyecta dependencia
        rightNumberInputBean.setLstCamaras(lstCamaras);

        Assert.assertNotNull(rightNumberInputBean.getLstCamaras());
        Assert.assertEquals(lstCamaras, rightNumberInputBean.getLstCamaras());
    }

    @Test
    public void testGetGroupBoxMultiCamara() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        Groupbox groupBoxMultiCam = Mockito.mock(Groupbox.class);
        // Se inyecta dependencia
        rightNumberInputBean.setGroupBoxMultiCamara(groupBoxMultiCam);

        Assert.assertNotNull(rightNumberInputBean.getGroupBoxMultiCamara());
        Assert.assertEquals(groupBoxMultiCam, rightNumberInputBean.getGroupBoxMultiCamara());
    }

    @Test
    public void testSetGroupBoxMultiCamara() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        Groupbox groupBoxMultiCam = Mockito.mock(Groupbox.class);
        // Se inyecta dependencia
        rightNumberInputBean.setGroupBoxMultiCamara(groupBoxMultiCam);

        Assert.assertNotNull(rightNumberInputBean.getGroupBoxMultiCamara());
        Assert.assertEquals(groupBoxMultiCam, rightNumberInputBean.getGroupBoxMultiCamara());
    }

    /**
     * Invocado al momento de cambiar de cámara (multicámara) Almacena información de la evaluación y prepara siguiente evidencia
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event
     * @since 6.0
     * @date 08/03/2013
     */
    @Test(expected = NullPointerException.class)
    public void testOnSelectCamara() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock objects
        Camara camara = Mockito.mock(Camara.class);
        List<Camara> lstCamaras = Mockito.mock(List.class);
        Combobox cmbMultiCamaras = Mockito.mock(Combobox.class);
        NorthHeaderBean northBean = Mockito.mock(NorthHeaderBean.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);

        Metric metric = Mockito.mock(Metric.class);
        Label lblCircles = Mockito.mock(Label.class);
        Label lblSquares = Mockito.mock(Label.class);
        Label lblCurrentTime = Mockito.mock(Label.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightNumberInputBean.setLstCamaras(lstCamaras);
        rightNumberInputBean.setLeftMetricBean(leftMetricBean);
        rightNumberInputBean.setCmbMultiCamaras(cmbMultiCamaras);

        rightNumberInputBean.setMetric(metric);
        // Define comportamientos
        Mockito.when(cmbMultiCamaras.getSelectedIndex()).thenReturn(0);
        Mockito.when(lstCamaras.get(0)).thenReturn(camara);
        Mockito.when(leftMetricBean.getGlobalEvaluation()).thenReturn(evalBean);
        Mockito.when(evalBean.getNorthHeaderBean()).thenReturn(northBean);
        Mockito.when(northBean.getZoneName()).thenReturn("Xxx/Xxxx");
        Mockito.when(camara.getEvidenceType()).thenReturn(EnumEvidenceType.IMAGE.toString());

        Mockito.when(lblCircles.getValue()).thenReturn("");
        Mockito.when(circlesInfo.get(0)).thenReturn(null);
        Mockito.when(lblSquares.getValue()).thenReturn("");
        Mockito.when(lblCurrentTime.getValue()).thenReturn("");
        // Invoca método a probar
        rightNumberInputBean.onSelectCamara(null);
        // Espera NullPointerException por Clients.evalJavaScript("deleteAllCurrentShapes('S','S','N');");
    }

    @Test(expected = NullPointerException.class)
    public void testOnSelectCamara2() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock objects
        Camara camara = Mockito.mock(Camara.class);
        List<Camara> lstCamaras = Mockito.mock(List.class);
        Combobox cmbMultiCamaras = Mockito.mock(Combobox.class);
        NorthHeaderBean northBean = Mockito.mock(NorthHeaderBean.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);

        Metric metric = Mockito.mock(Metric.class);
        Label lblCircles = Mockito.mock(Label.class);
        Label lblSquares = Mockito.mock(Label.class);
        Label lblCurrentTime = Mockito.mock(Label.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightNumberInputBean.setLstCamaras(lstCamaras);
        rightNumberInputBean.setLeftMetricBean(leftMetricBean);
        rightNumberInputBean.setCmbMultiCamaras(cmbMultiCamaras);
        rightNumberInputBean.setMetric(metric);
        // Define comportamientos
        Mockito.when(cmbMultiCamaras.getSelectedIndex()).thenReturn(0);
        Mockito.when(lstCamaras.get(0)).thenReturn(camara);
        Mockito.when(leftMetricBean.getGlobalEvaluation()).thenReturn(evalBean);
        Mockito.when(evalBean.getNorthHeaderBean()).thenReturn(northBean);
        Mockito.when(northBean.getZoneName()).thenReturn("Xxx/Xxxx");
        Mockito.when(camara.getEvidenceType()).thenReturn(EnumEvidenceType.VIDEO.toString());

        Mockito.when(lblCircles.getValue()).thenReturn("");
        Mockito.when(circlesInfo.get(0)).thenReturn(null);
        Mockito.when(lblSquares.getValue()).thenReturn("");
        Mockito.when(lblCurrentTime.getValue()).thenReturn("");
        // Invoca método a probar
        rightNumberInputBean.onSelectCamara(null);
        // Espera NullPointerException por Clients.evalJavaScript("deleteAllCurrentShapes('S','S','N');");
    }

    @Test
    public void testUpdateMetricValue() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        // Inyecta dependencia
        rightNumberInputBean.setLeftMetricBean(leftMetricBean);
        // Invoca método a probar
        rightNumberInputBean.updateMetricValue("1");
        // Verifica ejecución
        Mockito.verify(leftMetricBean).updateMetricValue("1");
    }

    @Test
    public void testGetLblValorMetrica() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        Label lblValorMetrica = Mockito.mock(Label.class);
        // Se inyecta dependencia
        rightNumberInputBean.setLblValorMetrica(lblValorMetrica);

        Assert.assertNotNull(rightNumberInputBean.getLblValorMetrica());
        Assert.assertEquals(lblValorMetrica, rightNumberInputBean.getLblValorMetrica());
    }

    @Test
    public void testSetTimeButtonsDisabledState() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        rightNumberInputBean.setTimeButtonsDisabledState(true);
    }

    @Test
    public void testGetShowMessage() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Se inyecta dependencia
        rightNumberInputBean.setShowMessage(showMessage);

        Assert.assertNotNull(rightNumberInputBean.getShowMessage());
        Assert.assertEquals(showMessage, rightNumberInputBean.getShowMessage());
    }

    @Test
    public void testGetShowMessage2() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        Assert.assertNotNull(rightNumberInputBean.getShowMessage());
    }

    @Test
    public void testGetEvidenceURL() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Se inyecta dependencia
        String evidenceURL = "http://domain/evidence";
        rightNumberInputBean.setEvidenceURL(evidenceURL);

        Assert.assertNotNull(rightNumberInputBean.getEvidenceURL());
        Assert.assertEquals(evidenceURL, rightNumberInputBean.getEvidenceURL());
    }

    @Test
    public void testGetIboxValor() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        Intbox iBoxValor = Mockito.mock(Intbox.class);
        // Se inyecta dependencia
        rightNumberInputBean.setIboxValor(iBoxValor);

        Assert.assertNotNull(rightNumberInputBean.getIboxValor());
        Assert.assertEquals(iBoxValor, rightNumberInputBean.getIboxValor());
    }

    @Test
    public void testGetTemplatePath() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Se inyecta dependencia
        String templatePath = "http://domain/template";
        rightNumberInputBean.setTemplatePath(templatePath);

        Assert.assertNotNull(rightNumberInputBean.getTemplatePath());
        Assert.assertEquals(templatePath, rightNumberInputBean.getTemplatePath());
    }

    @Test
    public void testSaveCurrentEvidenceEvalData() {
        // Crea instancia de la clase a probar
        Metric metric = Mockito.mock(Metric.class);
        Label lblCurrentTime = Mockito.mock(Label.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        Metric metricaLeftModel = Mockito.mock(Metric.class);

        Mockito.when(lblCurrentTime.getValue()).thenReturn("");
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        Mockito.when(leftMetricBean.getMetricasModel()).thenReturn(metricasModel);
        Mockito.when(metricasModel.get(1)).thenReturn(metricaLeftModel);

        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        rightNumberInputBean.setLblCurrentTIme(lblCurrentTime);
        rightNumberInputBean.setMetric(metric);
        rightNumberInputBean.setLeftMetricBean(leftMetricBean);

        rightNumberInputBean.saveCurrentEvidenceEvalData(0, true);
    }

    @Test
    public void testGetCurrentCamIndex() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        Assert.assertEquals(-1, rightNumberInputBean.getCurrentCameraId());
    }

    @Test
    public void testGetUserCredentialManager() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        UserCredentialManager userCredentialMgr = Mockito.mock(UserCredentialManager.class);
        // Se inyecta dependencia
        rightNumberInputBean.setUserCredentialManager(userCredentialMgr);

        Assert.assertNotNull(rightNumberInputBean.getUserCredentialManager());
        Assert.assertEquals(userCredentialMgr, rightNumberInputBean.getUserCredentialManager());
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserCredentialManager2() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        Assert.assertNotNull(rightNumberInputBean.getUserCredentialManager());
    }

    @Test
    public void testGetExecution() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        Execution execution = Mockito.mock(Execution.class);
        // Se inyecta dependencia
        rightNumberInputBean.setExecution(execution);

        Assert.assertNotNull(rightNumberInputBean.getExecution());
        Assert.assertEquals(execution, rightNumberInputBean.getExecution());
    }

    @Test
    public void testPrepareCenterEvidence() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Inyecta dependencias
        rightNumberInputBean.setCenterEvidence(centerEvidence);
        rightNumberInputBean.setMetric(metric);
        rightNumberInputBean.setLeftMetricBean(leftMetricBean);
        // Define comportamiento
        Mockito.when(leftMetricBean.getGlobalEvaluation()).thenReturn(evalBean);
        // Invoca método a probar
        rightNumberInputBean.prepareCenterEvidence(EnumEvidenceType.IMAGE.toString());
    }

    @Test
    public void testPrepareCenterEvidence2() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Inyecta dependencias
        rightNumberInputBean.setCenterEvidence(centerEvidence);
        rightNumberInputBean.setMetric(metric);
        rightNumberInputBean.setLeftMetricBean(leftMetricBean);
        // Define comportamiento
        Mockito.when(leftMetricBean.getGlobalEvaluation()).thenReturn(evalBean);
        // Invoca método a probar
        rightNumberInputBean.prepareCenterEvidence(EnumEvidenceType.VIDEO.toString());
    }

    @Test
    public void testPrepareCenterDetail() {
        // Crea instancia de la clase a probar
        RightNumberInputMetricBean rightNumberInputBean = new RightNumberInputMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        Include centerInclude = Mockito.mock(Include.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Inyecta dependencias
        rightNumberInputBean.setCenterEvidence(centerEvidence);
        rightNumberInputBean.setMetric(metric);
        rightNumberInputBean.setLeftMetricBean(leftMetricBean);
        // Define comportamiento
        Mockito.when(leftMetricBean.getGlobalEvaluation()).thenReturn(evalBean);
        // Invoca método a probar
        rightNumberInputBean.prepareCenterDetail(centerInclude);
        // Verifica ejecución
        Mockito.verify(evalBean).setCenterInclude(centerInclude);
    }
}