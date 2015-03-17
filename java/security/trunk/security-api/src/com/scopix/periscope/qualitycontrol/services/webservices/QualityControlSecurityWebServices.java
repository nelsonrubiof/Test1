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
 * QualityControlSecurityWebServices.java
 *
 * Created on 09-07-2010, 03:05:19 PM
 *
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scopix.periscope.qualitycontrol.services.webservices;

import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.qualitycontrol.dto.PeriscopeUserForQualityControlDTO;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author Gustavo Alvarez
 */
//@CustomWebService
@WebService(name = "QualityControlSecurityWebServices")
public interface QualityControlSecurityWebServices {

    List<PeriscopeUserForQualityControlDTO> getPeriscopeUsersForAccessReport(Date start, Date end, long sessionId,
            int corporateId) throws ScopixWebServiceException;
}
