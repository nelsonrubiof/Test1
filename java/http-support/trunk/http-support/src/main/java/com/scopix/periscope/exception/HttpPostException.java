package com.scopix.periscope.exception;

public class HttpPostException extends Exception {

	private static final long serialVersionUID = -6305259184721804264L;

	public HttpPostException(Exception ex) {
		super(ex);
	}
}