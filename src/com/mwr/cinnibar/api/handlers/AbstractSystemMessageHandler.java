package com.mwr.cinnibar.api.handlers;

import com.mwr.cinnibar.api.InvalidMessageException;
import com.mwr.cinnibar.api.Protobuf.Message;

public abstract class AbstractSystemMessageHandler implements MessageHandler {
	
	@Override
	public Message handle(Message message) throws InvalidMessageException {
		if(message.getType() != Message.MessageType.SYSTEM_REQUEST)
			throw new InvalidMessageException("is not a SYSTEM_REQUEST", message);
		if(!message.hasSystemRequest())
			throw new InvalidMessageException("does not contain a SYSTEM_REQUEST", message);
		
		switch(message.getSystemRequest().getType()) {
		case LIST_DEVICES:
			return this.handleListDevices(message);
			
		case LIST_SESSIONS:
			return this.handleListSessions(message);
			
		case PING:
			return this.handlePing(message);
			
		case START_SESSION:
			return this.startSession(message);
			
		case STOP_SESSION:
			return this.stopSession(message);
			
		default:
			throw new InvalidMessageException("unhandled SYSTEM_REQUEST type: " + message.getSystemRequest().getType().toString(), message);
		}
	}
		
	protected abstract Message handleListDevices(Message message) throws InvalidMessageException;
	
	protected abstract Message handleListSessions(Message message) throws InvalidMessageException; 
	
	protected abstract Message handlePing(Message message) throws InvalidMessageException;
	
	protected abstract Message startSession(Message message) throws InvalidMessageException;
	
	protected abstract Message stopSession(Message message) throws InvalidMessageException;

}
