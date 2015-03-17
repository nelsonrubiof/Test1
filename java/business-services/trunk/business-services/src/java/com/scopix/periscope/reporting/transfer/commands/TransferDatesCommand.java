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
 *  TransferDatesCommand.java
 * 
 *  Created on 07-02-2011, 06:19:49 PM
 * 
 */
package com.scopix.periscope.reporting.transfer.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;
import com.scopix.periscope.reporting.UploadProcess;
import com.scopix.periscope.reporting.transfer.dao.ReportingUploadDao;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class TransferDatesCommand {

    private static Logger log = Logger.getLogger(TransferDatesCommand.class);
    private ReportingUploadDao dao;

    public void execute(UploadProcess process, LinkedHashMap<Integer, String> initYears) throws ScopixException {

        try {
            String corporateLocale = SystemConfig.getStringParameter("CORPORATE_LOCALE");
            Locale locale = new Locale(corporateLocale);
            String pivotDate = SystemConfig.getStringParameter("PIVOT_DATE");

            Calendar calIterator = Calendar.getInstance(locale);
            if (initYears.get(calIterator.get(Calendar.YEAR)) == null) {
                log.error("Se debe inicializar el año en calendar_init_dates");
                throw new ScopixException("Se debe inicializar el año en calendar_init_dates");
            }

            if (!initYears.isEmpty()) {
                Calendar calStartDate = Calendar.getInstance();

                Date startDate = null;
                //obteniendo la ultima fecha del traspaso
                Date lastDate = getDao().getLastDate();

                //si la ultima fecha de traspaso es nula, significa que no hay datos en la BD, por lo tanto la fecha de inicio
                //sera la fecha de inicio del primer año obtenido desde calendar_init_dates.
                //caso contrario, la fecha de inicio de traspaso sera el dia siguiente a la fecha de ultimo traspaso
                if (lastDate == null) {
                    startDate = DateUtils.parseDate(initYears.values().iterator().next(), new String[]{"dd-MM-yyyy"});
                } else {
                    calStartDate.setTime(lastDate);
                    calStartDate.add(Calendar.DATE, 1);
                    startDate = calStartDate.getTime();
                }
                log.debug("startDate: " + startDate);
                String[] we = SystemConfig.getStringParameter("WEEK_END_DAYS").split(",");
                ArrayList<Integer> weekEnds = new ArrayList<Integer>();
                for (String temp : we) {
                    weekEnds.add(Integer.valueOf(temp.trim()));
                }

                // seteando los datos iniciales
                calIterator.setTime(DateUtils.parseDate(pivotDate, new String[]{"yyyy-MM-dd"}));

                getDao().transferDates(process, locale, initYears, calIterator, startDate, weekEnds,
                        SystemConfig.getStringParameter("WEEK_NAME"));
            }
        } catch (ParseException e) {
            log.error(e, e);
            throw new ScopixException(e);
        } catch (NumberFormatException e) {
            log.error(e, e);
            throw new ScopixException(e);
        }


    }

    public ReportingUploadDao getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ReportingUploadDao.class);
        }
        return dao;
    }

    public void setDao(ReportingUploadDao dao) {
        this.dao = dao;
    }
}
