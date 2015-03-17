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
 * OperatorImagesServiceImpl.java
 * 
 * Created on 18-04-2013, 05:45:07 PM
 */
package com.scopix.periscope.operatorimages.services;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.scopix.periscope.enums.EnumEvidenceType;
import com.scopix.periscope.operatorimages.Marks;
import com.scopix.periscope.operatorimages.MarksContainer;
import com.scopix.periscope.operatorimages.MarksContainerDTO;
import com.scopix.periscope.operatorimages.MarksDTO;
import com.scopix.periscope.operatorimages.OperatorImageManager;
import com.scopix.periscope.operatorimages.Range;
import com.scopix.periscope.operatorimages.ResultMarks;
import com.scopix.periscope.operatorimages.ResultMarksContainerDTO;
import com.scopix.periscope.operatorimages.ResultMarksDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
@WebService(endpointInterface = "com.scopix.periscope.operatorimages.services.OperatorImagesService")
@SpringBean(rootClass = OperatorImagesService.class)
public class OperatorImagesServiceImpl implements OperatorImagesService {

    private HttpServletUtils servletUtils;
    private OperatorImageManager imageManager;
    private final int CACHE_DURATION_IN_SECOND = 60 * 60 * 24; // 1 day
    private final long CACHE_DURATION_IN_MS = CACHE_DURATION_IN_SECOND * 1000;
    private static Logger log = Logger.getLogger(OperatorImagesServiceImpl.class);

    /**
     * Retorna un SnapShot de un video en una posicion dada
     * 
     * @param elapsedTime
     * @param corporateName
     * @param store
     * @param date
     * @param fileName
     * @return
     * @throws ScopixException
     */
    @GET
    @Produces("image/jpeg;type=image/jpeg")
    @Path("/getSnapshot/{elapsedTime}/{corporateName}/{store}/{date}/{fileName}")
    @Override
    public OutputStream getSnapshot(@PathParam("elapsedTime") Double elapsedTime,
            @PathParam("corporateName") String corporateName, @PathParam("store") String store, @PathParam("date") String date,
            @PathParam("fileName") String fileName) throws ScopixException {

        log.info("start");
        HttpServletResponse response = getResponse();
        setCacheHeaders(response);

        String completeFilePath = getImageManager().processFilePath(corporateName, store, date, fileName,
                EnumEvidenceType.VIDEO.toString(), null);

        OutputStream out = getImageManager().getSnapshot(elapsedTime, completeFilePath, response);
        log.info("end");
        return out;
    }

    /**
     * Retorna un SnapShot de un video en la mitad del mismo
     * 
     * @param corporateName
     * @param store
     * @param date
     * @param fileName
     * @return
     * @throws ScopixException
     */
    @GET
    @Produces("image/jpeg;type=image/jpeg")
    @Path("/getSnapshotTMedio/{corporateName}/{store}/{date}/{fileName}")
    @Override
    public OutputStream getSnapshotTMedio(@PathParam("corporateName") String corporateName, @PathParam("store") String store,
            @PathParam("date") String date, @PathParam("fileName") String fileName) throws ScopixException {

        log.info("start");
        HttpServletResponse response = getResponse();
        setCacheHeaders(response);

        String completeFilePath = getImageManager().processFilePath(corporateName, store, date, fileName,
                EnumEvidenceType.VIDEO.toString(), null);

        OutputStream out = getImageManager().getSnapshot(completeFilePath, response);
        log.info("end");
        return out;
    }

    /**
     * Retorna imágen con marca en una posición determinada de un video
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
    @GET
    @Produces("image/jpeg;type=image/jpeg")
    @Path("/getSnapshotWithMark/{elapsedTime}/{markData}/{corporateName}/{store}/{date}/{fileName}")
    @Override
    public OutputStream getSnapshotWithMark(@PathParam("elapsedTime") Double elapsedTime, @PathParam("markData") String markData,
            @PathParam("corporateName") String corporateName, @PathParam("store") String store, @PathParam("date") String date,
            @PathParam("fileName") String fileName) throws ScopixException {

        log.info("start");
        HttpServletResponse response = getResponse();
        setCacheHeaders(response);

        String completeFilePath = getImageManager().processFilePath(corporateName, store, date, fileName,
                EnumEvidenceType.VIDEO.toString(), null);

        OutputStream out = getImageManager().getSnapshotWithMark(elapsedTime, completeFilePath, markData, response);
        log.info("end");
        return out;
    }

    /**
     * 
     * @param corporateName
     * @param store
     * @param fileName
     * @return OutputStream que representa a la plantilla solicitada
     */
    @GET
    @Produces("image/png;type=image/png")
    @Path("/getTemplate/{corporateName}/{store}/{fileName}")
    @Override
    public OutputStream getTemplate(@PathParam("corporateName") String corporateName, @PathParam("store") String store,
            @PathParam("fileName") String fileName) throws ScopixException {

        log.info("start");
        HttpServletResponse response = getResponse();
        String completeFilePath = getImageManager().processFilePath(corporateName, store, null, fileName,
                EnumEvidenceType.TEMPLATE.toString(), null);

        int CACHE_DURATION_IN_SECOND = 60 * 30; // 30 minutes
        long CACHE_DURATION_IN_MS = CACHE_DURATION_IN_SECOND * 1000;

        long now = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", now);
        response.setDateHeader("Expires", now + CACHE_DURATION_IN_MS);
        response.addHeader("Cache-Control", "public, max-age=" + CACHE_DURATION_IN_SECOND);

        OutputStream out = getImageManager().getTemplate(completeFilePath, response);
        log.info("end");
        return out;
    }

    /**
     * 
     * @param corporateName
     * @param store
     * @param date
     * @param fileName
     * @return OutputStream que representa a la imagen solicitada
     * @throws ScopixException
     */
    @GET
    @Produces("image/jpeg;type=image/jpeg")
    @Path("/getImage/{corporateName}/{store}/{date}/{fileName}")
    @Override
    public OutputStream getImage(@PathParam("corporateName") String corporateName, @PathParam("store") String store,
            @PathParam("date") String date, @PathParam("fileName") String fileName) throws ScopixException {

        log.info("start, fileName: [" + fileName + "]");
        HttpServletResponse response = getResponse();
        setCacheHeaders(response);

        String completeFilePath = getImageManager().processFilePath(corporateName, store, date, fileName,
                EnumEvidenceType.IMAGE.toString(), null);

        OutputStream out = getImageManager().getMediaFile(completeFilePath, response);
        log.info("end");
        return out;
    }

    /**
     * 
     * @param response
     */
    private void setCacheHeaders(HttpServletResponse response) {
        log.info("start");
        long now = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", now);
        response.setDateHeader("Expires", now + CACHE_DURATION_IN_MS);
        response.addHeader("Cache-Control", "public, max-age=" + CACHE_DURATION_IN_SECOND);
        log.info("end");
    }

    /**
     * 
     * @param corporateName
     * @param store
     * @param date
     * @param fileName
     * @param vttFile
     * @return OutputStream que representa a la imagen solicitada
     * @throws ScopixException
     */
    @GET
    @Produces("image/jpeg;type=image/jpeg")
    @Path("/getSpriteImage/{corporateName}/{store}/{date}/{fileName}/{vttFile}")
    @Override
    public OutputStream getSpriteImage(@PathParam("corporateName") String corporateName, @PathParam("store") String store,
            @PathParam("date") String date, @PathParam("fileName") String fileName, @PathParam("vttFile") String vttFileName)
            throws ScopixException {

        log.info("start, fileName: [" + fileName + "]");
        HttpServletResponse response = getResponse();
        setCacheHeaders(response);

        String completeFilePath = getImageManager().processFilePath(corporateName, store, date, fileName,
                EnumEvidenceType.SPRITE.toString(), vttFileName);

        OutputStream out = getImageManager().getMediaFile(completeFilePath, response);
        log.info("end");
        return out;
    }

    /**
     * Retorna archivo plano con información de sprites del video requerido
     *
     * @param fileName video de dónde obtener el snapshot
     * @return OutputStream representando al archivo plano
     * @throws ScopixException excepción en caso de error
     */
    @GET
    @Produces("text/plain;type=text/plain")
    @Path("/getVttVideoFile/{corporateName}/{store}/{date}/{fileName}")
    @Override
    public OutputStream getVttVideoFile(@PathParam("corporateName") String corporateName, @PathParam("store") String store,
            @PathParam("date") String date, @PathParam("fileName") String fileName) throws ScopixException {

        log.info("start");
        HttpServletResponse response = getResponse();
        setCacheHeaders(response);
        response.setHeader("Access-Control-Allow-Origin", "*");

        String completeFilePath = getImageManager().processFilePath(corporateName, store, date, fileName,
                EnumEvidenceType.VTT.toString(), null);

        OutputStream out = getImageManager().getVttVideoFile(completeFilePath, response);
        log.info("end");
        return out;
    }

    /**
     * Lee un archivo de video para ser retornado en el flujo de salida
     *
     * @author carlos polo
     * @param fileName ubicación del archivo de video
     * @date 30-sep-2013
     * @return OutputStream video
     *
     *         net/balusc/webapp/FileServlet.java
     *
     *         Copyright (C) 2009 BalusC
     *
     *         This program is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
     *         Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
     *         any later version.
     * 
     *         This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
     *         warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
     *         details.
     * 
     *         You should have received a copy of the GNU Lesser General Public License along with this library. If not, see
     *         <http://www.gnu.org/licenses/>.
     * @throws ScopixException
     */
    @GET
    @Produces("video/mp4;type=video/mp4")
    @Path("/getVideo/{corporateName}/{store}/{date}/{fileName}")
    @Override
    public OutputStream getVideo(@PathParam("corporateName") String corporateName, @PathParam("store") String store,
            @PathParam("date") String date, @PathParam("fileName") String fileName) throws ScopixException {

        log.info("start, fileName: [" + fileName + "]");
        boolean content = true;

        RandomAccessFile input = null;
        OutputStream output = null;

        HttpServletResponse response = getResponse();
        HttpServletRequest request = getServletUtils().getRequest();

        String completeFilePath = getImageManager().processFilePath(corporateName, store, date, fileName,
                EnumEvidenceType.VIDEO.toString(), null);

        // Prepare some variables. The ETag is an unique identifier of the file.
        File file = new File(completeFilePath);
        long length = file.length();
        long lastModified = file.lastModified();
        String eTag = fileName + "_" + length + "_" + lastModified;
        long expires = System.currentTimeMillis() + 604800000L;

        try {
            // Validate request headers for caching ---------------------------------------------------

            // If-None-Match header should contain "*" or ETag. If so, then return 304.
            String ifNoneMatch = request.getHeader("If-None-Match");
            if (ifNoneMatch != null && matches(ifNoneMatch, eTag)) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.setHeader("ETag", eTag); // Required in 304.
                response.setDateHeader("Expires", expires); // Postpone cache with 1 week.
                return response.getOutputStream();
            }

            // If-Modified-Since header should be greater than LastModified. If so, then return 304.
            // This header is ignored if any If-None-Match header is specified.
            long ifModifiedSince = request.getDateHeader("If-Modified-Since");
            if (ifNoneMatch == null && ifModifiedSince != -1 && ifModifiedSince + 1000 > lastModified) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.setHeader("ETag", eTag); // Required in 304.
                response.setDateHeader("Expires", expires); // Postpone cache with 1 week.
                return response.getOutputStream();
            }

            // Validate request headers for resume ----------------------------------------------------

            // If-Match header should contain "*" or ETag. If not, then return 412.
            String ifMatch = request.getHeader("If-Match");
            if (ifMatch != null && !matches(ifMatch, eTag)) {
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
                return response.getOutputStream();
            }

            // If-Unmodified-Since header should be greater than LastModified. If not, then return 412.
            long ifUnmodifiedSince = request.getDateHeader("If-Unmodified-Since");
            if (ifUnmodifiedSince != -1 && ifUnmodifiedSince + 1000 <= lastModified) {
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
                return response.getOutputStream();
            }

            // Validate and process range -------------------------------------------------------------

            // Prepare some variables. The full Range represents the complete file.
            Range full = new Range(0, length - 1, length);
            List<Range> ranges = new ArrayList<Range>();

            // Validate and process Range and If-Range headers.
            String range = request.getHeader("Range");
            if (range != null) {
                // Range header should match format "bytes=n-n,n-n,n-n...". If not, then return 416.
                if (!range.matches("^bytes=\\d*-\\d*(,\\d*-\\d*)*$")) {
                    response.setHeader("Content-Range", "bytes */" + length); // Required in 416.
                    response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                    return response.getOutputStream();
                }

                // If-Range header should either match ETag or be greater then LastModified. If not,
                // then return full file.
                String ifRange = request.getHeader("If-Range");
                if (ifRange != null && !ifRange.equals(eTag)) {
                    try {
                        long ifRangeTime = request.getDateHeader("If-Range"); // Throws IAE if invalid.
                        if (ifRangeTime != -1 && ifRangeTime + 1000 < lastModified) {
                            ranges.add(full);
                        }
                    } catch (IllegalArgumentException ignore) {
                        ranges.add(full);
                    }
                }

                // If any valid If-Range header, then process each part of byte range.
                if (ranges.isEmpty()) {
                    for (String part : range.substring(6).split(",")) {
                        // Assuming a file with length of 100, the following examples returns bytes at:
                        // 50-80 (50 to 80), 40- (40 to length=100), -20 (length-20=80 to length=100).
                        long start = sublong(part, 0, part.indexOf("-"));
                        long end = sublong(part, part.indexOf("-") + 1, part.length());

                        if (start == -1) {
                            start = length - end;
                            end = length - 1;
                        } else if (end == -1 || end > length - 1) {
                            end = length - 1;
                        }

                        // Check if Range is syntactically valid. If not, then return 416.
                        if (start > end) {
                            response.setHeader("Content-Range", "bytes */" + length); // Required in 416.
                            response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                            return response.getOutputStream();
                        }

                        // Add range.
                        ranges.add(new Range(start, end, length));
                    }
                }
            }

            // Prepare and initialize response --------------------------------------------------------

            // Get content type by file name and set default GZIP support and content disposition.
            String disposition = "inline";

            // Initialize response.
            response.reset();
            response.setBufferSize(10240);
            response.setHeader("Content-Disposition", disposition + ";filename=\"" + fileName + "\"");
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("ETag", eTag);
            response.setDateHeader("Last-Modified", lastModified);
            response.setDateHeader("Expires", expires);

            // Send requested file (part(s)) to client ------------------------------------------------

            // Prepare streams
            // Open streams.
            input = new RandomAccessFile(file, "r");
            output = response.getOutputStream();

            if (ranges.isEmpty() || ranges.get(0) == full) {

                // Return full file.
                Range r = full;
                response.setContentType("video/mp4");
                response.setHeader("Content-Range", "bytes " + r.start + "-" + r.end + "/" + r.total);

                if (content) {
                    // Content length is not directly predictable in case of GZIP.
                    // So only add it if there is no means of GZIP, else browser will hang.
                    response.setHeader("Content-Length", String.valueOf(r.length));

                    // Copy full range.
                    copy(input, output, r.start, r.length);
                }

            } else if (ranges.size() == 1) {

                // Return single part of file.
                Range r = ranges.get(0);
                response.setContentType("video/mp4");
                response.setHeader("Content-Range", "bytes " + r.start + "-" + r.end + "/" + r.total);
                response.setHeader("Content-Length", String.valueOf(r.length));
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206.

                if (content) {
                    // Copy single part range.
                    copy(input, output, r.start, r.length);
                }
            }

        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }

        // OutputStream out = getImageManager().getMediaFile(fileName, response);
        log.info("end fileName: [" + fileName + "]");
        return output;
    }

    /**
     * Close the given resource.
     * 
     * @param resource The resource to be closed.
     */
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException ignore) {
                log.error(ignore.getMessage(), ignore);
                // Ignore IOException. If you want to handle this anyway, it might be useful to know
                // that this will generally only be thrown when the client aborted the request.
            }
        }
    }

    /**
     * Copy the given byte range of the given input to the given output.
     * 
     * @param input The input to copy the given range to the given output for.
     * @param output The output to copy the given range from the given input for.
     * @param start Start of the byte range.
     * @param length Length of the byte range.
     * @throws IOException If something fails at I/O level.
     */
    private static void copy(RandomAccessFile input, OutputStream output, long start, long length) throws IOException {
        byte[] buffer = new byte[10240];
        int read;

        if (input.length() == length) {
            // Write full range.
            while ((read = input.read(buffer)) > 0) {
                output.write(buffer, 0, read);
            }
        } else {
            // Write partial range.
            input.seek(start);
            long toRead = length;

            while ((read = input.read(buffer)) > 0) {
                if ((toRead -= read) > 0) {
                    output.write(buffer, 0, read);
                } else {
                    output.write(buffer, 0, (int) toRead + read);
                    break;
                }
            }
        }
    }

    /**
     * Returns true if the given match header matches the given value.
     * 
     * @param matchHeader The match header.
     * @param toMatch The value to be matched.
     * @return True if the given match header matches the given value.
     */
    private static boolean matches(String matchHeader, String toMatch) {
        String[] matchValues = matchHeader.split("\\s*,\\s*");
        Arrays.sort(matchValues);
        return Arrays.binarySearch(matchValues, toMatch) > -1 || Arrays.binarySearch(matchValues, "*") > -1;
    }

    /**
     * Returns a substring of the given string value from the given begin index to the given end index as a long. If the substring
     * is empty, then -1 will be returned
     * 
     * @param value The string value to return a substring as long for.
     * @param beginIndex The begin index of the substring to be returned as long.
     * @param endIndex The end index of the substring to be returned as long.
     * @return A substring of the given string value as long or -1 if substring is empty.
     */
    private static long sublong(String value, int beginIndex, int endIndex) {
        String substring = value.substring(beginIndex, endIndex);
        return (substring.length() > 0) ? Long.parseLong(substring) : -1;
    }

    /**
     * Genera un proof con marcas para una video en una posicion dada
     *
     * @param containerDto Datos de las marcas para el SnapShot
     * @return ResultMarksContainer del archivo generado con marcas
     * @throws ScopixException Excepcion en caso de Error
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/generateProof")
    @Override
    public ResultMarksContainerDTO generateProof(MarksContainerDTO containerDto) throws ScopixException {
        log.info("start");
        MarksContainer container = new MarksContainer();
        // convertimos el dto a Objecto de nuestro modelo
        List<ResultMarksDTO> lRet = new ArrayList<ResultMarksDTO>();
        if (containerDto.getMarks() != null && !containerDto.getMarks().isEmpty()) {
            container = (MarksContainer) ConvertDTOToObject.convert(containerDto, container);

            List<ResultMarks> list = getImageManager().generateProof(container.getMarks());

            for (ResultMarks resultMark : list) {
                ResultMarksDTO resultMarkDTO = new ResultMarksDTO();
                resultMarkDTO = (ResultMarksDTO) ConvertDTOToObject.convert(resultMark, resultMarkDTO);
                log.debug("resultMarkDTO fileName: [" + resultMarkDTO.getFileName() + "]");
                lRet.add(resultMarkDTO);
            }
        }

        ResultMarksContainerDTO ret = new ResultMarksContainerDTO();
        ret.setResults(lRet);
        log.info("end " + lRet.size());
        return ret;
    }

    /**
     * Genera asíncronamente proofs con marcas para una video en una posicion dada
     *
     * @param containerDto Datos de las marcas para el SnapShot
     * @throws ScopixException Excepcion en caso de Error
     */
    @POST
    @Consumes("application/json")
    @Path("/generateAsynchronousProofs")
    @Override
    public void generateAsynchronousProofs(MarksContainerDTO containerDto) throws ScopixException {
        log.info("start");
        getImageManager().generateAsynchronousProofs(containerDto);
        log.info("end");
    }

    /**
     * Genera un proof con marcas para una video en una posicion dada
     *
     * @param containerDto Datos de las marcas para el SnapShot
     * @return String con el nombre del proof generado
     * @throws ScopixException Excepcion en caso de Error
     */
    @POST
    @Consumes("application/json")
    @Path("/generateProofTMedio")
    @Override
    public ResultMarksContainerDTO generateProofTMedio(MarksContainerDTO containerDto) throws ScopixException {
        log.info("start");
        MarksContainer container = new MarksContainer();
        List<ResultMarksDTO> lRet = new ArrayList<ResultMarksDTO>();
        if (containerDto.getMarks() != null && !containerDto.getMarks().isEmpty()) {
            container = (MarksContainer) ConvertDTOToObject.convert(containerDto, container);
            List<ResultMarks> list = getImageManager().generateProof(container.getMarks());

            for (ResultMarks rm : list) {
                ResultMarksDTO rmdto = new ResultMarksDTO();
                rmdto = (ResultMarksDTO) ConvertDTOToObject.convert(rm, rmdto);
                lRet.add(rmdto);
            }

        }
        ResultMarksContainerDTO ret = new ResultMarksContainerDTO();
        ret.setResults(lRet);
        log.info("end " + lRet.size());
        return ret;
    }

    /**
     * Genera un proof con marcas para una video en una posicion dada
     *
     * @param marcasDto Datos de las marcas para el SnapShot
     * @return String con el nombre del proof generado
     * @throws ScopixException Excepcion en caso de Error
     */
    @POST
    @Consumes("application/json")
    @Path("/generateProofSimple")
    @Override
    public String generateProofSimple(MarksDTO marcasDto) throws ScopixException {
        log.info("start");
        Marks marcas = new Marks();
        marcas = (Marks) ConvertDTOToObject.convert(marcasDto, marcas);
        ResultMarks resultMarks = getImageManager().generateProof(marcas, new HashMap<String, BufferedImage>());
        return resultMarks.getFileName();
    }

    /**
     * Genera sprite images a partir del correspondiente video
     *
     * @param fileName ruta y nombre del archivo de video
     * @return Response respuesta con estado de la generación
     */
    @POST
    @Consumes("application/json")
    @Path("/generateVideoSprites")
    @Override
    public Response generateVideoSprites(String fileName) {
        log.info("start");
        getImageManager().generateVideoSprites(fileName);
        log.info("end");
        return Response.ok().build();
    }

    /**
     * 
     * @param videoNames
     * @return
     * @throws ScopixException
     */
    @POST
    @Consumes("application/json")
    @Path("/getSpritesURLs")
    @Override
    public String getSpritesURLs(String videoNames) throws ScopixException {
        log.info("start, videoNames: [" + videoNames + "]");
        String spritesURLs = getImageManager().getSpritesURLs(videoNames);
        log.info("end, spritesURLs: [" + spritesURLs + "]");
        return spritesURLs;
    }

    /**
     * @return the imageManager
     */
    public OperatorImageManager getImageManager() {
        if (imageManager == null) {
            imageManager = SpringSupport.getInstance().findBeanByClassName(OperatorImageManager.class);
        }
        return imageManager;
    }

    /**
     * @param imageManager the imageManager to set
     */
    public void setImageManager(OperatorImageManager imageManager) {
        this.imageManager = imageManager;
    }

    private HttpServletResponse getResponse() {
        return getServletUtils().getResponse();
    }

    /**
     * @return the servletUtils
     */
    public HttpServletUtils getServletUtils() {
        if (servletUtils == null) {
            servletUtils = new HttpServletUtils();
        }
        return servletUtils;
    }

    /**
     * @param servletUtils the servletUtils to set
     */
    public void setServletUtils(HttpServletUtils servletUtils) {
        this.servletUtils = servletUtils;
    }
}