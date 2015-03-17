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
 */
package com.scopix.periscope.periscopefoundation.mailer;

import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Utility Class used to send mails.
 *
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public class SimpleMailer {

    private static SimpleMailer instance;

    /**
     * This method returns the instance of SimpleMailer. This is a singleton object
     */
    public static synchronized SimpleMailer getInstance() {
        if (instance == null) {
            instance = new SimpleMailer();
            instance.init();
        }
        return instance;
    }

    private SimpleMailer() {
    }

    /**
     * Sends an SMTP email with the specified parameters.
     *
     * @param to The Recipient. Must be a well formed mail address.
     * @param subject The subject
     * @param text The body of the message.
     */
    public void send(String to, String subject, String text) {

        try {
            MimeMessage msg = this.createMimeMessage(subject, text);

            Address toAddr = new InternetAddress(to);
            msg.addRecipient(Message.RecipientType.TO, toAddr);

            Transport.send(msg);
        } catch (AddressException e) {
            throw new UnexpectedRuntimeException(e);
        } catch (MessagingException e) {
            throw new UnexpectedRuntimeException(e);
        }
    }

    /**
     * Send method uses this method to create the MIME message.
     *
     * @param subject The subject
     * @param text The body of the message.
     */
    private MimeMessage createMimeMessage(String subject, String text) throws MessagingException {
        MimeMessage msg = new MimeMessage(this.session);
        msg.setText(text);
        msg.setSubject(subject);
        msg.setFrom(new InternetAddress(this.from));
        return msg;
    }

    /**
     * Sets the initial parameters for mail sending. It takes them from the profile.server.properties file or the profile.<user
     * name>.properties
     */
    private void init() {

        Properties p = new Properties();
        p.put("mail.smtp.host", SystemConfig.getStringParameter("periscope.smtp.host"));
        p.put("mail.smtp.port", SystemConfig.getStringParameter("periscope.smtp.port"));
        p.put("mail.smtp.auth", SystemConfig.getStringParameter("periscope.smtp.auth"));
        p.put("mail.smtp.socketFactory.port", SystemConfig.getStringParameter("periscope.smtp.socketFactory.port"));
        p.put("mail.smtp.socketFactory.class", SystemConfig.getStringParameter("periscope.smtp.socketFactory.class"));
        p.put("mail.smtp.socketFactory.fallback", SystemConfig.getStringParameter("periscope.smtp.socketFactory.fallback"));

        javax.mail.Authenticator authenticator = new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String userName = SystemConfig.getStringParameter("periscope.smtp.userName");
                String pass = SystemConfig.getStringParameter("periscope.smtp.pass");
                return new PasswordAuthentication(userName, pass);
            }
        };

        this.from = SystemConfig.getStringParameter("periscope.smtp.from");
        this.session = Session.getDefaultInstance(p, authenticator);
    }
    private String from;
    private Session session;
}
