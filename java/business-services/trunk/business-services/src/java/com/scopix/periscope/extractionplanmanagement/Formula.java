/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.Indicator;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author Cesar Abarza
 */
@Entity
public class Formula extends BusinessObject implements Comparable<Formula> {

    @Lob
    private String formula;
    @Lob
    private String denominator;
    @Lob
    private String description;
    @Enumerated(EnumType.STRING)
    private FormulaTypeEnum type;
    @Enumerated(EnumType.STRING)
    private CompliantTypeEnum compliantType;
    @ManyToMany(targetEntity = SituationTemplate.class, fetch = FetchType.LAZY,
    cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "rel_formula_situation_template", joinColumns = {
        @JoinColumn(name =
        "formula_id")}, inverseJoinColumns = {
        @JoinColumn(name = "situation_template_id")})
    private List<SituationTemplate> situationTemplates;
    @ManyToMany(targetEntity = Store.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
        CascadeType.MERGE})
    @JoinTable(name = "rel_formula_store", joinColumns = {
        @JoinColumn(name = "formula_id")}, inverseJoinColumns = {
        @JoinColumn(name = "store_id")})
    private List<Store> stores;
    @Lob
    private String variables;
    @Lob
    private String observations;
    @OneToOne(targetEntity = Indicator.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "formula",
    fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Indicator indicator;
    private Double target;
    private Double standard;
    private static Comparator<Formula> comparatorByDescription;

    public int compareTo(Formula o) {
        return this.getId() - o.getId();
    }

    public static Comparator<Formula> getComparatorByDescription() {
        comparatorByDescription = new Comparator<Formula>() {

            public int compare(Formula o1, Formula o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        };

        return comparatorByDescription;
    }

    /**
     * @return the formula
     */
    public String getFormula() {
        return formula;
    }

    /**
     * @param formula the formula to set
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the type
     */
    public FormulaTypeEnum getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(FormulaTypeEnum type) {
        this.type = type;
    }

    /**
     * @return the compliantType
     */
    public CompliantTypeEnum getCompliantType() {
        return compliantType;
    }

    /**
     * @param compliantType the compliantType to set
     */
    public void setCompliantType(CompliantTypeEnum compliantType) {
        this.compliantType = compliantType;
    }

    /**
     * @return the situationTemplates
     */
    public List<SituationTemplate> getSituationTemplates() {
        if (situationTemplates == null) {
            situationTemplates = new ArrayList<SituationTemplate>();
        }
        return situationTemplates;
    }

    /**
     * @param situationTemplates the situationTemplates to set
     */
    public void setSituationTemplates(List<SituationTemplate> situationTemplates) {
        this.situationTemplates = situationTemplates;
    }

    /**
     * @return the stores
     */
    public List<Store> getStores() {
        if (stores == null) {
            stores = new ArrayList<Store>();
        }
        return stores;
    }

    /**
     * @param stores the stores to set
     */
    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    /**
     * @return the variables
     */
    public String getVariables() {
        return variables;
    }

    /**
     * @param variables the variables to set
     */
    public void setVariables(String variables) {
        this.variables = variables;
    }

    /**
     * @return the observations
     */
    public String getObservations() {
        return observations;
    }

    /**
     * @param observations the observations to set
     */
    public void setObservations(String observations) {
        this.observations = observations;
    }

//    /**
//     * @return the situationTemplateStoreRelations
//     */
//    public List<SituationTemplateStoreRelation> getSituationTemplateStoreRelations() {
//        if (situationTemplateStoreRelations == null) {
//            situationTemplateStoreRelations = new ArrayList<SituationTemplateStoreRelation>();
//        }
//        return situationTemplateStoreRelations;
//    }
//
//    /**
//     * @param situationTemplateStoreRelations the situationTemplateStoreRelations to set
//     */
//    public void setSituationTemplateStoreRelations(List<SituationTemplateStoreRelation> situationTemplateStoreRelations) {
//        this.situationTemplateStoreRelations = situationTemplateStoreRelations;
//    }
    /**
     * @return the denominator
     */
    public String getDenominator() {
        return denominator;
    }

    /**
     * @param denominator the denominator to set
     */
    public void setDenominator(String denominator) {
        this.denominator = denominator;
    }

    /**
     * @return the target
     */
    public Double getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(Double target) {
        this.target = target;
    }

    /**
     * @return the standard
     */
    public Double getStandard() {
        return standard;
    }

    /**
     * @param standard the standard to set
     */
    public void setStandard(Double standard) {
        this.standard = standard;
    }

    /**
     * @return the indicator
     */
    public Indicator getIndicator() {
        return indicator;
    }

    /**
     * @param indicator the indicator to set
     */
    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }
}
