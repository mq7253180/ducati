package com.hce.cfca.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hce.cfca.CFCAConstants;
import com.hce.cfca.o.CFCAError;
import com.hce.cfca.service.CFCAService;

import cfca.sadk.algorithm.common.Mechanism;
import cfca.sadk.algorithm.common.PKIException;
import cfca.sadk.lib.crypto.JCrypto;
import cfca.sadk.lib.crypto.Session;
import cfca.sadk.util.CertUtil;
import cfca.sadk.util.Signature;
import cfca.sadk.x509.certificate.X509Cert;
import cfca.trustsign.common.util.CommonUtil;
import cfca.trustsign.common.vo.cs.HeadVO;
import cfca.trustsign.common.vo.request.tx3.Tx3203ReqVO;
import cfca.trustsign.common.vo.request.tx3.Tx3ReqVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CFCAServiceImpl implements CFCAService {
	@Autowired
	private SSLSocketFactory sslSocketFactory;
	@Autowired
	private PrivateKey privateKey;
	@Value("${cfca.jks.location}")
	private String jksLocation;
	@Resource(name = "httpPrefix")
	private String httpPrefix;
	private final static String HTTP_SUFFIX = "/transaction";
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String CHANNEL = "Test";
	private static final String PREFIX = "--";
	private static final String LINEND = "\r\n";

	@Override
	public byte[] send(Tx3ReqVO vo, int txCode) throws IOException, MalformedURLException, DocumentException, PKIException {
		return this.send(vo, txCode, null, null, false);
	}

	@Override
	public byte[] signContract(Tx3203ReqVO vo, InputStream contract, String contractFileName) throws IOException, MalformedURLException, DocumentException, PKIException {
		return this.send(vo, 3203, contract, contractFileName, false);
	}

	@Override
	public byte[] signContract(Tx3203ReqVO vo, File contract) throws IOException, MalformedURLException, DocumentException, PKIException {
		InputStream in = new FileInputStream(contract);
		return this.send(vo, 3203, in, contract.getName(), true);
	}

	private byte[] send(Tx3ReqVO vo, int txCode, InputStream contract, String contractFileName, boolean closeStream) throws IOException, MalformedURLException, DocumentException, PKIException {
		HeadVO head = new HeadVO();
        head.setTxTime(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()));
		vo.setHead(head);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		String json = objectMapper.writeValueAsString(vo);
		String signature = this.p7SignMessageDetach(json);
		String url = httpPrefix+txCode+HTTP_SUFFIX;
		HttpsURLConnection sslConn = (HttpsURLConnection)new URL(url).openConnection();
        sslConn.setSSLSocketFactory(sslSocketFactory);
        sslConn.setHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        sslConn.setConnectTimeout(3000);
        sslConn.setReadTimeout(10000);
        sslConn.setDoInput(true);
        sslConn.setDoOutput(true);
        sslConn.setUseCaches(false);
        sslConn.setRequestMethod("POST");
        sslConn.setRequestProperty("User-Agent", "TrustSign FEP");
        sslConn.setRequestProperty("Accept", "application/json");
        sslConn.setRequestProperty("Accept-Charset", "UTF-8");
        String headerConnection = null;
        String headerContentType = null;
        String req = null;
        String random = null;
        if(contract==null) {
        	headerConnection = "close";
        	headerContentType = "application/x-www-form-urlencoded;charset=UTF-8";
        	req = this.prepare(json, signature, null);
        } else {
        	random = java.util.UUID.randomUUID().toString();
        	headerConnection = "keep-alive";
        	headerContentType = "multipart/form-data;boundary="+random;
        	sslConn.setRequestProperty("Charsert", "UTF-8");
        	req = this.prepareAsFormdata(json, signature, random);
        }
        byte[] b = req.getBytes(DEFAULT_CHARSET);
        sslConn.setRequestProperty("Connection", headerConnection);
        sslConn.setRequestProperty("Content-Type", headerContentType);
        if(contract==null)
        	sslConn.setFixedLengthStreamingMode(b.length);
        log.info("CFCA_URL==========================="+url);
		log.info("CFCA_JSON=========================="+json);
		log.info("CFCA_REQ==========================="+req);
        OutputStream out = null;
        InputStream in = null;//读http返回
//        InputStream contractIn = null;//读合同文件
        InputStream domIn = null;//读http返回的html给dom4j解析用
        int responseCode = -1;
        try {
            log.info("ConnectTimeout============"+sslConn.getConnectTimeout());
            log.info("ReadTimeout============"+sslConn.getReadTimeout());
            log.info("DoInput============"+sslConn.getDoInput());
            log.info("DoOutput============"+sslConn.getDoOutput());
            log.info("RequestMethod============"+sslConn.getRequestMethod());
            Map<String, List<String>> map = sslConn.getRequestProperties();
            Iterator<Entry<String, List<String>>> it = map.entrySet().iterator();
            while(it.hasNext()) {
            	Entry<String, List<String>> e = it.next();
            	log.info("KEY================"+e.getKey());
            	List<String> vals = e.getValue();
            	for(String v:vals) {
            		log.info("VALUE----------------"+v);            		
            	}
            }
//            sslConn.connect();
			out = sslConn.getOutputStream();
	        out.write(b);
	        if(contract!=null) {
	        	StringBuilder sb = new StringBuilder(200);
                sb.append(PREFIX);
                sb.append(random);
                sb.append(LINEND);
                sb.append("Content-Disposition:form-data;name=\"contractFile\";filename=\"");
                sb.append(contractFileName);
                sb.append("\"");
                sb.append(LINEND);
                sb.append("Content-Type:application/octet-stream;charset=");
                sb.append(PREFIX);
                sb.append(LINEND);
                sb.append(LINEND);
                out.write(sb.toString().getBytes());
                b = new byte[contract.available()];
                contract.read(b);
                out.write(b);
                out.write(LINEND.getBytes());
                sb = new StringBuilder(100);
                sb.append(PREFIX);
                sb.append(random);
                sb.append(PREFIX);
                sb.append(LINEND);
                out.write(sb.toString().getBytes());
	        }
	        out.flush();
	        responseCode = sslConn.getResponseCode();
	        if(responseCode==200) {
	        	in = sslConn.getInputStream();
	        	b = new byte[in.available()];
	        	in.read(b);
	        	return b;
	        } else {
	        	in = sslConn.getErrorStream();
	        	b = new byte[in.available()];
	        	in.read(b);
	        	String s = new String(b, DEFAULT_CHARSET);
	        	int htmlIndex = s.indexOf("<html ");
	        	String msg = null;
	        	if(htmlIndex>=0) {
	        		s = s.substring(htmlIndex, s.length());
		        	domIn = new ByteArrayInputStream(s.getBytes(DEFAULT_CHARSET));
		        	SAXReader reader = new SAXReader();
		        	Document document = reader.read(domIn);
		        	Element html = document.getRootElement();
		        	Element body = html.element("body");
		        	Element h1 = body.element("h1");
		        	msg = h1.getText();
	        	} else {
	        		CFCAError error = objectMapper.readValue(b, CFCAError.class);
	        		msg = error.getErrorCode()+": "+error.getErrorMessage();
	        	}
	        	throw new RuntimeException(msg);
	        }
		} finally {
			if(out!=null)
				out.close();
			if(in!=null)
				in.close();
			if(closeStream&&contract!=null)
				contract.close();
			if(domIn!=null)
				domIn.close();
			if(sslConn!=null)
				sslConn.disconnect();
		}
	}

	private String p7SignMessageDetach(String sourceData) throws UnsupportedEncodingException, PKIException {
    	JCrypto.getInstance().initialize(JCrypto.JSOFT_LIB, null);
    	Session session = JCrypto.getInstance().openSession(JCrypto.JSOFT_LIB);
        X509Cert signCert = CertUtil.getCertFromJKS(jksLocation, CFCAConstants.JKS_PASSWORD, CFCAConstants.CERTIFICATION_ALIAS_ANXINSIGN);
        Signature signature = new Signature();
        byte[] signatureByte = signature.p7SignMessageDetach(Mechanism.SHA256_RSA, CommonUtil.getBytes(sourceData), privateKey, signCert, session);
        return new String(signatureByte);
    }

	private String prepare(String data, String signature, Map<String, String> map) throws UnsupportedEncodingException {
		StringBuilder request = new StringBuilder();
        request.append("channel").append("=").append(URLEncoder.encode(CHANNEL, DEFAULT_CHARSET));
        if(CommonUtil.isNotEmpty(data)) {
            request.append("&").append("data").append("=").append(URLEncoder.encode(data, DEFAULT_CHARSET));
        }
        if(CommonUtil.isNotEmpty(signature)) {
            request.append("&").append("signature").append("=").append(URLEncoder.encode(signature, DEFAULT_CHARSET));
        }
        if(CommonUtil.isNotEmpty(map)) {
            for (Entry<String, String> pair : map.entrySet()) {
                request.append("&").append(pair.getKey()).append("=").append(pair.getValue()==null?"":URLEncoder.encode(pair.getValue(), DEFAULT_CHARSET));
            }
        }
        return request.toString();
    }

	private String prepareAsFormdata(String data, String signature, String random) {
		StringBuilder sb = new StringBuilder();
        sb.append(PREFIX);
        sb.append(random);
        sb.append(LINEND);
        sb.append("Content-Disposition:form-data;name=\"data\"");
        sb.append(LINEND);
        sb.append(LINEND);
        sb.append(data);
        sb.append(LINEND);
        sb.append(PREFIX);
        sb.append(random);
        sb.append(LINEND);
        sb.append("Content-Disposition: form-data; name=\"signature\"");
        sb.append(LINEND);
        sb.append(LINEND);
        sb.append(signature);
        sb.append(LINEND);
        return sb.toString();
	}
}
