package com.yaic.servicelayer.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;

import com.yaic.servicelayer.enums.MonthEnum;


/**
 * 时间日期格式化工具类
 *
 * @author Qu Dihuai
 *
 */
public class TimeUtil
{
	/**
	 * Represents the default date-time-zone is Asia/Shanghai.
	 */
	public static final DateTimeZone DEFAULT_DATETIME_ZONE = DateTimeZone.forID("Asia/Shanghai");

	/**
	 * ISO8601 format for date-time. The format used is <tt>{@value}</tt>.
	 */
	public static final String ISO_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * ISO8601 format for date-time with millis. The format used is <tt>{@value}</tt>.
	 */
	public static final String ISO_DATETIME_MILLIS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * ISO8601 format for date. The format used is <tt>{@value}</tt>.
	 */
	public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * ISO8601 format for date only with year and month. The format used is <tt>{@value}</tt>.
	 */
	public static final String ISO_DATE_NO_SEC_FORMAT = "yyyy-MM";

	/**
	 * ISO8601 format for time. The format used is <tt>{@value}</tt>.
	 */
	public static final String ISO_TIME_FORMAT = "HH:mm:ss";

	/**
	 * ISO8601 format for date-time without second. The format used is <tt>{@value}</tt>.
	 */
	public static final String ISO_DATETIME_NO_SEC_FORMAT = "yyyy-MM-dd HH:mm";

	/**
	 * ISO8601 format for date-time without second and minute. The format used is <tt>{@value}</tt>.
	 */
	public static final String ISO_DATETIME_NO_MIN_SEC_FORMAT = "yyyy-MM-dd HH";

	/**
	 * ISO8601-like format for date-time. The format used is <tt>{@value}</tt>.
	 */
	public static final String DATETIME_FORMAT = "yyyyMMddHHmmss";

	/**
	 * ISO8601-like format for date. The format used is <tt>{@value}</tt>.
	 */
	public static final String DATE_FORMAT = "yyyyMMdd";

	/**
	 * ISO8601-like format for date only with year and month. The format used is <tt>{@value}</tt>.
	 */
	public static final String DATE_NO_DAY_FORMAT = "yyyyMM";

	/**
	 * ISO8601-like format for date only with year. The format used is <tt>{@value}</tt>.
	 */
	public static final String DATE_ONLY_YEAR_FORMAT = "yyyy";

	/**
	 * ISO8601-like format for date-time without second. The format used is <tt>{@value}</tt>.
	 */
	public static final String DATETIME_NO_SEC_FORMAT = "yyyyMMddHHmm";

	/**
	 * ISO8601-like format for date-time without second and minute. The format used is <tt>{@value}</tt>.
	 */
	public static final String DATETIME_NO_MIN_SEC_FORMAT = "yyyyMMddHH";

	/**
	 * ISO8601-like format for date only with month. The format used is <tt>{@value}</tt>.
	 */
	public static final String DATE_ONLY_MONTH_FORMAT = "MM";

	/**
	 * ISO8601 format for time with millis. The format used is <tt>{@value}</tt>.
	 */
	private static final String ISO_TIME_MILLIS_FORMAT = "HH:mm:ss.SSS";

	/**
	 * ISO8601 format for time from second to millis. The format used is <tt>{@value}</tt>.
	 */
	private static final String ISO_TIME_S2MS_FORMAT = "ss.SSS";

	/**
	 * ISO8601 format for time from minute to millis. The format used is <tt>{@value}</tt>.
	 */
	private static final String ISO_TIME_MIN2MS_FORMAT = "mm:ss.SSS";

	/**
	 * ISO8601 format for date-time from day to millis. The format used is <tt>{@value}</tt>.
	 */
	private static final String ISO_DATETIME_D2MS_FORMAT = "dd HH:mm:ss.SSS";

	/**
	 * ISO8601 format for date-time from day to second. The format used is <tt>{@value}</tt>.
	 */
	private static final String ISO_DATETIME_D2S_FORMAT = "dd HH:mm:ss";

	/**
	 * ISO8601 format for date-time from day to minute. The format used is <tt>{@value}</tt>.
	 */
	private static final String ISO_DATETIME_D2MIN_FORMAT = "dd HH:mm";

	/**
	 * ISO8601 format for date-time from day to hour. The format used is <tt>{@value}</tt>.
	 */
	private static final String ISO_DATETIME_D2H_FORMAT = "dd HH";

	/**
	 * ISO8601 format for date-time from month to millis. The format used is <tt>{@value}</tt>.
	 */
	private static final String ISO_DATETIME_M2MS_FORMAT = "MM-dd HH:mm:ss.SSS";

	/**
	 * ISO8601 format for date-time from month to second. The format used is <tt>{@value}</tt>.
	 */
	private static final String ISO_DATETIME_M2S_FORMAT = "MM-dd HH:mm:ss";

	/**
	 * ISO8601 format for date-time from month to minute. The format used is <tt>{@value}</tt>.
	 */
	private static final String ISO_DATETIME_M2MIN_FORMAT = "MM-dd HH:mm";

	/**
	 * ISO8601 format for date-time from month to hour. The format used is <tt>{@value}</tt>.
	 */
	private static final String ISO_DATETIME_M2H_FORMAT = "MM-dd HH";

	/**
	 * ISO8601 format for time without hour. The format used is <tt>{@value}</tt>.
	 */
	private static final String ISO_TIME_NO_HOUR_FORMAT = "mm:ss";

	/**
	 * ISO8601 format for time without minute. The format used is <tt>{@value}</tt>.
	 */
	private static final String ISO_TIME_NO_MINUTE_FORMAT = "HH:mm";

	/**
	 * ISO8601 format for time only millis. The format used is <tt>{@value}</tt>.
	 */
	private static final String DATE_ONLY_MILLIS_FORMAT = "SSS";

	/**
	 * ISO8601 format for time only second. The format used is <tt>{@value}</tt>.
	 */
	private static final String DATE_ONLY_SECOND_FORMAT = "ss";

	/**
	 * ISO8601 format for time only minute. The format used is <tt>{@value}</tt>.
	 */
	private static final String DATE_ONLY_MINUTE_FORMAT = "mm";

	/**
	 * ISO8601 format for time only hour. The format used is <tt>{@value}</tt>.
	 */
	private static final String DATE_ONLY_HOUR_FORMAT = "HH";

	/**
	 * ISO8601 format for time only day. The format used is <tt>{@value}</tt>.
	 */
	private static final String DATE_ONLY_DAY_FORMAT = "dd";

	/**
	 * ISO8601 format for date without year. The format used is <tt>{@value}</tt>.
	 */
	private static final String ISO_DATE_NO_YEAR_FORMAT = "MM-dd";

	/**
	 * 1 day = {@value} ms
	 */
	public static final long D2MS = 0x5265c00L;

	/**
	 * 1 hour = {@value} ms
	 */
	public static final long H2MS = 0x36ee80L;

	/**
	 * 1 minute = {@value} ms
	 */
	public static final long M2MS = 0xea60L;

	/**
	 * 1 second = {@value} ms
	 */
	public static final long S2MS = 0x3e8L;

	// 年用1表示，月用2表示，日用3表示，依次类推
	// 从年开始11-17
	/**
	 * <code>年到年</code>
	 */
	public static final int YEAR_TO_YEAR = 11;
	/**
	 * <code>年到月</code>
	 */
	public static final int YEAR_TO_MONTH = 12;
	/**
	 * <code>年到日</code>
	 */
	public static final int YEAR_TO_DAY = 13;
	/**
	 * <code>年到小时</code>
	 */
	public static final int YEAR_TO_HOUR = 14;
	/**
	 * <code>年到分</code>
	 */
	public static final int YEAR_TO_MINUTE = 15;
	/**
	 * <code>年到秒</code>
	 */
	public static final int YEAR_TO_SECOND = 16;
	/**
	 * <code>年到毫秒</code>
	 */
	public static final int YEAR_TO_MILLISECOND = 17;
	// 从月开始22-27
	/**
	 * <code>月到月</code>
	 */
	public static final int MONTH_TO_MONTH = 22;
	/**
	 * <code>月到日</code>
	 */
	public static final int MONTH_TO_DAY = 23;
	/**
	 * <code>月到小时</code>
	 */
	public static final int MONTH_TO_HOUR = 24;
	/**
	 * <code>月到分</code>
	 */
	public static final int MONTH_TO_MINUTE = 25;
	/**
	 * <code>月到秒</code>
	 */
	public static final int MONTH_TO_SECOND = 26;
	/**
	 * <code>月到毫秒</code>
	 */
	public static final int MONTH_TO_MILLISECOND = 27;
	// 从日开始33-37
	/**
	 * <code>日到日</code>
	 */
	public static final int DAY_TO_DAY = 33;
	/**
	 * <code>日到小时</code>
	 */
	public static final int DAY_TO_HOUR = 34;
	/**
	 * <code>日到分</code>
	 */
	public static final int DAY_TO_MINUTE = 35;
	/**
	 * <code>日到秒</code>
	 */
	public static final int DAY_TO_SECOND = 36;
	/**
	 * <code>日到毫秒</code>
	 */
	public static final int DAY_TO_MILLISECOND = 37;
	// 从小时开始44-47
	/**
	 * <code>小时到小时</code>
	 */
	public static final int HOUR_TO_HOUR = 44;
	/**
	 * <code>小时到分</code>
	 */
	public static final int HOUR_TO_MINUTE = 45;
	/**
	 * <code>小时到秒</code>
	 */
	public static final int HOUR_TO_SECOND = 46;
	/**
	 * <code>小时到毫秒</code>
	 */
	public static final int HOUR_TO_MILLISECOND = 47;
	// 从分开始55-57
	/**
	 * <code>分到分</code>
	 */
	public static final int MINUTE_TO_MINUTE = 55;
	/**
	 * <code>分到秒</code>
	 */
	public static final int MINUTE_TO_SECOND = 56;
	/**
	 * <code>分到毫秒</code>
	 */
	public static final int MINUTE_TO_MILLISECOND = 57;
	// 从秒开始66-67
	/**
	 * <code>秒到秒</code>
	 */
	public static final int SECOND_TO_SECOND = 66;
	/**
	 * <code>秒到毫秒</code>
	 */
	public static final int SECOND_TO_MILLISECOND = 67;
	// 从毫秒开始77-77
	/**
	 * <code>毫秒到毫秒</code>
	 */
	public static final int MILLISECOND_TO_MILLISECOND = 77;

	private static final int[] dayArray = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	
	private static DateTimeFormatter DATETIME_FORMATTER;
	static
	{
		final String[] dateFormats =
			{
					ISO_DATETIME_FORMAT,
					ISO_DATETIME_MILLIS_FORMAT,
					ISO_DATE_FORMAT,
					ISO_DATE_NO_SEC_FORMAT,
					ISO_TIME_FORMAT,
					ISO_DATETIME_NO_SEC_FORMAT,
					ISO_DATETIME_NO_MIN_SEC_FORMAT,
					ISO_TIME_MILLIS_FORMAT,
					DATETIME_FORMAT,
					DATE_FORMAT,
					DATE_NO_DAY_FORMAT,
					DATE_ONLY_YEAR_FORMAT,
					DATETIME_NO_SEC_FORMAT,
					DATETIME_NO_MIN_SEC_FORMAT,
					ISO_TIME_S2MS_FORMAT,
					ISO_TIME_MIN2MS_FORMAT,
					ISO_DATETIME_D2MS_FORMAT,
					ISO_DATETIME_D2S_FORMAT,
					ISO_DATETIME_D2MIN_FORMAT,
					ISO_DATETIME_D2H_FORMAT,
					ISO_DATETIME_M2MS_FORMAT,
					ISO_DATETIME_M2S_FORMAT,
					ISO_DATETIME_M2MIN_FORMAT,
					ISO_DATETIME_M2H_FORMAT,
					ISO_DATE_NO_YEAR_FORMAT,
					DATE_ONLY_MILLIS_FORMAT
			};

		final List<DateTimeParser> dateTimeFormatters = new ArrayList<>(dateFormats.length);
		for (final String dateFormat : dateFormats)
		{
			dateTimeFormatters.add(DateTimeFormat.forPattern(dateFormat).getParser());
		}
		final DateTimeParser[] parsers = dateTimeFormatters.toArray(new DateTimeParser[0]);
		DATETIME_FORMATTER = new DateTimeFormatterBuilder().append(null, parsers).toFormatter();
	}

	private static Map<String, Pattern> PATTERN_FORMAT;
	static
	{
		// Regex for yyyyMMdd
		final String dateRegex = "^(?:(?!0000)[0-9]{4}(?:(?:0[1-9]|1[0-2])(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])(?:29|30)|(?:0[13578]|1[02])31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)0229)$";

		// Regex for yyyyMMddHHmmss
		final String dateTimeRegex = "^(?:(?!0000)[0-9]{4}(?:(?:0[1-9]|1[0-2])(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])(?:29|30)|(?:0[13578]|1[02])31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)0229)([0-1][0-9]|2[0-3])[0-5][0-9][0-5][0-9]$";

		// Regex for yyyy-MM-dd
		final String isoDateRegex = "^(?:(?!0000)[0-9]{4}([-])(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-])0?2\\2(?:29))$";

		// Regex for yyyy-MM-dd HH:mm:ss
		final String isoDateTimeRegex = "^(?:(?!0000)[0-9]{4}([-])(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-])0?2\\2(?:29))\\s([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";

		final Map<String, Pattern> map = new HashMap<>();
		map.put(DATE_FORMAT, Pattern.compile(dateRegex));
		map.put(DATETIME_FORMAT, Pattern.compile(dateTimeRegex));
		map.put(ISO_DATE_FORMAT, Pattern.compile(isoDateRegex));
		map.put(ISO_DATETIME_FORMAT, Pattern.compile(isoDateTimeRegex));
		PATTERN_FORMAT = Collections.unmodifiableMap(map);
	}

	private static Map<Integer, DateTimeFormatter> DATETIME_FORMATTERS;
	private static Map<Integer, SimpleDateFormat> DATETIME_SIMPLE_FORMATTERS;
	static
	{
		final Map<Integer, String> isoDateFormatMap = new HashMap<>();
		isoDateFormatMap.put(YEAR_TO_YEAR, DATE_ONLY_YEAR_FORMAT);
		isoDateFormatMap.put(YEAR_TO_MONTH, ISO_DATE_NO_SEC_FORMAT);
		isoDateFormatMap.put(YEAR_TO_DAY, ISO_DATE_FORMAT);
		isoDateFormatMap.put(YEAR_TO_HOUR, ISO_DATETIME_NO_MIN_SEC_FORMAT);
		isoDateFormatMap.put(YEAR_TO_MINUTE, ISO_DATETIME_NO_SEC_FORMAT);
		isoDateFormatMap.put(YEAR_TO_SECOND, ISO_DATETIME_FORMAT);
		isoDateFormatMap.put(YEAR_TO_MILLISECOND, ISO_DATETIME_MILLIS_FORMAT);
		isoDateFormatMap.put(MONTH_TO_MONTH, DATE_ONLY_MONTH_FORMAT);
		isoDateFormatMap.put(MONTH_TO_DAY, ISO_DATE_NO_YEAR_FORMAT);
		isoDateFormatMap.put(MONTH_TO_HOUR, ISO_DATETIME_M2H_FORMAT);
		isoDateFormatMap.put(MONTH_TO_MINUTE, ISO_DATETIME_M2MIN_FORMAT);
		isoDateFormatMap.put(MONTH_TO_SECOND, ISO_DATETIME_M2S_FORMAT);
		isoDateFormatMap.put(MONTH_TO_MILLISECOND, ISO_DATETIME_M2MS_FORMAT);
		isoDateFormatMap.put(DAY_TO_DAY, DATE_ONLY_DAY_FORMAT);
		isoDateFormatMap.put(DAY_TO_HOUR, ISO_DATETIME_D2H_FORMAT);
		isoDateFormatMap.put(DAY_TO_MINUTE, ISO_DATETIME_D2MIN_FORMAT);
		isoDateFormatMap.put(DAY_TO_SECOND, ISO_DATETIME_D2S_FORMAT);
		isoDateFormatMap.put(DAY_TO_MILLISECOND, ISO_DATETIME_D2MS_FORMAT);
		isoDateFormatMap.put(HOUR_TO_HOUR, DATE_ONLY_HOUR_FORMAT);
		isoDateFormatMap.put(HOUR_TO_MINUTE, ISO_TIME_NO_MINUTE_FORMAT);
		isoDateFormatMap.put(HOUR_TO_SECOND, ISO_TIME_FORMAT);
		isoDateFormatMap.put(HOUR_TO_MILLISECOND, ISO_TIME_MILLIS_FORMAT);
		isoDateFormatMap.put(MINUTE_TO_MINUTE, DATE_ONLY_MINUTE_FORMAT);
		isoDateFormatMap.put(MINUTE_TO_SECOND, ISO_TIME_NO_HOUR_FORMAT);
		isoDateFormatMap.put(MINUTE_TO_MILLISECOND, ISO_TIME_MIN2MS_FORMAT);
		isoDateFormatMap.put(SECOND_TO_SECOND, DATE_ONLY_SECOND_FORMAT);
		isoDateFormatMap.put(SECOND_TO_MILLISECOND, ISO_TIME_S2MS_FORMAT);
		isoDateFormatMap.put(MILLISECOND_TO_MILLISECOND, DATE_ONLY_MILLIS_FORMAT);

		final Map<Integer, DateTimeFormatter> formatterMap = new HashMap<>(isoDateFormatMap.size());
		for (final Map.Entry<Integer, String> entry : isoDateFormatMap.entrySet())
		{
			formatterMap.put(entry.getKey(), DateTimeFormat.forPattern(entry.getValue()));
		}

		final Map<Integer, SimpleDateFormat> simpleFormatterMap = new HashMap<>(isoDateFormatMap.size());
		for (final Map.Entry<Integer, String> entry : isoDateFormatMap.entrySet())
		{
			simpleFormatterMap.put(entry.getKey(), new SimpleDateFormat(entry.getValue()));
		}
		
		DATETIME_FORMATTERS = Collections.unmodifiableMap(formatterMap);
		DATETIME_SIMPLE_FORMATTERS = Collections.unmodifiableMap(simpleFormatterMap);
	}

	/**
	 * Adds a number of days to a date returning a new object. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @param amount
	 *           the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date addDays(final Date date, final int amount)
	{
		return newDateTime(date).plusDays(amount).toDate();
	}

	/**
	 * Adds a number of hours to a date returning a new object. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @param amount
	 *           the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date addHours(final Date date, final int amount)
	{
		return newDateTime(date).plusHours(amount).toDate();
	}

	/**
	 * Adds a number of milliseconds to a date returning a new object. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @param amount
	 *           the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date addMilliseconds(final Date date, final int amount)
	{
		return newDateTime(date).plusMillis(amount).toDate();
	}

	/**
	 * Adds a number of minutes to a date returning a new object. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @param amount
	 *           the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date addMinutes(final Date date, final int amount)
	{
		return newDateTime(date).plusMinutes(amount).toDate();
	}

	/**
	 * Adds a number of months to a date returning a new object. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @param amount
	 *           the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date addMonths(final Date date, final int amount)
	{
		return newDateTime(date).plusMonths(amount).toDate();
	}

	/**
	 * Adds a number of seconds to a date returning a new object. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @param amount
	 *           the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date addSeconds(final Date date, final int amount)
	{
		return newDateTime(date).plusSeconds(amount).toDate();
	}

	/**
	 * Adds a number of weeks to a date returning a new object. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @param amount
	 *           the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date addWeeks(final Date date, final int amount)
	{
		return newDateTime(date).plusWeeks(amount).toDate();
	}

	/**
	 * Adds a number of years to a date returning a new object. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @param amount
	 *           the amount to add, may be negative
	 * @return the new date object with the amount added
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date addYears(final Date date, final int amount)
	{
		return newDateTime(date).plusYears(amount).toDate();
	}

	/**
	 * Check if date1 after date2
	 *
	 * @param date1
	 * @param date2
	 * @return true if date1 after date2
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static boolean afterLatterDate(final Date date1, final Date date2)
	{
		if (date1 == null || date2 == null)
		{
			throw new IllegalArgumentException("date must be not null");
		}

		if (date1.getTime() > date2.getTime())
		{
			return true;
		}

		return false;
	}

	/**
	 * Check if date1 before date2
	 *
	 * @param date1
	 * @param date2
	 * @return true if date1 before date2
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static boolean beforeLatterDate(final Date date1, final Date date2)
	{
		if (date1 == null || date2 == null)
		{
			throw new IllegalArgumentException("date must be not null");
		}

		if (date1.getTime() < date2.getTime())
		{
			return true;
		}

		return false;
	}

	/**
	 * Return the begin day of month. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @return the new date object
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date beginDateOfMonth(final Date date)
	{
		return new DateTime(date).dayOfMonth().withMinimumValue().toDate();
	}

	/**
	 * Return the begin day of the week. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @return the new date object
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date beginDateOfWeek(final Date date)
	{
		return new DateTime(date).dayOfWeek().withMinimumValue().toDate();
	}

	/**
	 * Return the begin of year. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @return the new date object
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date beginDateOfYear(final Date date)
	{
		return new DateTime(date).dayOfYear().withMinimumValue().toDate();
	}

	/**
	 * Return the begin of day. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @return the new date object
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date beginOfDay(final Date date)
	{
		return new DateTime(date).withTimeAtStartOfDay().toDate();
	}

	/**
	 * @param year
	 *           the year, not 0
	 * @param monthOfYear
	 *           the month of the year, from 1 to 12
	 * @param dayOfMonth
	 *           the day of the month, from 1 to 31
	 * @param hourOfDay
	 *           the hour of the day, from 0 to 23
	 * @param minuteOfHour
	 *           the minute of the hour, from 0 to 59
	 * @param secondOfMinute
	 *           the second of the minute, from 0 to 59
	 * @return the created date
	 */
	public static Date createDate(final int year, final int monthOfYear, final int dayOfMonth, final int hourOfDay,
			final int minuteOfHour, final int secondOfMinute)
	{
		if (year == 0)
		{
			throw new IllegalArgumentException("Year must be not 0");
		}

		final MonthEnum monthEnum = MonthEnum.fromMonth(monthOfYear);
		if (monthEnum == null)
		{
			throw new IllegalArgumentException("MonthOfYear must be between 1 and 12");
		}

		if (monthEnum == MonthEnum.FEBRUARY)
		{
			if (isLeapYear(year))
			{
				if (dayOfMonth > 29)
				{
					throw new IllegalArgumentException("dayOfMonth is invalid");
				}
			}
			else
			{
				if (dayOfMonth > 28)
				{
					throw new IllegalArgumentException("dayOfMonth is invalid");
				}
			}
		}
		else
		{
			if (dayOfMonth > monthEnum.maxDays())
			{
				throw new IllegalArgumentException("dayOfMonth is invalid");
			}
		}

		return new DateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute, DEFAULT_DATETIME_ZONE).toDate();
	}

	/**
	 * Return the end day of month. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @return the new date object
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date endDateOfMonth(final Date date)
	{
		return new DateTime(date).dayOfMonth().withMaximumValue().toDate();
	}

	/**
	 * Return the last day of the week. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @return the new date object
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date endDateOfWeek(final Date date)
	{
		return new DateTime(date).dayOfWeek().withMaximumValue().toDate();
	}

	/**
	 * Return the end of year. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @return the new date object
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date endDateOfYear(final Date date)
	{
		return new DateTime(date).dayOfYear().withMaximumValue().toDate();
	}

	/**
	 * Return the end of day. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @return the new date object
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date endOfDay(final Date date)
	{
		return new DateTime(date).withTime(23, 59, 59, 999).toDate();
	}

	/**
	 * <p>
	 * Formats a date/time into a default pattern yyyy-MM-dd HH:mm:ss.
	 * </p>
	 *
	 * @param date
	 *           the date to format
	 * @return the formatted date
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static String format(final Date date)
	{
		return format(date, ISO_DATETIME_FORMAT);
	}

	/**
	 * <p>
	 * Formats a date/time into a specific pattern.
	 * </p>
	 *
	 * @param date
	 *           the date to format
	 * @param pattern
	 *           the pattern to use to format the date
	 * @return the formatted date
	 * @throws IllegalArgumentException
	 *            if the date or pattern is null
	 */
	public static String format(final Date date, final String pattern)
	{
		if (StringUtil.isEmpty(pattern))
		{
			throw new IllegalArgumentException("pattern must be not null");
		}

		return new DateTime(date).toString(pattern);
	}

	/**
	 * <p>
	 * Formats a date/time into a specific pattern.
	 * </p>
	 *
	 * @param millis
	 *           the date to format expressed in milliseconds
	 * @param pattern
	 *           the pattern to use to format the date
	 * @return the formatted date
	 * @throws IllegalArgumentException
	 *            if the pattern is null
	 */
	public static String format(final long millis, final String pattern)
	{
		if (StringUtil.isEmpty(pattern))
		{
			throw new IllegalArgumentException("pattern must be not null");
		}
		return new DateTime(millis, DEFAULT_DATETIME_ZONE).toString(pattern);
	}

	/**
	 * 计算到指定日期的年龄(周岁)
	 *
	 * @param birthdate
	 * @param specifiedDate
	 *
	 * @return the actual age
	 * @throws IllegalArgumentException
	 *            If if either date is <code>null</code>, or birthdate after the specified date
	 */
	public static int getActualAge(final Date birthdate, final Date specifiedDate)
	{
		if (birthdate == null)
		{
			throw new IllegalArgumentException("Bithdate must be not null");
		}

		if (specifiedDate == null)
		{
			throw new IllegalArgumentException("Specified date must be not null");
		}

		if (birthdate.getTime() > specifiedDate.getTime())
		{
			throw new IllegalArgumentException("Bithdate can not after the specified date");
		}

		final LocalDate birthDateTime = new LocalDate(birthdate);
		final LocalDate specifiedDateTime = new LocalDate(specifiedDate);
		if (specifiedDateTime.getYear() == birthDateTime.getYear())
		{
			return 0;
		}

		final int interval = specifiedDateTime.getYear() - birthDateTime.getYear();
		if (specifiedDateTime.getMonthOfYear() < birthDateTime.getMonthOfYear())
		{
			return interval - 1;
		}
		else if (specifiedDateTime.getMonthOfYear() > birthDateTime.getMonthOfYear())
		{
			return interval;
		}
		else
		{
			if (specifiedDateTime.getDayOfMonth() < birthDateTime.getDayOfMonth())
			{
				return interval - 1;
			}
			return interval;
		}
	}

	/**
	 * 计算现在的年龄(周岁)
	 *
	 * @param birthdate
	 *
	 * @return the actual age
	 * @throws IllegalArgumentException
	 *            If birthdate after the current time
	 */
	public static int getCurrentActualAge(final Date birthdate)
	{
		return getActualAge(birthdate, getCurrentTime());
	}

	/**
	 * @return the current day of month
	 */
	public static int getCurrentDay()
	{
		return newDateTime().getDayOfMonth();
	}

	/**
	 * @return the current hour of day
	 */
	public static int getCurrentHour()
	{
		return newDateTime().getHourOfDay();
	}

	/**
	 * If current time is 2018-03-31, will return 3
	 *
	 * @return the current month of year.
	 */
	public static int getCurrentMonth()
	{
		return newDateTime().getMonthOfYear();
	}

	/**
	 * 计算现在的年龄(虚岁)
	 *
	 * @param birthdate
	 *
	 * @return the norminal age
	 * @throws IllegalArgumentException
	 *            If birthdate after the current time
	 */
	public static int getCurrentNorminalAge(final Date birthdate)
	{
		return getNorminalAge(birthdate, getCurrentTime());
	}

	/**
	 * @return the current time
	 */
	public static Date getCurrentTime()
	{
		return new DateTime(DEFAULT_DATETIME_ZONE).toDate();
	}

	/**
	 * Return the current time as string
	 *
	 * @param format
	 * @return the current time as string
	 */
	public static String getCurrentTime(final String format)
	{
		return format(getCurrentTime(), format);
	}

	/**
	 * @return the current time in millis
	 */
	public static final long getCurrentTimeMillis()
	{
		return System.currentTimeMillis();
	}

	/**
	 * @return the current year
	 */
	public static int getCurrentYear()
	{
		return newDateTime().getYear();
	}

	/**
	 * @param date
	 * @return the day of month
	 */
	public static int getDay(final Date date)
	{
		return newDateTime(date).getDayOfMonth();
	}

	/**
	 * Return the first day of the month. The original date object is unchanged.
	 *
	 * @param date
	 * @return first day of the month
	 */
	public static Date getFirstDayOfMonth(final Date date)
	{
		return newDateTime(date).withDayOfMonth(1).toDate();
	}

	/**
	 * @param date
	 * @return the hour of day
	 */
	public static int getHour(final Date date)
	{
		return newDateTime(date).getHourOfDay();
	}

	/**
	 * 获取两个日期相差的年份数, 不满一年不计入
	 *
	 *
	 * @param date1
	 * @param date2
	 * @return the interval of years
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static long getIntervalYears(final Date date1, final Date date2)
	{
		if (date1 == null || date2 == null)
		{
			throw new IllegalArgumentException("Date must be not null");
		}

		DateTime begin = null;
		DateTime end = null;
		if (date1.getTime() <= date2.getTime())
		{
			begin = newDateTime(date1);
			end = newDateTime(date2);
		}
		else
		{
			begin = newDateTime(date2);
			end = newDateTime(date1);
		}

		final int yearsBetween = end.getYear() - begin.getYear();
		if (end.getMonthOfYear() < begin.getMonthOfYear())
		{
			return yearsBetween - 1;
		}
		else if (end.getMonthOfYear() > begin.getMonthOfYear())
		{
			return yearsBetween;
		}

		if (end.getDayOfMonth() < begin.getDayOfMonth())
		{
			return yearsBetween - 1;
		}
		else if (end.getDayOfMonth() > begin.getDayOfMonth())
		{
			return yearsBetween;
		}

		if (end.getSecondOfDay() < begin.getSecondOfDay())
		{
			return yearsBetween - 1;
		}

		return yearsBetween;
	}

	/**
	 * 获取两个日期相差的年份数, 不满一年算1年
	 *
	 * @param date1
	 * @param date2
	 * @return the interval of years
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static long getIntervalYearsByRoundup(final Date date1, final Date date2)
	{
		if (date1 == null || date2 == null)
		{
			throw new IllegalArgumentException("Date must be not null");
		}

		DateTime begin = null;
		DateTime end = null;
		if (date1.getTime() <= date2.getTime())
		{
			begin = newDateTime(date1);
			end = newDateTime(date2);
		}
		else
		{
			begin = newDateTime(date2);
			end = newDateTime(date1);
		}

		int yearsBetween = end.getYear() - begin.getYear();
		if (end.getMonthOfYear() < begin.getMonthOfYear())
		{
			return yearsBetween;
		}
		else if (end.getMonthOfYear() > begin.getMonthOfYear())
		{
			return yearsBetween + 1;
		}

		if (end.getDayOfMonth() < begin.getDayOfMonth())
		{
			return yearsBetween;
		}
		else if (end.getDayOfMonth() > begin.getDayOfMonth())
		{
			if (!begin.year().isLeap() && begin.getMonthOfYear() == 2 && begin.getDayOfMonth() == 28)
			{
				if (end.year().isLeap() && end.getMonthOfYear() == 2 && end.getDayOfMonth() == 29)
				{
					return yearsBetween;
				}
			}
			return yearsBetween + 1;
		}

		if (end.getSecondOfDay() >= begin.getSecondOfDay())
		{
			yearsBetween += 1;
		}

		if (begin.year().isLeap() && begin.getMonthOfYear() == 2 && begin.getDayOfMonth() == 28)
		{
			if (!end.year().isLeap() && end.getMonthOfYear() == 2 && end.getDayOfMonth() == 28)
			{
				return yearsBetween + 1;
			}
		}

		return yearsBetween;
	}

	/**
	 * 获取两个日期相差的月份数, 不满一月不计入
	 *
	 * @param date1
	 * @param date2
	 * @return the interval of month
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static long getIntervalMonths(final Date date1, final Date date2)
	{
		if (date1 == null || date2 == null)
		{
			throw new IllegalArgumentException("Date must be not null");
		}

		DateTime begin = null;
		DateTime end = null;
		if (date1.getTime() <= date2.getTime())
		{
			begin = newDateTime(date1);
			end = newDateTime(date2);
		}
		else
		{
			begin = newDateTime(date2);
			end = newDateTime(date1);
		}

		final int monthsBetween = (end.getYear() - begin.getYear()) * 12 + (end.getMonthOfYear() - begin.getMonthOfYear());
		if (end.getDayOfMonth() < begin.getDayOfMonth())
		{
			return monthsBetween - 1;
		}
		else if (end.getDayOfMonth() > begin.getDayOfMonth())
		{
			return monthsBetween;
		}

		if (end.getSecondOfDay() < begin.getSecondOfDay())
		{
			return monthsBetween - 1;
		}

		return monthsBetween;
	}

	/**
	 * 获取两个日期相差的月份数, 不满一月不计入
	 *
	 * @param date1
	 * @param date2
	 * @return the interval of months
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static long getIntervalMonthsByRoundup(final Date date1, final Date date2)
	{
		if (date1 == null || date2 == null)
		{
			throw new IllegalArgumentException("Date must be not null");
		}

		DateTime begin = null;
		DateTime end = null;
		if (date1.getTime() <= date2.getTime())
		{
			begin = newDateTime(date1);
			end = newDateTime(date2);
		}
		else
		{
			begin = newDateTime(date2);
			end = newDateTime(date1);
		}

		int monthsBetween = (end.getYear() - begin.getYear()) * 12 + (end.getMonthOfYear() - begin.getMonthOfYear());
		if (end.getDayOfMonth() < begin.getDayOfMonth())
		{
			return monthsBetween;
		}
		else if (end.getDayOfMonth() > begin.getDayOfMonth())
		{
			if (!begin.year().isLeap() && begin.getMonthOfYear() == 2 && begin.getDayOfMonth() == 28)
			{
				if (end.year().isLeap() && end.getMonthOfYear() == 2 && end.getDayOfMonth() == 29)
				{
					return monthsBetween;
				}
			}
			return monthsBetween + 1;
		}

		if (end.getSecondOfDay() >= begin.getSecondOfDay())
		{
			monthsBetween += 1;
		}

		if (begin.year().isLeap() && begin.getMonthOfYear() == 2 && begin.getDayOfMonth() == 28)
		{
			if (!end.year().isLeap() && end.getMonthOfYear() == 2 && end.getDayOfMonth() == 28)
			{
				return monthsBetween + 1;
			}
		}

		return monthsBetween;
	}

	/**
	 * 获取两个日期相差的天数, 不满一天不计入
	 *
	 * @param date1
	 * @param date2
	 * @return the interval of days
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static long getIntervalDays(final Date date1, final Date date2)
	{
		if (date1 == null || date2 == null)
		{
			throw new IllegalArgumentException("Date date must be not null");
		}

		DateTime begin = null;
		DateTime end = null;
		if (date1.getTime() <= date2.getTime())
		{
			begin = newDateTime(date1);
			end = newDateTime(date2);
		}
		else
		{
			begin = newDateTime(date2);
			end = newDateTime(date1);
		}

		return (end.getMillis() - begin.getMillis()) / D2MS;
	}

	/**
	 * 获取两个日期相差的天数, 不满一天按一天算
	 *
	 * @param date1
	 * @param date2
	 * @return the interval of days
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static long getIntervalDaysByRoundup(final Date date1, final Date date2)
	{
		if (date1 == null || date2 == null)
		{
			throw new IllegalArgumentException("Date date must be not null");
		}

		DateTime begin = null;
		DateTime end = null;
		if (date1.getTime() <= date2.getTime())
		{
			begin = newDateTime(date1);
			end = newDateTime(date2);
		}
		else
		{
			begin = newDateTime(date2);
			end = newDateTime(date1);
		}

		final long millisBetween = end.getMillis() - begin.getMillis();
		if (millisBetween % D2MS != 0)
		{
			return millisBetween / D2MS + 1;
		}

		return millisBetween / D2MS;
	}

	/**
	 * 获取间隔的小时数, 不满1小时不计入
	 *
	 * @param date1
	 * @param date2
	 * @return the interval of hours
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static long getIntervalHours(final Date date1, final Date date2)
	{
		if (date1 == null || date2 == null)
		{
			throw new IllegalArgumentException("Date date must be not null");
		}

		DateTime begin = null;
		DateTime end = null;
		if (date1.getTime() <= date2.getTime())
		{
			begin = newDateTime(date1);
			end = newDateTime(date2);
		}
		else
		{
			begin = newDateTime(date2);
			end = newDateTime(date1);
		}

		return (end.getMillis() - begin.getMillis()) / H2MS;
	}

	/**
	 * 获取间隔的小时数, 不满1小时按一小时算
	 *
	 * @param date1
	 * @param date2
	 * @return the interval of hours
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static long getIntervalHoursByRoundUp(final Date date1, final Date date2)
	{
		if (date1 == null || date2 == null)
		{
			throw new IllegalArgumentException("Date date must be not null");
		}

		DateTime begin = null;
		DateTime end = null;
		if (date1.getTime() <= date2.getTime())
		{
			begin = newDateTime(date1);
			end = newDateTime(date2);
		}
		else
		{
			begin = newDateTime(date2);
			end = newDateTime(date1);
		}

		final long millisBetween = end.getMillis() - begin.getMillis();

		if (millisBetween % H2MS != 0)
		{
			return millisBetween / H2MS + 1;
		}

		return millisBetween / H2MS;
	}

	/**
	 * 获取间隔的分钟数, 不满1分钟不计入
	 *
	 * @param date1
	 * @param date2
	 * @return the interval of minutes
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static long getIntervalMinutes(final Date date1, final Date date2)
	{
		if (date1 == null || date2 == null)
		{
			throw new IllegalArgumentException("Date date must be not null");
		}

		DateTime begin = null;
		DateTime end = null;
		if (date1.getTime() <= date2.getTime())
		{
			begin = newDateTime(date1);
			end = newDateTime(date2);
		}
		else
		{
			begin = newDateTime(date2);
			end = newDateTime(date1);
		}

		return (end.getMillis() - begin.getMillis()) / M2MS;
	}

	/**
	 * 获取间隔的分钟数, 不满1分钟按一分钟算
	 *
	 * @param date1
	 * @param date2
	 * @return the interval of minutes
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static long getIntervalMinutesByRoundUp(final Date date1, final Date date2)
	{
		if (date1 == null || date2 == null)
		{
			throw new IllegalArgumentException("Date date must be not null");
		}

		DateTime begin = null;
		DateTime end = null;
		if (date1.getTime() <= date2.getTime())
		{
			begin = newDateTime(date1);
			end = newDateTime(date2);
		}
		else
		{
			begin = newDateTime(date2);
			end = newDateTime(date1);
		}

		final long millisBetween = end.getMillis() - begin.getMillis();

		if (millisBetween % M2MS != 0)
		{
			return millisBetween / M2MS + 1;
		}

		return millisBetween / M2MS;
	}

	/**
	 * 获取间隔的秒钟数, 不满1秒钟不计入
	 *
	 * @param date1
	 * @param date2
	 * @return the interval of seconds
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static long getIntervalSeconds(final Date date1, final Date date2)
	{
		if (date1 == null || date2 == null)
		{
			throw new IllegalArgumentException("Date date must be not null");
		}

		DateTime begin = null;
		DateTime end = null;
		if (date1.getTime() <= date2.getTime())
		{
			begin = newDateTime(date1);
			end = newDateTime(date2);
		}
		else
		{
			begin = newDateTime(date2);
			end = newDateTime(date1);
		}

		return (end.getMillis() - begin.getMillis()) / S2MS;
	}

	/**
	 * Return the last day of month. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @return the new date object
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date getLastDayOfMonth(final Date date)
	{
		final DateTime dateTime = newDateTime(date);
		final int hourOfDay = dateTime.getHourOfDay();
		final int minuteOfHour = dateTime.getMinuteOfHour();
		final int secondOfMinute = dateTime.getSecondOfMinute();
		return dateTime.dayOfMonth().withMaximumValue().withHourOfDay(hourOfDay).withMinuteOfHour(minuteOfHour)
				.withSecondOfMinute(secondOfMinute).toDate();
	}
	
	/**
	 * 
	 * 获取月份最后一天
	 * 
	 * @param year
	 * @param month
	 * @return int the last day of month
	 */
	public static int getLastDayOfMonth(int year, int month) {
		if (month < 1 || month > 12) 
		{
			return -1;
		}
		
		int retn = 0;
		if (month == 2) 
		{
			if (isLeapYear(year)) 
			{
				retn = 29;
			} 
			else 
			{
				retn = dayArray[(month - 1)];
			}
		} 
		else 
		{
			retn = dayArray[(month - 1)];
		}
		
		return retn;
	}

	/**
	 * @param date
	 * @return the date of last month
	 */
	public static Date getLastMonth(final Date date)
	{
		return addMonths(date, -1);
	}

	/**
	 * @param date
	 * @return the date of last year
	 */
	public static Date getLastYear(final Date date)
	{
		return addYears(date, -1);
	}

	/**
	 * If current time is 2018-03-31, will return 3
	 *
	 * @param date
	 *
	 * @return the month of year.
	 */
	public static int getMonth(final Date date)
	{
		return newDateTime(date).getMonthOfYear();
	}

	/**
	 * Return the next day. The original date object is unchanged.
	 *
	 * @param date
	 * @return the next day
	 */
	public static Date getNextDay(final Date date)
	{
		return addDays(date, 1);
	}

	/**
	 * @param date
	 * @return the date of next month
	 */
	public static Date getNextMonth(final Date date)
	{
		return addYears(date, 1);
	}

	/**
	 * @param date
	 * @return the date of next year
	 */
	public static Date getNextYear(final Date date)
	{
		return addYears(date, 1);
	}

	/**
	 * 计算计算到指定日期的年龄(虚岁)
	 *
	 * @param birthdate
	 * @param specifiedDate
	 *
	 * @return the norminal age
	 * @throws IllegalArgumentException
	 *            If birthdate after the specified date, or either date is <code>null</code>
	 */
	public static int getNorminalAge(final Date birthdate, final Date specifiedDate)
	{
		if (birthdate == null)
		{
			throw new IllegalArgumentException("Bithdate must be not null");
		}

		if (specifiedDate == null)
		{
			throw new IllegalArgumentException("Specified date must be not null");
		}

		if (birthdate.getTime() > specifiedDate.getTime())
		{
			throw new IllegalArgumentException("Bithdate can not after the specified date");
		}

		final LocalDate birthDateTime = new LocalDate(birthdate);
		final LocalDate specifiedDateTime = new LocalDate(specifiedDate);
		if (specifiedDateTime.getYear() == birthDateTime.getYear())
		{
			return 1;
		}

		final int interval = specifiedDateTime.getYear() - birthDateTime.getYear();
		if (specifiedDateTime.getMonthOfYear() < birthDateTime.getMonthOfYear())
		{
			return interval;
		}
		else if (specifiedDateTime.getMonthOfYear() > birthDateTime.getMonthOfYear())
		{
			return interval + 1;
		}
		else
		{
			if (specifiedDateTime.getDayOfMonth() < birthDateTime.getDayOfMonth())
			{
				return interval;
			}
			return interval + 1;
		}
	}

	/**
	 * @param date
	 * @return the previous day
	 */
	public static Date getPreviousDay(final Date date)
	{
		return addDays(date, -1);
	}

	/**
	 * @param date
	 * @return the year
	 */
	public static int getYear(final Date date)
	{
		return newDateTime(date).getYear();
	}

	/**
	 * Is the specified date after the current date comparing solely by millisecond.
	 *
	 * @param date
	 * @return true if the specified date is after the current date
	 * @throws IllegalArgumentException
	 *            if date is null
	 */
	public static boolean isAfterNow(final Date date)
	{
		if (date == null)
		{
			throw new IllegalArgumentException("date must be not null");
		}

		if (date.getTime() > System.currentTimeMillis())
		{
			return true;
		}

		return false;
	}

	/**
	 * Is the specified date before the current date comparing solely by millisecond.
	 *
	 * @param date
	 * @return true if the specified date is before the current date
	 * @throws IllegalArgumentException
	 *            if date is null
	 */
	public static boolean isBeforeNow(final Date date)
	{
		if (date == null)
		{
			throw new IllegalArgumentException("date must be not null");
		}
		if (date.getTime() < System.currentTimeMillis())
		{
			return true;
		}
		return false;
	}

	/**
	 * date1 equals date2 or not
	 *
	 * @param date1
	 * @param date2
	 * @return If date1 equals date2, return true, else return false
	 */
	public static boolean isEqual(final Date date1, final Date date2)
	{
		if (date1 == null && date2 == null)
		{
			return true;
		}

		if (date1 != null && date2 != null)
		{
			if (date1.getTime() == date2.getTime())
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * @param beginDate
	 * @param endDate
	 * @param specialedDate
	 * @return If the specialed date in the specialed time period, return true, else return false
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static boolean isInTimePeriod(final Date beginDate, final Date endDate, final Date specialedDate)
	{
		if (beginDate == null || endDate == null)
		{
			throw new IllegalArgumentException("Date must be not null");
		}

		final long beginMillis = beginDate.getTime();
		final long endMillis = endDate.getTime();
		if (beginMillis > endMillis)
		{
			throw new IllegalArgumentException("Begin date must be before end date");
		}

		final long specialedMillis = specialedDate.getTime();
		if (specialedMillis < beginMillis)
		{
			return false;
		}

		if (specialedMillis > endMillis)
		{
			return false;
		}

		return true;
	}

	/**
	 * @param date
	 * @return If the year of date is leap, return true, ortherwise, return false.
	 * @throws IllegalArgumentException
	 *            if date is <code>null</code>
	 */
	public static boolean isLeapYear(final Date date)
	{
		return isLeap(newDateTime(date).getYear(), 0);
	}

	/**
	 * @param year
	 * @return If the year is leap, return true, ortherwise, return false.
	 */
	public static boolean isLeapYear(final int year)
	{
		if (year > 0)
		{
			return isLeap(year, 0);
		}
		else if (year < 0)
		{
			return isLeap(-year, 1);
		}
		else
		{
			return false;
		}
	}

	/**
	 * <p>
	 * Checks if two date objects are on the same day ignoring time.
	 * </p>
	 *
	 * <p>
	 * 28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true. 28 Mar 2002 13:45 and 12 Mar 2002 13:45 would return
	 * false.
	 * </p>
	 *
	 * @param date1
	 *           the first date, not altered, not null
	 * @param date2
	 *           the second date, not altered, not null
	 * @return true if they represent the same day
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static boolean isSameDay(final Date date1, final Date date2)
	{
		final DateTime dateTime1 = newDateTime(date1);
		final DateTime dateTime2 = newDateTime(date2);

		if (dateTime1.getYear() != dateTime2.getYear())
		{
			return false;
		}

		if (dateTime1.getMonthOfYear() != dateTime2.getMonthOfYear())
		{
			return false;
		}

		if (dateTime1.getDayOfMonth() != dateTime2.getDayOfMonth())
		{
			return false;
		}

		return true;
	}

	/**
	 * 判断时间段(beginDate1, endDate1)和时间段(beginDate2, endDate2)是否有交集
	 *
	 * @param beginDate1
	 * @param endDate1
	 * @param beginDate2
	 * @param endDate2
	 * @return If time periods intersect, return true, else return false
	 * @throws IllegalArgumentException
	 *            if either date is <code>null</code>
	 */
	public static boolean isTimePeriodIntersect(final Date beginDate1, final Date endDate1, final Date beginDate2,
			final Date endDate2)
	{
		if (beginDate1 == null || endDate1 == null || beginDate2 == null || endDate2 == null)
		{
			throw new IllegalArgumentException("date must be not null");
		}

		final long beginMillis1 = beginDate1.getTime();
		final long endMillis1 = endDate1.getTime();
		if (beginMillis1 > endMillis1)
		{
			throw new IllegalArgumentException("Begin date must be before end date");
		}

		final long beginMillis2 = beginDate2.getTime();
		final long endMillis2 = endDate2.getTime();
		if (beginMillis2 > endMillis2)
		{
			throw new IllegalArgumentException("Begin date must be before end date");
		}

		if (endMillis2 < beginMillis1)
		{
			return false;
		}

		if (endMillis1 < beginMillis2)
		{
			return false;
		}

		return true;
	}

	/**
	 * valid dataStr
	 *
	 * <pre>
	 * yyyyMMdd
	 * </pre>
	 *
	 * <pre>
	 * yyyyMMddHHmmss
	 * </pre>
	 *
	 * <pre>
	 * yyyy - MM - dd
	 * </pre>
	 *
	 * <pre>
	 * yyyy-MM-dd HH:mm:ss
	 * </pre>
	 *
	 * <pre>
	 * TimeUtil.isValidDate("20170228")   == true
	 * TimeUtil.isValidDate("2017-02-28") == true
	 * TimeUtil.isValidDate("2017-02-29") == false
	 * </pre>
	 *
	 * @param date
	 * @return boolean
	 */
	public static boolean isValidDate(final String date)
	{
		if (StringUtil.isEmpty(date))
		{
			return false;
		}

		for (final Pattern pattern : PATTERN_FORMAT.values())
		{
			if (RegexUtil.match(pattern, date))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * valid dateStr for pattern yyyyMMdd yyyyMMddHHmmss yyyy-MM-dd yyyy-MM-dd HH:mm:ss
	 *
	 * <pre>
	 * TimeUtil.isValidDate("20170228","yyyyMMdd")   == true
	 * TimeUtil.isValidDate("2017-02-28","yyyy-MM-dd") == true
	 * TimeUtil.isValidDate("20170228","yyyy-MM-dd")   == false
	 * </pre>
	 *
	 * @param date
	 * @param pattern
	 * @return boolean
	 */
	public static boolean isValidDate(final String date, final String pattern)
	{
		if (StringUtil.isEmpty(date))
		{
			return false;
		}

		if (PATTERN_FORMAT.get(pattern) == null)
		{
			return false;
		}

		return RegexUtil.match(PATTERN_FORMAT.get(pattern), date);
	}

	/**
	 * Convert a millis into a Date object.
	 *
	 * @param millis
	 *           the millis to convert to a Calendar
	 * @return the created Date
	 */
	public static Date parseDate(final long millis)
	{
		return new DateTime(millis, DEFAULT_DATETIME_ZONE).toDate();
	}
	
	/**
	 * <p>
	 * type represent a date-time format. This method used to parse a date by the specialed format
	 *
	 * <p>
	 * the value of type are
	 *
	 * <pre>
	 * <code>年到日</code>
	 * public static final int YEAR_TO_DAY = 13;
	 * </pre>
	 *
	 * @param date
	 *           the date to convert to a Date
	 * @return the created Date
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date parseDate(final Date date)
	{
		return parseDate(date, YEAR_TO_DAY);
	}

	/**
	 * <p>
	 * type represent a date-time format. This method used to parse a date by the specialed format
	 *
	 * <p>
	 * the value of type are
	 *
	 * <pre>
	 * <code>年到年</code>
	 * public static final int YEAR_TO_YEAR = 11;
	 * </pre>
	 *
	 * <pre>
	 * <code>年到月</code>
	 * public static final int YEAR_TO_MONTH = 12;
	 * </pre>
	 *
	 * <pre>
	 * <code>年到日</code>
	 * public static final int YEAR_TO_DAY = 13;
	 * </pre>
	 *
	 * <pre>
	 * <code>年到小时</code>
	 * public static final int YEAR_TO_HOUR = 14;
	 * </pre>
	 *
	 * <pre>
	 * <code>年到分</code>
	 * public static final int YEAR_TO_MINUTE = 15;
	 * </pre>
	 *
	 * <pre>
	 * <code>年到秒</code>
	 * public static final int YEAR_TO_SECOND = 16;
	 * </pre>
	 *
	 * <pre>
	 * <code>年到毫秒</code>
	 * public static final int YEAR_TO_MILLISECOND = 17;
	 * <pre>
	 * <code>月到月</code>
	 * public static final int MONTH_TO_MONTH = 22;
	 * </pre>
	 *
	 * <pre>
	 * <code>月到日</code>
	 * public static final int MONTH_TO_DAY = 23;
	 * </pre>
	 *
	 * <pre>
	 * <code>月到小时</code>
	 * public static final int MONTH_TO_HOUR = 24;
	 * </pre>
	 *
	 * <pre>
	 * <code>月到分</code>
	 * public static final int MONTH_TO_MINUTE = 25;
	 * </pre>
	 *
	 * <pre>
	 * <code>月到秒</code>
	 * public static final int MONTH_TO_SECOND = 26;
	 * </pre>
	 *
	 * <pre>
	 * <code>月到毫秒</code>
	 * public static final int MONTH_TO_MILLISECOND = 27;
	 * </pre>
	 *
	 * <pre>
	 * <code>日到日</code>
	 * public static final int DAY_TO_DAY = 33;
	 * </pre>
	 *
	 * <pre>
	 * <code>日到小时</code>
	 * public static final int DAY_TO_HOUR = 34;
	 * </pre>
	 *
	 * <pre>
	 * <code>日到分</code>
	 * public static final int DAY_TO_MINUTE = 35;
	 * </pre>
	 *
	 * <pre>
	 * <code>日到秒</code>
	 * public static final int DAY_TO_SECOND = 36;
	 * </pre>
	 *
	 * <pre>
	 * <code>日到毫秒</code>
	 * public static final int DAY_TO_MILLISECOND = 37;
	 * </pre>
	 *
	 * <pre>
	 * <code>小时到小时</code>
	 * public static final int HOUR_TO_HOUR = 44;
	 * </pre>
	 *
	 * <pre>
	 * <code>小时到分</code>
	 * public static final int HOUR_TO_MINUTE = 45;
	 * </pre>
	 *
	 * <pre>
	 * <code>小时到秒</code>
	 * public static final int HOUR_TO_SECOND = 46;
	 * </pre>
	 *
	 * <pre>
	 * <code>小时到毫秒</code>
	 * public static final int HOUR_TO_MILLISECOND = 47;
	 * </pre>
	 *
	 * <pre>
	 * <code>分到分</code>
	 * public static final int MINUTE_TO_MINUTE = 55;
	 * </pre>
	 *
	 * <pre>
	 * <code>分到秒</code>
	 * public static final int MINUTE_TO_SECOND = 56;
	 * </pre>
	 *
	 * <pre>
	 * <code>分到毫秒</code>
	 * public static final int MINUTE_TO_MILLISECOND = 57;
	 * </pre>
	 *
	 * <pre>
	 * <code>秒到秒</code>
	 * public static final int SECOND_TO_SECOND = 66;
	 * </pre>
	 *
	 * <pre>
	 * <code>秒到毫秒</code>
	 * public static final int SECOND_TO_MILLISECOND = 67;
	 * </pre>
	 *
	 * <pre>
	 * <code>毫秒到毫秒</code>
	 * public static final int MILLISECOND_TO_MILLISECOND = 77;
	 * </pre>
	 *
	 * @param date
	 *           the date to convert to a Date
	 * @param type
	 * @return the created Date
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date parseDate(final Date date, final int type)
	{
		final DateTimeFormatter formatter = DATETIME_FORMATTERS.get(type);
		if (formatter == null)
		{
			throw new IllegalArgumentException("Unknown type " + type);
		}
		
		final String dateStr = DATETIME_SIMPLE_FORMATTERS.get(type).format(date);
		return formatter.parseLocalDateTime(dateStr).toDate();
	}

	/**
	 * <p>
	 * type represent a date-time format. This method used to parse a date by the specialed format
	 *
	 * <p>
	 * the value of type are
	 *
	 * <pre>
	 * <code>年到年</code>
	 * public static final int YEAR_TO_YEAR = 11;
	 * </pre>
	 *
	 * <pre>
	 * <code>年到月</code>
	 * public static final int YEAR_TO_MONTH = 12;
	 * </pre>
	 *
	 * <pre>
	 * <code>年到日</code>
	 * public static final int YEAR_TO_DAY = 13;
	 * </pre>
	 *
	 * <pre>
	 * <code>年到小时</code>
	 * public static final int YEAR_TO_HOUR = 14;
	 * </pre>
	 *
	 * <pre>
	 * <code>年到分</code>
	 * public static final int YEAR_TO_MINUTE = 15;
	 * </pre>
	 *
	 * <pre>
	 * <code>年到秒</code>
	 * public static final int YEAR_TO_SECOND = 16;
	 * </pre>
	 *
	 * <pre>
	 * <code>年到毫秒</code>
	 * public static final int YEAR_TO_MILLISECOND = 17;
	 * <pre>
	 * <code>月到月</code>
	 * public static final int MONTH_TO_MONTH = 22;
	 * </pre>
	 *
	 * <pre>
	 * <code>月到日</code>
	 * public static final int MONTH_TO_DAY = 23;
	 * </pre>
	 *
	 * <pre>
	 * <code>月到小时</code>
	 * public static final int MONTH_TO_HOUR = 24;
	 * </pre>
	 *
	 * <pre>
	 * <code>月到分</code>
	 * public static final int MONTH_TO_MINUTE = 25;
	 * </pre>
	 *
	 * <pre>
	 * <code>月到秒</code>
	 * public static final int MONTH_TO_SECOND = 26;
	 * </pre>
	 *
	 * <pre>
	 * <code>月到毫秒</code>
	 * public static final int MONTH_TO_MILLISECOND = 27;
	 * </pre>
	 *
	 * <pre>
	 * <code>日到日</code>
	 * public static final int DAY_TO_DAY = 33;
	 * </pre>
	 *
	 * <pre>
	 * <code>日到小时</code>
	 * public static final int DAY_TO_HOUR = 34;
	 * </pre>
	 *
	 * <pre>
	 * <code>日到分</code>
	 * public static final int DAY_TO_MINUTE = 35;
	 * </pre>
	 *
	 * <pre>
	 * <code>日到秒</code>
	 * public static final int DAY_TO_SECOND = 36;
	 * </pre>
	 *
	 * <pre>
	 * <code>日到毫秒</code>
	 * public static final int DAY_TO_MILLISECOND = 37;
	 * </pre>
	 *
	 * <pre>
	 * <code>小时到小时</code>
	 * public static final int HOUR_TO_HOUR = 44;
	 * </pre>
	 *
	 * <pre>
	 * <code>小时到分</code>
	 * public static final int HOUR_TO_MINUTE = 45;
	 * </pre>
	 *
	 * <pre>
	 * <code>小时到秒</code>
	 * public static final int HOUR_TO_SECOND = 46;
	 * </pre>
	 *
	 * <pre>
	 * <code>小时到毫秒</code>
	 * public static final int HOUR_TO_MILLISECOND = 47;
	 * </pre>
	 *
	 * <pre>
	 * <code>分到分</code>
	 * public static final int MINUTE_TO_MINUTE = 55;
	 * </pre>
	 *
	 * <pre>
	 * <code>分到秒</code>
	 * public static final int MINUTE_TO_SECOND = 56;
	 * </pre>
	 *
	 * <pre>
	 * <code>分到毫秒</code>
	 * public static final int MINUTE_TO_MILLISECOND = 57;
	 * </pre>
	 *
	 * <pre>
	 * <code>秒到秒</code>
	 * public static final int SECOND_TO_SECOND = 66;
	 * </pre>
	 *
	 * <pre>
	 * <code>秒到毫秒</code>
	 * public static final int SECOND_TO_MILLISECOND = 67;
	 * </pre>
	 *
	 * <pre>
	 * <code>毫秒到毫秒</code>
	 * public static final int MILLISECOND_TO_MILLISECOND = 77;
	 * </pre>
	 *
	 * @param dateStr
	 *           the dateStr to convert to a Date
	 * @param type
	 * @return the created Date
	 * @throws IllegalArgumentException
	 *            if the dateStr is null
	 */
	public static Date parseDate(final String dateStr, final int type)
	{
		final DateTimeFormatter formatter = DATETIME_FORMATTERS.get(type);
		if (formatter == null)
		{
			throw new IllegalArgumentException("Unknown type " + type);
		}
		return parseDate(parseDate(dateStr), type);
	}

	/**
	 * <p>
	 * Parses a string representing a date, supported formats as follow
	 *
	 * <pre>
	 * yyyy-MM-dd HH:mm:ss
	 * </pre>
	 *
	 * <pre>
	 * yyyy-MM-dd HH:mm:ss
	 * </pre>
	 *
	 * <pre>
	 * yyyy - MM - dd
	 * </pre>
	 *
	 * <pre>
	 * yyyy - MM
	 * </pre>
	 *
	 * <pre>
	 * yyyy - MM - dd
	 * </pre>
	 *
	 * <pre>
	 * HH:mm:ss
	 * </pre>
	 *
	 * <pre>
	 * HH:mm:ss
	 * </pre>
	 *
	 * <pre>
	 * yyyy-MM-dd HH:mm
	 * </pre>
	 *
	 * <pre>
	 * yyyy-MM-dd HH
	 * </pre>
	 *
	 * <pre>
	 * yyyyMMddHHmmss
	 * </pre>
	 *
	 * <pre>
	 * yyyyMMdd
	 * </pre>
	 *
	 * <pre>
	 * yyyyMM
	 * </pre>
	 *
	 * <pre>
	 * yyyy
	 * </pre>
	 *
	 * <pre>
	 * yyyyMMddHHmm
	 * </pre>
	 *
	 * <pre>
	 * yyyyMMddHH
	 * </pre>
	 * </p>
	 *
	 * <p>
	 * If no parse patterns match, a ParseException is thrown.
	 * </p>
	 * The parser will be lenient toward the parsed date.
	 *
	 * @param dateStr
	 *           the date to parse, not null
	 *
	 * @return the parsed date
	 * @throws IllegalArgumentException
	 *            if the date string or pattern array is null
	 */
	public static Date parseDate(final String dateStr)
	{
		return DATETIME_FORMATTER.parseLocalDateTime(dateStr).toDate();
	}

	/**
	 * <p>
	 * Parses a string representing a date by the specified format.
	 * </p>
	 *
	 * <p>
	 * If no parse patterns match, a ParseException is thrown.
	 * </p>
	 * The parser will be lenient toward the parsed date.
	 *
	 * @param dateStr
	 *           the date to parse, not null
	 * @param pattern
	 *           the date pattern patterns to use, see SimpleDateFormat, not null
	 *
	 * @return the parsed date
	 * @throws IllegalArgumentException
	 *            if the date string or pattern array is null
	 */
	public static Date parseDate(final String dateStr, final String pattern)
	{
		return DateTimeFormat.forPattern(pattern).parseLocalDateTime(dateStr).toDate();
	}

	/**
	 * <p>
	 * Parses a string representing a date by the default format 'yyyy-MM-dd HH:mm:ss'.
	 * </p>
	 *
	 * <p>
	 * If no parse patterns match, a ParseException is thrown.
	 * </p>
	 * The parser will be lenient toward the parsed date.
	 *
	 * @param dateStr
	 *           the date to parse, not null
	 *
	 * @return the parsed date
	 * @throws IllegalArgumentException
	 *            if the date string or pattern array is null
	 */
	public static Date parseDateWithDefaultFormat(final String dateStr)
	{
		return DateTimeFormat.forPattern(ISO_DATETIME_FORMAT).parseLocalDateTime(dateStr).toDate();
	}

	/**
	 * Sets the day of month field to a date returning a new object. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @param amount
	 *           the amount to set
	 * @return a new Date object set with the specified value
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date setDays(final Date date, final int amount)
	{
		return newDateTime(date).withDayOfMonth(amount).toDate();
	}

	/**
	 * Sets the hours field to a date returning a new object. Hours range from 0-23. The original date object is
	 * unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @param amount
	 *           the amount to set
	 * @return a new Date object set with the specified value
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date setHours(final Date date, final int amount)
	{
		return newDateTime(date).withHourOfDay(amount).toDate();
	}

	/**
	 * Sets the miliseconds field to a date returning a new object. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @param amount
	 *           the amount to set
	 * @return a new Date object set with the specified value
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date setMilliseconds(final Date date, final int amount)
	{
		return newDateTime(date).withMillis(amount).toDate();
	}

	/**
	 * Sets the minute field to a date returning a new object. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @param amount
	 *           the amount to set
	 * @return a new Date object set with the specified value
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date setMinutes(final Date date, final int amount)
	{
		return newDateTime(date).withMinuteOfHour(amount).toDate();
	}

	/**
	 * Sets the months field to a date returning a new object. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @param amount
	 *           the amount to set
	 * @return a new Date object set with the specified value
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date setMonths(final Date date, final int amount)
	{
		return newDateTime(date).withMonthOfYear(amount).toDate();
	}

	/**
	 * Sets the seconds field to a date returning a new object. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @param amount
	 *           the amount to set
	 * @return a new Date object set with the specified value
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date setSeconds(final Date date, final int amount)
	{
		return newDateTime(date).withSecondOfMinute(amount).toDate();
	}

	/**
	 * Sets the years field to a date returning a new object. The original date object is unchanged.
	 *
	 * @param date
	 *           the date, not null
	 * @param amount
	 *           the amount to set
	 * @return a new Date object set with the specified value
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	public static Date setYears(final Date date, final int amount)
	{
		return newDateTime(date).withYear(amount).toDate();
	}

	/**
	 * @param year
	 * @param mod
	 * @return the year is leap or not
	 */
	private static boolean isLeap(final int year, final int mod)
	{
		if (((4 - 1) & year) == mod && year % 100 != mod)
		{
			return true;
		}

		if (year % 400 == mod)
		{
			return true;
		}

		return false;
	}

	/**
	 * Create dateTime using default time zone.
	 *
	 * @return the created dateTime
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	private static DateTime newDateTime()
	{
		return new DateTime(DEFAULT_DATETIME_ZONE);
	}

	/**
	 * Create dateTime by date and default time zone.
	 *
	 * @param date
	 * @return the created dateTime
	 * @throws IllegalArgumentException
	 *            if the date is null
	 */
	private static DateTime newDateTime(final Date date)
	{
		if (date == null)
		{
			throw new IllegalArgumentException("date must be not null");
		}

		return new DateTime(date, DEFAULT_DATETIME_ZONE);
	}
}
