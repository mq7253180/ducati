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
			StringBuilder request = new StringBuilder(1000)
					.append("GET /favicon.ico HTTP/1.1\r\n")
					.append("Host: localhost:8080\r\n")
					.append("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:72.0) Gecko/20100101 Firefox/72.0\r\n")
					.append("Accept: image/webp,*/*\r\n")
					.append("Accept-Language: en-US,en;q=0.5\r\n")
					.append("Accept-Encoding: gzip, deflate\r\n")
					.append("Connection: keep-alive\r\n")
					.append("Cache-Control: max-age=0\r\n")
					.append("\r\n");
			try {
				client.send("jlcedu.maqiangcgq.com", port, request.toString());
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
