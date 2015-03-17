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
 *  EvidenceExtractionRequestVideo.java
 * 
 *  Created on 16-09-2014, 04:15:50 PM
 * 
 */
package com.scopix.periscope.extractionmanagement;

import javax.persistence.Entity;

/**
 *
 * @author Nelson
 */
@Entity
public abstract class EvidenceExtractionRequestVideo extends EvidenceExtractionRequest {

    //se utiliza para recuperar el largo de los videos
    public abstract int getLengthInSecs();

    //se utiliza para recuperar el largo de los videos
    public abstract void setLengthInSecs(int length);

}
