/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionmanagement;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 *
 * @author Cesar
 */
public class HttpAuthenticator extends Authenticator {

    private String user;
    private String passwd;

    public HttpAuthenticator(String user, String passwd) {
        this.user = user;
        this.passwd = passwd;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, passwd.toCharArray());
    }
}
