/**
 *
 */
package com.yaic.servicelayer.datetime.impl;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;

import com.yaic.servicelayer.datetime.TimeService;


/**
 * @author Qu Dihuai
 */
@Service(value = "timeService")
public class TimeServiceImpl implements TimeService
{
	/**
	 * Represents the default date-time-zone is Asia/Shanghai.
	 */
	private static final DateTimeZone DEFAULT_DATETIME_ZONE = DateTimeZone.forID("Asia/Shanghai");

	@Override
	public Date getCurrentTime()
	{
		return new DateTime(DEFAULT_DATETIME_ZONE).toDate();
	}

}
