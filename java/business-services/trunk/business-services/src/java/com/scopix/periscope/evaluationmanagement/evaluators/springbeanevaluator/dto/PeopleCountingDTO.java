/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator.dto;

import java.util.Date;

/**
 *
 * @author Cesar
 */
public class PeopleCountingDTO implements Comparable<PeopleCountingDTO> {

    private Date date;
    private Integer valueIn;
    private Integer valueOut;

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    public synchronized int compareTo(PeopleCountingDTO o) {
        return this.getDate().compareTo(o.getDate());
    }

    /**
     * @return the valueIn
     */
    public Integer getValueIn() {
        return valueIn;
    }

    /**
     * @param valueIn the valueIn to set
     */
    public void setValueIn(Integer valueIn) {
        this.valueIn = valueIn;
    }

    /**
     * @return the valueOut
     */
    public Integer getValueOut() {
        return valueOut;
    }

    /**
     * @param valueOut the valueOut to set
     */
    public void setValueOut(Integer valueOut) {
        this.valueOut = valueOut;
    }
}
