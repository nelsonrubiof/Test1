package com.scopix.periscope.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;

import com.scopix.periscope.common.ShowMessage;
import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.enums.EnumEvaluationType;
import com.scopix.periscope.enums.EnumEvidenceType;
import com.scopix.periscope.interfaces.GlobalEvaluation;
import com.scopix.periscope.interfaces.LeftMetricList;
import com.scopix.periscope.interfaces.RightMetricDetail;
import com.scopix.periscope.manager.OperatorManager;
import com.scopix.periscope.model.Client;
import com.scopix.periscope.model.Evidence;
import com.scopix.periscope.model.Metric;
import com.scopix.periscope.model.Situation;
import com.scopix.periscope.model.UserEvaluation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * Clase encargada del manejo de la lista de métricas en la parte izquierda de la pantalla de evaluación
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SuppressWarnings(value = {"rawtypes", "unchecked"})
public class LeftMetricBean extends GenericForwardComposer implements Serializable, LeftMetricList {

    private String login;
    private Session mySession;
    private Label lblNoEvaluar;
    private Integer retryNumber;
    private Situation situation;
    private boolean allCounting;
    private Label lblPlayerTime;
    private String textoMetrica;
    private boolean allEvaluated;
    private int currentIndex = 0;
    private Metric currentMetric;
    private Button btnEnviarEval;
    private Textbox txtObsCalidad;
    private Label lblEvalExitosas;
    private Label lblEvalFallidas;
    private Listbox metricasListBox;
    private ShowMessage showMessage;
    private Label lblEvalPendientes;
    private Auxheader auxHeadMetrica;
    private String highlightrow = "0";
    private Label lblCurrentEvidenceType;
    private OperatorManager operatorManager;
    private GlobalEvaluation evaluacionBean;
    private PopLeftMetricBean popLeftMetricBean;
    private RightMetricDetail rightMetricDetail;
    private UserCredentialManager userCredentialManager;
    private ListModelList metricasModel = new ListModelList();
    private static Logger log = Logger.getLogger(LeftMetricBean.class);
    private static final long serialVersionUID = -2018234619619100033L;

    @Override
    public void doAfterCompose(Component comp) {
        login = ((String) getMySession().getAttribute("LOGIN"));
        log.info("start - " + login);
        try {
            super.doAfterCompose(comp);

            // Si el usuario no se encuentra autenticado, lo redirige a la pantalla de login
            if (!getUserCredentialManager().isAuthenticated()) {
                getExecution().sendRedirect("login.zul");
            } else {
                retryNumber = (Integer) getMySession().getAttribute("RETRY_NUMBER");
                log.debug("numero de intentos: " + retryNumber);

                setPopLeftMetricBean((PopLeftMetricBean) getMySession().getAttribute("popLeftMetricBean"));
                getPopLeftMetricBean().setLeftMetricBean(this);

                getLblNoEvaluar().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblPlayerTime().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblCurrentEvidenceType().setAttribute("org.zkoss.zk.ui.updateByClient", true);

                // Notifica al bean "padre" que finalizó su construcción
                getGlobalEvaluation().leftComponentReady(this);
                setTextoMetrica(Labels.getLabel("eval.metrica"));

                // Valida si todas las métricas de la situación son de conteo
                setAllCounting(validateAllCounting());

                validateAllNumberInput();

                // actualiza nros de métricas
                for (int i = 0; i < getMetricasModel().size(); i++) {
                    Metric metrica = (Metric) getMetricasModel().get(i);
                    metrica.setNumMetrica(String.valueOf(i));
                }

                currentMetric = (Metric) getMetricasModel().get(0);

                if (getAuxHeadMetrica() != null) {
                    getAuxHeadMetrica().setLabel(getTextoMetrica() + ": " + currentMetric.getName());
                }

                prepareRightMetric(getCurrentMetric().getType());

                setRowWhiteBg(); // Pone fondo blanco a todas las filas de la lista
                Clients.evalJavaScript("highLightRow(0);");

                updateEvaluationLabels();
            }
        } catch (Exception e) {
            log.error(e.getMessage() + " - " + login, e);
        }
        log.info("end - " + login);
    }

    private void updateEvaluationLabels() {
        log.info("start - " + login);
        Long sessionId = (Long) getMySession().getAttribute("SESSION_ID");

        UserEvaluation userEvaluation = getOperatorManager().getHmUsersEvaluations().get(sessionId);
        if (userEvaluation != null) {
            String fail = String.valueOf(userEvaluation.getFail());
            String success = String.valueOf(userEvaluation.getSuccess());
            String pending = String.valueOf(userEvaluation.getPending());

            log.debug("sessionId: [" + sessionId + "], success: [" + success + "], fail: [" + fail + "], pending: [" + pending
                    + "] - " + login);

            getLblEvalFallidas().setValue(fail);
            getLblEvalExitosas().setValue(success);
            getLblEvalPendientes().setValue(pending);
        }
        log.info("end - " + login);
    }

    /**
     * Invocado después de renderización de lista de métricas, para validar métricas que estén previamente evaluadas y definir el
     * color de la fila
     *
     * @param event
     */
    public void onAfterRenderListaMetricas(ForwardEvent event) {
        log.info("start - " + login);
        for (int i = 0; i < getMetricasModel().size(); i++) {
            Metric metrica = (Metric) getMetricasModel().get(i);

            if (metrica.isEvaluada() && metrica.isAutomatic()) {
                setRowFontColor(i, "blue");
            } else if (metrica.isEvaluada() && !metrica.isAutomatic()) {
                setRowFontColor(i, "green");
            }
        }

        log.debug("isAllCounting(): [" + isAllCounting() + "] - " + login);
        if (isAllCounting()) {
            Clients.evalJavaScript("setCamMetricsBgColor(); highLightRow(" + highlightrow + ");");
        }
        log.info("end - " + login);
    }

    /**
     * Valida si todas las métricas de la situación son de conteo
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 08/04/2013
     * @return boolean true si todas las métricas son de conteo
     */
    public boolean validateAllCounting() {
        log.info("start - " + login);
        for (int i = 0; i < getMetricasModel().size(); i++) {
            Metric metrica = (Metric) getMetricasModel().get(i);

            if (!metrica.getType().equalsIgnoreCase(EnumEvaluationType.COUNTING.toString())) {
                log.debug("Todas las metricas NO son de conteo - " + login);
                return false;
            }
        }
        log.debug("Todas las metricas son de conteo - " + login);
        // Si llega hasta acá, es porque todas las métricas son de conteo (no multicámara)
        sortOrderCriteria();

        log.info("end - " + login);
        return true;
    }

    public boolean validateAllNumberInput() {
        log.info("start - " + login);
        for (int i = 0; i < getMetricasModel().size(); i++) {
            Metric metrica = (Metric) getMetricasModel().get(i);

            if (!metrica.getType().equalsIgnoreCase(EnumEvaluationType.NUMBER_INPUT.toString())) {
                log.debug("Todas las metricas NO son de number input - " + login);
                return false;
            }
        }
        log.debug("Todas las metricas son de number input - " + login);
        // Si llega hasta acá, es porque todas las métricas son de number input (no multicámara)
        sortOrderCriteria();

        log.info("end - " + login);
        return true;
    }

    /**
     * Procesa y organiza criterios de ordenamiento de métricas
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 11/06/2013
     */
    protected void sortOrderCriteria() {
        log.info("start - " + login);
        List<Integer> lstSortNumbers = processSortNumbers();
        // Elimina los ids repetidos
        Iterator iterador = lstSortNumbers.iterator();

        Object obj;
        List<Integer> sortNumbers = new ArrayList<Integer>();
        while (iterador.hasNext()) {
            obj = iterador.next();
            if (!sortNumbers.contains(obj)) {
                sortNumbers.add((Integer) obj);
            }
        }
        // Crea modelo de métricas de acuerdo a los números ordenados (viewOrder/idCamara)
        setMetricasModel(setMetricSortedModel(sortNumbers));
        log.info("end - " + login);
    }

    /**
     * Procesa y organiza criterios de ordenamiento de métricas
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 11/06/2013
     * @return List<Integer> criterios ordenados
     */
    protected List<Integer> processSortNumbers() {
        log.info("start - " + login);
        List<Integer> lstSortNumbers = new ArrayList<Integer>();
        for (int i = 0; i < getMetricasModel().size(); i++) {
            Metric metrica = (Metric) getMetricasModel().get(i);

            Integer viewOrder = metrica.getEvidencias().get(0).getEvidenceProvider().getViewOrder();
            Integer idCamara = metrica.getEvidencias().get(0).getEvidenceProvider().getId();

            if (viewOrder != null) {
                lstSortNumbers.add(viewOrder);
            } else {
                lstSortNumbers.add(idCamara);
            }
        }
        // Organiza los ids de cámaras
        Collections.sort(lstSortNumbers);
        log.info("end - " + login);

        return lstSortNumbers;
    }

    /**
     * Crea modelo de métricas de acuerdo a los números ordenados (viewOrder/idCamara)
     *
     * @author carlos polo
     * @version 1.0.0
     * @param sortNumbers números de ordenamiento (viewOrder/idCamara)
     * @since 6.0
     * @date 21/02/2013
     * @return ListModelList modelo de métricas ordenado
     */
    public ListModelList setMetricSortedModel(List<Integer> sortNumbers) {
        log.info("start - " + login);
        int count = 0;
        boolean toAdd = false;
        ListModelList modelEvaluadas = new ListModelList();
        ListModelList modelNoEvaluadas = new ListModelList();

        for (Integer sortNumber : sortNumbers) {
            for (int j = 0; j < getMetricasModel().size(); j++) {
                Metric metrica = (Metric) getMetricasModel().get(j);
                Integer idCamara2 = metrica.getEvidencias().get(0).getEvidenceProvider().getId();
                Integer viewOrder2 = metrica.getEvidencias().get(0).getEvidenceProvider().getViewOrder();

                if (viewOrder2 != null && sortNumber.equals(viewOrder2)) {
                    metrica.setNumMetrica(String.valueOf(count));
                    toAdd = true;
                    count++;
                } else if (sortNumber.equals(idCamara2)) {
                    metrica.setNumMetrica(String.valueOf(count));
                    toAdd = true;
                    count++;
                }

                if (metrica.isEvaluada() && toAdd) {
                    metrica.setAutomatic(true);
                    modelEvaluadas.add(metrica);
                } else if (!metrica.isEvaluada() && toAdd) {
                    // la métrica NO está previamente evaluada por modalidad automática
                    modelNoEvaluadas.add(metrica);
                }
                toAdd = false;
            }
        }
        if (!modelEvaluadas.isEmpty()) {
            modelNoEvaluadas.addAll(modelEvaluadas);
        }

        log.info("end - " + login);
        return modelNoEvaluadas;
    }

    /**
     * Define página a ser mostrada en la parte derecha para crear una instancia del correspondiente bean dependiendo del tipo de
     * métrica
     *
     * @author carlos polo
     * @version 1.0.0
     * @param metricType tipo de métrica
     * @since 6.0
     * @date 21/02/2013
     */
    public void prepareRightMetric(String metricType) {
        log.info("start - " + login);
        Include rightInclude = new Include();
        rightMetricDetail = createRightMetricBean(metricType);

        if (metricType.equalsIgnoreCase(EnumEvaluationType.COUNTING.toString())) {
            rightInclude.setSrc("rightCountMetric.zul");
        } else if (metricType.equalsIgnoreCase(EnumEvaluationType.MEASURE_TIME.toString())) {
            rightInclude.setSrc("rightTimeMetric.zul");
        } else if (metricType.equalsIgnoreCase(EnumEvaluationType.YES_NO.toString())) {
            rightInclude.setSrc("rightYesNoMetric.zul");
        } else if (metricType.equalsIgnoreCase(EnumEvaluationType.NUMBER_INPUT.toString())) {
            rightInclude.setSrc("rightNumberInputMetric.zul");
        }
        prepareRightMetricDetail(rightInclude);
        log.info("end - " + login);
    }

    /**
     * Crea una instancia de bean de la parte derecha de la pantalla dependiendo del tipo de métrica
     *
     * @author carlos polo
     * @version 1.0.0
     * @param metricType tipo de métrica
     * @since 6.0
     * @date 21/02/2013
     * @return RightMetricDetail instancia de la parte derecha
     */
    public RightMetricDetail createRightMetricBean(String metricType) {
        log.info("start - " + login);
        RightMetricDetail rightMetricDetail = null;

        if (metricType.equalsIgnoreCase(EnumEvaluationType.COUNTING.toString())) {
            rightMetricDetail = new RightCountMetricBean();
        } else if (metricType.equalsIgnoreCase(EnumEvaluationType.MEASURE_TIME.toString())) {
            rightMetricDetail = new RightTimeMetricBean();
        } else if (metricType.equalsIgnoreCase(EnumEvaluationType.YES_NO.toString())) {
            rightMetricDetail = new RightYesNoMetricBean();
        } else if (metricType.equalsIgnoreCase(EnumEvaluationType.NUMBER_INPUT.toString())) {
            rightMetricDetail = new RightNumberInputMetricBean();
        }
        log.info("end - " + login);
        return rightMetricDetail;
    }

    /**
     * Prepara la inicialización del bean y la pantalla de la parte derecha
     *
     * @author carlos polo
     * @version 1.0.0
     * @param rightInclude página a incluir en la parte derecha de la pantalla
     * @since 6.0
     * @date 20/02/2013
     */
    public void prepareRightMetricDetail(Include rightInclude) {
        log.info("start - " + login);
        String playerPosition = getLblPlayerTime().getValue();
        if (!playerPosition.equals("-1")) { // Es un cambio de video
            getRightMetricDetail().setPlayerPosition(playerPosition);
        }
        getRightMetricDetail().setAllCounting(isAllCounting());
        getRightMetricDetail().setMetric(getCurrentMetric());
        // Vincula a nivel de eventos la página con la instancia del bean
        getRightMetricDetail().bindComponentAndParent(rightInclude, this);
        // Adiciona en la parte derecha del borderlayout la página a incluir
        getGlobalEvaluation().setRightInclude(rightInclude);
        getRightMetricDetail().setAllEvaluated(allEvaluated);
        log.info("end - " + login);
    }

    /**
     * Actualiza auxheader con el nombre de la métrica seleccionada y actualiza la parte derecha de la pantalla con las opciones
     * correspondientes
     *
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 21/02/2013
     */
    @Override
    public void onClickFilaMetrica(ForwardEvent event) {
        log.info("start - " + login);

        Integer selectedIndex = -1;
        if ("singleNoEval".equalsIgnoreCase(event.getName())) {
            Integer numMetrica = Integer.parseInt(getCurrentMetric().getNumMetrica());
            selectedIndex = numMetrica + 1;

        } else if ("onClickCero".equalsIgnoreCase(event.getName())) {
            Integer numMetrica = Integer.parseInt(getCurrentMetric().getNumMetrica());
            selectedIndex = numMetrica + 1;

        } else {
            Listitem selectedRow = (Listitem) event.getOrigin().getTarget();
            Listcell lstCell = (Listcell) selectedRow.getChildren().get(0);
            Label lblNomMetrica = (Label) lstCell.getFirstChild();
            selectedIndex = Integer.valueOf(lblNomMetrica.getTooltip());
        }

        getRightMetricDetail().saveCurrentEvidenceEvalData(getRightMetricDetail().getCurrentCameraId(), false);
        getRightMetricDetail().getShapesData();

        List lstValues = getRightMetricDetail().setMetricEvalValues();
        boolean evaluada = (Boolean) lstValues.get(0);

        if (getRightMetricDetail().validateCamerasEval(evaluada)) {
            processClickFilaMetrica(selectedIndex);
        } else {
            setNextMetric(getCurrentIndex());
        }
        log.info("end - " + login);
    }

    /**
     * Valida evaluación de la métrica actual al hacer click en una fila
     *
     * @author carlos polo
     * @version 1.0.0
     * @param selectedIndex índice seleccionado
     * @since 6.0
     * @date 21/02/2013
     */
    protected void processClickFilaMetrica(Integer selectedIndex) {
        log.info("start - " + login);
        if (selectedIndex != getCurrentIndex()) {
            // La fila seleccionada es diferente a la fila actual
            validateOneEvaluation(selectedIndex);
        } else {
            // Es la misma fila
            Metric metricaActual = (Metric) getMetricasModel().get(getCurrentIndex());
            // La métrica actual está evaluada o se determinó que no se puede evaluar
            if (metricaActual.isEvaluada() || metricaActual.isCantEval()) {
                // Pone en color verde la métrica actual
                setRowFontColor(selectedIndex, "green");
            }
        }
        log.info("end - " + login);
    }

    /**
     * Valida si la métrica fue evaluada
     *
     * @author carlos polo
     * @version 1.0.0
     * @param selectedIndex índice seleccionado
     * @since 6.0
     * @date 21/02/2013
     */
    @Override
    public void validateMetricEval(int selectedIndex) {
        log.info("start - " + login);
        Integer metricasSize = getMetricasModel().size();

        if (selectedIndex == metricasSize) { // Era la última métrica
            // Ejecuta lógica de envío de la evaluación
            executeSendEvaluation(selectedIndex);
        } else if (validateAllEvalNoNotify()) { // No era la última métrica, valida si están todas las métricas evaluadas
            // Ejecuta lógica de envío de la evaluación
            executeSendEvaluation(selectedIndex);
        } else {
            // Pasa a la siguiente métrica
            currentMetric = (Metric) getMetricasModel().get(selectedIndex);
            // La fila seleccionada es diferente a la fila actual
            if (selectedIndex != getCurrentIndex()) {
                validateOneEvaluation(selectedIndex);
            }
        }
        log.info("end - " + login);
    }

    /**
     * Envía la evaluación siempre y cuando todas las métricas estén evaluadas
     *
     * @author carlos polo
     * @version 1.0.0
     * @param selectedIndex índice seleccionado
     * @since 6.0
     * @date 21/02/2013
     */
    protected void executeSendEvaluation(int selectedIndex) {
        log.info("start - " + login);
        if (validateAllEvaluations()) {
            setRowFontColor(selectedIndex - 1, "green");

            Clients.evalJavaScript("hideAllCurrentCircles(); hideAllCurrentSquares();");
            try {
                sendEvaluation(false, false);
            } catch (ScopixException e) {
                log.error(e.getMessage(), e);
            }
        }
        log.info("end - " + login);
    }

    /**
     * Valida si existe información de marcas para eliminar
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 21/02/2013
     */
    protected void validateToDeleteAllShapes() {
        log.info("start - " + login);
        Integer circlesCount = (Integer) getMySession().getAttribute("CIRCLES_COUNT");
        Integer squaresCount = (Integer) getMySession().getAttribute("SQUARES_COUNT");

        Integer yesSquaresCount = (Integer) getMySession().getAttribute("YES_SQUARES_COUNT");
        Integer noSquaresCount = (Integer) getMySession().getAttribute("NO_SQUARES_COUNT");

        log.debug("circlesCount: " + circlesCount + ", squaresCount: " + squaresCount + ", " + "yesSquaresCount: "
                + yesSquaresCount + ", noSquaresCount: " + noSquaresCount + " - " + login);
        if (circlesCount != null && squaresCount != null) {
            log.debug("Se invoca limpieza de marcas, circlesCount: " + circlesCount + ", squaresCount: " + squaresCount + " - "
                    + login);

            Clients.evalJavaScript("currentCirclesCount=" + circlesCount + "; currentSquaresCount=" + squaresCount + "; "
                    + "validateToDeleteAllShapes = 'S'; deleteAllCurrentShapes('S','S','N'); metricCount=0;");

            getMySession().removeAttribute("CIRCLES_COUNT");
            getMySession().removeAttribute("SQUARES_COUNT");
        }

        if (yesSquaresCount != null) {
            log.debug("Se invoca limpieza de marcas YES, yesSquaresCount: " + yesSquaresCount + " - " + login);
            Clients.evalJavaScript("yesSquaresCount=" + yesSquaresCount + "; deleteMarks('S')");
            getMySession().removeAttribute("YES_SQUARES_COUNT");
        }

        if (noSquaresCount != null) {
            log.debug("Se invoca limpieza de marcas NO, noSquaresCount: " + noSquaresCount + " - " + login);
            Clients.evalJavaScript("noSquaresCount=" + noSquaresCount + "; deleteMarks('S')");
            getMySession().removeAttribute("NO_SQUARES_COUNT");
        }
        log.info("end - " + login);
    }

    /**
     * Valida si todas las métricas están evaluadas
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 21/02/2013
     * @return boolean true si todas las métricas están evaluadas
     */
    @Override
    public boolean validateAllEvaluations() {
        log.info("start - " + login);
        boolean isOk = true;

        Metric metricaActual = (Metric) getMetricasModel().get(getCurrentIndex());
        int numMetrica = Integer.parseInt(metricaActual.getNumMetrica());
        // Pone en color verde la métrica recién evaluada
        setRowFontColor(numMetrica, "green");

        for (int i = 0; i < getMetricasModel().getSize(); i++) {
            Metric metrica = (Metric) getMetricasModel().get(i);
            // La métrica NO está evaluada
            if (!metrica.isEvaluada() && !metrica.isCantEval()) {
                // NO se estableció que no se puede evaluar
                isOk = false;
                // No está evaluada ni tampoco se estableció que no se puede evaluar
                String titulo = Labels.getLabel("eval.validarEvalTitle");
                String mensaje = Labels.getLabel("eval.validarEvalText2");
                // Ej: Por favor evalúe la métrica 1-Xxxxxx
                mensaje = mensaje + " " + (i + 1) + ": " + "\"" + metrica.getName() + "\"";

                getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.EXCLAMATION);
                break;
            }
        }
        log.info("end, todasEvaluadas: " + isOk + " - " + login);
        return isOk;
    }

    /**
     * Valida si se completaron las evaluaciones y obtiene la siguiente situación
     *
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 02/04/2013
     */
    public void onClickEnviarEvaluaciones(ForwardEvent event) {
        log.info("start - " + login);
        List lstValues = getRightMetricDetail().setMetricEvalValues();
        boolean evaluada = (Boolean) lstValues.get(0);
        String valorMetrica = (String) lstValues.get(1);

        updateLeftMetric(valorMetrica, evaluada);

        if (validateAllEvaluations()) { // Todas las métricas se encuentran evaluadas
            log.debug("Todas las metricas se encuentran evaluadas - " + login);
            getRightMetricDetail().saveCurrentEvidenceEvalData(getRightMetricDetail().getCurrentCameraId(), false);
            getRightMetricDetail().getShapesData();
            Clients.evalJavaScript("hideAllCurrentCircles(); hideAllCurrentSquares();");
            try {
                sendEvaluation(false, false);
            } catch (ScopixException e) {
                log.error(e.getMessage(), e);
            }
        }
        log.info("end - " + login);
    }

    /**
     * Actualiza la métrica en la parte izquierda de la pantalla
     *
     * @author carlos polo
     * @version 1.0.0
     * @param valorMetrica valor por actualizar
     * @param evaluada indica si la métrica está evaluada o no
     * @since 6.0
     * @date 02/04/2013
     */
    public void updateLeftMetric(String valorMetrica, boolean evaluada) {
        log.info("start - " + login);
        Date fechaActual = new Date();
        // Actualiza métrica en listado izquierdo
        Integer numMetrica = Integer.valueOf(getRightMetricDetail().getMetric().getNumMetrica());
        Metric metrica = (Metric) getMetricasModel().get(numMetrica);
        metrica.setEvaluada(evaluada);
        metrica.setDescription(valorMetrica);

        HashMap<Integer, String> tiempoMarcas = metrica.getTiempoMarcas();
        if (tiempoMarcas != null) {
            if (getRightMetricDetail().getCenterEvidence().getLblTiempoMarcas() != null) {
                String marksTime = getRightMetricDetail().getCenterEvidence().getLblTiempoMarcas().getValue();

                tiempoMarcas.put(getRightMetricDetail().getCurrentCameraId(), marksTime);
                metrica.setTiempoMarcas(tiempoMarcas);
            }
        }

        log.debug("metricId: " + metrica.getMetricId() + ", setting metric endEvaluationTime: " + fechaActual + " - " + login);
        metrica.setEndEvaluationTime(fechaActual);

        getMetricasModel().set(numMetrica, metrica);
        log.info("end - " + login);
    }

    /**
     * Valida la evaluación de la métrica especificada
     *
     * @author carlos polo
     * @version 1.0.0
     * @param selectedIndex índice seleccionado
     * @since 6.0
     * @date 21/02/2013
     */
    public void validateOneEvaluation(int selectedIndex) {
        log.info("start - " + login);
        Date fechaActual = new Date();
        Metric metricaActual = (Metric) getMetricasModel().get(getCurrentIndex());
        int numMetrica = Integer.parseInt(metricaActual.getNumMetrica());

        // La métrica actual está evaluada o se determinó que no se puede evaluar
        if (metricaActual.isEvaluada() || metricaActual.isCantEval()) {
            // Pone en color verde la métrica recién evaluada
            setRowFontColor(numMetrica, "green");
            // Establece fecha final de la métrica actual y actualiza el modelo
            log.debug("metricId: " + metricaActual.getMetricId() + ", setting metric endEvaluationTime: " + fechaActual + " - "
                    + login);
            metricaActual.setEndEvaluationTime(fechaActual);
            getMetricasModel().set(numMetrica, metricaActual);

            setNextMetric(selectedIndex); // Pasa a la siguiente métrica
        } else {
            Metric nextMetric = (Metric) getMetricasModel().get(selectedIndex); // Obtiene la siguiente métrica
            Integer nextNumMetrica = Integer.parseInt(nextMetric.getNumMetrica());
            if (nextMetric.isEvaluada() || nextMetric.isCantEval()) {
                // La siguiente métrica está evaluada o se definió que no se puede evaluar
                // Pone en color verde la siguiente métrica
                setRowFontColor(nextNumMetrica, "green");
            }
            // La métrica actual no está evaluada ni tampoco se definió como "no se puede evaluar"
            // Permanece en la fila de la métrica actual y muestra el correspondiente mensaje de validación
            setRowFontColor(numMetrica, "red");
            setCurrentRow(metricaActual);
        }
        log.info("end - " + login);
    }

    /**
     * Establece la métrica actual
     *
     * @author carlos polo
     * @version 1.0.0
     * @param metricaActual métrica actual
     * @since 6.0
     * @date 21/02/2013
     */
    public void setCurrentRow(Metric metricaActual) {
        log.info("start - " + login);
        setCurrentMetric(metricaActual);
        // Pone fondo blanco a todas las filas de la lista
        setRowWhiteBg();
        // Resalta la fila actual
        highlightrow = metricaActual.getNumMetrica();
        Clients.evalJavaScript("highLightRow(" + highlightrow + ");");

        String titulo = Labels.getLabel("eval.validarEvalTitle");
        String mensaje = Labels.getLabel("eval.validarEvalText"); // Por favor evalúe la métrica actual
        getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.EXCLAMATION);
        log.info("end - " + login);
    }

    /**
     * Establece la siguiente métrica por evaluar
     *
     * @author carlos polo
     * @version 1.0.0
     * @param selectedIndex
     * @since 6.0
     * @date 21/02/2013
     */
    protected void setNextMetric(int selectedIndex) {
        log.info("start, selectedIndex: " + selectedIndex + " - " + login);
        validateToDeleteAllShapes();
        // Obtiene la siguiente métrica
        currentMetric = (Metric) getMetricasModel().get(selectedIndex);
        // Pone fondo blanco a todas las filas de la lista
        setRowWhiteBg();
        // Resalta la fila actual
        highlightrow = String.valueOf(selectedIndex);
        Clients.evalJavaScript("highLightRow(" + highlightrow + ");");

        if (getLblCurrentEvidenceType().getValue().equals(EnumEvidenceType.VIDEO.toString())) { // video
            Clients.evalJavaScript("if(typeof jwplayer() != 'undefined') { "
                    + "jwplayer().stop(); } clearTimeout(timeoutVar); clearInterval(timeoutVar2);");
        }

        Integer numMetrica = Integer.parseInt(getCurrentMetric().getNumMetrica());
        if (getCurrentMetric().isEvaluada() || getCurrentMetric().isCantEval()) {
            // La siguiente métrica está evaluada o se definió que no se puede evaluar
            // Pone en color verde la siguiente métrica
            setRowFontColor(numMetrica, "green");
        }

        setCurrentIndex(selectedIndex);
        String nomMetrica = getCurrentMetric().getName();
        String metricType = getCurrentMetric().getType();

        getAuxHeadMetrica().setLabel(getTextoMetrica() + ": " + nomMetrica);
        prepareRightMetric(metricType);
        log.info("end - " + login);
    }

    /**
     * Actualiza el valor de la métrica
     *
     * @author carlos polo
     * @version 1.0.0
     * @param value valor para actualizar en la métrica
     * @since 6.0
     * @date 21/02/2013
     */
    @Override
    public void updateMetricValue(String value) {
        log.info("start - " + login);
        String noDefinido = Labels.getLabel("eval.noDefinido");

        for (int i = 0; i < getMetricasModel().size(); i++) {
            Metric metrica = (Metric) getMetricasModel().get(i);
            if (metrica.equals(getCurrentMetric())) {
                if (value.equalsIgnoreCase(noDefinido)) {
                    metrica.setEvaluada(false);
                    metrica.setCantEval(false);
                    metrica.setCantEvalObs(null);
                    setRowFontColor(i, "red");
                } else {
                    metrica.setCantEval(false);
                    metrica.setEvaluada(true);
                    metrica.setCantEvalObs(null);
                    setRowFontColor(i, "green");
                }
                // indica que la evaluación de la métrica fue cambiada
                metrica.setChanged(true);
                metrica.setDescription(value);
                getMetricasModel().set(i, metrica);
                break;
            }
        }
        // Valida si todas las métricas han sido evaluadas, no notifica solo retorna un boolean
        if (validateAllEvalNoNotify()) {
            getBtnEnviarEval().setDisabled(false); // Habilita el botón para enviar y continuar las evaluaciones
            getRightMetricDetail().getBtnEnviarFinalizar().setDisabled(false);
        } else {
            getBtnEnviarEval().setDisabled(true);
            getRightMetricDetail().getBtnEnviarFinalizar().setDisabled(true);
        }
        log.info("end - " + login);
    }

    /**
     * Valida si todas las métricas han sido evaluadas, no notifica solo retorna un boolean
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 02/04/2013
     * @return boolean true si todas las métricas están evaluadas
     */
    public boolean validateAllEvalNoNotify() {
        log.info("start - " + login);
        for (int i = 0; i < getMetricasModel().size(); i++) {
            Metric metrica = (Metric) getMetricasModel().get(i);
            // La métrica NO está evaluada
            if (!metrica.isEvaluada() && !metrica.isCantEval()) {
                // NO se estableció que no se puede evaluar
                allEvaluated = false;
                return false;
            }
        }
        allEvaluated = true;
        log.info("end, allEvaluated: " + allEvaluated + " - " + login);
        return true;
    }

    /**
     * Guarda motivo al establecer que no se puede evaluar la métrica
     *
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 21/02/2013
     */
    public void onClickNoEvalGuardar(ForwardEvent event) {
        log.info("start - " + login);
        Radio selectedRadio = getPopLeftMetricBean().getMyRadioGroup().getSelectedItem();
        String noEvalType = getLblNoEvaluar().getValue();
        String cantEvalObs = selectedRadio.getLabel();
        Date fechaActual = new Date();

        log.debug("NoEvalType: " + noEvalType + " - " + login);
        if (noEvalType.equals("NO_EVAL")) {
            // Procesa actualización de métrica que no se pudo evaluar
            processNoEvalGuardar(cantEvalObs, fechaActual);

        } else if (noEvalType.equals("NO_EVAL_ALL")) {
            // Procesa actualización de todas las métricas que no se pudieron evaluar
            processNoEvalAllGuardar(cantEvalObs, fechaActual);

            if (validateAllEvalNoNotify()) {
                setAllEvaluated(true);
                getBtnEnviarEval().setDisabled(false); // Habilita el botón para enviar y continuar las evaluaciones
                getRightMetricDetail().getBtnEnviarFinalizar().setDisabled(false);
            }
        }
        getRightMetricDetail().getLblValorMetrica().setValue(cantEvalObs);

        if (noEvalType.equals("NO_EVAL")) {
            Integer numMetrica = Integer.parseInt(getCurrentMetric().getNumMetrica());
            int selectedIndex = numMetrica + 1;

            if (selectedIndex < getMetricasModel().getSize()) {
                ForwardEvent event2 = new ForwardEvent("singleNoEval", null, null);
                onClickFilaMetrica(event2);
            }
        }
        log.info("end - " + login);
    }

    /**
     * Procesa actualización de métrica que no se pudo evaluar
     *
     * @author carlos polo
     * @version 1.0.0
     * @param cantEvalObs motivo por el cual no se pudo evaluar
     * @param fechaActual fecha actual
     * @since 6.0
     * @date 21/02/2013
     */
    public void processNoEvalGuardar(String cantEvalObs, Date fechaActual) {
        log.info("start - " + login);
        // indica que la evaluación de la métrica fue cambiada
        getCurrentMetric().setChanged(true);
        getCurrentMetric().setEvaluada(false);
        getCurrentMetric().setCantEval(true);
        getCurrentMetric().setDescription(cantEvalObs);
        getCurrentMetric().setCantEvalObs(cantEvalObs);
        getCurrentMetric().getCirclesInfo().clear();
        getCurrentMetric().getSquaresInfo().clear();
        if (getCurrentMetric().getTiempoMarcas() != null) {
            getCurrentMetric().getTiempoMarcas().clear(); // limpia información del tiempo de marcas (videos)
        }

        getCurrentMetric().setEndEvaluationTime(fechaActual);
        log.debug("metricId: " + getCurrentMetric().getMetricId() + ", " + "setting metric endEvaluationTime: " + fechaActual
                + " - " + login);
        getMetricasModel().set(getCurrentIndex(), getCurrentMetric());

        Integer numMetrica = Integer.parseInt(getCurrentMetric().getNumMetrica());
        setRowFontColor(numMetrica, "green");

        // Valida si todas las métricas han sido evaluadas, no notifica solo retorna un boolean
        if (validateAllEvalNoNotify()) {
            getBtnEnviarEval().setDisabled(false); // Habilita el botón para enviar y continuar las evaluaciones
            getRightMetricDetail().getBtnEnviarFinalizar().setDisabled(false);
        } else {
            getBtnEnviarEval().setDisabled(true);
            getRightMetricDetail().getBtnEnviarFinalizar().setDisabled(true);
        }
        log.info("end - " + login);
    }

    /**
     * Procesa actualización de todas las métricas que no se pudieron evaluar
     *
     * @author carlos polo
     * @version 1.0.0
     * @param cantEvalObs motivo por el cual no se pudo evaluar
     * @param fechaActual fecha actual
     * @since 6.0
     * @date 21/02/2013
     */
    public void processNoEvalAllGuardar(String cantEvalObs, Date fechaActual) {
        log.info("start - " + login);
        for (int i = 0; i < getMetricasModel().size(); i++) {
            Metric metrica = (Metric) getMetricasModel().get(i);
            // indica que la evaluación de la métrica fue cambiada
            metrica.setChanged(true);
            metrica.setEvaluada(false);
            metrica.setCantEval(true);
            metrica.setCantEvalObs(cantEvalObs);
            metrica.setDescription(cantEvalObs);
            metrica.getCirclesInfo().clear();
            metrica.getSquaresInfo().clear();
            if (metrica.getTiempoMarcas() != null) {
                metrica.getTiempoMarcas().clear(); // limpia información del tiempo de marcas (videos)
            }

            if (metrica.getStartEvaluationTime() == null) {
                metrica.setStartEvaluationTime(fechaActual);
                log.debug("La métrica id: " + metrica.getMetricId() + ", aun no se ha seleccionado, "
                        + "se le establece startEvaluationTime: " + fechaActual + " - " + login);
            }

            log.debug("metricId: " + metrica.getMetricId() + ", " + "setting metric endEvaluationTime: " + fechaActual + " - "
                    + login);
            metrica.setEndEvaluationTime(fechaActual);
            getMetricasModel().set(i, metrica);
            setRowFontColor(i, "green");
        }
        log.info("end - " + login);
    }

    /**
     * Envía la evaluación de la situación actual para generar los proofs y actualizar el core
     *
     * @author carlos polo
     * @version 1.0.0
     * @param redirectInicio indica si debe redireccionar a la pantalla de inicio
     * @param isReintento indica si es reintento o no
     * @since 6.0
     * @date 26/04/2013
     * @throws ScopixException excepción en el envío
     */
    @Override
    public void sendEvaluation(boolean redirectInicio, boolean isReintento) throws ScopixException {
        log.info("start, redirectInicio: [" + redirectInicio + "], isReintento: [" + isReintento + "] - " + login);
        try {
            Client cliente = (Client) getMySession().getAttribute("CLIENT");
            String snapshotTime = (String) getMySession().getAttribute("SNAP_TIME");
            Long sessionId = (Long) getMySession().getAttribute("SESSION_ID");

            UserEvaluation userEvaluation = getOperatorManager().getHmUsersEvaluations().get(sessionId);
            if (userEvaluation == null) {
                log.debug("userEvaluation no existe, se crea");
                userEvaluation = new UserEvaluation();
            }
            if (!isReintento) {
                log.debug("userEvaluation.getPending(): [" + userEvaluation.getPending() + "]");
                userEvaluation.setPending(userEvaluation.getPending() + 1);
                getOperatorManager().getHmUsersEvaluations().put(sessionId, userEvaluation);
            }

            // Genera lista a partir de la situación para ser enviada
            log.debug("before updateSituationMetrics " + login);
            updateSituationMetrics();
            // Invoca generación de proofs
            log.debug("before generateProofs " + login);
            boolean proofsResult = generateProofs(cliente, snapshotTime);

            log.debug("[proofsResult:" + proofsResult + "][sessionId:" + sessionId + "][login:" + login + "]");
            if (proofsResult) {
                getLblEvalPendientes().setValue(String.valueOf(userEvaluation.getPending()));

                log.debug("sessionId: ["
                        + sessionId
                        + "], invocacion de proofs realizada, el envio al core se realizara en el correspondiente callback en OperatorWebServiceImpl, pendingCounter: ["
                        + userEvaluation.getPending() + "] - " + login);
                // Muestra mensaje para aceptar confirmación del envío exitoso de la evaluación
                // showFinalConfirmationMessage(redirectInicio, false); //isError -> false
                processSendEvalPageLoad(redirectInicio);

            } else {
                log.warn("error en la generacion de proofs - " + login);
                processProofsError(redirectInicio);
            }
        } catch (ScopixException e) {
            log.error(e.getMessage() + " - " + login, e);
        }
        log.info("end - " + login);
    }

    /**
     * Invoca servicio para la generación de proofs
     *
     * @author carlos polo
     * @version 1.0.0
     * @param cliente cliente seleccionado
     * @param snapshotTime tiempo para generación de snapshot
     * @since 6.0
     * @date 30/05/2013
     * @return boolean true si la generación fue exitosa
     * @throws ScopixException excepción en la generación de proofs
     */
    public boolean generateProofs(Client cliente, String snapshotTime) throws ScopixException {
        log.info("start, invocando generacion de proofs a traves del manager, cliente: [" + cliente.getName() + "] - " + login);
        boolean proofsResult = true;

        try {
            Long sessionId = (Long) getMySession().getAttribute("SESSION_ID");

            getSituation().setSessionId(sessionId);
            getSituation().setCliente(cliente);
            getSituation().setSnapshotTime(snapshotTime);
            getSituation().setEvaluationLogin(login);

            // Invoca servicio para la generación de proofs
            getOperatorManager().generateProofs(getSituation(), getMySession());

        } catch (ScopixException e) {
            proofsResult = false;
            log.error(e.getMessage() + " - " + login, e);
        }

        log.info("end, proofsResult: [" + proofsResult + "] - " + login);
        return proofsResult;
    }

    /**
     * Lógica de procesamiento en caso de error durante la generación de proofs
     *
     * @author carlos polo
     * @version 1.1.5
     * @param redirectInicio indica si debe redireccionar a la pantalla de inicio
     * @since 6.0
     * @date 23/07/2013
     * @throws ScopixException
     */
    protected void processProofsError(boolean redirectInicio) throws ScopixException {
        log.info("start, numero de reintentos: " + retryNumber + " - " + login);
        // Hubo error en la generación de proofs
        if (retryNumber > 0) {
            retryNumber--;
            log.debug("reintentando envio desde la generacion de proofs, numero de reintentos disponibles: " + retryNumber
                    + " - " + login);
            // Envía la evaluación de la situación actual para generar los proofs y actualizar el core
            sendEvaluation(redirectInicio, true);
        } else {
            Long sessionId = (Long) getMySession().getAttribute("SESSION_ID");
            UserEvaluation userEvaluation = getOperatorManager().getHmUsersEvaluations().get(sessionId);

            log.debug("userEvaluation.getFail(): [" + userEvaluation.getFail() + "], userEvaluation.getPending(): ["
                    + userEvaluation.getPending() + "]");
            userEvaluation.setFail(userEvaluation.getFail() + 1);
            userEvaluation.setPending(userEvaluation.getPending() - 1);
            getOperatorManager().getHmUsersEvaluations().put(sessionId, userEvaluation);

            log.debug("no se realizaran mas reintentos de generacion de proofs - " + login);
            // Muestra mensaje para aceptar confirmación del envío fallído de la evaluación
            showFinalConfirmationMessage(redirectInicio, true); // isError -> true
        }
        log.info("end - " + login);
    }

    /**
     * Muestra mensaje para aceptar confirmación del envío exitoso/fallído de una evaluación
     *
     * @author carlos polo
     * @version 1.1.5
     * @param redirectInicio indica si debe redireccionar a la pantalla de inicio
     * @param isError indica si debe ser un mensaje de confirmación de envío exitoso o fallído
     * @since 6.0
     * @date 23/07/2013
     */
    protected void showFinalConfirmationMessage(final boolean redirectInicio, boolean isError) {
        log.info("start, isError: " + isError + " - " + login);
        String messageType = isError ? Messagebox.ERROR : Messagebox.INFORMATION;
        log.debug("messageType: " + messageType + " - " + login);

        String[] strArray = setConfirmationMessage(isError);

        // strArray[0] -> título , strArray[1] -> mensaje
        Messagebox.show(strArray[1], strArray[0], Messagebox.OK, messageType, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                if ((((Integer) event.getData()).intValue() == Messagebox.OK) || event.getName().equalsIgnoreCase("onClose")) {
                    log.debug("El usuario acepta mensaje de confirmacion - " + login);
                    // Redirecciona a la pantalla de inicio o carga una nueva situación
                    processSendEvalPageLoad(redirectInicio);
                }
            }

        });
        log.info("end - " + login);
    }

    /**
     * Define mensaje de confirmación de envío a mostrar en pantalla dependiendo si hubo error o no durante el envío de la
     * evaluación
     *
     * @author carlos polo
     * @version 1.1.5
     * @param isError indica si debe ser un mensaje de confirmación de envío exitoso o fallído
     * @since 6.0
     * @date 23/07/2013
     * @return String[] arreglo con título y mensaje a mostrar
     */
    protected String[] setConfirmationMessage(boolean isError) {
        String titulo;
        String mensaje;
        log.info("start, isError: " + isError + " - " + login);

        if (isError) {
            // Ocurrió un error durante el envío de la evaluación
            mensaje = Labels.getLabel("eval.confirmarErrorEnvio");
            // No se pudo enviar la evaluación
            titulo = Labels.getLabel("eval.noExitoEnvioEvalTitle");
        } else {
            // Evaluación enviada exitosamente
            mensaje = Labels.getLabel("eval.confirmarExitoEnvio");
            // Envío exitoso
            titulo = Labels.getLabel("eval.confirmarExitoEnvioTitle");
        }

        log.debug("titulo: [" + titulo + "], mensaje: [" + mensaje + "] - " + login);
        return new String[]{titulo, mensaje};
    }

    /**
     * Redirecciona a la pantalla de inicio o carga una nueva situación
     *
     * @author carlos polo
     * @version 1.0.0
     * @param redirectInicio true si debe redireccionar a inicio
     * @since 6.0
     * @date 31/05/2013
     */
    public void processSendEvalPageLoad(boolean redirectInicio) {
        log.info("start, redirectInicio: " + redirectInicio + " - " + login);
        // Valida si existe información de marcas para eliminar
        validateToDeleteAllShapes();
        if (redirectInicio) {
            log.debug("redireccionando a la pantalla de inicio - " + login);
            // No necesita mostrar mensaje de confirmación de cierre/recarga de página
            Clients.evalJavaScript("needToConfirmCenterImg = false; needToConfirmCenterVid = false;");
            getExecution().sendRedirect("inicio.zul");
        } else {
            log.debug("cargando nueva situacion - " + login);
            // Obtiene la siguiente situación
            getGlobalEvaluation().setSituation();
        }
        log.info("end - " + login);
    }

    /**
     * Actualiza las métricas de la situación
     */
    public void updateSituationMetrics() {
        log.info("start - " + login);
        getSituation().setMetricas((getMetricasModel()));
        log.info("end - " + login);
    }

    /**
     * @return the metricasListBox
     */
    public Listbox getMetricasListBox() {
        return metricasListBox;
    }

    /**
     * @param metricasListBox the metricasListBox to set
     */
    public void setMetricasListBox(Listbox metricasListBox) {
        this.metricasListBox = metricasListBox;
    }

    /**
     * @return the metricasModel
     */
    @Override
    public ListModelList getMetricasModel() {
        return metricasModel;
    }

    /**
     * @param metricasModel the metricasModel to set
     */
    @Override
    public void setMetricasModel(ListModelList metricasModel) {
        this.metricasModel = metricasModel;
    }

    /**
     * @return the txtObsCalidad
     */
    public Textbox getTxtObsCalidad() {
        return txtObsCalidad;
    }

    /**
     * @param txtObsCalidad the txtObsCalidad to set
     */
    public void setTxtObsCalidad(Textbox txtObsCalidad) {
        this.txtObsCalidad = txtObsCalidad;
    }

    /**
     * @return the evaluacionBean
     */
    @Override
    public GlobalEvaluation getGlobalEvaluation() {
        return evaluacionBean;
    }

    /**
     * @param evaluacionBean the evaluacionBean to set
     */
    public void setEvaluacionBean(GlobalEvaluation evaluacionBean) {
        this.evaluacionBean = evaluacionBean;
    }

    /**
     * @return the rightMetricDetail
     */
    public RightMetricDetail getRightMetricDetail() {
        return rightMetricDetail;
    }

    /**
     * @param rightMetricDetail the rightMetricDetail to set
     */
    public void setRightMetricDetail(RightMetricDetail rightMetricDetail) {
        this.rightMetricDetail = rightMetricDetail;
    }

    /**
     * @return the auxHeadMetrica
     */
    public Auxheader getAuxHeadMetrica() {
        return auxHeadMetrica;
    }

    /**
     * @param auxHeadMetrica the auxHeadMetrica to set
     */
    public void setAuxHeadMetrica(Auxheader auxHeadMetrica) {
        this.auxHeadMetrica = auxHeadMetrica;
    }

    @Override
    public String getQualityObservation() {
        return getTxtObsCalidad().getValue();
    }

    @Override
    public void setQualityObservation(String observation) {
        log.info("start, quality observation: [" + observation + "] - " + login);
        if (observation != null && !observation.trim().equals("")) {
            getTxtObsCalidad().setStyle(
                    "resize:none; background-color: #383FCA; " + "font-weight: bold; color:#F2F5A9; font-size: 15px;");

            getTxtObsCalidad().setValue(observation);
        }
        log.info("end - " + login);
    }

    /**
     * Marca en cero todas las evidencias de todas las métricas de conteo
     *
     * @author carlos polo
     * @version 1.0.0
     * @param value valor para actualizar en las métricas (cero)
     * @since 6.0
     * @date 08/03/2013
     */
    @Override
    public void setCountMetricsToZero(String value) {
        log.info("start - " + login);
        Date fechaActual = new Date();

        for (int i = 0; i < getMetricasModel().size(); i++) {
            Metric metrica = (Metric) getMetricasModel().get(i);
            if (metrica.getType().equals(EnumEvaluationType.COUNTING.toString())) {
                // indica que la evaluación de la métrica fue cambiada
                metrica.setChanged(true);
                metrica.setEvaluada(true);
                metrica.setCantEval(false);
                metrica.setCantEvalObs(null);
                metrica.setDescription(value);

                if (metrica.getStartEvaluationTime() == null) {
                    metrica.setStartEvaluationTime(fechaActual);
                    log.debug("La metrica id: " + metrica.getMetricId() + ", aun no se ha seleccionado, "
                            + "se le establece startEvaluationTime: " + fechaActual + " - " + login);
                }

                log.debug("metricId: " + metrica.getMetricId() + ", " + "setting metric endEvaluationTime: " + fechaActual
                        + " - " + login);
                metrica.setEndEvaluationTime(fechaActual);

                if (metrica.isMultiple()) {
                    List<Evidence> lstEvidencias = metrica.getEvidencias();

                    for (Evidence evidencia : lstEvidencias) {
                        Integer cameraId = evidencia.getEvidenceProvider().getId();
                        metrica.getCirclesInfo().put(cameraId, "0"); // limpia información de círculos marcados
                    }
                } else {
                    metrica.getCirclesInfo().put(metrica.getCurrentCameraId(), "0"); // limpia información de círculos marcados
                }
                if (metrica.getTiempoMarcas() != null) {
                    metrica.getTiempoMarcas().clear(); // limpia información del tiempo de marcas (videos)
                }

                getMetricasModel().set(i, metrica);
                setRowFontColor(i, "green");
            }
        }

        if (validateAllEvalNoNotify()) {
            setAllEvaluated(true);
            getBtnEnviarEval().setDisabled(false); // Habilita el botón para enviar y continuar las evaluaciones
            getRightMetricDetail().getBtnEnviarFinalizar().setDisabled(false);
        }
        log.info("end - " + login);
    }

    /**
     * Marca en cero las evidencias de la cámara actual en todas las métricas de conteo
     *
     * @author carlos polo
     * @version 1.0.0
     * @param cameraId id de la cámara
     * @since 6.0
     * @date 08/04/2013
     */
    @Override
    public void setCountMetricsToZeroCamera(int cameraId) {
        log.info("start, cameraId: " + cameraId + " - " + login);
        Date fechaActual = new Date();

        for (int i = 0; i < getMetricasModel().size(); i++) {
            Metric metrica = (Metric) getMetricasModel().get(i);

            if (metrica.getType().equals(EnumEvaluationType.COUNTING.toString())) {
                Integer cameraId2 = metrica.getEvidencias().get(0).getEvidenceProvider().getId();
                if (cameraId == cameraId2) {
                    // indica que la evaluación de la métrica fue cambiada
                    metrica.setChanged(true);
                    metrica.setEvaluada(true);
                    metrica.setCantEval(false);
                    metrica.setCantEvalObs(null);
                    metrica.setDescription("0");

                    if (metrica.getStartEvaluationTime() == null) {
                        metrica.setStartEvaluationTime(fechaActual);
                        log.debug("La metrica id: " + metrica.getMetricId() + ", aun no se ha seleccionado, "
                                + "se le establece startEvaluationTime: " + fechaActual + " - " + login);
                    }

                    log.debug("metricId: " + metrica.getMetricId() + ", " + "setting metric endEvaluationTime: " + fechaActual
                            + " - " + login);
                    metrica.setEndEvaluationTime(fechaActual);
                    metrica.getCirclesInfo().put(cameraId, "0"); // limpia información de círculos marcados
                    // metrica.getSquaresInfo().put(cameraId, "0"); //limpia información de marcas de grupo realizadas
                    if (metrica.getTiempoMarcas() != null) {
                        metrica.getTiempoMarcas().clear(); // limpia información del tiempo de marcas (videos)
                    }

                    getMetricasModel().set(i, metrica);
                    setRowFontColor(i, "green");
                }
            }
        }

        if (validateAllEvalNoNotify()) {
            setAllEvaluated(true);
            getBtnEnviarEval().setDisabled(false); // Habilita el botón para enviar y continuar las evaluaciones
            getRightMetricDetail().getBtnEnviarFinalizar().setDisabled(false);
        }
        log.info("end - " + login);
    }

    /**
     * Pone fondo blanco a todas las filas de la lista
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0 @date 21/02/2013
     */
    protected void setRowWhiteBg() {
        for (int i = 0; i < getMetricasModel().size(); i++) {
            Clients.evalJavaScript("setRowWhiteBg(" + i + ");");
        }
    }

    /**
     * Ajusta color de letra a la fila especificada
     *
     * @author carlos polo
     * @version 1.0.0
     * @param index
     * @param color
     * @since 6.0 @date 21/02/2013
     */
    protected void setRowFontColor(int index, String color) {
        Clients.evalJavaScript("setRowFontColor(" + index + ", '" + color + "');");
    }

    /**
     * @return the textoMetrica
     */
    public String getTextoMetrica() {
        return textoMetrica;
    }

    /**
     * @param textoMetrica the textoMetrica to set
     */
    public void setTextoMetrica(String textoMetrica) {
        this.textoMetrica = textoMetrica;
    }

    /**
     * @return the currentMetric
     */
    @Override
    public Metric getCurrentMetric() {
        return currentMetric;
    }

    /**
     * @param currentMetric the currentMetric to set
     */
    public void setCurrentMetric(Metric currentMetric) {
        this.currentMetric = currentMetric;
    }

    /**
     * @return the showMessage
     */
    public ShowMessage getShowMessage() {
        if (showMessage == null) {
            showMessage = ShowMessage.getInstance();
        }
        return showMessage;
    }

    /**
     * @param showMessage the showMessage to set
     */
    public void setShowMessage(ShowMessage showMessage) {
        this.showMessage = showMessage;
    }

    /**
     * @return the currentIndex
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * @param currentIndex the currentIndex to set
     */
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    /**
     * @return the lblNoEvaluar
     */
    public Label getLblNoEvaluar() {
        return lblNoEvaluar;
    }

    /**
     * @param lblNoEvaluar the lblNoEvaluar to set
     */
    public void setLblNoEvaluar(Label lblNoEvaluar) {
        this.lblNoEvaluar = lblNoEvaluar;
    }

    /**
     * @return the userCredentialManager
     */
    public UserCredentialManager getUserCredentialManager() {
        if (userCredentialManager == null) {
            userCredentialManager = UserCredentialManager.getIntance(getMySession());
        }
        return userCredentialManager;
    }

    /**
     * @param userCredentialManager the userCredentialManager to set
     */
    public void setUserCredentialManager(UserCredentialManager userCredentialManager) {
        this.userCredentialManager = userCredentialManager;
    }

    /**
     * @return the execution
     */
    public Execution getExecution() {
        return execution;
    }

    /**
     * @param execution the execution to set
     */
    public void setExecution(Execution execution) {
        this.execution = execution;
    }

    /**
     * @return the btnEnviarEval
     */
    public Button getBtnEnviarEval() {
        return btnEnviarEval;
    }

    /**
     * @param btnEnviarEval the btnEnviarEval to set
     */
    public void setBtnEnviarEval(Button btnEnviarEval) {
        this.btnEnviarEval = btnEnviarEval;
    }

    /**
     * @return the lblPlayerTime
     */
    @Override
    public Label getLblPlayerTime() {
        return lblPlayerTime;
    }

    /**
     * @param lblPlayerTime the lblPlayerTime to set
     */
    public void setLblPlayerTime(Label lblPlayerTime) {
        this.lblPlayerTime = lblPlayerTime;
    }

    /**
     * @return the allCounting
     */
    public boolean isAllCounting() {
        return allCounting;
    }

    /**
     * @param allCounting the allCounting to set
     */
    public void setAllCounting(boolean allCounting) {
        this.allCounting = allCounting;
    }

    /**
     * @return the lblCurrentEvidenceType
     */
    public Label getLblCurrentEvidenceType() {
        return lblCurrentEvidenceType;
    }

    /**
     * @param lblCurrentEvidenceType the lblCurrentEvidenceType to set
     */
    public void setLblCurrentEvidenceType(Label lblCurrentEvidenceType) {
        this.lblCurrentEvidenceType = lblCurrentEvidenceType;
    }

    /**
     * @return the allEvaluated
     */
    public boolean isAllEvaluated() {
        return allEvaluated;
    }

    /**
     * @param allEvaluated the allEvaluated to set
     */
    public void setAllEvaluated(boolean allEvaluated) {
        this.allEvaluated = allEvaluated;
    }

    /**
     * @return the popLeftMetricBean
     */
    public PopLeftMetricBean getPopLeftMetricBean() {
        return popLeftMetricBean;
    }

    /**
     * @param popLeftMetricBean the popLeftMetricBean to set
     */
    public void setPopLeftMetricBean(PopLeftMetricBean popLeftMetricBean) {
        this.popLeftMetricBean = popLeftMetricBean;
    }

    /**
     * @return the operatorManager
     */
    public OperatorManager getOperatorManager() {
        if (operatorManager == null) {
            operatorManager = SpringSupport.getInstance().findBeanByClassName(OperatorManager.class);
        }
        return operatorManager;
    }

    /**
     * @param operatorManager the operatorManager to set
     */
    public void setOperatorManager(OperatorManager operatorManager) {
        this.operatorManager = operatorManager;
    }

    @Override
    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    /**
     * @return the situation
     */
    @Override
    public Situation getSituation() {
        return situation;
    }

    /**
     * @return the mySession
     */
    public Session getMySession() {
        if (mySession == null) {
            mySession = Sessions.getCurrent();
        }
        return mySession;
    }

    /**
     * @param mySession the mySession to set
     */
    public void setMySession(Session mySession) {
        this.mySession = mySession;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the retryNumber
     */
    public Integer getRetryNumber() {
        return retryNumber;
    }

    /**
     * @param retryNumber the retryNumber to set
     */
    public void setRetryNumber(Integer retryNumber) {
        this.retryNumber = retryNumber;
    }

    @Override
    public Label getLblEvalPendientes() {
        return lblEvalPendientes;
    }

    public void setLblEvalPendientes(Label lblEvalPendientes) {
        this.lblEvalPendientes = lblEvalPendientes;
    }

    @Override
    public Label getLblEvalExitosas() {
        return lblEvalExitosas;
    }

    public void setLblEvalExitosas(Label lblEvalExitosas) {
        this.lblEvalExitosas = lblEvalExitosas;
    }

    @Override
    public Label getLblEvalFallidas() {
        return lblEvalFallidas;
    }

    public void setLblEvalFallidas(Label lblEvalFallidas) {
        this.lblEvalFallidas = lblEvalFallidas;
    }
}
