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
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
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
 * Clase de pruebas de com.scopix.periscope.bean.RightTimeMetricBean
 * 
 * @author Carlos
 */
@SuppressWarnings(value = { "unchecked" })
public class RightTimeMetricBeanTest {

    public RightTimeMetricBeanTest() {
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
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Invoca método a probar
        CenterEvidence centerEvidence = rightTimeBean.createCenterEvidenceBean(EnumEvidenceType.IMAGE.toString());
        Assert.assertNotNull(centerEvidence);
    }

    @Test
    public void testCreateCenterEvidenceBean2() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Invoca método a probar
        CenterEvidence centerEvidence = rightTimeBean.createCenterEvidenceBean(EnumEvidenceType.VIDEO.toString());
        Assert.assertNotNull(centerEvidence);
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
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock objects
        Camara camara = Mockito.mock(Camara.class);
        List<Camara> lstCamaras = Mockito.mock(List.class);
        Combobox cmbMultiCamaras = Mockito.mock(Combobox.class);
        NorthHeaderBean northBean = Mockito.mock(NorthHeaderBean.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);

        int currentCamIndex = 0;
        Metric metric = Mockito.mock(Metric.class);
        Label lblCircles = Mockito.mock(Label.class);
        Label lblSquares = Mockito.mock(Label.class);
        Label lblCurrentTime = Mockito.mock(Label.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightTimeBean.setLstCamaras(lstCamaras);
        rightTimeBean.setLeftMetricBean(leftMetricBean);
        rightTimeBean.setCmbMultiCamaras(cmbMultiCamaras);

        rightTimeBean.setMetric(metric);
        // rightTimeBean.setLblSquares(lblSquares);
        rightTimeBean.setCurrentCameraId(currentCamIndex);
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
        rightTimeBean.onSelectCamara(null);
        // Espera NullPointerException por Clients.evalJavaScript("deleteAllCurrentShapes('S','S','N');");
    }

    @Test(expected = NullPointerException.class)
    public void testOnSelectCamara2() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock objects
        Camara camara = Mockito.mock(Camara.class);
        List<Camara> lstCamaras = Mockito.mock(List.class);
        Combobox cmbMultiCamaras = Mockito.mock(Combobox.class);
        NorthHeaderBean northBean = Mockito.mock(NorthHeaderBean.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);

        int currentCamIndex = 0;
        Metric metric = Mockito.mock(Metric.class);
        Label lblCircles = Mockito.mock(Label.class);
        Label lblSquares = Mockito.mock(Label.class);
        Label lblCurrentTime = Mockito.mock(Label.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightTimeBean.setLstCamaras(lstCamaras);
        rightTimeBean.setLeftMetricBean(leftMetricBean);
        rightTimeBean.setCmbMultiCamaras(cmbMultiCamaras);
        rightTimeBean.setMetric(metric);
        // rightTimeBean.setLblSquares(lblSquares);
        rightTimeBean.setCurrentCameraId(currentCamIndex);
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
        rightTimeBean.onSelectCamara(null);
        // Espera NullPointerException por Clients.evalJavaScript("deleteAllCurrentShapes('S','S','N');");
    }

    @Test
    public void testOnAfterRenderMulticamara() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        Combobox cmbMulticamara = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        rightTimeBean.setMetric(metric);
        rightTimeBean.setCmbMultiCamaras(cmbMulticamara);
        // Define comportamiento
        Mockito.when(metric.isMultiple()).thenReturn(false);
        // Invoca método a probar
        rightTimeBean.onAfterRenderMulticamara(null);
        // Verifica ejecución
        Mockito.verify(cmbMulticamara, Mockito.never()).setSelectedIndex(0);
    }

    @Test
    public void testOnAfterRenderMulticamara2() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        Combobox cmbMulticamara = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        rightTimeBean.setMetric(metric);
        rightTimeBean.setCmbMultiCamaras(cmbMulticamara);
        // Define comportamiento
        Mockito.when(metric.isMultiple()).thenReturn(true);
        // Invoca método a probar
        rightTimeBean.onAfterRenderMulticamara(null);
        // Verifica ejecución
        Mockito.verify(cmbMulticamara).setSelectedIndex(0);
    }

    @Test
    public void testGetMetric() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        // Se inyecta dependencia
        rightTimeBean.setMetric(metric);

        Assert.assertNotNull(rightTimeBean.getMetric());
        Assert.assertEquals(metric, rightTimeBean.getMetric());
    }

    @Test
    public void testGetLeftMetricBean() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        // Se inyecta dependencia
        rightTimeBean.setLeftMetricBean(leftMetricBean);

        Assert.assertNotNull(rightTimeBean.getLeftMetricBean());
        Assert.assertEquals(leftMetricBean, rightTimeBean.getLeftMetricBean());
    }

    @Test
    public void testGetCenterEvidence() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Se inyecta dependencia
        rightTimeBean.setCenterEvidence(centerEvidence);

        Assert.assertNotNull(rightTimeBean.getCenterEvidence());
        Assert.assertEquals(centerEvidence, rightTimeBean.getCenterEvidence());
    }

    @Test
    public void testBindComponentAndParent() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Include rightInclude = Mockito.mock(Include.class);
        LeftMetricBean leftMetricBean = Mockito.mock(LeftMetricBean.class);
        // Invoca método a probar
        rightTimeBean.bindComponentAndParent(rightInclude, leftMetricBean);

        Assert.assertNotNull(rightTimeBean.getLeftMetricBean());
    }

    @Test
    public void testGetMetricResult() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        Assert.assertNull(rightTimeBean.getMetricResult());
    }

    @Test
    public void testGetEvidenceType() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();

        String evidenceType = "1";
        // Se inyecta dependencia
        rightTimeBean.setEvidenceType(evidenceType);

        Assert.assertNotNull(rightTimeBean.getEvidenceType());
        Assert.assertEquals(evidenceType, rightTimeBean.getEvidenceType());
    }

    @Test
    public void testGetCmbMultiCamaras() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        rightTimeBean.setCmbMultiCamaras(cmbMulticamaras);

        Assert.assertNotNull(rightTimeBean.getCmbMultiCamaras());
        Assert.assertEquals(cmbMulticamaras, rightTimeBean.getCmbMultiCamaras());
    }

    @Test
    public void testGetLstCamaras() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        List<Camara> lstCamaras = new ArrayList<Camara>();
        // Se inyecta dependencia
        rightTimeBean.setLstCamaras(lstCamaras);

        Assert.assertNotNull(rightTimeBean.getLstCamaras());
        Assert.assertEquals(lstCamaras, rightTimeBean.getLstCamaras());
    }

    @Test
    public void testGetGroupBoxMultiCamara() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Groupbox groupBoxMultiCam = Mockito.mock(Groupbox.class);
        // Se inyecta dependencia
        rightTimeBean.setGroupBoxMultiCamara(groupBoxMultiCam);

        Assert.assertNotNull(rightTimeBean.getGroupBoxMultiCamara());
        Assert.assertEquals(groupBoxMultiCam, rightTimeBean.getGroupBoxMultiCamara());
    }

    @Test
    public void testUpdateMetricValue() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        // Inyecta dependencia
        rightTimeBean.setLeftMetricBean(leftMetricBean);
        // Invoca método a probar
        rightTimeBean.updateMetricValue("1");
        // Verifica ejecución
        Mockito.verify(leftMetricBean).updateMetricValue("1");
    }

    @Test
    public void testGetLblValorMetrica() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Label lblValorMetrica = Mockito.mock(Label.class);
        // Se inyecta dependencia
        rightTimeBean.setLblValorMetrica(lblValorMetrica);

        Assert.assertNotNull(rightTimeBean.getLblValorMetrica());
        Assert.assertEquals(lblValorMetrica, rightTimeBean.getLblValorMetrica());
    }

    @Test
    public void testSetTimeButtonsDisabledState() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Button btnAsignarInicial = Mockito.mock(Button.class);
        Button btnAsignarFinal = Mockito.mock(Button.class);
        // Inyecta dependencia
        rightTimeBean.setBtnAsignarInicial(btnAsignarInicial);
        rightTimeBean.setBtnAsignarFinal(btnAsignarFinal);
        // Invoca método a probar
        rightTimeBean.setTimeButtonsDisabledState(true);
        // Verifica ejecución
        Mockito.verify(btnAsignarInicial).setDisabled(true);
        Mockito.verify(btnAsignarFinal).setDisabled(true);
    }

    @Test
    public void testGetLblElapsed() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Label lblElapsed = Mockito.mock(Label.class);
        // Se inyecta dependencia
        rightTimeBean.setLblElapsed(lblElapsed);

        Assert.assertNotNull(rightTimeBean.getLblElapsed());
        Assert.assertEquals(lblElapsed, rightTimeBean.getLblElapsed());
    }

    @Test
    public void testGetImgFinal() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Image imgFinal = Mockito.mock(Image.class);
        // Se inyecta dependencia
        rightTimeBean.setImgFinal(imgFinal);

        Assert.assertNotNull(rightTimeBean.getImgFinal());
        Assert.assertEquals(imgFinal, rightTimeBean.getImgFinal());
    }

    @Test
    public void testGetImgInicial() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Image imgInicial = Mockito.mock(Image.class);
        // Se inyecta dependencia
        rightTimeBean.setImgInicial(imgInicial);

        Assert.assertNotNull(rightTimeBean.getImgInicial());
        Assert.assertEquals(imgInicial, rightTimeBean.getImgInicial());
    }

    @Test
    public void testGetBtnAsignarInicial() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Button btnAsignarInicial = Mockito.mock(Button.class);
        // Se inyecta dependencia
        rightTimeBean.setBtnAsignarInicial(btnAsignarInicial);

        Assert.assertNotNull(rightTimeBean.getBtnAsignarInicial());
        Assert.assertEquals(btnAsignarInicial, rightTimeBean.getBtnAsignarInicial());
    }

    @Test
    public void testGetBtnAsignarFinal() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Button btnAsignarFinal = Mockito.mock(Button.class);
        // Se inyecta dependencia
        rightTimeBean.setBtnAsignarFinal(btnAsignarFinal);

        Assert.assertNotNull(rightTimeBean.getBtnAsignarFinal());
        Assert.assertEquals(btnAsignarFinal, rightTimeBean.getBtnAsignarFinal());
    }

    @Test
    public void testGetLblSquares() {
        // //Crea instancia de la clase a probar
        // RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // //Crea mock object
        // Label lblSquares = Mockito.mock(Label.class);
        // //Se inyecta dependencia
        // rightTimeBean.setLblSquares(lblSquares);
        //
        // Assert.assertNotNull(rightTimeBean.getLblSquares());
        // Assert.assertEquals(lblSquares, rightTimeBean.getLblSquares());
    }

    @Test
    public void testGetEvidenceURL() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();

        String evidenceURL = "url";
        // Se inyecta dependencia
        rightTimeBean.setEvidenceURL(evidenceURL);

        Assert.assertNotNull(rightTimeBean.getEvidenceURL());
        Assert.assertEquals(evidenceURL, rightTimeBean.getEvidenceURL());
    }

    @Test
    public void testGetLblTFinalMenor() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Label lblTFinalMenor = Mockito.mock(Label.class);
        // Se inyecta dependencia
        rightTimeBean.setLblTFinalMenor(lblTFinalMenor);

        Assert.assertNotNull(rightTimeBean.getLblTFinalMenor());
        Assert.assertEquals(lblTFinalMenor, rightTimeBean.getLblTFinalMenor());
    }

    @Test
    public void testGetLblTiempoFinal() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Label lblTiempoFinal = Mockito.mock(Label.class);
        // Se inyecta dependencia
        rightTimeBean.setLblTiempoFinal(lblTiempoFinal);

        Assert.assertNotNull(rightTimeBean.getLblTiempoFinal());
        Assert.assertEquals(lblTiempoFinal, rightTimeBean.getLblTiempoFinal());
    }

    @Test
    public void testGetLblTiempoInicial() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Label lblTiempoInicial = Mockito.mock(Label.class);
        // Se inyecta dependencia
        rightTimeBean.setLblTiempoInicial(lblTiempoInicial);

        Assert.assertNotNull(rightTimeBean.getLblTiempoInicial());
        Assert.assertEquals(lblTiempoInicial, rightTimeBean.getLblTiempoInicial());
    }

    @Test
    public void testGetShowMessage() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Se inyecta dependencia
        rightTimeBean.setShowMessage(showMessage);

        Assert.assertNotNull(rightTimeBean.getShowMessage());
        Assert.assertEquals(showMessage, rightTimeBean.getShowMessage());
    }

    @Test
    public void testGetShowMessage2() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        Assert.assertNotNull(rightTimeBean.getShowMessage());
    }

    @Test
    public void testGetTemplatePath() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();

        String templatePath = "path";
        // Se inyecta dependencia
        rightTimeBean.setTemplatePath(templatePath);

        Assert.assertNotNull(rightTimeBean.getTemplatePath());
        Assert.assertEquals(templatePath, rightTimeBean.getTemplatePath());
    }

    @Test
    public void testGetCurrentCamIndex() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();

        int currentCamIndex = 0;
        // Se inyecta dependencia
        rightTimeBean.setCurrentCameraId(currentCamIndex);

        Assert.assertNotNull(rightTimeBean.getCurrentCameraId());
        Assert.assertEquals(currentCamIndex, rightTimeBean.getCurrentCameraId());
    }

    @Test
    public void testGetUserCredentialManager() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        UserCredentialManager userCredentialMgr = Mockito.mock(UserCredentialManager.class);
        // Se inyecta dependencia
        rightTimeBean.setUserCredentialManager(userCredentialMgr);

        Assert.assertNotNull(rightTimeBean.getUserCredentialManager());
        Assert.assertEquals(userCredentialMgr, rightTimeBean.getUserCredentialManager());
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserCredentialManager2() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        Assert.assertNotNull(rightTimeBean.getUserCredentialManager());
    }

    @Test
    public void testGetExecution() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Execution execution = Mockito.mock(Execution.class);
        // Se inyecta dependencia
        rightTimeBean.setExecution(execution);

        Assert.assertNotNull(rightTimeBean.getExecution());
        Assert.assertEquals(execution, rightTimeBean.getExecution());
    }

    @Test
    public void testPrepareCenterEvidence() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Inyecta dependencias
        rightTimeBean.setCenterEvidence(centerEvidence);
        rightTimeBean.setMetric(metric);
        rightTimeBean.setLeftMetricBean(leftMetricBean);
        // Define comportamiento
        Mockito.when(leftMetricBean.getGlobalEvaluation()).thenReturn(evalBean);
        // Invoca método a probar
        rightTimeBean.prepareCenterEvidence(EnumEvidenceType.IMAGE.toString());
    }

    @Test
    public void testPrepareCenterEvidence2() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Inyecta dependencias
        rightTimeBean.setCenterEvidence(centerEvidence);
        rightTimeBean.setMetric(metric);
        rightTimeBean.setLeftMetricBean(leftMetricBean);
        // Define comportamiento
        Mockito.when(leftMetricBean.getGlobalEvaluation()).thenReturn(evalBean);
        // Invoca método a probar
        rightTimeBean.prepareCenterEvidence(EnumEvidenceType.VIDEO.toString());
    }

    @Test
    public void testPrepareCenterDetail() {
        // Crea instancia de la clase a probar
        RightTimeMetricBean rightTimeBean = new RightTimeMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        Include centerInclude = Mockito.mock(Include.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Inyecta dependencias
        rightTimeBean.setCenterEvidence(centerEvidence);
        rightTimeBean.setMetric(metric);
        rightTimeBean.setLeftMetricBean(leftMetricBean);
        // Define comportamiento
        Mockito.when(leftMetricBean.getGlobalEvaluation()).thenReturn(evalBean);
        // Invoca método a probar
        rightTimeBean.prepareCenterDetail(centerInclude);
        // Verifica ejecución
        Mockito.verify(evalBean).setCenterInclude(centerInclude);
    }
}