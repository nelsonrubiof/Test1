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
 * TransferDatesCommand.java
 *
 * Created on 07-11-2012, 04:38:35 PM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferBWHibernateDAO;
import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class TransferDatesCommand {

    private static final String START = "start";
    private static final String END = "end";
    private Logger log = Logger.getLogger(TransferDatesCommand.class);
    private TransferHibernateDAO dao;
    private TransferBWHibernateDAO daoBW;

    /**
     * Método encargado de transferir las fechas a BW.
     * 
     * @param endDate
     * @param corporateLocale
     * @param pivotDate
     * @throws PeriscopeException 
     */
    public void execute(Date endDate, String corporateLocale, String pivotDate, String[] weekEndDays, String weekName, 
            Integer firstDayOfWeek, String weekEndDesc, String weekDayDesc) throws ScopixException {
        log.info(START);
        try {
            List<String> sqls = new ArrayList<String>();

            Locale locale = new Locale(corporateLocale);

            LinkedHashMap<Integer, String> initYears = getDao().getCalendarInitDates();

            //CHECKSTYLE:OFF
            String frm_yyyyMMdd = "yyyyMMdd";
            String frm_MMM_yyyy = "MMM yyyy";
            String frm_yyyy_MM_dd = "yyyy-MM-dd";
            String frm_dd_MM_yyyy = "dd-MM-yyyy";
            String frm_EEEE = "EEEE";
//            String frm_ddMM = "ddMM";
            //CHECKSTYLE:ON

            Calendar calIterator = Calendar.getInstance(locale);

            if (initYears.get(calIterator.get(Calendar.YEAR)) == null) {
                log.error("Se debe inicializar el año en calendar_init_dates");
                throw new ScopixException("Se debe inicializar el año en calendar_init_dates");
            }

            if (!initYears.isEmpty()) {
                Calendar calStartDate = Calendar.getInstance();

                Date startDate = null;
                //obteniendo la ultima fecha del traspaso
                Date lastDate = getDaoBW().getLastDate();

                //si la ultima fecha de traspaso es nula, significa que no hay datos en la BD, por lo tanto la fecha de inicio
                //sera la fecha de inicio del primer año obtenido desde calendar_init_dates.
                //caso contrario, la fecha de inicio de traspaso sera el dia siguiente a la fecha de ultimo traspaso
                if (lastDate == null) {
                    startDate = DateUtils.parseDate(initYears.values().iterator().next(), new String[]{frm_dd_MM_yyyy});
                } else {
                    calStartDate.setTime(lastDate);
                    calStartDate.add(Calendar.DATE, 1);
                    startDate = calStartDate.getTime();
                }
                log.debug("startDate: " + startDate.toString());

                SimpleDateFormat sdfYearMonthDay = new SimpleDateFormat(frm_yyyy_MM_dd);
                SimpleDateFormat sdfDayOfWeek = new SimpleDateFormat(frm_EEEE, locale);

                Iterator<Integer> years = initYears.keySet().iterator();
                Integer thisYear = years.next();

                ArrayList<Integer> weekEnds = new ArrayList<Integer>();
                for (String temp : weekEndDays) {
                    weekEnds.add(Integer.valueOf(temp.trim()));
                }

                // seteando los datos iniciales
                calIterator.setTime(sdfYearMonthDay.parse(pivotDate));
                Integer weekOfYear = 1;
                int weekCounterHelper = 1;
                boolean startYear = true;
                Calendar calDummy = null;

                while (Integer.valueOf(DateFormatUtils.format(calIterator.getTime(), frm_yyyyMMdd)).intValue()
                        <= Integer.valueOf(DateFormatUtils.format(endDate, frm_yyyyMMdd)).intValue()) {

                    // validando si estan en el mismo año de acuerdo a los
                    // inicios de años configurados
                    String nextYear = initYears.get(thisYear + 1);
                    if (nextYear != null
                            && DateFormatUtils.format(calIterator.getTime(), frm_dd_MM_yyyy).equalsIgnoreCase(nextYear)) {
                        thisYear = years.next();
                        weekOfYear = 1;
                        startYear = true;
                    }

                    // calendario usado para los QUARTER
                    if (startYear) {
                        calDummy = Calendar.getInstance(locale);
                        calDummy.set(thisYear, 0, 1);
                        startYear = false;
                    }

                    if (Integer.valueOf(DateFormatUtils.format(startDate, frm_yyyyMMdd)).intValue()
                            <= Integer.valueOf(DateFormatUtils.format(calIterator.getTime(), frm_yyyyMMdd)).intValue()) {

                        String id = DateFormatUtils.format(calIterator.getTime(), frm_yyyyMMdd);
                        String month = DateFormatUtils.format(calIterator.getTime(), frm_MMM_yyyy);
                        String day = "to_timestamp('" + id + "', 'YYYYMMDD')";
                        String week = weekName + " " + weekOfYear + " " + thisYear;
                        int weekOrd = (thisYear * 100) + weekOfYear;
                        int monthOrd = (calIterator.get(Calendar.YEAR) * 100) + (calIterator.get(Calendar.MONTH) + 1);
                        int quarterOrd = (thisYear * 100) + this.getQuarter(calDummy);
                        String dayOfWeek = sdfDayOfWeek.format(calIterator.getTime());
                        // define el dia inicial de la semana (de 1 a 7), siendo
                        // 1 para el primero y 7 para el ultimo.
                        // por ejemplo, para el caso Cabelas el dia 1
                        // corresponde al domingo y en el caso de Lowes es el
                        // sábado
                        int dayOfWeekOrd = ((calIterator.get(Calendar.DAY_OF_WEEK) + 7
                                - firstDayOfWeek) % 7) + 1;
                        
                        // define si es dia de semana o fin de semana de acuerdo
                        // al primer dia de la semana definido previamente
                        String weekDay = null;
                        if (weekEnds.contains(dayOfWeekOrd)) {
                            weekDay = weekEndDesc;
                        } else {
                            weekDay = weekDayDesc;
                        }

                        String dayString = sdfYearMonthDay.format(calIterator.getTime());

                        StringBuilder sql = new StringBuilder();

                        sql.append("INSERT INTO date_hierarchy (id, year, month, week, day, quarter_ord, quarter, month_ord, ");
                        sql.append("week_ord, day_of_week, day_of_week_ord, week_day, ");
                        sql.append("day_string) ");
                        sql.append("VALUES (");
                        sql.append(id).append(", ");
                        sql.append(thisYear).append(", ");
                        sql.append("'").append(month).append("', ");
                        sql.append("'").append(week).append("', ");
                        sql.append(day).append(", ");
                        sql.append(quarterOrd).append(", ");
                        sql.append("'").append("Q").append(this.getQuarter(calDummy)).append(" ").append(thisYear).append("', ");
                        sql.append(monthOrd).append(", ");
                        sql.append(weekOrd).append(", ");
                        sql.append("'").append(dayOfWeek).append("', ");
                        sql.append(dayOfWeekOrd).append(", ");
//                        sql.append("'").append(specialDay).append("', ");
//                        sql.append(specialDayOrd).append(", ");
                        sql.append("'").append(weekDay).append("', ");
                        sql.append("'").append(dayString).append("')");
                        sqls.add(sql.toString());
                    } // Contador de semanas. Suma una semana cada 7 dias
                    weekCounterHelper++;
                    if (weekCounterHelper > 7) {
                        weekCounterHelper = 1;
                        weekOfYear++;
                    }

                    calIterator.add(Calendar.DATE, 1);
                    calDummy.add(Calendar.DATE, 1);

                    if (calDummy.get(Calendar.YEAR) > thisYear && weekOfYear > 3) {
                        // dias de holgura para mantener los nombres de los meses como corresponde (de acuerdo a la fecha)
                        calDummy.add(Calendar.DATE, -20);
                    }
                }
                getDaoBW().executeSQL(sqls);
            }

        } catch (ParseException ex) {
            log.error("ParseException = " + ex.getMessage());
            throw new ScopixException(ex);
        }
        log.info(END);
    }

    private int getQuarter(Calendar cal) {
        int[] months = {3, 6, 9, 12};
        int month = cal.get(Calendar.MONTH) + 1;
        for (int i = 0; i
                < months.length; i++) {
            if (i == 0) {
                if (month > 0 && month <= months[i]) {
                    return i + 1;
                }
            } else if (month > months[i - 1] && month <= months[i]) {
                return i + 1;
            }

        }
        return 0;
    }

    public TransferHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(TransferHibernateDAO.class);
        }
        return dao;
    }

    public void setDao(TransferHibernateDAO dao) {
        this.dao = dao;
    }

    public TransferBWHibernateDAO getDaoBW() {
        if (daoBW == null) {
            daoBW = SpringSupport.getInstance().findBeanByClassName(TransferBWHibernateDAO.class);
        }
        return daoBW;
    }

    public void setDaoBW(TransferBWHibernateDAO daoBW) {
        this.daoBW = daoBW;
    }
}