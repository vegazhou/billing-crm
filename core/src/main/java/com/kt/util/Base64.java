package com.kt.util;

import org.apache.commons.lang.StringUtils;

public class Base64 {
	private static final String CHAR_PLUS = "+";
	private static final String CHAR_SLASH = "/";
	private static final String CHAR_MINUS = "-";
	private static final String CHAR_UNDERSCORE = "_";
	
	/**
	 * base64 encode in variant of URL and filename safe.
	 * @param data
	 * @return String
	 */
	public static String encodeURL(byte[] data) {
		String result = null;
		String original = encode(data);
		if (original != null) {
			result = StringUtils.replaceChars(original, CHAR_PLUS, CHAR_MINUS);
			result = StringUtils.replaceChars(result, CHAR_SLASH, CHAR_UNDERSCORE);
		}
		return result;
	}
	
	/**
	 * standard encode.
	 * @param data
	 * @return String
	 */
	public static String encode(byte[] data) {
		return Base64.encode(data);
	}
	
	/**
	 * base64 decode in variant of URL and filename safe.
	 * @param str 
	 * @return byte[]
	 */
	public static byte[] decodeURL(String str) {
		byte[] result = null;
		if (str != null) {
			String data = StringUtils.replaceChars(str, CHAR_MINUS, CHAR_PLUS);
			data = StringUtils.replaceChars(data, CHAR_UNDERSCORE, CHAR_SLASH);
			result = decode(data);
		}
		return result;
	}
	
	/**
	 * standard decode.
	 * @param str
	 * @return byte[]
	 */
	public static byte[] decode(String str) {
		return Base64.decode(str);
	}
}
