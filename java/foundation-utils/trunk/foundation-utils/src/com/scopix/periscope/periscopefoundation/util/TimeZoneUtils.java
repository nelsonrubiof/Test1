/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.periscopefoundation.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class TimeZoneUtils {

    private static Logger log = Logger.getLogger(TimeZoneUtils.class);

    /**
     * Returns time difference between the server and a specific zone. </br>
     * 
     * I.e Using "America/Santiago" as reference:</br>
     * 		"Europe/London" = -4</br>
     * 		"America/Los_Angeles" = 5</br>
     */
    public static double getDiffInHoursTimeZone(String timeZoneId) {
        return getDiffHourTimezoneFromServer(timeZoneId);
    }
    
    /**
     * 
     * Returns time difference between the fromZoneId and toZoneId. </br>
     * 
     * Using from "America/Santiago" to "Europe/London" = -4</br>
     * Using from "America/Santiago" to "America/Los_Angeles" = 5</br>
     * Using from "America/Los_Angeles" to "America/Santiago" = -5</br>

     * @param fromZoneId
     * @param toZoneId
     * @return hours of difference between fromZoneId and toZoneId.
     */
    public static double getDiffInHoursTimeZone(String fromZoneId, String toZoneId) {
		long currentTime = System.currentTimeMillis();
		int fromOffset = TimeZone.getTimeZone(fromZoneId).getOffset(currentTime);
		int toOffset = TimeZone.getTimeZone(toZoneId).getOffset(currentTime);
		int hourDifference = (toOffset - fromOffset) / (1000 * 60 * 60);

		log.debug("diferencia en horas entre ["+fromZoneId+"], toZoneId: ["+toZoneId+"]: " + hourDifference);
		return hourDifference;
    }
    
    public static double getDiffHourTimezoneFromServer(String timeZoneId) {
    	return getDiffInHoursTimeZone(TimeZone.getDefault().getID(), timeZoneId);
    }
    
    public static double getDiffHourTimezoneToServer(String timeZoneId) {
    	return getDiffInHoursTimeZone(timeZoneId, TimeZone.getDefault().getID());
    }

    /**
     * Retorna el proximo dia de cambio horario segun la regla
     * dia de la semana, dia de inicio, posicion
     * ej Primer domingo de Marzo: 1, 01-03-2010, 1
     *    Primer domingo apartide del 9 de Marzo :1, 09-03-2010, 1
     */
    public static Date getNextScheduleChange(int dayOfWeek, Date dateInit, int posicion) {
        log.debug("[dayOfWeek:" + dayOfWeek + "][dateInit:" + DateFormatUtils.format(dateInit, "yyyy-MM-dd HH:mm:ss") + "]"
                + "[posicion:" + posicion + "]");
        Date ret = dateInit;
        Calendar c = Calendar.getInstance();
        c.setTime(ret);
        int pos = 0;
        while (posicion != pos) {
            c.add(Calendar.DATE, 1);
            if (c.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
                pos++;
            }
        }
        ret = c.getTime();
        log.debug("[ret:" + DateFormatUtils.format(ret, "yyyy-MM-dd HH:mm:ss") + "]");
        return ret;
    }
    
    /**
     * Gets the correct execution day of week.
     * 
     * @param executionTime Current execution time
     * @param timezoneDiff Timezone difference between executionTime and place to execute
     * @param dayOfWeek Current execution day of week
     * @return Execution day of execution
     * @throws NumberFormatException
     */
    public static Integer calculateDayOfWeek(String executionTime, double timezoneDiff, Integer dayOfWeek) throws NumberFormatException {
        try {
            Date dExecution = DateUtils.parseDate(executionTime, new String[]{"HH:mm:ss"});
            int hourExecution = Integer.parseInt(DateFormatUtils.format(dExecution, "HH"));
            if (timezoneDiff > 0) {
                if ((24 - timezoneDiff) <= hourExecution) {
                    dayOfWeek = dayOfWeek - 1;
                    if (dayOfWeek == 0) {
                        dayOfWeek = 7;
                    }
                }
            } else if (timezoneDiff < 0) {
                timezoneDiff = timezoneDiff * -1;
                if (timezoneDiff > hourExecution) {
                    dayOfWeek = dayOfWeek + 1;
                    if (dayOfWeek == 8) {
                        dayOfWeek = 1;
                    }
                }
            }

        } catch (ParseException e) {
            log.info(e);
        }
        return dayOfWeek;
    }

    /**
     * Gets the correct execution day of week.
     * 
     * @param executionTime Current execution time
     * @param timezoneDiff Timezone difference between executionTime and place to execute
     * @return Correct date of store
     * @throws NumberFormatException
     */
    public static Date calculateDayOfWeek(Date executionTime, double timezoneDiff) throws NumberFormatException {
    	
    	Date ret = new Date(executionTime.getTime());
    	
    	int hourExecution = Integer.parseInt(DateFormatUtils.format(executionTime, "HH"));
		if (timezoneDiff > 0) {
			if ((24 - timezoneDiff) <= hourExecution) {
				ret = DateUtils.addDays(ret, -1);
			}
		} else if (timezoneDiff < 0) {
			timezoneDiff = timezoneDiff * -1;
			if (timezoneDiff > hourExecution) {
				ret = DateUtils.addDays(ret, 1);
			}
		}
		
    	return ret;
    }
}
