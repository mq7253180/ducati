package com.quincy.core.test.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public abstract class BIOClient extends IOAbstract {
	protected abstract void handle(InputStream in, OutputStream out, String request) throws IOException;

	public void send(String ip, int port, String request) throws IOException, GeneralSecurityException {
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		OutputStream bOut = null;
		InputStream bIn = null;
		try {
			socket = createSocket(ip, port);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			bIn = new BufferedInputStream(in);
			bOut = new BufferedOutputStream(out);
			handle(bIn, bOut, request);
			bOut.close();
			bIn.close();
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			throw e;
		} finally {
			if(bOut!=null)
				bOut.close();
			if(bIn!=null)
				bIn.close();
			if(out!=null)
				out.close();
			if(in!=null)
				in.close();
			if(socket!=null)
				socket.close();
		}
	}

	private Socket createSocket(String ip, int port) throws IOException, GeneralSecurityException {
		this.ssl = (port%1000)==443;
		if(this.ssl) {
			System.setProperty("javax.net.debug", "ssl,handshake");
			System.setProperty("javax.net.ssl.trustStore", SSL_KEYSTORE_PATH);
			System.setProperty("javax.net.ssl.trustStorePassword", SSL_KEYSTORE_PWD);

			return SSLSocketFactory.getDefault().createSocket(ip, port);

			/*KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());//创建一个keystore来管理密钥库
			keyStore.load(new FileInputStream(SSL_KEYSTORE_PATH), SSL_KEYSTORE_PWD.toCharArray());
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
			trustManagerFactory.init(keyStore);//验证数据，可以不传入key密码，创建TrustManagerFactory,管理授权证书
			SSLContext context = SSLContext.getInstance(SSL_VERSION_TLS_1);//构造SSL环境，指定SSL版本为3.0，也可以使用TLSv1，但是SSLv3更加常用。
			//KeyManager[] 第一个参数是授权的密钥管理器，用来授权验证。第二个是被授权的证书管理器，
			//用来验证服务器端的证书。只验证服务器数据，第一个管理器可以为null
			context.init(null, trustManagerFactory.getTrustManagers(), null);
			SSLSocketFactory socketFactory = context.getSocketFactory();
			return socketFactory.createSocket(ip, port);*/
		} else {
			return new Socket(ip, port);
		}
	}

}
