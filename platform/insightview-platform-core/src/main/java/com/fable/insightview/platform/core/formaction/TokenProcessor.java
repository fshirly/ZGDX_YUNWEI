package com.fable.insightview.platform.core.formaction;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

/**
 * 令牌工具类
 * @author xue.antai
 *
 */
public class TokenProcessor {
	private static TokenProcessor tokenProcessor;
	
	public static TokenProcessor newInstance() {
		if(null == tokenProcessor) {
			tokenProcessor = new TokenProcessor();
		}
		return tokenProcessor;
	}
	
	public String generateToken() {
		String token = String.valueOf(System.currentTimeMillis() + new Random().nextInt(999999999));
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] md5Arr = md.digest(token.getBytes());
		Base64 encoder = new Base64();
		token = String.valueOf(encoder.encode(md5Arr));
		return token;
	}
}
