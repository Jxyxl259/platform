package com.yaic.servicelayer.http.service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import com.yaic.servicelayer.http.exception.TransceiverException;
import com.yaic.servicelayer.http.wrapper.HttpRequestWrapper;
import com.yaic.servicelayer.http.wrapper.HttpResponseWrapper;


/**
 * @author Qu Dihuai
 *
 */
public abstract class AbstractHttpTransceiver
{
	private static final Logger LOG = LoggerFactory.getLogger(AbstractHttpTransceiver.class);

	/**
	 * Cache for ContentType
	 */
	protected static final ConcurrentHashMap<String, ContentType> CONTENT_TYPE_CACHE = new ConcurrentHashMap<>(64);
	static
	{
		//******************************************************* key = mimeType_charset ***********************************************
		CONTENT_TYPE_CACHE.put("application/json_UTF-8", ContentType.create("application/json", StandardCharsets.UTF_8));
		CONTENT_TYPE_CACHE.put("text/plain_UTF-8", ContentType.create("text/plain", StandardCharsets.UTF_8));

		//********************************************************* key = mimeType *****************************************************
		CONTENT_TYPE_CACHE.put("application/atom+xml", ContentType.create("application/atom+xml", StandardCharsets.ISO_8859_1));

		CONTENT_TYPE_CACHE.put("application/x-www-form-urlencoded",
				ContentType.create("application/x-www-form-urlencoded", StandardCharsets.ISO_8859_1));

		CONTENT_TYPE_CACHE.put("application/json", ContentType.create("application/json", StandardCharsets.UTF_8));

		CONTENT_TYPE_CACHE.put("application/octet-stream", ContentType.create("application/octet-stream", (Charset) null));

		CONTENT_TYPE_CACHE.put("application/svg+xml", ContentType.create("application/svg+xml", StandardCharsets.ISO_8859_1));

		CONTENT_TYPE_CACHE.put("application/xhtml+xml", ContentType.create("application/xhtml+xml", StandardCharsets.ISO_8859_1));

		CONTENT_TYPE_CACHE.put("application/xml", ContentType.create("application/xml", StandardCharsets.ISO_8859_1));

		CONTENT_TYPE_CACHE.put("image/bmp", ContentType.create("image/bmp"));

		CONTENT_TYPE_CACHE.put("image/gif", ContentType.create("image/gif"));

		CONTENT_TYPE_CACHE.put("image/jpeg", ContentType.create("image/jpeg"));

		CONTENT_TYPE_CACHE.put("image/png", ContentType.create("image/png"));

		CONTENT_TYPE_CACHE.put("image/svg+xml", ContentType.create("image/svg+xml"));

		CONTENT_TYPE_CACHE.put("image/tiff", ContentType.create("image/tiff"));

		CONTENT_TYPE_CACHE.put("image/webp", ContentType.create("image/webp"));

		CONTENT_TYPE_CACHE.put("multipart/form-data", ContentType.create("multipart/form-data", StandardCharsets.ISO_8859_1));

		CONTENT_TYPE_CACHE.put("text/html", ContentType.create("text/html", StandardCharsets.ISO_8859_1));

		CONTENT_TYPE_CACHE.put("text/plain", ContentType.create("text/plain", StandardCharsets.ISO_8859_1));

		CONTENT_TYPE_CACHE.put("text/xml", ContentType.create("text/xml", StandardCharsets.ISO_8859_1));

		CONTENT_TYPE_CACHE.put("*/* ", ContentType.create("*/*", (Charset) null));
	}

	private static final String SERVER_RESP_TIME = "Server's response time is {} milliseconds";

	/**
	 * Execute http request, and get response from the server
	 *
	 * @param request
	 * @param requestWrapper
	 * @param charset
	 * @return HttpResponseWrapper
	 */
	public abstract HttpResponseWrapper doHttpRequest(HttpUriRequest request, HttpRequestWrapper requestWrapper, Charset charset);

	/**
	 * Parse the server's response to get http entity
	 *
	 * @param response
	 * @param responseWrapper
	 * @return HttpEntity
	 * @throws TransceiverException
	 */
	public abstract HttpEntity getHttpEntity(HttpResponse response, HttpResponseWrapper responseWrapper)
			throws TransceiverException;

	/**
	 * Parse the server's response entity to get content
	 *
	 * @param entity
	 * @param requestWrapper
	 * @param charset
	 * @return HttpContent
	 * @throws TransceiverException
	 */
	public abstract Object getHttpContent(HttpEntity entity, HttpRequestWrapper requestWrapper, Charset charset)
			throws TransceiverException;

	/**
	 * Execute request, and get the server's response
	 *
	 * @param client
	 * @param request
	 * @return HttpResponse
	 * @throws TransceiverException
	 */
	public HttpResponse executeHttpRequest(final HttpClient client, final HttpUriRequest request) throws TransceiverException
	{
		final StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		try
		{
			return client.execute(request);
		}
		catch (final Exception e)
		{
			throw new TransceiverException(getMessage(e), e);
		}
		finally
		{
			stopWatch.stop();
			LOG.info(SERVER_RESP_TIME, stopWatch.getTotalTimeMillis());
		}
	}

	/**
	 * @param map
	 * @return the NameValuePair list
	 */
	public List<NameValuePair> toNameValuePairList(final Map<String, String> map)
	{
		int size;
		if (map == null || (size = map.size()) == 0)
		{
			return null;
		}

		final List<NameValuePair> list = new ArrayList<>(size);
		for (final Map.Entry<String, String> entry : map.entrySet())
		{
			if (entry != null)
			{
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		return list;
	}

	/**
	 * Get the charset from response entity, if it's null, return default charset ISO-8859-1
	 *
	 * @param responseEntity
	 * @return Charset
	 */
	protected Charset getCharsetFromResponse(final HttpEntity responseEntity) throws ParseException, UnsupportedCharsetException
	{
		ContentType contentType = ContentType.get(responseEntity);
		if (contentType == null)
		{
			return HTTP.DEF_CONTENT_CHARSET;
		}

		if (contentType.getCharset() != null)
		{
			return contentType.getCharset();
		}

		contentType = CONTENT_TYPE_CACHE.get(contentType.getMimeType());
		if (contentType != null)
		{
			return contentType.getCharset();
		}

		return HTTP.DEF_CONTENT_CHARSET;
	}

	/**
	 * Get the root cause for exception
	 *
	 * @param throwable
	 */
	protected String getMessage(final Throwable throwable)
	{
		if (throwable == null)
		{
			return null;
		}

		if (throwable.getMessage() != null)
		{
			return throwable.getMessage();
		}

		Throwable cause = throwable.getCause();
		if (cause == null)
		{
			return throwable.toString();
		}

		while (cause.getMessage() == null)
		{
			if (cause.getCause() == null || cause.getCause() == cause)
			{
				return cause.toString();
			}
			cause = cause.getCause();
		}

		return cause.getMessage();
	}

	/**
	 * @param request
	 */
	protected void releaseConnection(final HttpRequest request)
	{
		if (request != null)
		{
			((HttpRequestBase) request).releaseConnection();
		}
	}
}
