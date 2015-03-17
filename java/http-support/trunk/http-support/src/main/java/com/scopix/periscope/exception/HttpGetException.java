package com.scopix.periscope.exception;

public class HttpGetException extends Exception {

	private static final long serialVersionUID = 2083001392648306213L;
	
	public HttpGetException(Exception ex) {
		super(ex);
	}
}