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
 * OperatorImageManager.java
 * 
 * Created on 22-04-2013, 12:58:47 PM
 */
package com.scopix.periscope.operatorimages;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.scopix.periscope.enums.EnumEvidenceType;
import com.scopix.periscope.ffmpeg.FFmpegImpl;
import com.scopix.periscope.ffmpeg.FFmpegProcessBuilder;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.thread.ProofsThread;
import com.scopix.periscope.thread.SpritesThread;
import com.scopix.periscope.xuggler.XugglerImpl;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.transport.http.HTTPConduit;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
@SuppressWarnings("rawtypes")
@SpringBean(rootClass = OperatorImageManager.class)
public class OperatorImageManager implements InitializingBean {

    private FFmpegImpl fFmpeg;
    private XugglerImpl xuggler;
    private String filesBasePath;
    private String spritesBaseDir;
    private WebClient serviceClient;
    private String proofsPathBaseName;
    private String evidencesPathBaseName;
    private String templatesPathBaseName;
    private FFmpegProcessBuilder processBuilder;
    private ExecutorService proofsExecutor = null;
    private ExecutorService spritesExecutor = null;
    public static final String SEPARATOR_NAME = "_";
    private PropertiesConfiguration systConfiguration;
    public static final String FORMAT_DATE_FILE = "yyyyMMdd_HHmm";
    private static Logger log = Logger.getLogger(OperatorImageManager.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        // cargamos el system.properties
        getSystConfiguration();

        filesBasePath = getSystConfiguration().getString("files.basepath");
        spritesBaseDir = getSystConfiguration().getString("sprites.basedir");
        proofsPathBaseName = getSystConfiguration().getString("proofs.path.basename");
        evidencesPathBaseName = getSystConfiguration().getString("evidences.path.basename");
        templatesPathBaseName = getSystConfiguration().getString("templates.path.basename");

        initServiceClient();

        // inicia pool de hilos para generación de sprites
        Integer spritesThreadsNumber = getSystConfiguration().getInteger("sprites.pool.number", 5);
        spritesExecutor = Executors.newFixedThreadPool(spritesThreadsNumber);

        // inicia pool de hilos para generación de proofs
        Integer proofsThreadsNumber = getSystConfiguration().getInteger("proofs.pool.number", 10);
        proofsExecutor = Executors.newFixedThreadPool(proofsThreadsNumber);

        log.debug("spritesThreadsNumber: [" + spritesThreadsNumber + "], proofsThreadsNumber: [" + proofsThreadsNumber + "]");
    }

    protected void initServiceClient() {
        log.info("start");
        String operatorWebURL = getSystConfiguration().getString("operator.web.url");
        log.debug("operatorWebURL: [" + operatorWebURL + "]");

        serviceClient = WebClient.create(operatorWebURL);
                //new code
        HTTPConduit conduit = WebClient.getConfig(serviceClient).getHttpConduit();
        TLSClientParameters params
                = conduit.getTlsClientParameters();

        if (params == null) {
            params = new TLSClientParameters();
            conduit.setTlsClientParameters(params);
        }

        params.setTrustManagers(new TrustManager[]{new X509TrustManager() {

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
        }
        });

        params.setDisableCNCheck(true);
        serviceClient.path("/notifyProofsGeneration").accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE);

        log.info("end");
    }

    /**
     *
     * @param elapsedTime
     * @param fileName
     * @return
     * @throws ScopixException Excepcion en caso de Error
     */
    protected File generateSnapShot(Double elapsedTime, String fileName) throws ScopixException {
        log.debug("start");

        File f = null;
        OutputStream out = null;
        try {
            if (elapsedTime != null && fileName != null && !FilenameUtils.isExtension(fileName, new String[] { "jpg", "jpeg" })) {
                BufferedImage outputImage = getfFmpeg().getSnapshot(fileName, elapsedTime); // obtiene el snapshot
                if (outputImage != null) {
                    f = File.createTempFile("SnapShot_", ".jpg");
                    f.deleteOnExit();
                    out = new FileOutputStream(f);
                    Iterator iter = ImageIO.getImageWritersByFormatName("jpeg");
                    ImageWriter writer = (ImageWriter) iter.next();
                    writer.setOutput(ImageIO.createImageOutputStream(out));
                    ImageWriteParam iwp = writer.getDefaultWriteParam();
                    iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    iwp.setCompressionQuality(new Float(0.5)); // an integer between 0 and 1
                    // 1 specifies minimum compression and maximum quality
                    IIOImage image = new IIOImage(outputImage, null, null);
                    writer.write(null, image, iwp);

                    writer.dispose();
                }
            }
        } catch (IOException e) {
            log.error(e, e);
            try {
                if (f != null) {
                    String archivo = FilenameUtils.separatorsToUnix(f.getAbsolutePath());
                    log.debug("Disponiendose a borrar archivo: " + FilenameUtils.getName(archivo));
                    FileUtils.forceDelete(f);
                    log.debug("Archivo borrado: " + FilenameUtils.getName(archivo));
                }
            } catch (IOException e2) {
                log.warn("Error elimiando file " + e2);
            }
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.warn("no es posible cerrar outputStream " + e);
                }
            }
        }
        log.debug("end [file:" + f + "]");
        return f;
    }

    protected File generateSnapShotWithMark(Double elapsedTime, String fileName, String markData) throws ScopixException {
        log.debug("start");

        File snapshotFile = null;
        OutputStream out = null;
        BufferedImage snapshotImage = null;

        try {
            if (elapsedTime != null && fileName != null && !FilenameUtils.isExtension(fileName, new String[] { "jpg", "jpeg" })) {

                snapshotImage = getfFmpeg().getSnapshot(fileName, elapsedTime); // obtiene el snapshot

                if (snapshotImage != null) {
                    // Sí generó snapshot, ahora combina la marca realizada
                    Integer width = getSystConfiguration().getInt("proof.width", 640);
                    Integer height = getSystConfiguration().getInt("proof.height", 480);
                    BufferedImage combinadas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    Graphics g = combinadas.getGraphics();

                    // se debe redimensionar el snapshot a 640 x 480
                    Image imagen1 = snapshotImage.getScaledInstance(combinadas.getWidth(), combinadas.getHeight(),
                            Image.SCALE_AREA_AVERAGING);

                    g.drawImage(imagen1, 0, 0, null);

                    // marca el snapshot
                    markInGraphic(processSnapshotMark(markData), g, combinadas, false);

                    snapshotFile = File.createTempFile("SnapShot_", ".jpg");
                    snapshotFile.deleteOnExit();
                    out = new FileOutputStream(snapshotFile);
                    Iterator iter = ImageIO.getImageWritersByFormatName("jpeg");
                    ImageWriter writer = (ImageWriter) iter.next();
                    writer.setOutput(ImageIO.createImageOutputStream(out));
                    ImageWriteParam iwp = writer.getDefaultWriteParam();
                    iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    iwp.setCompressionQuality(new Float(0.5)); // an integer between 0 and 1
                    // 1 specifies minimum compression and maximum quality
                    IIOImage image = new IIOImage(combinadas, null, null);
                    writer.write(null, image, iwp);

                    writer.dispose();
                }
            }
        } catch (IOException e) {
            log.error(e, e);
            try {
                if (snapshotFile != null) {
                    String archivo = FilenameUtils.separatorsToUnix(snapshotFile.getAbsolutePath());
                    log.debug("Disponiendose a borrar archivo: " + FilenameUtils.getName(archivo));
                    FileUtils.forceDelete(snapshotFile);
                    log.debug("Archivo borrado: " + FilenameUtils.getName(archivo));
                }
            } catch (IOException e2) {
                log.warn("Error elimiando file " + e2);
            }
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.warn("no es posible cerrar outputStream " + e);
                }
            }
        }
        log.debug("end [file:" + snapshotFile + "]");
        return snapshotFile;
    }

    /**
     *
     * @param elapsedTime para el snapshot
     * @param fileName ruta del video
     * @param response onde se escribira el ouputStream
     * @return OutputStream representativo del snapshot
     * @throws ScopixException Excepcion en caso de Error
     */
    public OutputStream getSnapshot(Double elapsedTime, String fileName, HttpServletResponse response) throws ScopixException {
        log.info("start [elapsedTime:" + elapsedTime + "][fileName:" + fileName + "]");

        File f = null;
        boolean removeTemPFile = true;
        try {
            elapsedTime = validateElapsedTime(fileName, elapsedTime);
            log.info("[newElapsedTime:" + elapsedTime + "]");
            if (elapsedTime > 0) {
                f = generateSnapShot(elapsedTime, fileName);
            }
            if (f == null) {
                removeTemPFile = false;
                f = getNoImageFile();
            }

        } catch (NumberFormatException e) {
            log.error("NumberFormatException " + e, e);
            removeTemPFile = false;
            f = getNoImageFile();
        }
        log.info("end");
        return fileToOutputStream(f, response, removeTemPFile);
    }

    /**
     *
     * @param fileName nombre del archivo
     * @param response donde se escribira el ouputStream
     * @return OutputStream representativo del snapshot
     * @throws ScopixException Excepcion en caso de Error
     */
    public OutputStream getSnapshot(String fileName, HttpServletResponse response) throws ScopixException {
        log.info("start [fileName:" + fileName + "]");
        // calculamos el TMedio
        Double tiempoMedio = new Double((getXuggler().getVideoDuration(fileName)) / 2);
        OutputStream ret = getSnapshot(tiempoMedio, fileName, response);
        log.info("end");
        return ret;
    }

    /**
     * @return the xuggler
     */
    public XugglerImpl getXuggler() {
        xuggler = new XugglerImpl();
        return xuggler;
    }

    private File getNoImageFile() {
        log.info("start");
        File tmp = null;
        try {
            String path = OperatorImageManager.class.getClassLoader().getResource("").getPath() + "/img/noEvidence_es.png";
            String decodedPath = URLDecoder.decode(path, "UTF-8");
            tmp = new File(decodedPath);
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException " + e, e);
        }
        log.info("end");
        return tmp;
    }

    /**
     *
     * @param fileName ruta completa del template
     * @param response donde se escribira el ouputStream
     * @return OutputStream represntativo del template
     */
    public OutputStream getTemplate(String fileName, HttpServletResponse response) {
        log.info("start [fileName:" + fileName + "]");
        OutputStream out = getMediaFile(fileName, response);
        log.info("end");
        return out;
    }

    /**
     *
     * @param fileName ruta completa del template
     * @param response donde se escribira el ouputStream
     * @return OutputStream represntativo del template
     */
    public OutputStream getMediaFile(String fileName, HttpServletResponse response) {
        log.info("start [fileName:" + fileName + "]");
        File ret = new File(fileName);
        OutputStream out = fileToOutputStream(ret, response, false);
        log.info("end");
        return out;
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

    /**
     * Genera un archivo marcado y lo almacena en una ruta especifica
     *
     * @param elapsedTime tiempo en el cual se necesita el proof para un video
     * @param fileName ruta del video o imagen a generar proof
     * @param pathBase base donde se debe almacenar el proof
     * @param marcas detalle de las marcas tanto circulos como rectangulos
     * @param snapShots Mapa para no generar mas de una vez cada snapshot
     * @return nombre final del nuevo file
     * @throws ScopixException Excepcion en caso de Error
     */
    public String generateProof(Double elapsedTime, String fileName, String pathBase, Marks marcas,
            Map<String, BufferedImage> snapShots) throws ScopixException {
        log.info("start");
        String name = null;
        if (fileName != null) {
            if (!FilenameUtils.isExtension(fileName, new String[] { "jpg", "jpeg" })) {
                name = generateProofFromMovie(fileName, elapsedTime, marcas, pathBase, snapShots);
            } else {
                name = generateProofFromImage(fileName, marcas, pathBase, snapShots);
            }
        }
        log.info("end [name:" + name + "]");
        return name;
    }

    /**
     *
     * @param listMarcas lista de Marks para generar un conjunto de proofs
     * @return List<ResultMarks> lista con evidenceId, metricId y nombre los archivos generados
     * @throws ScopixException Excepcion en caso de Error
     */
    public List<ResultMarks> generateProof(List<Marks> listMarcas) throws ScopixException {
        log.info("start");
        List<ResultMarks> ret = new ArrayList<ResultMarks>();
        String path = "";
        try {
            if (listMarcas != null) {
                Map<String, BufferedImage> snapshots = new HashMap<String, BufferedImage>();
                for (Marks marks : listMarcas) {
                    if (path.length() == 0) {
                        path = marks.getPathDestino();
                    }
                    ResultMarks result = generateProof(marks, snapshots);
                    log.debug("result fileName: [" + result.getFileName() + "]");
                    ret.add(result);
                }
            }
        } catch (ScopixException e) {
            log.error("ScopixException: " + e, e);
            // borramos todos los archivos generados
            for (ResultMarks r : ret) {
                try {
                    String pathSinMarcas = path + "/proofs/" + r.getFileName();
                    String pathWithMarks = path + "/proofs_with_marks/" + r.getFileName();

                    log.debug("Disponiendose a borrar: " + pathWithMarks);
                    FileUtils.forceDelete(new File(pathWithMarks));
                    log.debug("Archivo borrado: " + pathWithMarks);

                    log.debug("Disponiendose a borrar: " + pathSinMarcas);
                    FileUtils.forceDelete(new File(pathSinMarcas));
                    log.debug("Archivo borrado: " + pathSinMarcas);
                } catch (IOException e2) {
                    log.warn("NO es posible eliminar file " + e2);
                }
            }
            throw (e);
        }
        log.info("end resultMarks size:" + ret.size());
        return ret;
    }

    /**
     * 
     * @param containerDto
     */
    public void generateAsynchronousProofs(MarksContainerDTO containerDto) {
        log.info("start");
        ProofsThread proofsThread = new ProofsThread();
        proofsThread.setContainerDto(containerDto);

        // inicia ejecución de proofs
        getProofsExecutor().execute(proofsThread);
        log.info("end");
    }

    /**
     * @param marcas detalle de las marcas tanto circulos como rectangulos
     * @param snapShots Mapa para no generar mas de una vez cada snapshot
     * @return ResultMarks contiene el evidenceId, metricId y nombre final para el file generado
     * @throws ScopixException Excepcion en caso de Error
     */
    public ResultMarks generateProof(Marks marcas, Map<String, BufferedImage> snapShots) throws ScopixException {
        log.info("start");
        ResultMarks ret = new ResultMarks();
        Double tiempoMedio = null;
        String name = null;
        if (marcas != null) {
            ret.setEvidenceId(marcas.getEvidenceId());
            ret.setMetricId(marcas.getMetricId());
            if (marcas.getElapsedTime() != null) {
                tiempoMedio = marcas.getElapsedTime();
            } else if (!FilenameUtils.isExtension(marcas.getPathOrigen(), new String[] { "jpg", "jpeg" })) {
                tiempoMedio = new Double((getXuggler().getVideoDuration(marcas.getPathOrigen())) / 2);
            }
            tiempoMedio = validateElapsedTime(marcas.getPathOrigen(), tiempoMedio);
            name = generateProof(tiempoMedio, marcas.getPathOrigen(), marcas.getPathDestino(), marcas, snapShots);
            if (name == null) {
                throw new ScopixException("No se puede generar proof:" + marcas.getPathOrigen());
            }
        } else {
            log.warn("no se reciben marcas");
        }

        ret.setFileName(name);
        log.info("end");
        return ret;
    }

    /**
     *
     * @param absolutePath ruta base
     * @param fileName nombre base para el archivo
     * @param extension extension para el nuevo archivo
     * @param iCurrent vuelta en la que se encuentra
     * @return String nombre unico para un archivo en un directorio dato
     */
    public static synchronized String generateUniqueFile(String absolutePath, String fileName, String extension, Integer iCurrent) {
        Integer current = iCurrent == null ? 1 : iCurrent + 1;
        File filename = new File(absolutePath, fileName + SEPARATOR_NAME + current + "." + extension);

        if (filename.exists()) {
            return generateUniqueFile(absolutePath, fileName, extension, current);
        } else {
            return filename.getName();
        }
    }

    /**
     *
     * @param fileName ruta del video al cual se le generaran los proof
     * @param elapsedTime tiempo en le cual se desea generar los proofs
     * @param marcas marcas en el proof
     * @param pathBase ruta donde se almacenara el nuevo proof
     * @param snapShots Mapa para no generar mas de una vez cada snapshot
     * @return
     * @throws ScopixException
     */
    protected String generateProofFromMovie(String fileName, Double elapsedTime, Marks marcas, String pathBase,
            Map<String, BufferedImage> snapShots) throws ScopixException {
        log.info("start [fileName:" + fileName + "][destino:" + pathBase + "][elapsedTime:" + elapsedTime + "]");
        // Carga imágen del snapshot del momento de la pausa
        String name = null;
        BufferedImage imagen1 = snapShots.get(FilenameUtils.getName(fileName + elapsedTime));
        if (imagen1 == null) {
            imagen1 = getfFmpeg().getSnapshot(fileName, elapsedTime);
            snapShots.put(FilenameUtils.getName(fileName + elapsedTime), imagen1);
        }

        if (imagen1 != null) {
            // create the new image, canvas size is the max. of both image sizes
            name = generateMarksInImage(imagen1, marcas, pathBase);
        } else {
            log.error("No ser puede generar marcas para " + fileName);
        }
        log.info("end [name:" + name + "]");
        return name;
    }

    /**
     *
     * @param fileName ruta de la imagen base para los proof
     * @param marcas marcas para el nuevo proof
     * @param pathBase ruta donde se almacenara el nuevo proof
     * @param snapShots Mapa para no generar mas de una vez cada snapshot
     * @return
     */
    protected String generateProofFromImage(String fileName, Marks marcas, String pathBase, Map<String, BufferedImage> snapShots)
            throws ScopixException {
        log.info("start [fileName:" + fileName + "][destino:" + pathBase + "]");
        String name = null;
        try {
            BufferedImage imagen1 = snapShots.get(FilenameUtils.getName(fileName));
            if (imagen1 == null) {
                imagen1 = ImageIO.read(new File(fileName));
                snapShots.put(FilenameUtils.getName(fileName), imagen1);
            }
            // create the new image, canvas size is the max. of both image sizes
            name = generateMarksInImage(imagen1, marcas, pathBase);

        } catch (IOException e) {
            log.error("IOException " + e, e);
        }
        log.info("end");
        return name;
    }

    /**
     *
     * @param snapshot
     * @param marcas
     * @param pathBase
     * @return
     */
    protected String generateMarksInImage(BufferedImage snapshot, Marks marcas, String pathBase) throws ScopixException {
        log.info("start");
        String name = null;
        if (marcas != null) {
            try {
                Integer width = getSystConfiguration().getInt("proof.width", 640);
                Integer height = getSystConfiguration().getInt("proof.height", 480);
                BufferedImage combinadas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = combinadas.getGraphics();

                // se debe redimensionar el snapshot a 640 x 480
                Image imagen1 = snapshot.getScaledInstance(combinadas.getWidth(), combinadas.getHeight(),
                        Image.SCALE_AREA_AVERAGING);

                g.drawImage(imagen1, 0, 0, null);
                // marcamos la imágen
                markInGraphic(marcas, g, combinadas, marcas.getWithNumber());

                log.debug("Path base: [" + pathBase + "]");

                String file = DateFormatUtils.format(marcas.getEvidenceDate(), FORMAT_DATE_FILE) + SEPARATOR_NAME
                        + marcas.getMetricId() + SEPARATOR_NAME + marcas.getEvidenceId();
                String newProof = generateUniqueFile(pathBase + "proofs/", file, "jpg", null);
                name = newProof;
                log.debug("Nombre de proof generado: [" + name + "]");

                String pathWithMarks = pathBase + "proofs_with_marks/";

                // Verifica directorio
                if (createProofDirectory(pathWithMarks)) {
                    // Copia archivo a /proofsWithMarks
                    String proofWithMarksPath = pathWithMarks + newProof;
                    log.debug("Disponiendose a copiar archivo a proofsWithMarks: [" + proofWithMarksPath + "]");
                    ImageIO.write(combinadas, "jpg", new File(proofWithMarksPath));
                    log.debug("Archivo copiado en proofsWithMarks: [" + proofWithMarksPath + "]");

                    // Verifica existencia del archivo creado en /proofs_with_marks
                    if (verifyFileExistence(proofWithMarksPath)) {
                        String pathWithoutMarks = pathBase + "proofs/";

                        // Verifica directorio
                        if (createProofDirectory(pathWithoutMarks)) {
                            // Copia archivo a /proofs
                            String proofPath = pathWithoutMarks + newProof;
                            log.debug("Disponiendose a copiar archivo a proofs: [" + proofPath + "]");
                            ImageIO.write(snapshot, "jpg", new File(proofPath));
                            log.debug("Archivo copiado en proofs: [" + proofPath + "]");

                            // Verifica existencia del archivo creado en /proofs
                            if (verifyFileExistence(proofPath)) {
                                // writeToTextFile(pathProof);
                                log.debug("Proofs generados exitosamente");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Exception " + e, e);
                throw new ScopixException(e);
            }
        } else {
            log.warn("no se reciben las marcas");
        }
        log.info("end [name:" + name + "]");
        return name;
    }

    /**
     * Crea directorio en donde se almacenará el proof correspondiente
     *
     * @author carlos polo
     * @param proofDirectory path del directorio a crear
     * @return boolean true si el directorio fue creado correctamente y tiene permisos de escritura
     * @throws ScopixException en caso de que el directorio no se pueda crear o no tenga permisos de escritura
     * @date 15-jul-2013
     */
    private boolean createProofDirectory(String proofDirectory) throws ScopixException {
        log.debug("start, proofDirectory: [" + proofDirectory + "]");
        boolean result = false;

        try {
            log.debug("Se dispone a crear file para proofDirectory: [" + proofDirectory + "]");
            // Crea directorio en donde se copiarán los archivos
            File directory = new File(proofDirectory);

            // true if and only if the file denoted by this abstract pathname exists and is a directory
            if (!directory.isDirectory()) {
                log.debug("Directorio NO existe, se dispone a forzar creación");
                // Forza la creación del directorio
                FileUtils.forceMkdir(directory);
                log.debug("Después de forceMkdir");
            }

            // Valida si en el directorio se puede escribir
            boolean exists = directory.exists();
            boolean canWrite = directory.canWrite();
            boolean isDirectory = directory.isDirectory();

            log.debug("exists: [" + exists + "] , isDirectory: [" + isDirectory + "] , " + "canWrite: [" + canWrite
                    + "] , proofDirectory: [" + proofDirectory + "]");

            // El directorio no tiene permisos de escritura
            if (!canWrite) {
                log.debug("El directorio: [" + proofDirectory + "] NO tiene permisos de escritura, se define "
                        + "directory.setWritable(true)");
                directory.setWritable(true);
                canWrite = directory.canWrite();
            }

            // Valida que el directorio esté creado, sea válido y tenga permisos de escritura
            if (exists && isDirectory && canWrite) {
                log.debug("El directorio: [" + proofDirectory + "] SI existe, es un directorio "
                        + "válido y tiene permisos de escritura");
                result = true;
            } else {
                log.error("No es posible crear/escribir en el directorio: [" + proofDirectory + "]");
                throw new ScopixException("No es posible crear/escribir en el directorio: [" + proofDirectory + "]");
            }
        } catch (IOException e) {
            log.error("IOException " + e, e);
            throw new ScopixException(e);
        } catch (SecurityException e) {
            log.error("SecurityException " + e, e);
            throw new ScopixException(e);
        } catch (Exception e) {
            log.error("Exception " + e, e);
            throw new ScopixException(e);
        }
        return result;
    }

    /**
     * Verifica que el archivo exista en el correspondiente directorio, sea un archivo válido y que no contenga errores
     *
     * @author carlos polo
     * @param filePathName ubicación y nombre del archivo por verificar
     * @return boolean true si el archivo existe y está correctamente creado
     * @throws ScopixException en caso de que el archivo no exista o esté creado con errores
     * @date 04-jul-2013
     */
    public boolean verifyFileExistence(String filePathName) throws ScopixException {
        log.info("start, verificando existencia del archivo: [" + filePathName + "]");
        boolean result = false;
        File file = new File(filePathName);

        // Verifica que el archivo exista, se pueda leer y sea un archivo válido
        if (file.exists() && file.canRead() && file.isFile()) {
            log.debug("El archivo [" + filePathName + "] SI existe y es un archivo correctamente creado");
            result = true;
        } else {
            log.warn("El archivo [" + filePathName + "] NO existe o tiene errores");
            throw new ScopixException("El archivo [" + filePathName + "] no existe o se generó con errores");
        }

        log.info("end, [result verify: " + result + "]");
        return result;
    }

    private Double validateElapsedTime(String fileName, Double elapsedTime) {
        if (!FilenameUtils.isExtension(fileName, new String[] { "jpg", "jpeg" })
                && getXuggler().getVideoDuration(fileName) < elapsedTime) {
            // verificamos el largo del video y si el tMedio solicitado es permitido
            elapsedTime = new Double((getXuggler().getVideoDuration(fileName)) / 2);

        }
        return elapsedTime;
    }

    /**
     * @return the systConfiguration
     */
    public PropertiesConfiguration getSystConfiguration() {
        if (systConfiguration == null) {
            try {
                systConfiguration = new PropertiesConfiguration("system.properties");
                systConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
                log.debug("[systConfiguration.basePath:" + systConfiguration.getBasePath() + "]");
            } catch (ConfigurationException e) {
                log.error("No es posible abrir webservices.properties " + e, e);
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
     * @return the fFmpeg
     */
    public FFmpegImpl getfFmpeg() {
        if (fFmpeg == null) {
            fFmpeg = new FFmpegImpl(getSystConfiguration().getString("path.ffmpeg"));
        }
        return fFmpeg;
    }

    /**
     * @param fFmpeg the fFmpeg to set
     */
    public void setfFmpeg(FFmpegImpl fFmpeg) {
        this.fFmpeg = fFmpeg;
    }

    /**
     *
     * @param marcas
     * @param g
     * @param combinadas
     * @param withNumber
     */
    protected void markInGraphic(Marks marcas, Graphics g, BufferedImage combinadas, boolean withNumber) {
        for (Shapes circle : marcas.getCircles()) {
            g.setColor(ImageUtilis.getColorFromRGB(circle.getColor()));
            g.fillOval(circle.getPositionX(), circle.getPositionY(), circle.getWidth(), circle.getHeight());
            g.drawOval(circle.getPositionX(), circle.getPositionY(), circle.getWidth(), circle.getHeight());
        }
        for (Shapes square : marcas.getSquares()) {
            g.setColor(ImageUtilis.getColorFromRGB(square.getColor()));
            g.drawRect(square.getPositionX(), square.getPositionY(), square.getWidth(), square.getHeight());
            g.drawRect(square.getPositionX() + 1, square.getPositionY() + 1, square.getWidth() - 2, square.getHeight() - 2);
        }

        if (withNumber) {
            // pintamos el Numero
            g.setColor(Color.YELLOW);
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
            int posx = marcas.getResult().toString().length() * 25;
            g.drawString(marcas.getResult().toString(), combinadas.getWidth() - posx, 30);
        }
    }

    /**
     * Borra todos los proofs soliciados en el contianer obtenendo la ruta desde este
     *
     * @param container definicion de files a borrar
     */
    public void deleteProofs(DeleteContainer container) {
        log.info("start");
        if (container != null) {
            String pathProofs = container.getPathOrigen() + "/proofs/";
            String pathProofsWithMarks = container.getPathOrigen() + "/proofs_with_marks/";
            for (String file : container.getList()) {
                try {
                    if (file != null && !file.trim().equals("")) {
                        File proofs = new File(pathProofs + file);
                        File proofsWithMarks = new File(pathProofsWithMarks + file);

                        log.debug("proofPath: [" + pathProofs + file + "], " + "proofWithMarksPath: [" + pathProofsWithMarks
                                + file + "]");

                        if (proofs.isFile() && proofs.canRead()) {
                            log.debug("Disponiendose a borrar archivo: " + pathProofs + file);
                            FileUtils.forceDelete(proofs);
                            log.debug("Archivo borrado: " + pathProofs + file);
                        }
                        if (proofsWithMarks.isFile() && proofsWithMarks.canRead()) {
                            log.debug("Disponiendose a borrar archivo: " + pathProofsWithMarks + file);
                            FileUtils.forceDelete(proofsWithMarks);
                            log.debug("Archivo borrado: " + pathProofsWithMarks + file);
                        }
                    }
                } catch (IOException e) {
                    log.warn("no es posible eliminar " + file + "[e:" + e + "]");
                }
            }
        }
        log.info("end");
    }

    /**
     * Retorna imágen con marca en una posición determinada de un video
     *
     * @param elapsedTime posición del snapshot
     * @param fileName video de dónde obtener el snapshot
     * @param markData coordenadas y tamaño de la marca
     * @param response httpServletResponse
     * @return OutputStream representando a la imagen
     * @throws ScopixException excepción en caso de error
     */
    public OutputStream getSnapshotWithMark(Double elapsedTime, String fileName, String markData, HttpServletResponse response)
            throws ScopixException {

        log.info("start, fileName: [" + fileName + "], elapsedTime: [" + elapsedTime + "], markData: [" + markData + "]");
        File snapshotFile = null;
        boolean removeTemPFile = true;
        try {
            elapsedTime = validateElapsedTime(fileName, elapsedTime);
            log.info("[newElapsedTime:" + elapsedTime + "]");
            if (elapsedTime > 0) {
                snapshotFile = generateSnapShotWithMark(elapsedTime, fileName, markData);
            }
            if (snapshotFile == null) {
                removeTemPFile = false;
                snapshotFile = getNoImageFile();
            }
        } catch (NumberFormatException e) {
            log.error("NumberFormatException " + e, e);
            removeTemPFile = false;
            snapshotFile = getNoImageFile();
        }
        log.info("end");
        return fileToOutputStream(snapshotFile, response, removeTemPFile);
    }

    protected Marks processSnapshotMark(String markData) {
        Marks mark = new Marks();
        String[] square = markData.split("#");

        List<Shapes> squares = generateMark(square);
        mark.setSquares(squares);

        return mark;
    }

    /**
     * Genera la marca de la evaluación (waiting time)
     *
     * @author carlos polo
     * @version 1.0.0
     * @param shapeArr datos de coordenadas y tamaños de la figura
     * @since 6.0
     * @date 26/04/2013
     * @return List<Shapes> lista de figuras
     */
    protected List<Shapes> generateMark(String[] shapeArr) {
        log.info("start");
        List<Shapes> lstShapes = new ArrayList<Shapes>();

        for (int i = 0; i < shapeArr.length; i++) {
            String shapeStyle = shapeArr[i];
            String[] shapeData = shapeStyle.split("_");

            String coordenadas = shapeData[0];
            String size = shapeData[1];

            String[] coordsArray = coordenadas.split(":");
            String[] sizeArray = size.split(":");

            String x = coordsArray[0];
            String y = coordsArray[1];

            String width = sizeArray[0];
            String height = sizeArray[1];

            // El substring es para quitar el "px"
            int xPos = Integer.valueOf(x.substring(0, x.length() - 2)) - 386;
            int yPos = Integer.valueOf(y.substring(0, y.length() - 2)) - 78;
            // El substring es para quitar el "px"
            int w = Integer.valueOf(width.substring(0, width.length() - 2));
            int h = Integer.valueOf(height.substring(0, height.length() - 2));

            Shapes shape = new Shapes();
            shape.setPositionX(xPos);
            shape.setPositionY(yPos);
            shape.setWidth(w);
            shape.setHeight(h);

            shape.setColor("FFAADD00"); // verde
            lstShapes.add(shape);
        }
        log.info("end");
        return lstShapes;
    }

    /**
     * Retorna archivo plano con información de sprites del video requerido
     *
     * @param fileName video de dónde obtener el snapshot
     * @param response donde se escribira el ouputStream
     * @return OutputStream representando al archivo plano
     * @throws ScopixException excepción en caso de error
     */
    public OutputStream getVttVideoFile(String fileName, HttpServletResponse response) throws ScopixException {
        log.info("start");
        OutputStream outputStream = fileToOutputStream(new File(fileName), response, false);
        log.info("end");
        return outputStream;
    }

    /**
     * Genera sprite images a partir del correspondiente video
     *
     * @param fileName ruta y nombre del archivo de video
     */
    public void generateVideoSprites(String fileName) {
        log.info("start, fileName: [" + fileName + "]");
        String spritesScaleX = getSystConfiguration().getString("sprites.scale.x");
        String spritesScaleY = getSystConfiguration().getString("sprites.scale.y");
        String[] ffmpegSpritesCmd = getSystConfiguration().getStringArray("ffmpeg.sprites.cmd");
        String operatorImgUrl = getSystConfiguration().getString("operator.images.services.url");

        // inicia el correspondiente hilo de sprites
        SpritesThread spritesThread = new SpritesThread();
        spritesThread.setFileName(fileName);
        spritesThread.setfFmpeg(getfFmpeg());
        spritesThread.setSpritesScaleX(spritesScaleX);
        spritesThread.setSpritesScaleY(spritesScaleY);
        spritesThread.setMetodo("generateVideoSprites");
        spritesThread.setSpritesBaseDir(getSpritesBaseDir());
        spritesThread.setOperatorImgUrl(operatorImgUrl);
        spritesThread.setFfmpegSpritesCmd(ffmpegSpritesCmd);

        // ejecuta el hilo
        getSpritesExecutor().execute(spritesThread);
        log.info("end");
    }

    /**
     * 
     * @param corporateName client name
     * @param store store name
     * @param date date in yyyyMMdd format
     * @param fileName fileName with extension
     * @param fileType file type: IMAGE, VIDEO, PROOF or TEMPLATE
     * @param vttFileName
     * @throws scopixException
     * @return
     */
    public String processFilePath(String corporateName, String store, String date, String fileName, String fileType,
            String vttFileName) throws ScopixException {

        log.info("start, corporateName: [" + corporateName + "], store: [" + store + "], date: [" + date + "], " + "fileName: ["
                + fileName + "], fileType: [" + fileType + "], vttFileName: [" + vttFileName + "]");

        String year = null;
        String month = null;
        String day = null;
        String completeFilePath = null;

        try {
            if (date != null) {
                Date d1 = DateUtils.parseDate(date, new String[] { "yyyyMMdd" });
                String[] dateParser = StringUtils.split(DateFormatUtils.format(d1, "yyyy-MM-dd"), "-");
                year = dateParser[0];
                month = dateParser[1];
                day = dateParser[2];

                log.debug("fileDate: [" + year + "-" + month + "-" + day + "]");
            }

            String fileTypeBasePath = processFileTypeBasePath(fileType);

            if (EnumEvidenceType.VTT.toString().equalsIgnoreCase(fileType)) {
                String fileBaseName = FilenameUtils.getBaseName(fileName); // without extension
                // /sprites/Lowes/404/20140510/20140510_3723728/20140510_3723728.vtt
                completeFilePath = FilenameUtils.separatorsToUnix(getSpritesBaseDir() + corporateName + "/" + store + "/" + date
                        + "/" + fileBaseName + "/" + fileName);

            } else if (EnumEvidenceType.SPRITE.toString().equalsIgnoreCase(fileType)) {
                // /sprites/Lowes/404/20140510/20140510_3723728/20140510_3723728.vtt
                completeFilePath = FilenameUtils.separatorsToUnix(getSpritesBaseDir() + corporateName + "/" + store + "/" + date
                        + "/" + vttFileName + "/" + fileName);

            } else if (EnumEvidenceType.TEMPLATE.toString().equalsIgnoreCase(fileType)) {
                // /data/ftp/evidence/Lowes/404/templates/
                completeFilePath = FilenameUtils.separatorsToUnix(getFilesBasePath() + fileTypeBasePath + corporateName + "/"
                        + store + getTemplatesPathBaseName() + fileName);

            } else {
                // /data/ftp/evidence/Lowes/404/2014/07/25/xxxx.jpg
                completeFilePath = FilenameUtils.separatorsToUnix(getFilesBasePath() + fileTypeBasePath + corporateName + "/"
                        + store + "/" + year + "/" + month + "/" + day + "/" + fileName);
            }
        } catch (ParseException e) {
            throw new ScopixException("Error parsing fileDate: [" + e.getMessage() + "]", e);
        }

        log.info("end, filePath: [" + completeFilePath + "]");
        return completeFilePath;
    }

    /**
     * 
     * @param fileType
     * @return
     */
    private String processFileTypeBasePath(String fileType) {
        log.info("start, fileType: [" + fileType + "]");
        String basePath = null;

        if (EnumEvidenceType.IMAGE.toString().equalsIgnoreCase(fileType)
                || EnumEvidenceType.VIDEO.toString().equalsIgnoreCase(fileType)
                || EnumEvidenceType.TEMPLATE.toString().equalsIgnoreCase(fileType)) {

            // /evidence/
            basePath = getEvidencesPathBaseName();
        } else {
            // /proofs/
            basePath = getProofsPathBaseName();
        }
        log.info("end, basePath: [" + basePath + "]");
        return basePath;
    }

    /**
     * 
     * @param videoNames
     * @return
     * @throws ScopixException
     */
    public String getSpritesURLs(String videoNames) throws ScopixException {
        log.info("start, videoNames: [" + videoNames + "]");

        String spritesURLs = null;
        StringBuilder sBuilder = new StringBuilder();
        String[] saVideoNames = videoNames.split(";");

        for (String videoName : saVideoNames) {
            // videoName: data/ftp/evidence/cliente/store/año/mes/dia/nombreVideo.mp4
            // debe buscar correspondiente vtt:
            // /data/ftp/sprites/cliente/store/añomesdia/nombreVideo/nombreVideo.vtt

            String[] saVideoName = videoName.split("/");
            String nombreVideo = FilenameUtils.getBaseName(videoName); // sin extensión

            String vttPath = FilenameUtils.separatorsToUnix(getSpritesBaseDir() + saVideoName[4] + "/" + saVideoName[5] + "/"
                    + saVideoName[6] + saVideoName[7] + saVideoName[8] + "/" + nombreVideo + "/" + nombreVideo + ".vtt");

            String vttSpritesURLs = processSpritesURLs(vttPath);
            if (vttSpritesURLs != null && !"".equals(vttSpritesURLs.trim())) {
                sBuilder.append(vttSpritesURLs);
            }
        }

        spritesURLs = sBuilder.toString();
        spritesURLs = spritesURLs.substring(0, spritesURLs.length() - 1); // elimina último ;

        log.info("end, spritesURLs: [" + spritesURLs + "]");
        return spritesURLs;
    }

    /**
     * 
     * @param vttPath
     * @return
     * @throws ScopixException
     */
    private String processSpritesURLs(String vttPath) throws ScopixException {
        log.info("start, vttPath: [" + vttPath + "]");
        LineIterator it = null;
        String spritesURLs = null;
        StringBuilder sBuilder = new StringBuilder();
        HashMap<String, String> hmSpritesNames = new HashMap<String, String>();

        try {
            it = FileUtils.lineIterator(new File(vttPath), "UTF-8");

            if (it != null) {
                while (it.hasNext()) {
                    String line = it.nextLine();

                    if (line.startsWith("http")) {
                        int jpgIndex = line.indexOf(".jpg");
                        int spriteIndex = line.indexOf("sprite");
                        String spriteName = line.substring(spriteIndex, jpgIndex);

                        String value = hmSpritesNames.get(spriteName);

                        if (value == null) {
                            sBuilder.append(line).append(";");
                            hmSpritesNames.put(spriteName, "PROCESSED");
                        }
                    }
                }
                spritesURLs = sBuilder.toString();
            }
        } catch (IOException e) {
            throw new ScopixException("error procesando urls de sprites, vttPath: [" + vttPath + "]", e);
        } finally {
            if (it != null) {
                LineIterator.closeQuietly(it);
            }
        }
        log.info("end, spritesURLs: [" + spritesURLs + "], vttPath: [" + vttPath + "]");
        return spritesURLs;
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

    public ExecutorService getSpritesExecutor() {
        return spritesExecutor;
    }

    /**
     * @return the filesBasePath
     */
    public String getFilesBasePath() {
        return filesBasePath;
    }

    /**
     * @param filesBasePath the filesBasePath to set
     */
    public void setFilesBasePath(String filesBasePath) {
        this.filesBasePath = filesBasePath;
    }

    /**
     * @return the evidencesPathBaseName
     */
    public String getEvidencesPathBaseName() {
        return evidencesPathBaseName;
    }

    /**
     * @param evidencesPathBaseName the evidencesPathBaseName to set
     */
    public void setEvidencesPathBaseName(String evidencesPathBaseName) {
        this.evidencesPathBaseName = evidencesPathBaseName;
    }

    /**
     * @return the proofsPathBaseName
     */
    public String getProofsPathBaseName() {
        return proofsPathBaseName;
    }

    /**
     * @param proofsPathBaseName the proofsPathBaseName to set
     */
    public void setProofsPathBaseName(String proofsPathBaseName) {
        this.proofsPathBaseName = proofsPathBaseName;
    }

    /**
     * @return the templatesPathBaseName
     */
    public String getTemplatesPathBaseName() {
        return templatesPathBaseName;
    }

    /**
     * @param templatesPathBaseName the templatesPathBaseName to set
     */
    public void setTemplatesPathBaseName(String templatesPathBaseName) {
        this.templatesPathBaseName = templatesPathBaseName;
    }

    /**
     * @return the spritesBaseDir
     */
    public String getSpritesBaseDir() {
        return spritesBaseDir;
    }

    /**
     * @param spritesBaseDir the spritesBaseDir to set
     */
    public void setSpritesBaseDir(String spritesBaseDir) {
        this.spritesBaseDir = spritesBaseDir;
    }

    public ExecutorService getProofsExecutor() {
        return proofsExecutor;
    }

    public WebClient getServiceClient() {
        return serviceClient;
    }
}