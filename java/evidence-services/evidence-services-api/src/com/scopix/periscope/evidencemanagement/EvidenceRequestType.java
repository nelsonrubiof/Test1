/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement;

/**
 * This enum represent the type of evidence request. SCHEDULED represent all evidence request that are scheduled, this are created
 * when the extraction plan is sent. ON_DEMAND represent all evidence request that be solicited especifically and not be created
 * when the extraction plan is sent. AUTO_GENERATED represent all evidence request that is generated automatically for a kind of
 * alert, for example, motion detect.
 *
 * @author Cesar Abarza Suazo.
 * @version 1.0.0
 */
public enum EvidenceRequestType {

    SCHEDULED,
    ON_DEMAND,
    AUTO_GENERATED,
    REAL_RANDOM;

    /**
     *
     * @return String para el Enum seleccionado
     */
    public String getName() {
        return this.name();
    }
}
