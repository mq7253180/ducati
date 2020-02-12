package com.quincy.core.test.net.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

import com.quincy.core.test.net.BIOServer;

public class BIOServerNettyImpl extends BIOServer {
	public BIOServerNettyImpl(int port) throws IOException, GeneralSecurityException {
		super(port);
	}

	@Override
	protected void handle(InputStream in, OutputStream out) throws IOException {
		boolean first = true;
		while(running) {
			if(first) {
				this.doHandShake(in, out);
				first = false;
			} else {
				
			}
		}
	}
	
	private void doHandShake(InputStream in, OutputStream out) {
		
	}

}
