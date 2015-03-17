package com.scopix.periscope.model;

import java.io.Serializable;

/**
 * Clase para referenciar datos de los evidenceProviders de la aplicación
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class EvidenceProvider implements Serializable {

    private Integer id;
    private Integer viewOrder;
    private String descripcion;
    private static final long serialVersionUID = 805350497699828429L;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the viewOrder
     */
    public Integer getViewOrder() {
        return viewOrder;
    }

    /**
     * @param viewOrder the viewOrder to set
     */
    public void setViewOrder(Integer viewOrder) {
        this.viewOrder = viewOrder;
    }
}