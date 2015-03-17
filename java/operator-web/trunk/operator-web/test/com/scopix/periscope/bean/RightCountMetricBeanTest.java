package com.scopix.periscope.bean;

import java.util.ArrayList;
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
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Include;
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
 * Clase de pruebas de com.scopix.periscope.bean.RightCountMetricBean
 * 
 * @author Carlos
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class RightCountMetricBeanTest {

    public RightCountMetricBeanTest() {
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
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Invoca método a probar
        CenterEvidence centerEvidence = rightCountBean.createCenterEvidenceBean(EnumEvidenceType.IMAGE.toString());
        Assert.assertNotNull(centerEvidence);
    }

    @Test
    public void testCreateCenterEvidenceBean2() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Invoca método a probar
        CenterEvidence centerEvidence = rightCountBean.createCenterEvidenceBean(EnumEvidenceType.VIDEO.toString());
        Assert.assertNotNull(centerEvidence);
    }

    @Test
    public void testOnAfterRenderMulticamara() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        Combobox cmbMulticamara = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        rightCountBean.setMetric(metric);
        rightCountBean.setCmbMultiCamaras(cmbMulticamara);
        // Define comportamiento
        Mockito.when(metric.isMultiple()).thenReturn(false);
        // Invoca método a probar
        rightCountBean.onAfterRenderMulticamara(null);
        // Verifica ejecución
        Mockito.verify(cmbMulticamara, Mockito.never()).setSelectedIndex(0);
    }

    @Test
    public void testOnAfterRenderMulticamara2() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        Combobox cmbMulticamara = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        rightCountBean.setMetric(metric);
        rightCountBean.setCmbMultiCamaras(cmbMulticamara);
        // Define comportamiento
        Mockito.when(metric.isMultiple()).thenReturn(true);
        // Invoca método a probar
        rightCountBean.onAfterRenderMulticamara(null);
        // Verifica ejecución
        Mockito.verify(cmbMulticamara).setSelectedIndex(0);
    }

    /**
     * Invocado al momento de presionar el botón "siguiente"
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event
     * @since 6.0
     * @date 08/03/2013
     */
    @Test(expected = NullPointerException.class)
    public void testOnClickSiguiente() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Metric metric = Mockito.mock(Metric.class);
        Label lblValorMetrica = Mockito.mock(Label.class);
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        // Inyecta dependencias
        rightCountBean.setMetric(metric);
        rightCountBean.setLblValorMetrica(lblValorMetrica);
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);
        // Define comportamientos
        Mockito.when(metric.isCantEval()).thenReturn(true);
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        Mockito.when(lblValorMetrica.getValue()).thenReturn("x");
        Mockito.when(cmbMulticamaras.isVisible()).thenReturn(true);
        Mockito.when(cmbMulticamaras.getSelectedIndex()).thenReturn(0);

        // Crea mock objects
        int currentCamIndex = 0;
        Label lblCircles = Mockito.mock(Label.class);
        Label lblSquares = Mockito.mock(Label.class);
        Camara camara = Mockito.mock(Camara.class);
        Label lblCurrentTime = Mockito.mock(Label.class);
        List<Camara> lstCamaras = Mockito.mock(List.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setMetric(metric);
        rightCountBean.setLstCamaras(lstCamaras);
        rightCountBean.setLblCircles(lblCircles);
        rightCountBean.setLblSquares(lblSquares);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setSquaresInfo(squaresInfo);
        rightCountBean.setLblCurrentTIme(lblCurrentTime);
        rightCountBean.setCurrentCameraId(currentCamIndex);
        // Define comportamientos
        Mockito.when(lblCircles.getValue()).thenReturn("");
        Mockito.when(circlesInfo.get(0)).thenReturn(null);
        Mockito.when(lblSquares.getValue()).thenReturn("");
        Mockito.when(lblCurrentTime.getValue()).thenReturn("");
        Mockito.when(lstCamaras.get(Matchers.anyInt())).thenReturn(camara);
        Mockito.when(camara.getId()).thenReturn(1);
        // Invoca método a probar
        rightCountBean.onClickSiguiente(null);
    }

    /**
     * Invocado al momento de presionar el botón "siguiente"
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event
     * @since 6.0
     * @date 08/03/2013
     */
    @Test
    public void testOnClickSiguiente2() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Metric metric = Mockito.mock(Metric.class);
        Label lblValorMetrica = Mockito.mock(Label.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Inyecta dependencias
        rightCountBean.setMetric(metric);
        rightCountBean.setShowMessage(showMessage);
        rightCountBean.setLblValorMetrica(lblValorMetrica);
        // Define comportamientos
        Mockito.when(metric.isCantEval()).thenReturn(false);
        Mockito.when(lblValorMetrica.getValue()).thenReturn("x");
        // Invoca método a probar
        rightCountBean.onClickSiguiente(null);
        // Verifica comportamiento
        Mockito.verify(showMessage).mostrarMensaje(Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    /**
     * Invocado al momento de presionar el botón "siguiente"
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event
     * @since 6.0
     * @date 08/03/2013
     */
    @Test(expected = NullPointerException.class)
    public void testOnClickSiguiente3() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Metric metric = Mockito.mock(Metric.class);
        Label lblValorMetrica = Mockito.mock(Label.class);
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        // Inyecta dependencias
        rightCountBean.setMetric(metric);
        rightCountBean.setLblValorMetrica(lblValorMetrica);
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);
        // Define comportamientos
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        Mockito.when(metric.isCantEval()).thenReturn(true);
        Mockito.when(lblValorMetrica.getValue()).thenReturn("x");
        Mockito.when(cmbMulticamaras.isVisible()).thenReturn(false);

        // Crea mock objects
        int currentCamIndex = 0;
        Label lblCircles = Mockito.mock(Label.class);
        Label lblSquares = Mockito.mock(Label.class);
        Label lblCurrentTime = Mockito.mock(Label.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setMetric(metric);
        rightCountBean.setLblCircles(lblCircles);
        rightCountBean.setLblSquares(lblSquares);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setSquaresInfo(squaresInfo);
        rightCountBean.setLblCurrentTIme(lblCurrentTime);
        rightCountBean.setCurrentCameraId(currentCamIndex);
        // Define comportamientos
        Mockito.when(lblCircles.getValue()).thenReturn("");
        Mockito.when(circlesInfo.get(0)).thenReturn(null);
        Mockito.when(lblSquares.getValue()).thenReturn("");
        Mockito.when(lblCurrentTime.getValue()).thenReturn("");
        // Invoca método a probar
        rightCountBean.onClickSiguiente(null);
    }

    /**
     * Valida que todas las cámaras hayan sido evaluadas
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 08/03/2013
     */
    @Test
    public void testValidateCamerasEval() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Inyecta dependencias
        rightCountBean.setShowMessage(showMessage);
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);
        // Define comportamientos
        Mockito.when(cmbMulticamaras.isVisible()).thenReturn(false);
        // Invoca método a probar
        rightCountBean.validateCamerasEval(true);
        // Verifica ejecución
        Mockito.verify(showMessage, Mockito.never()).mostrarMensaje(Matchers.anyString(), Matchers.anyString(),
                Matchers.anyString());
    }

    @Test
    public void testValidateCamerasEval2() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        List<Camara> lstCamaras = Mockito.mock(List.class);
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Inyecta dependencias
        rightCountBean.setLstCamaras(lstCamaras);
        rightCountBean.setShowMessage(showMessage);
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);
        // Define comportamientos
        Mockito.when(cmbMulticamaras.isVisible()).thenReturn(true);
        Mockito.when(lstCamaras.size()).thenReturn(0);
        // Invoca método a probar
        rightCountBean.validateCamerasEval(true);
        // Verifica ejecución
        Mockito.verify(showMessage, Mockito.never()).mostrarMensaje(Matchers.anyString(), Matchers.anyString(),
                Matchers.anyString());
    }

    @Test
    public void testValidateCamerasEval3() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Camara camara = Mockito.mock(Camara.class);
        List<Camara> lstCamaras = Mockito.mock(List.class);
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setLstCamaras(lstCamaras);
        rightCountBean.setShowMessage(showMessage);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);
        // Define comportamientos
        Mockito.when(cmbMulticamaras.isVisible()).thenReturn(true);
        Mockito.when(lstCamaras.size()).thenReturn(1);
        Mockito.when(circlesInfo.get(Matchers.anyInt())).thenReturn(null);
        Mockito.when(lstCamaras.get(Matchers.anyInt())).thenReturn(camara);
        // Invoca método a probar
        rightCountBean.validateCamerasEval(true);
        // Verifica ejecución
        Mockito.verify(showMessage).mostrarMensaje(Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    @Test
    public void testValidateCamerasEval4() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Camara camara = Mockito.mock(Camara.class);
        List<Camara> lstCamaras = Mockito.mock(List.class);
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setLstCamaras(lstCamaras);
        rightCountBean.setShowMessage(showMessage);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);
        // Define comportamientos
        Mockito.when(cmbMulticamaras.isVisible()).thenReturn(true);
        Mockito.when(lstCamaras.size()).thenReturn(1);
        Mockito.when(circlesInfo.get(Matchers.anyInt())).thenReturn("");
        Mockito.when(lstCamaras.get(Matchers.anyInt())).thenReturn(camara);
        // Invoca método a probar
        rightCountBean.validateCamerasEval(true);
        // Verifica ejecución
        Mockito.verify(showMessage).mostrarMensaje(Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    @Test
    public void testValidateCamerasEval5() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Camara camara = Mockito.mock(Camara.class);
        List<Camara> lstCamaras = Mockito.mock(List.class);
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setLstCamaras(lstCamaras);
        rightCountBean.setShowMessage(showMessage);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);
        // Define comportamientos
        Mockito.when(cmbMulticamaras.isVisible()).thenReturn(true);
        Mockito.when(lstCamaras.size()).thenReturn(1);
        Mockito.when(circlesInfo.get(Matchers.anyInt())).thenReturn("1");
        Mockito.when(lstCamaras.get(Matchers.anyInt())).thenReturn(camara);
        Mockito.when(camara.getId()).thenReturn(1);
        // Invoca método a probar
        rightCountBean.validateCamerasEval(true);
        // Verifica ejecución
        Mockito.verify(showMessage, Mockito.never()).mostrarMensaje(Matchers.anyString(), Matchers.anyString(),
                Matchers.anyString());
    }

    @Test
    public void testValidateCamerasEval6() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Camara camara = Mockito.mock(Camara.class);
        List<Camara> lstCamaras = Mockito.mock(List.class);
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setLstCamaras(lstCamaras);
        rightCountBean.setShowMessage(showMessage);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);
        // Define comportamientos
        Mockito.when(cmbMulticamaras.isVisible()).thenReturn(true);
        Mockito.when(lstCamaras.size()).thenReturn(1);
        Mockito.when(circlesInfo.get(Matchers.anyInt())).thenReturn("0");
        Mockito.when(lstCamaras.get(Matchers.anyInt())).thenReturn(camara);
        Mockito.when(camara.getId()).thenReturn(1);
        // Invoca método a probar
        rightCountBean.validateCamerasEval(true);
        // Verifica ejecución
        Mockito.verify(showMessage, Mockito.never()).mostrarMensaje(Matchers.anyString(), Matchers.anyString(),
                Matchers.anyString());
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
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
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
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);

        // Inyecta dependencias
        rightCountBean.setLstCamaras(lstCamaras);
        rightCountBean.setLeftMetricBean(leftMetricBean);
        rightCountBean.setCmbMultiCamaras(cmbMultiCamaras);

        rightCountBean.setMetric(metric);
        rightCountBean.setLblCircles(lblCircles);
        rightCountBean.setLblSquares(lblSquares);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setSquaresInfo(squaresInfo);
        rightCountBean.setLblCurrentTIme(lblCurrentTime);
        rightCountBean.setCurrentCameraId(currentCamIndex);
        // Define comportamientos
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
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
        rightCountBean.onSelectCamara(null);
        // Espera NullPointerException por Clients.evalJavaScript("deleteAllCurrentShapes('S','S','N');");
    }

    @Test(expected = NullPointerException.class)
    public void testOnSelectCamara2() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
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
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);

        // Inyecta dependencias
        rightCountBean.setLstCamaras(lstCamaras);
        rightCountBean.setLeftMetricBean(leftMetricBean);
        rightCountBean.setCmbMultiCamaras(cmbMultiCamaras);

        rightCountBean.setMetric(metric);
        rightCountBean.setLblCircles(lblCircles);
        rightCountBean.setLblSquares(lblSquares);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setSquaresInfo(squaresInfo);
        rightCountBean.setLblCurrentTIme(lblCurrentTime);
        rightCountBean.setCurrentCameraId(currentCamIndex);
        // Define comportamientos
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
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
        rightCountBean.onSelectCamara(null);
        // Espera NullPointerException por Clients.evalJavaScript("deleteAllCurrentShapes('S','S','N');");
    }

    /**
     * Almacena información de la evaluación y prepara siguiente evidencia
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param selectedIndex
     * @param isCambioCamara
     * @since 6.0
     * @date 08/03/2013
     */
    @Test
    public void testSaveCurrentEvidenceEvalData() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        int currentCamIndex = 0;
        Metric metric = Mockito.mock(Metric.class);
        Label lblCircles = Mockito.mock(Label.class);
        Label lblSquares = Mockito.mock(Label.class);
        Label lblCurrentTime = Mockito.mock(Label.class);
        Metric metricaLeftModel = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setMetric(metric);
        rightCountBean.setLblCircles(lblCircles);
        rightCountBean.setLblSquares(lblSquares);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setSquaresInfo(squaresInfo);
        rightCountBean.setLeftMetricBean(leftMetricBean);
        rightCountBean.setLblCurrentTIme(lblCurrentTime);
        rightCountBean.setCurrentCameraId(currentCamIndex);
        // Define comportamientos
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        Mockito.when(lblCircles.getValue()).thenReturn("");
        Mockito.when(circlesInfo.get(0)).thenReturn(null);
        Mockito.when(lblSquares.getValue()).thenReturn("");
        Mockito.when(lblCurrentTime.getValue()).thenReturn("");
        Mockito.when(leftMetricBean.getMetricasModel()).thenReturn(metricasModel);
        Mockito.when(metricasModel.get(Matchers.anyInt())).thenReturn(metricaLeftModel);
        // Invoca método a probar
        rightCountBean.saveCurrentEvidenceEvalData(currentCamIndex, true);
    }

    @Test
    public void testSaveCurrentEvidenceEvalData2() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        int currentCamIndex = 0;
        Metric metric = Mockito.mock(Metric.class);
        Label lblCircles = Mockito.mock(Label.class);
        Label lblSquares = Mockito.mock(Label.class);
        Label lblCurrentTime = Mockito.mock(Label.class);
        Metric metricaLeftModel = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setMetric(metric);
        rightCountBean.setLblCircles(lblCircles);
        rightCountBean.setLblSquares(lblSquares);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setSquaresInfo(squaresInfo);
        rightCountBean.setLeftMetricBean(leftMetricBean);
        rightCountBean.setLblCurrentTIme(lblCurrentTime);
        rightCountBean.setCurrentCameraId(currentCamIndex);
        // Define comportamientos
        Mockito.when(circlesInfo.get(0)).thenReturn("0");
        Mockito.when(lblCircles.getValue()).thenReturn("");
        Mockito.when(lblSquares.getValue()).thenReturn("");
        Mockito.when(lblCurrentTime.getValue()).thenReturn("");
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        Mockito.when(leftMetricBean.getMetricasModel()).thenReturn(metricasModel);
        Mockito.when(metricasModel.get(Matchers.anyInt())).thenReturn(metricaLeftModel);
        // Invoca método a probar
        rightCountBean.saveCurrentEvidenceEvalData(currentCamIndex, false);
    }

    @Test
    public void testSaveCurrentEvidenceEvalData3() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        int currentCamIndex = 0;
        Metric metric = Mockito.mock(Metric.class);
        Label lblCircles = Mockito.mock(Label.class);
        Label lblSquares = Mockito.mock(Label.class);
        Label lblCurrentTime = Mockito.mock(Label.class);
        Metric metricaLeftModel = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setMetric(metric);
        rightCountBean.setLblCircles(lblCircles);
        rightCountBean.setLblSquares(lblSquares);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setSquaresInfo(squaresInfo);
        rightCountBean.setLeftMetricBean(leftMetricBean);
        rightCountBean.setLblCurrentTIme(lblCurrentTime);
        rightCountBean.setCurrentCameraId(currentCamIndex);
        // Define comportamientos
        Mockito.when(circlesInfo.get(0)).thenReturn("1");
        Mockito.when(lblCircles.getValue()).thenReturn("");
        Mockito.when(lblSquares.getValue()).thenReturn("");
        Mockito.when(lblCurrentTime.getValue()).thenReturn("");
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        Mockito.when(leftMetricBean.getMetricasModel()).thenReturn(metricasModel);
        Mockito.when(metricasModel.get(Matchers.anyInt())).thenReturn(metricaLeftModel);
        // Invoca método a probar
        rightCountBean.saveCurrentEvidenceEvalData(currentCamIndex, false);
    }

    @Test
    public void testSaveCurrentEvidenceEvalData4() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        int currentCamIndex = 0;
        Metric metric = Mockito.mock(Metric.class);
        Label lblCircles = Mockito.mock(Label.class);
        Label lblSquares = Mockito.mock(Label.class);
        Label lblCurrentTime = Mockito.mock(Label.class);
        Metric metricaLeftModel = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setMetric(metric);
        rightCountBean.setLblCircles(lblCircles);
        rightCountBean.setLblSquares(lblSquares);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setSquaresInfo(squaresInfo);
        rightCountBean.setLeftMetricBean(leftMetricBean);
        rightCountBean.setLblCurrentTIme(lblCurrentTime);
        rightCountBean.setCurrentCameraId(currentCamIndex);
        // Define comportamientos
        Mockito.when(circlesInfo.get(0)).thenReturn("0");
        Mockito.when(lblCircles.getValue()).thenReturn("1");
        Mockito.when(lblSquares.getValue()).thenReturn("");
        Mockito.when(lblCurrentTime.getValue()).thenReturn("");
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        Mockito.when(leftMetricBean.getMetricasModel()).thenReturn(metricasModel);
        Mockito.when(metricasModel.get(Matchers.anyInt())).thenReturn(metricaLeftModel);
        // Invoca método a probar
        rightCountBean.saveCurrentEvidenceEvalData(currentCamIndex, false);
    }

    @Test
    public void testSaveCurrentEvidenceEvalData5() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        int currentCamIndex = 0;
        Metric metric = Mockito.mock(Metric.class);
        Label lblCircles = Mockito.mock(Label.class);
        Label lblSquares = Mockito.mock(Label.class);
        Label lblCurrentTime = Mockito.mock(Label.class);
        Metric metricaLeftModel = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setMetric(metric);
        rightCountBean.setLblCircles(lblCircles);
        rightCountBean.setLblSquares(lblSquares);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setSquaresInfo(squaresInfo);
        rightCountBean.setLeftMetricBean(leftMetricBean);
        rightCountBean.setLblCurrentTIme(lblCurrentTime);
        rightCountBean.setCurrentCameraId(currentCamIndex);
        // Define comportamientos
        Mockito.when(circlesInfo.get(0)).thenReturn("1");
        Mockito.when(lblCircles.getValue()).thenReturn("1");
        Mockito.when(lblSquares.getValue()).thenReturn("");
        Mockito.when(lblCurrentTime.getValue()).thenReturn("");
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        Mockito.when(leftMetricBean.getMetricasModel()).thenReturn(metricasModel);
        Mockito.when(metricasModel.get(Matchers.anyInt())).thenReturn(metricaLeftModel);
        // Invoca método a probar
        rightCountBean.saveCurrentEvidenceEvalData(currentCamIndex, false);
    }

    @Test
    public void testGetMetric() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        // Se inyecta dependencia
        rightCountBean.setMetric(metric);

        Assert.assertNotNull(rightCountBean.getMetric());
        Assert.assertEquals(metric, rightCountBean.getMetric());
    }

    @Test
    public void testGetLeftMetricBean() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        // Se inyecta dependencia
        rightCountBean.setLeftMetricBean(leftMetricBean);

        Assert.assertNotNull(rightCountBean.getLeftMetricBean());
        Assert.assertEquals(leftMetricBean, rightCountBean.getLeftMetricBean());
    }

    @Test
    public void testGetCenterEvidence() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Se inyecta dependencia
        rightCountBean.setCenterEvidence(centerEvidence);

        Assert.assertNotNull(rightCountBean.getCenterEvidence());
        Assert.assertEquals(centerEvidence, rightCountBean.getCenterEvidence());
    }

    @Test
    public void testBindComponentAndParent() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Include rightInclude = Mockito.mock(Include.class);
        LeftMetricBean leftMetricBean = Mockito.mock(LeftMetricBean.class);
        // Invoca método a probar
        rightCountBean.bindComponentAndParent(rightInclude, leftMetricBean);

        Assert.assertNotNull(rightCountBean.getLeftMetricBean());
    }

    @Test
    public void testGetMetricResult() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        Assert.assertNull(rightCountBean.getMetricResult());
    }

    @Test
    public void testGetEvidenceType() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        String evidenceType = "1";
        // Se inyecta dependencia
        rightCountBean.setEvidenceType(evidenceType);

        Assert.assertNotNull(rightCountBean.getEvidenceType());
        Assert.assertEquals(evidenceType, rightCountBean.getEvidenceType());
    }

    @Test
    public void testGetLstCamaras() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        List<Camara> lstCamaras = new ArrayList<Camara>();
        // Se inyecta dependencia
        rightCountBean.setLstCamaras(lstCamaras);

        Assert.assertNotNull(rightCountBean.getLstCamaras());
        Assert.assertEquals(lstCamaras, rightCountBean.getLstCamaras());
    }

    @Test
    public void testGetCmbMultiCamaras() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);

        Assert.assertNotNull(rightCountBean.getCmbMultiCamaras());
        Assert.assertEquals(cmbMulticamaras, rightCountBean.getCmbMultiCamaras());
    }

    @Test
    public void testGetGroupBoxMultiCamara() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Groupbox groupBoxMultiCam = Mockito.mock(Groupbox.class);
        // Se inyecta dependencia
        rightCountBean.setGroupBoxMultiCamara(groupBoxMultiCam);

        Assert.assertNotNull(rightCountBean.getGroupBoxMultiCamara());
        Assert.assertEquals(groupBoxMultiCam, rightCountBean.getGroupBoxMultiCamara());
    }

    @Test
    public void testGetLblValorMetrica() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Label lblValorMetrica = Mockito.mock(Label.class);
        // Se inyecta dependencia
        rightCountBean.setLblValorMetrica(lblValorMetrica);

        Assert.assertNotNull(rightCountBean.getLblValorMetrica());
        Assert.assertEquals(lblValorMetrica, rightCountBean.getLblValorMetrica());
    }

    @Test
    public void testUpdateMetricValue() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        // Inyecta dependencia
        rightCountBean.setLeftMetricBean(leftMetricBean);
        // Invoca método a probar
        rightCountBean.updateMetricValue("1");
        // Verifica ejecución
        Mockito.verify(leftMetricBean).updateMetricValue("1");
    }

    @Test
    public void testGetEvidenceURL() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Se inyecta dependencia
        String evidenceURL = "http://domain/evidence";
        rightCountBean.setEvidenceURL(evidenceURL);

        Assert.assertNotNull(rightCountBean.getEvidenceURL());
        Assert.assertEquals(evidenceURL, rightCountBean.getEvidenceURL());
    }

    @Test
    public void testSetTimeButtonsDisabledState() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        rightCountBean.setTimeButtonsDisabledState(true);
    }

    @Test
    public void testGetShowMessage() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Se inyecta dependencia
        rightCountBean.setShowMessage(showMessage);

        Assert.assertNotNull(rightCountBean.getShowMessage());
        Assert.assertEquals(showMessage, rightCountBean.getShowMessage());
    }

    @Test
    public void testGetShowMessage2() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        Assert.assertNotNull(rightCountBean.getShowMessage());
    }

    @Test
    public void testGetLblCircles() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Label lblCircles = Mockito.mock(Label.class);
        // Se inyecta dependencia
        rightCountBean.setLblCircles(lblCircles);

        Assert.assertNotNull(rightCountBean.getLblCircles());
        Assert.assertEquals(lblCircles, rightCountBean.getLblCircles());
    }

    @Test
    public void testGetLblSquares() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Label lblSquares = Mockito.mock(Label.class);
        // Se inyecta dependencia
        rightCountBean.setLblSquares(lblSquares);

        Assert.assertNotNull(rightCountBean.getLblSquares());
        Assert.assertEquals(lblSquares, rightCountBean.getLblSquares());
    }

    @Test
    public void testGetTemplatePath() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Se inyecta dependencia
        String templatePath = "http://domain/template";
        rightCountBean.setTemplatePath(templatePath);

        Assert.assertNotNull(rightCountBean.getTemplatePath());
        Assert.assertEquals(templatePath, rightCountBean.getTemplatePath());
    }

    @Test
    public void testGetCurrentCamIndex() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        int camIndex = 0;
        // Se inyecta dependencia
        rightCountBean.setCurrentCameraId(camIndex);

        Assert.assertNotNull(rightCountBean.getCurrentCameraId());
        Assert.assertEquals(camIndex, rightCountBean.getCurrentCameraId());
    }

    @Test
    public void testGetLblCurrentTIme() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Label lblCurrentTime = Mockito.mock(Label.class);
        // Se inyecta dependencia
        rightCountBean.setLblCurrentTIme(lblCurrentTime);

        Assert.assertNotNull(rightCountBean.getLblCurrentTIme());
        Assert.assertEquals(lblCurrentTime, rightCountBean.getLblCurrentTIme());
    }

    @Test
    public void testGetUserCredentialManager() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        UserCredentialManager userCredentialMgr = Mockito.mock(UserCredentialManager.class);
        // Se inyecta dependencia
        rightCountBean.setUserCredentialManager(userCredentialMgr);

        Assert.assertNotNull(rightCountBean.getUserCredentialManager());
        Assert.assertEquals(userCredentialMgr, rightCountBean.getUserCredentialManager());
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserCredentialManager2() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        Assert.assertNotNull(rightCountBean.getUserCredentialManager());
    }

    @Test
    public void testGetExecution() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Execution execution = Mockito.mock(Execution.class);
        // Se inyecta dependencia
        rightCountBean.setExecution(execution);

        Assert.assertNotNull(rightCountBean.getExecution());
        Assert.assertEquals(execution, rightCountBean.getExecution());
    }

    @Test
    public void testGetCirclesInfo() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Se inyecta dependencia
        rightCountBean.setCirclesInfo(circlesInfo);

        Assert.assertNotNull(rightCountBean.getCirclesInfo());
        Assert.assertEquals(circlesInfo, rightCountBean.getCirclesInfo());
    }

    @Test
    public void testGetSquaresInfo() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Se inyecta dependencia
        rightCountBean.setSquaresInfo(squaresInfo);

        Assert.assertNotNull(rightCountBean.getSquaresInfo());
        Assert.assertEquals(squaresInfo, rightCountBean.getSquaresInfo());
    }

    @Test
    public void testPrepareCenterEvidence() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Inyecta dependencias
        rightCountBean.setCenterEvidence(centerEvidence);
        rightCountBean.setMetric(metric);
        rightCountBean.setLeftMetricBean(leftMetricBean);
        // Define comportamiento
        Mockito.when(leftMetricBean.getGlobalEvaluation()).thenReturn(evalBean);
        // Invoca método a probar
        rightCountBean.prepareCenterEvidence(EnumEvidenceType.IMAGE.toString());
    }

    @Test
    public void testPrepareCenterEvidence2() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Inyecta dependencias
        rightCountBean.setCenterEvidence(centerEvidence);
        rightCountBean.setMetric(metric);
        rightCountBean.setLeftMetricBean(leftMetricBean);
        // Define comportamiento
        Mockito.when(leftMetricBean.getGlobalEvaluation()).thenReturn(evalBean);
        // Invoca método a probar
        rightCountBean.prepareCenterEvidence(EnumEvidenceType.VIDEO.toString());
    }

    @Test
    public void testPrepareCenterDetail() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        Include centerInclude = Mockito.mock(Include.class);
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        // Inyecta dependencias
        rightCountBean.setCenterEvidence(centerEvidence);
        rightCountBean.setMetric(metric);
        rightCountBean.setLeftMetricBean(leftMetricBean);
        // Define comportamiento
        Mockito.when(leftMetricBean.getGlobalEvaluation()).thenReturn(evalBean);
        // Invoca método a probar
        rightCountBean.prepareCenterDetail(centerInclude, "");
        // Verifica ejecución
        Mockito.verify(evalBean).setCenterInclude(centerInclude);
    }

    @Test
    public void testIsAllEvaluated() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        boolean allEvaluated = true;
        // Se inyecta dependencia
        rightCountBean.setAllEvaluated(allEvaluated);

        Assert.assertNotNull(rightCountBean.isAllEvaluated());
        Assert.assertEquals(allEvaluated, rightCountBean.isAllEvaluated());
    }

    @Test
    public void testGetBtnEnviarFinalizar() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Button btnEnviarFinalizar = Mockito.mock(Button.class);
        // Se inyecta dependencia
        rightCountBean.setBtnEnviarFinalizar(btnEnviarFinalizar);

        Assert.assertNotNull(rightCountBean.getBtnEnviarFinalizar());
        Assert.assertEquals(btnEnviarFinalizar, rightCountBean.getBtnEnviarFinalizar());
    }

    @Test
    public void testGetBtnNextCamara() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        Button btnNextCamara = Mockito.mock(Button.class);
        // Se inyecta dependencia
        rightCountBean.setBtnNextCamara(btnNextCamara);

        Assert.assertNotNull(rightCountBean.getBtnNextCamara());
        Assert.assertEquals(btnNextCamara, rightCountBean.getBtnNextCamara());
    }

    @Test
    public void testGetPlayerPosition() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock object
        String playerPosition = "1";
        // Se inyecta dependencia
        rightCountBean.setPlayerPosition(playerPosition);

        Assert.assertNotNull(rightCountBean.getPlayerPosition());
        Assert.assertEquals(playerPosition, rightCountBean.getPlayerPosition());
    }

    @Test
    public void testGetShapesData() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Integer currentCameraId = 1;
        Label lblCircles = Mockito.mock(Label.class);
        Label lblSquares = Mockito.mock(Label.class);
        Session mySession = Mockito.mock(Session.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setMySession(mySession);
        rightCountBean.setLblCircles(lblCircles);
        rightCountBean.setLblSquares(lblSquares);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setSquaresInfo(squaresInfo);
        rightCountBean.setCurrentCameraId(currentCameraId);
        // Define comportamientos
        Mockito.when(circlesInfo.get(currentCameraId)).thenReturn(null);
        Mockito.when(squaresInfo.get(currentCameraId)).thenReturn(null);
        // Invoca método a probar
        rightCountBean.getShapesData();
        // Verifica ejecución
        Mockito.verify(mySession).setAttribute("CIRCLES_COUNT", 0);
        Mockito.verify(mySession).setAttribute("SQUARES_COUNT", 0);
    }

    @Test
    public void testGetShapesData2() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Integer currentCameraId = 1;
        Label lblCircles = Mockito.mock(Label.class);
        Label lblSquares = Mockito.mock(Label.class);
        Session mySession = Mockito.mock(Session.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setMySession(mySession);
        rightCountBean.setLblCircles(lblCircles);
        rightCountBean.setLblSquares(lblSquares);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setSquaresInfo(squaresInfo);
        rightCountBean.setCurrentCameraId(currentCameraId);
        // Define comportamientos
        Mockito.when(circlesInfo.get(currentCameraId)).thenReturn("");
        Mockito.when(squaresInfo.get(currentCameraId)).thenReturn("");
        // Invoca método a probar
        rightCountBean.getShapesData();
        // Verifica ejecución
        Mockito.verify(mySession).setAttribute("CIRCLES_COUNT", 0);
        Mockito.verify(mySession).setAttribute("SQUARES_COUNT", 0);
    }

    @Test
    public void testGetShapesData3() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Integer currentCameraId = 1;
        Label lblCircles = Mockito.mock(Label.class);
        Label lblSquares = Mockito.mock(Label.class);
        Session mySession = Mockito.mock(Session.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setMySession(mySession);
        rightCountBean.setLblCircles(lblCircles);
        rightCountBean.setLblSquares(lblSquares);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setSquaresInfo(squaresInfo);
        rightCountBean.setCurrentCameraId(currentCameraId);
        // Define comportamientos
        Mockito.when(circlesInfo.get(currentCameraId)).thenReturn("0");
        Mockito.when(squaresInfo.get(currentCameraId)).thenReturn("0");
        // Invoca método a probar
        rightCountBean.getShapesData();
        // Verifica ejecución
        Mockito.verify(mySession).setAttribute("CIRCLES_COUNT", 0);
        Mockito.verify(mySession).setAttribute("SQUARES_COUNT", 0);
    }

    @Test
    public void testGetShapesData4() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Integer currentCameraId = 1;
        Label lblCircles = Mockito.mock(Label.class);
        Label lblSquares = Mockito.mock(Label.class);
        Session mySession = Mockito.mock(Session.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setMySession(mySession);
        rightCountBean.setLblCircles(lblCircles);
        rightCountBean.setLblSquares(lblSquares);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setSquaresInfo(squaresInfo);
        rightCountBean.setCurrentCameraId(currentCameraId);
        // Define comportamientos
        Mockito.when(circlesInfo.get(currentCameraId)).thenReturn("371px:243px_30px:30px");
        Mockito.when(squaresInfo.get(currentCameraId)).thenReturn("371px:243px_30px:30px");
        // Invoca método a probar
        rightCountBean.getShapesData();
        // Verifica ejecución
        Mockito.verify(mySession).setAttribute("CIRCLES_COUNT", 1);
        Mockito.verify(mySession).setAttribute("SQUARES_COUNT", 1);
    }

    @Test
    public void testOnClickNextCamara() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        List<Camara> lstCamaras = Mockito.mock(List.class);
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Inyecta dependencias
        rightCountBean.setLstCamaras(lstCamaras);
        rightCountBean.setShowMessage(showMessage);
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);
        // Define comportamientos
        Mockito.when(cmbMulticamaras.getSelectedIndex()).thenReturn(1);
        Mockito.when(lstCamaras.size()).thenReturn(1);
        // Invoca método a probar
        rightCountBean.onClickNextCamara(null);
        // Verifica ejecución
        Mockito.verify(showMessage).mostrarMensaje(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void testOnClickNextCamara2() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        List<Camara> lstCamaras = Mockito.mock(List.class);
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Inyecta dependencias
        rightCountBean.setLstCamaras(lstCamaras);
        rightCountBean.setShowMessage(showMessage);
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);
        // Define comportamientos
        Mockito.when(cmbMulticamaras.getSelectedIndex()).thenReturn(1);
        Mockito.when(lstCamaras.size()).thenReturn(2);
        // Invoca método a probar
        rightCountBean.onClickNextCamara(null);
        // Verifica ejecución
        Mockito.verify(showMessage).mostrarMensaje(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test(expected = NullPointerException.class)
    public void testOnClickNextCamara3() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        List<Camara> lstCamaras = Mockito.mock(List.class);
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Inyecta dependencias
        rightCountBean.setLstCamaras(lstCamaras);
        rightCountBean.setShowMessage(showMessage);
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);
        // Define comportamientos
        Mockito.when(cmbMulticamaras.getSelectedIndex()).thenReturn(1);
        Mockito.when(lstCamaras.size()).thenReturn(3);
        // Invoca método a probar
        rightCountBean.onClickNextCamara(null);
    }

    @Test
    public void testUpdateLeftMetric() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Metric metric = Mockito.mock(Metric.class);
        Label lblTiempoMarcas = Mockito.mock(Label.class);
        CenterEvidence centerEvidence = Mockito.mock(CenterEvidence.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        // Inyecta dependencias
        rightCountBean.setMetric(metric);
        rightCountBean.setCenterEvidence(centerEvidence);
        rightCountBean.setLeftMetricBean(leftMetricBean);
        // Define comportamientos
        Mockito.when(metric.getNumMetrica()).thenReturn("0");
        Mockito.when(centerEvidence.getLblTiempoMarcas()).thenReturn(lblTiempoMarcas);
        Mockito.when(leftMetricBean.getMetricasModel()).thenReturn(metricasModel);
        Mockito.when(metricasModel.get(0)).thenReturn(metric);
        Mockito.when(lblTiempoMarcas.getValue()).thenReturn("1");
        // Invoca método a probar
        rightCountBean.updateLeftMetric("1", true);
        // Verifica ejecución
        Mockito.verify(metricasModel).set(0, metric);
    }

    @Test
    public void testProcessMetricaCero() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Metric metric = Mockito.mock(Metric.class);
        Camara camara = Mockito.mock(Camara.class);
        Label lblValorMetrica = Mockito.mock(Label.class);
        List<Camara> lstCamaras = Mockito.mock(List.class);
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        Iterator<Camara> iterator = Mockito.mock(Iterator.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setMetric(metric);
        rightCountBean.setCurrentCameraId(0);
        rightCountBean.setLstCamaras(lstCamaras);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setLeftMetricBean(leftMetricBean);
        rightCountBean.setLblValorMetrica(lblValorMetrica);
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);
        // Define comportamientos
        Mockito.when(cmbMulticamaras.isVisible()).thenReturn(true);
        Mockito.when(lstCamaras.iterator()).thenReturn(iterator);
        Mockito.when(iterator.hasNext()).thenReturn(true, false);
        Mockito.when(iterator.next()).thenReturn(camara);
        Mockito.when(camara.getId()).thenReturn(1);
        Mockito.when(circlesInfo.get(1)).thenReturn("");
        Mockito.when(metric.getCirclesInfo()).thenReturn(circlesInfo);
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        Mockito.when(leftMetricBean.getMetricasModel()).thenReturn(metricasModel);
        // Invoca método a probar
        rightCountBean.processMetricaCero();
        // Verifica ejecución
        Mockito.verify(lblValorMetrica).setValue(Mockito.anyString());
        Mockito.verify(metric).setTiempoMarcas(null);
        Mockito.verify(metric).setEvaluada(true);
        Mockito.verify(metric).setCantEval(false);
        Mockito.verify(metric).setCantEvalObs(null);
        Mockito.verify(metricasModel).set(1, metric);
    }

    @Test
    public void testProcessMetricaCero2() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Metric metric = Mockito.mock(Metric.class);
        Camara camara = Mockito.mock(Camara.class);
        Label lblValorMetrica = Mockito.mock(Label.class);
        List<Camara> lstCamaras = Mockito.mock(List.class);
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        Iterator<Camara> iterator = Mockito.mock(Iterator.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setMetric(metric);
        rightCountBean.setCurrentCameraId(0);
        rightCountBean.setLstCamaras(lstCamaras);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setLeftMetricBean(leftMetricBean);
        rightCountBean.setLblValorMetrica(lblValorMetrica);
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);
        // Define comportamientos
        Mockito.when(cmbMulticamaras.isVisible()).thenReturn(true);
        Mockito.when(lstCamaras.iterator()).thenReturn(iterator);
        Mockito.when(iterator.hasNext()).thenReturn(true, false);
        Mockito.when(iterator.next()).thenReturn(camara);
        Mockito.when(camara.getId()).thenReturn(1);
        Mockito.when(circlesInfo.get(1)).thenReturn(null);
        Mockito.when(metric.getCirclesInfo()).thenReturn(circlesInfo);
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        Mockito.when(leftMetricBean.getMetricasModel()).thenReturn(metricasModel);
        // Invoca método a probar
        rightCountBean.processMetricaCero();
        // Verifica ejecución
        Mockito.verify(lblValorMetrica).setValue(Mockito.anyString());
        Mockito.verify(metric).setTiempoMarcas(null);
        Mockito.verify(metric).setEvaluada(true);
        Mockito.verify(metric).setCantEval(false);
        Mockito.verify(metric).setCantEvalObs(null);
        Mockito.verify(metricasModel).set(1, metric);
    }

    @Test
    public void testProcessMetricaCero3() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Metric metric = Mockito.mock(Metric.class);
        Camara camara = Mockito.mock(Camara.class);
        Label lblValorMetrica = Mockito.mock(Label.class);
        List<Camara> lstCamaras = Mockito.mock(List.class);
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        Iterator<Camara> iterator = Mockito.mock(Iterator.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setMetric(metric);
        rightCountBean.setCurrentCameraId(0);
        rightCountBean.setLstCamaras(lstCamaras);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setLeftMetricBean(leftMetricBean);
        rightCountBean.setLblValorMetrica(lblValorMetrica);
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);
        // Define comportamientos
        Mockito.when(cmbMulticamaras.isVisible()).thenReturn(true);
        Mockito.when(lstCamaras.iterator()).thenReturn(iterator);
        Mockito.when(iterator.hasNext()).thenReturn(true, false);
        Mockito.when(iterator.next()).thenReturn(camara);
        Mockito.when(camara.getId()).thenReturn(1);
        Mockito.when(circlesInfo.get(1)).thenReturn("0");
        Mockito.when(metric.getCirclesInfo()).thenReturn(circlesInfo);
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        Mockito.when(leftMetricBean.getMetricasModel()).thenReturn(metricasModel);
        // Invoca método a probar
        rightCountBean.processMetricaCero();
        // Verifica ejecución
        Mockito.verify(lblValorMetrica).setValue(Mockito.anyString());
        Mockito.verify(metric).setTiempoMarcas(null);
        Mockito.verify(metric).setEvaluada(true);
        Mockito.verify(metric).setCantEval(false);
        Mockito.verify(metric).setCantEvalObs(null);
        Mockito.verify(metricasModel).set(1, metric);
    }

    @Test
    public void testProcessMetricaCero4() {
        // Crea instancia de la clase a probar
        RightCountMetricBean rightCountBean = new RightCountMetricBean();
        // Crea mock objects
        Metric metric = Mockito.mock(Metric.class);
        Camara camara = Mockito.mock(Camara.class);
        Label lblValorMetrica = Mockito.mock(Label.class);
        Combobox cmbMulticamaras = Mockito.mock(Combobox.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        LeftMetricList leftMetricBean = Mockito.mock(LeftMetricList.class);
        HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        rightCountBean.setMetric(metric);
        rightCountBean.setCurrentCameraId(0);
        rightCountBean.setCirclesInfo(circlesInfo);
        rightCountBean.setLeftMetricBean(leftMetricBean);
        rightCountBean.setLblValorMetrica(lblValorMetrica);
        rightCountBean.setCmbMultiCamaras(cmbMulticamaras);
        // Define comportamientos
        Mockito.when(cmbMulticamaras.isVisible()).thenReturn(false);
        Mockito.when(camara.getId()).thenReturn(1);
        Mockito.when(circlesInfo.get(1)).thenReturn("0");
        Mockito.when(metric.getCirclesInfo()).thenReturn(circlesInfo);
        Mockito.when(metric.getNumMetrica()).thenReturn("1");
        Mockito.when(leftMetricBean.getMetricasModel()).thenReturn(metricasModel);
        // Invoca método a probar
        rightCountBean.processMetricaCero();
        // Verifica ejecución
        Mockito.verify(lblValorMetrica).setValue(Mockito.anyString());
        Mockito.verify(metric).setTiempoMarcas(null);
        Mockito.verify(metric).setEvaluada(true);
        Mockito.verify(metric).setCantEval(false);
        Mockito.verify(metric).setCantEvalObs(null);
        Mockito.verify(metricasModel).set(1, metric);
    }
}