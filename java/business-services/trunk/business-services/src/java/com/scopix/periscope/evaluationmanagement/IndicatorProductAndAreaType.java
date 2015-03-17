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

import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.templatemanagement.Product;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class IndicatorProductAndAreaType extends BusinessObject {

    @ManyToOne
    private Indicator indicator;

    @ManyToOne
    private AreaType areaType;

    @ManyToOne
    private Product product;

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

    /**
     * @return the areaType
     */
    public AreaType getAreaType() {
        return areaType;
    }

    /**
     * @param areaType the areaType to set
     */
    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    
}
