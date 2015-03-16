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
 *  ZipCompresser.java
 * 
 *  Created on 26-07-2011, 06:09:35 PM
 * 
 */
package com.scopix.periscope.businesswarehouse.transfer.utils;

import com.scopix.periscope.businesswarehouse.transfer.TransferProofFilesLog;
import com.scopix.periscope.businesswarehouse.transfer.TransferProofFilesStatus;
import com.scopix.periscope.businesswarehouse.transfer.commands.AddTransferProofFilesLogCommand;
import com.scopix.periscope.businesswarehouse.transfer.commands.GetProofListCommand;
import com.scopix.periscope.evaluationmanagement.Proof;
import com.scopix.periscope.evaluationmanagement.commands.UpdateProofsTransferProofFileLogCommand;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class ZipCompresser {

    private static Logger log = Logger.getLogger(ZipCompresser.class);
    public static final int BUFFER = 2048;
    private ZipOutputStream zipOutputStreamCurrent;
    private String basePath = null;
    private File dir = null;
    private String prefixZip;
    private long currentSize;
    private String path;
    private int currentChunkIndex;
    private String zipCurrentName;
    private Set<Integer> currentProofIds;
    private static final long MAX_FILE_SIZE = 104857600; // Whatever size you want 100M aprox
    private static final String FILE_EXTENSION = ".zip";
    private Map<Integer, Set<String>> filesToCompress;
    private List<TransferProofFilesLog> processed;

    public ZipCompresser(String basePathFile, String prefix, String pathDestination, Map<Integer, Set<String>> filesCompress,
            String directory) {
        basePath = basePathFile;
        if (directory != null && directory.length() > 0) {
            dir = new File(directory);
        }
        prefixZip = prefix;
        path = pathDestination;
        filesToCompress = filesCompress;
        processed = new ArrayList<TransferProofFilesLog>();
    }

    public List<TransferProofFilesLog> compress() throws ScopixException {
        //crear un list para la transferencia
        try {
            if (dir != null) {
                compressDir(dir);
            } else {
                compressFiles();
            }
            if (currentSize <= MAX_FILE_SIZE && currentSize > 0) {
                createNewTransferLog();
            }
            if (zipOutputStreamCurrent != null) { //no fue creado el zip
                zipOutputStreamCurrent.setMethod(ZipOutputStream.DEFLATED);
                zipOutputStreamCurrent.close();
            } else {
                log.info("No se ha creado Zip");
            }
        } catch (IOException e) {
            throw new ScopixException("Something wrong in compresser: " + e, e);
        }
        return processed;
    }

    /**
     * Descomprime un Zip en una ruta especifica
     */
    public static void deCompress(String fileName, String decompressPath) {
        try {
            BufferedOutputStream dest = null;
            FileInputStream fis = new FileInputStream(fileName);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                log.info("Extracting: " + entry);
                int count;
                byte[] data = new byte[BUFFER];
                // write the files to the disk
                String pathFile = FilenameUtils.getFullPathNoEndSeparator(decompressPath + entry.getName());
                File f = new File(pathFile);
                if (!f.exists()) {
                    try {
                        FileUtils.forceMkdir(f);
                    } catch (IOException e) {
                        log.warn("No es posible crear directirios " + f.getAbsolutePath() + " " + e);
                    }
                    //creamos el directorio
                    //if (!f.mkdirs()) {

                    //}
                }
                //descomprimimos
                FileOutputStream fos = new FileOutputStream(decompressPath + entry.getName());
                dest = new BufferedOutputStream(fos, BUFFER);
                while ((count = zis.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, count);
                }
                dest.flush();
                dest.close();
            }
            zis.close();
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Recorre la estructura de directorios
     */
    private void compressDir(File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    log.info("calling CompressOneDir with:" + file.getPath());
                    compressDir(file);
                }
                if (file.isFile()) {
                    log.info("adding file:" + file.getPath());
                    addOneFile(0, file.getPath());
                }
            }
        }
    }

    /**
     * Recorremos una lista de Archivos recibidos
     */
    private void compressFiles() {
        for (Integer proofId : filesToCompress.keySet()) {
            Set<String> filePaths = filesToCompress.get(proofId);
            for (String filePath : filePaths) {
                log.info("adding file:" + FilenameUtils.separatorsToSystem(basePath + filePath));
                addOneFile(proofId, FilenameUtils.separatorsToSystem(filePath));
            }
        }
    }

    /**
     * Comprime un file y lo agrega al zip que esta en el current
     */
    private void addOneFile(Integer proofId, String filePath) {
        FileInputStream fileinputstream = null;
        try {
            log.info("fileEntry: " + filePath);
            ZipEntry zipentry = new ZipEntry(filePath);

            CRC32 crc32 = new CRC32();

            byte[] rgb = new byte[BUFFER];
            int n;

            //Compute CRC of input stream
            File file = new File(FilenameUtils.separatorsToSystem(basePath + filePath));
            fileinputstream = new FileInputStream(file);
            while ((n = fileinputstream.read(rgb)) > -1) {
                crc32.update(rgb, 0, n);
            }
            fileinputstream.close();

            //Set Up Zip Entry
            zipentry.setSize(file.length());
            zipentry.setTime(file.lastModified());
            zipentry.setCrc(crc32.getValue());
            //solo se crea si existe un archivo
            if (zipOutputStreamCurrent == null && zipentry.getSize() > 0) {
                constructNewStream();
            }

            if (zipentry.getSize() + currentSize > MAX_FILE_SIZE) {
                closeStream();
                createNewTransferLog();
                constructNewStream();
            }
            currentProofIds.add(proofId);
            //Write Data
            fileinputstream = new FileInputStream(file);
            zipOutputStreamCurrent.putNextEntry(zipentry);
            while ((n = fileinputstream.read(rgb)) > -1) {
                zipOutputStreamCurrent.write(rgb, 0, n);
            }
            zipOutputStreamCurrent.closeEntry();
            currentSize += zipentry.getSize();
        } catch (IOException e) {
            log.error("No es posible crear el Archivo [e:" + e + "]");
        } finally {
            try {
                if (fileinputstream != null) {
                    fileinputstream.close();
                }
            } catch (IOException e2) {
                log.error("NO se pudo cerrar el Entry", e2);
            }
        }
    }

    /**
     * Cierrar el Zip que se encuentra Current
     */
    public void closeStream() throws IOException {
        zipOutputStreamCurrent.close();
    }

    /**
     * Crea un Zip y lo deja current
     */
    private void constructNewStream() throws FileNotFoundException {
        zipCurrentName = constructCurrentPartName();
        zipOutputStreamCurrent = new ZipOutputStream(new FileOutputStream(new File(path, zipCurrentName)));
        currentChunkIndex++;
        currentSize = 0;
        currentProofIds = new HashSet<Integer>();

    }

    /**
     * Crea la estructura de nombre para el nuevo Zip
     * <file_name>.part.0.zip a <file_name>.part.N.zip
     */
    private String constructCurrentPartName() {
        StringBuilder partNameBuilder = new StringBuilder(prefixZip + ".");
        partNameBuilder.append(currentChunkIndex);
        partNameBuilder.append(FILE_EXTENSION);
        return partNameBuilder.toString();
    }

    private void createNewTransferLog() {
        log.info("start");
        TransferProofFilesLog transferProofFilesLog = new TransferProofFilesLog();
        transferProofFilesLog.setFileName(zipCurrentName);

        transferProofFilesLog.setStatus(TransferProofFilesStatus.CREATED);
        transferProofFilesLog.setUploadDate(new Date());
        //Guardar transferProofFilesLog
        AddTransferProofFilesLogCommand addTransferProofFilesLogCommand = new AddTransferProofFilesLogCommand();
        addTransferProofFilesLogCommand.execute(transferProofFilesLog);

        UpdateProofsTransferProofFileLogCommand updateProofsTransferProofFileLogCommand =
                new UpdateProofsTransferProofFileLogCommand();
        
        log.debug("transferProofFilesLogId: " + transferProofFilesLog.getId());
        updateProofsTransferProofFileLogCommand.execute(currentProofIds, transferProofFilesLog.getId());
        
        GetProofListCommand getProofListCommand = new GetProofListCommand();
        List<Proof> proofList = getProofListCommand.execute(currentProofIds);
        transferProofFilesLog.setProofs(proofList);
        /*
         List<Proof> proofs = new ArrayList<Proof>();
         GetProofCommand getProofCommand = new GetProofCommand();
         AddProofCommand addProofCommand = new AddProofCommand();
         for (Integer proofId : currentProofIds) {
         try {
         Proof p = getProofCommand.execute(proofId);
         proofs.add(p);
         p.setTransferProofFileLog(transferProofFilesLog);
         addProofCommand.execute(p);
         } catch (PeriscopeException ex) {
         log.error("No es posible recuperar proof", ex);
         }
         }
         transferProofFilesLog.setProofs(proofs);
         */
        log.info("end");
        processed.add(transferProofFilesLog);
    }
}
