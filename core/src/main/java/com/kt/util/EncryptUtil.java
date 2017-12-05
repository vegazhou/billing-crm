package com.kt.util;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.lang.Exception;
import javax.crypto.SecretKey;

import org.apache.log4j.Logger;


public class EncryptUtil {

	private static Logger logger = Logger.getLogger(EncryptUtil.class.getName());

    public static final int SHA2_LENGTH = 32;
    

	public static byte[] sha2sum(byte[] buffer) throws Exception {
		try {
			MessageDigest sha2 = MessageDigest.getInstance("SHA-256");
			sha2.update(buffer);
			return sha2.digest();
		} catch (NoSuchAlgorithmException e) {
			logger.error("SHA2 Algorithm not available", e);
			throw new Exception("SHA2 Algorithm not available");
		}
	}

	public static byte[] sha2sum(byte[] buffer, int offset, int len)
			throws Exception {
		try {
			MessageDigest sha2 = MessageDigest.getInstance("SHA-256");
			sha2.update(buffer, offset, len);
			return sha2.digest();
		} catch (NoSuchAlgorithmException e) {
			logger.error("SHA2 Algorithm not available", e);
			throw new Exception("SHA2 Algorithm not available");
		}
	}

	public static byte[] encrypt(byte[] plainText, SecretKey skey, byte[] iv)
			throws Exception {
		return encrypt(plainText, 0, plainText.length, skey, iv);
	}

	public static byte[] decrypt(byte[] cipherText, SecretKey skey, byte[] iv)
			throws Exception {
		return decrypt(cipherText, 0, cipherText.length, skey, iv);
	}

	public synchronized static byte[] encrypt(byte[] plainText, int offset, int len,
								 SecretKey skey, byte[] iv) throws Exception {
		try {
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aes.init(Cipher.ENCRYPT_MODE, skey, ivSpec);
			byte[] cipherText = aes.doFinal(plainText, offset, len);
			return cipherText;
		} catch (Exception e) {
			throw new Exception("Error in encryption:", e);
		}
	}

	public static byte[] decrypt(byte[] cipherText, int offset, int len,
								 SecretKey skey, byte[] iv) throws Exception {
		IvParameterSpec ivSpec = null;
		Cipher aes = null;
		try {
			ivSpec = new IvParameterSpec(iv);
			aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] plainText = null;
			aes.init(Cipher.DECRYPT_MODE, skey, ivSpec);
			plainText = aes.doFinal(cipherText, offset, len);
			if (plainText != null) {
				return plainText;
			}
		} catch (Exception e) {
			logger.error("Decryption failed", e);
			throw new Exception("Decryption failed", e);
		}
		throw new Exception("Decryption failed");
	}

} 