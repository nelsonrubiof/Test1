package com.scopix.periscope.interfaces;

import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;

import com.scopix.periscope.model.Metric;

/**
 * Métodos expuestos para la parte central de la pantalla de evaluaciones
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public interface CenterEvidence {

    /**
     * @param alternateSrc the alternateSrc to set
     */
    void setAlternateSrc(String alternateSrc);

    /**
     * @param templatePath the templatePath to set
     */
    void setTemplatePath(String templatePath);

    /**
     * @param metric the metric to set
     */
    void setMetric(Metric metric);

    /**
     * @param metricType the metricType to set
     */
    void setMetricType(String metricType);

    /**
     * @return the evidenceURL
     */
    String getEvidenceURL();

    /**
     * @param evidenceURL the evidenceURL to set
     */
    void setEvidenceURL(String evidenceURL);

    /**
     * @param evidenceType the evidenceType to set
     */
    void setEvidenceType(String evidenceType);

    /**
     * @param playerPosition the playerPosition to set
     */
    void setPlayerPosition(String playerPosition);

    /**
     * Vincula componente include con bean central y establece bean padre de la parte derecha
     * 
     * @param centerInclude include de la parte central
     * @param rightMetricBean bean de la parte derecha
     */
    void bindComponentAndParent(Include centerInclude, RightMetricDetail rightMetricBean);

    /**
     * Retorna el label con tiempo de marcas
     * 
     * @return label con tiempo de marcas
     */
    Label getLblTiempoMarcas();

    /**
     * Retorna el label con tiempo de marcas inicial
     * 
     * @return label con tiempo de marcas
     */
    Label getLblTiempoMarcasInicial();

    /**
     * Retorna el label con tiempo de marcas final
     * 
     * @return label con tiempo de marcas
     */
    Label getLblTiempoMarcasFinal();

    /**
     * Muestra marcas en caso de existir
     * 
     * @param event evento generado
     */
    void onClickVerMarcas(ForwardEvent event);

    void setOperatorImagesIp(String operatorImagesIp);
}