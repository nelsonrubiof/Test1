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
 *  Testpath.java
 * 
 *  Created on 25-09-2014, 09:46:06 AM
 * 
 */
package com.scopix.periscope.qualitycontrol.services.controllers;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Nelson
 */
public class Testpath {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String proofPrePath = "\\\\192.168.9.10\\periscope.data\\proofs\\Starbucks/13331/";
        String localPaht = FilenameUtils.separatorsToUnix("/data/ftp/" + StringUtils.substring(proofPrePath, StringUtils.indexOf(proofPrePath, "proofs")));
        System.out.println(proofPrePath);
        System.out.println(localPaht);

    }

}
