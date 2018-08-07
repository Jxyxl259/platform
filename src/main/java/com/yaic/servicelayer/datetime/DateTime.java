package com.yaic.servicelayer.datetime;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.yaic.servicelayer.util.StringUtil;


/**
 * DateTime数据类型. <br>
 */
public class DateTime extends Date implements Serializable
{
	private static final long serialVersionUID = 3014814714315968010L;

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
	
	/**
	 * <code>秒到秒</code>
	 */
	public static final int SECOND_TO_SECOND = 66;
	
	/**
	 * <code>秒到毫秒</code>
	 */
	public static final int SECOND_TO_MILLISECOND = 67;
	
	/**
	 * <code>毫秒到毫秒</code>
	 */
	public static final int MILLISECOND_TO_MILLISECOND = 77;
	
	/**
	 * <code>日期分割符</code>
	 */
	private static String delimiter = "-"; 
	
	/**
	 * <code>日期格式类型</code>
	 */
	private int type = 0;
	
	/**
	 * <code>是否有值</code>
	 */
	private boolean empty = true; 
	
	/**
	 * <code>毫秒</code>
	 */
	private static final long MILLS_ONE_SECOND = 1000;
	
	/**
	 * <code>分钟</code>
	 */
	private static final long MILLS_ONE_MINUTE = 60 * MILLS_ONE_SECOND;
	
	/**
	 * <code>小时</code>
	 */
	private static final long MILLS_ONE_HOUR = 60 * MILLS_ONE_MINUTE;
	
	/**
	 * <code>天</code>
	 */
	private static final long MILLS_ONE_DAY = 24 * MILLS_ONE_HOUR;
	
	/**
	 * <code>年月日</code>
	 */
	public static final int YMD_FORMAT = 0; 
	
	/**
	 * <code>日月年</code>
	 */
	public static final int DMY_FORMAT = 1;// 
	
	/**
	 * <code>月日年</code>
	 */
	public static final int MDY_FORMAT = 2;
	
	/**
	 * <code>默认年月日</code>
	 */
	private static int dateFormatType = YMD_FORMAT; 

	/**
	 * DateTime的构造函数,不含任何值. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 * DateTime startDate = new DateTime();
	 * </pre>
	 */
	public DateTime()
	{
		this((Date) null);
	}

	/**
	 * DateTime的构造函数. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 * DateTime startDate = new DateTime(new java.util.Date());
	 * &lt;br&gt;
	 * DateTime startDate = new DateTime((java.util.Date) null);
	 * &lt;br&gt;
	 * </pre>
	 *
	 * @param date
	 *           一个java.util.Date对象
	 */
	public DateTime(final Date date)
	{
		this(date, YEAR_TO_DAY);
	}

	/**
	 * DateTime的构造函数. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 * DateTime startDate = new DateTime(&quot;2004/02/01&quot;);
	 * &lt;br&gt;
	 * DateTime startDate = new DateTime(&quot;2004-2-1&quot;);
	 * &lt;br&gt;
	 * DateTime startDate = new DateTime((String) null);
	 * &lt;br&gt;
	 * </pre>
	 *
	 * @param dateString
	 *           一个表示日期的字符串
	 */
	public DateTime(final String dateString)
	{
		this(dateString, YEAR_TO_DAY);
	}

	/**
	 * DateTime的构造函数. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 * DateTime startDate = new DateTime(&quot;2004-2-1&quot;, DateTime.YEAR_TO_DAY);
	 * &lt;br&gt;
	 * DateTime startDate = new DateTime(&quot;2004-02-01 11:32&quot;, DateTime.YEAR_TO_MINUTE);
	 * &lt;br&gt;
	 * DateTime startDate = new DateTime(&quot;2004/02/01 11:32&quot;, DateTime.YEAR_TO_MINUTE);
	 * &lt;br&gt;
	 * </pre>
	 *
	 * @param dateString
	 *           一个表示日期和时间的字符串
	 * @param type
	 *           DateTime类型
	 */
	public DateTime(final String dateString, final int type)
	{
		setTime(dateString, type);
	}

	/**
	 * 设置时间
	 *
	 * @param dateTimeString
	 *           日期时间字符串
	 * @param type
	 *           日期时间类型
	 */
	private void setTime(final String dateTimeString, final int type)
	{
		if (StringUtil.isEmpty(dateTimeString))
		{
			empty = true;
			return;
		}
		try
		{
			final String str = correct(dateTimeString);
			String pattern = getDateFormatPattern(type);
			pattern = StringUtil.replace(pattern, delimiter, "");
			final Date date = new SimpleDateFormat(pattern).parse(str);
			this.setTime(date.getTime());
			empty = false;
			this.type = type;
			check(this, type);
		}
		catch (final ParseException e)
		{
			empty = true;
			throw new IllegalArgumentException("unable to parse " + dateTimeString);
		}
	}

	/**
	 * 设置时间
	 *
	 * @param date
	 *           日期对象
	 * @param type
	 *           日期时间类型
	 */
	private void setTime(final Date date, final int type)
	{
		if (date == null)
		{
			empty = true;
			return;
		}
		String pattern = getDateFormatPattern(type);
		pattern = StringUtil.replace(pattern, delimiter, "");
		final String dateStr = new SimpleDateFormat(pattern).format(date);
		this.setTime(dateStr, type);
	}



	/**
	 * DateTime的构造函数. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 * DateTime startDate = new DateTime(new Date(), DateTime.YEAR_TO_DAY);
	 * &lt;br&gt;
	 * </pre>
	 *
	 * @param date
	 *           日期对象
	 * @param type
	 *           DateTime类型
	 */
	public DateTime(final Date date, final int type)
	{
		setTime(date, type);
	}

	/**
	 * DateTime的构造函数. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 * DateTime startDate = new DateTime(new DateTime(new Date(),
	 * 		DateTime.YEAR_TO_SECOND), DateTime.YEAR_TO_DAY);
	 * &lt;br&gt;
	 * </pre>
	 *
	 * @param dateTime
	 *           日期时间对象
	 * @param type
	 *           DateTime类型
	 */
	public DateTime(final DateTime dateTime, final int type)
	{
		this((Date) dateTime, type);
	}

	/**
	 * 返回日期时间对象中的年. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 * DateTime dt = new DateTime(&quot;2004-02-03 09:34:52&quot;, DateTime.YEAR_TO_SECOND);
	 * &lt;br&gt;
	 *
	 *                 dt.getYear() 返回 2004
	 *
	 *
	 * </pre>
	 *
	 * @return 日期时间对象中的年
	 */
	@Override
	@Deprecated
	public int getYear()
	{
		return super.getYear() + 1900;
	}

	/**
	 * 返回日期时间对象中的月. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 * DateTime dt = new DateTime(&quot;2004-02-03 09:34:52&quot;, DateTime.YEAR_TO_SECOND);
	 * &lt;br&gt;
	 *
	 *                 dt.getMonth() 返回 2
	 *
	 *
	 * </pre>
	 *
	 * @return 日期时间对象中的月
	 */
	@Override
	@Deprecated
	public int getMonth()
	{
		return super.getMonth() + 1;
	}

	/**
	 * 返回日期时间对象中的日. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 * DateTime dt = new DateTime(&quot;2004-02-03 09:34:52&quot;, DateTime.YEAR_TO_SECOND);
	 * &lt;br&gt;
	 *
	 *                 dt.getDay() 返回 3
	 *
	 *
	 * </pre>
	 *
	 * @return 日期时间对象中的日
	 */
	@Override
	@Deprecated
	public int getDay()
	{
		return super.getDate();
	}

	/**
	 * 返回日期时间对象中的时. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 * DateTime dt = new DateTime(&quot;2004-02-03 09:34:52&quot;, DateTime.YEAR_TO_SECOND);
	 * &lt;br&gt;
	 *
	 *                 dt.getHour() 返回 9
	 *
	 *
	 * </pre>
	 *
	 * @return 日期时间对象中的时
	 */
	@Deprecated
	public int getHour()
	{
		return super.getHours();
	}

	/**
	 * 返回日期时间对象中的分. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 * DateTime dt = new DateTime(&quot;2004-02-03 09:34:52&quot;, DateTime.YEAR_TO_SECOND);
	 * &lt;br&gt;
	 *
	 *                 dt.getMinute() 返回 34
	 *
	 *
	 * </pre>
	 *
	 * @return 日期时间对象中的分
	 */
	@Deprecated
	public int getMinute()
	{
		return super.getMinutes();
	}

	/**
	 * 返回日期时间对象中的秒. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 * DateTime dt = new DateTime(&quot;2004-02-03 09:34:52&quot;, DateTime.YEAR_TO_SECOND);
	 * &lt;br&gt;
	 *
	 *                 dt.getSecond() 返回 52
	 *
	 *
	 * </pre>
	 *
	 * @return 日期时间对象中的秒
	 */
	@Deprecated
	public int getSecond()
	{
		return super.getSeconds();
	}

	/**
	 * 设置DateTime类默认采用的日期分割符，默认为'-'
	 *
	 * @param delimiter
	 *           日期分割符
	 */
	public static void setDateDelimiter(final String delimiter)
	{
		DateTime.delimiter = delimiter;
	}

	/**
	 * 获取DateTime类默认采用的日期分割符，默认为'-'
	 *
	 * @return 日期分割符
	 */
	public static String getDateDelimiter()
	{
		return delimiter;
	}

	private static String getRealDateFomat()
	{
		String pattern = "";
		switch (dateFormatType)
		{
			case YMD_FORMAT:
				pattern = "yyyy" + delimiter + "MM" + delimiter + "dd";
				break;
			case MDY_FORMAT:
				pattern = "MM" + delimiter + "dd" + delimiter + "yyyy";
				break;
			case DMY_FORMAT:
				pattern = "dd" + delimiter + "MM" + delimiter + "yyyy";
				break;
			default:
				throw new IllegalArgumentException("dateFormatType can't be " + dateFormatType);
		}
		return pattern;
	}



	/**
	 * 得到DateFormatPattern
	 *
	 * @param pattern
	 *           String类型
	 * @return type对应的DateFormatPattern
	 */
	private static String getDateFormatPattern(final int type)
	{
		String pattern = "";
		switch (type)
		{
			// 从年开始
			case YEAR_TO_YEAR:
				pattern = "yyyy";
				break;
			case YEAR_TO_MONTH:
				if (dateFormatType == YMD_FORMAT)
				{
					pattern = "yyyy" + delimiter + "MM";
				}
				else
				{
					pattern = "MM" + delimiter + "yyyy";
				}
				break;
			case YEAR_TO_DAY:
				pattern = getRealDateFomat();
				break;
			case YEAR_TO_HOUR:
				pattern = getRealDateFomat() + " HH";
				break;
			case YEAR_TO_MINUTE:
				pattern = getRealDateFomat() + " HH:mm";
				break;
			case YEAR_TO_SECOND:
				pattern = getRealDateFomat() + " HH:mm:ss";
				break;
			case YEAR_TO_MILLISECOND:
				pattern = getRealDateFomat() + " HH:mm:ss.SSS";
				break;
			// 从月开始
			case MONTH_TO_MONTH:
				pattern = "MM";
				break;
			case MONTH_TO_DAY:
				pattern = "MM" + delimiter + "dd";
				break;
			case MONTH_TO_HOUR:
				pattern = "MM" + delimiter + "dd HH";
				break;
			case MONTH_TO_MINUTE:
				pattern = "MM" + delimiter + "dd HH:mm";
				break;
			case MONTH_TO_SECOND:
				pattern = "MM" + delimiter + "dd HH:mm:ss";
				break;
			case MONTH_TO_MILLISECOND:
				pattern = "MM" + delimiter + "dd HH:mm:ss.SSS";
				break;
			// 从日开始
			case DAY_TO_DAY:
				pattern = "dd";
				break;
			case DAY_TO_HOUR:
				pattern = "dd HH";
				break;
			case DAY_TO_MINUTE:
				pattern = "dd HH:mm";
				break;
			case DAY_TO_SECOND:
				pattern = "dd HH:mm:ss";
				break;
			case DAY_TO_MILLISECOND:
				pattern = "dd HH:mm:ss.SSS";
				break;
			// 从小时开始
			case HOUR_TO_HOUR:
				pattern = "HH";
				break;
			case HOUR_TO_MINUTE:
				pattern = "HH:mm";
				break;
			case HOUR_TO_SECOND:
				pattern = "HH:mm:ss";
				break;
			case HOUR_TO_MILLISECOND:
				pattern = "HH:mm:ss.SSS";
				break;
			// 从分开始
			case MINUTE_TO_MINUTE:
				pattern = "mm";
				break;
			case MINUTE_TO_SECOND:
				pattern = "mm:ss";
				break;
			case MINUTE_TO_MILLISECOND:
				pattern = "mm:ss.SSS";
				break;
			// 从秒开始
			case SECOND_TO_SECOND:
				pattern = "ss";
				break;
			case SECOND_TO_MILLISECOND:
				pattern = "ss.SSS";
				break;
			// 从毫秒开始
			case MILLISECOND_TO_MILLISECOND:
				pattern = "SSS";
				break;
			default:
				throw new IllegalArgumentException(type + " is not support");
		}
		return pattern;
	}

	/**
	 * 返回DateTime对象的字符串值. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 *
	 *                 new DateTime().toString() 返回 &quot;&quot;
	 *
	 *
	 * </pre>
	 *
	 * @return 一个表示日期和/或时间的字符串
	 */
	@Override
	public String toString()
	{
		if (empty == true)
		{
			return "";
		}
		return toString(getType());
	}

	/**
	 * 根据类型返回DateTime对象的字符串值. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 *
	 *                 new DateTime().toString() 返回 &quot;&quot;
	 *
	 *
	 * </pre>
	 *
	 * @param type
	 *           类型
	 * @return 根据类型返回一个表示日期和/或时间的字符串
	 */
	public String toString(final int type)
	{
		if (empty == true)
		{
			return "";
		}
		// check(this, type);
		return new SimpleDateFormat(getDateFormatPattern(type)).format(this);
	}

	/**
	 * 返回当前日期和时间. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 * DateTime dt = DateTime().current();
	 * </pre>
	 *
	 * @return 返回表示当前日期和时间的DateTime对象
	 */
	public static DateTime current()
	{
		return new DateTime(new Date(), YEAR_TO_MILLISECOND);
	}


	/**
	 * 返回一个新的日期对象，新的日期对象是当前日期对象加上指定天数之后的日期. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 *
	 *                 示例1：
	 *                 DateTime dt = new DateTime(&quot;2004/02/01&quot;);
	 *                 dt.addDate(5) 返回 new DateTime(&quot;2004/02/06&quot;);
	 *
	 *
	 * &lt;br&gt;
	 *
	 *                 示例2：
	 *                 DateTime dt = new DateTime(&quot;2004/02/01&quot;);
	 *                 dt.addDate(-3) 返回 new DateTime(&quot;2004/01/29&quot;);
	 *
	 *
	 * &lt;br&gt;
	 *
	 *                 示例3：
	 *                 DateTime dt = new DateTime(&quot;2004/02/01&quot;);
	 *                 dt.addDay(0) 返回 new DateTime(&quot;2004/02/01&quot;);
	 *
	 *
	 * &lt;br&gt;
	 * </pre>
	 *
	 * @param day
	 *           日期数
	 * @return 新的日期对象
	 */
	public DateTime addDay(final int day)
	{
		final DateTime dt = new DateTime(this.toString());
		dt.setTime(getTime() + day * MILLS_ONE_DAY);
		return dt;
	}

	/**
	 * 返回一个新的日期对象，新的日期对象是当前日期对象加上指定月数之后的日期. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 *
	 *                 DateTime dt = new DateTime(&quot;2004/02/14&quot;);
	 *                 dt.addMonth(5) 返回 new DateTime(&quot;2004/07/14&quot;);
	 *
	 *
	 * </pre>
	 *
	 * @param iMonth
	 *           月数
	 * @return 新的日期对象
	 */
	public DateTime addMonth(final int iMonth)
	{
		final DateTime dt = (DateTime) this.clone();
		final GregorianCalendar gval = new GregorianCalendar();
		gval.setTime(dt);
		gval.add(Calendar.MONTH, iMonth);
		dt.setTime(gval.getTime().getTime());
		return dt;
	}

	/**
	 * 返回一个新的日期对象，新的日期对象是当前日期对象加上指定年数之后的日期. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 *
	 *                 示例1：
	 *                 DateTime dt = new DateTime(&quot;2004/02/14&quot;);
	 *                 dt.addYear(1) 返回 new DateTime(&quot;2005/02/14&quot;);
	 *
	 *
	 * </pre>
	 *
	 * @param iYear
	 *           年数
	 * @return 新的日期对象
	 */
	public DateTime addYear(final int iYear)
	{
		final DateTime dt = (DateTime) this.clone();
		final GregorianCalendar gval = new GregorianCalendar();
		gval.setTime(dt);
		gval.add(Calendar.YEAR, iYear);
		dt.setTime(gval.getTime().getTime());
		return dt;
	}

	/**
	 * 返回一个新的日期对象，新的日期对象是当前日期对象加上指定小时数之后的日期. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 *
	 *                 示例1：
	 *                 DateTime dt = new DateTime(&quot;2004/02/01 00&quot;,DateTime.YEAR_TO_HOUR);
	 *                 dt.addDate(5) 返回 new DateTime(&quot;2004/02/01 05&quot;,DateTime.YEAR_TO_HOUR);
	 *
	 *
	 * &lt;br&gt;
	 *
	 *                 示例2：
	 *                 DateTime dt = new DateTime(&quot;2004/02/01 00&quot;,DateTime.YEAR_TO_HOUR);
	 *                 dt.addDate(-3) 返回 new DateTime(&quot;2004/02/31 21&quot;,DateTime.YEAR_TO_HOUR);
	 *
	 *
	 * &lt;br&gt;
	 *
	 *                 示例3：
	 *                 DateTime dt = new DateTime(&quot;2004/02/01 00&quot;,DateTime.YEAR_TO_HOUR);
	 *                 dt.addDate(0) 返回 new DateTime(&quot;2004/02/01 00&quot;,DateTime.YEAR_TO_HOUR);
	 *
	 *
	 * &lt;br&gt;
	 * </pre>
	 *
	 * @param hour
	 *           小时数
	 * @return 新的日期对象
	 */
	public DateTime addHour(final int hour)
	{
		final DateTime dt = (DateTime) this.clone();
		dt.setTime(getTime() + hour * MILLS_ONE_HOUR);
		return dt;
	}

	/**
	 * 返回一个新的日期对象，新的日期对象是当前日期对象加上指定分钟数之后的日期. <br>
	 * <br>
	 *
	 * @param minute
	 *           分钟数
	 * @return 新的日期对象
	 */
	public DateTime addMinute(final int minute)
	{
		final DateTime dt = (DateTime) this.clone();
		dt.setTime(getTime() + minute * MILLS_ONE_MINUTE);
		return dt;
	}

	/**
	 * 返回日期类型属性
	 *
	 * @return 返回日期类型属性 type.
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * 修正数据.即将'/','-'去掉
	 *
	 * @param dateString
	 *           传入日期串
	 * @return 修正后的日期串
	 */
	private static String correct(final String dateString)
	{
		String resultString = dateString;
		if (dateString.indexOf('/') > -1)
		{
			resultString = StringUtil.replace(dateString, "/", "");
		}
		if (dateString.indexOf('-') > -1)
		{
			resultString = StringUtil.replace(dateString, "-", "");
		}
		return resultString;
	}

	/**
	 * 判断是否有值. <br>
	 * <br>
	 * <b>示例: </b>
	 *
	 * <pre>
	 *
	 *                 DateTime startDate = new DateTime();
	 *                 startDate.isEmpty() 返回true
	 *                 DateTime endDate = new DateTime(new java.util.Date());
	 *                 endDate.isEmpty() 返回false
	 *
	 * </pre>
	 *
	 * @return 返回属性 empty.
	 */
	public boolean isEmpty()
	{
		return empty;
	}

	/**
	 * 检查数据
	 *
	 * @param dateTime
	 *           datetime
	 * @param type
	 *           类型
	 */
	private void check(final DateTime dateTime, final int type)
	{
		if (dateTime.isEmpty())
		{
			throw new IllegalStateException("DateTime is empty.");
		}
		final int types[] = new int[]
		{ YEAR_TO_YEAR, YEAR_TO_MONTH, YEAR_TO_DAY, YEAR_TO_HOUR, YEAR_TO_MINUTE, YEAR_TO_SECOND, YEAR_TO_MILLISECOND,
				MONTH_TO_MONTH, MONTH_TO_DAY, MONTH_TO_HOUR, MONTH_TO_MINUTE, MONTH_TO_SECOND, MONTH_TO_MILLISECOND, DAY_TO_DAY,
				DAY_TO_HOUR, DAY_TO_MINUTE, DAY_TO_SECOND, DAY_TO_MILLISECOND, HOUR_TO_HOUR, HOUR_TO_MINUTE, HOUR_TO_SECOND,
				HOUR_TO_MILLISECOND, MINUTE_TO_MINUTE, MINUTE_TO_SECOND, MINUTE_TO_MILLISECOND, SECOND_TO_SECOND,
				SECOND_TO_MILLISECOND, MILLISECOND_TO_MILLISECOND };
		boolean isValidType = false;
		for (int i = 0; i < types.length; i++)
		{
			if (types[i] == type)
			{
				isValidType = true;
				continue;
			}
		}
		if (isValidType == false)
		{
			throw new IllegalStateException("this type is not support.");
		}
		// 如果类型一致就无需再检查类型了
		if (dateTime.getType() != type)
		{
			// 如果datetime类型10位大于type，那是肯定不行的,因为首部越界
			if ((dateTime.getType() / 10) > (type / 10))
			{
				throw new IllegalStateException("this type is out of range of this datetime instance.");
			}
			// 如果datetime类型个位小于type个位，那是肯定不行的,因为尾部越界
			if ((dateTime.getType() % 10) < (type % 10))
			{
				throw new IllegalStateException("this type is out of range of this datetime instance.");
			}
		}
	}

	/**
	 * 计算两个日期相隔的天数。只要传入日期的小时没到24点，就算作为一天。见如下举例： <br>
	 * (1) startDate = new DateTime("2002-04-23 00:00:00", DateTime.YEAR_TO_SECOND); endDate = new DateTime("2002-04-25
	 * 00:00:00", DateTime.YEAR_TO_SECOND); return interval=2. <br>
	 * (2) startDate = new DateTime("2002-04-23 24:00:00", DateTime.YEAR_TO_SECOND); endDate = new DateTime("2002-04-25
	 * 00:00:00", DateTime.YEAR_TO_SECOND); return interval=1. <br>
	 * (3) startDate = new DateTime("2002-04-23 10:00:01", DateTime.YEAR_TO_SECOND); endDate = new DateTime("2002-04-25
	 * 24:00:00", DateTime.YEAR_TO_SECOND); return interval=3. <br>
	 * (4) startDate = new DateTime("2002-04-23 00:00:00", DateTime.YEAR_TO_SECOND); endDate = new DateTime("2002-04-25
	 * 24:00:00", DateTime.YEAR_TO_SECOND); return interval=3. (5) startDate = new DateTime("2002-04-23 20:20:20",
	 * DateTime.YEAR_TO_SECOND); endDate = new DateTime("2002-04-25 10:10:10", DateTime.YEAR_TO_SECOND); return
	 * interval=2.
	 *
	 * @param startDate
	 * @param endDate
	 * @return date interval
	 */
	public static int getDateInterval(final Date startDate, final Date endDate)
	{
		// 计算年月日
		final Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(startDate);
		calendar1.set(Calendar.DST_OFFSET, 0);
		calendar1.set(Calendar.HOUR_OF_DAY, 0);
		calendar1.set(Calendar.MINUTE, 0);
		calendar1.set(Calendar.SECOND, 0);
		calendar1.set(Calendar.MILLISECOND, 0);
		final Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(endDate);
		calendar2.set(Calendar.DST_OFFSET, 0);
		calendar2.set(Calendar.HOUR_OF_DAY, 0);
		calendar2.set(Calendar.MINUTE, 0);
		calendar2.set(Calendar.SECOND, 0);
		calendar2.set(Calendar.MILLISECOND, 0);
		int interval = (int) ((calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) / MILLS_ONE_DAY);
		// 计算时分秒
		calendar1.setTime(startDate);
		calendar1.set(Calendar.DST_OFFSET, 0);
		calendar1.set(Calendar.YEAR, 1970);
		calendar1.set(Calendar.MONTH, 1);
		calendar1.set(Calendar.DATE, 1);
		calendar1.set(Calendar.MILLISECOND, 0);
		calendar2.setTime(endDate);
		calendar2.set(Calendar.DST_OFFSET, 0);
		calendar2.set(Calendar.YEAR, 1970);
		calendar2.set(Calendar.MONTH, 1);
		calendar2.set(Calendar.DATE, 1);
		calendar2.set(Calendar.MILLISECOND, 0);
		if (calendar2.after(calendar1))
		{
			interval++;
		}
		return interval;
	}

	/**
	 * 设置日期格式.参数为:YMD_FORMAT 年月日;DMY_FORMAT 日月年;MDY_FORMAT 月日年
	 *
	 * @param dateFormatType
	 */
	public static void setDateFormatType(final int dateFormatType)
	{
		DateTime.dateFormatType = dateFormatType;
	}
}
