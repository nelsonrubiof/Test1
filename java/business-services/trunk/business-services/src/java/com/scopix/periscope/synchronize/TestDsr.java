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
 *  TestDsr.java
 * 
 *  Created on 03-03-2014, 05:51:45 PM
 * 
 */
package com.scopix.periscope.synchronize;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.util.Arrays;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author Nelson
 */
public class TestDsr {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String[] metricTemplateIds = new String[]{"1", "2", "6", "7", "8", "65536"};
        System.out.println("metricTemplateIds: " + metricTemplateIds);
        System.out.println("ArrayUtils.contains(metricTemplateIds, String.valueOf(1)):" + ArrayUtils.contains(metricTemplateIds, String.valueOf(1)));
        System.out.println("ArrayUtils.contains(metricTemplateIds, String.valueOf(2)):" + ArrayUtils.contains(metricTemplateIds, String.valueOf(2)));
        System.out.println("ArrayUtils.contains(metricTemplateIds, String.valueOf(6)):" + ArrayUtils.contains(metricTemplateIds, String.valueOf(6)));
        System.out.println("ArrayUtils.contains(metricTemplateIds, String.valueOf(7)):" + ArrayUtils.contains(metricTemplateIds, String.valueOf(7)));
        System.out.println("ArrayUtils.contains(metricTemplateIds, String.valueOf(8)):" + ArrayUtils.contains(metricTemplateIds, String.valueOf(8)));
        System.out.println("ArrayUtils.contains(metricTemplateIds, String.valueOf(65536)):" + ArrayUtils.contains(metricTemplateIds, String.valueOf(65536)));
        if (ArrayUtils.contains(metricTemplateIds, String.valueOf(8))) {
            System.out.println("OK");
        }
//        DSRExternalApplication dsr = new DSRExternalApplication();
//        try {
//            dsr.sendData(1);
//        } catch (ScopixException e) {
//            System.out.println("error " + e);
//        }
    }

}
