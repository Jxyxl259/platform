package com.yaic.servicelayer.util;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * @author Qu Dihuai
 *         <p>
 *         利用BigDecimal实现小数的精确计算
 */
public final class DecimalUtil
{
	private final static int DEFAULT_SCALE = 0x2; //保留小数位

	private final static RoundingMode DEFAULT_MODE = RoundingMode.HALF_UP;

	private DecimalUtil()
	{
		super();
	}

	/**
	 * @param number
	 * @return BigDecimal
	 */
	public static BigDecimal toBigDecimal(final double number)
	{
		return BigDecimal.valueOf(number);
	}

	/**
	 * @param number
	 * @return BigDecimal
	 */
	public static BigDecimal toBigDecimal(final String number)
	{
		if (StringUtil.isEmpty(number))
		{
			throw new IllegalArgumentException("parameter is illegal");
		}
		return new BigDecimal(number);
	}

	/**
	 * @param number
	 * @param scale
	 * @param roundingMode
	 * @return BigDecimal
	 */
	public static double round(final double number, final int scale, final RoundingMode roundingMode)
	{
		return toBigDecimal(number).setScale(scale, roundingMode).doubleValue();
	}

	/**
	 * @param number
	 * @param scale
	 * @param roundingMode
	 * @return BigDecimal
	 */
	public static double round(final String number, final int scale, final RoundingMode roundingMode)
	{
		if (StringUtil.isEmpty(number))
		{
			throw new IllegalArgumentException("parameter is illegal");
		}
		return toBigDecimal(number).setScale(scale, roundingMode).doubleValue();
	}

	/**
	 * @param augend
	 * @param addend
	 * @return sum 默认四舍五入, 保留2位小数
	 */
	public static double plus(final double augend, final double addend)
	{
		final BigDecimal sum = toBigDecimal(augend).add(toBigDecimal(addend));
		return sum.setScale(DEFAULT_SCALE, DEFAULT_MODE).doubleValue();
	}

	/**
	 * @param augend
	 * @param addend
	 * @param scale
	 * @param roundingMode
	 * @return sum
	 */
	public static double plus(final double augend, final double addend, final int scale, final RoundingMode roundingMode)
	{
		if (scale < 0)
		{
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		final BigDecimal sum = toBigDecimal(augend).add(toBigDecimal(addend));
		if (roundingMode == null)
		{
			return sum.setScale(scale, DEFAULT_MODE).doubleValue();
		}
		return sum.setScale(scale, roundingMode).doubleValue();
	}

	/**
	 * @param augend
	 * @param addend
	 * @return sum 默认四舍五入, 保留2位小数
	 */
	public static double plus(final String augend, final String addend)
	{
		final BigDecimal sum = toBigDecimal(augend).add(toBigDecimal(addend));
		return sum.setScale(DEFAULT_SCALE, DEFAULT_MODE).doubleValue();
	}

	/**
	 * @param augend
	 * @param addend
	 * @param scale
	 * @param roundingMode
	 * @return sum
	 */
	public static double plus(final String augend, final String addend, final int scale, final RoundingMode roundingMode)
	{
		if (scale < 0)
		{
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		final BigDecimal sum = toBigDecimal(augend).add(toBigDecimal(addend));
		if (roundingMode == null)
		{
			return sum.setScale(scale, DEFAULT_MODE).doubleValue();
		}
		return sum.setScale(scale, roundingMode).doubleValue();
	}

	/**
	 * @param minuend
	 * @param subtrahend
	 * @return diff 默认四舍五入，保留2位小数
	 */
	public static double subtract(final double minuend, final double subtrahend)
	{
		final BigDecimal diff = toBigDecimal(minuend).subtract(toBigDecimal(minuend));
		return diff.setScale(DEFAULT_SCALE, DEFAULT_MODE).doubleValue();
	}

	/**
	 * @param minuend
	 * @param subtrahend
	 * @param scale
	 * @param roundingMode
	 * @return diff
	 */
	public static double subtract(final double minuend, final double subtrahend, final int scale, final RoundingMode roundingMode)
	{
		if (scale < 0)
		{
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		final BigDecimal diff = toBigDecimal(minuend).subtract(toBigDecimal(minuend));
		if (roundingMode == null)
		{
			return diff.setScale(scale, DEFAULT_MODE).doubleValue();
		}
		return diff.setScale(scale, roundingMode).doubleValue();
	}

	/**
	 * @param minuend
	 * @param subtrahend
	 * @return diff 默认四舍五入，保留2位小数
	 */
	public static double subtract(final String minuend, final String subtrahend)
	{
		final BigDecimal diff = toBigDecimal(minuend).subtract(toBigDecimal(minuend));
		return diff.setScale(DEFAULT_SCALE, DEFAULT_MODE).doubleValue();
	}

	/**
	 * @param minuend
	 * @param subtrahend
	 * @param scale
	 * @param roundingMode
	 * @return diff
	 */
	public static double subtract(final String minuend, final String subtrahend, final int scale, final RoundingMode roundingMode)
	{
		if (scale < 0)
		{
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		final BigDecimal diff = toBigDecimal(minuend).subtract(toBigDecimal(minuend));
		if (roundingMode == null)
		{
			return diff.setScale(scale, DEFAULT_MODE).doubleValue();
		}
		return diff.setScale(scale, roundingMode).doubleValue();
	}

	/**
	 * @param multiplicand
	 * @param multiplier
	 * @return product 默认四舍五入, 保留2位小数
	 */
	public static double multiply(final double multiplicand, final double multiplier)
	{
		final BigDecimal product = toBigDecimal(multiplicand).multiply(toBigDecimal(multiplier));
		return product.setScale(DEFAULT_SCALE, DEFAULT_MODE).doubleValue();
	}

	/**
	 * @param multiplicand
	 * @param multiplier
	 * @param scale
	 * @param roundingMode
	 * @return product
	 */
	public static double multiply(final double multiplicand, final double multiplier, final int scale,
			final RoundingMode roundingMode)
	{
		if (scale < 0)
		{
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		final BigDecimal product = toBigDecimal(multiplicand).multiply(toBigDecimal(multiplicand));
		if (roundingMode == null)
		{
			return product.setScale(scale, DEFAULT_MODE).doubleValue();
		}
		return product.setScale(scale, roundingMode).doubleValue();
	}

	/**
	 * @param multiplicand
	 * @param multiplier
	 * @return product 默认四舍五入, 保留2位小数
	 */
	public static double multiply(final String multiplicand, final String multiplier)
	{
		final BigDecimal product = toBigDecimal(multiplicand).multiply(toBigDecimal(multiplier));
		return product.setScale(DEFAULT_SCALE, DEFAULT_MODE).doubleValue();
	}

	/**
	 * @param multiplicand
	 * @param multiplier
	 * @param scale
	 * @param roundingMode
	 * @return product
	 */
	public static double multiply(final String multiplicand, final String multiplier, final int scale,
			final RoundingMode roundingMode)
	{
		if (scale < 0)
		{
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		final BigDecimal product = toBigDecimal(multiplicand).multiply(toBigDecimal(multiplier));
		if (roundingMode == null)
		{
			return product.setScale(scale, DEFAULT_MODE).doubleValue();
		}
		return product.setScale(scale, roundingMode).doubleValue();
	}

	/**
	 * @param dividend
	 * @param divisor
	 * @return quotient 默认四舍五入, 保留2位小数
	 */
	public static double divide(final double dividend, final double divisor)
	{
		final BigDecimal quotient = toBigDecimal(dividend).divide(toBigDecimal(divisor), 2, DEFAULT_MODE);
		return quotient.doubleValue();
	}

	/**
	 * @param dividend
	 * @param divisor
	 * @param scale
	 * @param roundingMode
	 * @return quotient
	 */
	public static double divide(final double dividend, final double divisor, final int scale, final RoundingMode roundingMode)
	{
		if (scale < 0)
		{
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		BigDecimal quotient;
		if (roundingMode == null)
		{
			quotient = toBigDecimal(dividend).divide(toBigDecimal(divisor), scale, DEFAULT_MODE);
		}
		else
		{
			quotient = toBigDecimal(dividend).divide(toBigDecimal(divisor), scale, roundingMode);
		}

		return quotient.doubleValue();
	}

	/**
	 * @param dividend
	 * @param divisor
	 * @return quotient 默认四舍五入, 保留2位小数
	 */
	public static double divide(final String dividend, final String divisor)
	{
		final BigDecimal quotient = toBigDecimal(dividend).divide(toBigDecimal(divisor), 2, DEFAULT_MODE);
		return quotient.doubleValue();
	}

	/**
	 * @param dividend
	 * @param divisor
	 * @param scale
	 * @param roundingMode
	 * @return quotient
	 */
	public static double divide(final String dividend, final String divisor, final int scale, final RoundingMode roundingMode)
	{
		if (scale < 0)
		{
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		BigDecimal quotient;
		if (roundingMode == null)
		{
			quotient = toBigDecimal(dividend).divide(toBigDecimal(divisor), scale, DEFAULT_MODE);
		}
		else
		{
			quotient = toBigDecimal(dividend).divide(toBigDecimal(divisor), scale, roundingMode);
		}

		return quotient.doubleValue();
	}

	/**
	 * <p>
	 * if a = b, return true
	 * <p>
	 * if a > b, return false
	 * <p>
	 * if a < b, return false
	 *
	 * @param a
	 * @param b
	 * @return true or false
	 */
	public static boolean isEqual(final BigDecimal a, final BigDecimal b)
	{
		if (a.compareTo(b) == 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * if a = b, return false
	 * <p>
	 * if a > b, return true
	 * <p>
	 * if a < b, return true
	 *
	 * @param a
	 * @param b
	 * @return true if a is not equal b
	 */
	public static boolean isNotEqual(final BigDecimal a, final BigDecimal b)
	{
		if (a.compareTo(b) != 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * if a > b, return true
	 * <p>
	 * if a = b, return false
	 * <p>
	 * if a < b, return false
	 *
	 * @param a
	 * @param b
	 * @return true or false
	 */
	public static boolean greaterThanLatter(final BigDecimal a, final BigDecimal b)
	{
		if (a.compareTo(b) > 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * if a < b, return true
	 * <p>
	 * if a > b, return false
	 * <p>
	 * if a = b, return false
	 *
	 * @param a
	 * @param b
	 * @return true or false
	 */
	public static boolean lessThanLatter(final BigDecimal a, final BigDecimal b)
	{
		if (a.compareTo(b) < 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * if a > b, return true
	 * <p>
	 * if a = b, return true
	 * <p>
	 * if a < b, return false
	 *
	 * @param a
	 * @param b
	 * @return true if a greater than or equals b
	 */
	public static boolean greaterThanOrEqualsLatter(final BigDecimal a, final BigDecimal b)
	{
		if (a.compareTo(b) >= 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * if a < b, return true
	 * <p>
	 * if a > b, return false
	 * <p>
	 * if a = b, return true
	 *
	 * @param a
	 * @param b
	 * @return true if a less than or equals b
	 */
	public static boolean lessThanOrEqualsLatter(final BigDecimal a, final BigDecimal b)
	{
		if (a.compareTo(b) <= 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * 判断target是不是在指定的闭区间范围内
	 *
	 * <pre>
	 * DecimalUtil.isInClosedInterval(9, 10, 20) == false
	 * </pre>
	 *
	 * <pre>
	 * DecimalUtil.isInClosedInterval(10, 10, 20) == true
	 * </pre>
	 *
	 * <pre>
	 * DecimalUtil.isInClosedInterval(15, 10, 20) == true
	 * </pre>
	 *
	 * <pre>
	 * DecimalUtil.isInClosedInterval(20, 10, 20) == true
	 * </pre>
	 *
	 * <pre>
	 * DecimalUtil.isInClosedInterval(21, 10, 20) == false
	 * </pre>
	 *
	 * @param checkedValue
	 * @param minBigDecimal
	 * @param maxBigDecimal
	 * @return true if the checked value in closed interval
	 */
	public static boolean isInClosedInterval(final BigDecimal checkedValue, final BigDecimal minBigDecimal,
			final BigDecimal maxBigDecimal)
	{
		if (checkedValue == null)
		{
			return false;
		}

		if (minBigDecimal == null || maxBigDecimal == null)
		{
			throw new IllegalArgumentException("Paramters must be not null");
		}

		if (minBigDecimal.compareTo(maxBigDecimal) > 0)
		{
			throw new IllegalArgumentException("MinBigDecimal must be less than maxBigDecimal");
		}

		if (checkedValue.compareTo(minBigDecimal) < 0)
		{
			return false;
		}

		if (checkedValue.compareTo(maxBigDecimal) > 0)
		{
			return false;
		}

		return true;
	}

	/**
	 * 判断target是不是在指定的开区间范围内
	 *
	 * <pre>
	 * DecimalUtil.isInOpenInterval(9, 10, 20) == false
	 * </pre>
	 *
	 * <pre>
	 * DecimalUtil.isInOpenInterval(10, 10, 20) == false
	 * </pre>
	 *
	 * <pre>
	 * DecimalUtil.isInOpenInterval(15, 10, 20) == true
	 * </pre>
	 *
	 * <pre>
	 * DecimalUtil.isInOpenInterval(20, 10, 20) == false
	 * </pre>
	 *
	 * <pre>
	 * DecimalUtil.isInOpenInterval(21, 10, 20) == false
	 * </pre>
	 *
	 * @param checkedValue
	 * @param minBigDecimal
	 * @param maxBigDecimal
	 * @return true if the checked value in open interval
	 */
	public static boolean isInOpenInterval(final BigDecimal checkedValue, final BigDecimal minBigDecimal,
			final BigDecimal maxBigDecimal)
	{
		if (checkedValue == null)
		{
			return false;
		}

		if (minBigDecimal == null || maxBigDecimal == null)
		{
			throw new IllegalArgumentException("Paramters must be not null");
		}

		if (minBigDecimal.compareTo(maxBigDecimal) > 0)
		{
			throw new IllegalArgumentException("MinBigDecimal must be less than maxBigDecimal");
		}

		if (checkedValue.compareTo(minBigDecimal) <= 0)
		{
			return false;
		}

		if (checkedValue.compareTo(maxBigDecimal) >= 0)
		{
			return false;
		}

		return true;
	}

	/**
	 * 比较一个值是否在某个区间内
	 *
	 * @param current
	 * @param min
	 * @param max
	 * @return 存在true，否则false
	 */
	public static boolean isInClosedInterval(final int current, final int min, final int max)
	{
		return Math.max(min, current) == Math.min(current, max);
	}
}
