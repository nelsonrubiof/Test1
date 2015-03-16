/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scopix.periscope.queuemanagement.dto;

/**
 *
 * @author cabarza
 */
public class OperatorQueueDTO {
    private Integer id;
    private String name;

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
}
