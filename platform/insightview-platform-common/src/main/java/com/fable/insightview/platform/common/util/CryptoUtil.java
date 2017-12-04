package com.fable.insightview.platform.common.util;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Hex;

public class CryptoUtil {
	static byte[] keybytes = { 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38,
			0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38 };
	static byte[] iv = { 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x38,
			0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31 };

	public static String Encrypt(String content) throws Exception {
		BufferedBlockCipher engine = new PaddedBufferedBlockCipher(
				new CBCBlockCipher(new AESFastEngine()));
		engine.init(true, new ParametersWithIV(new KeyParameter(keybytes), iv));
		byte[] enc = new byte[engine.getOutputSize(content.getBytes().length)];
		int size1 = engine.processBytes(content.getBytes(), 0,
				content.getBytes().length, enc, 0);
		int size2 = engine.doFinal(enc, size1);
		// System.out.println("size2 =" + size2);
		byte[] encryptedContent = new byte[size1 + size2];
		System.arraycopy(enc, 0, encryptedContent, 0, encryptedContent.length);
		// System.out.println("Encrypted Content:");
		String key = new String(Hex.encode(encryptedContent));
		// System.out.println(key);
		return key;
	}

	public static String Decrypt(String Key) throws Exception {
		byte[] encryptedContent = hex2byte(Key);
		BufferedBlockCipher engine = new PaddedBufferedBlockCipher(
				new CBCBlockCipher(new AESFastEngine()));

		engine.init(false, new ParametersWithIV(new KeyParameter(keybytes), iv));
		byte[] dec = new byte[engine.getOutputSize(encryptedContent.length)];
		int size1 = engine.processBytes(encryptedContent, 0,
				encryptedContent.length, dec, 0);
		int size2 = engine.doFinal(dec, size1);
		byte[] decryptedContent = new byte[size1 + size2];
		System.arraycopy(dec, 0, decryptedContent, 0, decryptedContent.length);
		String content = new String(new String(decryptedContent));
		return content;
	}

	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
					16);
		}
		return b;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	public static void main(String[] args) {

		String content = "wulin";
		// String content = "KaRant is The Best !!";
		// System.out.println("Original content:");
		System.out.println(content);
		String enc = null;
		try {
			enc = Encrypt(content);
			System.out.println(enc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String se = new String(Decrypt(enc));
			System.out.println(se);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
