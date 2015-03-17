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
 * QualityControlSecurityWebServicesImpl.java
 *
 * Created on 09-07-2010, 04:10:27 PM
 *
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.qualitycontrol.services.webservices;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.qualitycontrol.QualityControlManager;
import com.scopix.periscope.qualitycontrol.dto.PeriscopeUserForQualityControlDTO;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
@WebService(endpointInterface = "com.scopix.periscope.qualitycontrol.services.webservices.QualityControlSecurityWebServices")
//@CustomWebService(serviceClass = QualityControlSecurityWebServices.class)
@SpringBean(rootClass = QualityControlSecurityWebServices.class)
public class QualityControlSecurityWebServicesImpl implements QualityControlSecurityWebServices {

    private static Logger log = Logger.getLogger(QualityControlSecurityWebServicesImpl.class);

    @Override
    public List<PeriscopeUserForQualityControlDTO> getPeriscopeUsersForAccessReport(Date start, Date end, long sessionId,
            int corporateId) throws ScopixWebServiceException {
        log.debug("start");

        List<PeriscopeUserForQualityControlDTO> list = null;
        try {
             list = SpringSupport.getInstance().findBeanByClassName(
                    QualityControlManager.class).getPeriscopeUsersForAccessReport(start, end, sessionId, corporateId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }

        log.debug("end");
        return list;
    }
}
