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
 * EvidenceProviderSendDTO.java
 *
 * Created on 29-05-2009, 04:49:05 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.dto;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class EvidenceProviderSendDTO implements Comparable<EvidenceProviderSendDTO> {

    private Integer id;
    private String description;
    private Integer left;
    private Integer right;
    private Integer top;
    private Integer bottom;
    private Boolean defaultEvidenceProvider;
    private Integer viewOrder;


    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return the left
     */
    public Integer getLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(Integer left) {
        this.left = left;
    }

    /**
     * @return the rigth
     */
    public Integer getRight() {
        return right;
    }

    /**
     * @param rigth the rigth to set
     */
    public void setRight(Integer right) {
        this.right = right;
    }

    /**
     * @return the top
     */
    public Integer getTop() {
        return top;
    }

    /**
     * @param top the top to set
     */
    public void setTop(Integer top) {
        this.top = top;
    }

    /**
     * @return the bottom
     */
    public Integer getBottom() {
        return bottom;
    }

    /**
     * @param bottom the bottom to set
     */
    public void setBottom(Integer bottom) {
        this.bottom = bottom;
    }

    public int compareTo(EvidenceProviderSendDTO o) {
        return this.getId() - o.getId();
    }

    /**
     * @return the defaultEvidenceProvider
     */
    public Boolean getDefaultEvidenceProvider() {
        return defaultEvidenceProvider;
    }

    /**
     * @param defaultEvidenceProvider the defaultEvidenceProvider to set
     */
    public void setDefaultEvidenceProvider(Boolean defaultEvidenceProvider) {
        this.defaultEvidenceProvider = defaultEvidenceProvider;
    }

    /**
     * @return the viewOrder
     */
    public Integer getViewOrder() {
        return viewOrder;
    }

    /**
     * @param viewOrder the viewOrder to set
     */
    public void setViewOrder(Integer viewOrder) {
        this.viewOrder = viewOrder;
    }
}
