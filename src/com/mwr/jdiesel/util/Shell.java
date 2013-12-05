package com.mwr.jdiesel.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.util.Log;

public class Shell {
	
	private Process fd = null;
	private int[] id = new int[1];
	BufferedInputStream stdin = null;
	BufferedOutputStream stdout = null;
	
	public Shell() throws IOException, InterruptedException {
		Log.i("JDIESEL : SHELL", "STARTING SHELL");
		this.fd = Runtime.getRuntime().exec("/system/bin/sh");
		Log.i("JDIESEL : SHELL", "ATTACHING STREAMS");
		this.stdin = new BufferedInputStream(this.fd.getInputStream());
		this.stdout = new BufferedOutputStream(this.fd.getOutputStream());
		Log.i("JDIESEL : SHELL", "MOVING TO USER DIR");
		this.write(String.format("cd %s", System.getProperty("user.dir")));
		this.read();
		
	}
	
    public void close() {
    	this.fd.destroy();
    }

	public String read() throws IOException, InterruptedException {
		Log.i("JDIESEL : SHELL", "READING SHELL");
		StringBuffer value = new StringBuffer();
		
		while(this.stdin.available() > 0) {
			for(int i=0; i<this.stdin.available(); i++) {
				int c = this.stdin.read();

				value.append((char)c);
			}
			
			Thread.sleep(50);
		}
		Log.i("JDIESEL : SHELL", "RETURNING: " + value.toString());
		return value.toString();
	}
	
	public boolean valid() {
		try {
			Runtime run = Runtime.getRuntime();
			Process pr = run.exec("ps " + this.id[0]);
			pr.waitFor();
			
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = "";
			while((line=buf.readLine()) != null) {
				if(line.contains("" + this.id[0])) {
					if(line.split("\\s+")[7].equals("Z"))
						return false;
				}
			}
		}
		catch(IOException e) {Log.e("JDIESEL : SHELL", String.format("IO ERROR: %s", e.getMessage()));}
		catch (InterruptedException e) {Log.e("JDIESEL : SHELL", String.format("INTERRUPTED ERROR: %s", e.getMessage()));}
		
		return true;
	}
    
    public void write(String value) throws IOException {
    	Log.i("JDIESEL : SHELL", "WRITING TO SHELL: " + value);
		this.stdout.write((value + "\n").getBytes());
		this.stdout.flush();
		Log.i("JDIESEL : SHELL", "FLUSHING");
		
	}
    
}
