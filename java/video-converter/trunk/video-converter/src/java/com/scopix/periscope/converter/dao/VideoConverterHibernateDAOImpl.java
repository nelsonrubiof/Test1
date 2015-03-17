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
 * VideoConverterHibernateDAOImpl.java
 * 
 * Created on 19-08-2013, 06:05:00 PM
 */

package com.scopix.periscope.converter.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.transform.Transformers;

import com.scopix.periscope.converter.Video;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;

/**
 * DAO que implementa acceso a base de datos personalizado
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SpringBean(rootClass = VideoConverterHibernateDAOImpl.class)
public class VideoConverterHibernateDAOImpl extends DAOHibernate<Object, Integer> implements VideoConverterHibernateDAO {

    private static Logger log = Logger.getLogger(VideoConverterHibernateDAOImpl.class);

    /**
     * Consulta registros de los videos pendientes de conversión
     * 
     * @author carlos polo
     * @date 21-ago-2013
     * @return List<Video> lista de videos pendientes de conversión (en caso de existir)
     * @throws ScopixException excepción durante la consulta de registros
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Video> getPendingVideos() throws ScopixException {
        log.info("start");
        List<Video> videoList = null;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT id as id, path_original as pathOriginal, "
                    + "path_convertido as pathConvertido, estado as estado, fecha_solicitud as fechaSolicitud, "
                    + "fecha_conversion as fechaConversion, url_notificacion as urlNotificacion, evidence_file_id as evidenceFileId ");
            sql.append("FROM video ");
            sql.append("WHERE estado = 'P'");

            videoList = this.getSession().createSQLQuery(sql.toString()).addScalar("id").addScalar("pathOriginal")
                    .addScalar("pathConvertido").addScalar("estado").addScalar("fechaSolicitud").addScalar("fechaConversion")
                    .addScalar("urlNotificacion").addScalar("evidenceFileId")
                    .setResultTransformer(Transformers.aliasToBean(Video.class)).list();

        } catch (HibernateException hibernateException) {
            throw new ScopixException(hibernateException.getMessage(), hibernateException);
        }

        log.info("end");
        return videoList;
    }
}