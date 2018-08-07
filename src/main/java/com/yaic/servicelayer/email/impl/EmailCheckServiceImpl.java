/**
 *
 */
package com.yaic.servicelayer.email.impl;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.yaic.servicelayer.email.EmailCheckService;
import com.yaic.servicelayer.util.RegexUtil;


/**
 * @author Qu Dihuai
 */
@Service(value = "emailCheckService")
public class EmailCheckServiceImpl implements EmailCheckService
{
	private final static Pattern EMAIL = Pattern
			.compile("^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$");

	/*
	 * @see com.yaic.servicelayer.email.EmailService#validateEmail(java.lang.String)
	 */
	@Override
	public boolean validateEmail(final String email)
	{
		int len;
		if (email == null || (len = email.length()) == 0)
		{
			return false;
		}

		if (email.getBytes().length != len)
		{
			return false;
		}

		return RegexUtil.match(EMAIL, email);
	}
}
