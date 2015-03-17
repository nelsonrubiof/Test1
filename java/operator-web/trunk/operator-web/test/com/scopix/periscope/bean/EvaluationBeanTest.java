package com.scopix.periscope.bean;

import java.util.ArrayList;
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
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.East;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.North;
import org.zkoss.zul.West;

import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.interfaces.LeftMetricList;
import com.scopix.periscope.interfaces.NorthEvalHeader;
import com.scopix.periscope.manager.OperatorManager;
import com.scopix.periscope.model.Evidence;
import com.scopix.periscope.model.EvidenceProvider;
import com.scopix.periscope.model.Metric;
import com.scopix.periscope.model.Situation;

/**
 * Clase de pruebas de com.scopix.periscope.bean.EvaluationBean
 * 
 * @author Carlos
 */
@SuppressWarnings(value = { "unchecked" })
public class EvaluationBeanTest {

    public EvaluationBeanTest() {
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
     * Prepara la inicialización del bean y la pantalla de la parte superior
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 20/02/2013
     */
    @Test
    public void testPrepareNorthSide() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock objects
        North north = Mockito.mock(North.class);
        Borderlayout evalBorderLayout = Mockito.mock(Borderlayout.class);
        // Inyecta dependencia
        evaluationBean.setEvalBorderLayout(evalBorderLayout);
        // Define comportamiento
        Mockito.when(evalBorderLayout.getNorth()).thenReturn(north);
        // Invoca método a probar
        evaluationBean.prepareNorthSide();
    }

    /**
     * Prepara la inicialización del bean y la pantalla de la parte superior
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 20/02/2013
     */
    @Test
    public void testPrepareNorthSide2() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock objects
        North north = Mockito.mock(North.class);
        List<Component> children = Mockito.mock(List.class);
        Borderlayout evalBorderLayout = Mockito.mock(Borderlayout.class);
        // Inyecta dependencia
        evaluationBean.setEvalBorderLayout(evalBorderLayout);
        // Define comportamiento
        Mockito.when(evalBorderLayout.getNorth()).thenReturn(north);
        Mockito.when(north.getChildren()).thenReturn(children);
        Mockito.when(children.isEmpty()).thenReturn(false);
        // Invoca método a probar
        evaluationBean.prepareNorthSide();
    }

    /**
     * Prepara la inicialización del bean y la pantalla de la parte izquierda
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 20/02/2013
     */
    @Test
    public void testPrepareWestSide() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock objects
        West west = Mockito.mock(West.class);
        Borderlayout evalBorderLayout = Mockito.mock(Borderlayout.class);
        // Inyecta dependencia
        evaluationBean.setEvalBorderLayout(evalBorderLayout);
        // Define comportamiento
        Mockito.when(evalBorderLayout.getWest()).thenReturn(west);
        // Invoca método a probar
        evaluationBean.prepareWestSide();
        // Verifica ejecución
        Mockito.verify(west).appendChild(Mockito.any(Component.class));
    }

    /**
     * Prepara la inicialización del bean y la pantalla de la parte izquierda
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 20/02/2013
     */
    @Test
    public void testPrepareWestSide2() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock objects
        West west = Mockito.mock(West.class);
        List<Component> children = Mockito.mock(List.class);
        Borderlayout evalBorderLayout = Mockito.mock(Borderlayout.class);
        // Inyecta dependencia
        evaluationBean.setEvalBorderLayout(evalBorderLayout);
        // Define comportamiento
        Mockito.when(evalBorderLayout.getWest()).thenReturn(west);
        Mockito.when(west.getChildren()).thenReturn(children);
        Mockito.when(children.isEmpty()).thenReturn(false);
        // Invoca método a probar
        evaluationBean.prepareWestSide();
    }

    @Test
    public void testGetWestSide() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock object
        West west = Mockito.mock(West.class);
        // Se inyecta dependencia
        evaluationBean.setWestSide(west);

        Assert.assertNotNull(evaluationBean.getWestSide());
        Assert.assertEquals(west, evaluationBean.getWestSide());
    }

    @Test
    public void testGetLeftMetricBean() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock object
        LeftMetricBean leftMetricBean = Mockito.mock(LeftMetricBean.class);
        // Se inyecta dependencia
        evaluationBean.setLeftMetricBean(leftMetricBean);

        Assert.assertNotNull(evaluationBean.getLeftMetricBean());
        Assert.assertEquals(leftMetricBean, evaluationBean.getLeftMetricBean());
    }

    @Test
    public void testGetEvalBorderLayout() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock object
        Borderlayout borderLayout = Mockito.mock(Borderlayout.class);
        // Se inyecta dependencia
        evaluationBean.setEvalBorderLayout(borderLayout);

        Assert.assertNotNull(evaluationBean.getEvalBorderLayout());
        Assert.assertEquals(borderLayout, evaluationBean.getEvalBorderLayout());
    }

    @Test
    public void testGetOperatorManager() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock object
        OperatorManager opManager = Mockito.mock(OperatorManager.class);
        // Se inyecta dependencia
        evaluationBean.setOperatorManager(opManager);

        Assert.assertNotNull(evaluationBean.getOperatorManager());
        Assert.assertEquals(opManager, evaluationBean.getOperatorManager());
    }

    @Test
    public void testGetOperatorManager2() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        Assert.assertNotNull(evaluationBean.getOperatorManager());
    }

    @Test
    public void testGetMetricInterface() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock object
        LeftMetricList metricInterface = Mockito.mock(LeftMetricList.class);
        // Se inyecta dependencia
        evaluationBean.setMetricInterface(metricInterface);

        Assert.assertNotNull(evaluationBean.getMetricInterface());
        Assert.assertEquals(metricInterface, evaluationBean.getMetricInterface());
    }

    @Test
    public void testGetSituation() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock object
        Situation situation = Mockito.mock(Situation.class);
        // Se inyecta dependencia
        evaluationBean.setSituation2(situation);

        Assert.assertNotNull(evaluationBean.getSituation());
        Assert.assertEquals(situation, evaluationBean.getSituation());
    }

    @Test
    public void testSetSituation2() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock object
        Situation situation = Mockito.mock(Situation.class);
        // Se inyecta dependencia
        evaluationBean.setSituation2(situation);

        Assert.assertNotNull(evaluationBean.getSituation());
        Assert.assertEquals(situation, evaluationBean.getSituation());
    }

    /**
     * Método invocado una vez el bean de la parte izquierda (west) finaliza su construcción
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param bean beanHijo
     * @since 6.0
     * @date 20/02/2013
     */
    @Test
    public void testLeftComponentReady() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock object
        Metric metrica = new Metric();
        List<Metric> lstMetricas = new ArrayList<Metric>();
        lstMetricas.add(metrica);

        Situation situation = Mockito.mock(Situation.class);
        LeftMetricList bean = Mockito.mock(LeftMetricList.class);
        // Se inyecta dependencia
        evaluationBean.setSituation2(situation);
        // Define comportamiento
        Mockito.when(situation.getMetricas()).thenReturn(lstMetricas);
        Mockito.when(situation.isRejected()).thenReturn(true);
        Mockito.when(situation.getRejectedObservation()).thenReturn("Obs");
        // Invoca método a probar
        evaluationBean.leftComponentReady(bean);
        // Verifica ejecución
        Mockito.verify(bean).setMetricasModel(Mockito.any(ListModelList.class));
        Mockito.verify(bean).setQualityObservation(Matchers.anyString());
    }

    /**
     * Método invocado una vez el bean de la parte izquierda (west) finaliza su construcción
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param bean beanHijo
     * @since 6.0
     * @date 20/02/2013
     */
    @Test
    public void testLeftComponentReady2() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock object
        Metric metrica = new Metric();
        List<Metric> lstMetricas = new ArrayList<Metric>();
        lstMetricas.add(metrica);

        Situation situation = Mockito.mock(Situation.class);
        LeftMetricList bean = Mockito.mock(LeftMetricList.class);
        // Se inyecta dependencia
        evaluationBean.setSituation2(situation);
        // Define comportamiento
        Mockito.when(situation.getMetricas()).thenReturn(lstMetricas);
        Mockito.when(situation.isRejected()).thenReturn(false);
        // Invoca método a probar
        evaluationBean.leftComponentReady(bean);
    }

    /**
     * Método invocado una vez el bean de la parte superior (north) finaliza su construcción
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param bean beanHijo
     * @since 6.0
     * @date 20/02/2013
     */
    @Test(expected = NullPointerException.class)
    public void testNorthComponentReady() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock object
        Situation situation = Mockito.mock(Situation.class);
        NorthEvalHeader bean = Mockito.mock(NorthEvalHeader.class);

        List<Metric> lstMetricas = new ArrayList<Metric>();
        List<Evidence> lstEvidencias = new ArrayList<Evidence>();

        Evidence evidencia = new Evidence();
        EvidenceProvider evProvider = new EvidenceProvider();
        evProvider.setDescripcion("desc");
        evidencia.setEvidenceProvider(evProvider);
        lstEvidencias.add(evidencia);

        Metric metrica = new Metric();
        metrica.setEvidencias(lstEvidencias);
        lstMetricas.add(metrica);

        // Se inyecta dependencia
        evaluationBean.setSituation2(situation);
        // Se define comportamiento
        Mockito.when(situation.getStoreName()).thenReturn("store");
        Mockito.when(situation.getClient()).thenReturn("client");
        Mockito.when(situation.getMetricas()).thenReturn(lstMetricas);
        Mockito.when(situation.getArea()).thenReturn("area");
        Mockito.when(situation.getEvidenceDateTime()).thenReturn("2013-06-06 10:10:10");

        // Invoca método a probar
        evaluationBean.northComponentReady(bean);
    }

    @Test
    public void testGetNorthHeaderBean() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock object
        NorthHeaderBean northHeaderBean = Mockito.mock(NorthHeaderBean.class);
        // Se inyecta dependencia
        evaluationBean.setNorthHeaderBean(northHeaderBean);

        Assert.assertNotNull(evaluationBean.getNorthHeaderBean());
        Assert.assertEquals(northHeaderBean, evaluationBean.getNorthHeaderBean());
    }

    @Test
    public void testGetNorthSide() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock object
        North northSide = Mockito.mock(North.class);
        // Se inyecta dependencia
        evaluationBean.setNorthSide(northSide);

        Assert.assertNotNull(evaluationBean.getNorthSide());
        Assert.assertEquals(northSide, evaluationBean.getNorthSide());
    }

    @Test
    public void testGetHeaderInterface() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock object
        NorthEvalHeader northEvalHeader = Mockito.mock(NorthEvalHeader.class);
        // Se inyecta dependencia
        evaluationBean.setHeaderInterface(northEvalHeader);

        Assert.assertNotNull(evaluationBean.getHeaderInterface());
        Assert.assertEquals(northEvalHeader, evaluationBean.getHeaderInterface());
    }

    @Test
    public void testSetRightInclude() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock objects
        East east = Mockito.mock(East.class);
        Include rightInclude = Mockito.mock(Include.class);
        List<Component> children = Mockito.mock(List.class);
        Borderlayout evalBorderLayout = Mockito.mock(Borderlayout.class);
        // Inyecta dependencia
        evaluationBean.setEvalBorderLayout(evalBorderLayout);
        // Define comportamiento
        Mockito.when(evalBorderLayout.getEast()).thenReturn(east);
        Mockito.when(east.getChildren()).thenReturn(children);
        Mockito.when(children.isEmpty()).thenReturn(false);
        // Invoca método a probar
        evaluationBean.setRightInclude(rightInclude);
        // Verifica ejecución
        Mockito.verify(east).appendChild(Mockito.any(Component.class));
    }

    @Test
    public void testSetRightInclude2() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock objects
        East east = Mockito.mock(East.class);
        Include rightInclude = Mockito.mock(Include.class);
        List<Component> children = Mockito.mock(List.class);
        Borderlayout evalBorderLayout = Mockito.mock(Borderlayout.class);
        // Inyecta dependencia
        evaluationBean.setEvalBorderLayout(evalBorderLayout);
        // Define comportamiento
        Mockito.when(evalBorderLayout.getEast()).thenReturn(east);
        Mockito.when(east.getChildren()).thenReturn(children);
        Mockito.when(children.isEmpty()).thenReturn(true);
        // Invoca método a probar
        evaluationBean.setRightInclude(rightInclude);
        // Verifica ejecución
        Mockito.verify(east).appendChild(Mockito.any(Component.class));
    }

    @Test
    public void testSetCenterInclude() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock objects
        Center center = Mockito.mock(Center.class);
        Include centerInclude = Mockito.mock(Include.class);
        List<Component> children = Mockito.mock(List.class);
        Borderlayout evalBorderLayout = Mockito.mock(Borderlayout.class);
        // Inyecta dependencia
        evaluationBean.setEvalBorderLayout(evalBorderLayout);
        // Define comportamiento
        Mockito.when(evalBorderLayout.getCenter()).thenReturn(center);
        Mockito.when(center.getChildren()).thenReturn(children);
        Mockito.when(children.isEmpty()).thenReturn(false);
        // Invoca método a probar
        evaluationBean.setCenterInclude(centerInclude);
        // Verifica ejecución
        Mockito.verify(center).appendChild(Mockito.any(Component.class));
    }

    @Test
    public void testSetCenterInclude2() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock objects
        Center center = Mockito.mock(Center.class);
        Include centerInclude = Mockito.mock(Include.class);
        List<Component> children = Mockito.mock(List.class);
        Borderlayout evalBorderLayout = Mockito.mock(Borderlayout.class);
        // Inyecta dependencia
        evaluationBean.setEvalBorderLayout(evalBorderLayout);
        // Define comportamiento
        Mockito.when(evalBorderLayout.getCenter()).thenReturn(center);
        Mockito.when(center.getChildren()).thenReturn(children);
        Mockito.when(children.isEmpty()).thenReturn(true);
        // Invoca método a probar
        evaluationBean.setCenterInclude(centerInclude);
        // Verifica ejecución
        Mockito.verify(center).appendChild(Mockito.any(Component.class));
    }

    @Test
    public void testGetUserCredentialManager() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock object
        UserCredentialManager userCredentialMgr = Mockito.mock(UserCredentialManager.class);
        // Se inyecta dependencia
        evaluationBean.setUserCredentialManager(userCredentialMgr);

        Assert.assertNotNull(evaluationBean.getUserCredentialManager());
        Assert.assertEquals(userCredentialMgr, evaluationBean.getUserCredentialManager());
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserCredentialManager2() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        Assert.assertNotNull(evaluationBean.getUserCredentialManager());
    }

    @Test
    public void testGetExecution() {
        // Crea instancia de la clase a probar
        EvaluationBean evaluationBean = new EvaluationBean();
        // Crea mock object
        Execution execution = Mockito.mock(Execution.class);
        // Se inyecta dependencia
        evaluationBean.setExecution(execution);

        Assert.assertNotNull(evaluationBean.getExecution());
        Assert.assertEquals(execution, evaluationBean.getExecution());
    }
}