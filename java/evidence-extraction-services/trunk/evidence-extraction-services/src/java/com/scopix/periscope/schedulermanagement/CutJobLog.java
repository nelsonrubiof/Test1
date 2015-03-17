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
 * CutJobLog.java
 *
 * Created on 3 de julio de 2007, 9:51
 *
 */

package com.scopix.periscope.schedulermanagement;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author marko.perich
 */
public class CutJobLog {
    
    /** Creates a new instance of CutJobLog */
    public CutJobLog() {
        fileNames = new LinkedList<String>();
    }
    
    private Date uploadTime;
    private Date evidenceStart;
    private long evidenceLongInSec;
    private String sourcePath;
    private List<String> fileNames;
    private String targetPath;
    
    public Date getUploadTime() {
        return uploadTime;
    }
    
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
    
    public Date getEvidenceStart() {
        return evidenceStart;
    }
    
    public void setEvidenceStart(Date evidenceStart) {
        this.evidenceStart = evidenceStart;
    }
    
    public long getEvidenceLongInSec() {
        return evidenceLongInSec;
    }
    
    public void setEvidenceLongInSec(long evidenceLongInSec) {
        this.evidenceLongInSec = evidenceLongInSec;
    }
    
    public String getSourcePath() {
        return sourcePath;
    }
    
    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }
    
    public List<String> getFileNames() {
        return fileNames;
    }
    
    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }
    
    public String getTargetPath() {
        return targetPath;
    }
    
    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }
    
    
    
}
