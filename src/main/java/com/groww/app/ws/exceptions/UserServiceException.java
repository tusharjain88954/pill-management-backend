package com.groww.app.ws.exceptions;

public class UserServiceException extends RuntimeException{
	 

	private static final long serialVersionUID = 6221632102895140494L;

	public UserServiceException(String message)
	{
		super(message);
	}
}
