package com.soft.web.base;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soft.web.util.AquaDataUtil;

/**
 *  암복호화 핸들러<br>
 *  AES 128 대칭키
 * 
 */
public class Cryptography {

	private static final Logger LOGGER = LoggerFactory.getLogger(Cryptography.class);
	
	/**
	 * 대칭키
	 */
	private Key secureKey;
	
	/**
	 * 생성자
	 * @param aesSeed 대칭키
	 */
	public Cryptography(String aesSeed){
		if (aesSeed.length()!=16) {
			throw new RuntimeException("seed 에러");
		}
		secureKey = new SecretKeySpec(aesSeed.getBytes(), "AES");
	}
	
	/**
	 * 암호화
	 * @param source
	 * @return
	 */
	public String encrypt(String source) {
		try {
			String encString = "";
			if(!AquaDataUtil.booleanCheckNull(source)){
		        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		        cipher.init(Cipher.ENCRYPT_MODE, secureKey);
		        byte[] encrypted = cipher.doFinal(source.getBytes());
		        encString = String.valueOf(Hex.encodeHex(encrypted));
			}
	        return encString;
			
		} catch (Exception e) {
			LOGGER.error("암호화 에러 : {}", e);
			throw new RuntimeException(e);
		}
    }

	/**
	 * 복호화
	 * @param source
	 * @return
	 */
	public String decrypt(String source) {
		try {
			String decString = "";
			if(!AquaDataUtil.booleanCheckNull(source)){			
		        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		        cipher.init(Cipher.DECRYPT_MODE, secureKey);
		        byte[] decrypted = cipher.doFinal(Hex.decodeHex(source.toCharArray()));
		        decString = new String(decrypted);
			}
	        
	        return decString;
		} catch (Exception e) {
			LOGGER.error("복호화 에러 : {}", e);
			throw new RuntimeException(e);
		}

    }
	
 
}
