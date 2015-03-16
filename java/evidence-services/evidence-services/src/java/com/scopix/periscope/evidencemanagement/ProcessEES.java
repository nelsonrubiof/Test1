/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement;

import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlan;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @version 1.0.0
 * @author nelson
 */
@Entity
public class ProcessEES extends BusinessObject {

    @ManyToOne
    private ExtractionPlan extractionPlan;
    private Integer processIdEes;
    private Integer processIdLocal;

    /**     
     * @return ExtractionPlan extraction plan relacionado
     */
    public ExtractionPlan getExtractionPlan() {
        return extractionPlan;
    }

    /**
     * @param extractionPlan the extractionPlan to set
     */
    public void setExtractionPlan(ExtractionPlan extractionPlan) {
        this.extractionPlan = extractionPlan;
    }

    /**
     * @return Integer processId recuperado desde Evidence Extraction
     */
    public Integer getProcessIdEes() {
        return processIdEes;
    }

    /**
     * @param processIdEes the processIdEes to set
     */
    public void setProcessIdEes(Integer processIdEes) {
        this.processIdEes = processIdEes;
    }

    /**
     * @return Integer processId generado en forma local
     */
    public Integer getProcessIdLocal() {
        return processIdLocal;
    }

    /**
     * @param processIdLocal the processIdLocal to set
     */
    public void setProcessIdLocal(Integer processIdLocal) {
        this.processIdLocal = processIdLocal;
    }
}
