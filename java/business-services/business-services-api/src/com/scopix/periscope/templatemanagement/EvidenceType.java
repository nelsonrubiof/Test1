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
 * EvidenceType.java
 *
 * Created on 30-05-2008, 03:54:59 PM
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
public enum EvidenceType {

    IMAGE, VIDEO, XML;

    public String getName() {
        return this.name();
    }
}
