package com.yaic.servicelayer.app.provider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;
import com.yaic.servicelayer.charset.StandardCharset;
import com.yaic.servicelayer.util.IOUtil;
import com.yaic.servicelayer.util.StringUtil;


/**
 * 从配置文件中读取应用系统编号表
 *
 * @author Qu Dihuai
 */
public class AppNoProvider
{
	private static final Logger LOG = LoggerFactory.getLogger(AppNoProvider.class);

	private static final String RESOURCE_NAME = "systemNo.json";
	private static final Map<String, String> APPNO_MAP = initData();

	/**
	 * 通过key获取系统的编号
	 *
	 * @param systemKey
	 * @return AppNo
	 */
	public static String provide(final String systemKey)
	{
		if (StringUtil.isEmpty(systemKey))
		{
			return null;
		}
		return APPNO_MAP.get(systemKey);
	}

	private static Map<String, String> initData()
	{
		String resource = null;
		try
		{
			resource = loadResource();
		}
		catch (final IOException e)
		{
			LOG.error("Load resource {} failed.", RESOURCE_NAME, e);
		}

		if (StringUtil.isEmpty(resource))
		{
			return Collections.emptyMap();
		}

		final Map<String, Object> jsonMap = JSON.parseObject(resource);
		final Map<String, String> map = new HashMap<>(jsonMap.size());
		for (final Map.Entry<String, Object> entry : jsonMap.entrySet())
		{
			map.put(entry.getKey(), (String) entry.getValue());
		}

		return Collections.unmodifiableMap(map);
	}

	private static String loadResource() throws IOException
	{
		final ClassLoader classLoader = ClassUtils.getDefaultClassLoader();

		Enumeration<URL> urls;
		if (classLoader != null)
		{
			urls = classLoader.getResources(RESOURCE_NAME);
		}
		else
		{
			urls = ClassLoader.getSystemResources(RESOURCE_NAME);
		}

		final StringBuilder builder = new StringBuilder();
		while (urls.hasMoreElements())
		{
			final URL url = urls.nextElement();
			final URLConnection con = url.openConnection();

			ResourceUtils.useCachesIfNecessary(con);

			try (final InputStream is = con.getInputStream())
			{
				if (is != null)
				{
					builder.append(IOUtil.toString(is, StandardCharset.UTF_8));
				}
			}
		}

		return builder.toString();
	}

}
