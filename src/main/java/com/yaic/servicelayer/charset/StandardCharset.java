package com.yaic.servicelayer.charset;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * 通用标准字符集
 *
 * @author Qu Dihuai
 *
 */
public final class StandardCharset
{
	private StandardCharset()
	{
		throw new AssertionError("No com.yaic.service.charset.StandardCharset instances for you!");
	}

	/**
	 * Eight-bit UCS Transformation Format
	 */
	public static final Charset UTF_8 = Charset.forName("UTF-8");
	/**
	 * Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the Unicode character set
	 */
	public static final Charset US_ASCII = Charset.forName("US-ASCII");
	/**
	 * ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1
	 */
	public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
	/**
	 * Sixteen-bit UCS Transformation Format, big-endian byte order
	 */
	public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
	/**
	 * Sixteen-bit UCS Transformation Format, little-endian byte order
	 */
	public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
	/**
	 * Sixteen-bit UCS Transformation Format, byte order identified by an optional byte-order mark
	 */
	public static final Charset UTF_16 = Charset.forName("UTF-16");

	/**
	 * 1981年5月1日发布的简体中文汉字编码国家标准。GB2312对汉字采用双字节编码，收录7445个图形字符，其中包括6763个汉字
	 */
	public static final Charset GB2312 = Charset.forName("GB2312");

	/**
	 * 台湾地区繁体中文标准字符集，采用双字节编码，共收录13053个中文字，1984年实施
	 */
	public static final Charset BIG5 = Charset.forName("BIG5");

	/**
	 * 1995年12月发布的汉字编码国家标准，是对GB2312编码的扩充，对汉字采用双字节编码。GBK字符集共收录21003个汉字，包含国家标准GB13000-1中的全部中日韩汉字，和BIG5编码中的所有汉字。
	 */
	public static final Charset GBK = Charset.forName("GBK");

	/**
	 * 2000年3月17日发布的汉字编码国家标准，是对GBK编码的扩充，覆盖中文、日文、朝鲜语和中国少数民族文字，其中收录27484个汉字。GB18030字符集采用单字节、双字节和四字节三种方式对字符编码。兼容GBK和GB2312字符集
	 */
	public static final Charset GB18030 = Charset.forName("GB18030");

	private static Map<String, Charset> CHARSETS;
	static
	{
		final Charset[] array =
		{ UTF_8, US_ASCII, ISO_8859_1, UTF_16BE, UTF_16LE, UTF_16, GB2312, GBK, BIG5, GB18030 };

		final Map<String, Charset> map = new HashMap<>(array.length);
		for (final Charset charset : array)
		{
			map.put(charset.name(), charset);
		}

		CHARSETS = Collections.unmodifiableMap(map);
	}

	/**
	 * @param charsetName
	 * @return Charset name of Charset is the gived name
	 */
	public static Charset forName(final String charsetName)
	{
		final String upperCaseCharsetName = toUpperCase(charsetName);
		final Charset charset = CHARSETS.get(upperCaseCharsetName);

		if (charset != null)
		{
			return charset;
		}

		return Charset.forName(upperCaseCharsetName);
	}

	/**
	 * Converts all of the latin letter in this <code>String</code> to upper case
	 * 
	 * @param source
	 * @return the <code>String</code>, converted to uppercase.
	 */
	private static String toUpperCase(final String source)
	{
		if (source == null)
		{
			return null;
		}

		final int len = source.length();
		final StringBuilder builder = new StringBuilder(len);

		char character;
		for (int i = 0; i < len; i++)
		{
			character = source.charAt(i);
			if (character >= 'a' && character <= 'z')
			{
				builder.append(character -= 32);
			}
			else
			{
				builder.append(character);
			}
		}

		return builder.toString();
	}
}
