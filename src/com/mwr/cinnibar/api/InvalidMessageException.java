package com.mwr.cinnibar.api;

import com.mwr.cinnibar.api.Protobuf.Message;

public class InvalidMessageException extends RuntimeException {
	
	private Message invalid_message = null;
	private static final long serialVersionUID = -3727783632022708351L;
	
	public InvalidMessageException(Message invalid_message) {
		this.invalid_message = invalid_message;
	}
	
	public InvalidMessageException(String message, Message invalid_message) {
		super(message);
		
		this.invalid_message = invalid_message;
	}
	
	public Message getInvalidMessage() {
		return this.invalid_message;
	}

}
