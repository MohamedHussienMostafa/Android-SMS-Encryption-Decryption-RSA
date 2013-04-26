package com.example.androidrsaproject;

import java.security.SecureRandom;



import javax.crypto.Cipher;

import javax.crypto.KeyGenerator;

import javax.crypto.SecretKey;

import javax.crypto.spec.SecretKeySpec;



import android.util.Base64;



 public class EncodeDecodeAES {



	private final static String HEX = "0123456789ABCDEF";

	private final static int JELLY_BEAN_4_2 = 17;

	private final static byte[] key = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };


 	// static {

	// Security.addProvider(new BouncyCastleProvider());

 	// }


 	public static String encrypt(String seed, String cleartext) throws Exception {

		byte[] rawKey = getRawKey(seed.getBytes());

		byte[] result = encrypt(rawKey, cleartext.getBytes());

		String fromHex = toHex(result);

		String base64 = new String(Base64.encodeToString(fromHex.getBytes(), 0));

		return base64;

 	}
 

	public static String decrypt(String seed, String encrypted) throws Exception {

		byte[] seedByte = seed.getBytes();

 		System.arraycopy(seedByte, 0, key, 0, ((seedByte.length < 16) ? seedByte.length : 16));

		String base64 = new String(Base64.decode(encrypted, 0));

 		byte[] rawKey = getRawKey(seedByte);

		byte[] enc = toByte(base64);

 		byte[] result = decrypt(rawKey, enc);

		return new String(result);

}
	public static byte[] encryptBytes(String seed, byte[] cleartext) throws Exception {

		byte[] rawKey = getRawKey(seed.getBytes());

 		byte[] result = encrypt(rawKey, cleartext);

 		return result;

 	}


	public static byte[] decryptBytes(String seed, byte[] encrypted) throws Exception {

		byte[] rawKey = getRawKey(seed.getBytes());
 		byte[] result = decrypt(rawKey, encrypted);

		return result;

 	}


	private static byte[] getRawKey(byte[] seed) throws Exception {

		  		KeyGenerator kgen = KeyGenerator.getInstance("AES"); // , "SC");

				SecureRandom sr = null;

		  		if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN_4_2) {

		  			sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");

		  		} else {

		 			sr = SecureRandom.getInstance("SHA1PRNG");

		  		}

				sr.setSeed(seed);

		  		try {

		 			kgen.init(256, sr);

		 			// kgen.init(128, sr);

		 		} catch (Exception e) {

		  			// Log.w(LOG, "This device doesn't suppor 256bits, trying 192bits.");

		  			try {

		 				kgen.init(192, sr);

		 			} catch (Exception e1) {

						// Log.w(LOG, "This device doesn't suppor 192bits, trying 128bits.");

		  				kgen.init(128, sr);

					}

		 		}

		 		SecretKey skey = kgen.generateKey();

		  		byte[] raw = skey.getEncoded();

			return raw;

		  	}


	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {

		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

 		Cipher cipher = Cipher.getInstance("AES"); // /ECB/PKCS7Padding", "SC");

		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

 		byte[] encrypted = cipher.doFinal(clear);

 		return encrypted;

} 

 	private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {

 		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

 		Cipher cipher = Cipher.getInstance("AES"); // /ECB/PKCS7Padding", "SC");

 		cipher.init(Cipher.DECRYPT_MODE, skeySpec);

 		byte[] decrypted = cipher.doFinal(encrypted);

 		return decrypted;

 	}
 

 	public static String toHex(String txt) {

 		return toHex(txt.getBytes());
 	}

 



 	public static String fromHex(String hex) {

 		return new String(toByte(hex));

 	}

 

 

 	public static byte[] toByte(String hexString) {

 		int len = hexString.length() / 2;

		byte[] result = new byte[len];

		for (int i = 0; i < len; i++)

 			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();

 		return result;

 	}

 

 

 	public static String toHex(byte[] buf) {

 		if (buf == null)

 			return "";

 		StringBuffer result = new StringBuffer(2 * buf.length);

		for (int i = 0; i < buf.length; i++) {

 			appendHex(result, buf[i]);

 		}

 		return result.toString();

 	}

 

 

 	private static void appendHex(StringBuffer sb, byte b) {

 		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));

 	}

 

 }