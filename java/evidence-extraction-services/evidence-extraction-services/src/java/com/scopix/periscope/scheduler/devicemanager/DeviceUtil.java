/*
 *
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * DeviceUtil.java
 *
 * Created on 05-01-2010, 02:55:42 PM
 *
 */
package com.scopix.periscope.scheduler.devicemanager;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;

/**
 * Esta clase tiene como responsabilidad administrar el montaje y desmontaje de dispositivos en linux
 *
 * Obtendr� los comandos a ejecutar del archivo system.properties
 *
 * @author Gustavo Alvarez
 */
@SpringBean //(initMethod = "init")
public class DeviceUtil implements InitializingBean {

    Logger log = Logger.getLogger(DeviceUtil.class);
    private String mountCommand;
    private String unmountCommand;
    private String checkMountCommand;
    private String externalDevice;
    private String rootFolder;
    private String mainFile;
    private String createPartitionCommand;
    private String checkPartitionCommand;
    private String formatPartitionCommand;
    private String encrypted;
    private String labelEncryptedPartition;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            ClassPathResource res = new ClassPathResource("system.properties");
            Properties prop = new Properties();
            prop.load(res.getInputStream());
            mountCommand = prop.getProperty("DeviceUtil.mount");
            unmountCommand = prop.getProperty("DeviceUtil.unmount");
            checkMountCommand = prop.getProperty("DeviceUtil.checkMountCommand");
            externalDevice = prop.getProperty("DeviceUtil.externalDevice");
            rootFolder = prop.getProperty("DeviceUtil.rootFolder");
            mainFile = prop.getProperty("DeviceUtil.mainControlFile");
            createPartitionCommand = prop.getProperty("DeviceUtil.createPartitionCommand");
            checkPartitionCommand = prop.getProperty("DeviceUtil.checkPartitionCommand");
            formatPartitionCommand = prop.getProperty("DeviceUtil.formatPartitionCommand");
            encrypted = prop.getProperty("DeviceUtil.encrypted");
            labelEncryptedPartition = prop.getProperty("DeviceUtil.labelEncryptedPartition");

        } catch (IOException ioex) {
            log.error("Error Inicializando DeviceUtil. ", ioex);
            throw new ScopixException("Error Inicializando DeviceUtil. ", ioex);
        }

    }

    /**
     * Verifica si el dispositivo esta montado
     *
     * @param device
     * @return boolean Indica el estado del dispositivo. TRUE si esta montado y FALSO en caso contrario
     */
    public boolean checkMountDevice() throws Exception {
        log.debug("start");

        boolean resp = false;
        String s = "";
        StringBuilder error = new StringBuilder();
        StringBuilder answer = new StringBuilder();
        String partition = null;

        try {
            log.debug("encrypted: " + encrypted);
            if (encrypted.equalsIgnoreCase("true")) {
                partition = labelEncryptedPartition;
            } else {
                partition = externalDevice;
            }
            log.debug("partition: " + partition);
            log.debug("checkMountCommand: " + checkMountCommand);

            Process p = Runtime.getRuntime().exec(checkMountCommand);

            p.waitFor();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            //si ocurre un error se llena el "answerError"
            while ((s = stdError.readLine()) != null) {
                error.append(s).append("\n");
            }

            if (error.length() > 0) {
                log.debug("Error: " + error.toString());
            }

            //verificando el dispositivo
            while ((s = stdInput.readLine()) != null) {
                if (s.contains(partition)) {
                    answer.append(s).append("\n");
                    resp = true;
                }
            }

            if (answer.length() > 0) {
                log.debug("answer: " + answer.toString());
            }
        } catch (IOException e) {
            log.debug("Error: " + e.getMessage());
            throw e;
        } catch (InterruptedException e) {
            log.debug("Error: " + e.getMessage());
            throw e;
        }

        log.debug("end. Status: " + resp);
        return resp;
    }

    /**
     * Metodo para montar un dispositivo
     *
     * @return boolean que indica TRUE en caso de montar exitosamente el disco, FALSE en caso contrario
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean mountDevice() throws Exception {
        log.debug("start");

        boolean resp = false;
        String s = "";
        StringBuilder error = new StringBuilder();
        StringBuilder answer = new StringBuilder();

        try {
            //ejecutando el comando
            log.debug("mountCommand: " + mountCommand);
            Process p = Runtime.getRuntime().exec(mountCommand);

            //esperando que finalice el comando
            int proc = p.waitFor();
            resp = proc == 0;
            log.debug("p.waitFor: " + proc);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            //si ocurre un error se llena el "answerError"
            while ((s = stdError.readLine()) != null) {
                error.append(s).append("\n");
            }

            if (error.length() > 0) {
                log.debug("Error: " + error.toString());
            }

            //si todo sale bien se llena el "answerMount"
            while ((s = stdInput.readLine()) != null) {
                answer.append(s).append("\n");
            }

            if (answer.length() > 0) {
                log.debug("answer: " + answer.toString());
            }

        } catch (Exception ex) {
            throw ex;
        }

        log.debug("end. Status: " + resp);
        return resp;
    }

    /**
     * Metodo para desmontar un dispositivo. TRUE indica que se desmonto exitosamente, FALSE en caso contrario.
     *
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean unMountDevice() throws Exception {
        log.debug("start");

        boolean resp = false;
        String s = "";
        StringBuilder error = new StringBuilder();
        StringBuilder answer = new StringBuilder();

        try {
            //desmontando unidad
            log.debug("unmountCommand" + unmountCommand);
            Process p = Runtime.getRuntime().exec(unmountCommand);

            //esperando que finalice el comando
            int proc = p.waitFor();
            resp = proc == 0;
            log.debug("p.waitFor: " + proc);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            //si ocurre un error se llena el "answerError"
            while ((s = stdError.readLine()) != null) {
                error.append(s).append("\n");
            }

            if (error.length() > 0) {
                log.debug("Error: " + error.toString());
            }

            //si todo sale bien se llena el "answerMount"
            while ((s = stdInput.readLine()) != null) {
                answer.append(s).append("\n");
            }

            if (answer.length() > 0) {
                log.debug("answer: " + answer.toString());
            }

        } catch (Exception ex) {
            throw ex;
        }

        log.debug("end. Status: " + resp);
        return resp;
    }

    /**
     * Este metodo es especifico para este proceso y se encarga de generar/actualizar el archivo de control "main.ctrl" que se
     * ubica en la raiz del dispositivo. Si el "patron" no existe se agrega al listado de dicho archivo.
     *
     * @param patron Combinacion cliente/tienda/fecha a buscar en el archivo
     * @throws IOException
     */
    public synchronized void writeMainControlFile(String patron) throws IOException {
        boolean addLine = true;
        Scanner scan = null;
        BufferedWriter bw = null;
        log.debug("start");

        try {
            File file = new File(rootFolder + mainFile);

            if (!file.exists() && !file.createNewFile()) {
                log.debug("error: no se puede crear el archivo");
                throw new IOException("No se puede crear el archivo " + mainFile);
            }

            scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (patron.equalsIgnoreCase(line)) {
                    addLine = false;
                    break;
                }
            }

            log.debug("agregar: " + addLine);

            if (addLine) {
                //el parametro "true" en el constructor de FileWriter indica que el dato se agregara al archivo (no sobreescribe)
                bw = new BufferedWriter(new FileWriter(file, true));
                bw.write(patron);
                bw.newLine();
            }
        } catch (FileNotFoundException fex) {
            log.debug("FileNotFoundException Error: " + fex.getMessage());
            throw new IOException("FileNotFoundException");
        } finally {
            try {
                if (scan != null) {
                    scan.close();
                    scan = null;
                }
                if (bw != null) {
                    bw.flush();
                    bw.close();
                    bw = null;
                }
            } catch (IOException ioex) {
                log.error("IOException Error: " + ioex.getMessage());
                throw ioex;
            }
        }
        log.debug("end");
    }

    /**
     * Este metodo crea y formatea la unidad previamente configurada en el archivo system.properties. Para ello verifica si la
     * particion existe. De no existir la crea y posteriormente la formatea.
     *
     * @return boolean TRUE para indicar que el formateo se realizo con exito. FALSE en caso contrario
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean formatDevice() throws Exception {
        log.debug("start");
        boolean resp = false;

        try {
            resp = createPartition();
            log.debug("create partition: " + resp);

            if (resp) {
                resp = formatPartition();
                log.debug("format device: " + resp);
            }

        } catch (Exception ex) {
            throw ex;
        }

        return resp;
    }

    /**
     * Este metodo crea la partici�n previamente configurada en el archivo system.properties. Para ello verifica si la particion
     * existe. De no existir la crea.
     *
     * @return boolean Indica si la creo con �xito (TRUE) o no (FALSE)
     * @throws IOException
     * @throws InterruptedException
     */
    private boolean createPartition() throws Exception {
        log.debug("start");

        boolean resp = false;
        String s = "";
        StringBuilder error = new StringBuilder();
        StringBuilder answer = new StringBuilder();

        try {
            resp = checkPartitionExist();

            if (!resp) {
                //creando particion
                log.debug("createPartitionCommand: " + createPartitionCommand);
                Process p = Runtime.getRuntime().exec(createPartitionCommand);

                //esperando que finalice la creacion
                int proc = p.waitFor();
                resp = proc == 0;
                log.debug("p.waitFor: " + proc);

                BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

                //si ocurre un error se llena el "answerError"
                while ((s = stdError.readLine()) != null) {
                    error.append(s).append("\n");
                }

                if (error.length() > 0) {
                    log.debug("Error: " + error.toString());
                }

                //si todo sale bien se llena el "answerMount"
                while ((s = stdInput.readLine()) != null) {
                    answer.append(s).append("\n");
                }

                if (answer.length() > 0) {
                    log.debug("answer: " + answer.toString());
                }
            } else {
                log.debug("particion ya existe");
            }
        } catch (Exception ex) {
            log.debug("Exception: " + ex.getMessage());
            throw ex;
        }

        log.debug("end. Status: " + resp);
        return resp;
    }

    /**
     * Verifica si la particion previamente indicada en el archivo system.properties existe o no.
     *
     * @return boolean TRUE si la particion existe, FALSE en caso contrario.
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean checkPartitionExist() throws Exception {
        log.debug("start");
        boolean resp = false;
        String s = "";
        StringBuilder error = new StringBuilder();
        StringBuilder answer = new StringBuilder();

        try {
            log.debug("checkPartitionCommand: " + checkPartitionCommand);
            Process p = Runtime.getRuntime().exec(checkPartitionCommand);

            p.waitFor();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            //si ocurre un error se llena el "answerError"
            while ((s = stdError.readLine()) != null) {
                error.append(s).append("\n");
            }

            if (error.length() > 0) {
                log.debug("Error: " + error.toString());
            }

            //verificando la particion
            while ((s = stdInput.readLine()) != null) {
                if (s.contains(externalDevice)) {
                    answer.append(s).append("\n");
                    resp = true;
                }
            }

            if (answer.length() > 0) {
                log.debug("answer: " + answer.toString());
            }

        } catch (Exception ex) {
            log.debug("Error: " + ex.getMessage());
            throw ex;
        }

        log.debug("end. Status: " + resp);
        return resp;
    }

    /**
     * Formata la particion configurada previamente en el archivo system.properties.
     *
     * @return boolean TRUE si el formateo fue exitoso, FALSE en caso contrario
     * @throws IOException
     * @throws InterruptedException
     */
    private boolean formatPartition() throws Exception {
        log.debug("start");

        boolean resp = false;
        String s = "";
        StringBuilder error = new StringBuilder();
        StringBuilder answer = new StringBuilder();

        try {
            //formateando particion
            Process p = Runtime.getRuntime().exec(formatPartitionCommand);

            //esperando que finalice el formateo
            int proc = p.waitFor();
            resp = proc == 0;
            log.debug("p.waitFor: " + proc);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            //si ocurre un error se llena el "answerError"
            while ((s = stdError.readLine()) != null) {
                error.append(s).append("\n");
            }

            if (error.length() > 0) {
                log.debug("Error: " + error.toString());
            }

            //si todo sale bien se llena el "answerMount"
            while ((s = stdInput.readLine()) != null) {
                answer.append(s).append("\n");
            }

            if (answer.length() > 0) {
                log.debug("answer: " + answer.toString());
            }
        } catch (Exception ex) {
            log.debug("Exception: " + ex.getMessage());
            throw ex;
        }

        log.debug("end. Status: " + resp);
        return resp;
    }
}
