/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * SendExtractionPlanController.java
 *
 * Created on 30-06-2008, 08:49:10 PM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer.services.controllers;

import com.scopix.periscope.businesswarehouse.transfer.Transfer;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.view.XMLView;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author marko.perich
 */
@SpringBean(rootClass = TransferController.class)
public class TransferController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String endDate = request.getParameter("date");
            if (endDate != null) {

                //Valido formato de fecha
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                sdf.parse(endDate);
                
                SchedulerFactory sf = new StdSchedulerFactory();
                Scheduler sched = sf.getScheduler();
                if (!sched.isStarted()) {
                    sched.start();
                }
                JobDetail job = new JobDetail("TransferSimpleJob", "TransferGroup", Transfer.class);
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("END_DATE", endDate);
                job.setJobDataMap(jobDataMap);
                SimpleTrigger trigger = new SimpleTrigger("TransferSimpleTrigger", "TransferGroup");
                sched.scheduleJob(job, trigger);

            } else {
                throw new Exception();
            }
        } catch (Exception ex) {
            throw new Exception("The parameter 'date' is required, and the format is 'yyyyMMdd'");
        }
        return new ModelAndView(new XMLView());
    }
}
