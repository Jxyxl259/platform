package com.yaic.servicelayer.http.wrapper;

/**
 * 以text/plain、application/json、application/javascript、application/xml、text/xml、text/html方式提交数据
 *
 * @author Qu Dihuai
 *
 */
public class HttpPostRawWrapper extends HttpPostWrapper
{
	/**
	 * <p>
	 * 原生字符串
	 */
	private String rawBody;

	/**
	 * <p>
	 * 对URL是否加密
	 */
	private boolean urlEncodeEnabled;

	/**
	 * 附加编码, 指响应内容传输到客户端之前，服务器需要对响应内容再做这样的额外编码
	 */
	private String contentEncoding;

	/**
	 * <p>
	 * 内容编码类型, 默认为text/plain
	 */
	private String mimeType;

	/**
	 * @return the contentEncoding
	 */
	public String getContentEncoding()
	{
		return contentEncoding;
	}

	/**
	 * @return the mimeType
	 */
	public String getMimeType()
	{
		return mimeType;
	}

	/**
	 * @return the rawBody
	 */
	public String getRawBody()
	{
		return rawBody;
	}

	/**
	 * @return the urlEncodeEnabled
	 */
	public boolean isUrlEncodeEnabled()
	{
		return urlEncodeEnabled;
	}

	/**
	 * @param contentEncoding
	 *           the contentEncoding to set
	 */
	public void setContentEncoding(final String contentEncoding)
	{
		this.contentEncoding = contentEncoding;
	}

	/**
	 * @param mimeType
	 *           the mimeType to set
	 */
	public void setMimeType(final String mimeType)
	{
		this.mimeType = mimeType;
	}

	/**
	 * @param rawBody
	 *           the rawBody to set
	 */
	public void setRawBody(final String rawBody)
	{
		this.rawBody = rawBody;
	}

	/**
	 * @param urlEncodeEnabled
	 *           the urlEncodeEnabled to set
	 */
	public void setUrlEncodeEnabled(final boolean urlEncodeEnabled)
	{
		this.urlEncodeEnabled = urlEncodeEnabled;
	}

}
