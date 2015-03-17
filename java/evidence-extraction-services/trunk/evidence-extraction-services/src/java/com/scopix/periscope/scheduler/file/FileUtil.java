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
 * FileUtil.java
 *
 * Created on 06-01-2010, 02:00:48 PM
 *
 */
package com.scopix.periscope.scheduler.file;

import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *  Esta clase es utilizada para crear un archivo y escribir lineas en �l. Se desarrollo para la generacion de archivos
 *  de control, pero puede ser utilizada para otros propositos.
 *
 * @author Gustavo Alvarez
 */
@SpringBean
public class FileUtil {

    Logger log = Logger.getLogger(FileUtil.class);
    private File output;
    private BufferedWriter bw;

    /**
     * Crea un archivo a partir de una ruta determinada
     *
     * @param url  ruta del archivo
     * @throws IOException  en caso de errores de operacion
     */
    public void createFile(String url) throws IOException {
        log.error("start. URL: " + url);
        output = new File(url);

        try {
            if (!output.createNewFile() && !output.delete()) {
                log.error("Error: no se pudo crear (ni eliminar) el archivo.");
                throw new IOException("Error: no se pudo crear (ni eliminar) el archivo.");
            }

            bw = new BufferedWriter(new FileWriter(output));
        } catch (IOException ioex) {
            log.debug("IOError: " + ioex.getMessage());
            throw ioex;
        }
        log.error("end");
    }

    /**
     * Metodo utilizado para escribir una linea. Esta deja un retorno de carro al final (new line)
     *
     * @param text texto a grabar en el archivo
     * @throws IOException en caso de errores de operacion
     */
    public void writeLine(String text) throws IOException {
        log.debug("start. TEXT: " + text);
        try {
            bw.write(text);
            bw.newLine();
        } catch (IOException ioex) {
            log.debug("IOError: " + ioex.getMessage());
            throw ioex;
        }
        log.debug("end");
    }

    /**
     * Metodo utilizado para cerrar el buffer de escritura
     * 
     * @throws IOException en caso de errores de operacion
     */
    public void close() throws IOException {
        log.debug("start");
        try {
            if (bw != null) {
                bw.flush();
                bw.close();
                bw = null;
            }
        } catch (IOException ioex) {
            log.debug("IOError: " + ioex.getMessage());
            throw ioex;
        }
        log.debug("end");
    }

    /**
     * Metodo que sirve para crear un directorio a partir de una ruta dada.
     * 
     * @param directory Especifica el directorio a crear
     * @throws IOException En caso de no poder crear el directorio
     */
    public void makeDirectory(String directory) throws IOException {
        log.debug("start");
        try {
            File dir = new File(directory);

            if (!dir.exists() && !dir.mkdir()) {
                throw new IOException("No se pudo crear el directorio");
            }
        } catch (IOException ioex) {
            log.debug("Error: " + ioex.getMessage());
            throw ioex;
        } catch (SecurityException secex) {
            log.debug("Error: " + secex.getMessage());
            throw secex;
        }
        
        log.debug("end");
    }
}
