package com.scopix.periscope.interfaces;

import java.util.List;

import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;

import com.scopix.periscope.bean.LeftMetricBean;
import com.scopix.periscope.model.Metric;

/**
 * Métodos expuestos para la parte derecha de la pantalla de evaluaciones
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SuppressWarnings("rawtypes")
public interface RightMetricDetail {

    /**
     * @return the metric
     */
    Metric getMetric();

    /**
     * Obtiene valor de la métrica y estado de evaluación
     * 
     * @return List con valor de la métrica y estado de evaluación
     */
    List setMetricEvalValues();

    /**
     * Obtiene información de marcas realizadas
     */
    void getShapesData();

    /**
     * @return the currentCameraId
     */
    int getCurrentCameraId();

    /**
     * @return the leftMetricBean
     */
    LeftMetricList getLeftMetricBean();

    /**
     * @param allEvaluated the allEvaluated to set
     */
    void setAllEvaluated(boolean allEvaluated);

    /**
     * Almacena información de la evaluación y prepara siguiente evidencia
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param selectedCameraId id de la cámara seleccionada
     * @param isCambioCamara indica si hubo cambio de cámara
     * @since 6.0
     * @date 08/03/2013
     */
    void saveCurrentEvidenceEvalData(int selectedCameraId, boolean isCambioCamara);

    /**
     * Habilita/deshabilita botones para métricas de MEASURE_TIME
     * 
     * @param state estado para habilitar/deshabilitar botones
     */
    void setTimeButtonsDisabledState(boolean state);

    /**
     * @return the lblValorMetrica
     */
    Label getLblValorMetrica();

    /**
     * Actualiza el valor de la métrica
     * 
     * @param value valor por actualizar
     */
    void updateMetricValue(String value);

    /**
     * @param metric the metric to set
     */
    void setMetric(Metric metric);

    /**
     * @return the metricResult
     */
    Long getMetricResult();

    /**
     * Vincula componente include con bean derecho y establece bean padre de la parte izquierda
     * 
     * @param rightInclude include de la parte derecha
     * @param leftMetricBean bean de la parte izquierda
     */
    void bindComponentAndParent(Include rightInclude, LeftMetricBean leftMetricBean);

    /**
     * @param playerPosition the playerPosition to set
     */
    void setPlayerPosition(String playerPosition);

    /**
     * @param allCounting the allCounting to set
     */
    void setAllCounting(boolean allCounting);

    /**
     * @return the btnEnviarFinalizar
     */
    Button getBtnEnviarFinalizar();

    /**
     * Habilita/deshabilita el campo de entrada de número
     * 
     * @param state true o false
     */
    void setNumberInputBoxState(boolean state);

    /**
     * Retorna instancia de la parte central de la pantalla
     * 
     * @return instancia de la parte central de la pantalla
     */
    CenterEvidence getCenterEvidence();

    /**
     * Actualiza información de marcas realizadas
     */
    void updateEvalData();

    /**
     * Cambia hacia la cámara en donde se hicieron las marcas
     * 
     * @param idCamaraMarcas id de la cámara en donde se hicieron las marcas
     * @param tiempoMarcas tiempo en el que se hicieron las marcas
     */
    void changeCameraVerMarcas(int idCamaraMarcas, String tiempoMarcas);

    /**
     * Valida que al menos una cámara haya sido evaluada
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param evaluada indica si el valor de la métrica es diferente a "no definido"
     * @since 6.0
     * @date 08/03/2013
     * @return boolean true si al menos una cámara fue evaluada
     */
    boolean validateCamerasEval(boolean evaluada);
}