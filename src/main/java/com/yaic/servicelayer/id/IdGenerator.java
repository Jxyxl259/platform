package com.yaic.servicelayer.id;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Qu Dihuai
 */
public class IdGenerator
{
	private static final Logger LOG = LoggerFactory.getLogger(IdGenerator.class);

	private static final String WORK_ID_KEY = "idwork.service.work.id";
	private static final String DATACENTER_ID_KEY = "idwork.service.datacenter.id";

	private static final IdWorker idWorker = initIdWorker();

	private IdGenerator()
	{
		super();
	}

	/**
	 * @return ID
	 */
	public static String generate()
	{
		try
		{
			return String.valueOf(idWorker.nextId());
		}
		catch (final Exception e)
		{
			LOG.warn("Failed to generate User ID", e);
		}
		return null;
	}

	private static IdWorker initIdWorker()
	{
		final String filePath = "/idwork.properties";
		final Properties properties = loadProperties(filePath);
		return new IdWorker(getValue(properties, WORK_ID_KEY), getValue(properties, DATACENTER_ID_KEY));
	}

	private static long getValue(final Properties properties, final String key)
	{
		return Long.parseLong(properties.getProperty(key));
	}

	/**
	 * @param filePath
	 *           filePath is relative path in classpath
	 * @return properties
	 * @throws IOException
	 */
	private static Properties loadProperties(final String filePath)
	{
		final Properties properties = new Properties();

		try (final InputStream in = IdGenerator.class.getResourceAsStream(filePath))
		{
			properties.load(in);
			return properties;
		}
		catch (IOException e)
		{
			LOG.error("Load properties file '{}' failed", filePath, e);
		}

		return null;
	}
}
