package com.scopix.periscope.extractionmanagement;

import javax.persistence.Entity;

@Entity
@SuppressWarnings("serial")
public class VadaroPeopleCountingEvidenceProvider extends EvidenceProvider {

	private String port;
    private String userName;
    private String password;
    private String protocol;
    private String ipAddress;

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}