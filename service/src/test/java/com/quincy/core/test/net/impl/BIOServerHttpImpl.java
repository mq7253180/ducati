package com.quincy.core.test.net.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

import com.quincy.core.test.net.BIOServer;

public class BIOServerHttpImpl extends BIOServer {
	public BIOServerHttpImpl(int port) throws IOException, GeneralSecurityException {
		super(port);
	}

	@Override
	protected void handle(InputStream in, OutputStream out) throws IOException {
		String infoPrefix = "Server: ";
		int length = in.read();
		byte[] buf = new byte[length];
		in.read(buf, 0, length);
		String request = new String(buf);
		System.out.println(infoPrefix+request);
		String html = "<html><head></head><body>WWW</body></html>";
		out.write(html.getBytes().length);
		out.write(html.getBytes());
		out.flush();
	}
	
}
