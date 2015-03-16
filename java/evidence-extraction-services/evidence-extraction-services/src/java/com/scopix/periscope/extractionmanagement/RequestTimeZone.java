package com.scopix.periscope.extractionmanagement;

/**
 * 
 * @author nelson
 */
public class RequestTimeZone {

	private String request;
	private String timeZone;
	private String storeName;
    private String dtype;

	public RequestTimeZone(String request, String timeZone, String storeName, String dtype) {
		this.request = request;
		this.timeZone = timeZone;
		this.storeName = storeName;
        this.dtype = dtype;
	}

	/**
	 * @return the request
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(String request) {
		this.request = request;
	}

	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * @return the storeName
	 */
	public String getStoreName() {
		return storeName;
	}

	/**
	 * @param storeName the storeName to set
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

    /**
     * @return the dtype
     */
    public String getDtype() {
        return dtype;
    }

    /**
     * @param dtype the dtype to set
     */
    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

}
