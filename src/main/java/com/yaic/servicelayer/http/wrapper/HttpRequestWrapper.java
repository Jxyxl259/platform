package com.yaic.servicelayer.http.wrapper;

import java.util.Map;


/**
 * HttpRequest的封装类
 *
 * @author Qu Dihuai
 */
public abstract class HttpRequestWrapper
{
	/**
	 * <p>
	 * 服务器地址, 不能为空
	 */
	private String serverUrl;

	/**
	 * <p>
	 * 请求头
	 */
	private Map<String, String> headers;

	/**
	 * <p>
	 * URL的编码类型
	 */
	private String charset;

	/**
	 * <p>
	 * 对响应内容是否解码
	 */
	private boolean urlDecodeEnabled;

	/**
	 * <p>
	 * 响应结果是否为InputStream
	 */
	private boolean receiveInputStreamEnabled;

	/**
	 * 从服务器读取数据的超时, 时间单位为ms 若超时，抛出异常 java.net.SocketTimeoutException: Read timed out exception
	 */
	private Integer socketTimeout;

	/**
	 * 和服务器建立连接的超时, 时间单位为ms 若超时，抛出异常 java.net.ConnectionTimeoutException: Connection timed out exception
	 */
	private Integer connectTimeout;

	/**
	 * 从连接池获取连接的超时, 时间单位为ms
	 */
	private Integer connectionRequestTimeout;

	/**
	 * @return the charset
	 */
	public String getCharset()
	{
		return charset;
	}

	/**
	 * @return the connectionRequestTimeout
	 */
	public Integer getConnectionRequestTimeout()
	{
		return connectionRequestTimeout;
	}

	/**
	 * @return the connectTimeout
	 */
	public Integer getConnectTimeout()
	{
		return connectTimeout;
	}

	/**
	 * @return the headers
	 */
	public Map<String, String> getHeaders()
	{
		return headers;
	}

	/**
	 * @return the serverUrl
	 */
	public String getServerUrl()
	{
		return serverUrl;
	}

	/**
	 * @return the socketTimeout
	 */
	public Integer getSocketTimeout()
	{
		return socketTimeout;
	}

	/**
	 * @return the receiveInputStreamEnabled
	 */
	public boolean isReceiveInputStreamEnabled()
	{
		return receiveInputStreamEnabled;
	}

	/**
	 * @return the urlDecodeEnabled
	 */
	public boolean isUrlDecodeEnabled()
	{
		return urlDecodeEnabled;
	}

	/**
	 * @param charset
	 *           the charset to set
	 */
	public void setCharset(final String charset)
	{
		this.charset = charset;
	}

	/**
	 * @param connectionRequestTimeout
	 *           the connectionRequestTimeout to set
	 */
	public void setConnectionRequestTimeout(final Integer connectionRequestTimeout)
	{
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

	/**
	 * @param connectTimeout
	 *           the connectTimeout to set
	 */
	public void setConnectTimeout(final Integer connectTimeout)
	{
		this.connectTimeout = connectTimeout;
	}

	/**
	 * @param headers
	 *           the headers to set
	 */
	public void setHeaders(final Map<String, String> headers)
	{
		this.headers = headers;
	}

	/**
	 * @param receiveInputStreamEnabled
	 *           the receiveInputStreamEnabled to set
	 */
	public void setReceiveInputStreamEnabled(final boolean receiveInputStreamEnabled)
	{
		this.receiveInputStreamEnabled = receiveInputStreamEnabled;
	}

	/**
	 * @param serverUrl
	 *           the serverUrl to set
	 */
	public void setServerUrl(final String serverUrl)
	{
		this.serverUrl = serverUrl;
	}

	/**
	 * @param socketTimeout
	 *           the socketTimeout to set
	 */
	public void setSocketTimeout(final Integer socketTimeout)
	{
		this.socketTimeout = socketTimeout;
	}

	/**
	 * @param urlDecodeEnabled
	 *           the urlDecodeEnabled to set
	 */
	public void setUrlDecodeEnabled(final boolean urlDecodeEnabled)
	{
		this.urlDecodeEnabled = urlDecodeEnabled;
	}

}
