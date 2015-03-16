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
 *  FileDownloadList.java
 * 
 *  Created on 19-06-2014, 05:57:37 PM
 * 
 */
package com.scopix.periscope.businessservices.commons;

import java.util.LinkedList;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nelson
 */
@XmlRootElement
public class FileDownloadList {

    private LinkedList<FileDownload> data;

    /**
     * @return the data
     */
    public LinkedList<FileDownload> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(LinkedList<FileDownload> data) {
        this.data = data;
    }

}
