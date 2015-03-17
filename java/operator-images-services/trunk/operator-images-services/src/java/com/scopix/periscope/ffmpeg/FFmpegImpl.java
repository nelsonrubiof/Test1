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
 * FFmpegImpl.java
 * 
 * Created on 03-05-2013, 05:35:53 PM
 */
package com.scopix.periscope.ffmpeg;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
@SuppressWarnings(value = { "serial", "unused" })
public class FFmpegImpl implements Serializable {

    private File tmpFile;
    private String pathExec;
    private FFmpegProcessBuilder processBuilder;
    private static Logger log = Logger.getLogger(FFmpegImpl.class);

    /**
     *
     * @param configFFmpeg ruta de ejecutable para ffmpeg
     */
    public FFmpegImpl(String configFFmpeg) {
        pathExec = configFFmpeg;
    }

    /**
     *
     * @param fileName nombre del archivo original
     * @param seconds segundo en el cual desamos sacar el snapshot
     * @return BufferedImage con el snapshot solicitado
     * @throws ScopixException Excepcion en caso de Error
     */
    public BufferedImage getSnapshot(String fileName, Double seconds) throws ScopixException {
        log.info("start [fileName:" + fileName + "][seconds:" + seconds + "]");
        Process p = null;
        BufferedImage img = null;
        String newFile = null;

        try {
            setTmpFile(File.createTempFile("SnapshotFFmpeg_" + FilenameUtils.getName(fileName), ".jpg"));
            String pathOrigen = FilenameUtils.separatorsToUnix(fileName);
            newFile = FilenameUtils.separatorsToUnix(getTmpFile().getAbsolutePath());
            String pathDestino = newFile;

            String command = getPathExec() + "ffmpeg -i " + pathOrigen + " -r 1 -ss " + seconds + " -t 1 -y " + pathDestino;
            log.debug("command: " + command);

            String[] param = StringUtils.split(command, " ");
            getProcessBuilder().setParam(param);
            p = getProcessBuilder().start();
            if (p != null) {
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    // log.debug(line);
                }
                img = readTempFile(newFile);
            }
        } catch (IOException e) {
            log.error(e, e);
            throw new ScopixException(e.getMessage(), e);
        } finally {
            if (p != null) {
                IOUtils.closeQuietly(p.getOutputStream());
                IOUtils.closeQuietly(p.getInputStream());
                IOUtils.closeQuietly(p.getErrorStream());
            }
            try {
                // borramos el temporal
                log.debug("Disponiendose a borrar fileTmp:" + FilenameUtils.getName(newFile));
                FileUtils.forceDelete(new File(newFile));
                log.debug("File tmp borrado:" + FilenameUtils.getName(newFile));
            } catch (IOException e) {
                log.warn("Error borrando tmpFile e:" + e);
            }
        }

        log.info("end");
        return img;
    }

    /**
     * Retorna un BufferedImage de un snapshot generado por ffmpeg
     *
     * @param newFile file que se desea leer
     * @return BufferedImage con la representacion de un file
     * @throws IOException Excepcion en caso de error
     */
    public BufferedImage readTempFile(String newFile) throws IOException {
        BufferedImage bi = ImageIO.read(new File(newFile));
        return bi;
    }

    /**
     * Genera sprite images a partir del correspondiente video
     *
     * @param fileName ruta y nombre del archivo de video
     * @param spritesBaseDir ruta de destino de los sprites
     * @param operatorImgUrl url de operator images
     * @param ffmpegSpritesCmd comando con parámetros para generación de sprite images
     * @param spritesScaleX ancho para cada thumbnail del sprite
     * @param spritesScaleY alto para cada thumbnail del sprite
     * @return boolean true si es exitoso
     */
    public boolean generateVideoSprites(String fileName, String spritesBaseDir, String operatorImgUrl, String[] ffmpegSpritesCmd,
            String spritesScaleX, String spritesScaleY) {
        log.info("start, fileName: [" + fileName + "], " + "pathDestino: [" + spritesBaseDir + "], ffmpegSpritesCmd: ["
                + StringUtils.join(ffmpegSpritesCmd, ", ") + "]");

        Process process = null;
        boolean success = false;
        StringBuilder sb = new StringBuilder();
        char separatorChar = File.separatorChar;
        StringBuilder spritesCmd = new StringBuilder();

        try {
            int ix = fileName.indexOf("evidence");
            String subPath = fileName.substring(ix, fileName.length());
            fileName = FilenameUtils.separatorsToSystem("/data/ftp/" + subPath);
            log.debug("converted fileName: [" + fileName + "]");

            String videoBaseName = FilenameUtils.getBaseName(fileName);
            // llega ej: /data/ftp/evidence/Lowes/404/2014/04/03/nombrevideo.mp4
            // debe quedar ej: /spritesbasedir/Lowes/404/20140403/nombrevideo/sprites_y_vtt
            String filePath = FilenameUtils.getPathNoEndSeparator(fileName); // ej, data/ftp/evidence/Lowes/404/2014/04/03
            String[] saFilePath = filePath.split("/");// "/"

            String clientName = saFilePath[3];
            String store = saFilePath[4];
            String date = saFilePath[5] + saFilePath[6] + saFilePath[7];
            String videoBasePath = clientName + separatorChar + store + separatorChar + date + separatorChar + videoBaseName;
            log.debug("videoBasePath: [" + videoBasePath + "]");

            String initPath = FilenameUtils.separatorsToUnix(fileName);
            String endPath = FilenameUtils.separatorsToUnix(spritesBaseDir + videoBasePath);

            File directory = new File(endPath);

            // true if and only if the file denoted by this abstract pathname exists and is a directory
            if (!directory.isDirectory()) {
                log.debug("Directorio NO existe, se dispone a forzar creación: [" + endPath + "]");
                // Forza la creación del directorio
                FileUtils.forceMkdir(directory);
                log.debug("Después de forceMkdir");
            }

            for (String cmdPart : ffmpegSpritesCmd) {
                spritesCmd.append(cmdPart).append(",");
            }
            String strSpritesCmd = spritesCmd.toString();
            strSpritesCmd = strSpritesCmd.substring(0, strSpritesCmd.length() - 1);

            String command = getPathExec() + "ffmpeg -i " + initPath + " " + strSpritesCmd + " " + endPath + "/sprite%d.jpg";

            log.debug("FFMPEG sprites command: [" + command + "]");
            String[] param = StringUtils.split(command, " ");
            getProcessBuilder().setParam(param);
            process = getProcessBuilder().start();

            if (process != null) {
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                String output = sb.toString();
                if (output != null && !output.trim().equals("")) {
                    int index = output.indexOf("Duration");
                    if (index != -1) {
                        // HH:MM:SS
                        String duration = output.substring(index + 10, index + 18);

                        VTTObject vttObject = new VTTObject();
                        vttObject.setDuration(duration);
                        vttObject.setOperatorImgUrl(operatorImgUrl);
                        vttObject.setVideoBaseName(videoBaseName);
                        vttObject.setSpritesScaleX(spritesScaleX);
                        vttObject.setSpritesScaleY(spritesScaleY);
                        vttObject.setEndPath(endPath);
                        vttObject.setClientName(clientName);
                        vttObject.setStore(store);
                        vttObject.setDate(date);

                        generateVttFile(vttObject);
                        success = true;
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (process != null) {
                IOUtils.closeQuietly(process.getOutputStream());
                IOUtils.closeQuietly(process.getInputStream());
                IOUtils.closeQuietly(process.getErrorStream());
            }
        }
        log.info("end, success: [" + success + "]");
        return success;
    }

    /**
     * Genera archivo .vtt con la información de las imágenes de los sprites
     * 
     * @param vttObject
     */
    private void generateVttFile(VTTObject vttObject) throws IOException {
        log.info("start");

        String duration = vttObject.getDuration();
        String operatorImgUrl = vttObject.getOperatorImgUrl();
        String videoBaseName = vttObject.getVideoBaseName();
        String spritesScaleX = vttObject.getSpritesScaleX();
        String spritesScaleY = vttObject.getSpritesScaleY();
        String endPath = vttObject.getEndPath();
        String clientName = vttObject.getClientName();
        String store = vttObject.getStore();
        String date = vttObject.getDate();

        log.debug("duracion: [" + duration + "], operatorImgUrl: [" + operatorImgUrl + "], videoBaseName: [" + videoBaseName
                + "], " + "spritesScaleX: [" + spritesScaleX + "], spritesScaleY: [" + spritesScaleY + "], endPath: [" + endPath
                + "], " + "clientName: [" + clientName + "], store: [" + store + "], date: [" + date + "]");

        int xPos = 0;
        int yPos = 0;
        int colsCount = 0;
        int rowsCount = 0;
        int hoursCount = 0;
        int minsCount = 0;
        int secsCount = 0;
        int spritesCount = 1;
        String strHoursCount = null;
        String strMinsCount = null;
        String strSecsCount = null;
        StringBuilder vttContent = new StringBuilder();
        int scaleX = Integer.valueOf(spritesScaleX).intValue();
        int scaleY = Integer.valueOf(spritesScaleY).intValue();

        // HH:MM:SS
        String[] saDuracion = duration.split(":");
        Long horas = Long.valueOf(saDuracion[0]);
        Long minutos = Long.valueOf(saDuracion[1]);
        Long segundos = Long.valueOf(saDuracion[2]);

        Long durationInSegs = (horas * 3600) + (minutos * 60) + segundos;
        log.debug("durationInSegs: [" + durationInSegs + "]");

        vttContent.append("WEBVTT\n\n");

        for (long i = 0; i < durationInSegs; i++) {
            strHoursCount = "";
            strMinsCount = "";
            strSecsCount = "";

            strHoursCount = processHourUnits(hoursCount, strHoursCount);
            strMinsCount = processMinUnits(minsCount, strMinsCount);
            strSecsCount = processSecUnits(secsCount, strSecsCount);

            String leftPart = strHoursCount + ":" + strMinsCount + ":" + strSecsCount + ".000";

            if ((secsCount + 1) == 60) {
                secsCount = 0;

                if ((minsCount + 1) == 60) {
                    hoursCount++;
                    minsCount = 0;
                    secsCount = 0;
                } else {
                    minsCount++;
                }
            } else {
                secsCount++;
            }

            strHoursCount = processHourUnits(hoursCount, strHoursCount);
            strMinsCount = processMinUnits(minsCount, strMinsCount);
            strSecsCount = processSecUnits(secsCount, strSecsCount);

            String rightPart = strHoursCount + ":" + strMinsCount + ":" + strSecsCount + ".000\n";

            vttContent.append(leftPart).append(" --> ").append(rightPart);

            // getSpriteImage/{corporateName}/{store}/{date}/{fileName}/{vttFile}
            vttContent.append(operatorImgUrl).append("getSpriteImage/").append(clientName).append("/").append(store).append("/")
                    .append(date).append("/sprite").append(spritesCount).append(".jpg/" + videoBaseName + "#xywh=").append(xPos)
                    .append(",").append(yPos).append(",").append(spritesScaleX).append(",").append(spritesScaleY).append("\n\n");

            xPos += scaleX;
            colsCount++;

            if (colsCount == 5) {
                xPos = 0;
                yPos += scaleY;
                rowsCount++;
                colsCount = 0;
            }

            if (rowsCount == 5) {
                xPos = 0;
                yPos = 0;
                colsCount = 0;
                rowsCount = 0;
                spritesCount++;
            }
        }// fin for

        String vttPath = FilenameUtils.separatorsToUnix(endPath + "/" + videoBaseName + ".vtt");
        log.debug("vttPath: [" + vttPath + "]");
        File file = new File(vttPath);
        FileUtils.writeStringToFile(file, vttContent.toString());
        log.info("end");
    }

    /**
     * Procesa las unidades de horas, para concatenar cero en caso de ser menores que 10
     * 
     * @param hoursCount conteo de horas
     * @param strHoursCount conteo de horas string
     * @return horas procesadas
     */
    private String processHourUnits(int hoursCount, String strHoursCount) {
        if (hoursCount < 10) {
            strHoursCount = "0" + hoursCount;
        } else {
            strHoursCount = String.valueOf(hoursCount);
        }
        return strHoursCount;
    }

    /**
     * Procesa las unidades de minutos, para concatenar cero en caso de ser menores que 10
     * 
     * @param minsCount conteo de minutos
     * @param strMinsCount conteo de minutos string
     * @return minutos procesados
     */
    private String processMinUnits(int minsCount, String strMinsCount) {
        if (minsCount < 10) {
            strMinsCount = "0" + minsCount;
        } else {
            strMinsCount = String.valueOf(minsCount);
        }
        return strMinsCount;
    }

    /**
     * Procesa las unidades de segundos, para concatenar cero en caso de ser menores que 10
     * 
     * @param secsCount conteo de segundos
     * @param strSecsCount conteo de segundos string
     * @return segundos procesados
     */
    private String processSecUnits(int secsCount, String strSecsCount) {
        if (secsCount < 10) {
            strSecsCount = "0" + secsCount;
        } else {
            strSecsCount = String.valueOf(secsCount);
        }
        return strSecsCount;
    }

    /**
     * @return the tmpFile
     */
    public File getTmpFile() {
        return tmpFile;
    }

    /**
     * @param tmpFile the tmpFile to set
     */
    public void setTmpFile(File tmpFile) {
        this.tmpFile = tmpFile;
        this.tmpFile.deleteOnExit();
    }

    /**
     * @return the pathExec
     */
    public String getPathExec() {
        return pathExec;
    }

    /**
     * @return the processBuilder
     */
    public FFmpegProcessBuilder getProcessBuilder() {
        if (processBuilder == null) {
            processBuilder = new FFmpegProcessBuilder();
        }
        return processBuilder;
    }

    /**
     * @param processBuilder the processBuilder to set
     */
    public void setProcessBuilder(FFmpegProcessBuilder processBuilder) {
        this.processBuilder = processBuilder;
    }
}