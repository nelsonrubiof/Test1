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
 * NewEnum.java
 *
 * Created on 29-05-2008, 06:40:51 PM
 *
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.templatemanagement;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public enum MetricType {

    YES_NO,
    COUNTING,
    MEASURE_TIME,
    NUMBER_INPUT;

    public String getName() {
        return this.name();
    }
}
