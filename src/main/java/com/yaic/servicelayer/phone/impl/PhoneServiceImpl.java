/**
 *
 */
package com.yaic.servicelayer.phone.impl;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.yaic.servicelayer.phone.PhoneService;
import com.yaic.servicelayer.util.RegexUtil;
import com.yaic.servicelayer.util.StringUtil;


/**
 * @author Qu Dihuai
 */
@Service(value = "phoneService")
public class PhoneServiceImpl implements PhoneService
{
	/**
	 * <p>
	 * 中国的座机号码是有4个部分, 分别是代表中国的区号, 当地的区号, 分隔符及具体的号码。即：+86000-00000000；
	 * <p>
	 * 中国的区号: +86, 86-, (86), (+86)
	 * <p>
	 * 区号：以数字0开始, 并跟随2-3个数字
	 * <p>
	 * 分隔符：以"-"代替, 便于书面的理解
	 * <p>
	 * 具体的号码：大部分地区的号码是7-8位数字组成
	 */
	private final static Pattern TELEPHONE = Pattern
			.compile("(\\(\\+?86\\)|(\\+?86-?))?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?");

	/*
	 * @see com.yaic.servicelayer.phone.PhoneService#validateMobilePhone(java.lang.String)
	 */
	@Override
	public boolean validateMobilePhone(final String phoneNo)
	{
		int len;
		if (phoneNo == null || (len = phoneNo.length()) != 11)
		{
			return false;
		}

		// 第一位必须是1
		if (phoneNo.charAt(0) != '1')
		{
			return false;
		}

		// 第二位不能是0, 1, 2
		if ((phoneNo.charAt(1) - '0') < 3)
		{
			return false;
		}

		// 第三位开始校验是否是数字
		for (int i = 2; i < len; i++)
		{
			if ((Character.isDigit(phoneNo.charAt(i)) == false))
			{
				return false;
			}
		}

		return true;
	}

	/*
	 * @see com.yaic.servicelayer.phone.PhoneService#validateTelePhone(java.lang.String)
	 */
	@Override
	public boolean validateTelePhone(final String phoneNo)
	{
		if (StringUtil.isEmpty(phoneNo))
		{
			return false;
		}

		return RegexUtil.match(TELEPHONE, phoneNo);
	}
}
