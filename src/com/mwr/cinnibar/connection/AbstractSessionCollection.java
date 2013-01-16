package com.mwr.cinnibar.connection;

import java.util.Collection;
import java.util.HashMap;

public abstract class AbstractSessionCollection {
	
	private HashMap<String,AbstractSession> sessions = new HashMap<String,AbstractSession>();
	
	public Collection<AbstractSession> all() {
		return this.sessions.values();
	}

	public boolean any() {
		return !this.sessions.isEmpty();
	}

	public abstract AbstractSession create();
	
	protected AbstractSession storeSession(AbstractSession session) {
		this.sessions.put(session.getSessionId(), session);
		session.start();
			
		return session;
	}

	public AbstractSession get(String session_id) {
		return this.sessions.get(session_id);
	}
	
	protected abstract void onSessionStarted(AbstractSession session);
	
	protected abstract void onSessionStopped(AbstractSession session);

	public AbstractSession stop(String session_id) {
		AbstractSession session = this.sessions.get(session_id);

		if(session != null) {
			try {
				session.stopSession();
				session.join();
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}

			this.sessions.remove(session_id);
				
			this.onSessionStopped(session);
		}
			
		return session;
	}

	public void stopAll() {
		String[] keys = this.sessions.keySet().toArray(new String[] {});
		
		for(String session_id : keys)
			this.stop(session_id);
	}

}
