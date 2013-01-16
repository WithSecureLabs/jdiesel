package com.mwr.cinnibar.connection;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.mwr.cinnibar.api.InvalidMessageException;
import com.mwr.cinnibar.api.Protobuf.Message;

public abstract class AbstractSession extends Thread {

	private BlockingQueue<Message> messages = new LinkedBlockingQueue<Message>();
	private String session_id = null;
	public volatile boolean running = false;
	
	public AbstractSession() {
		this.session_id = this.generateSessionId();
	}

	protected AbstractSession(String session_id) {
		this.session_id = session_id;
	}
	
	public void deliverMessage(Message message) {
		this.messages.offer(message);
	}
		
	private String generateSessionId() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}

	public String getSessionId() {
		return this.session_id;
	}
	
	protected abstract Message handleMessage(Message message) throws InvalidMessageException;
		
	@Override
	public void run() {
		this.running = true;
		
		while(this.running) {
			Message message = null;
			
			try {
				message = this.messages.take();
			}
			catch (InterruptedException e) {}
			
			if(message != null) {
				try {
					Message response = this.handleMessage(message);
					
					if(response != null)
						this.send(response);
				}
				catch(InvalidMessageException e) {}
			}
		}
	}
	
	public abstract void send(Message message);
	
	public void stopSession() {
		this.running = false;
		
		this.interrupt();
	}

}
