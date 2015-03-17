/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  ScopixUtilities.java
 * 
 *  Created on 03-01-2014, 10:20:29 AM
 * 
 */
package com.scopix.periscope.utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceProvider;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;

import java.util.Map;

import org.apache.commons.lang.WordUtils;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
public class ScopixUtilities {

    private static Logger log = Logger.getLogger(ScopixUtilities.class);

    public static Class<?> findClassByName(String name, String searchPackages) throws ScopixException {
        Class c = null;
        try {
            c = Class.forName(searchPackages + "." + name);
        } catch (ClassNotFoundException e) {
            throw new ScopixException("Class no encontrada " + searchPackages + "." + name, e);
        }
        return c;
    }

    /**
     *
     * @param simpleClassName
     * @return
     */
    public static EvidenceExtractionRequest findEvidenceExtractionRequestByClassName(String simpleClassName) throws ScopixException {
        log.info("start [simpleClassName:" + simpleClassName + "]");
        EvidenceExtractionRequest eer = null;

        try {
            Class c = findClassByName(simpleClassName, "com.scopix.periscope.extractionmanagement");
            eer = (EvidenceExtractionRequest) c.newInstance();
        } catch (InstantiationException e) {
            log.error(e, e);
        } catch (IllegalAccessException e) {
            log.error(e, e);
        }
        log.info("end [eer:" + eer + "]");
        return eer;

    }

    /**
     *
     * @param simpleClassName
     * @return
     */
    public static EvidenceProvider findEvidenceProviderByClassName(String simpleClassName) throws ScopixException {
        log.info("start [simpleClassName:" + simpleClassName + "]");
        EvidenceProvider ep = null;

        try {
            Class c = findClassByName(simpleClassName, "com.scopix.periscope.extractionmanagement");
            ep = (EvidenceProvider) c.newInstance();
        } catch (InstantiationException e) {
            log.error(e, e);
        } catch (IllegalAccessException e) {
            log.error(e, e);
        }
        log.info("end [eer:" + ep + "]"); 
        return ep;
    }

    /**
     *
     * @param obj
     * @param value
     * @param field
     */
    public static void setterInObject(Object obj, Object value, String field) {
        try {
            //Field f = obj.getClass().getField(field);
            String set = "set" + StringUtils.capitalize(field);
            Method setMethod = findMethod(obj.getClass(), set);
            if (setMethod != null) {
                setMethod.invoke(obj, value);
            }
        } catch (SecurityException e) {
            log.info(obj.getClass().getSimpleName() + " " + field + " " + e);
        } catch (IllegalAccessException e) {
            log.info(obj.getClass().getSimpleName() + " " + field + " " + e);
        } catch (IllegalArgumentException e) {
            log.info(obj.getClass().getSimpleName() + " " + field + " " + e);
        } catch (InvocationTargetException e) {
            log.info(obj.getClass().getSimpleName() + " " + field + " " + e);
        } catch (RuntimeException e) {
            log.info(obj.getClass().getSimpleName() + " " + field + " " + e);
        }
    }

    /**
     * Busca un metodo dentro de una clase
     *
     * @param clazz Class donde buscar el metodo
     * @param methodName nombre de metodo soliciatdo
     * @return Method metodo solicitado
     */
    public static Method findMethod(Class<?> clazz, String methodName) {
        //log.info("start [clazz:" + clazz + "][methodName:" + methodName + "]");
        Method ret = null;
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(methodName)) {
                ret = method;
                break;
            }
        }
        //log.info("end [ret:" + ret + "]");
        return ret;
    }

    /**
     *
     * @param fecha1
     * @param fecha2
     * @return
     */
    public static double diffDateInMin(Date fecha1, Date fecha2) {
        Date fMayor;
        Date fMenor;
        if (fecha1.compareTo(fecha2) > 0) {
            fMayor = fecha1;
            fMenor = fecha2;
        } else {
            fMayor = fecha2;
            fMenor = fecha1;
        }
        double diff = (fMayor.getTime() - fMenor.getTime()) / (1000 * 60);

        return diff;
    }

    /**
     *
     *
     * @param from must be complete date
     * @param to must be complete date
     * @return random date between "from" and "to"
     */
    public static Date getRandomDateBetween(Date from, Date to) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(from);
        BigDecimal decFrom = new BigDecimal(cal.getTimeInMillis());

        cal.setTime(to);
        BigDecimal decTo = new BigDecimal(cal.getTimeInMillis());

        BigDecimal selisih = decTo.subtract(decFrom);
        BigDecimal factor = selisih.multiply(new BigDecimal(Math.random()));
        cal.setTimeInMillis((factor.add(decFrom)).longValue());
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     *
     * @param horaTomaMuestra
     * @param timeZoneId
     * @param dayOfWeekRequest
     * @return Date in store from TimeZoneId
     */
    public static Date calculateDateByStore(Date horaTomaMuestra, String timeZoneId, Integer dayOfWeekRequest) {
        Calendar calHoraEvidencia = Calendar.getInstance();
        Calendar calFechaHoraSystem = Calendar.getInstance();

        Date extractionStartTime = horaTomaMuestra;
        calHoraEvidencia.setTime(extractionStartTime);
        int dayOfWeek = calHoraEvidencia.get(Calendar.DAY_OF_WEEK);
        log.debug("[extractionStartTime:" + extractionStartTime + "]");
        if (timeZoneId != null && timeZoneId.length() > 0) {
            //recuperamos la diferencia en hrs entre el timeZone del Store y el TimeZone del Server
            double d = TimeZoneUtils.getDiffInHoursTimeZone(timeZoneId);
            log.debug("[timeZoneId:" + timeZoneId + "][difHours:" + d + "]");
            extractionStartTime = DateUtils.addHours(extractionStartTime, (int) d);
        }
        calHoraEvidencia.setTime(extractionStartTime);
        log.debug("[calHoraEvidencia:" + calHoraEvidencia.getTime() + "]");
        int difDayOfWeek = 0;
        if (calHoraEvidencia.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
            difDayOfWeek = calHoraEvidencia.get(Calendar.DAY_OF_WEEK) - dayOfWeek;
        }
        calFechaHoraSystem.setTimeInMillis(System.currentTimeMillis());
        log.debug("[calFechaHoraSystem:" + calFechaHoraSystem.getTime() + "]");
        calFechaHoraSystem.set(Calendar.HOUR_OF_DAY, calHoraEvidencia.get(Calendar.HOUR_OF_DAY));
        calFechaHoraSystem.set(Calendar.MINUTE, calHoraEvidencia.get(Calendar.MINUTE));
        //recuperamos el dayOfWeek en el cual se debe ejecutar el job
        calFechaHoraSystem.set(Calendar.SECOND, 0);
        calFechaHoraSystem.set(Calendar.MILLISECOND, 0);
        calFechaHoraSystem.set(Calendar.DAY_OF_WEEK, dayOfWeekRequest + difDayOfWeek);
        log.info("end [calFechaHoraSystem:" + calFechaHoraSystem.getTime() + "]");
        return calFechaHoraSystem.getTime();
    }

    public static void setterParam(Object obj, Map<String, Object> row, String field) {
        char[] replace = new char[]{'_'};
        setterInObject(obj, row.get(field), StringUtils.replace(WordUtils.capitalizeFully(field, replace), "_", ""));
    }
    
    /**
     * Calcula la fecha de evidencia de acuerdo al tipo de generación
     * 
     * @param extractionPlanPastDate
     * @param evidenceExtractionRequest
     * @return
     * @throws ParseException
     */
    public static Date calculateEvidenceDate(Date extractionPlanPastDate, 
    		EvidenceExtractionRequest evidenceExtractionRequest) throws ParseException {
    	log.info("start, extractionPlanPastDate: [" + extractionPlanPastDate + "], "
    			+ "evidenceExtractionRequest id: ["+evidenceExtractionRequest.getId()+"], "
    					+ "evidenceExtractionRequest.getType(): ["+evidenceExtractionRequest.getType()+"]");

        Calendar dateEjecutionServer = null;
        Calendar evidenceDate = Calendar.getInstance();
        
        if (extractionPlanPastDate != null) { //EP al pasado
        	dateEjecutionServer = Calendar.getInstance();
            dateEjecutionServer.setTime(extractionPlanPastDate);
            
        }else if (EvidenceRequestType.AUTO_GENERATED.equals(evidenceExtractionRequest.getType()) //BEE o inyección de evidencias
                && evidenceExtractionRequest.getCreationTimestamp() != null) {
        	//se utiliza evidenceDate que viene en el autogenerado
        	dateEjecutionServer = Calendar.getInstance();
        	log.debug("evidenceExtractionRequest.getEvidenceDate(): ["+evidenceExtractionRequest.getEvidenceDate()+"]");
            dateEjecutionServer.setTime(evidenceExtractionRequest.getEvidenceDate());	
        }
        
        log.debug("dateEjecutionServer: [" + dateEjecutionServer + "]");
        if(dateEjecutionServer==null){
        	dateEjecutionServer = Calendar.getInstance();
            evidenceDate.setTimeInMillis(dateEjecutionServer.getTimeInMillis());

            if (evidenceExtractionRequest.getExtractionPlan().getTimeZoneId() != null
                    && evidenceExtractionRequest.getExtractionPlan().getTimeZoneId().length() > 0) {
                dateEjecutionServer.setTimeZone(TimeZone.getTimeZone(evidenceExtractionRequest.getExtractionPlan().getTimeZoneId()));

                double d = TimeZoneUtils.getDiffInHoursTimeZone(evidenceExtractionRequest.getExtractionPlan().getTimeZoneId());
                //Ajusta fecha(día) acorde a diferencia horaria con timezone del store
                Date newDateOfDiffHours = new Date(dateEjecutionServer.getTimeInMillis());
                newDateOfDiffHours = DateUtils.addHours(newDateOfDiffHours, (int) d * -1);
                evidenceDate.setTimeInMillis(newDateOfDiffHours.getTime());
                //Configura la hora en un día en particular dado el eer
                configureDate(evidenceDate, evidenceExtractionRequest);
            }
        }else{
        	//Es un EP al pasado o un autogenerado, se conserva la fecha del evidenceExtractionRequest o EP solicitado
        	evidenceDate.setTimeInMillis(dateEjecutionServer.getTimeInMillis());
        }

    	log.info("end, evidenceDate: ["+evidenceDate.getTime()+"]");
    	return evidenceDate.getTime();
    }
    
    /**
     * Configura la hora en un día en particular dado el eer
     * 
     * @param dia
     * @param evidenceRequest
     * @throws ParseException
     */
    private static void configureDate(Calendar dia, EvidenceExtractionRequest evidenceRequest) throws ParseException {
    	log.info("start, dia antes de ajustar hora: ["+dia.getTime()+"], evidenceRequest id: ["+evidenceRequest+"]");
        dia.set(Calendar.HOUR_OF_DAY, 0);
        dia.set(Calendar.MINUTE, 0);
        dia.set(Calendar.SECOND, 0);
        dia.set(Calendar.MILLISECOND, 0);
        log.debug("dia " + dia.getTime());
        Date requestedTime = DateUtils.parseDate(
                DateFormatUtils.format(evidenceRequest.getRequestedTime(), "HH:mm:ss"), new String[]{"HH:mm:ss"});
        Calendar hora = Calendar.getInstance();
        hora.setTime(requestedTime);
        log.debug("hora " + hora.getTime());
        dia.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
        dia.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));
        dia.set(Calendar.SECOND, hora.get(Calendar.SECOND));
        log.info("end, dia despues de ajustar hora: ["+dia.getTime()+"], evidenceRequest id: ["+evidenceRequest+"]");
    }
}