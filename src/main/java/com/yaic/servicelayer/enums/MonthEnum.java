package com.yaic.servicelayer.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Qu Dihuai
 */
public enum MonthEnum
{
	/**
	 * January
	 */
	JANUARY(1, 31),
	/**
	 * February
	 */
	FEBRUARY(2, 29),
	/**
	 * March
	 */
	MARCH(3, 31),
	/**
	 * April
	 */
	APRIL(4, 30),
	/**
	 * May
	 */
	MAY(5, 31),
	/**
	 * June
	 */
	JUNE(6, 30),
	/**
	 * July
	 */
	JULY(7, 31),
	/**
	 * August
	 */
	AUGUST(8, 31),
	/**
	 * September
	 */
	SEPTEMBER(9, 30),
	/**
	 * October
	 */
	OCTOBER(10, 31),
	/**
	 * November
	 */
	NOVEMBER(11, 30),
	/**
	 * December
	 */
	DECEMBER(12, 31);

	private static Map<Integer, MonthEnum> cache;
	static
	{
		final Map<Integer, MonthEnum> map = new HashMap<Integer, MonthEnum>(12);
		for (final MonthEnum monthEnum : MonthEnum.values())
		{
			map.put(monthEnum.month, monthEnum);
		}
		cache = Collections.unmodifiableMap(map);
	}

	private int month;
	private int maxDays;

	private MonthEnum(final int month, final int maxDays)
	{
		this.month = month;
		this.maxDays = maxDays;
	}

	/**
	 * @return month
	 */
	public int month()
	{
		return month;
	}

	/**
	 * @return maxDays
	 */
	public int maxDays()
	{
		return maxDays;
	}

	/**
	 * @param month
	 * @return MonthEnum
	 */
	public static MonthEnum fromMonth(final int month)
	{
		return cache.get(month);
	}

}
