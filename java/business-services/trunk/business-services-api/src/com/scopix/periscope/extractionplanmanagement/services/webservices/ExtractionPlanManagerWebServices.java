/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement.services.webservices;

import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceProviderDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.SituationSensorDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.extractionplanmanagement.dto.DetalleSolicitudDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanCustomizingDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanRangeDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanRangeDetailDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.templatemanagement.dto.MetricTemplateDTO;
import com.scopix.periscope.templatemanagement.dto.SituationTemplateDTO;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author nelson
 */
@WebService(name = "ExtractionPlanManagerWebServices")
public interface ExtractionPlanManagerWebServices {

    List<SituationTemplateDTO> getSituationTemplates(long sessionId) throws ScopixWebServiceException;

    List<StoreDTO> getStores(long sessionId) throws ScopixWebServiceException;

    List<ExtractionPlanCustomizingDTO> getExtractionPlanCustomizings(Integer storeId, String estado, long sessionId)
            throws ScopixWebServiceException;

    ExtractionPlanCustomizingDTO createExtractionPlanCustomizing(Integer situationTemplateId, Integer storeId, long sessionId)
            throws ScopixWebServiceException;

    List<EvidenceProviderDTO> getEvidenceProvidersStoreSituationTemplate(Integer storeId, Integer situationTemplateId,
            long sessionId) throws ScopixWebServiceException;

    List<SituationSensorDTO> getSensors(Integer extractionPlanCustomizingId, long sessionId)
            throws ScopixWebServiceException;

    List<MetricTemplateDTO> getMetricTemplates(Integer extractionPlanCustomizingId, long sessionId)
            throws ScopixWebServiceException;

    ExtractionPlanCustomizingDTO getUltimoExtractionPlanCustomizingNoEnviado(Integer situationTemplateId, Integer storeId,
            long sessionId) throws ScopixWebServiceException;

    ExtractionPlanCustomizingDTO getUltimoExtractionPlanCustomizingEnviado(Integer situationTemplateId, Integer storeId,
            long sessionId) throws ScopixWebServiceException;

    ExtractionPlanCustomizingDTO getExtractionPlanCustomizingDatosGenerales(Integer extractionPlanRangeId,
            long sessionId) throws ScopixWebServiceException;

    List<ExtractionPlanRangeDTO> getExtractionPlanRanges(Integer extractionPlanCustomizingId, long sessionId)
            throws ScopixWebServiceException;

    List<ExtractionPlanRangeDetailDTO> getExtractionPlanRangeDetails(Integer extractionPlanRangeId, long sessionId)
            throws ScopixWebServiceException;

    void saveEPCGeneral(ExtractionPlanCustomizingDTO dTO, long sessionId) throws ScopixWebServiceException;

    ExtractionPlanRangeDTO saveExtractionPlanRange(Integer extractionPlanCustomizingId, ExtractionPlanRangeDTO dTO,
            long sessionId) throws ScopixWebServiceException;

    List<ExtractionPlanRangeDetailDTO> regenerateDetailForRange(Integer extractionPlanCustomizingId, ExtractionPlanRangeDTO dTO,
            long sessionId) throws ScopixWebServiceException;

    List<String> copyCalendarActions(Integer situationTemplateId, Integer storeId,
            long sessionId) throws ScopixWebServiceException;

    void copyCalendar(Integer extractionPlanCustomizingId, Integer situationTemplateId, Integer storeId,
            long sessionId) throws ScopixWebServiceException;

    void copyDayInDays(Integer extractionPlanCustomizingId, Integer day, List<Integer> days, Boolean copyDetail, long sessionId)
            throws ScopixWebServiceException;

    void deleteExtractionPlanRange(Integer extractionPlanCustomizingId, Integer extractionPlanRangeId, long sessionId)
            throws ScopixWebServiceException;

    void regenerateDetailForEPC(Integer extractionPlanCustomizingId, List<Integer> days, long sessionId)
            throws ScopixWebServiceException;

    ExtractionPlanRangeDTO getExtractionPlanRange(Integer extractionPlanRangeId, long sessionId) throws ScopixWebServiceException;

    void cleanExtractionPlanCutomizingDay(Integer extractionPlanCustomizingId, List<Integer> days, long sessionId)
            throws ScopixWebServiceException;

    List<DetalleSolicitudDTO> getDetalleSolicitudes(Integer extractionPlanCustomizingId, long sessionId)
            throws ScopixWebServiceException;

    ExtractionPlanCustomizingDTO copyEPCToEdition(Integer extractionPlanCustomizingId, long sessionId)
            throws ScopixWebServiceException;

    /**
     * realiza una copia Full de un EPC Enviado a Edicion borra los datos originales y luego carga los datos nuevos
     */
    ExtractionPlanCustomizingDTO copyEPCFullToEdition(Integer extractionPlanCustomizingId, long sessionId)
            throws ScopixWebServiceException;

    void disableExtractionPlanCustomizings(List<Integer> epcIds, long sessionId) throws ScopixWebServiceException;
}
