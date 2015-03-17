package com.scopix.periscope.queuemanagement;

/**
 * Clase para referenciar datos de las colas de la aplicación
 * 
 * @author  carlos polo
 * @version 1.0.0
 * @since   6.0
 */
public class Queue {

    private Integer id;
    private String name;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

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
}