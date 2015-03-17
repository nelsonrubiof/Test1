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
 *  UpdateVideoRecordCommand.java
 * 
 *  Created on 21-08-2013, 06:07:09 PM
 * 
 */

package com.scopix.periscope.converter.commands;

import com.scopix.periscope.converter.Video;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 * Comando para actualizar en base de datos un registro de conversión de video
 *
 * @author  carlos polo
 * @version 1.0.0
 * @since   6.0
 */
public class UpdateVideoRecordCommand {

    private GenericDAO dao;
    private static Logger log = Logger.getLogger(UpdateVideoRecordCommand.class);

    /**
     * Obtiene DAO genérico para acceso a base de datos
     * 
     * @author carlos polo
     * @date   21-ago-2013
     * @return GenericDAO  DAO genérico para acceso a base de datos
     */
    public GenericDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }
    
    /**
     * Actualiza registro del video en base de datos
     * 
     * @author carlos polo
     * @date   21-ago-2013
     * @param videoId          id del registro del video
     * @param estado           estado de la conversión
     * @param fechaConversion  fecha en que se realizó la conversión
     * @param pathConvertido   path en donde quedará el video convertido
     * @param mensajeError     mensaje de error de conversión (en caso de ocurrir)
     * @throws ScopixException excepción durante la actualización del registro
     */
    public void execute(Integer videoId, String estado, 
            Date fechaConversion, String pathConvertido, String mensajeError) throws ScopixException {

        log.info("start, videoId: [" + videoId + "], estado: [" + estado + "], fechaConversion: [" + fechaConversion + "], "
                + "pathConvertido: [" + pathConvertido + "], mensajeError: [" + mensajeError + "]");
        try {
            //Consulta registro a través del id
            Video vTemp = getDao().get(videoId, Video.class);
            
            if(vTemp!=null){
                vTemp.setEstado(estado);
                vTemp.setFechaConversion(fechaConversion);
                vTemp.setPathConvertido(pathConvertido);
                vTemp.setMensajeError(mensajeError);
            }
        } catch (ObjectRetrievalFailureException objRetFailException) {
            throw new ScopixException(objRetFailException.getMessage(), objRetFailException);
        }
        log.info("end");
    }
}