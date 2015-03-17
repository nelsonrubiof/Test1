package com.scopix.periscope.exception;

public class HttpClientInitializationException extends Exception {

	private static final long serialVersionUID = -6266872262772056793L;

	public HttpClientInitializationException(Exception ex) {
		super(ex);
	}

	public HttpClientInitializationException(String message) {
		super(message);
	}
}