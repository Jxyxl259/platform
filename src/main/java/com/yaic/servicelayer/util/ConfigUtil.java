package com.yaic.servicelayer.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;
import com.yaic.servicelayer.charset.StandardCharset;


/**
 * @Description 读取属性配置文件
 * @author wangzhonghua
 *
 */
public class ConfigUtil
{
	private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

	private static final ConcurrentMap<String, String> ALLPROPS_CACHE = new ConcurrentHashMap<>();

	private static final String XML_FILE_EXTENSION = ".xml";
	private static final String JSON_FILE_EXTENSION = ".json";

	/**
	 * @param propertiesList
	 *           项目启动执行
	 */
	public static void loadProperties(final List<String> propertiesList)
	{
		if (CollectionUtil.isEmpty(propertiesList))
		{
			return;
		}

		final ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
		Properties properties = null;
		for (final String resourceName : propertiesList)
		{
			try
			{
				properties = loadAllProperties(resourceName, classLoader);
				ALLPROPS_CACHE.putAll(prop2Map(properties));
			}
			catch (final IOException e)
			{
				logger.error("读取文件异常:", e);
			}
		}
	}

	/**
	 * @param resourceName
	 *           要读取的文件名
	 * @return Properties 返回文件中的配置
	 * @throws IOException
	 */
	public static Properties loadAllProperties(final String resourceName) throws IOException
	{
		final ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
		return loadAllProperties(resourceName, classLoader);
	}


	/**
	 * @param resourceName
	 *           要读取的文件名
	 * @return Map<String, String> 返回文件中的配置
	 * @throws IOException
	 */
	public static Map<String, String> loadFromJson(final String resourceName) throws IOException
	{
		return prop2Map(loadAllProperties(resourceName));
	}

	/**
	 * @param resourceName
	 *           要读取的文件名
	 * @return Map<String, String> 返回文件中的配置
	 * @throws IOException
	 */
	public static Map<String, String> loadFromXml(final String resourceName) throws IOException
	{
		return prop2Map(loadAllProperties(resourceName));
	}

	/**
	 * @param key
	 * @return 获取值结果
	 */
	public static String getValue(final String key)
	{
		if (StringUtil.isEmpty(key))
		{
			logger.warn("参数不合法,key:[{}]", key);
			return null;
		}
		return ALLPROPS_CACHE.get(key);
	}

	/**
	 * @param key
	 *           key值
	 * @param resourceName
	 *           配置文件名
	 * @return 获取值结果
	 * @throws IOException
	 */
	public static String getValue(final String key, final String resourceName) throws IOException
	{
		return loadAllProperties(resourceName).getProperty(key);
	}

	/**
	 * Load all properties from the specified class path resource (in ISO-8859-1 encoding), using the given class loader.
	 * <p>
	 * Merges properties if more than one resource of the same name found in the class path.
	 *
	 * @param resourceName
	 *           the name of the class path resource
	 * @param classLoader
	 *           the ClassLoader to use for loading (or {@code null} to use the default class loader)
	 * @return the populated Properties instance
	 * @throws IOException
	 *            if loading failed
	 */
	private static Properties loadAllProperties(final String resourceName, final ClassLoader classLoader) throws IOException
	{
		Assert.notNull(resourceName, "Resource name must not be null");

		ClassLoader classLoaderToUse;
		if (classLoader != null)
		{
			classLoaderToUse = classLoader;
		}
		else
		{
			classLoaderToUse = ClassUtils.getDefaultClassLoader();
		}

		Enumeration<URL> urls;
		if (classLoaderToUse != null)
		{
			urls = classLoaderToUse.getResources(resourceName);
		}
		else
		{
			urls = ClassLoader.getSystemResources(resourceName);
		}

		final Properties props = new Properties();
		while (urls.hasMoreElements())
		{
			final URL url = urls.nextElement();
			final URLConnection con = url.openConnection();
			ResourceUtils.useCachesIfNecessary(con);

			try (final InputStream is = con.getInputStream())
			{
				if (resourceName.endsWith(XML_FILE_EXTENSION))
				{
					props.loadFromXML(is);
				}
				else if (resourceName.endsWith(JSON_FILE_EXTENSION))
				{
					loadFromJSON(props, is);
				}
				else
				{
					props.load(is);
				}
			}
		}

		return props;
	}

	private static void loadFromJSON(final Properties properties, final InputStream input) throws IOException
	{
		if (input == null)
		{
			throw new NullPointerException();
		}

		final String json = IOUtil.toString(input, StandardCharset.UTF_8);
		if (StringUtil.isEmpty(json))
		{
			return;
		}

		final Map<String, Object> map = JSON.parseObject(json);
		if (CollectionUtil.isEmpty(map))
		{
			return;
		}

		for (final Map.Entry<String, Object> entry : map.entrySet())
		{
			if (entry != null)
			{
				String value;
				if (entry.getValue() instanceof String)
				{
					value = (String) entry.getValue();
				}
				else
				{
					value = JSON.toJSONString(entry.getValue());
				}
				properties.setProperty(entry.getKey(), value);
			}
		}
	}

	private static Map<String, String> prop2Map(final Properties properties)
	{
		final Set<Entry<Object, Object>> entrySet = properties.entrySet();
		if (CollectionUtil.isEmpty(entrySet))
		{
			return Collections.emptyMap();
		}

		final Map<String, String> map = new HashMap<>(entrySet.size());
		for (final Entry<Object, Object> entry : entrySet)
		{
			if (entry != null)
			{
				map.put((String) entry.getKey(), (String) entry.getValue());
			}
		}

		return map;
	}
}
