/**
 *
 */
package com.yaic.servicelayer.idcard.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.yaic.servicelayer.enums.IdProvinceEnum;
import com.yaic.servicelayer.enums.MonthEnum;
import com.yaic.servicelayer.idcard.IdcardNoService;
import com.yaic.servicelayer.util.TimeUtil;


/**
 * @author Qu Dihuai
 */
@Service(value = "idcardNoService")
public class IdcardNoServiceImpl implements IdcardNoService
{
	private static final int[] WEIGHT =
	{ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	private static final char[] CHECK_CODES =
	{ '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };

	private static final String FEMALE = "02"; // 女性
	private static final String MALE = "01"; // 男性

	private static final int caseDiff = ('a' - 'A');

	@Override
	public boolean validateIdcardNo(final String idcardNo)
	{
		if (idcardNo == null)
		{
			return false;
		}

		final int len = idcardNo.length();
		if (len == 18)
		{
			// 判断最后一位
			final char lastChar = idcardNo.charAt(17);
			if (!Character.isDigit(lastChar) && (lastChar != 'X') && (lastChar != 'x'))
			{
				return false;
			}

			// 判断前17位
			if (!isDigit(idcardNo, 17))
			{
				return false;
			}
			return validate(idcardNo, true);
		}

		if (len == 15)
		{
			if (!isDigit(idcardNo, len))
			{
				return false;
			}
			return validate(convert(idcardNo), false);
		}

		return false;
	}

	@Override
	public String calculateCheckCode(final String str)
	{
		if (str == null)
		{
			return null;
		}

		if (str.length() == 18)
		{
			return String.valueOf(calcCheckCode(str.substring(0, 17)));
		}

		throw new IllegalArgumentException("Length of ID card number must be 18");
	}

	@Override
	public String convert15To18(final String idcardNo)
	{
		if (idcardNo == null)
		{
			return null;
		}

		final int len = idcardNo.length();
		if (len == 15)
		{
			if (!isDigit(idcardNo, 15))
			{
				throw new IllegalArgumentException("Invalid ID card number");
			}
			return convert(idcardNo);
		}

		if (len == 18)
		{
			return idcardNo;
		}

		throw new IllegalArgumentException("Invalid ID card number");
	}

	@Override
	public String getAddressCode(final String idcardNo)
	{
		if (idcardNo == null)
		{
			return null;
		}

		final int len = idcardNo.length();
		if (len != 18 && len != 15)
		{
			throw new IllegalArgumentException("Invalid ID card number " + idcardNo);
		}

		return idcardNo.substring(0, 6);
	}

	@Override
	public Date getBirthDate(final String idcardNo)
	{
		if (idcardNo == null)
		{
			return null;
		}

		final int length = idcardNo.length();
		if (length == 18)
		{
			return TimeUtil.parseDate(idcardNo.substring(6, 14), TimeUtil.DATE_FORMAT);
		}

		if (length == 15)
		{
			return TimeUtil.parseDate(convert(idcardNo).substring(6, 14), TimeUtil.DATE_FORMAT);
		}

		throw new IllegalArgumentException("Invalid ID card number " + idcardNo);
	}

	@Override
	public String getBirthDateCode(final String idcardNo)
	{
		if (idcardNo == null)
		{
			return null;
		}

		final int length = idcardNo.length();
		if (length == 18)
		{
			return idcardNo.substring(6, 14);
		}

		if (length == 15)
		{
			return convert(idcardNo).substring(6, 14);
		}

		throw new IllegalArgumentException("Invalid ID card number " + idcardNo);
	}

	@Override
	public int getDayOfBirthdate(final String idcardNo)
	{
		if (idcardNo != null)
		{
			final int len = idcardNo.length();
			if (len == 18)
			{
				return Integer.parseInt(idcardNo.substring(12, 14));
			}
			if (len == 15)
			{
				return Integer.parseInt(idcardNo.substring(10, 12));
			}
		}

		throw new IllegalArgumentException("Invalid ID card number " + idcardNo);
	}

	@Override
	public int getMonthOfBirthdate(final String idcardNo)
	{
		if (idcardNo != null)
		{
			final int len = idcardNo.length();
			if (len == 18)
			{
				return Integer.parseInt(idcardNo.substring(10, 12));
			}
			if (len == 15)
			{
				return Integer.parseInt(idcardNo.substring(8, 10));
			}
		}
		throw new IllegalArgumentException("Invalid ID card number " + idcardNo);
	}

	@Override
	public int getBirthYear(final String idcardNo)
	{
		if (idcardNo != null)
		{
			final int len = idcardNo.length();
			if (len == 18)
			{
				return Integer.parseInt(idcardNo.substring(6, 10));
			}
			if (len == 15)
			{
				return Integer.parseInt(convert(idcardNo).substring(6, 10));
			}
		}
		throw new IllegalArgumentException("Invalid ID card number " + idcardNo);
	}

	@Override
	public String getCheckCode(final String idcardNo)
	{
		if (idcardNo == null || idcardNo.length() != 18)
		{
			throw new IllegalArgumentException("Invalid ID card number " + idcardNo);
		}
		return idcardNo.substring(17);
	}

	@Override
	public int getProvinceCode(final String idcardNo)
	{
		if (idcardNo == null)
		{
			throw new IllegalArgumentException("ID card number must be not null");
		}

		final int len = idcardNo.length();
		if (len != 18 && len != 15)
		{
			throw new IllegalArgumentException("Invalid ID card number!");
		}

		return Integer.parseInt(idcardNo.substring(0, 2));
	}

	@Override
	public String getSequenceCode(final String idcardNo)
	{
		if (idcardNo != null)
		{
			final int len = idcardNo.length();
			if (len == 18)
			{
				return idcardNo.substring(14, 17);
			}
			if (len == 15)
			{
				return idcardNo.substring(12, 15);
			}
		}
		throw new IllegalArgumentException("Invalid ID card number " + idcardNo);
	}

	@Override
	public String getSex(final String idcardNo)
	{
		if (idcardNo == null)
		{
			return null;
		}

		final int len = idcardNo.length();
		if (len != 18 && len != 15)
		{
			throw new IllegalArgumentException("Invalid ID card number " + idcardNo);
		}

		String sexStr;
		if (len == 18)
		{
			sexStr = idcardNo.substring(16, 17);
		}
		else
		{
			sexStr = idcardNo.substring(14, 15);
		}

		final int sexCode = Integer.parseInt(sexStr);
		if ((sexCode & 1) != 0)
		{
			return MALE;
		}
		return FEMALE;
	}

	private boolean validate(final String idcardNo, final boolean checkLastCharEnabled)
	{
		final int proviceCode = Integer.parseInt(idcardNo.substring(0, 2));
		if (IdProvinceEnum.fromCode(proviceCode) == null)
		{
			return false;
		}

		final int birthYear = Integer.parseInt(idcardNo.substring(6, 10));
		if (birthYear > TimeUtil.getCurrentYear())
		{
			return false;
		}

		final int birthMonth = Integer.parseInt(idcardNo.substring(10, 12));
		if (birthMonth > 12)
		{
			return false;
		}

		final int birthDay = Integer.parseInt(idcardNo.substring(12, 14));
		if (birthDay > 31)
		{
			return false;
		}

		final MonthEnum monthEnum = MonthEnum.fromMonth(birthMonth);
		if (monthEnum == MonthEnum.FEBRUARY)
		{
			if (TimeUtil.isLeapYear(birthYear))
			{
				if (birthDay > 29)
				{
					return false;
				}
			}
			else
			{
				if (birthDay > 28)
				{
					return false;
				}
			}
		}
		else
		{
			if (birthDay > monthEnum.maxDays())
			{
				return false;
			}
		}

		final Date birthDate = TimeUtil.parseDate(idcardNo.substring(6, 14), TimeUtil.DATE_FORMAT);
		if (birthDate.getTime() > System.currentTimeMillis())
		{
			return false;
		}

		if (checkLastCharEnabled)
		{
			final char specifiedCheckCode = idcardNo.charAt(17);
			final char calculatedCheckCode = calcCheckCode(idcardNo.substring(0, 17));
			if ((specifiedCheckCode != calculatedCheckCode) && (specifiedCheckCode != calculatedCheckCode + caseDiff))
			{
				return false;
			}
		}

		return true;
	}

	private char calcCheckCode(final CharSequence charSequence)
	{
		int sum = 0;
		for (int i = 0, len = charSequence.length(); i < len; i++)
		{
			sum += (charSequence.charAt(i) - '0') * WEIGHT[i];
		}
		return CHECK_CODES[sum % 11];
	}

	/**
	 * 将长度为15位的第一代身份证号码号码转化为18位的核心算法。本算法存在缺陷，对于1913年1月1日前出生的百岁老人, 出生年份会换算错误。
	 */
	private String convert(final String idCardNo15)
	{
		final StringBuilder builder = new StringBuilder(18);
		for (int i = 0, len = idCardNo15.length(); i < len; i++)
		{
			if (i == 6)
			{
				// 自2013年1月1日起, 第一代身份证停止使用。理论上讲, 2013年出生的人, 不会再有15位的身份证号。
				if (Integer.parseInt(idCardNo15.substring(6, 8)) > 12)
				{
					builder.append("19");
				}
				else
				{
					builder.append("20");
				}
			}
			builder.append(idCardNo15.charAt(i));
		}
		return builder.append(calcCheckCode(builder)).toString();
	}

	/**
	 * @param string
	 * @param len
	 * @return if all chars are digit, return true
	 */
	private boolean isDigit(final String string, final int len)
	{
		for (int i = 0; i < len; i++)
		{
			if (!Character.isDigit(string.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}
}
