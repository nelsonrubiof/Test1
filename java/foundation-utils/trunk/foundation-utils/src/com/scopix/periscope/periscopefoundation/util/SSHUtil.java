/**
 *
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 *
 * SSHUtil.java
 *
 * Created on 07-07-2008, 12:56:14 PM
 *
 */
package com.scopix.periscope.periscopefoundation.util;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author César Abarza Suazo.
 * @version 1.0.0
 */
public class SSHUtil {

    private static Logger logger = Logger.getLogger(SSHUtil.class);
    private String host;
    private int port;
    private String user;
    private String password;
    private Session session;

    /**
     *
     */
    public SSHUtil() {
        super();
    }

    /**
     *
     * @param host Host a conectarse
     * @param port puerto
     * @param user usario
     * @param password clave
     */
    public SSHUtil(String host, int port, String user, String password) {
        super();
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    /**
     * Se conecta via ssh a el host indicado
     * @throws JSchException en caso de error retorna una excepcion nativa
     */
    public void connect() throws JSchException {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            LocalUserInfo lui = new LocalUserInfo();
            lui.setPassword(password);
            session.setUserInfo(lui);
            session.connect();
        } catch (JSchException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Crea un tunel segun datos entregados
     * @param localPort puerto local
     * @param remoteHost host remoto
     * @param remotePort puerto remoto
     * @throws JSchException en caso de error retorna una excepcion nativa
     */
    public void addTunnel(int localPort, String remoteHost, int remotePort) throws JSchException {
        try {
            session.setPortForwardingL(localPort, remoteHost, remotePort);
        } catch (JSchException e) {
            logger.info(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Se desconecta del ssh
     */
    public void disconnect() {
        session.disconnect();
    }

    /**
     *
     * @return String del Host Asociado
     */
    public String getHost() {
        return host;
    }

    /**
     *
     * @param host String con host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     *
     * @return puerto para el ssh
     */
    public int getPort() {
        return port;
    }

    /**
     *
     * @param port set de puerto
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     *
     * @return String usuario 
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user usuario a utilizar
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return String password para la conexion
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password para la conexion
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return Session ssh para el usuario
     */
    public Session getSession() {
        return session;
    }

    /**
     *
     * @param session ssh para el usuario
     */
    public void setSession(Session session) {
        this.session = session;
    }

    class LocalUserInfo implements UserInfo {

        private String passwd;

        @Override
        public String getPassword() {
            return passwd;
        }

        public void setPassword(String pwd) {
            this.passwd = pwd;
        }

        @Override
        public boolean promptYesNo(String str) {
            return true;
        }

        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public boolean promptPassphrase(String message) {
            return false;
        }

        @Override
        public boolean promptPassword(String message) {
            return true;
        }

        @Override
        public void showMessage(String message) {
            JOptionPane.showMessageDialog(null, message);
        }
    }
}
