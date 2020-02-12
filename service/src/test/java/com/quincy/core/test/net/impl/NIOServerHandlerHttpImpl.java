package com.quincy.core.test.net.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.quincy.core.test.net.NIOServerHandlerShort;

public class NIOServerHandlerHttpImpl extends NIOServerHandlerShort {
	private final static String CHARSET = "UTF-8";

	protected void handle(SocketChannel socketChannel) throws IOException {
		String infoPrefix = "Server: ";
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf;
		int size = 0;
		while((size=socketChannel.read(buffer))>0) {
			buffer.flip();
			buf = new byte[size];
			buffer.get(buf);
			buffer.clear();
			baos.write(buf);
		}
		System.out.println(infoPrefix+new String(baos.toByteArray()));
		String http = "";
//		String http = "Server: Quincy\r\nStatus-String: OK\r\nStatus-Integer: 200\r\nProtocol: HTTP/1.1\r\nContent-Type: text/html; charset="+CHARSET+"\r\n\r\n";
		http += "<html><head></head><body><font color=\"blue\">"+this.getId()+"处理的:</font> <font color=\"red\">内容WWW</font><br/><input type=\"text\"/><input type=\"button\" value=\"提交\"/></body></html>";
		buffer.clear();
		byte[] bufTmp = http.getBytes(CHARSET);
		buffer = ByteBuffer.allocate(bufTmp.length);
		buffer.put(bufTmp);
		buffer.flip();
		socketChannel.write(buffer);
	}
}
