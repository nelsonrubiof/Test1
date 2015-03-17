package com.scopix.periscope.interfaces;

import org.zkoss.zul.Include;

import com.scopix.periscope.bean.NorthHeaderBean;

/**
 * Métodos expuestos para la parte global de la pantalla de evaluaciones de situaciones
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public interface GlobalEvaluation {

    /**
     * Obtiene siguiente situación por evaluar
     */
    void setSituation();

    /**
     * @return the northHeaderBean
     */
    NorthHeaderBean getNorthHeaderBean();

    /**
     * Método invocado una vez el bean de la parte izquierda (west) finaliza su construcción
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param bean beanHijo
     * @since 6.0
     * @date 20/02/2013
     */
    void leftComponentReady(LeftMetricList bean);

    /**
     * Método invocado una vez el bean de la parte superior (north) finaliza su construcción
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param bean beanHijo
     * @since 6.0
     * @date 20/02/2013
     */
    void northComponentReady(NorthEvalHeader bean);

    /**
     * @param rightInclude the rightInclude to set
     */
    void setRightInclude(Include rightInclude);

    /**
     * @param centerInclude the centerInclude to set
     */
    void setCenterInclude(Include centerInclude);
}