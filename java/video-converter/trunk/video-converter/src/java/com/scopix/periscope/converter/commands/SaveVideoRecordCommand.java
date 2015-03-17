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
 * SaveVideoRecordCommand.java
 * 
 * Created on 19-08-2013, 06:00:40 PM
 */

package com.scopix.periscope.converter.commands;

import org.apache.log4j.Logger;

import com.scopix.periscope.converter.Video;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 * Comando para guardar en base de datos registros de videos por convertir
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class SaveVideoRecordCommand {

    private GenericDAO dao;
    private static Logger log = Logger.getLogger(SaveVideoRecordCommand.class);

    /**
     * Obtiene DAO genérico para acceso a base de datos
     * 
     * @author carlos polo
     * @date 21-ago-2013
     * @return GenericDAO DAO genérico para acceso a base de datos
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
     * Inserta en base de datos un nuevo registro de conversión de video
     * 
     * @author carlos polo
     * @date 21-ago-2013
     * @param video archivo por convertir
     * @return Integer id del nuevo registro en base de datos
     * @throws ScopixException excepción durante la inserción del registro
     */
    public Integer execute(Video video) throws ScopixException {
        log.info("start, fileName: [" + video.getPathOriginal() + "]");
        getDao().save(video);
        log.info("end, videoId: [" + video.getId() + "]");
        return video.getId();
    }
}