package com.yaic.servicelayer.util;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yaic.servicelayer.charset.StandardCharset;

/**
 * 获取ServletRequest的请求体内容
 * 
 * @author Qu Dihuai
 *
 */
public class HttpHelper {
	private static final Logger LOG = LoggerFactory.getLogger(HttpHelper.class);

	/**
	 * 以字符串的形式解析ServletRequest的请求内容, 字符集默认为UTF-8
	 * 
	 * @param request ServletRequest
	 * @return String the content of request
	 */
	public static String getBodyString(final ServletRequest request) {
		try (final InputStream input = request.getInputStream())
		{
			if (input == null) {
				return null;
			}
			return IOUtil.toString(input, StandardCharset.UTF_8);
		}
		catch (final IOException e)
		{
			LOG.error(e.getMessage(), e);
		} 
		return null;
	}

}