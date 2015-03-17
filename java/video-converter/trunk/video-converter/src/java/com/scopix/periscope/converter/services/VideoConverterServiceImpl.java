/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * 
 * VideoConverterServiceImpl.java
 * 
 * Created on 06-08-2013, 02:30:00 PM
 */
package com.scopix.periscope.converter.services;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.scopix.periscope.converter.VideoConverterDTO;
import com.scopix.periscope.converter.thread.ThreadManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * Implementación de los servicios por invocar
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@WebService(endpointInterface = "com.scopix.periscope.converter.services.VideoConverterService")
@SpringBean(rootClass = VideoConverterService.class)
public class VideoConverterServiceImpl implements VideoConverterService {

    private static Logger log = Logger.getLogger(VideoConverterServiceImpl.class);

    /**
     * Convierte un archivo de video
     * 
     * @author carlos polo
     * @param videoConverterDTO objeto contenedor del fileName y URL de notificación
     * @date 06-jul-2013
     * @return Response respuesta servicio
     * @throws ScopixException excepción durante la conversión
     */
    @POST
    @Consumes("application/json")
    @Path("/convertVideo")
    @Override
    public Response convertVideo(VideoConverterDTO videoConverterDTO) throws ScopixException {
        log.info("start");
        try {
            String fileName = videoConverterDTO.getFileName();
            Integer evidenceFileId = videoConverterDTO.getEvidenceFileId();
            String urlNotificacion = videoConverterDTO.getUrlNotificacion();
            String waitForConverter = videoConverterDTO.getWaitForConverter();

            log.debug("evidenceFileId: [" + evidenceFileId + "], fileName: [" + fileName + "], urlNotificacion original: ["
                    + urlNotificacion + "]");

            urlNotificacion = urlNotificacion + "?waitForConverter=" + waitForConverter + "&evidenceId=" + evidenceFileId
                    + "&originalName=" + fileName;
            log.debug("urlNotificacion complementado: [" + urlNotificacion + "]");

            // a través del administrador de hilos se invoca la conversión de un nuevo video, por eso el id se envía null
            ThreadManager threadManager = SpringSupport.getInstance().findBeanByClassName(ThreadManager.class);
            threadManager.convertVideo(null, fileName, urlNotificacion, evidenceFileId);

        } catch (ScopixException scopixException) {
            log.error(scopixException.getMessage(), scopixException);
            throw scopixException;
        }
        log.info("end");
        return Response.ok().build();
    }
}