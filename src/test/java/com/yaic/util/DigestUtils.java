/**
 * 
 */
package com.yaic.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.yaic.servicelayer.charset.StandardCharset;

/**
 * 
 * <p>
 * User: 何林
 * <p>
 * Date: 2017年11月11日
 * <p>
 * Version: 1.0
 */
public class DigestUtils {
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	@Deprecated
	public static byte[] getBytesUtf8(String string) {
		return getBytes(string, StandardCharset.UTF_8);
	}

	@Deprecated
	private static byte[] getBytes(String string, Charset charset) {
		if (string == null) {
			return null;
		}
		return string.getBytes(charset);
	}

	@Deprecated
	public static byte[] sha1(String data) {
		return sha1(getBytesUtf8(data));
	}

	@Deprecated
	public static String sha1Hex(String data) {
		return encodeHexString(sha1(data));
	}

	@Deprecated
	public static String encodeHexString(byte[] data) {
		return new String(encodeHex(data));
	}

	@Deprecated
	public static char[] encodeHex(byte[] data) {
		return encodeHex(data, true);
	}

	@Deprecated
	public static char[] encodeHex(byte[] data, boolean toLowerCase) {
		return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	@Deprecated
	protected static char[] encodeHex(byte[] data, char[] toDigits) {
		int l = data.length;
		char[] out = new char[l << 1];

		int i = 0;
		for (int j = 0; i < l; i++) {
			out[(j++)] = toDigits[((0xF0 & data[i]) >>> 4)];
			out[(j++)] = toDigits[(0xF & data[i])];
		}
		return out;
	}

	@Deprecated
	public static byte[] sha1(byte[] data) {
		return getSha1Digest().digest(data);
	}

	@Deprecated
	public static MessageDigest getSha1Digest() {
		return getDigest("SHA-1");
	}

	@Deprecated
	public static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
