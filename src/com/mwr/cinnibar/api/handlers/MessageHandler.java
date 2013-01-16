package com.mwr.cinnibar.api.handlers;

import com.mwr.cinnibar.api.Protobuf.Message;
import com.mwr.cinnibar.api.InvalidMessageException;

public interface MessageHandler {
	
	public Message handle(Message message) throws InvalidMessageException;
	
}
