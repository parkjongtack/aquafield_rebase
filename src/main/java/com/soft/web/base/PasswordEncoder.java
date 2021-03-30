package com.soft.web.base;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordEncoder {

	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordEncoder.class);

	/**
	 * SALT 문자열
	 */
	//private static final String SALT_KEY = "shinsegaeAquafield";
	
	public String encode(String rawPassword) {
		//String saltedPassword = SALT_KEY + rawPassword.toString();
		
		return getSha256Encode(rawPassword);
	}

	public boolean matches(String rawPassword, String encodedPassword) {
		return encode(rawPassword).equals(encodedPassword);
	}
	
	/**
	 * <p>SHA-256 인코딩</p>
	 * 
	 * @param str 평문문
	 * @return 인코딩된 문자열 
	 */
	private String getSha256Encode(String str) {

		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte byteData[] = sh.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("SHA-256 암호화 오류", e);
			throw new RuntimeException("SHA-256 암호화 오류");
		}

	}
}
