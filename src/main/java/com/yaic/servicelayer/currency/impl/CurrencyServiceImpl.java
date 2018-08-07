package com.yaic.servicelayer.currency.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import org.springframework.stereotype.Service;
import com.yaic.servicelayer.currency.CurrencyService;
import com.yaic.servicelayer.util.StringUtil;


/**
 * @author Qu Dihuai
 */
@Service(value = "currencyService")
public class CurrencyServiceImpl implements CurrencyService
{
	/**
	 * Represents the minus sign <code>-</code>.
	 */
	private static final char MINUS_SIGN = '-';

	/**
	 * 特殊字符：零头
	 */
	private static final String CN_ODD = "零";

	/**
	 * 特殊字符：元
	 */
	private static final String CN_YUAN = "元";

	/**
	 * 特殊字符：元整
	 */
	private static final String CN_YUAN_ZHENG = "元整";

	/**
	 * 特殊字符：负
	 */
	private static final String CN_NEGATIVE = "负";

	/**
	 * 人民币大写格式
	 */
	private static final String[] CN_RMB_CAPITAL =
	{ "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	private static final String[] CN_INTEGER_PART_UNIT =
	{ "元", "拾", "佰", "仟", "万", "拾万", "佰万", "仟万", "亿", "拾亿", "佰亿", "仟亿", "兆", "拾兆", "佰兆", "仟兆" };

	private static final String[] CN_DECIMAL_PART_UNIT =
	{ "角", "分" };

	/**
	 * 小数点符号
	 */
	private static final String POINT = ".";
	
	/**
	 * @param sum
	 * @return sum's RMB capital string
	 * @throws IllegalArgumentException
	 *            if sum is not decimal or integer, or scale of decimal part bigger than 2
	 *
	 *            <p>
	 *            该方法的功能为将传入的金额转化为人民币大写, 规范如下:
	 *            </p>
	 *
	 *            <pre>
	 * 一、中文大写金额数字到“元”为止的，在“元”之后、应写“整”(或“正”)字；在“角”之后，可以不写“整”(或“正”)字；大写金额数字有“分”的，“分”后面不写“整”(或“正”)字。
	 *            </pre>
	 *
	 *            <pre>
	 * 二、中文大写金额数字前应标明“人民币”字样，大写金额数字应紧接“人民币”字样填写，不得留有空白。大写金额数字前未印“人民币”字样的，应加填“人民币”三字，在票据和结算凭证大写金额栏内不得预印固定的“仟、佰、拾、万、仟、佰、拾、元、角、分”字样。
	 *            </pre>
	 *
	 *            <pre>
	 * 三、阿拉伯数字小写金额数字中有“0”时，中文大写应按照汉语语言规律、金额数字构成和防止涂改的要求进行书写。举例如下：
	 *            </pre>
	 *
	 *            <pre>
	 * 1、阿拉伯数字中间有“0”时，中文大写要写“零”字，如￥1409.50应写成人民币壹仟肆佰零玖元伍角；
	 *            </pre>
	 *
	 *            <pre>
	 * 2、阿拉伯数字中间连续有几个“0”时、中文大写金额中间可以只写一个“零”字，如￥6007.14应写成人民币陆仟零柒元壹角肆分。
	 *            </pre>
	 *
	 *            <pre>
	 * 3、阿拉伯金额数字万位和元位是“0”，或者数字中间连续有几个“0”，万位、元位也是“0”但千位、角位不是“0”时，中文大写金额中可以只写一个零字，也可以不写“零”字，如￥1680.32应写成人民币壹仟陆佰捌拾元零叁角贰分，或者写成人民币壹仟陆佰捌拾元叁角贰分。又如￥107000.53应写成人民币壹拾万柒仟元零伍角叁分，或者写成人民币壹拾万零柒仟元伍角叁分。
	 *            </pre>
	 *
	 *            <pre>
	 * 4、阿拉伯金额数字角位是“0”而分位不是“0”时，中文大写金额“元”后面应写“零”字，如￥16409.02应写成人民币壹万陆仟肆佰零玖元零贰分，又如￥325.04应写成人民币叁佰贰拾伍元零肆分。
	 *            </pre>
	 */
	@Override
	public String convertToRMBCapital(final String sum)
	{
		if (!StringUtil.isNumber(sum))
		{
			throw new IllegalArgumentException("Invalid argument sum [" + sum + "]");
		}

		final StringBuilder builder = new StringBuilder();
		if (isEqualZero(sum)) // 零元整
		{
			builder.append(CN_RMB_CAPITAL[0]);
			builder.append(CN_YUAN_ZHENG);
			return builder.toString();
		}

		final int indexOfPoint = sum.indexOf('.');

		String sumToUse;
		if (indexOfPoint > 0 && (sum.length() - indexOfPoint > 3)) // 小数点位数大于2位时, 四舍五入为2位
		{
			sumToUse = new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP).toString();
		}
		else
		{
			sumToUse = sum;
		}

		if (startsWithMinusSign(sumToUse))
		{
			sumToUse = sumToUse.substring(1);
			builder.append(CN_NEGATIVE);
		}

		if (indexOfPoint < 0) // 整数
		{
			buildInteger(builder, sumToUse, false);
		}
		else
		{
			final String[] parts = sumToUse.split("\\.");
			buildInteger(builder, parts[0], true);
			buildDecimal(builder, parts[1]);
		}

		return builder.toString();
	}

	@Override
	public String formatMoney(final Double money)
	{
		return formatMoney(money, false);
	}

	@Override
	public String formatMoney(final Double money, final boolean symbol)
	{
		return currencyFormat(money, symbol);
	}

	@Override
	public String formatMoney(final Long money)
	{
		return formatMoney(money, false);
	}

	@Override
	public String formatMoney(final Long money, final boolean symbol)
	{
		return currencyFormat(money, symbol);
	}

	@Override
	public String formatMoney(final Object money)
	{
		return formatMoney(money, false);
	}

	@Override
	public String formatMoney(final Object money, final boolean symbol)
	{

		if (!StringUtil.isDecimal(String.valueOf(money)))
		{
			throw new IllegalArgumentException("Invalid argument money [" + money + "]");
		}

		return currencyFormat(money, symbol);
	}

	@Override
	public String formatMoney(final String money)
	{
		return formatMoney(money, false);
	}

	@Override
	public String formatMoney(final String money, final boolean symbol)
	{

		if (StringUtil.isEmpty(money))
		{
			return "0.00";
		}

		if (!StringUtil.isDecimal(money))
		{
			throw new IllegalArgumentException("Invalid argument money [" + money + "]");
		}

		return currencyFormat(Double.valueOf(money), symbol);
	}

	@Override
	public boolean isValid(final String money)
	{
		if(!StringUtil.isNumber(money)) 
		{
			return false;
		}
		
		int index = money.indexOf(POINT);
		if(index < 0) 
		{
			return true;
		}
		
		if(money.length() - index > 3) // 加上小数点不超过3位
		{
			return false;
		}
		
		return true;
	}

	private void buildDecimal(final StringBuilder builder, final String decimalPart)
	{
		if (decimalPart == null)
		{
			return;
		}

		final char jiao = decimalPart.charAt(0);
		if (decimalPart.length() < 2) // 要么是1位，要么是2位
		{
			if (jiao == '0')
			{
				builder.append(CN_YUAN_ZHENG.charAt(1));
				return;
			}

			builder.append(CN_RMB_CAPITAL[toInt(jiao)]);
			builder.append(CN_DECIMAL_PART_UNIT[0]);
			return;
		}

		final char fen = decimalPart.charAt(1);
		if (jiao == '0')
		{
			if (fen == '0')
			{
				builder.append(CN_YUAN_ZHENG.charAt(1));
			}
			else
			{
				if (!CN_NEGATIVE.equals(builder.toString()))
				{
					builder.append(CN_ODD);
				}
				builder.append(CN_RMB_CAPITAL[toInt(fen)]);
				builder.append(CN_DECIMAL_PART_UNIT[1]);
			}
		}
		else
		{
			if (fen == '0')
			{
				builder.append(CN_RMB_CAPITAL[toInt(jiao)]);
				builder.append(CN_DECIMAL_PART_UNIT[0]);
			}
			else
			{
				builder.append(CN_RMB_CAPITAL[toInt(jiao)]);
				builder.append(CN_DECIMAL_PART_UNIT[0]);
				builder.append(CN_RMB_CAPITAL[toInt(fen)]);
				builder.append(CN_DECIMAL_PART_UNIT[1]);
			}
		}
	}

	private void buildInteger(final StringBuilder builder, final String integerPart, final boolean isDecimal)
	{
		int index;
		int val;

		for (int i = 0, len = integerPart.length(), endIndex = len - 1; i < len; i++)
		{
			val = toInt(integerPart.charAt(i));
			if (i == 0)
			{
				builder.append(CN_RMB_CAPITAL[val]);
				builder.append(CN_INTEGER_PART_UNIT[endIndex - i]);
				continue;
			}

			if (val != 0)
			{
				builder.append(CN_RMB_CAPITAL[val]);
				if (isDecimal)
				{
					builder.append(CN_INTEGER_PART_UNIT[endIndex - i]);
				}
				else
				{
					if (i == len - 1)
					{
						builder.append(CN_YUAN_ZHENG);
					}
					else
					{
						builder.append(CN_INTEGER_PART_UNIT[endIndex - i]);
					}
				}
				continue;
			}

			index = findIndexForValueNotEquals0(integerPart, i, len);
			if (index != -1)
			{
				if (!CN_ODD.equals(builder.substring(builder.length() - 1)))
				{
					builder.append(CN_ODD);
					continue;
				}
			}
			else
			{
				if (isDecimal)
				{
					builder.append(CN_YUAN);
				}
				else
				{
					builder.append(CN_YUAN_ZHENG);
				}
				i = len;
			}
		}
	}

	private String currencyFormat(final Object money, Boolean symbol)
	{
		if (money == null)
		{
			return "0.00";
		}

		if (symbol == null)
		{
			symbol = false;
		}

		final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		final String moneyStr = currencyFormat.format(money);

		if (!symbol)
		{
			final String symbolStr = Currency.getInstance(Locale.getDefault()).getSymbol();
			return moneyStr.replace(symbolStr, "");
		}

		return moneyStr;
	}

	private int findIndexForValueNotEquals0(final String sum, final int beginIndex, final int length)
	{
		for (int j = beginIndex; j < length; j++)
		{
			if (toInt(sum.charAt(j)) != 0)
			{
				return j;
			}
		}
		return -1;
	}

	private boolean isEqualZero(final String sum)
	{
		char ch;
		for (int i = 0, len = sum.length(); i < len; i++)
		{
			ch = sum.charAt(i);
			if (ch != '-' && ch != '.' && ch != '0')
			{
				return false;
			}
		}

		return true;
	}

	private boolean startsWithMinusSign(final String sum)
	{
		return sum.charAt(0) == MINUS_SIGN;
	}

	private int toInt(final char ch)
	{
		return ch - '0';
	}

}
