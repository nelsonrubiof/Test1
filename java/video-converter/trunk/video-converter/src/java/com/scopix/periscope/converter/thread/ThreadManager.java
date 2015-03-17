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
 * ThreadManager.java
 * 
 * Created on 08-08-2013, 01:03:00 PM
 */
package com.scopix.periscope.converter.thread;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.scopix.periscope.converter.Video;
import com.scopix.periscope.converter.VideoConverterManager;
import com.scopix.periscope.converter.enums.EnumVideoStatus;
import com.scopix.periscope.converter.ffmpeg.FFMPEGImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * Clase encargada de administrar los hilos que ejecutarán las conversiones de video
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SpringBean
public class ThreadManager implements InitializingBean {

    private String bitRate;
    private String frameRate;
    private String keyFrames;
    private String pixelFormat;
    private String ffmpegCmdPath;
    private String pathOriginales;
    private String pathConvertidos;
    private String conversionFormat;
    private ExecutorService executor;
    private PropertiesConfiguration systConfiguration;
    private VideoConverterManager videoConverterManager;
    private static Logger log = Logger.getLogger(ThreadManager.class);

    /**
     * Inicializa el pool de hilos de conversión
     *
     * @author carlos polo
     * @date 14-ago-2013
     * @throws Exception excepción durante la inicialización
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("start");
        // obtiene parámetros de conversión
        ffmpegCmdPath = getSystConfiguration().getString("ffmpeg.path");
        pathOriginales = getSystConfiguration().getString("originales.path");
        pathConvertidos = getSystConfiguration().getString("convertidos.path");
        frameRate = getSystConfiguration().getString("framerate.value", "4");
        keyFrames = getSystConfiguration().getString("keyframes.value", "10");
        bitRate = getSystConfiguration().getString("bitrate.value", "160000");
        conversionFormat = getSystConfiguration().getString("conversion.format", ".mp4");
        pixelFormat = getSystConfiguration().getString("pixelformat.value", "yuv420p");

        // inicializa el pool de hilos
        Integer threadsNumber = getSystConfiguration().getInt("threads.number", 10);
        log.debug("iniciando pool de hilos, threadsNumber: [" + threadsNumber + "]");
        setExecutor(Executors.newFixedThreadPool(threadsNumber));

        try {
            // procesa videos pendientes de conversión (en caso de existir)
            processPendingVideos();
        } catch (ScopixException scopixException) {
            log.error(scopixException.getMessage(), scopixException);
        }
        log.info("end");
    }

    /**
     * Consulta si existen registros de videos pendientes de conversión para su posterior procesamiento
     *
     * @author carlos polo
     * @date 20-ago-2013
     * @throws ScopixException excepción durante la conversión
     */
    protected void processPendingVideos() throws ScopixException {
        log.info("start");
        // consulta si existen videos pendientes de conversion
        List<Video> lstVideosPendientes = getVideoConverterManager().getPendingVideos();

        if (lstVideosPendientes != null && !lstVideosPendientes.isEmpty()) {
            log.debug("si existen videos pendientes de conversion, numero de videos: [" + lstVideosPendientes.size() + "]");
            for (Video videoPendiente : lstVideosPendientes) {
                String pathOriginal = videoPendiente.getPathOriginal();
                String fileName = FilenameUtils.getName(pathOriginal); // nombre de archivo con extensión
                // convierte archivo de video
                convertVideo(videoPendiente.getId(), fileName, videoPendiente.getUrlNotificacion(),
                        videoPendiente.getEvidenceFileId());
            }
        }
        log.info("end");
    }

    /**
     * Convierte un archivo de video
     *
     * @author carlos polo
     * @param internalVideoId id del registro de video
     * @param fileName nombre del video por convertir
     * @param urlNotificacion url donde se enviará notificación de conversión
     * @param evidenceFileId id de la evidencia
     * @date 08-jul-2013
     * @throws ScopixException excepción durante la conversión
     */
    public void convertVideo(Integer internalVideoId, String fileName, String urlNotificacion, Integer evidenceFileId)
            throws ScopixException {
        log.info("start, internalVideoId: [" + internalVideoId + "], fileName: [" + fileName + "], urlNotificacion: ["
                + urlNotificacion + "], evidenceFileId: [" + evidenceFileId + "]");
        if (internalVideoId == null) {
            // es una solicitud de conversión nueva (no existe id en base de datos),
            // crea objeto de video para ser persistido en base datos con estado pendiente de conversión
            log.debug("es una solicitud de conversion nueva, se insertara registro en base de datos");

            Video video = new Video();
            video.setPathOriginal(getPathOriginales() + fileName);
            video.setEstado(EnumVideoStatus.PENDIENTE.toString());
            video.setFechaSolicitud(new Date());
            video.setUrlNotificacion(urlNotificacion);
            video.setEvidenceFileId(evidenceFileId);

            internalVideoId = getVideoConverterManager().saveVideoRecord(video);
        }

        FFMPEGImpl ffmpegImpl = new FFMPEGImpl();
        String nombreBase = FilenameUtils.getBaseName(fileName); // nombre sin extensión

        // comando original bitRate fRate kFrames pixelFormat convertido
        // ffmpeg -i video1.flv -b:v 160000 -r 4 -g 10 -pix_fmt yuv420p video1.mp4
        ffmpegImpl.setBitRateValue(getBitRate());
        ffmpegImpl.setFrameRateValue(getFrameRate());
        ffmpegImpl.setKeyFramesValue(getKeyFrames());
        ffmpegImpl.setEvidenceFileId(evidenceFileId);
        ffmpegImpl.setInternalVideoId(internalVideoId);
        ffmpegImpl.setUrlNotificacion(urlNotificacion);
        ffmpegImpl.setPixelFormatValue(getPixelFormat());
        ffmpegImpl.setComandoEjecucion(getFfmpegCmdPath());
        ffmpegImpl.setVideoOriginal(getPathOriginales() + fileName);
        ffmpegImpl.setVideoConvertido(getPathConvertidos() + nombreBase + getConversionFormat());

        // ejecuta hilo que invocará comando de FFMPEG
        getExecutor().execute(ffmpegImpl);
        log.info("end");
    }

    /**
     * @return the systConfiguration
     */
    public PropertiesConfiguration getSystConfiguration() {
        if (systConfiguration == null) {
            try {
                systConfiguration = new PropertiesConfiguration("system.properties");
                systConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
                log.debug("systConfiguration.basePath: [" + systConfiguration.getBasePath() + "]");
            } catch (ConfigurationException e) {
                log.error("ConfigurationException: [" + e.getMessage() + "]", e);
            }
        }
        return systConfiguration;
    }

    /**
     * @param systConfiguration the systConfiguration to set
     */
    public void setSystConfiguration(PropertiesConfiguration systConfiguration) {
        this.systConfiguration = systConfiguration;
    }

    /**
     * @return the executor
     */
    public ExecutorService getExecutor() {
        return executor;
    }

    /**
     * @param executor the executor to set
     */
    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    /**
     * @return the ffmpegCmdPath
     */
    public String getFfmpegCmdPath() {
        return ffmpegCmdPath;
    }

    /**
     * @param ffmpegCmdPath the ffmpegCmdPath to set
     */
    public void setFfmpegCmdPath(String ffmpegCmdPath) {
        this.ffmpegCmdPath = ffmpegCmdPath;
    }

    /**
     * @return the pathOriginales
     */
    public String getPathOriginales() {
        return pathOriginales;
    }

    /**
     * @param pathOriginales the pathOriginales to set
     */
    public void setPathOriginales(String pathOriginales) {
        this.pathOriginales = pathOriginales;
    }

    /**
     * @return the pathConvertidos
     */
    public String getPathConvertidos() {
        return pathConvertidos;
    }

    /**
     * @param pathConvertidos the pathConvertidos to set
     */
    public void setPathConvertidos(String pathConvertidos) {
        this.pathConvertidos = pathConvertidos;
    }

    /**
     * @return the frameRate
     */
    public String getFrameRate() {
        return frameRate;
    }

    /**
     * @param frameRate the frameRate to set
     */
    public void setFrameRate(String frameRate) {
        this.frameRate = frameRate;
    }

    /**
     * @return the keyFrames
     */
    public String getKeyFrames() {
        return keyFrames;
    }

    /**
     * @param keyFrames the keyFrames to set
     */
    public void setKeyFrames(String keyFrames) {
        this.keyFrames = keyFrames;
    }

    /**
     * @return the bitRate
     */
    public String getBitRate() {
        return bitRate;
    }

    /**
     * @param bitRate the bitRate to set
     */
    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    /**
     * @return the conversionFormat
     */
    public String getConversionFormat() {
        return conversionFormat;
    }

    /**
     * @param conversionFormat the conversionFormat to set
     */
    public void setConversionFormat(String conversionFormat) {
        this.conversionFormat = conversionFormat;
    }

    /**
     * @return the pixelFormat
     */
    public String getPixelFormat() {
        return pixelFormat;
    }

    /**
     * @param pixelFormat the pixelFormat to set
     */
    public void setPixelFormat(String pixelFormat) {
        this.pixelFormat = pixelFormat;
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

    public Integer getIntegerProperties(String key) {
        return getSystConfiguration().getInteger(key, -1);
    }
}