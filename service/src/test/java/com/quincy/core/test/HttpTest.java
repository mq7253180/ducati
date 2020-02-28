package com.quincy.core.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPInputStream;

public class HttpTest {
	public static void test(String[][] REQUEST_PROPERTIES_PAIR) throws IOException {
		URL url = null;
//		InetAddress inetAddress = null;
//		SocketAddress socketAddress = null;
		String domain = null;
		int port = -1;
		String uri = null;
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
//		OutputStreamWriter osw = null;
		LineNumberReader lnr = null;
		Selector selector = null;
		try {
			url = new URL(REQUEST_PROPERTIES_PAIR[0][0]);
//			inetAddress = InetAddress.getByName(url.getHost());
//			String ip = inetAddress.getHostAddress();
			domain = url.getHost();
			port = url.getDefaultPort();
			uri = url.getPath();
			System.out.println(domain+":"+port+uri);
			socket = new Socket(domain, port);
			
//			socketAddress = new InetSocketAddress(domain, port);
//			socket = new Socket();
//			socket.connect(socketAddress);

			String http = appendHttp(domain, port, uri, REQUEST_PROPERTIES_PAIR);
			System.out.println(http.length()+": \r\n"+http);
			
			out = new BufferedOutputStream(socket.getOutputStream());
			out.write(http.getBytes());
			out.flush();
//			osw = new OutputStreamWriter(out);
//			osw.write(http);
//			osw.flush();

			/*selector = Selector.open();
			SocketChannel socketChannel = socket.getChannel();
			socketChannel.register(selector, SelectionKey.OP_ACCEPT);
			while (selector.select() > 0) {
				// Someone is ready for I/O, get the ready keys
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				// Walk through the ready keys collection and process date requests.
				while (it.hasNext()) {
					SelectionKey readyKey = it.next();
					it.remove();
					// The key indexes into the selector so you
					// can retrieve the socket that's ready for I/O
					SelectableChannel channel = readyKey.channel();
					String rs = handleChannel((ServerSocketChannel)channel);
					System.out.println("------------"+rs);
				}
			}*/

			in = new BufferedInputStream(socket.getInputStream());
			System.out.println("***"+in.available());
//			byte[] buf = new byte[in.available()];
//			in.read(buf);
//			System.out.println(uri+"--------------"+in.available()+"======="+new String(buf));
			lnr = new LineNumberReader(new InputStreamReader(in));
	        String line = null;
	        Map<String, String> header = new HashMap<String, String>(20);
	        while((line=lnr.readLine())!=null&&line.trim().length()>0) {
	        	int colonIndex = line.indexOf(':');
	        	if(colonIndex>0) {
	        		header.put(line.substring(0, colonIndex).trim(), line.substring(colonIndex+1, line.length()).trim());
	        	} else {
	        		String[] s = line.split("\\s+");
	        		if(s!=null&&s.length>2) {
	        			header.put("Protocol", s[0].trim());
		        		header.put("Status-Integer", s[1].trim());
		        		header.put("Status-String", s[2].trim());
	        		}
	        	}
	        }
	        System.out.println("********************HEADER******************");
	        Set<Entry<String, String>> entrySet = header.entrySet();
	        for(Entry<String, String> entry:entrySet) {
	        	System.out.println(entry.getKey()+": "+entry.getValue());
	        }
	        System.out.println("******************CONTENT********************");
	        StringBuffer content = new StringBuffer();
	        while((line=lnr.readLine())!=null) {
	        	content.append(line);
	        }
	        System.out.println("CONTENT: "+content.toString());
	        String extractedContent = null;
	        if("GZIP".equalsIgnoreCase(header.get("Content-Encoding"))) {
	        	extractedContent = extract(content.toString());
	        }
	        System.out.println(extractedContent);
	        System.out.println("**************************************");
		} catch (IOException e) {
			throw e;
		} finally {
//			if(osw!=null) {
//				osw.close();
//			}
			if(lnr!=null) {
				lnr.close();
			}
			if(selector!=null) {
				selector.close();
			}
			if(in!=null) {
				in.close();
			}
			if(out!=null) {
				out.close();
			}
			if(socket!=null) {
				socket.close();
			}
		}
	}

	private static String appendHttp(String domain, int port, String uri, String[][] REQUEST_PROPERTIES_PAIR) {
		StringBuffer http = new StringBuffer(2000)
				.append("GET ")
				.append(uri!=null&&uri.trim().length()>0?uri:"/")
				.append(" HTTP/1.1\r\n");
		for(int i=1;i<REQUEST_PROPERTIES_PAIR.length;i++)
			http.append(REQUEST_PROPERTIES_PAIR[i][0])
			.append(":")
			.append(REQUEST_PROPERTIES_PAIR[i][1])
			.append("\r\n");
		return http.append("Host:")
				.append(domain)
				.append("\r\n\r\n")
				.toString();
	}
	
	public static String extract(String str) throws IOException {   
		if (str == null || str.length() == 0) {
			return str;
		}
		InputStream in = null;
	    try {
			in = new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(str.getBytes("ISO-8859-1"))));
			byte[] buf = new byte[in.available()];
		    System.out.println("Content-Length: "+in.available());
		    in.read(buf);
		    return new String(buf);
		} catch (IOException e) {
			throw e;
		} finally {
			if(in!=null) {
				in.close();
			}
		}
	}
	
	private static String handleChannel(SocketChannel socketChannel) throws IOException {
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			byte[] bytes;
			int size = 0;
			while((size = socketChannel.read(buffer)) >= 0) {
				buffer.flip();
				bytes = new byte[size];
				buffer.get(bytes);
				baos.write(bytes);
				buffer.clear();
			}
			bytes = baos.toByteArray();
			return new String(bytes);
		} catch (IOException e) {
			throw e;
		} finally {
			if(baos!=null)
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(socketChannel!=null)
				socketChannel.close();
		}
	}

	public static void testHttp(String[][] REQUEST_PROPERTIES_PAIR) throws IOException {
		URL url = new URL(REQUEST_PROPERTIES_PAIR[0][0]);
		BufferedReader in = null;
		URLConnection connection = url.openConnection();
		for(int i=1;i<REQUEST_PROPERTIES_PAIR.length;i++) {
			connection.setRequestProperty(REQUEST_PROPERTIES_PAIR[i][0], REQUEST_PROPERTIES_PAIR[i][1]);
		}
		connection.setRequestProperty("Host", url.getHost());
//		connection.setDoOutput(true);
//		connection.setDoInput(true);
		connection.connect();
		Map<String, List<String>> map = connection.getHeaderFields();
		for(Map.Entry<String, List<String>> entry:map.entrySet()) {
            System.out.println(entry.getKey()+"--->"+entry.getValue());
        }
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = in.readLine()) != null)
			sb.append(line).append("\r\n");
		System.out.println("Result: "+sb.toString());
	}
	
	private static String[][] REQUEST_PROPERTIES_PAIR_SINA = {
			{"http://blog.sina.com.cn/yanjun258", "6"},
			{"Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"},
			{"Accept-Encoding", "gzip, deflate"},
			{"Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3"},
			{"Cache-Control", "max-age=0"},
			{"Connection", "keep-alive"},
			{"Cookie", "U_TRS1=00000053.37327fe7.57ac7bbb.53b241bb; blogAppAd_blog7index=1; UOR=,blog.sina.com.cn,; SINAGLOBAL=114.243.99.133_1470921707.248964; ULV=1470921699711:1:1:1::; vjuids=-4d0b903ba.15679c3f46f.0.bfc858bbea9a9; vjlast=1470921700.1470921700.30; U_TRS2=00000016.8b3836a0.57ac7c42.825cdca8; Apache=114.243.99.133_1470921794.424473"},
			{"If-Modified-Since", "Thu, 11 Aug 2016 07:25:51 GMT+8"},
			{"Upgrade-Insecure-Requests", "1"},
			{"User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0"}
		};
	
	private static String[][] REQUEST_PROPERTIES_PAIR_BAIDU = {
			{"http://tieba.baidu.com", "5"},
			{"Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"},
			{"Accept-Encoding", "gzip, deflate"},
			{"Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3"},
			{"Cache-Control", "max-age=0"},
			{"Connection", "keep-alive"},
			{"Cookie", "BAIDUID=E3C90D192DCF7C36ABD78E106EACC482:FG=1; TIEBAUID=cb23caae14130a0d384a57f1; TIEBA_USERTYPE=2efb27f75aacbb6939f3e510"},
			{"Upgrade-Insecure-Requests", "1"},
			{"User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0"}
		};
	
	private static String[][] REQUEST_PROPERTIES_PAIR = {
			{"http://my.oschina.net/hly3825/blog/33729", "6"},
			{"Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"},
			{"Accept-Encoding", "gzip, deflate"},
			{"Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3"},
			{"Cache-Control", "max-age=0"},
			{"Connection", "keep-alive"},
			{"Cookie", "U_TRS1=00000053.37327fe7.57ac7bbb.53b241bb; blogAppAd_blog7index=1; UOR=,blog.sina.com.cn,; SINAGLOBAL=114.243.99.133_1470921707.248964; ULV=1470921699711:1:1:1::; vjuids=-4d0b903ba.15679c3f46f.0.bfc858bbea9a9; vjlast=1470921700.1470921700.30; U_TRS2=00000016.8b3836a0.57ac7c42.825cdca8; Apache=114.243.99.133_1470921794.424473"},
			{"If-Modified-Since", "Thu, 11 Aug 2016 07:25:51 GMT+8"},
			{"Upgrade-Insecure-Requests", "1"},
			{"User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0"}
		};

	public static void main(String[] args) throws IOException {
//		SSLTest.test(REQUEST_PROPERTIES_PAIR_SINA);
//		SSLTest.testHttp(REQUEST_PROPERTIES_PAIR_SINA);

		HttpTest.test(REQUEST_PROPERTIES_PAIR_BAIDU);
//		SSLTest.testHttp(REQUEST_PROPERTIES_PAIR_BAIDU);

//		SSLTest.test(REQUEST_PROPERTIES_PAIR);
//		SSLTest.testHttp(REQUEST_PROPERTIES_PAIR);
		
//		Map<String, String> header = new HashMap<String, String>(20);
//		header.put("Content-Encoding", "gzip");
//		System.out.println("GZIP".equalsIgnoreCase(header.get("Content-Encoding")));
	}

}
