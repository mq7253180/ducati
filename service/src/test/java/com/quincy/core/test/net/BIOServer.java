package com.quincy.core.test.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStore.SecretKeyEntry;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public abstract class BIOServer extends IOAbstract implements Runnable {
	private ServerSocket serverSocket = null;
	private List<Thread> threads = new ArrayList<Thread>();
	protected boolean running = true;

	protected abstract void handle(InputStream in, OutputStream out) throws IOException;

	public BIOServer(int port) throws IOException, GeneralSecurityException {
		this.ssl = (port%1000)==443;
		if(this.ssl) {
			System.setProperty("javax.net.debug", "ssl,handshake");
			System.setProperty("javax.net.ssl.trustStore", SSL_KEYSTORE_PATH);
			System.setProperty("javax.net.ssl.trustStorePassword", SSL_KEYSTORE_PWD);
			InputStream in = new FileInputStream(SSL_KEYSTORE_PATH);
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(in, SSL_KEYSTORE_PWD.toCharArray());
			logSSLInfo(keyStore);
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
			keyManagerFactory.init(keyStore, SSL_KEY_PWD.toCharArray());//key密码，创建管理jks密钥库的x509密钥管理器，用来管理密钥，需要key的密码
			SSLContext context = SSLContext.getInstance(SSL_VERSION_TLS_1);//构造SSL环境，指定SSL版本为3.0，也可以使用TLSv1，但是SSLv3更加常用。
			//第二个参数TrustManager[] 是认证管理器，在需要双向认证时使用
			//第二个参数是告诉JSSE使用的可信任证书的来源，设置为null是从javax.net.ssl.trustStore中获得证书
			//第三个参数是JSSE生成的随机数，这个参数将影响系统的安全性，设置为null是个好选择，可以保证JSSE的安全性
			context.init(keyManagerFactory.getKeyManagers(), null, null);
			SSLServerSocketFactory serverSocketFactory = context.getServerSocketFactory();
			this.serverSocket =  serverSocketFactory.createServerSocket(port);
			((SSLServerSocket)this.serverSocket).setNeedClientAuth(false);
		} else {
			this.serverSocket = new ServerSocket(port);
		}
	}

	@Override
	public void run() {
		try {
			doServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start(int count) {
		synchronized(this.threads) {
			for(int i=0;i<count;i++) {
				Thread thread = new Thread(this);
				this.threads.add(thread);
				thread.start();
			}
		}
	}

	private void doServer() throws IOException {
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		OutputStream bOut = null;
		InputStream bIn = null;
		try {
			while(!serverSocket.isClosed()) {
				socket = serverSocket.accept();
				if(!serverSocket.isClosed()) {
					in = socket.getInputStream();
					out = socket.getOutputStream();
					bIn = new BufferedInputStream(in);
					bOut = new BufferedOutputStream(out);
					this.handle(bIn, bOut);
				}
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
			if(serverSocket!=null)
				serverSocket.close();
		}
	}
	
	public void stop() throws IOException {
		if(this.serverSocket != null)
			this.serverSocket.close();
	}

	private static String SSL_KEY_ALIAS = "quincy1";
	private static String SSL_KEY_PWD = "keypwd1";

	private void logSSLInfo(KeyStore keyStore) throws GeneralSecurityException {
		Provider provider = keyStore.getProvider();
		PrivateKeyEntry privateKeyEntry = (PrivateKeyEntry)keyStore.getEntry(SSL_KEY_ALIAS, new KeyStore.PasswordProtection(SSL_KEY_PWD.toCharArray()));
		PrivateKey privateKey = privateKeyEntry.getPrivateKey();
		System.out.println("KeyStore: "+keyStore.getType()+"|"+keyStore.isKeyEntry(SSL_KEY_ALIAS)+"|"+keyStore.isCertificateEntry(SSL_KEY_ALIAS)+"|"+keyStore.entryInstanceOf(SSL_KEY_ALIAS, PrivateKeyEntry.class)+"|"+keyStore.entryInstanceOf(SSL_KEY_ALIAS, SecretKeyEntry.class)+"|"+provider.getName()+"|"+provider.getInfo()+"|"+provider.size()+"\r\n\r\n");
		System.out.println("PrivateKey: "+privateKey.getAlgorithm()+"|"+privateKey.getFormat()+"\r\n\r\n"+new String(privateKey.getEncoded())+"\r\n\r\n");
		Certificate[] certificates = keyStore.getCertificateChain(SSL_KEY_ALIAS);
		for(Certificate certificate:certificates) {
			PublicKey publicKey = certificate.getPublicKey();
			System.out.println("PublicKey: "+certificate.getType()+"|"+publicKey.getAlgorithm()+"|"+publicKey.getFormat()+"|"+new String(certificate.getEncoded())+"\r\n\r\n"+new String(publicKey.getEncoded()));
		}
//		SecretKey secretKey = null;
//	    SecretKeyEntry secretKeyEntry = new SecretKeyEntry(secretKey);
//	    keyStore.setEntry(KEY_ALIAS, secretKeyEntry, new KeyStore.PasswordProtection(SSL_KEYSTORE_PWD.toCharArray()));
	}

}
