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
 * FFMPEGImpl.java
 * 
 * Created on 03-07-2013, 12:16:00 PM
 */
package com.scopix.periscope.converter.ffmpeg;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.converter.VideoConverterManager;
import com.scopix.periscope.converter.enums.EnumVideoStatus;
import com.scopix.periscope.converter.http.HttpManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * Implementación para invocar proceso que realiza conversión de videos a través de FFMPEG
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class FFMPEGImpl implements Runnable {

    private String bitRateValue;
    private String videoOriginal;
    private String frameRateValue;
    private String keyFramesValue;
    private Integer evidenceFileId;
    private String urlNotificacion;
    private String videoConvertido;
    private Integer internalVideoId;
    private String pixelFormatValue;
    private String comandoEjecucion;
    private HttpManager httpManager;
    private FFMPEGProcessBuilder ffmpegProcessBuilder;
    private VideoConverterManager videoConverterManager;
    private static Logger log = Logger.getLogger(FFMPEGImpl.class);

    /**
     * Ejecuta el hilo de conversión
     * 
     * @author carlos polo
     * @date 08-ago-2013
     */
    @Override
    public void run() {
        log.info("start");
        try {
            convertVideo();
        } catch (ScopixException scopixException) {
            log.error(scopixException.getMessage(), scopixException);
            // responde al EES que lo llamó con la notificación de error
            callEvidenceExtractionController(true);
        }
        log.info("end");
    }

    /**
     * Convierte el video original al formato establecido
     * 
     * @author carlos polo
     * @date 03-jul-2013
     * @throws ScopixException excepción durante la conversión
     */
    public void convertVideo() throws ScopixException {
        log.info("start, evidenceFileId: [" + getEvidenceFileId() + "], internalVideoId: [" + getInternalVideoId()
                + "], videoOriginal: [" + getVideoOriginal() + "]");

        Process process = null;
        StringBuilder command = new StringBuilder();

        try {
            // comando original bitRate fRate kFrames pixelFormat convertido
            // ffmpeg -i video1.flv -b:v 160000 -r 4 -g 1 -pix_fmt yuv420p -y video1.mp4
            command.append(getComandoEjecucion()).append(" -i ").append(getVideoOriginal()).append(" -b:v ")
                    .append(getBitRateValue()).append(" -r ").append(getFrameRateValue()).append(" -g ")
                    .append(getKeyFramesValue()).append(" -pix_fmt ").append(getPixelFormatValue()).append(" -y ")
                    .append(getVideoConvertido());

            String comandoConversion = command.toString();
            log.debug("comando de conversion: " + comandoConversion);

            String[] params = StringUtils.split(comandoConversion, " ");
            getFfmpegProcessBuilder().setParams(params);

            // Ejecuta proceso de conversión de videos
            process = executeProcess();

        } finally {
            if (process != null) {
                log.debug("finalizando proceso de conversion para video: [" + getVideoConvertido() + "]");
                IOUtils.closeQuietly(process.getOutputStream());
                IOUtils.closeQuietly(process.getInputStream());
                IOUtils.closeQuietly(process.getErrorStream());
                log.debug("proceso de conversion finalizado para video: [" + getVideoConvertido() + "]");
            }
        }
        log.info("end");
    }

    /**
     * Ejecuta proceso de conversión de videos
     * 
     * @author carlos polo
     * @date 08-jul-2013
     * @return proceso ejecutado
     * @throws ScopixException excepción durante la conversión
     */
    public Process executeProcess() throws ScopixException {
        log.info("start");
        Process process = null;
        StringBuilder sbProcess = new StringBuilder();

        try {
            process = getFfmpegProcessBuilder().start();
            if (process != null) {
                log.debug("proceso de conversion iniciado para video: [" + getVideoOriginal() + "]");
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    // escribe salida del proceso de ffmpeg
                    sbProcess.append(line).append("\n");
                }
                // Una vez sale del while, es porque el proceso ha finalizado
                // Verifica si el video convertido fue creado exitosamente
                verifyConvertedVideo(sbProcess.toString());

                // responde al EES que lo llamó con la notificación del video convertido
                callEvidenceExtractionController(false);
            }
        } catch (IOException e) {
            throw new ScopixException(e.getMessage(), e);
        }

        log.info("end");
        return process;
    }

    /**
     * Verifica si el archivo de video convertido existe y sea válido
     * 
     * @author carlos polo
     * @date 13-ago-2013
     * @throws ScopixException excepción en caso que el video no esté bien generado
     */
    protected void verifyConvertedVideo(String mensajeError) throws ScopixException {
        log.info("start, verificando video: [" + getVideoConvertido() + "]");
        File convertedVideo = new File(getVideoConvertido());

        if (!convertedVideo.exists() || !convertedVideo.isFile() || !convertedVideo.canRead() || convertedVideo.length() < 1) {
            updateVideoRecord(EnumVideoStatus.ERROR.toString(), null, mensajeError);
            throw new ScopixException("El video [" + getVideoConvertido() + "] no pudo ser convertido");
        }
        log.debug("video convertido exitosamente, se actualizara registro en base de datos, video: [" + getVideoConvertido()
                + "]");
        // Si no lanza la excepción, es un video convertido exitosamente
        updateVideoRecord(EnumVideoStatus.CONVERTIDO.toString(), new Date(), null);
        log.info("end, video verificado: [" + getVideoConvertido() + "]");
    }

    /**
     * 
     * @param estadoConversion
     * @param fechaConversion
     * @param mensajeError
     * @throws ScopixException
     */
    protected void updateVideoRecord(String estadoConversion, Date fechaConversion, String mensajeError) throws ScopixException {
        log.info("start");
        getVideoConverterManager().updateVideoRecord(getInternalVideoId(), estadoConversion, fechaConversion,
                getVideoConvertido(), mensajeError);
        log.info("end");
    }

    /**
     * Responde al EES que lo llamó con la notificación del video convertido
     * 
     * @param error
     */
    private void callEvidenceExtractionController(boolean error) {
        log.info("start, notifyURL: [" + getUrlNotificacion() + "], videoConvertido: [" + getVideoConvertido() + "], error: ["
                + error + "]");

        String videoName = null;
        if (!error) {
            videoName = FilenameUtils.getName(getVideoConvertido()); // nombre con extensión
            log.debug("videoName: [" + videoName + "]");
        }

        // ej http://localhost:8080/ees/spring/videoconverterfileready?waitForConverter=N&evidenceId=001&originalName=videoTmp.avi
        String url = getUrlNotificacion() + "&error=" + error + "&convertedName=" + videoName;
        // http://localhost:8080/ees/spring/videoconverterfileready?waitForConverter=N&evidenceId=001&originalName=videoTmp.avi&error=false&convertedName=videoTmp.mp4
        log.debug("url respuesta a EES: [" + url + "]");
        try {
            getHttpManager().executeHttpGet(url);
        } catch (ScopixException e) {
            log.error(e.getMessage(), e);
        }
        log.info("end, respuesta enviada a EES");
    }

    /**
     * @return the bitRateValue
     */
    public String getBitRateValue() {
        return bitRateValue;
    }

    /**
     * @param bitRateValue the bitRateValue to set
     */
    public void setBitRateValue(String bitRateValue) {
        this.bitRateValue = bitRateValue;
    }

    /**
     * @return the frameRateValue
     */
    public String getFrameRateValue() {
        return frameRateValue;
    }

    /**
     * @param frameRateValue the frameRateValue to set
     */
    public void setFrameRateValue(String frameRateValue) {
        this.frameRateValue = frameRateValue;
    }

    /**
     * @return the keyFramesValue
     */
    public String getKeyFramesValue() {
        return keyFramesValue;
    }

    /**
     * @param keyFramesValue the keyFramesValue to set
     */
    public void setKeyFramesValue(String keyFramesValue) {
        this.keyFramesValue = keyFramesValue;
    }

    /**
     * @return the pixelFormatValue
     */
    public String getPixelFormatValue() {
        return pixelFormatValue;
    }

    /**
     * @param pixelFormatValue the pixelFormatValue to set
     */
    public void setPixelFormatValue(String pixelFormatValue) {
        this.pixelFormatValue = pixelFormatValue;
    }

    /**
     * @return the ffmpegProcessBuilder
     */
    public FFMPEGProcessBuilder getFfmpegProcessBuilder() {
        if (ffmpegProcessBuilder == null) {
            ffmpegProcessBuilder = new FFMPEGProcessBuilder();
        }
        return ffmpegProcessBuilder;
    }

    /**
     * @param ffmpegProcessBuilder the ffmpegProcessBuilder to set
     */
    public void setFfmpegProcessBuilder(FFMPEGProcessBuilder ffmpegProcessBuilder) {
        this.ffmpegProcessBuilder = ffmpegProcessBuilder;
    }

    /**
     * @return the comandoEjecucion
     */
    public String getComandoEjecucion() {
        return comandoEjecucion;
    }

    /**
     * @param comandoEjecucion the comandoEjecucion to set
     */
    public void setComandoEjecucion(String comandoEjecucion) {
        this.comandoEjecucion = comandoEjecucion;
    }

    /**
     * @return the videoOriginal
     */
    public String getVideoOriginal() {
        return videoOriginal;
    }

    /**
     * @param videoOriginal the videoOriginal to set
     */
    public void setVideoOriginal(String videoOriginal) {
        this.videoOriginal = videoOriginal;
    }

    /**
     * @return the videoConvertido
     */
    public String getVideoConvertido() {
        return videoConvertido;
    }

    /**
     * @param videoConvertido the videoConvertido to set
     */
    public void setVideoConvertido(String videoConvertido) {
        this.videoConvertido = videoConvertido;
    }

    /**
     * @return the videoConverterManager
     */
    public VideoConverterManager getVideoConverterManager() {
        if (videoConverterManager == null) {
            videoConverterManager = SpringSupport.getInstance().findBeanByClassName(VideoConverterManager.class);
        }
        return videoConverterManager;
    }

    /**
     * @param videoConverterManager the videoConverterManager to set
     */
    public void setVideoConverterManager(VideoConverterManager videoConverterManager) {
        this.videoConverterManager = videoConverterManager;
    }

    public String getUrlNotificacion() {
        return urlNotificacion;
    }

    public void setUrlNotificacion(String urlNotificacion) {
        this.urlNotificacion = urlNotificacion;
    }

    public HttpManager getHttpManager() {
        if (httpManager == null) {
            httpManager = SpringSupport.getInstance().findBeanByClassName(HttpManager.class);
        }
        return httpManager;
    }

    public Integer getInternalVideoId() {
        return internalVideoId;
    }

    public void setInternalVideoId(Integer internalVideoId) {
        this.internalVideoId = internalVideoId;
    }

    public Integer getEvidenceFileId() {
        return evidenceFileId;
    }

    public void setEvidenceFileId(Integer evidenceFileId) {
        this.evidenceFileId = evidenceFileId;
    }
}