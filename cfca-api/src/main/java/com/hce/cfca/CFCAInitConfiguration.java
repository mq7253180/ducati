package com.hce.cfca;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CFCAInitConfiguration {
	@Value("${cfca.platid}")
	private String cfcaPlatId;
	@Value("${cfca.addr}")
	private String cfcaHttpAddr;
	@Value("${cfca.jks.location}")
	private String jksLocation;

	/*@Bean("jksPassword")
	public char[] jksPassword() {
		return _jksPassword.toCharArray();
	}

	@Resource(name = "jksPassword")
	private char[] jksPassword;*/

	@Bean
	public KeyStore keyStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
    	KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
    	InputStream in = null;
		try {
			in = new FileInputStream(jksLocation);
			keyStore.load(in, CFCAConstants.JKS_PASSWORD.toCharArray());
			return keyStore;
		} finally {
			if(in!=null)
				in.close();
		}
	}

	@Autowired
	private KeyStore keyStore;

	@Bean
	public SSLSocketFactory sslSocketFactory() throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, KeyManagementException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, CFCAConstants.JKS_PASSWORD.toCharArray());
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLSv1.1");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        return sslSocketFactory;
	}

	@Bean
	public RSAPrivateKey privateKey() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		return (RSAPrivateKey)keyStore.getKey(CFCAConstants.CERTIFICATION_ALIAS_ANXINSIGN, CFCAConstants.JKS_PASSWORD.toCharArray());
	}

	@Bean("httpPrefix")
	public String httpPrefix() {
		return "https://"+cfcaHttpAddr+"/FEP/platId/"+cfcaPlatId+"/txCode/";
	}
}
