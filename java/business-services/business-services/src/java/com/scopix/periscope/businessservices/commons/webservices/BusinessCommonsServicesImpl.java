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
 *  BusinessCommonsServicesImpl.java
 * 
 *  Created on 27-05-2014, 05:16:14 PM
 * 
 */
package com.scopix.periscope.businessservices.commons.webservices;

import com.scopix.periscope.businessservices.commons.FileDownload;
import com.scopix.periscope.businessservices.commons.FileDownloadList;
import com.scopix.periscope.evaluationmanagement.EvaluationManager;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.log4j.Logger;

/**
 *
 * @author Nelson
 */
@WebService(endpointInterface = "com.scopix.periscope.businessservices.commons.webservices.BusinessCommonsServices")
@SpringBean(rootClass = BusinessCommonsServices.class)
public class BusinessCommonsServicesImpl implements BusinessCommonsServices {

    public static Logger log = Logger.getLogger(BusinessCommonsServicesImpl.class);
    private EvaluationManager evaluationManager;
    private ExtractionPlanManager extractionPlanManager;

    @GET
    @Override
    @Path("/expirePendingEvaluations/{date}")
    public Response expirePendingEvaluations(@PathParam(value = "date") String date) throws ScopixWebServiceException {
        log.info("start");
        //debe busacar todas situatciones live y obtener todos los pending evaluation que tengan evidencias de mas de los minitos 
        //de expiracion de la situacion
        Integer cont = 0;
        try {
            List<Integer> pendingEvaluationExpired = getEvaluationManager().expirePendingEvaluations(date);
            for (Integer peId : pendingEvaluationExpired) {
                getEvaluationManager().expiredPendingEvaluation(peId);
            }

        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end");
        return Response.ok(cont + " Pending Evaluations Expired").build();
    }

    @POST
    @Path("/uploadExtractionPlanCustomizing")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Override
    public Response uploadExtractionPlanCustomizing(@Multipart(value = "csv") List<Attachment> csv, @Context HttpServletRequest request)
        throws ScopixWebServiceException {
        log.info("start");
        String realPath = request.getRealPath("/");
        for (Attachment attachment : csv) {
            DataHandler handler = attachment.getDataHandler();
            try {
                InputStream stream = handler.getInputStream();
                MultivaluedMap<String, String> map = attachment.getHeaders();
                String fileName = getFileName(map);
                log.debug("fileName Here " + fileName);
                File tmp = File.createTempFile("csv", "." + FilenameUtils.getExtension(fileName));

                OutputStream out = new FileOutputStream(tmp);

                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = stream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                stream.close();
                out.flush();
                out.close();
                //upload file process file
                getExtractionPlanManager().processNewEPCCSV(tmp);
                //move tmp to processed 
                String datePath = DateFormatUtils.format(new Date(), "yyyyMMdd");
                String newFileName = "EPC_"
                    + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "." + FilenameUtils.getExtension(fileName);
                FileUtils.copyFile(tmp, new File(realPath + "/epc_upload/" + datePath + "/" + newFileName));
                try {
                    FileUtils.forceDelete(tmp);
                } catch (IOException e) {
                    log.warn("not delete file tmp " + e);
                }
            } catch (IOException e) {
                log.error(e, e);
                return Response.status(Response.Status.OK).entity(e.getMessage()).build();
            } catch (ScopixException e) {
                log.error(e, e);
                return Response.status(Response.Status.OK).entity(e.getMessage()).build();
            }

        }
        log.info("end");
        return Response.ok("File Uploaded").build();
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        log.info("start");
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String exactFileName = name[1].trim().replaceAll("\"", "");
                return exactFileName;
            }
        }
        log.info("end");
        return "unknown";
    }

    /**
     * @return the evaluationManager
     */
    public EvaluationManager getEvaluationManager() {
        if (evaluationManager == null) {
            evaluationManager = SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class);
        }
        return evaluationManager;
    }

    /**
     * @param evaluationManager the evaluationManager to set
     */
    public void setEvaluationManager(EvaluationManager evaluationManager) {
        this.evaluationManager = evaluationManager;
    }

    /**
     * @return the extractionPlanManager
     */
    public ExtractionPlanManager getExtractionPlanManager() {
        if (extractionPlanManager == null) {
            extractionPlanManager = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class);
        }
        return extractionPlanManager;
    }

    /**
     * @param extractionPlanManager the extractionPlanManager to set
     */
    public void setExtractionPlanManager(ExtractionPlanManager extractionPlanManager) {
        this.extractionPlanManager = extractionPlanManager;
    }

    @GET
    @Path("/allFiles")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public FileDownloadList getAllFiles(@Context HttpServletRequest request) throws ScopixWebServiceException {
        log.info("start");
        LinkedList l = new LinkedList<FileDownload>();
        try {
            String realPath = request.getRealPath("/") + "epc_upload";
            log.debug("realPath " + realPath);
            Collection files = FileUtils.listFiles(new File(realPath), new String[]{"csv"}, true);
            String folder = "";

            for (Iterator iterator = files.iterator(); iterator.hasNext();) {
                File file = (File) iterator.next();

                folder = FilenameUtils.getBaseName(FilenameUtils.getPathNoEndSeparator(file.getAbsolutePath()));

                FileDownload fd = new FileDownload();
                fd.setFileName(file.getName());
                fd.setFolder(folder);
                log.debug("[folder:" + folder + "][file.getName():" + file.getName() + "]");
                l.add(fd);

            }
            LinkedHashMap<String, Boolean> colsSort = new LinkedHashMap<String, Boolean>();
            colsSort.put("fileName", Boolean.TRUE);
            SortUtil.sortByColumn(colsSort, l);
        } catch (Exception e) {
            log.warn(e);
        }
        FileDownloadList sl = new FileDownloadList();
        sl.setData(l);
        log.info("end");
        return sl;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/getFile/{folder}/{fileName}")
    @Override
    public OutputStream getFile(@PathParam(value = "folder") String folder, @PathParam(value = "fileName") String fileName,
        @Context HttpServletRequest request, @Context HttpServletResponse response) throws ScopixWebServiceException {
        String realPath = request.getRealPath("/") + "epc_upload";
        File f = new File(realPath + "/" + folder + "/" + fileName);
        response.setContentType("application/force-download");
        response.setContentLength((int) f.length());
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        OutputStream ret = fileToOutputStream(f, response, false);
        return ret;
    }

    private OutputStream fileToOutputStream(File f, HttpServletResponse response, boolean forceDelete) {
        log.info("start [filename:" + f.getAbsolutePath() + "][forceDelete:" + forceDelete + "]");
        OutputStream ret = null;
        try {
            ret = response.getOutputStream();
            ret.write(FileUtils.readFileToByteArray(f));
            ret.close();
            if (forceDelete) {
                String archivo = FilenameUtils.separatorsToUnix(f.getAbsolutePath());
                log.debug("Disponiendose a borrar archivo: " + FilenameUtils.getName(archivo));
                FileUtils.forceDelete(f);
                log.debug("Archivo borrado: " + FilenameUtils.getName(archivo));
            }
        } catch (IOException e) {
            log.error(e, e);
        } catch (Exception e) {
            log.error(e, e);
        }
        log.info("end");
        return ret;
    }

}
