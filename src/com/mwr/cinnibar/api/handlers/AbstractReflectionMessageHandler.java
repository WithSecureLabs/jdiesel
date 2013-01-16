package com.mwr.cinnibar.api.handlers;

import com.mwr.cinnibar.api.InvalidMessageException;
import com.mwr.cinnibar.api.Protobuf.Message;

public abstract class AbstractReflectionMessageHandler implements MessageHandler {
	
	@Override
	public Message handle(Message message) throws InvalidMessageException {
		if(message.getType() != Message.MessageType.REFLECTION_REQUEST)
			throw new InvalidMessageException(message);
		if(!message.hasReflectionRequest())
			throw new InvalidMessageException(message);
		
		try {
			switch(message.getReflectionRequest().getType()) {
			case CONSTRUCT:
				if(!message.getReflectionRequest().hasConstruct())
					throw new InvalidMessageException(message);
				
				return this.handleConstruct(message);
				
			case DELETE:
				if(!message.getReflectionRequest().hasDelete())
					throw new InvalidMessageException(message);
				
				return this.handleDelete(message);
				
			case DELETE_ALL:
				return this.handleDeleteAll(message);
				
			case GET_PROPERTY:
				if(!message.getReflectionRequest().hasGetProperty())
					throw new InvalidMessageException(message);
				
				return this.handleGetProperty(message);
				
			case INVOKE:
				if(!message.getReflectionRequest().hasInvoke())
					throw new InvalidMessageException(message);
				
				return this.handleInvoke(message);
				
			case RESOLVE:
				if(!message.getReflectionRequest().hasResolve())
					throw new InvalidMessageException(message);
				
				return this.handleResolve(message);
						
			case SET_PROPERTY:
				if(!message.getReflectionRequest().hasSetProperty())
					throw new InvalidMessageException(message);
				
				return this.handleSetProperty(message);
			
			default:
				throw new InvalidMessageException(message);
			}
		}
		catch(Exception e) {
			if(e.getMessage() != null)
				return this.handleError(message, e.getMessage());
			else if(e.getCause() != null && e.getCause().getMessage() != null)
				return this.handleError(message, e.getCause().getMessage());
			else
				return this.handleError(message, "Unknown Exception");
		}
	}
	
	protected abstract Message handleConstruct(Message message) throws InvalidMessageException;
	
	protected abstract Message handleDelete(Message message) throws InvalidMessageException;
	
	protected abstract Message handleDeleteAll(Message message) throws InvalidMessageException;

	protected abstract Message handleError(Message request, String error_message);
	
	protected abstract Message handleGetProperty(Message message) throws InvalidMessageException;
	
	protected abstract Message handleInvoke(Message message) throws InvalidMessageException;
	
	protected abstract Message handleResolve(Message message) throws InvalidMessageException;
	
	protected abstract Message handleSetProperty(Message message) throws InvalidMessageException;
	
}
