/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * Evidence.java
 *
 * Created on 06-05-2008, 07:44:43 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.extractionplanmanagement.Formula;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class Indicator extends BusinessObject {

    @Lob
    private String name;
    @Lob
    private String labelY;
    @Lob
    private String labelX;
    private Integer initialTime;
    private Integer endingTime;
    @OneToMany(targetEntity = IndicatorProductAndAreaType.class, mappedBy = "indicator", cascade = {CascadeType.PERSIST,
        CascadeType.MERGE})
    private List<IndicatorProductAndAreaType> indicatorProductAndAreaTypes;
    @Lob
    private String numeratorFormula;
    @OneToMany(mappedBy = "indicator", fetch = FetchType.LAZY)
    private List<IndicatorValues> indicatorValues;
    @OneToOne(fetch = FetchType.EAGER)
    private Formula formula;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabelY() {
        return labelY;
    }

    public void setLabelY(String labelY) {
        this.labelY = labelY;
    }

    public String getLabelX() {
        return labelX;
    }

    public void setLabelX(String labelX) {
        this.labelX = labelX;
    }

    public Integer getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(Integer initialTime) {
        this.initialTime = initialTime;
    }

    public Integer getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(Integer endingTime) {
        this.endingTime = endingTime;
    }

    public String getNumeratorFormula() {
        return numeratorFormula;
    }

    public void setNumeratorFormula(String numeratorFormula) {
        this.numeratorFormula = numeratorFormula;
    }

    public List<IndicatorValues> getIndicatorValues() {
        if (indicatorValues == null) {
            indicatorValues = new ArrayList<IndicatorValues>();
        }
        return indicatorValues;
    }

    public void setIndicatorValues(List<IndicatorValues> indicatorValues) {
        this.indicatorValues = indicatorValues;
    }

    /**
     * @return the indicatorProductAndAreaTypes
     */
    public List<IndicatorProductAndAreaType> getIndicatorProductAndAreaTypes() {
        if (indicatorProductAndAreaTypes == null) {
            indicatorProductAndAreaTypes = new ArrayList<IndicatorProductAndAreaType>();
        }
        return indicatorProductAndAreaTypes;
    }

    /**
     * @param indicatorProductAndAreaTypes the indicatorProductAndAreaTypes to set
     */
    public void setIndicatorProductAndAreaTypes(List<IndicatorProductAndAreaType> indicatorProductAndAreaTypes) {
        this.indicatorProductAndAreaTypes = indicatorProductAndAreaTypes;
    }

    /**
     * @return the formula
     */
    public Formula getFormula() {
        return formula;
    }

    /**
     * @param formula the formula to set
     */
    public void setFormula(Formula formula) {
        this.formula = formula;
    }
}
