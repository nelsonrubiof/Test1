/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 * 
 */
package com.scopix.periscope.periscopefoundation;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * This is a base class for all business object.<br> It provides an id to be used as PK in database.
 *
 * @author Nelson Rubio
 * @version 2.0.0
 *
 */
@MappedSuperclass
public abstract class BusinessObject implements Serializable {

    /**
     *
     * @return Id del Objeto
     */
    public Integer getId() {
        return this.id;
    }

    /**
     *
     * @param id del Objeto
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Integer id;

    @Override
    public String toString() {
        return "[id:" + this.getId() + "][class:" + this.getClass().getSimpleName() + "]";
    }
}
