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
 * VideoConverterManager.java
 * 
 * Created on 19-08-2013, 05:59:28 PM
 */

package com.scopix.periscope.converter;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.scopix.periscope.converter.commands.GetPendingVideosCommand;
import com.scopix.periscope.converter.commands.SaveVideoRecordCommand;
import com.scopix.periscope.converter.commands.UpdateVideoRecordCommand;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;

/**
 * Manager para administrar persistencia y transacciones en base de datos
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SpringBean(rootClass = VideoConverterManager.class)
@Transactional(rollbackFor = ScopixException.class)
public class VideoConverterManager {

    private SaveVideoRecordCommand saveVideoRecordCommand;
    private GetPendingVideosCommand getPendingVideosCommand;
    private UpdateVideoRecordCommand updateVideoRecordCommand;
    private static Logger log = Logger.getLogger(VideoConverterManager.class);

    /**
     * Inserta en base de datos un nuevo registro de conversión de video
     * 
     * @author carlos polo
     * @date 21-ago-2013
     * @param video archivo por convertir
     * @throws ScopixException excepción durante la inserción del registro
     */
    public Integer saveVideoRecord(Video video) throws ScopixException {
        log.info("start, fileName: [" + video.getPathOriginal() + "]");
        Integer videoId = getSaveVideoRecordCommand().execute(video);
        log.info("end, videoId: [" + videoId + "]");
        return videoId;
    }

    /**
     * Consulta registros de los videos pendientes de conversión
     * 
     * @author carlos polo
     * @date 21-ago-2013
     * @return List<Video> lista de videos pendientes de conversión (en caso de existir)
     * @throws ScopixException excepción durante la consulta en base de datos
     */
    public List<Video> getPendingVideos() throws ScopixException {
        log.info("start");
        List<Video> lstVideosPendientes = getGetPendingVideosCommand().execute();
        log.info("end");
        return lstVideosPendientes;
    }

    /**
     * Actualiza registro del video en base de datos
     * 
     * @author carlos polo
     * @date 21-ago-2013
     * @param videoId id del registro del video
     * @param estado estado de la conversión
     * @param fechaConversion fecha en que se realizó la conversión
     * @param pathConvertido path en donde quedará el video convertido
     * @param mensajeError mensaje de error de conversión (en caso de ocurrir)
     * @throws ScopixException excepción durante la actualización del registro
     */
    public void updateVideoRecord(Integer videoId, String estado, Date fechaConversion, String pathConvertido, String mensajeError)
            throws ScopixException {

        log.info("start, videoId: [" + videoId + "]");
        getUpdateVideoRecordCommand().execute(videoId, estado, fechaConversion, pathConvertido, mensajeError);
        log.info("end");
    }

    /**
     * @return the saveVideoRecordCommand
     */
    public SaveVideoRecordCommand getSaveVideoRecordCommand() {
        if (saveVideoRecordCommand == null) {
            saveVideoRecordCommand = new SaveVideoRecordCommand();
        }
        return saveVideoRecordCommand;
    }

    /**
     * @param saveVideoRecordCommand the saveVideoRecordCommand to set
     */
    public void setSaveVideoRecordCommand(SaveVideoRecordCommand saveVideoRecordCommand) {
        this.saveVideoRecordCommand = saveVideoRecordCommand;
    }

    /**
     * @return the getPendingVideosCommand
     */
    public GetPendingVideosCommand getGetPendingVideosCommand() {
        if (getPendingVideosCommand == null) {
            getPendingVideosCommand = new GetPendingVideosCommand();
        }
        return getPendingVideosCommand;
    }

    /**
     * @param getPendingVideosCommand the getPendingVideosCommand to set
     */
    public void setGetPendingVideosCommand(GetPendingVideosCommand getPendingVideosCommand) {
        this.getPendingVideosCommand = getPendingVideosCommand;
    }

    /**
     * @return the updateVideoRecordCommand
     */
    public UpdateVideoRecordCommand getUpdateVideoRecordCommand() {
        if (updateVideoRecordCommand == null) {
            updateVideoRecordCommand = new UpdateVideoRecordCommand();
        }
        return updateVideoRecordCommand;
    }

    /**
     * @param updateVideoRecordCommand the updateVideoRecordCommand to set
     */
    public void setUpdateVideoRecordCommand(UpdateVideoRecordCommand updateVideoRecordCommand) {
        this.updateVideoRecordCommand = updateVideoRecordCommand;
    }
}