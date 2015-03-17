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
 *  ActivityLogWebServices.java
 * 
 *  Created on Jun 11, 2014, 12:34:42 PM
 * 
 */
package com.scopix.periscope.activitylog.services.webservices;

import com.scopix.periscope.activitylog.services.webservices.dto.ActivityLogDTOContainer;
import com.scopix.periscope.activitylog.services.webservices.dto.OperatorUsersDTOContainer;
import com.scopix.periscope.activitylog.services.webservices.dto.RequestActivityLogDTO;
import com.scopix.periscope.activitylog.services.webservices.dto.UsersLastRequestDTOContainer;
import com.scopix.periscope.activitylog.services.webservices.dto.UsersOnQueueDTOContainer;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import javax.jws.WebService;

/**
 *
 * @author Sebastian
 */
@WebService(name = "ActivityLogWebServices")
public interface ActivityLogWebServices {

    /**
     * web service that returns the activity log for a user
     *
     * @param sessionId
     * @return ActivityLogDTOContainer
     * @throws ScopixWebServiceException
     */
    public ActivityLogDTOContainer getUsersActivityLogStats(Long sessionId) throws ScopixWebServiceException;

    /**
     * web service that returns the activity log for a user
     *
     * @param request
     * @param sessionId
     * @return ActivityLogDTOContainer
     * @throws ScopixWebServiceException
     */
    public ActivityLogDTOContainer getUserActivityLog(RequestActivityLogDTO request, Long sessionId) throws ScopixWebServiceException;

    /**
     * gets the user list
     *
     * @param sessionId
     * @return OperatorUsersDTOContainer
     * @throws ScopixWebServiceException
     */
    public OperatorUsersDTOContainer getOperatorUsersList(Long sessionId) throws ScopixWebServiceException;

    /**
     * get all users operating on queue given a past time range until now
     *
     * @param secondsAgo
     * @param sessionId
     * @return UsersOnQueueDTOContainer
     * @throws ScopixWebServiceException
     */
    public UsersOnQueueDTOContainer getUsersOnQueues(Long secondsAgo, Long sessionId) throws ScopixWebServiceException;

    /**
     *
     * @param timeZoneId 
     * @param secondsAgo
     * @param sessionId
     * @return UsersLastRequestDTOContainer
     * @throws ScopixWebServiceException
     */
    public UsersLastRequestDTOContainer getUsersLastRequest(String timeZoneId, Long secondsAgo, Long sessionId) throws ScopixWebServiceException;
}
