/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
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
 *  VideoConverterService.java
 * 
 *  Created on 06-08-2013, 12:29:00 PM
 * 
 */
package com.scopix.periscope.converter.services;

import com.scopix.periscope.converter.VideoConverterDTO;
import javax.jws.WebService;
import javax.ws.rs.core.Response;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 * Interface que expone los servicios por invocar
 *
 * @author  carlos polo
 * @version 1.0.0
 * @since   6.0
 */
@WebService(name = "VideoConverterService")
public interface VideoConverterService {

    /**
     * Convierte un archivo de video
     * 
     * @author carlos polo
     * @param  videoConverterDTO objeto contenedor del fileName y URL de notificación
     * @date   06-jul-2013
     * @return Response          respuesta servicio
     * @throws ScopixException   excepción durante la conversión
     */
    Response convertVideo(VideoConverterDTO videoConverterDTO) throws ScopixException;
}