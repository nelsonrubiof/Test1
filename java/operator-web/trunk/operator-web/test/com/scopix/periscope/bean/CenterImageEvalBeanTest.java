package com.scopix.periscope.bean;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

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
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;

import com.scopix.periscope.common.ShowMessage;
import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.enums.EnumEvaluationType;
import com.scopix.periscope.interfaces.LeftMetricList;
import com.scopix.periscope.interfaces.RightMetricDetail;
import com.scopix.periscope.model.Client;
import com.scopix.periscope.model.Metric;

/**
 * Clase de pruebas de com.scopix.periscope.bean.CenterImageEvalBean
 * 
 * @author Carlos
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class CenterImageEvalBeanTest {

    public CenterImageEvalBeanTest() {
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

    /**
     * Actualiza el tiempo de inicio evaluación de la métrica en el modelo de la lista izquierda
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 07/05/2013
     */
    @Test
    public void testUpdateMetricStarTime() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        LeftMetricList leftMetricList = Mockito.mock(LeftMetricList.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerImageBean.setMetric(metrica);
        centerImageBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamientos
        Mockito.when(metrica.getNumMetrica()).thenReturn("0");
        Mockito.when(metrica.getMetricId()).thenReturn(0);
        Mockito.when(metrica.getStartEvaluationTime()).thenReturn(null);
        Mockito.when(rightMetricDetail.getLeftMetricBean()).thenReturn(leftMetricList);
        Mockito.when(leftMetricList.getMetricasModel()).thenReturn(metricasModel);
        // Invoca método a probar
        centerImageBean.updateMetricStarTime();
        // Verifica ejecución
        Mockito.verify(metrica).setStartEvaluationTime(Matchers.any(Date.class));
        Mockito.verify(metricasModel).set(0, metrica);
    }

    /**
     * Actualiza el tiempo de inicio evaluación de la métrica en el modelo de la lista izquierda
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 07/05/2013
     */
    @Test
    public void testUpdateMetricStarTime2() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock objects
        Metric metrica = Mockito.mock(Metric.class);
        ListModelList metricasModel = Mockito.mock(ListModelList.class);
        LeftMetricList leftMetricList = Mockito.mock(LeftMetricList.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerImageBean.setMetric(metrica);
        centerImageBean.setRightMetricDetail(rightMetricDetail);
        // Define comportamientos
        Mockito.when(metrica.getNumMetrica()).thenReturn("0");
        Mockito.when(metrica.getMetricId()).thenReturn(0);
        Mockito.when(metrica.getStartEvaluationTime()).thenReturn(new Date());
        Mockito.when(rightMetricDetail.getLeftMetricBean()).thenReturn(leftMetricList);
        Mockito.when(leftMetricList.getMetricasModel()).thenReturn(metricasModel);
        // Invoca método a probar
        centerImageBean.updateMetricStarTime();
    }

    /**
     * Establece el src de la imágen evidencia
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testProcessEvidenceSrc() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock objects
        String alternateSrc = "src";
        Client cliente = Mockito.mock(Client.class);
        Image imgEvidence = Mockito.mock(Image.class);
        Session mySession = Mockito.mock(Session.class);
        // Inyecta dependencias
        centerImageBean.setMySession(mySession);
        centerImageBean.setImgEvidence(imgEvidence);
        centerImageBean.setAlternateSrc(alternateSrc);
        // Define comportamientos
        Mockito.when(mySession.getAttribute("CLIENT")).thenReturn(cliente);
        Mockito.when(cliente.getTemplateUrl()).thenReturn("url");
        Mockito.when(cliente.getOperatorImgServicesURL()).thenReturn("url");
        // Invoca método a probar
        try {
            centerImageBean.processEvidenceSrc();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Verifica comportamiento
        Mockito.verify(imgEvidence).setSrc(alternateSrc);
    }

    /**
     * Establece el src de la imágen evidencia
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testProcessEvidenceSrc2() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock objects
        String evidenceUrl = "url";
        Client cliente = Mockito.mock(Client.class);
        Image imgEvidence = Mockito.mock(Image.class);
        Session mySession = Mockito.mock(Session.class);
        // Inyecta dependencias
        centerImageBean.setMySession(mySession);
        centerImageBean.setEvidenceURL(evidenceUrl);
        centerImageBean.setImgEvidence(imgEvidence);
        // Define comportamientos
        Mockito.when(mySession.getAttribute("CLIENT")).thenReturn(cliente);
        Mockito.when(cliente.getName()).thenReturn("name");
        Mockito.when(cliente.getEvidenceUrl()).thenReturn("url");
        Mockito.when(cliente.getTemplateUrl()).thenReturn("url");
        Mockito.when(cliente.getOperatorImgServicesURL()).thenReturn("url");
        // Invoca método a probar
        try {
            centerImageBean.processEvidenceSrc();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Verifica comportamiento
        Mockito.verify(imgEvidence, Mockito.never()).setSrc(Matchers.anyString());
    }

    /**
     * Establece el src de la imágen evidencia
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testProcessEvidenceSrc3() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock objects
        String evidenceUrl = "127.0.0.1/periscope.data/evidence/mallmarina/002";
        Client cliente = Mockito.mock(Client.class);
        Image imgEvidence = Mockito.mock(Image.class);
        Session mySession = Mockito.mock(Session.class);
        // Inyecta dependencias
        centerImageBean.setMySession(mySession);
        centerImageBean.setEvidenceURL(evidenceUrl);
        centerImageBean.setImgEvidence(imgEvidence);
        // Define comportamientos
        Mockito.when(mySession.getAttribute("CLIENT")).thenReturn(cliente);
        Mockito.when(cliente.getName()).thenReturn("mallmarina");
        Mockito.when(cliente.getEvidenceUrl()).thenReturn("url");
        Mockito.when(cliente.getTemplateUrl()).thenReturn("url");
        Mockito.when(cliente.getOperatorImgServicesURL()).thenReturn("url");
        // Invoca método a probar
        try {
            centerImageBean.processEvidenceSrc();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Verifica comportamiento
        Mockito.verify(imgEvidence).setSrc(Matchers.anyString());
    }

    /**
     * Si la métrica tiene figuras previamente marcadas, dibuja la figura (círculo o cuadrado) Tipo CIRCLE
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testProcessMetricShapes() {
        Button btnDeshacer = Mockito.mock(Button.class);
        Button btnDeshacerTodo = Mockito.mock(Button.class);

        try {
            // Crea instancia de la clase a probar
            CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
            // Crea mock objects
            Metric metric = Mockito.mock(Metric.class);
            HashMap<Integer, String> circlesInfo = Mockito.mock(HashMap.class);
            // Inyecta dependencias
            centerImageBean.setMetric(metric);
            centerImageBean.setBtnDeshacer(btnDeshacer);
            centerImageBean.setBtnDeshacerTodo(btnDeshacerTodo);
            // Se define comportamiento
            Mockito.when(metric.getType()).thenReturn(EnumEvaluationType.NUMBER_INPUT.toString());
            Mockito.when(metric.getCurrentCameraId()).thenReturn(0);

            Mockito.when(metric.getCirclesInfo()).thenReturn(circlesInfo);
            Mockito.when(circlesInfo.get(0)).thenReturn("10px:10px_100px:100px#");
            // Se invoca el método a probar
            centerImageBean.processMetricShapes();

            // Se invoca la clase utilitaria de ZK: Clients.evalJavaScript(xx) la cual invoca de manera estática
            // la función javascript, lo cual no puede simularse a través de mockito
        } catch (Exception e) {
        }
        // Verifica que los botones de deshacer hayan sido deshabilitados (NUMBER_INPUT)
        Mockito.verify(btnDeshacer).setDisabled(true);
        Mockito.verify(btnDeshacerTodo).setDisabled(true);
    }

    /**
     * Si la métrica tiene figuras previamente marcadas, dibuja la figura (círculo o cuadrado) Tipo SQUARE
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test(expected = NullPointerException.class)
    public void testProcessMetricShapes2() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock objects
        Metric metric = Mockito.mock(Metric.class);
        HashMap<Integer, String> squaresInfo = Mockito.mock(HashMap.class);
        // Inyecta dependencias
        centerImageBean.setMetric(metric);

        // Se define comportamiento
        Mockito.when(metric.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        Mockito.when(metric.getCurrentCameraId()).thenReturn(0);
        Mockito.when(metric.getDescription()).thenReturn("1");

        Mockito.when(metric.getSquaresInfo()).thenReturn(squaresInfo);
        Mockito.when(squaresInfo.get(0)).thenReturn("10px:10px_100px:100px#");

        // Se invoca el método a probar
        centerImageBean.processMetricShapes();

        // Se invoca la clase utilitaria de ZK: Clients.evalJavaScript(xx) la cual invoca de manera estática
        // la función javascript, lo cual no puede simularse a través de mockito, por lo tanto se especifíca
        // que este test retorna una excepción del tipo: NullPointerException
    }

    /**
     * Dibuja una figura (círculo o cuadrado) dependiendo del tipo
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testDrawShapeByJavaScript() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea simulación de variables
        String type = "SQUARE";
        String shapeInfo = "10px:10px_100px:100px#"; // #left:top_width:height#
        // Se invoca la clase utilitaria de ZK: Clients.evalJavaScript(xx) la cual invoca de manera estática
        // la función javascript, lo cual no puede simularse a través de mockito, por lo tanto se especifíca
        // que este test retorna una excepción del tipo: NullPointerException
        centerImageBean.drawShapeByJavaScript(shapeInfo, type);
    }

    /**
     * Dibuja una figura (círculo o cuadrado) dependiendo del tipo
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test(expected = NullPointerException.class)
    public void testDrawShapeByJavaScript2() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea simulación de variables
        String type = "YES_SQUARE";
        String shapeInfo = "10px:10px_100px:100px#"; // #left:top_width:height#
        // Se invoca la clase utilitaria de ZK: Clients.evalJavaScript(xx) la cual invoca de manera estática
        // la función javascript, lo cual no puede simularse a través de mockito, por lo tanto se especifíca
        // que este test retorna una excepción del tipo: NullPointerException
        centerImageBean.drawShapeByJavaScript(shapeInfo, type);
    }

    /**
     * Dibuja una figura (círculo o cuadrado) dependiendo del tipo
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test(expected = NullPointerException.class)
    public void testDrawShapeByJavaScript3() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea simulación de variables
        String type = "NO_SQUARE";
        String shapeInfo = "10px:10px_100px:100px#"; // #left:top_width:height#
        // Se invoca la clase utilitaria de ZK: Clients.evalJavaScript(xx) la cual invoca de manera estática
        // la función javascript, lo cual no puede simularse a través de mockito, por lo tanto se especifíca
        // que este test retorna una excepción del tipo: NullPointerException
        centerImageBean.drawShapeByJavaScript(shapeInfo, type);
    }

    /**
     * Dibuja una figura (círculo o cuadrado) dependiendo del tipo
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testDrawShapeByJavaScript4() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea simulación de variables
        String type = "CIRCLE";
        String shapeInfo = "10px:10px_100px:100px#"; // #left:top_width:height#
        // Se invoca la clase utilitaria de ZK: Clients.evalJavaScript(xx) la cual invoca de manera estática
        // la función javascript, lo cual no puede simularse a través de mockito, por lo tanto se especifíca
        // que este test retorna una excepción del tipo: NullPointerException
        centerImageBean.drawShapeByJavaScript(shapeInfo, type);
    }

    /**
     * Dibuja una figura (círculo o cuadrado) dependiendo del tipo
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testDrawShapeByJavaScript5() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea simulación de variables
        String type = "X";
        String shapeInfo = "10px:10px_100px:100px#"; // #left:top_width:height#
        // Se invoca la clase utilitaria de ZK: Clients.evalJavaScript(xx) la cual invoca de manera estática
        // la función javascript, lo cual no puede simularse a través de mockito, por lo tanto se especifíca
        // que este test retorna una excepción del tipo: NullPointerException
        centerImageBean.drawShapeByJavaScript(shapeInfo, type);
    }

    /**
     * Oculta la plantilla de la evidencia
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testOnClickPlantilla2() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock objects
        Image imgPlantilla = Mockito.mock(Image.class);
        Label lblPlantilla = Mockito.mock(Label.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Inyecta dependencia
        centerImageBean.setImgPlantilla(imgPlantilla);
        centerImageBean.setLblPlantilla(lblPlantilla);
        centerImageBean.setShowMessage(showMessage);
        // Define comportamiento
        Mockito.when(lblPlantilla.getValue()).thenReturn("N");
        // Invoca método a probar
        centerImageBean.onClickPlantilla(null);
        // Verifica si la plantilla fue ocultada
        Mockito.verify(imgPlantilla, Mockito.never()).setVisible(true);
    }

    /**
     * Actualiza valor de la métrica, invocado cuando se hace click sobre la evidencia o se presionan los botones deshacer o
     * deshacer todo
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testOnClickUpdateMetricValue() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock object
        Label lblValorMetrica = Mockito.mock(Label.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Define comportamiento
        Mockito.when(rightMetricDetail.getLblValorMetrica()).thenReturn(lblValorMetrica);
        Mockito.when(rightMetricDetail.getLblValorMetrica().getValue()).thenReturn("1");
        // Inyecta dependencia
        centerImageBean.setRightMetricDetail(rightMetricDetail);
        // Invoca método a probar
        centerImageBean.onClickUpdateMetricValue(null);
        // Verifica ejecución
        Mockito.verify(rightMetricDetail).updateMetricValue("1");
    }

    /**
     * Actualiza valor de la métrica
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testUpdateMetricValue() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock objects
        Label lblValorMetrica = Mockito.mock(Label.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Define comportamiento
        Mockito.when(rightMetricDetail.getLblValorMetrica()).thenReturn(lblValorMetrica);
        Mockito.when(rightMetricDetail.getLblValorMetrica().getValue()).thenReturn("1");
        // Inyecta dependencia
        centerImageBean.setRightMetricDetail(rightMetricDetail);
        // Invoca método a probar
        centerImageBean.updateMetricValue();
        // Verifica ejecución
        Mockito.verify(rightMetricDetail).updateMetricValue("1");
    }

    @Test
    public void testGetRightMetricDetail() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock object
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Se inyecta dependencia
        centerImageBean.setRightMetricDetail(rightMetricDetail);

        Assert.assertNotNull(centerImageBean.getRightMetricDetail());
        Assert.assertEquals(rightMetricDetail, centerImageBean.getRightMetricDetail());
    }

    @Test
    public void testGetEvidenceURL() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Se inyecta dependencia
        String evidenceURL = "http://domain/evidence";
        centerImageBean.setEvidenceURL(evidenceURL);

        Assert.assertNotNull(centerImageBean.getEvidenceURL());
        Assert.assertEquals(evidenceURL, centerImageBean.getEvidenceURL());
    }

    @Test
    public void testGetEvidenceType() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Se inyecta dependencia
        String evidenceType = "1";
        centerImageBean.setEvidenceType(evidenceType);

        Assert.assertNotNull(centerImageBean.getEvidenceType());
        Assert.assertEquals(evidenceType, centerImageBean.getEvidenceType());
    }

    @Test
    public void testBindComponentAndParent() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock object
        Include centerInclude = Mockito.mock(Include.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Invoca método a probar
        centerImageBean.bindComponentAndParent(centerInclude, rightMetricDetail);
        // Verifica ejecución
        Assert.assertNotNull(centerImageBean.getRightMetricDetail());
    }

    @Test
    public void testSetMetric() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        // Se inyecta dependencia
        centerImageBean.setMetric(metric);

        Assert.assertNotNull(centerImageBean.getMetric());
        Assert.assertEquals(metric, centerImageBean.getMetric());
    }

    @Test
    public void testSetMetricType() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Se inyecta dependencia
        String metricType = "1";
        centerImageBean.setMetricType(metricType);

        Assert.assertNotNull(centerImageBean.getMetricType());
        Assert.assertEquals(metricType, centerImageBean.getMetricType());
    }

    @Test
    public void testGetImgEvidence() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock object
        Image imgEvidence = Mockito.mock(Image.class);
        // Se inyecta dependencia
        centerImageBean.setImgEvidence(imgEvidence);

        Assert.assertNotNull(centerImageBean.getImgEvidence());
        Assert.assertEquals(imgEvidence, centerImageBean.getImgEvidence());
    }

    @Test
    public void testGetBtnDeshacer() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock object
        Button btnDeshacer = Mockito.mock(Button.class);
        // Se inyecta dependencia
        centerImageBean.setBtnDeshacer(btnDeshacer);

        Assert.assertNotNull(centerImageBean.getBtnDeshacer());
        Assert.assertEquals(btnDeshacer, centerImageBean.getBtnDeshacer());
    }

    @Test
    public void testGetBtnDeshacerTodo() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock object
        Button btnDeshacerTodo = Mockito.mock(Button.class);
        // Se inyecta dependencia
        centerImageBean.setBtnDeshacerTodo(btnDeshacerTodo);

        Assert.assertNotNull(centerImageBean.getBtnDeshacerTodo());
        Assert.assertEquals(btnDeshacerTodo, centerImageBean.getBtnDeshacerTodo());
    }

    @Test
    public void testGetTemplatePath() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Se inyecta dependencia
        String templatePath = "http://domain/template";
        centerImageBean.setTemplatePath(templatePath);

        Assert.assertNotNull(centerImageBean.getTemplatePath());
        Assert.assertEquals(templatePath, centerImageBean.getTemplatePath());
    }

    @Test
    public void testGetImgPlantilla() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock object
        Image imgPlantilla = Mockito.mock(Image.class);
        // Se inyecta dependencia
        centerImageBean.setImgPlantilla(imgPlantilla);

        Assert.assertNotNull(centerImageBean.getImgPlantilla());
        Assert.assertEquals(imgPlantilla, centerImageBean.getImgPlantilla());
    }

    @Test
    public void testGetLblPlantilla() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock object
        Label lblPlantilla = Mockito.mock(Label.class);
        // Se inyecta dependencia
        centerImageBean.setLblPlantilla(lblPlantilla);

        Assert.assertNotNull(centerImageBean.getLblPlantilla());
        Assert.assertEquals(lblPlantilla, centerImageBean.getLblPlantilla());
    }

    @Test
    public void testGetUserCredentialManager() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock object
        UserCredentialManager userCredentialMgr = Mockito.mock(UserCredentialManager.class);
        // Se inyecta dependencia
        centerImageBean.setUserCredentialManager(userCredentialMgr);

        Assert.assertNotNull(centerImageBean.getUserCredentialManager());
        Assert.assertEquals(userCredentialMgr, centerImageBean.getUserCredentialManager());
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserCredentialManager2() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        Assert.assertNotNull(centerImageBean.getUserCredentialManager());
    }

    @Test
    public void testGetExecution() {
        // Crea instancia de la clase a probar
        CenterImageEvalBean centerImageBean = new CenterImageEvalBean();
        // Crea mock object
        Execution execution = Mockito.mock(Execution.class);
        // Se inyecta dependencia
        centerImageBean.setExecution(execution);

        Assert.assertNotNull(centerImageBean.getExecution());
        Assert.assertEquals(execution, centerImageBean.getExecution());
    }
}