package com.yaic.servicelayer.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.yaic.servicelayer.charset.StandardCharset;


/**
 * @author Qu Dihuai
 */
public class StringUtil
{
	/**
	 * The empty String <code>""</code>.
	 */
	public static final String EMPTY = "";

	/**
	 * Represents a failed index search.
	 */
	private static final int INDEX_NOT_FOUND = -1;

	/**
	 * Represents the minus sign <code>-</code>.
	 */
	private static final char MINUS_SIGN = '-';

	/**
	 * <p>
	 * Checks if String contains a search String, handling <code>null</code>. This method uses
	 * {@link String#indexOf(String)}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>false</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.contains(null, *)     = false
	 * StringUtil.contains(*, null)     = false
	 * StringUtil.contains("", "")      = true
	 * StringUtil.contains("abc", "")   = true
	 * StringUtil.contains("abc", "a")  = true
	 * StringUtil.contains("abc", "z")  = false
	 * </pre>
	 *
	 * @param str
	 *           the String to check, may be null
	 * @param searchStr
	 *           the String to find, may be null
	 * @return true if the String contains the search String, false if not or <code>null</code> string input
	 */
	public static boolean contains(final String str, final String searchStr)
	{
		if (str == null || searchStr == null)
		{
			return false;
		}
		return str.indexOf(searchStr) >= 0;
	}

	/**
	 * <p>
	 * Checks if the string pool contains target string.
	 * </p>
	 *
	 * <p>
	 * The method returns <code>false</code> if a <code>null</code> string is passed in.
	 * </p>
	 *
	 * @param stringToSplit
	 *           the string pool to search through
	 * @param stringToFind
	 *           the string to find
	 * @param delimiter
	 * @return <code>true</code> if the string pool contains target string
	 */
	public static boolean contains(final String stringToFind, final String stringToSplit, final String delimiter)
	{
		if (stringToFind == null || stringToSplit == null)
		{
			return false;
		}

		if (delimiter == null)
		{
			throw new IllegalArgumentException("delimiter must be not null");
		}

		if (stringToSplit.indexOf(delimiter) < 0)
		{
			return stringToFind.equals(stringToSplit);
		}

		final String[] poolArray = stringToSplit.split(delimiter);
		for (final String str : poolArray)
		{
			if (stringToFind.equals(str))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * <p>
	 * Checks if the string is in the given string.
	 * </p>
	 *
	 * <p>
	 * The method returns <code>false</code> if a <code>null</code> string is passed in.
	 * </p>
	 *
	 * @param array
	 *           the array to search through
	 * @param stringToFind
	 *           the string to find
	 * @return <code>true</code> if the string contains the object
	 */
	public static boolean contains(final String[] array, final String stringToFind)
	{
		return ArrayUtil.contains(array, stringToFind);
	}

	/**
	 * <p>
	 * Checks if a String contains SBC case or not.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.containsSbcCase(null)      = false
	 * StringUtil.containsSbcCase("")        = false
	 * StringUtil.containsSbcCase(" ")       = false
	 * StringUtil.containsSbcCase("全角")     = true
	 * StringUtil.containsSbcCase("ＤＡＯ５３２３２") = true
	 * </pre>
	 *
	 * @param str
	 *           the String to check, may be null
	 * @return <code>true</code> if the String contains SBC case
	 */
	public static boolean containsSbcCase(final String str)
	{
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
		{
			return false;
		}

		if (str.getBytes(StandardCharset.GBK).length != strLen)
		{
			return true;
		}

		return false;
	}

	/**
	 * <p>
	 * Returns either the passed in String, or if the String is empty or <code>null</code>, the value of
	 * <code>defaultStr</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.defaultIfEmpty(null, "NULL")  = "NULL"
	 * StringUtil.defaultIfEmpty("", "NULL")    = "NULL"
	 * StringUtil.defaultIfEmpty("bat", "NULL") = "bat"
	 * StringUtil.defaultIfEmpty("", null)      = null
	 * </pre>
	 *
	 * @param str
	 *           the String to check, may be null
	 * @param defaultStr
	 *           the default String to return if the input is empty ("") or <code>null</code>, may be null
	 * @return the passed in String, or the default
	 */
	public static String defaultIfEmpty(final String str, final String defaultStr)
	{
		return hasLength(str) ? str : defaultStr;
	}

	/**
	 * <p>
	 * Compares two Strings, returning <code>true</code> if they are equal
	 * </p>
	 *
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code> references are considered to be equal.
	 * The comparison is case sensitive.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.equals(null, null)   = true
	 * StringUtil.equals(null, "abc")  = false
	 * StringUtil.equals("abc", null)  = false
	 * StringUtil.equals("abc", "abc") = true
	 * StringUtil.equals("abc", "ABC") = false
	 * </pre>
	 *
	 * @see java.lang.String#equals(Object)
	 * @param str1
	 *           the first String, may be null
	 * @param str2
	 *           the second String, may be null
	 * @return <code>true</code> if the Strings are equal, case sensitive, or both <code>null</code>
	 */
	public static boolean equals(final String str1, final String str2)
	{
		return str1 == null ? str2 == null : str1.equals(str2);
	}

	/**
	 * <p>
	 * Compares two Strings, returning <code>true</code> if they are equal ignoring the case.
	 * </p>
	 *
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code> references are considered equal.
	 * Comparison is case insensitive.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.equalsIgnoreCase(null, null)   = true
	 * StringUtil.equalsIgnoreCase(null, "abc")  = false
	 * StringUtil.equalsIgnoreCase("abc", null)  = false
	 * StringUtil.equalsIgnoreCase("abc", "abc") = true
	 * StringUtil.equalsIgnoreCase("abc", "ABC") = true
	 * </pre>
	 *
	 * @see java.lang.String#equalsIgnoreCase(String)
	 * @param str1
	 *           the first String, may be null
	 * @param str2
	 *           the second String, may be null
	 * @return <code>true</code> if the Strings are equal, case insensitive, or both <code>null</code>
	 */
	public static boolean equalsIgnoreCase(final String str1, final String str2)
	{
		return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
	}

	/**
	 * Check that the given {@code String} is neither {@code null} nor of length 0.
	 * <p>
	 * Note: this method returns {@code true} for a {@code String} that purely consists of whitespace.
	 *
	 * @param str
	 *           the {@code String} to check (may be {@code null})
	 * @return {@code true} if the {@code String} is not {@code null} and has length
	 */
	public static boolean hasLength(final String str)
	{
		return (str != null && str.length() > 0);
	}

	/**
	 * 检测是否是小数
	 * <p>
	 * Checks if the String is decimal point.
	 * </p>
	 *
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String (length()=0) will return <code>true</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.isDecimal(null)   = false
	 * StringUtil.isDecimal("")     = false
	 * StringUtil.isDecimal("  ")   = false
	 * StringUtil.isDecimal("123")  = false
	 * StringUtil.isDecimal("12 3") = false
	 * StringUtil.isDecimal("ab2c") = false
	 * StringUtil.isDecimal("12-3") = false
	 * StringUtil.isDecimal("12.3") = true
	 * </pre>
	 *
	 * @param str
	 *           the String to check, may be null
	 * @return <code>true</code> if contains digits and decimal point, and is non-null
	 */
	public static boolean isDecimal(final String str)
	{
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
		{
			return false;
		}

		final int indexOfPoint = str.indexOf('.');
		if (indexOfPoint == INDEX_NOT_FOUND || indexOfPoint != str.lastIndexOf('.'))
		{
			return false;
		}

		if (indexOfPoint == 0 || indexOfPoint == (strLen - 1))
		{
			return false;
		}

		final boolean startsWithMinusSign = (str.charAt(0) == MINUS_SIGN);
		if (startsWithMinusSign && strLen == 1)
		{
			return false;
		}

		for (int i = (startsWithMinusSign ? 1 : 0); i != indexOfPoint && i < strLen; i++)
		{
			if (!Character.isDigit(str.charAt(i)))
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * <p>
	 * Checks if a String is whitespace, empty ("") or null.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.isEmpty(null)      = true
	 * StringUtil.isEmpty("")        = true
	 * StringUtil.isEmpty(" ")       = true
	 * StringUtil.isEmpty("bob")     = false
	 * StringUtil.isEmpty("  bob  ") = false
	 * </pre>
	 *
	 * @param str
	 *           the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 */
	public static boolean isEmpty(final String str)
	{
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
		{
			return true;
		}
		for (int i = 0; i < strLen; i++)
		{
			if (!Character.isWhitespace(str.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 检测是否是整数
	 *
	 * <p>
	 * Checks if the string is integer as string.
	 *
	 * @param str
	 * @return true if is integer
	 */
	public static boolean isInteger(final String str)
	{
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
		{
			return false;
		}

		for (int i = ((str.charAt(0) == MINUS_SIGN) ? 1 : 0); i < strLen; i++)
		{
			if (!Character.isDigit(str.charAt(i)))
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * <p>
	 * Checks if a String is not empty (""), not null and not whitespace only.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.isNotEmpty(null)      = false
	 * StringUtil.isNotEmpty("")        = false
	 * StringUtil.isNotEmpty(" ")       = false
	 * StringUtil.isNotEmpty("bob")     = true
	 * StringUtil.isNotEmpty("  bob  ") = true
	 * </pre>
	 *
	 * @param str
	 *           the String to check, may be null
	 * @return <code>true</code> if the String is not empty and not null and not whitespace
	 */
	public static boolean isNotEmpty(final String str)
	{
		return !StringUtil.isEmpty(str);
	}

	/**
	 * 检测是否是实数，包含整数、小数、正数、负数、0
	 * <p>
	 * Checks if the String is number, include positive integer, negative integer, zero and floating point.
	 * </p>
	 *
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String (length()=0) will return <code>true</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.isNumber("100") == true
	 * StringUtil.isNumber("100.1") == true
	 * StringUtil.isNumber("-100") == true
	 * StringUtil.isNumber("-100.1") == true
	 * StringUtil.isNumber(null) == false
	 * StringUtil.isNumber("") == false
	 * StringUtil.isNumber("   ") == false
	 * StringUtil.isNumber(".") == false
	 * StringUtil.isNumber("-") == false
	 * StringUtil.isNumber("100  .01") == false
	 * StringUtil.isNumber("-100  .01") == false
	 * StringUtil.isNumber("-100 ") == false
	 * StringUtil.isNumber(".100") == false
	 * StringUtil.isNumber("100.") == false
	 * StringUtil.isNumber("100-") == false
	 * StringUtil.isNumber("-.04") == false
	 * </pre>
	 *
	 * @param str
	 *           the String to check, may be null
	 * @return <code>true</code> if only contains digits, and is non-null
	 */
	public static boolean isNumber(final String str)
	{
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
		{
			return false;
		}

		final boolean startsWithMinusSign = (str.charAt(0) == MINUS_SIGN);
		if (startsWithMinusSign)
		{
			if (strLen == 1) // -是非法的
			{
				return false;
			}
			if (str.charAt(1) == '.') // -.4也是非法的
			{
				return false;
			}
		}

		final int indexOfPoint = str.indexOf('.');
		if (indexOfPoint == INDEX_NOT_FOUND)
		{
			for (int i = (startsWithMinusSign ? 1 : 0); i < strLen; i++)
			{
				if (!Character.isDigit(str.charAt(i)))
				{
					return false;
				}
			}
			return true;
		}
		else
		{
			if (indexOfPoint == 0 || indexOfPoint == (strLen - 1))
			{
				return false;
			}

			if (indexOfPoint != str.lastIndexOf('.'))
			{
				return false;
			}

			for (int i = (startsWithMinusSign ? 1 : 0); i != indexOfPoint && i < strLen; i++)
			{
				if (!Character.isDigit(str.charAt(i)))
				{
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * 检测是否是自然数
	 *
	 * <p>
	 * Checks if the String contains only unicode digits. A decimal point is not a unicode digit and returns false.
	 * </p>
	 *
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String (length()=0) will return <code>true</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.isNumeric(null)   = false
	 * StringUtil.isNumeric("")     = true
	 * StringUtil.isNumeric("  ")   = false
	 * StringUtil.isNumeric("123")  = true
	 * StringUtil.isNumeric("12 3") = false
	 * StringUtil.isNumeric("ab2c") = false
	 * StringUtil.isNumeric("12-3") = false
	 * StringUtil.isNumeric("12.3") = false
	 * </pre>
	 *
	 * @param str
	 *           the String to check, may be null
	 * @return <code>true</code> if only contains digits, and is non-null
	 */
	public static boolean isNumeric(final String str)
	{
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
		{
			return false;
		}

		for (int i = 0; i < strLen; i++)
		{
			if (!Character.isDigit(str.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 检测是否是正整数
	 * <p>
	 * Checks if the string is positive integer as string.
	 *
	 * @param str
	 * @return true if is positive integer
	 */
	public static boolean isPositiveInteger(final String str)
	{
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
		{
			return false;
		}

		if (strLen == 1 && str.charAt(0) == '0')
		{
			return false;
		}

		if (str.charAt(0) == '-')
		{
			return false;
		}

		for (int i = 0; i < strLen; i++)
		{
			if (!Character.isDigit(str.charAt(i)))
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Constructs a new <code>String</code> by decoding the specified array of bytes using the given charset.
	 *
	 * @param bytes
	 *           The bytes to be decoded into characters
	 * @param charset
	 *           The {@link Charset} to encode the {@code String}
	 * @return A new <code>String</code> decoded from the specified array of bytes using the given charset, or
	 *         {@code null} if the input byte array was {@code null}.
	 * @throws NullPointerException
	 *            Thrown if Charsets UTF_8 is not initialized, which should never happen since it is required by the Java
	 *            platform specification.
	 */
	public static String newString(final byte[] bytes, final Charset charset)
	{
		return bytes == null ? null : new String(bytes, charset);
	}

	/**
	 * Constructs a new <code>String</code> by decoding the specified array of bytes using the given charset.
	 * <p>
	 * This method catches {@link UnsupportedEncodingException} and re-throws it as {@link IllegalStateException}, which
	 * should never happen for a required charset name. Use this method when the encoding is required to be in the JRE.
	 * </p>
	 *
	 * @param bytes
	 *           The bytes to be decoded into characters, may be {@code null}
	 * @param charsetName
	 *           The name of a required {@link java.nio.charset.Charset}
	 * @return A new <code>String</code> decoded from the specified array of bytes using the given charset, or
	 *         {@code null} if the input byte array was {@code null}.
	 * @throws IllegalStateException
	 *            Thrown when a {@link UnsupportedEncodingException} is caught, which should never happen for a required
	 *            charset name.
	 * @see String#String(byte[], String)
	 */
	public static String newString(final byte[] bytes, final String charsetName)
	{
		if (bytes == null)
		{
			return null;
		}
		try
		{
			return new String(bytes, charsetName);
		}
		catch (final UnsupportedEncodingException e)
		{
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * Constructs a new <code>String</code> by decoding the specified array of bytes using the UTF-8 charset.
	 *
	 * @param bytes
	 *           The bytes to be decoded into characters
	 * @return A new <code>String</code> decoded from the specified array of bytes using the UTF-8 charset, or
	 *         {@code null} if the input byte array was {@code null}.
	 * @throws NullPointerException
	 *            Thrown if Charsets UTF_8 is not initialized, which should never happen since it is required by the Java
	 *            platform specification.
	 * @since As of 1.7, throws {@link NullPointerException} instead of UnsupportedEncodingException
	 */
	public static String newStringUtf8(final byte[] bytes)
	{
		return newString(bytes, StandardCharset.UTF_8);
	}

	/**
	 * <p>
	 * Checks if String contains a search String, handling <code>null</code>. This method uses
	 * {@link String#indexOf(String)}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>true</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.notContains(null, *)     = true
	 * StringUtil.notContains(*, null)     = true
	 * StringUtil.notContains("", "")      = false
	 * StringUtil.notContains("abc", "")   = false
	 * StringUtil.notContains("abc", "a")  = false
	 * StringUtil.notContains("abc", "z")  = true
	 * </pre>
	 *
	 * @param str
	 *           the String to check, may be null
	 * @param searchStr
	 *           the String to find, may be null
	 * @return true if the String does not contain the search String, true if not or <code>null</code> string input
	 */
	public static boolean notContains(final String str, final String searchStr)
	{
		return !contains(str, searchStr);
	}

	/**
	 * <p>
	 * Checks if the string pool doess not contain target string.
	 * </p>
	 *
	 * <p>
	 * The method returns <code>true</code> if a <code>null</code> string is passed in.
	 * </p>
	 *
	 * @param stringToSplit
	 *           the string pool to search through
	 * @param stringToFind
	 *           the string to find
	 * @param delimiter
	 * @return <code>true</code> if the string pool does not contain target string
	 */
	public static boolean notContains(final String stringToFind, final String stringToSplit, final String delimiter)
	{
		return !contains(stringToFind, stringToSplit, delimiter);
	}

	/**
	 * <p>
	 * Checks if the string is not in the given string.
	 * </p>
	 *
	 * <p>
	 * The method returns <code>false</code> if a <code>null</code> string is passed in.
	 * </p>
	 *
	 * @param array
	 *           the array to search through
	 * @param stringToFind
	 *           the string to find
	 * @return <code>true</code> if the string does not contain the object
	 */
	public static boolean notContains(final String[] array, final String stringToFind)
	{
		return !ArrayUtil.contains(array, stringToFind);
	}

	/**
	 * Replace all occurrences of a substring within a string with another string.
	 *
	 * @param inString
	 *           {@code String} to examine
	 * @param oldPattern
	 *           {@code String} to replace
	 * @param newPattern
	 *           {@code String} to insert
	 * @return a {@code String} with the replacements
	 */
	public static String replace(final String inString, final String oldPattern, final String newPattern)
	{
		if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null)
		{
			return inString;
		}
		final StringBuilder sb = new StringBuilder();
		int pos = 0; // our position in the old string
		int index = inString.indexOf(oldPattern);
		final int patLen = oldPattern.length();
		while (index >= 0)
		{
			sb.append(inString.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sb.append(inString.substring(pos));
		return sb.toString();
	}

	/**
	 * Replace all occurrences of a substring within a string with another string.
	 *
	 * @param inString
	 *           {@code String} to examine
	 * @param oldPattern
	 *           {@code String} to replace
	 * @param newPattern
	 *           {@code String} to insert
	 * @param caseSensitive
	 *           {@code String} to 是否区大小写
	 * @return a {@code String} with the replacements
	 */
	public static String replace(final String inString, final String oldPattern, final String newPattern,
			final boolean caseSensitive)
	{
		if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null)
		{
			return inString;
		}
		int i = 0;
		int j = 0;
		final StringBuilder buffer = new StringBuilder();
		while ((j = indexOf(inString, oldPattern, i, caseSensitive)) > -1)
		{
			buffer.append(inString.substring(i, j));
			buffer.append(newPattern);
			i = j + oldPattern.length();
		}
		buffer.append(inString.substring(i, inString.length()));
		return buffer.toString();
	}

	/**
	 * Split 按照长度分割字符串.
	 *
	 * @param originalString
	 *           原字符串
	 * @param splitByteLength
	 *           按长度分割
	 * @param charsetName
	 *           编码
	 * @return a {@code String[]}
	 */
	public static String[] split(final String originalString, final int splitByteLength, final String charsetName)
	{
		if (!hasLength(originalString) || splitByteLength <= 1)
		{
			return new String[]
			{ originalString };
		}

		final Charset charset = StandardCharset.forName(charsetName);
		if (charset == null)
		{
			throw new IllegalArgumentException("Invalid charset name [" + charsetName + "]");
		}

		final int size = originalString.length();
		final List<String> strList = new ArrayList<>();
		int count = 0;
		int start = 0;
		int len = 0;

		for (int i = 0; i < size; i++)
		{
			len = String.valueOf(originalString.charAt(i)).getBytes(charset).length;
			count += len;
			if (count == splitByteLength)
			{
				strList.add(originalString.substring(start, i + 1));
				count = 0;
				start = i + 1;
			}
			else if (count > splitByteLength)
			{
				strList.add(originalString.substring(start, i));
				count = len;
				start = i;
			}
		}

		if (start < size)
		{
			strList.add(originalString.substring(start));
		}

		return strList.toArray(new String[1]);
	}

	/**
	 * Split a {@code String} at the occurrence of the delimiter. Does not include the delimiter in the result.
	 *
	 * @param stringToSplit
	 *           the string to split
	 * @param delimiter
	 *           to split the string up with
	 * @return a array with index 0 being before the delimiter, and index 1 being after the delimiter (neither element
	 *         includes the delimiter); or {@code null} if the delimiter wasn't found in the given input {@code String}
	 */
	public static String[] split(final String stringToSplit, final String delimiter)
	{
		if (stringToSplit == null)
		{
			return null;
		}

		if (delimiter == null)
		{
			throw new IllegalArgumentException("delimiter must be not null");
		}

		if (stringToSplit.indexOf(delimiter) < 0)
		{
			return new String[]
			{ stringToSplit };
		}

		return stringToSplit.split(delimiter);
	}

	/**
	 * <p>
	 * Gets a substring from the specified String avoiding exceptions.
	 * </p>
	 *
	 * <p>
	 * A negative start position can be used to start/end <code>n</code> characters from the end of the String.
	 * </p>
	 *
	 * <p>
	 * The returned substring starts with the character in the <code>start</code> position and ends before the
	 * <code>end</code> position. All position counting is zero-based -- i.e., to start at the beginning of the string
	 * use <code>start = 0</code>. Negative start and end positions can be used to specify offsets relative to the end of
	 * the String.
	 * </p>
	 *
	 * <p>
	 * If <code>start</code> is not strictly to the left of <code>end</code>, "" is returned.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.substring(null, *, *)    = null
	 * StringUtil.substring("", * ,  *)    = "";
	 * StringUtil.substring("abc", 0, 2)   = "ab"
	 * StringUtil.substring("abc", 2, 0)   = ""
	 * StringUtil.substring("abc", 2, 4)   = "c"
	 * StringUtil.substring("abc", 4, 6)   = ""
	 * StringUtil.substring("abc", 2, 2)   = ""
	 * StringUtil.substring("abc", -2, -1) = "b"
	 * StringUtil.substring("abc", -4, 2)  = "ab"
	 * </pre>
	 *
	 * @param src
	 *           the String to get the substring from, may be null
	 * @param start
	 *           the position to start from, negative means count back from the end of the String by this many characters
	 * @param end
	 *           the position to end at (exclusive), negative means count back from the end of the String by this many
	 *           characters
	 * @return substring from start position to end positon, <code>null</code> if null String input
	 */
	public static String substring(final String src, int start, int end)
	{
		if (src == null)
		{
			return null;
		}

		// handle negatives
		if (end < 0)
		{
			end = src.length() + end; // remember end is negative
		}
		if (start < 0)
		{
			start = src.length() + start; // remember start is negative
		}

		// check length next
		if (end > src.length())
		{
			end = src.length();
		}

		// if start is greater than end, return ""
		if (start > end)
		{
			return EMPTY;
		}

		if (start < 0)
		{
			start = 0;
		}
		if (end < 0)
		{
			end = 0;
		}

		return src.substring(start, end);
	}

	/**
	 * <p>
	 * Gets the String that is nested in between two Strings. Only the first match is returned.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A <code>null</code> open/close returns
	 * <code>null</code> (no match). An empty ("") open and close returns an empty string.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.substring("wx[b]yz", "[", "]") = "b"
	 * StringUtil.substring(null, *, *)          = null
	 * StringUtil.substring(*, null, *)          = null
	 * StringUtil.substring(*, *, null)          = null
	 * StringUtil.substring("", "", "")          = ""
	 * StringUtil.substring("", "", "]")         = null
	 * StringUtil.substring("", "[", "]")        = null
	 * StringUtil.substring("yabcz", "", "")     = ""
	 * StringUtil.substring("yabcz", "y", "z")   = "abc"
	 * StringUtil.substring("yabczyabcz", "y", "z")   = "abc"
	 * </pre>
	 *
	 * @param src
	 *           the String containing the substring, may be null
	 * @param start
	 *           the String before the substring, may be null
	 * @param end
	 *           the String after the substring, may be null
	 * @return the substring, <code>null</code> if no match
	 */
	public static String substringBetween(final String src, final String start, final String end)
	{
		if (src == null || start == null || end == null)
		{
			return null;
		}

		final int indexFrom = src.indexOf(start);
		if (indexFrom != INDEX_NOT_FOUND)
		{
			final int indexTo = src.indexOf(end, indexFrom + start.length());
			if (indexTo != INDEX_NOT_FOUND)
			{
				return src.substring(indexFrom + start.length(), indexTo);
			}
		}

		return null;
	}

	/**
	 * String encoding conversion, transcode encoding type from old charset to new charset
	 *
	 * @param src
	 * @param oldCharset
	 * @param newCharset
	 * @return the string encoded by new charset
	 */
	public static String transcode(final String src, final Charset oldCharset, final Charset newCharset)
	{
		if (src == null)
		{
			return null;
		}

		if (oldCharset == null || newCharset == null)
		{
			throw new IllegalArgumentException("Charset must be not null");
		}

		return newString(src.getBytes(oldCharset), newCharset);
	}

	/**
	 * String encoding conversion, transcode encoding type from oldCharsetName to newCharsetName
	 *
	 * @param src
	 * @param oldCharsetName
	 * @param newCharsetName
	 * @return the string encoded by new charset
	 */
	public static String transcode(final String src, final String oldCharsetName, final String newCharsetName)
	{
		if (src == null)
		{
			return null;
		}

		if (oldCharsetName == null || newCharsetName == null)
		{
			throw new IllegalArgumentException("Charset name must be not null");
		}

		final Charset oldCharset = StandardCharset.forName(oldCharsetName);
		final Charset newCharset = StandardCharset.forName(newCharsetName);

		return newString(src.getBytes(oldCharset), newCharset);
	}

	private static int indexOf(final String str, final String subStr, final int fromIndex, final boolean caseSensitive)
	{
		if (!caseSensitive)
		{
			return str.toLowerCase().indexOf(subStr.toLowerCase(), fromIndex);
		}
		return str.indexOf(subStr, fromIndex);
	}
}
