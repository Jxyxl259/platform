package com.yaic.servicelayer.codec.binary;

import com.yaic.servicelayer.charset.StandardCharset;

/**
 * Base64编码, 基于commons-codec-1.11来实现
 * 
 * @author Qu Dihuai
 */
public class Base64Encoder
{
	
	/**
	 * Encodes binary data using the base64 algorithm
	 *
	 * @param binaryData
	 *           binary data to encode
	 * @return String containing Base64 characters
	 */
	public static String encode(final byte[] binaryData)
	{
		if (binaryData == null || binaryData.length == 0)
		{
			return null;
		}

		final Base64 base64 = new Base64(0, Base64.CHUNK_SEPARATOR, false);
		final long len = base64.getEncodedLength(binaryData);
		if (len > Integer.MAX_VALUE)
		{
			throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + len
					+ ") than the specified maximum size of " + Integer.MAX_VALUE);
		}

		final byte[] bytes = base64.encode(binaryData);
		if (bytes == null)
		{
			return null;
		}

		return new String(bytes, StandardCharset.UTF_8);
	}
}
