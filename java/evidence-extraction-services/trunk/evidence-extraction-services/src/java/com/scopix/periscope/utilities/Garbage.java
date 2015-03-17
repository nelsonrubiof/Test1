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
 *  Garbage.java
 * 
 *  Created on 27-11-2013, 01:02:03 PM
 * 
 */
package com.scopix.periscope.utilities;

import org.apache.log4j.Logger;

/**
 *
 * @author Nelson Rubio
 * @autor-email nelson.rubio@scopixsolutions.com
 * @version 1.0.0
 */
public class Garbage {
    
    private static Logger log = Logger.getLogger(Garbage.class);
    
    /**
     * libera la memoria utilizada por java
     */
    public static void freeMemory() {
        try {            
            //Runtime garbage = Runtime.getRuntime();
            //log.debug("Memoria libre antes de limpieza: " + System.freeMemory());
            System.gc();
            //garbage.gc();
            //log.debug("Memoria libre tras la limpieza : " + garbage.freeMemory());
        } catch (Exception e) {
            log.warn("No es posible liberar memoria " + e);
        }
    }
}
