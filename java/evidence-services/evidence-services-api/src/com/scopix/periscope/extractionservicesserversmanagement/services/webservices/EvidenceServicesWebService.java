package com.scopix.periscope.extractionservicesserversmanagement.services.webservices;

import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceExtractionServicesServerDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author marko.perich
 * @version 1.0.0
 */
@WebService(name = "EvidenceServicesWebService")
//@CustomWebService
public interface EvidenceServicesWebService {

    /**
     * set a new extraction plan for a Evidence Services server.
     *
     * This method receives a list of evidence_extraction_services_server DTOs, each one corresponding to one evidence extraction
     * server managed by this evidence server
     *
     * @param evidenceExtractionSericesServerDTOs lista de planes a enviar a extraccion
     * @throws ScopixWebServiceException En caso de Error
     */
    @WebMethod
    void newExtractionPlan(List<EvidenceExtractionServicesServerDTO> evidenceExtractionSericesServerDTOs) throws
            ScopixWebServiceException;

    /**
     *
     * @param date fecha de solicitud de evidencias al pasado
     * @param extractionServicesServerId Servidor a cual pedir las evidencias
     * @param storeName Tienda de la cual se desean las evidencias
     * @return List<Integer> lista de id de request solicitados
     * @throws ScopixWebServiceException En caso de Error
     */
    @WebMethod
    List<Integer> extractionPlanToPast(String date, Integer extractionServicesServerId, String storeName)
            throws ScopixWebServiceException;
}
