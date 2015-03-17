package com.scopix.periscope.bean;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;

import com.scopix.periscope.common.ShowMessage;
import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.enums.EnumEvidenceType;
import com.scopix.periscope.interfaces.CenterEvidence;
import com.scopix.periscope.interfaces.GlobalEvaluation;
import com.scopix.periscope.interfaces.LeftMetricList;
import com.scopix.periscope.model.Camara;
import com.scopix.periscope.model.Metric;

/**
 * Clase de pruebas de com.scopix.periscope.bean.RightYesNoMetricBean
 * 
 * @author Carlos
 */
public class RightYesNoMetricBeanTest {

    public RightYesNoMetricBeanTest() {
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
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Invoca método a probar
        CenterEvidence centerEvidence = rightYesNoBean.createCenterEvidenceBean(EnumEvidenceType.IMAGE.toString());
        Assert.assertNotNull(centerEvidence);
    }

    @Test
    public void testCreateCenterEvidenceBean2() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Invoca método a probar
        CenterEvidence centerEvidence = rightYesNoBean.createCenterEvidenceBean(EnumEvidenceType.VIDEO.toString());
        Assert.assertNotNull(centerEvidence);
    }

    @Test
    public void testOnAfterRenderMulticamara() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        Combobox cmbMulticamara = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        rightYesNoBean.setMetric(metric);
        rightYesNoBean.setCmbMultiCamaras(cmbMulticamara);
        // Define comportamiento
        Mockito.when(metric.isMultiple()).thenReturn(false);
        // Invoca método a probar
        rightYesNoBean.onAfterRenderMulticamara(null);
        // Verifica ejecución
        Mockito.verify(cmbMulticamara, Mockito.never()).setSelectedIndex(0);
    }

    @Test
    public void testOnAfterRenderMulticamara2() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        Combobox cmbMulticamara = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        rightYesNoBean.setMetric(metric);
        rightYesNoBean.setCmbMultiCamaras(cmbMulticamara);
        // Define comportamiento
        Mockito.when(metric.isMultiple()).thenReturn(true);
        // Invoca método a probar
        rightYesNoBean.onAfterRenderMulticamara(null);
        // Verifica ejecución
        Mockito.verify(cmbMulticamara).setSelectedIndex(0);
    }

    @Test
    public void testGetMetric() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        // Se inyecta dependencia
        rightYesNoBean.setMetric(metric);

        Assert.assertNotNull(rightYesNoBean.getMetric());
        Assert.assertEquals(metric, rightYesNoBean.getMetric());
    }

    @Test
    public void testGetLeftMetricBean() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        // Se inyecta dependencia
        rightYesNoBean.setLeftMetricBean(leftMetricBean);

        Assert.assertNotNull(rightYesNoBean.getLeftMetricBean());
        Assert.assertEquals(leftMetricBean, rightYesNoBean.getLeftMetricBean());
    }

    @Test
    public void testGetCenterEvidence() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Se inyecta dependencia
        rightYesNoBean.setCenterEvidence(centerEvidence);

        Assert.assertNotNull(rightYesNoBean.getCenterEvidence());
        Assert.assertEquals(centerEvidence, rightYesNoBean.getCenterEvidence());
    }

    @Test
    public void testBindComponentAndParent() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Include rightInclude = Mockito.mock(Include.class);
        LeftMetricBean leftMetricBean = Mockito.mock(LeftMetricBean.class);
        // Invoca método a probar
        rightYesNoBean.bindComponentAndParent(rightInclude, leftMetricBean);

        Assert.assertNotNull(rightYesNoBean.getLeftMetricBean());
    }

    @Test
    public void testGetMetricResult() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        Assert.assertNull(rightYesNoBean.getMetricResult());
    }

    @Test
    public void testGetEvidenceType() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        String evidenceType = "1";
        // Se inyecta dependencia
        rightYesNoBean.setEvidenceType(evidenceType);

        Assert.assertNotNull(rightYesNoBean.getEvidenceType());
        Assert.assertEquals(evidenceType, rightYesNoBean.getEvidenceType());
    }

    @Test
    public void testGetCmbMultiCamaras() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        rightYesNoBean.setCmbMultiCamaras(cmbMulticamaras);

        Assert.assertNotNull(rightYesNoBean.getCmbMultiCamaras());
        Assert.assertEquals(cmbMulticamaras, rightYesNoBean.getCmbMultiCamaras());
    }

    @Test
    public void testGetLstCamaras() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        List<Camara> lstCamaras = new ArrayList<Camara>();
        // Se inyecta dependencia
        rightYesNoBean.setLstCamaras(lstCamaras);

        Assert.assertNotNull(rightYesNoBean.getLstCamaras());
        Assert.assertEquals(lstCamaras, rightYesNoBean.getLstCamaras());
    }

    @Test
    public void testGetGroupBoxMultiCamara() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Groupbox groupBoxMultiCam = Mockito.mock(Groupbox.class);
        // Se inyecta dependencia
        rightYesNoBean.setGroupBoxMultiCamara(groupBoxMultiCam);

        Assert.assertNotNull(rightYesNoBean.getGroupBoxMultiCamara());
        Assert.assertEquals(groupBoxMultiCam, rightYesNoBean.getGroupBoxMultiCamara());
    }

    @Test
    public void testUpdateMetricValue() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        // Inyecta dependencia
        rightYesNoBean.setLeftMetricBean(leftMetricBean);
        // Invoca método a probar
        rightYesNoBean.updateMetricValue("1");
        // Verifica ejecución
        Mockito.verify(leftMetricBean).updateMetricValue("1");
    }

    @Test
    public void testGetLblValorMetrica() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Label lblValorMetrica = Mockito.mock(Label.class);
        // Se inyecta dependencia
        rightYesNoBean.setLblValorMetrica(lblValorMetrica);

        Assert.assertNotNull(rightYesNoBean.getLblValorMetrica());
        Assert.assertEquals(lblValorMetrica, rightYesNoBean.getLblValorMetrica());
    }

    @Test
    public void testSetTimeButtonsDisabledState() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Button btnNo = Mockito.mock(Button.class);
        Button btnYes = Mockito.mock(Button.class);
        // Se inyecta dependencias
        rightYesNoBean.setBtnNo(btnNo);
        rightYesNoBean.setBtnYes(btnYes);
        // Invoca método a probar
        rightYesNoBean.setTimeButtonsDisabledState(true);
        // Verifica ejecución
        Mockito.verify(btnNo).setDisabled(true);
        Mockito.verify(btnYes).setDisabled(true);
    }

    @Test
    public void testGetBtnNo() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Button btnNo = Mockito.mock(Button.class);
        // Se inyecta dependencia
        rightYesNoBean.setBtnNo(btnNo);

        Assert.assertNotNull(rightYesNoBean.getBtnNo());
        Assert.assertEquals(btnNo, rightYesNoBean.getBtnNo());
    }

    @Test
    public void testGetBtnYes() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Button btnYes = Mockito.mock(Button.class);
        // Se inyecta dependencia
        rightYesNoBean.setBtnYes(btnYes);

        Assert.assertNotNull(rightYesNoBean.getBtnYes());
        Assert.assertEquals(btnYes, rightYesNoBean.getBtnYes());
    }

    @Test
    public void testGetShowMessage() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Se inyecta dependencia
        rightYesNoBean.setShowMessage(showMessage);

        Assert.assertNotNull(rightYesNoBean.getShowMessage());
        Assert.assertEquals(showMessage, rightYesNoBean.getShowMessage());
    }

    @Test
    public void testGetShowMessage2() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        Assert.assertNotNull(rightYesNoBean.getShowMessage());
    }

    @Test
    public void testGetEvidenceURL() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Se inyecta dependencia
        String evidenceURL = "http://domain/evidence";
        rightYesNoBean.setEvidenceURL(evidenceURL);

        Assert.assertNotNull(rightYesNoBean.getEvidenceURL());
        Assert.assertEquals(evidenceURL, rightYesNoBean.getEvidenceURL());
    }

    @Test
    public void testGetLblNoSquares() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Label lblNoSquares = Mockito.mock(Label.class);
        // Se inyecta dependencia
        rightYesNoBean.setLblNoSquares(lblNoSquares);

        Assert.assertNotNull(rightYesNoBean.getLblNoSquares());
        Assert.assertEquals(lblNoSquares, rightYesNoBean.getLblNoSquares());
    }

    @Test
    public void testGetLblYesSquares() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Label lblYesSquares = Mockito.mock(Label.class);
        // Se inyecta dependencia
        rightYesNoBean.setLblYesSquares(lblYesSquares);

        Assert.assertNotNull(rightYesNoBean.getLblYesSquares());
        Assert.assertEquals(lblYesSquares, rightYesNoBean.getLblYesSquares());
    }

    @Test
    public void testGetTemplatePath() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Se inyecta dependencia
        String templatePath = "http://domain/template";
        rightYesNoBean.setTemplatePath(templatePath);

        Assert.assertNotNull(rightYesNoBean.getTemplatePath());
        Assert.assertEquals(templatePath, rightYesNoBean.getTemplatePath());
    }

    @Test
    public void testGetCurrentCamIndex() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        Assert.assertEquals(-1, rightYesNoBean.getCurrentCameraId());
    }

    @Test
    public void testGetUserCredentialManager() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        UserCredentialManager userCredentialMgr = Mockito.mock(UserCredentialManager.class);
        // Se inyecta dependencia
        rightYesNoBean.setUserCredentialManager(userCredentialMgr);

        Assert.assertNotNull(rightYesNoBean.getUserCredentialManager());
        Assert.assertEquals(userCredentialMgr, rightYesNoBean.getUserCredentialManager());
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserCredentialManager2() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        Assert.assertNotNull(rightYesNoBean.getUserCredentialManager());
    }

    @Test
    public void testGetExecution() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Execution execution = Mockito.mock(Execution.class);
        // Se inyecta dependencia
        rightYesNoBean.setExecution(execution);

        Assert.assertNotNull(rightYesNoBean.getExecution());
        Assert.assertEquals(execution, rightYesNoBean.getExecution());
    }

    @Test
    public void testPrepareCenterEvidence() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Inyecta dependencias
        rightYesNoBean.setCenterEvidence(centerEvidence);
        rightYesNoBean.setMetric(metric);
        rightYesNoBean.setLeftMetricBean(leftMetricBean);
        // Define comportamiento
        Mockito.when(leftMetricBean.getGlobalEvaluation()).thenReturn(evalBean);
        // Invoca método a probar
        rightYesNoBean.prepareCenterEvidence(EnumEvidenceType.IMAGE.toString());
    }

    @Test
    public void testPrepareCenterEvidence2() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Inyecta dependencias
        rightYesNoBean.setCenterEvidence(centerEvidence);
        rightYesNoBean.setMetric(metric);
        rightYesNoBean.setLeftMetricBean(leftMetricBean);
        // Define comportamiento
        Mockito.when(leftMetricBean.getGlobalEvaluation()).thenReturn(evalBean);
        // Invoca método a probar
        rightYesNoBean.prepareCenterEvidence(EnumEvidenceType.VIDEO.toString());
    }

    @Test
    public void testPrepareCenterDetail() {
        // Crea instancia de la clase a probar
        RightYesNoMetricBean rightYesNoBean = new RightYesNoMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        Include centerInclude = Mockito.mock(Include.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Inyecta dependencias
        rightYesNoBean.setCenterEvidence(centerEvidence);
        rightYesNoBean.setMetric(metric);
        rightYesNoBean.setLeftMetricBean(leftMetricBean);
        // Define comportamiento
        Mockito.when(leftMetricBean.getGlobalEvaluation()).thenReturn(evalBean);
        // Invoca método a probar
        rightYesNoBean.prepareCenterDetail(centerInclude);
        // Verifica ejecución
        Mockito.verify(evalBean).setCenterInclude(centerInclude);
    }
}