/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scopix.periscope.admin;

import com.scopix.periscope.periscopefoundation.services.Service;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author marko.perich
 */
//@CustomWebService
@WebService(name = "EvidenceServicesAdminService")
public interface EvidenceServicesAdminService extends Service {
    /**
     * Clean database
     */
    @WebMethod
    void resetDb();
}
