package com.yaic.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;


/**
 * Utility class for HTML form decoding. This class contains static methods for decoding a String from the
 * <CODE>application/x-www-form-urlencoded</CODE> MIME format.
 * <p>
 * The conversion process is the reverse of that used by the URLEncoder class. It is assumed that all characters in the
 * encoded string are one of the following: &quot;<code>a</code>&quot; through &quot;<code>z</code>&quot;,
 * &quot;<code>A</code>&quot; through &quot;<code>Z</code>&quot;, &quot;<code>0</code>&quot; through
 * &quot;<code>9</code>&quot;, and &quot;<code>-</code>&quot;, &quot;<code>_</code>&quot;, &quot;<code>.</code>&quot;,
 * and &quot;<code>*</code>&quot;. The character &quot;<code>%</code>&quot; is allowed but is interpreted as the start
 * of a special escaped sequence.
 * <p>
 * The following rules are applied in the conversion:
 * <p>
 * <ul>
 * <li>The alphanumeric characters &quot;<code>a</code>&quot; through &quot;<code>z</code>&quot;,
 * &quot;<code>A</code>&quot; through &quot;<code>Z</code>&quot; and &quot;<code>0</code>&quot; through
 * &quot;<code>9</code>&quot; remain the same.
 * <li>The special characters &quot;<code>.</code>&quot;, &quot;<code>-</code>&quot;, &quot;<code>*</code>&quot;, and
 * &quot;<code>_</code>&quot; remain the same.
 * <li>The plus sign &quot;<code>+</code>&quot; is converted into a space character &quot;<code>&nbsp;</code>&quot; .
 * <li>A sequence of the form "<code>%<i>xy</i></code>" will be treated as representing a byte where <i>xy</i> is the
 * two-digit hexadecimal representation of the 8 bits. Then, all substrings that contain one or more of these byte
 * sequences consecutively will be replaced by the character(s) whose encoding would result in those consecutive bytes.
 * The encoding scheme used to decode these characters may be specified, or if unspecified, the default encoding of the
 * platform will be used.
 * </ul>
 * <p>
 * There are two possible ways in which this decoder could deal with illegal strings. It could either leave illegal
 * characters alone or it could throw an <tt>{@link java.lang.IllegalArgumentException}</tt>. Which approach the decoder
 * takes is left to the implementation.
 */

public class URLDecoder
{

	/**
	 * Decodes a <code>application/x-www-form-urlencoded</code> string using a specific encoding scheme. The supplied
	 * encoding is used to determine what characters are represented by any consecutive sequences of the form
	 * "<code>%<i>xy</i></code>".
	 * <p>
	 * <em><strong>Note:</strong> The <a href= "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars"> World
	 * Wide Web Consortium Recommendation</a> states that UTF-8 should be used. Not doing so may introduce
	 * incompatibilites.</em>
	 *
	 * @param string
	 *           the <code>String</code> to decode
	 * @param encoding
	 *           The name of a supported <a href="../lang/package-summary.html#charenc">character encoding</a>.
	 * @return the newly decoded <code>String</code>
	 * @exception UnsupportedEncodingException
	 *               If character encoding needs to be consulted, but named character encoding is not supported
	 * @see URLEncoder#encode(java.lang.String, java.lang.String)
	 */
	public static String decode(final String string, final String encoding) throws UnsupportedEncodingException
	{
		if (encoding.length() == 0)
		{
			throw new UnsupportedEncodingException("URLDecoder: empty string enc parameter");
		}

		Charset charset;
		try
		{
			charset = Charset.forName(encoding);
		}
		catch (final IllegalCharsetNameException e)
		{
			throw new UnsupportedEncodingException(encoding);
		}
		catch (final UnsupportedCharsetException e)
		{
			throw new UnsupportedEncodingException(encoding);
		}

		return decode(string, charset);
	}

	/**
	 * Decodes a <code>application/x-www-form-urlencoded</code> string using a specific encoding scheme. The supplied
	 * encoding is used to determine what characters are represented by any consecutive sequences of the form
	 * "<code>%<i>xy</i></code>".
	 * <p>
	 * <em><strong>Note:</strong> The <a href= "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars"> World
	 * Wide Web Consortium Recommendation</a> states that UTF-8 should be used. Not doing so may introduce
	 * incompatibilites.</em>
	 *
	 * @param string
	 *           the <code>String</code> to decode
	 * @param charset
	 * @return the newly decoded <code>String</code>
	 */
	public static String decode(final String string, final Charset charset)
	{
		if (charset == null)
		{
			throw new NullPointerException("charset is null");
		}

		boolean needToChange = false;
		final int numChars = string.length();
		final StringBuffer sb = new StringBuffer(numChars > 500 ? numChars / 2 : numChars);

		char c;
		byte[] bytes = null;
		int i = 0;
		while (i < numChars)
		{
			c = string.charAt(i);
			switch (c)
			{
				case '+':
					sb.append(' ');
					i++;
					needToChange = true;
					break;
				case '%':
					/*
					 * Starting with this instance of %, process all consecutive substrings of the form %xy. Each substring
					 * %xy will yield a byte. Convert all consecutive bytes obtained this way to whatever character(s) they
					 * represent in the provided encoding.
					 */
					try
					{
						// (numChars-i)/3 is an upper bound for the number
						// of remaining bytes
						if (bytes == null)
						{
							bytes = new byte[(numChars - i) / 3];
						}
						int pos = 0;

						while (((i + 2) < numChars) && (c == '%'))
						{
							final int v = Integer.parseInt(string.substring(i + 1, i + 3), 16);
							if (v < 0)
							{
								throw new IllegalArgumentException(
										"URLDecoder: Illegal hex characters in escape (%) pattern - negative value");
							}
							bytes[pos++] = (byte) v;
							i += 3;
							if (i < numChars)
							{
								c = string.charAt(i);
							}
						}

						// A trailing, incomplete byte encoding such as
						// "%x" will cause an exception to be thrown
						if ((i < numChars) && (c == '%'))
						{
							throw new IllegalArgumentException("URLDecoder: Incomplete trailing escape (%) pattern");
						}

						sb.append(new String(bytes, 0, pos, charset));
					}
					catch (final NumberFormatException e)
					{
						throw new IllegalArgumentException(
								"URLDecoder: Illegal hex characters in escape (%) pattern - " + e.getMessage());
					}
					needToChange = true;
					break;
				default:
					sb.append(c);
					i++;
					break;
			}
		}

		return (needToChange ? sb.toString() : string);
	}
}