package com.yaic.servicelayer.http.wrapper;

import java.util.Map;


/**
 * HttpResponse的封装类
 *
 * @author Qu Dihuai
 *
 */
public class HttpResponseWrapper
{
	/**
	 * <p>
	 * true表示成功, false表示失败
	 */
	private boolean status;

	/**
	 * <p>
	 * statusCode用来记录服务器的响应码
	 */
	private int statusCode;

	/**
	 * <p>
	 * 响应头
	 */
	private Map<String, String> headers;

	/**
	 * <p>
	 * 成功时返回响应内容, 失败则为空
	 */
	private Object content;

	/**
	 * <p>
	 * 失败时返回原因, 成功则为空
	 */
	private String errorMessage;

	/**
	 * @return the status
	 */
	public boolean getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *           the status to set
	 */
	public void setStatus(final boolean status)
	{
		this.status = status;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode()
	{
		return statusCode;
	}

	/**
	 * @param statusCode
	 *           the statusCode to set
	 */
	public void setStatusCode(final int statusCode)
	{
		this.statusCode = statusCode;
	}

	/**
	 * @return the headers
	 */
	public Map<String, String> getHeaders()
	{
		return headers;
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
	 * @return the content
	 */
	public Object getContent()
	{
		return content;
	}

	/**
	 * @param content
	 *           the content to set
	 */
	public void setContent(final Object content)
	{
		this.content = content;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *           the errorMessage to set
	 */
	public void setErrorMessage(final String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

}
