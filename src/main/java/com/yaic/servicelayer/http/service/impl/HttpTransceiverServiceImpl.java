package com.yaic.servicelayer.http.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yaic.commons.codec.net.URLDecoder;
import com.yaic.commons.codec.net.URLEncoder;
import com.yaic.commons.io.output.ByteArrayOutputStream;
import com.yaic.commons.io.output.StringBuilderWriter;
import com.yaic.commons.util.MapUtil;
import com.yaic.commons.util.MapUtil.ValueCreator;
import com.yaic.servicelayer.http.exception.TransceiverException;
import com.yaic.servicelayer.http.service.AbstractHttpTransceiver;
import com.yaic.servicelayer.http.service.HttpTransceiverService;
import com.yaic.servicelayer.http.wrapper.BinaryBody;
import com.yaic.servicelayer.http.wrapper.HttpGetWrapper;
import com.yaic.servicelayer.http.wrapper.HttpPostKVpairWrapper;
import com.yaic.servicelayer.http.wrapper.HttpPostMultipartWrapper;
import com.yaic.servicelayer.http.wrapper.HttpPostRawWrapper;
import com.yaic.servicelayer.http.wrapper.HttpRequestWrapper;
import com.yaic.servicelayer.http.wrapper.HttpResponseWrapper;
import com.yaic.servicelayer.util.ArrayUtil;
import com.yaic.servicelayer.util.CollectionUtil;
import com.yaic.servicelayer.util.StringUtil;


/**
 * @author Qu Dihuai
 * @since 2018.2.5
 *        <p>
 *        数据收发器, 用于系统间的通信和数据交互.
 */
@Service(value = "httpTransceiverService")
public class HttpTransceiverServiceImpl extends AbstractHttpTransceiver implements HttpTransceiverService
{
	private static final Logger LOG = LoggerFactory.getLogger(HttpTransceiverServiceImpl.class);

	/**
	 * Represents the end-of-file (or stream).
	 */
	private static final int EOF = 0xffffffff;

	/**
	 * Represents the default buffer size ({@value}).
	 */
	private static final int DEFAULT_BUFFER_SIZE = 0x1000;

	@Override
	public HttpResponseWrapper doGet(final HttpGetWrapper requestWrapper)
	{
		if (requestWrapper == null)
		{
			return this.createErrorMessage(false, "HttpGetWrapper must be not null");
		}

		final String serverUrl = requestWrapper.getServerUrl();
		if (StringUtil.isEmpty(serverUrl))
		{
			return this.createErrorMessage(false, "ServerUrl must be not null");
		}

		final HttpGet httpGet = new HttpGet(serverUrl);
		setHeader(requestWrapper, httpGet);
		setRequestConfig(requestWrapper, httpGet);

		return doHttpRequest(httpGet, requestWrapper, createCharset(requestWrapper));
	}

	@Override
	public HttpResponseWrapper doPost(final HttpPostKVpairWrapper requestWrapper)
	{
		if (requestWrapper == null)
		{
			return this.createErrorMessage(false, "HttpPostKVpairWrapper must be not null");
		}

		final String serverUrl = requestWrapper.getServerUrl();
		if (StringUtil.isEmpty(serverUrl))
		{
			return this.createErrorMessage(false, "ServerUrl must be not null");
		}

		final HttpPost httpPost = new HttpPost(serverUrl);
		final Charset charset = createCharset(requestWrapper);

		setHeader(requestWrapper, httpPost);
		setRequestConfig(requestWrapper, httpPost);

		final List<NameValuePair> kvpairs = toNameValuePairList(requestWrapper.getKvpairs());
		if (CollectionUtil.isNotEmpty(kvpairs))
		{
			httpPost.setEntity(new UrlEncodedFormEntity(kvpairs, charset));
		}
		return doHttpRequest(httpPost, requestWrapper, charset);
	}

	@Override
	public HttpResponseWrapper doPost(final HttpPostMultipartWrapper requestWrapper)
	{
		if (requestWrapper == null)
		{
			return this.createErrorMessage(false, "HttpPostMultipartWrapper must be not null");
		}

		final String serverUrl = requestWrapper.getServerUrl();
		if (StringUtil.isEmpty(serverUrl))
		{
			return this.createErrorMessage(false, "ServerUrl must be not null");
		}

		final HttpPost httpPost = new HttpPost(serverUrl);
		setHeader(requestWrapper, httpPost);
		setRequestConfig(requestWrapper, httpPost);

		final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		final HttpMultipartMode mode = requestWrapper.getMode();
		if (mode != null)
		{
			builder.setMode(mode);
		}

		final String boundary = requestWrapper.getBoundary();
		if (boundary != null)
		{
			builder.setBoundary(boundary);
		}

		final Charset charset = createCharset(requestWrapper);
		if (charset != null)
		{
			builder.setCharset(charset);
		}

		try
		{
			setBinaryBody(requestWrapper, builder);
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage(), e);
			releaseConnection(httpPost);
			return this.createErrorMessage(false, e.getMessage());
		}

		setTextBody(requestWrapper, builder, charset);
		httpPost.setEntity(builder.build());

		return doHttpRequest(httpPost, requestWrapper, charset);
	}

	@Override
	public HttpResponseWrapper doPost(final HttpPostRawWrapper requestWrapper)
	{
		if (requestWrapper == null)
		{
			return this.createErrorMessage(false, "HttpPostRawWrapper must be not null");
		}

		final String serverUrl = requestWrapper.getServerUrl();
		if (StringUtil.isEmpty(serverUrl))
		{
			return this.createErrorMessage(false, "ServerUrl must be not null");
		}

		final HttpPost httpPost = new HttpPost(serverUrl);
		final Charset charset = createCharset(requestWrapper);

		setHeader(requestWrapper, httpPost);
		setRequestConfig(requestWrapper, httpPost);

		String rawBody = requestWrapper.getRawBody();
		if (rawBody != null)
		{
			if (requestWrapper.isUrlEncodeEnabled())
			{
				rawBody = URLEncoder.encode(rawBody, charset);
			}

			StringEntity stringEntity;
			if (StringUtil.isEmpty(requestWrapper.getMimeType()))
			{
				stringEntity = new StringEntity(rawBody, charset);
			}
			else
			{
				stringEntity = new StringEntity(rawBody, getContentType(requestWrapper.getMimeType(), charset));
			}

			final String contentEncoding = requestWrapper.getContentEncoding();
			if (StringUtil.isNotEmpty(contentEncoding))
			{
				stringEntity.setContentEncoding(contentEncoding);
			}

			httpPost.setEntity(stringEntity);
		}

		return doHttpRequest(httpPost, requestWrapper, charset);
	}

	@Override
	public HttpResponseWrapper doHttpRequest(final HttpUriRequest request, final HttpRequestWrapper requestWrapper,
			final Charset charset)
	{
		final HttpResponseWrapper responseWrapper = new HttpResponseWrapper();
		final HttpClient client = HttpClients.createDefault();

		HttpResponse response = null;

		try
		{
			// 执行请求, 获取响应结果
			response = executeHttpRequest(client, request);

			// 处理响应结果, 获取响应体
			final HttpEntity entity = getHttpEntity(response, responseWrapper);

			// 解析响应体, 获取响应内容
			final Object content = getHttpContent(entity, requestWrapper, charset);

			// 响应包装类
			responseWrapper.setStatus(true);
			responseWrapper.setContent(content);
		}
		catch (final TransceiverException e)
		{
			responseWrapper.setStatus(false);
			responseWrapper.setErrorMessage(MessageFormat.format("The request failed and root cause is [{0}]", e.getMessage()));
			LOG.error(e.getMessage(), e);
		}
		finally
		{
			HttpClientUtils.closeQuietly(response);
			HttpClientUtils.closeQuietly(client);
			releaseConnection(request);
		}

		return responseWrapper;
	}

	@Override
	public HttpEntity getHttpEntity(final HttpResponse response, final HttpResponseWrapper responseWrapper)
			throws TransceiverException
	{
		try
		{
			if (response == null)
			{
				throw new ClientProtocolException("Error empty response");
			}
		}
		catch (final ClientProtocolException e)
		{
			throw new TransceiverException(e.getMessage(), e);
		}

		final StatusLine statusLine = response.getStatusLine();
		final int statusCode = statusLine.getStatusCode();
		responseWrapper.setStatusCode(statusCode);

		final Header[] headers = response.getAllHeaders();
		if (ArrayUtil.isNotEmpty(headers))
		{
			final Map<String, String> map = new HashMap<>(headers.length);
			for (final Header header : headers)
			{
				map.put(header.getName(), header.getValue());
			}
			responseWrapper.setHeaders(Collections.unmodifiableMap(map));
		}

		final String message = "HTTP Status " + statusCode + " - " + statusLine.getReasonPhrase();
		LOG.info(message);

		try
		{
			if (statusCode != HttpStatus.SC_OK)
			{
				throw new HttpResponseException(statusCode, message);
			}

			final HttpEntity entity = response.getEntity();
			if (entity == null)
			{
				throw new ClientProtocolException("Response contains no content");
			}

			return entity;
		}
		catch (final Exception e)
		{
			throw new TransceiverException(e.getMessage(), e);
		}
	}

	@Override
	public Object getHttpContent(final HttpEntity entity, final HttpRequestWrapper requestWrapper, final Charset charset)
			throws TransceiverException
	{
		try (final InputStream input = entity.getContent())
		{
			if (input == null)
			{
				return null;
			}

			if (requestWrapper.isReceiveInputStreamEnabled())
			{
				return clone(input);
			}

			final Charset inputCharset = (charset == null) ? getCharsetFromResponse(entity) : charset;

			final String content = toString(input, inputCharset);
			if (StringUtil.isEmpty(content))
			{
				return null;
			}

			if (requestWrapper.isUrlDecodeEnabled())
			{
				return URLDecoder.decode(content, charset);
			}

			return content;
		}
		catch (final Exception e)
		{
			throw new TransceiverException(getMessage(e), e);
		}
	}

	private InputStream clone(final InputStream input) throws IOException
	{
		final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		int n;

		try (final ByteArrayOutputStream output = new ByteArrayOutputStream())
		{
			while (EOF != (n = input.read(buffer)))
			{
				output.write(buffer, 0, n);
			}
			final byte[] byteArray = output.toByteArray();
			return new ByteArrayInputStream(byteArray);
		}
	}

	private Charset createCharset(final HttpRequestWrapper httpRequestWrapper)
	{
		final String charset = httpRequestWrapper.getCharset();
		if (StringUtil.isEmpty(charset))
		{
			if (httpRequestWrapper instanceof HttpPostMultipartWrapper)
			{
				return null;
			}
			return StandardCharsets.UTF_8;
		}

		return Charset.forName(charset);
	}

	private HttpResponseWrapper createErrorMessage(final boolean status, final String errorMessage)
	{
		final HttpResponseWrapper wrapper = new HttpResponseWrapper();
		wrapper.setStatus(status);
		wrapper.setErrorMessage(errorMessage);
		return wrapper;
	}

	private ContentType getContentType(final String mimeType, final Charset charset)
	{
		final String key = ((charset != null) ? (mimeType + "_" + charset) : mimeType);
		final ContentType contentType = MapUtil.createIfAbsent(CONTENT_TYPE_CACHE, key, new ValueCreator<ContentType>()
		{
			@Override
			public ContentType execute()
			{
				return ContentType.create(mimeType, charset);
			}
		});
		return contentType;
	}

	private void setBinaryBody(final HttpPostMultipartWrapper requestWrapper, final MultipartEntityBuilder builder)
	{
		final List<BinaryBody> binaryBodyList = requestWrapper.getBinaryBody();
		if (CollectionUtil.isEmpty(binaryBodyList))
		{
			return;
		}

		final Set<String> bodyNames = new HashSet<>();
		ContentType contentType;

		for (final BinaryBody body : binaryBodyList)
		{
			if (body.getBodyName() == null)
			{
				throw new IllegalArgumentException("Binary body names must be not null");
			}

			if (body.getFileName() == null)
			{
				throw new IllegalArgumentException("File name must be not null");
			}

			if (body.getMimeType() == null)
			{
				contentType = ContentType.DEFAULT_BINARY;
			}
			else
			{
				contentType = getContentType(body.getMimeType(), null);
			}

			if (body.getByteArrayBody() != null)
			{
				builder.addBinaryBody(body.getBodyName(), body.getByteArrayBody(), contentType, body.getFileName());
			}
			else if (body.getFileBody() != null)
			{
				builder.addBinaryBody(body.getBodyName(), body.getFileBody(), contentType, body.getFileName());
			}
			else if (body.getStreamBody() != null)
			{
				builder.addBinaryBody(body.getBodyName(), body.getStreamBody(), contentType, body.getFileName());
			}
			else
			{
				throw new IllegalArgumentException("File must be not null");
			}

			bodyNames.add(body.getBodyName());
		}

		if (bodyNames.size() != binaryBodyList.size())
		{
			throw new IllegalArgumentException("Binary body names must be different from one another");
		}
	}

	private void setHeader(final HttpRequestWrapper httpRequestWrapper, final HttpRequestBase request)
	{
		final Map<String, String> headers = httpRequestWrapper.getHeaders();
		if (CollectionUtil.isEmpty(headers))
		{
			return;
		}

		for (final Map.Entry<String, String> entry : headers.entrySet())
		{
			if (entry != null)
			{
				request.setHeader(entry.getKey(), entry.getValue());
			}
		}
	}

	private void setRequestConfig(final HttpRequestWrapper httpRequestWrapper, final HttpRequestBase request)
	{
		final Integer connectTimeout = httpRequestWrapper.getConnectTimeout();
		final Integer socketTimeout = httpRequestWrapper.getSocketTimeout();
		final Integer connectionRequestTimeout = httpRequestWrapper.getConnectionRequestTimeout();
		if (connectTimeout == null && socketTimeout == null && connectionRequestTimeout == null)
		{
			return;
		}

		final RequestConfig.Builder buider = RequestConfig.custom();
		if (connectTimeout != null)
		{
			buider.setConnectTimeout(connectTimeout);
		}
		if (socketTimeout != null)
		{
			buider.setSocketTimeout(socketTimeout);
		}
		if (connectionRequestTimeout != null)
		{
			buider.setConnectionRequestTimeout(connectionRequestTimeout);
		}

		request.setConfig(buider.build());
	}

	private void setTextBody(final HttpPostMultipartWrapper requestWrapper, final MultipartEntityBuilder builder,
			final Charset charset)
	{
		final Map<String, String> keyValues = requestWrapper.getTextBody();
		if (CollectionUtil.isEmpty(keyValues))
		{
			return;
		}

		final Charset cs = (charset == null) ? StandardCharsets.UTF_8 : charset;
		final ContentType contentType = getContentType("text/plain", cs);

		for (final Map.Entry<String, String> entry : keyValues.entrySet())
		{
			if (entry != null)
			{
				builder.addTextBody(entry.getKey(), entry.getValue(), contentType);
			}
		}
	}

	private String toString(final InputStream input, final Charset charset) throws IOException
	{
		final char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		int n;

		try (final InputStreamReader in = new InputStreamReader(input, charset);
				final StringBuilderWriter out = new StringBuilderWriter())
		{
			while (EOF != (n = in.read(buffer)))
			{
				out.write(buffer, 0, n);
			}
			return out.toString();
		}
	}
}
