package com.yaic.servicelayer.id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zhujuan
 * From: https://github.com/twitter/snowflake
 * An object that generates IDs.
 * This is broken into a separate class in case
 * we ever want to support multiple worker threads
 * per process
 */
class IdWorker
{
	private static final Logger LOG = LoggerFactory.getLogger(IdWorker.class);

	private long workerId;
	private long datacenterId;

	private long lastTimestamp = -1L;
	private long sequence = 0L;

	private final long twepoch = 1288834974657L;
	private final long workerIdBits = 5L;
	private final long datacenterIdBits = 5L;
	private final long sequenceBits = 12L;

	private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
	private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
	private final long workerIdShift = sequenceBits;
	private final long datacenterIdShift = sequenceBits + workerIdBits;
	private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
	private final long sequenceMask = -1L ^ (-1L << sequenceBits);

	/**
	 * @param workerId
	 * @param datacenterId
	 */
	protected IdWorker(final long workerId, final long datacenterId)
	{
		if (workerId > maxWorkerId || workerId < 0)
		{
			throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
		}
		
		if (datacenterId > maxDatacenterId || datacenterId < 0)
		{
			throw new IllegalArgumentException(
					String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
		}

		LOG.info(
				"worker starting. timestamp left shift {}, datacenter id bits {}, worker id bits {}, sequence bits {}, workerid {}",
				timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);

		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}

	/**
	 * @return ID
	 */
	protected synchronized long nextId()
	{
		long timestamp = timeGen();

		if (timestamp < lastTimestamp)
		{
			LOG.error("clock is moving backwards.  Rejecting requests until {}.", lastTimestamp);
			throw new RuntimeException(
					String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		}

		if (lastTimestamp == timestamp)
		{
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0)
			{
				timestamp = tilNextMillis(lastTimestamp);
			}
		}
		else
		{
			sequence = 0L;
		}

		lastTimestamp = timestamp;

		return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift)
				| sequence;
	}

	private long tilNextMillis(final long lastTimestamp)
	{
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp)
		{
			timestamp = timeGen();
		}
		return timestamp;
	}

	private long timeGen()
	{
		return System.currentTimeMillis();
	}
}
