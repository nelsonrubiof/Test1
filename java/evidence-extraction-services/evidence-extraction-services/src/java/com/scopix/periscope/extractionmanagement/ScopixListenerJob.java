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
 *  Jobs.java
 * 
 *  Created on 02-01-2014, 05:30:57 PM
 * 
 */
package com.scopix.periscope.extractionmanagement;

/**
 * @author EmO
 */
public class ScopixListenerJob {

    private String protocol;
    private String ip;
    private String port;
    private String storeName;
    private String timeZone;
    private String providerName;
    private String queueName;

    @Override
    public String toString() {
        return "ScopixListenerJob{" + "protocol=" + protocol + ", ip=" + ip + ", port=" + port + ", storeName=" + storeName
            + ", timeZone=" + timeZone + ", providerName=" + providerName + ", queueName=" + queueName + '}';
    }

    //	@Override
//	public String toString() {
//		return "ScopixListenerJob [protocol=" + protocol + ", ip=" + ip
//				+ ", port=" + port + ", storeName=" + storeName + ", timeZone="
//				+ timeZone + "]";
//	}
    public ScopixListenerJob(String protocol, String ip, String port, String storeName, String timeZone, String providerName,
        String queueName) {
        this.protocol = protocol;
        this.ip = ip;
        this.port = port;
        this.storeName = storeName;
        this.timeZone = timeZone;
        this.providerName = providerName;
        this.queueName = queueName;

    }

    public String getProtocol() {
        return protocol;
    }

    public String getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getTimeZone() {
        return timeZone;
    }

    /**
     * @return the providerName
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * @return the queueName
     */
    public String getQueueName() {
        return queueName;
    }

}
