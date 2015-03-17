/*
 * 
 * Copyright 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 * 
 */
package com.scopix.periscope.periscopefoundation.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * This class provides some Date related helper methods.
 *
 * @author maximiliano.vazquez
 * @version 1.0.0
 *
 */
public abstract class DateUtils {

    /**
     * Creates a new Date based on specified day, month and year
     *
     * @param year Año
     * @param month Mes debe ser -1
     * @param day Dia
     * @return Date para datos soliciatdos
     */
    public static Date encodeDate(Integer year, Integer month, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * retornna now
     *
     * @return fecha actual en servidor
     */
    public static Date now() {
        return new Date();
    }

    /**
     *
     * @return retorna dia actual
     */
    public static Date today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();

    }

//    protected DateUtils() {
//    }
}
