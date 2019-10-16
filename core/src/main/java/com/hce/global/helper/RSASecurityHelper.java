package com.hce.global.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSASecurityHelper {
	private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    public static final String PUBLIC_KEY_BASE64 = "RSAPublicKey_Base64";
    public static final String PRIVATE_KEY_BASE64 = "RSAPrivateKey_Base64";
	public static final String KEY_ALGORITHM = "RSA";
//    public static final String SIGNATURE_ALGORITHMS = "MD5withRSA";
//    public static final String SIGNATURE_ALGORITHMS = "SHA1WithRSA";
    private final static int MAX_ENCRYPT_BLOCK = 117;
    private final static int MAX_DECRYPT_BLOCK = 128;

    //map对象中存放公私钥
    public static Map<String, Object> generateKeyPair() throws NoSuchAlgorithmException {
        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024, new SecureRandom());
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();
        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象存入map中
        Encoder base64Encoder = Base64.getEncoder();
        Map<String, Object> keyMap = new HashMap<String, Object>(8);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        keyMap.put(PUBLIC_KEY_BASE64, base64Encoder.encode(publicKey.getEncoded()));
        keyMap.put(PRIVATE_KEY_BASE64, base64Encoder.encode(privateKey.getEncoded()));
        return keyMap;
    }

	public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		Decoder base64Decoder = Base64.getDecoder();
		byte[] buffer = base64Decoder.decode(publicKeyStr);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
		return (RSAPublicKey) keyFactory.generatePublic(keySpec);
	}

	public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		Decoder base64Decoder = Base64.getDecoder();
		byte[] buffer = base64Decoder.decode(privateKeyStr);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
	}

    public static byte[] crypt(Key key, byte[] plainTextData, int opmode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {  
		int MAX_CRYPT_BLOCK = -1;
		switch(opmode) {
			case Cipher.ENCRYPT_MODE: MAX_CRYPT_BLOCK = MAX_ENCRYPT_BLOCK;break;
			case Cipher.DECRYPT_MODE: MAX_CRYPT_BLOCK = MAX_DECRYPT_BLOCK;break;
			default:;
		}
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(opmode, key);
		int loops = (plainTextData.length/MAX_CRYPT_BLOCK);
		if(plainTextData.length%MAX_CRYPT_BLOCK>0)
			loops++;
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			for(int i=0;i<loops;i++) {
				int start = i*MAX_CRYPT_BLOCK;
				int remaining = plainTextData.length-start;
				int offset = remaining>MAX_CRYPT_BLOCK?MAX_CRYPT_BLOCK:remaining;
				out.write(cipher.doFinal(plainTextData, start, offset));
			}
			return out.toByteArray();
		} finally {
			if(out!=null)
				out.close();
		}
	}

	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String byteArrayToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
    	for(int i=0;i<data.length;i++) {
			// 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移 
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
			// 取出字节的低四位 作为索引得到相应的十六进制标识符 
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if(i<data.length-1) {
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString(); 
	}

	public static String sign(String privateKey, String algorithms, String charset, String content) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, IOException {
		charset = CommonHelper.trim(charset);
		return sign(privateKey, algorithms, content.getBytes(charset==null?"UTF-8":charset.trim()));
	}

	public static String sign(PrivateKey privateKey, String algorithms, String charset, String content) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, IOException {
		charset = CommonHelper.trim(charset);
		return sign(privateKey, algorithms, content.getBytes(charset==null?"UTF-8":charset.trim()));
	}

	public static String sign(String privateKey, String algorithms, byte[] content) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, IOException {
		Decoder base64Decoder = Base64.getDecoder();
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(base64Decoder.decode(privateKey));
		KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey priKey = keyf.generatePrivate(priPKCS8);
		return sign(priKey, algorithms, content);
	}

	public static String sign(PrivateKey privateKey, String algorithms, byte[] content) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, IOException {
		Encoder base64Encoder = Base64.getEncoder();
		Signature signature = Signature.getInstance(algorithms);
		signature.initSign(privateKey);
		signature.update(content);
		byte[] signed = signature.sign();
		return new String(base64Encoder.encode(signed));
	}

	public static boolean verify(String publicKey, String algorithms, String signatureStr, String content, String encode) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, IOException {
		Decoder base64Decoder = Base64.getDecoder();
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		byte[] encodedKey = base64Decoder.decode(publicKey);
		PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
		Signature signature = Signature.getInstance(algorithms);
		signature.initVerify(pubKey);
		signature.update(content.getBytes(encode==null||encode.trim().length()==0?"UTF-8":encode.trim()));
		boolean bverify = signature.verify(base64Decoder.decode(signatureStr));
		return bverify;
	}

	public static void main(String[] args) {
		Map<String, Object> keyMap;
        try {
        		String content = "ABCDEFGHIGKLMNOPQRSTUVWXYZ";
        		for(int i=0;i<3;i++) {
        			content += content;
        		}
            keyMap = generateKeyPair();
            String publicKey = keyMap.get(PUBLIC_KEY_BASE64).toString().replaceAll("\r\n", "");
            String privateKey = keyMap.get(PRIVATE_KEY_BASE64).toString().replaceAll("\r\n", "");
            String signature = sign(privateKey, "SHA1WithRSA", "UTF-8", content);
            byte[] encryption = crypt(loadPrivateKeyByStr(privateKey), content.getBytes(), Cipher.ENCRYPT_MODE);
            String decryption = new String(crypt(loadPublicKeyByStr(publicKey), encryption, Cipher.DECRYPT_MODE));

            System.out.println("Content: "+content+"\r\nSignature: "+signature);
            System.out.println("-----------------");
            System.out.println(verify(publicKey, "SHA1WithRSA", signature, content, "UTF-8"));
            System.out.println("-----------------");
            System.out.println("Encryption: "+new String(encryption));
            System.out.println("-----------------");
            System.out.println("Decryption: "+decryption);
            System.out.println("*****************");

            System.out.println(keyMap.get(PUBLIC_KEY));
            System.out.println("-----------------");
            System.out.println(keyMap.get(PRIVATE_KEY));
            System.out.println("==========================");
            System.out.println(publicKey.length()+": "+publicKey);
            System.out.println("-----------------");
            System.out.println(privateKey.length()+": "+privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}