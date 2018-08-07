package com.yaic.servicelayer.util;

import java.util.regex.Pattern;


/**
 * @author Qu Dihuai
 *         <p>
 *         用于正则表达式的校验
 */
public class RegexUtil
{
	private RegexUtil()
	{
		super();
	}

	/**
	 * @param pattern
	 * @param str
	 * @return boolean
	 */
	public static boolean match(final Pattern pattern, final String str)
	{
		return pattern.matcher(str).matches();
	}
}
