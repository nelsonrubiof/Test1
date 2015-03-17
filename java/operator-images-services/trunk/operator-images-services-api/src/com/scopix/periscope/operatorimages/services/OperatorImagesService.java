/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.scopix.periscope.operatorimages.services;

import java.io.OutputStream;

import javax.jws.WebService;
import javax.ws.rs.core.Response;

import com.scopix.periscope.operatorimages.MarksContainerDTO;
import com.scopix.periscope.operatorimages.MarksDTO;
import com.scopix.periscope.operatorimages.ResultMarksContainerDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
@WebService(name = "OperatorImagesService")
public interface OperatorImagesService {

    /**
     * 
     * @param corporateName
     * @param store
     * @param date
     * @param fileName
     * @return OutputStream que representa al video solicitado
     */
    OutputStream getVideo(String corporateName, String store, String date, String fileName) throws ScopixException;

    /**
     * Genera todos los proofs con marcas para una video en una posicion dada
     *
     * @param container Datos de las marcas para el SnapShot
     * @return ResultMarksContainer del archivo generado con marcas
     * @throws ScopixException Excepcion en caso de Error
     */
    ResultMarksContainerDTO generateProof(MarksContainerDTO container) throws ScopixException;

    /**
     * Genera de forma asíncrona todos los proofs con marcas para una video en una posicion dada
     *
     * @param container Datos de las marcas para el SnapShot
     * @throws ScopixException Excepcion en caso de Error
     */
    void generateAsynchronousProofs(MarksContainerDTO container) throws ScopixException;

    /**
     * Genera todos los proofs con marcas para una video en una posicion dada
     *
     * @param marcas Datos de las marcas para el SnapShot
     * @return nombre del archivo generado con marcas
     * @throws ScopixException Excepcion en caso de Error
     */
    String generateProofSimple(MarksDTO marcas) throws ScopixException;

    /**
     * Genera todos los proofs con marcas para una video en una posicion dada
     *
     * @param container Datos de las marcas para el SnapShot
     * @return ResultMarksContainer con nombres de archivos para metrica_evidencia
     * @throws ScopixException Excepcion en caso de Error
     */
    ResultMarksContainerDTO generateProofTMedio(MarksContainerDTO container) throws ScopixException;

    /**
     * 
     * @param corporateName
     * @param store
     * @param fileName
     * @return OutputStream que representa a la plantilla solicitada
     */
    OutputStream getTemplate(String corporateName, String store, String fileName) throws ScopixException;

    /**
     * 
     * @param corporateName
     * @param store
     * @param date
     * @param fileName
     * @return OutputStream que representa a la imágen solicitada
     */
    OutputStream getImage(String corporateName, String store, String date, String fileName) throws ScopixException;

    /**
     * 
     * @param corporateName
     * @param store
     * @param date
     * @param fileName
     * @param vttFileName
     * @return OutputStream que representa a la imágen solicitada
     */
    OutputStream getSpriteImage(String corporateName, String store, String date, String fileName, String vttFileName)
            throws ScopixException;

    /**
     * 
     * @param corporateName
     * @param store
     * @param date
     * @param fileName
     * @return OutputStream que representa a la imágen solicitada
     */
    OutputStream getVttVideoFile(String corporateName, String store, String date, String fileName) throws ScopixException;

    /**
     * Genera sprite images a partir del correspondiente video
     *
     * @param fileName ruta y nombre del archivo de video
     * @return Response respuesta con estado de la generación
     */
    Response generateVideoSprites(String fileName);

    /**
     * 
     * @param elapsedTime
     * @param corporateName
     * @param store
     * @param date
     * @param fileName
     * @return
     * @throws ScopixException
     */
    OutputStream getSnapshot(Double elapsedTime, String corporateName, String store, String date, String fileName)
            throws ScopixException;

    /**
     * 
     * @param corporateName
     * @param store
     * @param date
     * @param fileName
     * @return
     * @throws ScopixException
     */
    OutputStream getSnapshotTMedio(String corporateName, String store, String date, String fileName) throws ScopixException;

    /**
     * 
     * @param elapsedTime
     * @param markData
     * @param corporateName
     * @param store
     * @param date
     * @param fileName
     * @return
     * @throws ScopixException
     */
    OutputStream getSnapshotWithMark(Double elapsedTime, String markData, String corporateName, String store, String date,
            String fileName) throws ScopixException;

    /**
     * Retorna URLs de los sprites de los videos especifidados
     *
     * @param nombres de los videos
     * @return nombre de los URLs de los sprites
     * @throws ScopixException Excepcion en caso de Error
     */
    String getSpritesURLs(String videoNames) throws ScopixException;
}