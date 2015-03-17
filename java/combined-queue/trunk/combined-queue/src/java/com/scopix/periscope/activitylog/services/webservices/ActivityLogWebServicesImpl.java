/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  ActivityLogWebServicesImpl.java
 * 
 *  Created on Jun 11, 2014, 2:41:43 PM
 * 
 */
package com.scopix.periscope.activitylog.services.webservices;

import com.scopix.periscope.activitylog.management.ActivityLogManager;
import com.scopix.periscope.activitylog.services.webservices.dto.ActivityLogDTOContainer;
import com.scopix.periscope.activitylog.services.webservices.dto.OperatorUsersDTOContainer;
import com.scopix.periscope.activitylog.services.webservices.dto.RequestActivityLogDTO;
import com.scopix.periscope.activitylog.services.webservices.dto.UsersLastRequestDTO;
import com.scopix.periscope.activitylog.services.webservices.dto.UsersLastRequestDTOContainer;
import com.scopix.periscope.activitylog.services.webservices.dto.UsersOnQueueDTO;
import com.scopix.periscope.activitylog.services.webservices.dto.UsersOnQueueDTOContainer;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.SecurityManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;

/**
 *
 * @author Sebastian
 */
@WebService(endpointInterface = "com.scopix.periscope.activitylog.services.webservices.ActivityLogWebServices")
@SpringBean(rootClass = ActivityLogWebServices.class)
@Path("/")
public class ActivityLogWebServicesImpl implements ActivityLogWebServices {

    private ActivityLogManager activityLogManager;
    private SecurityManager securityManager;
    private static Logger log = Logger.getLogger(ActivityLogManager.class);
    private PropertiesConfiguration configuration;

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getUsersActivityLogStats/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ActivityLogDTOContainer getUsersActivityLogStats(@PathParam("sessionId") Long sessionId) throws ScopixWebServiceException {
        //   getSecurityManager().checkSecurity(sessionId, "COMBINED_QUEUE_ADMINISTRATOR");
        try {
            Date day = new Date();
            Date to = new Date(day.getTime());
            Integer secsAgo = getConfiguration().getInt("live.stats.time.period");
            Date from = getTimeNSecondsAgo(to, new Long(secsAgo));
            return getActivityLogManager().getLogDTO(java.util.TimeZone.getDefault().getID(),day, from, to);
        } catch (Exception ex) {
            log.error("An error ocurred processing request", ex);
            throw new ScopixWebServiceException("An error ocurred processing request", ex);
        }
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getUserActivityLog/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ActivityLogDTOContainer getUserActivityLog(RequestActivityLogDTO request, @PathParam("sessionId") Long sessionId) throws ScopixWebServiceException {
        //   getSecurityManager().checkSecurity(sessionId, "COMBINED_QUEUE_ADMINISTRATOR");
        try {
            log.info("Proccesing incoming request");
            DateFormat fullDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            DateFormat fullDayFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date day = fullDayFormat.parse(request.getDayDate());
            Date from = null;
            if (request.getFromTime() != null && !request.getFromTime().trim().isEmpty()) {
                from = fullDateFormat.parse(request.getDayDate().trim() + " " + request.getFromTime().trim());
            }
            Date to = null;
            if (request.getToTime() != null && !request.getToTime().trim().isEmpty()) {
                to = fullDateFormat.parse(request.getDayDate().trim() + " " + request.getToTime().trim());
            }
            if ("ALL-USERS".equals(request.getUserName())) {
                return getActivityLogManager().getLogDTO(request.getTimeZoneId(), day, from, to);
            }
            return getActivityLogManager().getLogDTOForUser(request.getUserName().trim(), request.getTimeZoneId(), day, from, to);
        } catch (Exception ex) {
            log.error("An error ocurred processing request", ex);
            throw new ScopixWebServiceException("An error ocurred processing request", ex);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getOperatorUsersList/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public OperatorUsersDTOContainer getOperatorUsersList(@PathParam("sessionId") Long sessionId) throws ScopixWebServiceException {
        //   getSecurityManager().checkSecurity(sessionId, "COMBINED_QUEUE_ADMINISTRATOR");
        List<String> operators = getActivityLogManager().getOperatorsUsersInLog();
        OperatorUsersDTOContainer operatorUsersDTOContainer = new OperatorUsersDTOContainer();
        operatorUsersDTOContainer.setOperatorUsersDTO(operators);
        return operatorUsersDTOContainer;
    }

    @POST
    @Override
    @Path("/getUsersOnQueues/{secondsAgo}/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public UsersOnQueueDTOContainer getUsersOnQueues(@PathParam("secondsAgo") Long secondsAgo, @PathParam("sessionId") Long sessionId) throws ScopixWebServiceException {
        try {
            UsersOnQueueDTOContainer usersOnQueueDTOContainer = new UsersOnQueueDTOContainer();
            List<UsersOnQueueDTO> result = getActivityLogManager().getActiveUsersOnQueues(secondsAgo);
            usersOnQueueDTOContainer.setUsersOnQueues(result);
            return usersOnQueueDTOContainer;
        } catch (Exception ex) {
            throw new ScopixWebServiceException(ex);
        }
    }

    @POST
    @Override
    @Path("/getUsersLastRequest/{timeZoneId}/{secondsAgo}/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public UsersLastRequestDTOContainer getUsersLastRequest(@PathParam("timeZoneId") String timeZoneId, @PathParam("secondsAgo") Long secondsAgo, @PathParam("sessionId") Long sessionId) throws ScopixWebServiceException {
        try {
            timeZoneId = timeZoneId.replaceAll("-", "/");
            UsersLastRequestDTOContainer usersLastRequestDTOContainer = new UsersLastRequestDTOContainer();
            List<UsersLastRequestDTO> result = getActivityLogManager().getUsersLastRequest(timeZoneId, secondsAgo);
            usersLastRequestDTOContainer.setUsersLastRequest(result);
            return usersLastRequestDTOContainer;
        } catch (Exception ex) {
            throw new ScopixWebServiceException(ex);
        }
    }

    /**
     * @return the activityLogManager
     */
    public ActivityLogManager getActivityLogManager() {
        if (activityLogManager == null) {
            activityLogManager = SpringSupport.getInstance().findBeanByClassName(ActivityLogManager.class);
        }
        return activityLogManager;
    }

    /**
     * @param activityLogManager the activityLogManager to set
     */
    public void setActivityLogManager(ActivityLogManager activityLogManager) {
        this.activityLogManager = activityLogManager;
    }

    /**
     * @return the securityManager
     */
    public SecurityManager getSecurityManager() {
        if (securityManager == null) {
            securityManager = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        }
        return securityManager;
    }

    /**
     * @param securityManager the securityManager to set
     */
    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    /**
     * Returns a date N seconds ago
     *
     * @param date
     * @param seconds
     * @return Date
     */
    private static Date getTimeNSecondsAgo(Date date, Long seconds) {
        Long dateLong = date.getTime() - (seconds * 1000);
        Date dateBefore = new Date(dateLong);
        return dateBefore;
    }

    private void loadProperties() {
        try {
            configuration = new PropertiesConfiguration();
            configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
            //levamtamos todos los properties relacionados que nos interesan
            configuration.load("system.properties");
        } catch (ConfigurationException e) {
            log.error("ConfigurationException " + e, e);
        }
    }

    /**
     *
     * @return
     */
    public PropertiesConfiguration getConfiguration() {
        if (configuration == null) {
            loadProperties();
        }
        return configuration;
    }

    /**
     *
     * @param configuration
     */
    public void setConfiguration(PropertiesConfiguration configuration) {
        this.configuration = configuration;
    }
}
