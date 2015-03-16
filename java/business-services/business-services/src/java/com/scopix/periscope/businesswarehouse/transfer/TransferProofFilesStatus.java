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
 *  TransferProofFilesStatus.java
 * 
 *  Created on 19-07-2011, 04:14:53 PM
 * 
 */
package com.scopix.periscope.businesswarehouse.transfer;

/**
 *
 * @author Gustavo Alvarez
 */
public enum TransferProofFilesStatus {

    CREATED, TRANSFERED, FINISHED;

    public String getName() {
        return name();
    }
}
