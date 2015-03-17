package com.scopix.periscope.model;

import java.io.Serializable;

/**
 * Clase para referenciar datos de las colas de la aplicación
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class Queue implements Serializable, Comparable<Queue> {

    private Integer id;
    private String name;
    private static final long serialVersionUID = 4430695906138422530L;

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

    @Override
    public int compareTo(Queue queue) {
        int result = 0;
        if (this.getName() != null) {
            String name2 = queue.getName();
            if (name2 != null) {
                result = this.getName().compareToIgnoreCase(name2);
            }
        }
        return result;
    }
}