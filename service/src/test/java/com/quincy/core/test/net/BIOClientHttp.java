package com.quincy.core.test.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BIOClientHttp extends BIOClient {
	@Override
	protected void handle(InputStream in, OutputStream out, String request) throws IOException {
		out.write(request.getBytes());
		out.flush();
		int length = in.read();
		byte[] html = new byte[length];
		in.read(html, 0, length);
		System.out.println("Client: "+new String(html));
	}

	public static class BIOClientHttpImpl implements Runnable {
		private int port;
		private int index;

		public BIOClientHttpImpl(int port, int index) {
			this.port = port;
			this.index = index;
		}
		@Override
		public void run() {
			BIOClient client = new BIOClientHttp();
			String request = "a=a1&index="+index;
			try {
				client.send("localhost", port, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		final long start = System.currentTimeMillis();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("*************"+(System.currentTimeMillis()-start));
			}
        }));
        final int port = 8080;
//        final int port = 8443;
        int threads = 100;
		for(int i=0;i<threads;i++) {
			(new Thread(new BIOClientHttpImpl(port, i))).start();
		}
	}

}
