package com.scopix.periscope.interfaces;

import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;

import com.scopix.periscope.model.Metric;
import com.scopix.periscope.model.Situation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 * Métodos expuestos para la parte izquierda de la pantalla de evaluaciones
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SuppressWarnings("rawtypes")
public interface LeftMetricList {

    /**
     * Valida si todas las métricas están evaluadas
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 21/02/2013
     * @return boolean true si todas las métricas están evaluadas
     */
    boolean validateAllEvaluations();

    /**
     * @return the currentMetric
     */
    Metric getCurrentMetric();

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
    void sendEvaluation(boolean redirectInicio, boolean isReintento) throws ScopixException;

    /**
     * Valida si la métrica fue evaluada o no
     *
     * @author carlos polo
     * @version 1.0.0
     * @param selectedIndex índice seleccionado
     * @since 6.0
     * @date 21/02/2013
     */
    void validateMetricEval(int selectedIndex);

    /**
     * Marca en cero todas las evidencias de todas las métricas de conteo
     *
     * @author carlos polo
     * @version 1.0.0
     * @param value valor para actualizar en las métricas (cero)
     * @since 6.0
     * @date 08/03/2013
     */
    void setCountMetricsToZero(String value);

    /**
     * Marca en cero las evidencias de la cámara actual en todas las métricas de conteo
     *
     * @author carlos polo
     * @version 1.0.0
     * @param cameraId id de la cámara
     * @since 6.0
     * @date 08/04/2013
     */
    void setCountMetricsToZeroCamera(int cameraId);

    /**
     * Actualiza el valor de la métrica
     *
     * @author carlos polo
     * @version 1.0.0
     * @param value valor para actualizar en la métrica
     * @since 6.0
     * @date 21/02/2013
     */
    void updateMetricValue(String value);

    /**
     * @return the lblPlayerTime
     */
    Label getLblPlayerTime();

    /**
     * @return the evaluacionBean
     */
    GlobalEvaluation getGlobalEvaluation();

    /**
     * @return the metricasModel
     */
    ListModelList getMetricasModel();

    /**
     * @param metricList the metricList to set
     */
    void setMetricasModel(ListModelList metricList);

    /**
     * @param situation the situation to set
     */
    void setSituation(Situation situation);

    /**
     * @return the qualityObservation
     */
    String getQualityObservation();

    /**
     * @param observation the observation to set
     */
    void setQualityObservation(String observation);

    void onClickFilaMetrica(ForwardEvent event);

    Situation getSituation();

    Label getLblEvalFallidas();

    Label getLblEvalExitosas();

    Label getLblEvalPendientes();
}