package com.scopix.periscope.manager;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import com.scopix.periscope.command.LoginCommand;
import com.scopix.periscope.command.LogoutCommand;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;

/**
 * Manager para la invocación de servicios relacionados con autenticación
 * 
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SpringBean(rootClass = SecurityManager.class)
public class SecurityManager implements Serializable {

    private LoginCommand loginCommand;
    private LogoutCommand logoutCommand;
    private static final long serialVersionUID = 18424192726689083L;
    private static Logger log = Logger.getLogger(SecurityManager.class);

    /**
     * Invocación servicio para obtener lista de clientes
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param login login del usuario
     * @param password password del usuario
     * @param security número del security a invocar (parametrizado en el applicationContext-cxf.xml)
     * @return Long sessionId del usuario autenticado
     * @param security número del security a invocar (parametrizado en el applicationContext-cxf.xml)
     * @since 6.0
     * @date 26/03/2013
     * @throws ScopixException excepción durante la autenticación del usuario
     */
    public Long authenticateUser(String login, String password, String security) throws ScopixException {
        log.info("start, login: [" + login + "], security: [" + security + "]");
        Long userToken;
        try {
            String data = hash256(password, login);
            userToken = getLoginCommand().execute(login, data, security);
        } catch (NoSuchAlgorithmException e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end - " + login);
        return userToken;
    }

    /**
     * Encripta password
     * 
     * @param password dato a encriptar
     * @param login login del usuario autenticado
     * @return String con dato encriptado
     * @throws NoSuchAlgorithmException si no es posible generar la encriptacion del dato
     */
    public static String hash256(String password, String login) throws NoSuchAlgorithmException {
        log.info("start - " + login);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        log.info("end - " + login);
        return bytesToHex(md.digest(), login);
    }

    /**
     *
     * @param bytes arreglo de bytes a transformar
     * @param login login del usuario autenticado
     * @return String con los byts transformados a hexadecimal
     */
    public static String bytesToHex(byte[] bytes, String login) {
        log.info("start - " + login);
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        log.info("end - " + login);
        return result.toString();
    }

    /**
     * Invocación del servicio para cerrar la sesión del usuario
     * 
     * @author carlos polo
     * @version 2.0.0
     * @param sessionId token del usuario autenticado
     * @param login login del usuario autenticado
     * @param security número del security a invocar (parametrizado en el applicationContext-cxf.xml)
     * @since 6.0
     * @date 19/11/2013
     */
    public void logout(Long sessionId, String login, String security) {
        log.info("start, sessionId: [" + sessionId + "], security: [" + security + "] - " + login);
        getLogoutCommand().execute(sessionId, security);
        log.info("end - " + login);
    }

    /**
     * @return the loginCommand
     */
    public LoginCommand getLoginCommand() {
        if (loginCommand == null) {
            loginCommand = new LoginCommand();
        }
        return loginCommand;
    }

    /**
     * @param loginCommand the loginCommand to set
     */
    public void setLoginCommand(LoginCommand loginCommand) {
        this.loginCommand = loginCommand;
    }

    /**
     * @return the logoutCommand
     */
    public LogoutCommand getLogoutCommand() {
        if (logoutCommand == null) {
            logoutCommand = new LogoutCommand();
        }
        return logoutCommand;
    }

    /**
     * @param logoutCommand the logoutCommand to set
     */
    public void setLogoutCommand(LogoutCommand logoutCommand) {
        this.logoutCommand = logoutCommand;
    }
}