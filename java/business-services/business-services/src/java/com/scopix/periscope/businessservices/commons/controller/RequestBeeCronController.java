/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.businessservices.commons.controller;

import com.scopix.periscope.evaluationmanagement.EvaluationManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.view.XMLView;
import java.text.ParseException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author Nelson
 */
@SpringBean(rootClass = RequestBeeCronController.class)
public class RequestBeeCronController extends AbstractController {

    private static final Logger log = Logger.getLogger(RequestBeeCronController.class);

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("start");
        EvaluationManager manager = SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class);
        Date date;
        try {
            String dateString = request.getParameter("date");
            log.debug(dateString);
            date = DateUtils.parseDate(dateString, new String[]{"yyyyMMdd"});
        } catch (ParseException parseException) {
            log.error(parseException);
            date = null;
        }
        manager.automaticBee(date);
        log.info("end");
        return new ModelAndView(new XMLView());
    }

}
