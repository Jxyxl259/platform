package com.yaic.servicelayer.http;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.yaic.config.PlatformConfig;
import com.yaic.servicelayer.http.service.HttpTransceiverService;
import com.yaic.servicelayer.http.wrapper.HttpGetWrapper;
import com.yaic.servicelayer.http.wrapper.HttpPostKVpairWrapper;
import com.yaic.servicelayer.http.wrapper.HttpPostMultipartWrapper;
import com.yaic.servicelayer.http.wrapper.HttpPostRawWrapper;
import com.yaic.servicelayer.http.wrapper.HttpResponseWrapper;


/**
 * HttpTransceiver是数据收发器的门面, 提供对外开放调用的接口
 *
 * @author Qu Dihuai
 *
 */
public class HttpTransceiver
{
	private final static ApplicationContext CONTEXT = new AnnotationConfigApplicationContext(PlatformConfig.class);

	private static HttpTransceiverService getBean()
	{
		return CONTEXT.getBean("httpTransceiverService", HttpTransceiverService.class);
	}

	/**
	 * 执行Get请求, 默认编码类型为UTF-8
	 *
	 * @param httpGetWrapper
	 * @return httpResponseWrapper
	 */
	public static HttpResponseWrapper doGet(final HttpGetWrapper httpGetWrapper)
	{
		return getBean().doGet(httpGetWrapper);
	}

	/**
	 * <p>
	 * 执行Post请求, 以原生字符串(rawBody)的方式提交数据, 适用的MIME类型有: (1) text/plain; (2) application/json; (3) application/javascript;
	 * (4) application/xml; (5) text/html; (6) text/xml;
	 * <p>
	 * 默认编码类型为UTF-8
	 * <p>
	 * 默认MIME类型为text/plain
	 *
	 * @param httpPostWrapper
	 * @return httpResponseWrapper
	 */
	public static HttpResponseWrapper doPost(final HttpPostRawWrapper httpPostWrapper)
	{
		return getBean().doPost(httpPostWrapper);
	}

	/**
	 * <p>
	 * 执行Post请求, 适用于：
	 * <p>
	 * 1. 以multipart/form-data方式提交数据
	 * <p>
	 * 2. 以application/octet-stream方式上传二进制数据
	 * <p>
	 * 参数的默认编码类型为UTF-8
	 *
	 * @param httpPostWrapper
	 * @return httpResponseWrapper
	 */
	public static HttpResponseWrapper doPost(final HttpPostMultipartWrapper httpPostWrapper)
	{
		return getBean().doPost(httpPostWrapper);
	}

	/**
	 * 执行Post请求, 适用于以 application/x-www-form-urlencoded方式提交数据
	 *
	 * @param httpPostWrapper
	 * @return httpResponseWrapper
	 */
	public static HttpResponseWrapper doPost(final HttpPostKVpairWrapper httpPostWrapper)
	{
		return getBean().doPost(httpPostWrapper);
	}

	/**
	 * 执行Get请求, 默认编码类型为UTF-8
	 *
	 * @param serverUrl
	 * @return httpResponseWrapper
	 */
	public static HttpResponseWrapper doGet(final String serverUrl)
	{
		return doGet(serverUrl, null);
	}

	/**
	 * 方法说明：
	 * <p>
	 * 1.执行Get请求
	 * <p>
	 * 2.默认编码类型为UTF-8
	 *
	 * @param serverUrl
	 * @param headers
	 * @return httpResponseWrapper
	 */
	public static HttpResponseWrapper doGet(final String serverUrl, final Map<String, String> headers)
	{
		final HttpGetWrapper httpGetWrapper = new HttpGetWrapper();
		httpGetWrapper.setServerUrl(serverUrl);
		httpGetWrapper.setHeaders(headers);
		return doGet(httpGetWrapper);
	}

	/**
	 * 方法说明：
	 * <p>
	 * 1.执行Post请求
	 * <p>
	 * 2.以原生字符串的方式提交, 适用的MIME类型有: (1) text/plain; (2) application/json; (3) application/javascript; (4) application/xml;
	 * (5) text/html; (6) text/xml;
	 * <p>
	 * 3.默认编码类型为UTF-8
	 * <p>
	 * 4.urlEncodeEnabled指是否编码, true为编码, false为不编码
	 * <p>
	 * 5.urlDecodeEnabled指是否解码, true为解码, false为不解码
	 * <p>
	 * 6.默认MIME类型类型为text/plain
	 *
	 * @param serverUrl
	 * @param mimeType
	 * @param rawBody
	 * @param urlEncodeEnabled
	 * @param urlDecodeEnabled
	 *
	 * @return httpResponseWrapper
	 *
	 */
	public static HttpResponseWrapper doPostRaw(final String serverUrl, final String mimeType, final String rawBody,
			final boolean urlEncodeEnabled, final boolean urlDecodeEnabled)
	{
		final HttpPostRawWrapper httpPostWrapper = new HttpPostRawWrapper();
		httpPostWrapper.setServerUrl(serverUrl);
		httpPostWrapper.setMimeType(mimeType);
		httpPostWrapper.setRawBody(rawBody);
		httpPostWrapper.setUrlEncodeEnabled(urlEncodeEnabled);
		httpPostWrapper.setUrlDecodeEnabled(urlDecodeEnabled);
		return doPost(httpPostWrapper);
	}

	/**
	 * 方法说明：
	 * <p>
	 * 1.执行Post请求
	 * <p>
	 * 2.以原生字符串的方式提交, 适用的MIME类型有: (1) text/plain; (2) application/json; (3) application/javascript; (4) application/xml;
	 * (5) text/html; (6) text/xml;
	 * <p>
	 * 3.默认编码类型为UTF-8
	 * <p>
	 * 4.urlEncodeEnabled指是否编码, true为编码, false为不编码
	 * <p>
	 * 5.urlDecodeEnabled指是否解码, true为解码, false为不解码
	 * <p>
	 * 6.默认MIME类型类型为text/plain
	 *
	 * @param headers
	 * @param serverUrl
	 * @param rawBody
	 * @param urlEncodeEnabled
	 * @param urlDecodeEnabled
	 *
	 * @return httpResponseWrapper
	 */
	public static HttpResponseWrapper doPostRaw(final Map<String, String> headers, final String serverUrl, final String rawBody,
			final boolean urlEncodeEnabled, final boolean urlDecodeEnabled)
	{
		final HttpPostRawWrapper httpPostWrapper = new HttpPostRawWrapper();
		httpPostWrapper.setServerUrl(serverUrl);
		httpPostWrapper.setHeaders(headers);
		httpPostWrapper.setRawBody(rawBody);
		httpPostWrapper.setUrlEncodeEnabled(urlEncodeEnabled);
		httpPostWrapper.setUrlDecodeEnabled(urlDecodeEnabled);
		return doPost(httpPostWrapper);
	}

	/**
	 * 方法说明： 1.执行Post请求; 2.提交键值对数据; 3.默认编码类型为UTF-8;
	 *
	 * @param serverUrl
	 * @param headers
	 * @param kvpairs
	 * @param urlDecodeEnabled
	 *
	 * @return httpResponseWrapper
	 */
	public static HttpResponseWrapper doPostKVpair(final String serverUrl, final Map<String, String> headers,
			final Map<String, String> kvpairs, final boolean urlDecodeEnabled)
	{
		final HttpPostKVpairWrapper httpPostWrapper = new HttpPostKVpairWrapper();
		httpPostWrapper.setServerUrl(serverUrl);
		httpPostWrapper.setHeaders(headers);
		httpPostWrapper.setKvpairs(kvpairs);
		httpPostWrapper.setUrlDecodeEnabled(urlDecodeEnabled);
		return doPost(httpPostWrapper);
	}
}
