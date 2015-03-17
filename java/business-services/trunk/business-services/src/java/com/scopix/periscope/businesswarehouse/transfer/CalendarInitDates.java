/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * CalendarInitDates.java
 *
 * Created on 31-08-2010, 12:54:19 PM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import javax.persistence.Entity;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class CalendarInitDates extends BusinessObject {

    /**
     * Year
     */
    private Integer yearCalendar;
    
    /**
     * Start date for the year
     */
    private String initDate;

    public Integer getYearCalendar() {
        return yearCalendar;
    }

    public void setYearCalendar(Integer yearCalendar) {
        this.yearCalendar = yearCalendar;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }
}
