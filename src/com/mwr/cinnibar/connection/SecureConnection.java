package com.mwr.cinnibar.connection;

public interface SecureConnection {

	public String getHostCertificateFingerprint();
	public String getPeerCertificateFingerprint();
	
}
