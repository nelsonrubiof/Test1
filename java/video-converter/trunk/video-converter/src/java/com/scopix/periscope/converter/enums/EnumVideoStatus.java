/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  EnumVideoStatus.java
 * 
 *  Created on 20-08-2013, 11:11:44 AM
 * 
 */
package com.scopix.periscope.converter.enums;

/**
 * Enumeración con los diferentes estados de conversión de videos
 *
 * @author  carlos polo
 * @version 1.0.0
 * @since   6.0
 */
public enum EnumVideoStatus {

    PENDIENTE("P"),
    CONVERTIDO("C"),
    ERROR("E");

    private final String name;

    private EnumVideoStatus(String s) {
        name = s;
    }

    @Override
    public String toString(){
       return name;
    }
}