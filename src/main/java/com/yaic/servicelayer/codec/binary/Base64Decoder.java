package com.yaic.servicelayer.codec.binary;

/**
 * Base64解码, 基于commons-codec-1.11来实现
 *
 * @author Qu Dihuai
 */
public class Base64Decoder
{
	/**
	 * Decodes a Base64 String into octets
	 *
	 * @param base64String
	 *           String containing Base64 data
	 * @return Array containing decoded data.
	 */
	public static byte[] decode(final String base64String)
	{
		return Base64.decodeBase64(base64String);
	}

	/**
	 * Decodes Base64 data into octets
	 *
	 * @param base64Data
	 *           Byte array containing Base64 data
	 * @return Array containing decoded data.
	 */
	public static byte[] decode(final byte[] base64Data)
	{
		return Base64.decodeBase64(base64Data);
	}
}
