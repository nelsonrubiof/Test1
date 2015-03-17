package com.scopix.periscope.bean;

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
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;

import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.enums.EnumEvaluationType;
import com.scopix.periscope.interfaces.RightMetricDetail;
import com.scopix.periscope.model.Metric;

/**
 * Clase de pruebas de com.scopix.periscope.bean.CenterVideoEvalBean
 * 
 * @author Carlos
 */
@SuppressWarnings(value = { "unchecked" })
public class CenterVideoEvalBeanTest {

    public CenterVideoEvalBeanTest() {
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
     * Establece disponibilidad de los botones verMarcas, deshacer y deshacerTodo
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testSetButtonsAvailability() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        Button btnVerMarcas = Mockito.mock(Button.class);
        Button btnDeshacer = Mockito.mock(Button.class);
        Button btnDeshacerTodo = Mockito.mock(Button.class);
        String metricType = EnumEvaluationType.NUMBER_INPUT.toString();
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerVideoEval.setMetricType(metricType);
        centerVideoEval.setBtnDeshacer(btnDeshacer);
        centerVideoEval.setBtnVerMarcas(btnVerMarcas);
        centerVideoEval.setBtnDeshacerTodo(btnDeshacerTodo);
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Invoca método a probar
        centerVideoEval.setButtonsAvailability();
        // Verifica ejecución
        Mockito.verify(btnDeshacer).setDisabled(true);
        Mockito.verify(btnVerMarcas).setDisabled(true);
        Mockito.verify(btnDeshacerTodo).setDisabled(true);
    }

    /**
     * Establece disponibilidad de los botones verMarcas, deshacer y deshacerTodo
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testSetButtonsAvailability2() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        Button btnVerMarcas = Mockito.mock(Button.class);
        Button btnDeshacer = Mockito.mock(Button.class);
        Button btnDeshacerTodo = Mockito.mock(Button.class);
        String metricType = EnumEvaluationType.COUNTING.toString();
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerVideoEval.setMetricType(metricType);
        centerVideoEval.setBtnDeshacer(btnDeshacer);
        centerVideoEval.setBtnVerMarcas(btnVerMarcas);
        centerVideoEval.setBtnDeshacerTodo(btnDeshacerTodo);
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Invoca método a probar
        centerVideoEval.setButtonsAvailability();
        // Verifica ejecución
        Mockito.verify(btnDeshacer, Mockito.never()).setDisabled(true);
        Mockito.verify(btnVerMarcas, Mockito.never()).setDisabled(true);
        Mockito.verify(btnDeshacerTodo, Mockito.never()).setDisabled(true);
    }

    /**
     * Establece disponibilidad de los botones verMarcas, deshacer y deshacerTodo
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testSetButtonsAvailability3() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        Button btnVerMarcas = Mockito.mock(Button.class);
        Button btnDeshacer = Mockito.mock(Button.class);
        Button btnDeshacerTodo = Mockito.mock(Button.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerVideoEval.setBtnDeshacer(btnDeshacer);
        centerVideoEval.setBtnVerMarcas(btnVerMarcas);
        centerVideoEval.setBtnDeshacerTodo(btnDeshacerTodo);
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Invoca método a probar
        centerVideoEval.setButtonsAvailability();
        // Verifica ejecución
        Mockito.verify(btnDeshacer, Mockito.never()).setDisabled(true);
        Mockito.verify(btnVerMarcas, Mockito.never()).setDisabled(true);
        Mockito.verify(btnDeshacerTodo, Mockito.never()).setDisabled(true);
    }

    /**
     * Establece disponibilidad de los botones verMarcas, deshacer y deshacerTodo
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testSetButtonsAvailability4() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        Button btnDeshacer = Mockito.mock(Button.class);
        Button btnDeshacerTodo = Mockito.mock(Button.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerVideoEval.setBtnDeshacer(btnDeshacer);
        centerVideoEval.setBtnDeshacerTodo(btnDeshacerTodo);
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Invoca método a probar
        centerVideoEval.setButtonsAvailability();
        // Verifica ejecución
        Mockito.verify(btnDeshacer, Mockito.never()).setDisabled(true);
        Mockito.verify(btnDeshacerTodo, Mockito.never()).setDisabled(true);
    }

    /**
     * Establece disponibilidad de los botones verMarcas, deshacer y deshacerTodo
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testSetButtonsAvailability5() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        Metric metric = Mockito.mock(Metric.class);
        Button btnDeshacer = Mockito.mock(Button.class);
        Button btnDeshacerTodo = Mockito.mock(Button.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerVideoEval.setMetric(metric);
        centerVideoEval.setBtnDeshacer(btnDeshacer);
        centerVideoEval.setBtnDeshacerTodo(btnDeshacerTodo);
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Define comportamiento
        Mockito.when(metric.getType()).thenReturn(EnumEvaluationType.NUMBER_INPUT.toString());
        // Invoca método a probar
        centerVideoEval.setButtonsAvailability();
        // Verifica ejecución
        // Mockito.verify(btnDeshacer).setDisabled(true);
        // Mockito.verify(btnDeshacerTodo).setDisabled(true);
    }

    /**
     * Establece disponibilidad de los botones verMarcas, deshacer y deshacerTodo
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testSetButtonsAvailability6() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        Metric metric = Mockito.mock(Metric.class);
        Button btnDeshacer = Mockito.mock(Button.class);
        Button btnDeshacerTodo = Mockito.mock(Button.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerVideoEval.setMetric(metric);
        centerVideoEval.setBtnDeshacer(btnDeshacer);
        centerVideoEval.setBtnDeshacerTodo(btnDeshacerTodo);
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Define comportamiento
        Mockito.when(metric.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        // Invoca método a probar
        centerVideoEval.setButtonsAvailability();
        // Verifica ejecución
        Mockito.verify(btnDeshacer, Mockito.never()).setDisabled(true);
        Mockito.verify(btnDeshacerTodo, Mockito.never()).setDisabled(true);
    }

    /**
     * Establece disponibilidad de los botones verMarcas, deshacer y deshacerTodo
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    // @Test
    // public void testSetButtonsAvailability7() {
    // //Crea instancia de la clase a probar
    // CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
    // //Crea mock objects
    // Metric metric = Mockito.mock(Metric.class);
    // RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
    // //Inyecta dependencias
    // centerVideoEval.setMetric(metric);
    // centerVideoEval.setRightMetricDetail(rightMetricDetail);
    // //Define comportamiento
    // Mockito.when(metric.getTiempoMarcas()).thenReturn(null);
    // Mockito.when(metric.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
    // //Invoca método a probar
    // centerVideoEval.setButtonsAvailability();
    // }

    /**
     * Establece disponibilidad de los botones verMarcas, deshacer y deshacerTodo
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testSetButtonsAvailability8() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        HashMap<Integer, String> tiempoMarcas = Mockito.mock(HashMap.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Crea mock objects
        Metric metric = Mockito.mock(Metric.class);
        // Inyecta dependencias
        centerVideoEval.setMetric(metric);
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Define comportamiento
        Mockito.when(metric.getTiempoMarcas()).thenReturn(tiempoMarcas);
        Mockito.when(metric.getType()).thenReturn(EnumEvaluationType.COUNTING.toString());
        // Invoca método a probar
        centerVideoEval.setButtonsAvailability();
        // Se invoca la clase utilitaria de ZK: Clients.evalJavaScript(xx) la cual invoca de manera estática
        // la función javascript, lo cual no puede simularse a través de mockito, por lo tanto se especifíca
        // que este test retorna una excepción del tipo: NullPointerException
    }

    /**
     * Muestra/Oculta snapshot o imágen de plantilla dependiendo del estado del reproductor
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 08/03/2013
     */
    @Test
    public void testOnClickPauseOrPlay() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        Image jwImage = Mockito.mock(Image.class);
        Label lblElapsed = Mockito.mock(Label.class);
        Image imgPlantilla = Mockito.mock(Image.class);
        Image myTransparent = Mockito.mock(Image.class);
        Label lblIsPlayerTime = Mockito.mock(Label.class);
        String metricType = EnumEvaluationType.YES_NO.toString();
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerVideoEval.setJwImage(jwImage);
        centerVideoEval.setMetricType(metricType);
        centerVideoEval.setLblElapsed(lblElapsed);
        centerVideoEval.setMyTransparent(myTransparent);
        centerVideoEval.setImgPlantilla(imgPlantilla);
        centerVideoEval.setLblIsPlayerTime(lblIsPlayerTime);
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Se define comportamiento
        Mockito.when(lblElapsed.getValue()).thenReturn("1PL");
        Mockito.when(lblIsPlayerTime.getValue()).thenReturn("N");
        // Se invoca el método a probar
        centerVideoEval.onClickPauseOrPlay(null);
        // Verifica ejecución
        Mockito.verify(jwImage).setVisible(false);
        Mockito.verify(imgPlantilla).setVisible(false);
        Mockito.verify(rightMetricDetail).setTimeButtonsDisabledState(true);
    }

    /**
     * Muestra/Oculta snapshot o imágen de plantilla dependiendo del estado del reproductor
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 08/03/2013
     */
    @Test(expected = NullPointerException.class)
    public void testOnClickPauseOrPlay2() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        Image jwImage = Mockito.mock(Image.class);
        Label lblElapsed = Mockito.mock(Label.class);
        Image imgPlantilla = Mockito.mock(Image.class);
        Image myTransparent = Mockito.mock(Image.class);
        Label lblIsPlayerTime = Mockito.mock(Label.class);
        String metricType = null;
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerVideoEval.setJwImage(jwImage);
        centerVideoEval.setMetricType(metricType);
        centerVideoEval.setLblElapsed(lblElapsed);
        centerVideoEval.setMyTransparent(myTransparent);
        centerVideoEval.setImgPlantilla(imgPlantilla);
        centerVideoEval.setLblIsPlayerTime(lblIsPlayerTime);
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Se define comportamiento
        Mockito.when(lblIsPlayerTime.getValue()).thenReturn("S");
        Mockito.when(lblElapsed.getValue()).thenReturn("1PL");
        // Se invoca el método a probar
        centerVideoEval.onClickPauseOrPlay(null);
        // Verifica ejecución
        Mockito.verify(jwImage).setVisible(false);
        Mockito.verify(imgPlantilla).setVisible(false);
        Mockito.verify(rightMetricDetail, Mockito.never()).setTimeButtonsDisabledState(true);
    }

    /**
     * Muestra/Oculta snapshot o imágen de plantilla dependiendo del estado del reproductor
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 08/03/2013
     */
    @Test
    public void testOnClickPauseOrPlay3() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        Image jwImage = Mockito.mock(Image.class);
        Label lblElapsed = Mockito.mock(Label.class);
        Image imgPlantilla = Mockito.mock(Image.class);
        Image myTransparent = Mockito.mock(Image.class);
        Label lblIsPlayerTime = Mockito.mock(Label.class);
        String metricType = EnumEvaluationType.COUNTING.toString();
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerVideoEval.setJwImage(jwImage);
        centerVideoEval.setMetricType(metricType);
        centerVideoEval.setLblElapsed(lblElapsed);
        centerVideoEval.setMyTransparent(myTransparent);
        centerVideoEval.setImgPlantilla(imgPlantilla);
        centerVideoEval.setLblIsPlayerTime(lblIsPlayerTime);
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Se define comportamiento
        Mockito.when(lblElapsed.getValue()).thenReturn("1PL");
        Mockito.when(lblIsPlayerTime.getValue()).thenReturn("N");
        // Se invoca el método a probar
        centerVideoEval.onClickPauseOrPlay(null);
        // Verifica ejecución
        Mockito.verify(jwImage).setVisible(false);
        Mockito.verify(imgPlantilla).setVisible(false);
        Mockito.verify(rightMetricDetail, Mockito.never()).setTimeButtonsDisabledState(true);
    }

    /**
     * Muestra/Oculta snapshot o imágen de plantilla dependiendo del estado del reproductor
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 08/03/2013
     */
    @Test
    public void testOnClickPauseOrPlay4() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        Image jwImage = Mockito.mock(Image.class);
        Label lblElapsed = Mockito.mock(Label.class);
        Image imgPlantilla = Mockito.mock(Image.class);
        Image myTransparent = Mockito.mock(Image.class);
        Label lblIsPlayerTime = Mockito.mock(Label.class);
        String metricType = EnumEvaluationType.YES_NO.toString();
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerVideoEval.setJwImage(jwImage);
        centerVideoEval.setMetricType(metricType);
        centerVideoEval.setLblElapsed(lblElapsed);
        centerVideoEval.setMyTransparent(myTransparent);
        centerVideoEval.setImgPlantilla(imgPlantilla);
        centerVideoEval.setLblIsPlayerTime(lblIsPlayerTime);
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Se define comportamiento
        Mockito.when(lblElapsed.getValue()).thenReturn("1PAPA");
        Mockito.when(lblIsPlayerTime.getValue()).thenReturn("S");
        // Se invoca el método a probar
        centerVideoEval.onClickPauseOrPlay(null);
        // Verifica ejecución
        Mockito.verify(jwImage).setVisible(false);
        Mockito.verify(jwImage, Mockito.never()).setSrc(Matchers.anyString());
        // Mockito.verify(rightMetricDetail).setTimeButtonsDisabledState(false);
    }

    /**
     * Muestra/Oculta snapshot o imágen de plantilla dependiendo del estado del reproductor
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 08/03/2013
     */
    @Test
    public void testOnClickPauseOrPlay5() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        String metricType = null;
        Image jwImage = Mockito.mock(Image.class);
        Label lblElapsed = Mockito.mock(Label.class);
        Image imgPlantilla = Mockito.mock(Image.class);
        Image myTransparent = Mockito.mock(Image.class);
        Label lblIsPlayerTime = Mockito.mock(Label.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerVideoEval.setJwImage(jwImage);
        centerVideoEval.setMetricType(metricType);
        centerVideoEval.setLblElapsed(lblElapsed);
        centerVideoEval.setMyTransparent(myTransparent);
        centerVideoEval.setImgPlantilla(imgPlantilla);
        centerVideoEval.setLblIsPlayerTime(lblIsPlayerTime);
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Se define comportamiento
        Mockito.when(lblElapsed.getValue()).thenReturn("10PA");
        Mockito.when(lblIsPlayerTime.getValue()).thenReturn("S");
        // Se invoca el método a probar
        centerVideoEval.onClickPauseOrPlay(null);
        // Verifica ejecución
        Mockito.verify(jwImage).setVisible(false);
        Mockito.verify(rightMetricDetail, Mockito.never()).setTimeButtonsDisabledState(false);
    }

    /**
     * Muestra/Oculta snapshot o imágen de plantilla dependiendo del estado del reproductor
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 08/03/2013
     */
    @Test
    public void testOnClickPauseOrPlay6() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        Image jwImage = Mockito.mock(Image.class);
        Label lblElapsed = Mockito.mock(Label.class);
        Image imgPlantilla = Mockito.mock(Image.class);
        Image myTransparent = Mockito.mock(Image.class);
        Label lblIsPlayerTime = Mockito.mock(Label.class);
        String metricType = EnumEvaluationType.COUNTING.toString();
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerVideoEval.setJwImage(jwImage);
        centerVideoEval.setMetricType(metricType);
        centerVideoEval.setLblElapsed(lblElapsed);
        centerVideoEval.setImgPlantilla(imgPlantilla);
        centerVideoEval.setMyTransparent(myTransparent);
        centerVideoEval.setLblIsPlayerTime(lblIsPlayerTime);
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Se define comportamiento
        Mockito.when(lblElapsed.getValue()).thenReturn("10PA");
        Mockito.when(lblIsPlayerTime.getValue()).thenReturn("S");
        // Se invoca el método a probar
        centerVideoEval.onClickPauseOrPlay(null);
        // Verifica ejecución
        Mockito.verify(jwImage).setVisible(false);
        Mockito.verify(rightMetricDetail, Mockito.never()).setTimeButtonsDisabledState(false);
    }

    /**
     * Muestra/Oculta snapshot o imágen de plantilla dependiendo del estado del reproductor
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 08/03/2013
     */
    @Test
    public void testOnClickPauseOrPlay7() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        Image jwImage = Mockito.mock(Image.class);
        Label lblElapsed = Mockito.mock(Label.class);
        Image imgPlantilla = Mockito.mock(Image.class);
        Image myTransparent = Mockito.mock(Image.class);
        Label lblIsPlayerTime = Mockito.mock(Label.class);
        String metricType = EnumEvaluationType.YES_NO.toString();
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerVideoEval.setJwImage(jwImage);
        centerVideoEval.setMetricType(metricType);
        centerVideoEval.setLblElapsed(lblElapsed);
        centerVideoEval.setMyTransparent(myTransparent);
        centerVideoEval.setImgPlantilla(imgPlantilla);
        centerVideoEval.setLblIsPlayerTime(lblIsPlayerTime);
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Se define comportamiento
        Mockito.when(lblElapsed.getValue()).thenReturn("1PLPA");
        Mockito.when(lblIsPlayerTime.getValue()).thenReturn("S");
        // Se invoca el método a probar
        centerVideoEval.onClickPauseOrPlay(null);
        // Verifica ejecución
        Mockito.verify(jwImage).setVisible(false);
        // Mockito.verify(rightMetricDetail).setTimeButtonsDisabledState(false);
    }

    /**
     * Muestra/Oculta snapshot o imágen de plantilla dependiendo del estado del reproductor
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 08/03/2013
     */
    @Test
    public void testOnClickPauseOrPlay8() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        Image jwImage = Mockito.mock(Image.class);
        Label lblElapsed = Mockito.mock(Label.class);
        Image imgPlantilla = Mockito.mock(Image.class);
        Image myTransparent = Mockito.mock(Image.class);
        Label lblIsPlayerTime = Mockito.mock(Label.class);
        String metricType = EnumEvaluationType.YES_NO.toString();
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerVideoEval.setJwImage(jwImage);
        centerVideoEval.setMetricType(metricType);
        centerVideoEval.setLblElapsed(lblElapsed);
        centerVideoEval.setMyTransparent(myTransparent);
        centerVideoEval.setImgPlantilla(imgPlantilla);
        centerVideoEval.setLblIsPlayerTime(lblIsPlayerTime);
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Se define comportamiento
        Mockito.when(lblElapsed.getValue()).thenReturn("1PXPA");
        Mockito.when(lblIsPlayerTime.getValue()).thenReturn("S");
        // Se invoca el método a probar
        centerVideoEval.onClickPauseOrPlay(null);
        // Verifica ejecución
        Mockito.verify(jwImage).setVisible(false);
        // Mockito.verify(rightMetricDetail).setTimeButtonsDisabledState(false);
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
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea simulación de variables
        String type = "SQUARE";
        String shapeInfo = "10px:10px_100px:100px#"; // #left:top_width:height#
        // Se invoca la clase utilitaria de ZK: Clients.evalJavaScript(xx) la cual invoca de manera estática
        // la función javascript, lo cual no puede simularse a través de mockito, por lo tanto se especifíca
        // que este test retorna una excepción del tipo: NullPointerException
        centerVideoEval.drawShapeByJavaScript(shapeInfo, type);
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
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea simulación de variables
        String type = "YES_SQUARE";
        String shapeInfo = "10px:10px_100px:100px#"; // #left:top_width:height#
        // Se invoca la clase utilitaria de ZK: Clients.evalJavaScript(xx) la cual invoca de manera estática
        // la función javascript, lo cual no puede simularse a través de mockito, por lo tanto se especifíca
        // que este test retorna una excepción del tipo: NullPointerException
        centerVideoEval.drawShapeByJavaScript(shapeInfo, type);
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
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea simulación de variables
        String type = "NO_SQUARE";
        String shapeInfo = "10px:10px_100px:100px#"; // #left:top_width:height#
        // Se invoca la clase utilitaria de ZK: Clients.evalJavaScript(xx) la cual invoca de manera estática
        // la función javascript, lo cual no puede simularse a través de mockito, por lo tanto se especifíca
        // que este test retorna una excepción del tipo: NullPointerException
        centerVideoEval.drawShapeByJavaScript(shapeInfo, type);
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
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea simulación de variables
        String type = "CIRCLE";
        String shapeInfo = "10px:10px_100px:100px#"; // #left:top_width:height#
        // Se invoca la clase utilitaria de ZK: Clients.evalJavaScript(xx) la cual invoca de manera estática
        // la función javascript, lo cual no puede simularse a través de mockito, por lo tanto se especifíca
        // que este test retorna una excepción del tipo: NullPointerException
        centerVideoEval.drawShapeByJavaScript(shapeInfo, type);
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
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea simulación de variables
        String type = "X";
        String shapeInfo = "10px:10px_100px:100px#"; // #left:top_width:height#
        // Se invoca la clase utilitaria de ZK: Clients.evalJavaScript(xx) la cual invoca de manera estática
        // la función javascript, lo cual no puede simularse a través de mockito, por lo tanto se especifíca
        // que este test retorna una excepción del tipo: NullPointerException
        centerVideoEval.drawShapeByJavaScript(shapeInfo, type);
    }

    /**
     * Ejecutado cuando se hace click sobre el snapshot
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    // @Test
    // public void testOnClickEvidenceImage() {
    // //Crea instancia de la clase a probar
    // CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
    // //Crea simulación de variable
    // Label lblPause = Mockito.mock(Label.class);
    // Metric metrica = Mockito.mock(Metric.class);
    // Label lblElapsed = Mockito.mock(Label.class);
    // Label lblValorMetrica = Mockito.mock(Label.class);
    // String metricType = EnumEvaluationType.NUMBER_INPUT.toString();
    // RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
    // //Inyecta dependencias
    // centerVideoEval.setMetric(metrica);
    // centerVideoEval.setLblPause(lblPause);
    // centerVideoEval.setMetricType(metricType);
    // centerVideoEval.setLblElapsed(lblElapsed);
    // centerVideoEval.setRightMetricDetail(rightMetricDetail);
    // //Define comportamiento
    // Mockito.when(lblPause.getValue()).thenReturn("S");
    // Mockito.when(rightMetricDetail.getLblValorMetrica()).thenReturn(lblValorMetrica);
    // Mockito.when(rightMetricDetail.getLblValorMetrica().getValue()).thenReturn("1");
    // //Se invoca el método a probar
    // centerVideoEval.onClickEvidenceImage(null);
    // //Verifica ejecución
    // Mockito.verify(rightMetricDetail).setTimeButtonsDisabledState(false);
    // }

    /**
     * Ejecutado cuando se hace click sobre el snapshot
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    // @Test
    // public void testOnClickEvidenceImage2() {
    // //Crea instancia de la clase a probar
    // CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
    // //Crea simulación de variable
    // String metricType = null;
    // Label lblPause = Mockito.mock(Label.class);
    // Metric metrica = Mockito.mock(Metric.class);
    // Label lblElapsed = Mockito.mock(Label.class);
    // RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
    // //Inyecta dependencias
    // centerVideoEval.setMetric(metrica);
    // centerVideoEval.setLblPause(lblPause);
    // centerVideoEval.setMetricType(metricType);
    // centerVideoEval.setLblElapsed(lblElapsed);
    // centerVideoEval.setRightMetricDetail(rightMetricDetail);
    // //Define comportamiento
    // Mockito.when(lblPause.getValue()).thenReturn("S");
    // //Se invoca el método a probar
    // centerVideoEval.onClickEvidenceImage(null);
    // //Verifica ejecución
    // Mockito.verify(rightMetricDetail).setTimeButtonsDisabledState(false);
    // }

    /**
     * Ejecutado cuando se hace click sobre el snapshot
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    // @Test
    // public void testOnClickEvidenceImage3() {
    // //Crea instancia de la clase a probar
    // CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
    // //Crea simulación de variable
    // Label lblPause = Mockito.mock(Label.class);
    // Metric metrica = Mockito.mock(Metric.class);
    // Label lblElapsed = Mockito.mock(Label.class);
    // Label lblValorMetrica = Mockito.mock(Label.class);
    // String metricType = EnumEvaluationType.COUNTING.toString();
    // RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
    // //Inyecta dependencias
    // centerVideoEval.setMetric(metrica);
    // centerVideoEval.setLblPause(lblPause);
    // centerVideoEval.setMetricType(metricType);
    // centerVideoEval.setLblElapsed(lblElapsed);
    // centerVideoEval.setRightMetricDetail(rightMetricDetail);
    // //Define comportamiento
    // Mockito.when(lblPause.getValue()).thenReturn("S");
    // Mockito.when(rightMetricDetail.getLblValorMetrica()).thenReturn(lblValorMetrica);
    // Mockito.when(rightMetricDetail.getLblValorMetrica().getValue()).thenReturn("1");
    // //Se invoca el método a probar
    // centerVideoEval.onClickEvidenceImage(null);
    // //Verifica ejecución
    // Mockito.verify(rightMetricDetail).updateMetricValue(Matchers.anyString());
    // Mockito.verify(rightMetricDetail).setTimeButtonsDisabledState(false);
    // }

    /**
     * Ejecutado cuando se hace click sobre el snapshot
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    // @Test
    // public void testOnClickEvidenceImage4() {
    // //Crea instancia de la clase a probar
    // CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
    // //Crea simulación de variable
    // Label lblPause = Mockito.mock(Label.class);
    // Metric metrica = Mockito.mock(Metric.class);
    // Label lblElapsed = Mockito.mock(Label.class);
    // Label lblValorMetrica = Mockito.mock(Label.class);
    // String metricType = EnumEvaluationType.NUMBER_INPUT.toString();
    // RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
    // //Inyecta dependencias
    // centerVideoEval.setMetric(metrica);
    // centerVideoEval.setLblPause(lblPause);
    // centerVideoEval.setMetricType(metricType);
    // centerVideoEval.setLblElapsed(lblElapsed);
    // centerVideoEval.setRightMetricDetail(rightMetricDetail);
    // //Define comportamiento
    // Mockito.when(lblPause.getValue()).thenReturn("N");
    // Mockito.when(rightMetricDetail.getLblValorMetrica()).thenReturn(lblValorMetrica);
    // Mockito.when(rightMetricDetail.getLblValorMetrica().getValue()).thenReturn("1");
    // //Se invoca el método a probar
    // centerVideoEval.onClickEvidenceImage(null);
    // //Verifica ejecución
    // Mockito.verify(rightMetricDetail, Mockito.never()).setTimeButtonsDisabledState(false);
    // }

    /**
     * Ejecutado cuando se hace click en deshacer, deshacer todo o para actualizar el valor de la métrica actual
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testOnClickUpdateMetricValue() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea simulación de variable
        Label lblValorMetrica = Mockito.mock(Label.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Inyecta dependencias
        centerVideoEval.setRightMetricDetail(rightMetricDetail);
        // Define comportamiento
        Mockito.when(rightMetricDetail.getLblValorMetrica()).thenReturn(lblValorMetrica);
        Mockito.when(rightMetricDetail.getLblValorMetrica().getValue()).thenReturn("1");
        // Se invoca el método a probar
        centerVideoEval.onClickUpdateMetricValue(null);
        // Verifica ejecución
        Mockito.verify(rightMetricDetail).updateMetricValue("1");
    }

    /**
     * Ejecutado cuando se hace click en verLimites, para ocultar o mostrar la plantilla
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test
    public void testOnClickPlantilla() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        centerVideoEval.onClickPlantilla(null);

        String templatePath = "//xxxx/harris/store/anno/mes/dia/harris_plan_40_cam5.png";
        centerVideoEval.setTemplatePath(templatePath);
        // Crea mock object
        Image imgPlantilla = Mockito.mock(Image.class);
        Label lblPlantilla = Mockito.mock(Label.class);
        // Inyecta dependencia
        centerVideoEval.setImgPlantilla(imgPlantilla);
        centerVideoEval.setLblPlantilla(lblPlantilla);
        // Define comportamiento
        Mockito.when(lblPlantilla.getValue()).thenReturn("N");
        // Se invoca el método a probar
        centerVideoEval.onClickPlantilla(null);
        // Verifica ejecución
        Mockito.verify(imgPlantilla).setVisible(false);
    }

    /**
     * Ejecutado cuando se hace click en verLimites, para ocultar o mostrar la plantilla
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    @Test(expected = NullPointerException.class)
    public void testOnClickPlantilla2() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        String templatePath = "//xxxx/harris/store/anno/mes/dia/harris_plan_40_cam5.png";
        centerVideoEval.setTemplatePath(templatePath);
        // Crea mock object
        Image imgPlantilla = Mockito.mock(Image.class);
        Label lblPlantilla = Mockito.mock(Label.class);
        // Inyecta dependencia
        centerVideoEval.setImgPlantilla(imgPlantilla);
        centerVideoEval.setLblPlantilla(lblPlantilla);
        // Define comportamiento
        Mockito.when(lblPlantilla.getValue()).thenReturn("S");
        // Se invoca el método a probar
        centerVideoEval.onClickPlantilla(null);
        // Verifica ejecución
        Mockito.verify(imgPlantilla).setVisible(false);
        // Se espera NullPointerException dado que a través de mockito no se puede setear en session un atributo
    }

    @Test
    public void testOnClickPlay() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        Image jwImage = Mockito.mock(Image.class);
        Image imgPlantilla = Mockito.mock(Image.class);
        Image myTransparent = Mockito.mock(Image.class);
        Label lblPause = Mockito.mock(Label.class);
        // Inyecta dependencias
        centerVideoEval.setJwImage(jwImage);
        centerVideoEval.setImgPlantilla(imgPlantilla);
        centerVideoEval.setMyTransparent(myTransparent);
        centerVideoEval.setLblPause(lblPause);

        Label lblIsPlayerTime = Mockito.mock(Label.class);
        centerVideoEval.setLblIsPlayerTime(lblIsPlayerTime);
        // Define comportamiento
        Mockito.when(lblPause.getValue()).thenReturn("N");
        Mockito.when(lblIsPlayerTime.getValue()).thenReturn("N");

        // Se invoca el método a probar
        centerVideoEval.onClickPlay(null);
        // Verifica ejecución
        Mockito.verify(jwImage).setVisible(false);
        Mockito.verify(imgPlantilla).setVisible(false);
    }

    @Test(expected = NullPointerException.class)
    public void testOnClickPlay2() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoEval = new CenterVideoEvalBean();
        // Crea mock objects
        Image jwImage = Mockito.mock(Image.class);
        Image imgPlantilla = Mockito.mock(Image.class);
        Image myTransparent = Mockito.mock(Image.class);
        // Inyecta dependencias
        centerVideoEval.setJwImage(jwImage);
        centerVideoEval.setImgPlantilla(imgPlantilla);
        centerVideoEval.setMyTransparent(myTransparent);

        Label lblIsPlayerTime = Mockito.mock(Label.class);
        centerVideoEval.setLblIsPlayerTime(lblIsPlayerTime);
        // Define comportamiento
        Mockito.when(lblIsPlayerTime.getValue()).thenReturn("S");

        // Se invoca el método a probar
        centerVideoEval.onClickPlay(null);
        // Verifica ejecución
        Mockito.verify(jwImage).setVisible(false);
        Mockito.verify(imgPlantilla).setVisible(false);
    }

    @Test
    public void testGetRightMetricDetail() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Crea mock object
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Se inyecta dependencia
        centerVideoBean.setRightMetricDetail(rightMetricDetail);

        Assert.assertNotNull(centerVideoBean.getRightMetricDetail());
        Assert.assertEquals(rightMetricDetail, centerVideoBean.getRightMetricDetail());
    }

    @Test
    public void testGetEvidenceType() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Se inyecta dependencia
        String evidenceType = "1";
        centerVideoBean.setEvidenceType(evidenceType);

        Assert.assertNotNull(centerVideoBean.getEvidenceType());
        Assert.assertEquals(evidenceType, centerVideoBean.getEvidenceType());
    }

    @Test
    public void testGetLblElapsed() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Crea mock object
        Label lblElapsed = Mockito.mock(Label.class);
        // Se inyecta dependencia
        centerVideoBean.setLblElapsed(lblElapsed);

        Assert.assertNotNull(centerVideoBean.getLblElapsed());
        Assert.assertEquals(lblElapsed, centerVideoBean.getLblElapsed());
    }

    @Test
    public void testGetJwImage() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Crea mock object
        Image jwImage = Mockito.mock(Image.class);
        // Se inyecta dependencia
        centerVideoBean.setJwImage(jwImage);

        Assert.assertNotNull(centerVideoBean.getJwImage());
        Assert.assertEquals(jwImage, centerVideoBean.getJwImage());
    }

    @Test
    public void testBindComponentAndParent() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Crea mock object
        Include centerInclude = Mockito.mock(Include.class);
        RightMetricDetail rightMetricDetail = Mockito.mock(RightMetricDetail.class);
        // Invoca método a probar
        centerVideoBean.bindComponentAndParent(centerInclude, rightMetricDetail);

        Assert.assertNotNull(centerVideoBean.getRightMetricDetail());
    }

    @Test
    public void testGetEvidenceURL() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Se inyecta dependencia
        String evidenceURL = "http://domain/evidence";
        centerVideoBean.setEvidenceURL(evidenceURL);

        Assert.assertNotNull(centerVideoBean.getEvidenceURL());
        Assert.assertEquals(evidenceURL, centerVideoBean.getEvidenceURL());
    }

    @Test
    public void testGetMetricType() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Se inyecta dependencia
        String metricType = "1";
        centerVideoBean.setMetricType(metricType);

        Assert.assertNotNull(centerVideoBean.getMetricType());
        Assert.assertEquals(metricType, centerVideoBean.getMetricType());
    }

    @Test
    public void testGetLblVideoURL() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Crea mock object
        Label lblVideoURL = Mockito.mock(Label.class);
        // Se inyecta dependencia
        centerVideoBean.setLblVideoURL(lblVideoURL);

        Assert.assertNotNull(centerVideoBean.getLblVideoURL());
        Assert.assertEquals(lblVideoURL, centerVideoBean.getLblVideoURL());
    }

    @Test
    public void testGetBtnVerMarcas() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Crea mock object
        Button btnVerMarcas = Mockito.mock(Button.class);
        // Se inyecta dependencia
        centerVideoBean.setBtnVerMarcas(btnVerMarcas);

        Assert.assertNotNull(centerVideoBean.getBtnVerMarcas());
        Assert.assertEquals(btnVerMarcas, centerVideoBean.getBtnVerMarcas());
    }

    @Test
    public void testGetBtnDeshacer() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Crea mock object
        Button btnDeshacer = Mockito.mock(Button.class);
        // Se inyecta dependencia
        centerVideoBean.setBtnDeshacer(btnDeshacer);

        Assert.assertNotNull(centerVideoBean.getBtnDeshacer());
        Assert.assertEquals(btnDeshacer, centerVideoBean.getBtnDeshacer());
    }

    @Test
    public void testGetBtnDeshacerTodo() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Crea mock object
        Button btnDeshacerTodo = Mockito.mock(Button.class);
        // Se inyecta dependencia
        centerVideoBean.setBtnDeshacerTodo(btnDeshacerTodo);

        Assert.assertNotNull(centerVideoBean.getBtnDeshacerTodo());
        Assert.assertEquals(btnDeshacerTodo, centerVideoBean.getBtnDeshacerTodo());
    }

    @Test
    public void testGetMetric() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Crea mock object
        Metric metric = Mockito.mock(Metric.class);
        // Se inyecta dependencia
        centerVideoBean.setMetric(metric);

        Assert.assertNotNull(centerVideoBean.getMetric());
        Assert.assertEquals(metric, centerVideoBean.getMetric());
    }

    @Test
    public void testGetHttpPath() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Se inyecta dependencia
        String httpPath = "1";
        centerVideoBean.setHttpPath(httpPath);

        Assert.assertNotNull(centerVideoBean.getHttpPath());
        Assert.assertEquals(httpPath, centerVideoBean.getHttpPath());
    }

    @Test
    public void testGetDiskPath() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Se inyecta dependencia
        String diskPath = "1";
        centerVideoBean.setDiskPath(diskPath);

        Assert.assertNotNull(centerVideoBean.getDiskPath());
        Assert.assertEquals(diskPath, centerVideoBean.getDiskPath());
    }

    @Test
    public void testGetFileName() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Se inyecta dependencia
        String fileName = "1";
        centerVideoBean.setFileName(fileName);

        Assert.assertNotNull(centerVideoBean.getFileName());
        Assert.assertEquals(fileName, centerVideoBean.getFileName());
    }

    @Test
    public void testGetTemplatePath() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Se inyecta dependencia
        String templatePath = "http://domain/template";
        centerVideoBean.setTemplatePath(templatePath);

        Assert.assertNotNull(centerVideoBean.getTemplatePath());
        Assert.assertEquals(templatePath, centerVideoBean.getTemplatePath());
    }

    @Test
    public void testGetLblPlantilla() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Crea mock object
        Label lblPlantilla = Mockito.mock(Label.class);
        // Se inyecta dependencia
        centerVideoBean.setLblPlantilla(lblPlantilla);

        Assert.assertNotNull(centerVideoBean.getLblPlantilla());
        Assert.assertEquals(lblPlantilla, centerVideoBean.getLblPlantilla());
    }

    @Test
    public void testGetImgPlantilla() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Crea mock object
        Image imgPlantilla = Mockito.mock(Image.class);
        // Se inyecta dependencia
        centerVideoBean.setImgPlantilla(imgPlantilla);

        Assert.assertNotNull(centerVideoBean.getImgPlantilla());
        Assert.assertEquals(imgPlantilla, centerVideoBean.getImgPlantilla());
    }

    @Test
    public void testGetExecution() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Crea mock object
        Execution execution = Mockito.mock(Execution.class);
        // Se inyecta dependencia
        centerVideoBean.setExecution(execution);

        Assert.assertNotNull(centerVideoBean.getExecution());
        Assert.assertEquals(execution, centerVideoBean.getExecution());
    }

    @Test
    public void testGetUserCredentialManager() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        // Crea mock object
        UserCredentialManager userCredentialMgr = Mockito.mock(UserCredentialManager.class);
        // Se inyecta dependencia
        centerVideoBean.setUserCredentialManager(userCredentialMgr);

        Assert.assertNotNull(centerVideoBean.getUserCredentialManager());
        Assert.assertEquals(userCredentialMgr, centerVideoBean.getUserCredentialManager());
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserCredentialManager2() {
        // Crea instancia de la clase a probar
        CenterVideoEvalBean centerVideoBean = new CenterVideoEvalBean();
        Assert.assertNotNull(centerVideoBean.getUserCredentialManager());
    }
}