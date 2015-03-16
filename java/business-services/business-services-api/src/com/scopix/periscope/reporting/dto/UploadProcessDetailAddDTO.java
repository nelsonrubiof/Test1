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
 *  UploadProcessDetailAddDTO.java
 * 
 *  Created on 13-01-2011, 05:05:06 PM
 * 
 */
package com.scopix.periscope.reporting.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class UploadProcessDetailAddDTO {

    private List<UploadProcessDetailDTO> aggregate;
    private List<UploadProcessDetailDTO> unknown;

    public List<UploadProcessDetailDTO> getAggregate() {
        if (aggregate == null) {
            aggregate = new ArrayList<UploadProcessDetailDTO>();
        }
        return aggregate;
    }

    public void setAggregate(List<UploadProcessDetailDTO> aggregate) {
        this.aggregate = aggregate;
    }

    public List<UploadProcessDetailDTO> getUnknown() {
        if (unknown == null) {
            unknown = new ArrayList<UploadProcessDetailDTO>();
        }
        return unknown;
    }

    public void setUnknown(List<UploadProcessDetailDTO> unknown) {
        this.unknown = unknown;
    }
}
