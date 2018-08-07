package com.yaic.servicelayer.http.wrapper;

import java.util.List;
import java.util.Map;

import org.apache.http.entity.mime.HttpMultipartMode;


/**
 * 以multipart/form-data 或 application/octet-stream方式提交数据
 *
 * @author Qu Dihuai
 *
 */
public class HttpPostMultipartWrapper extends HttpPostWrapper
{
	/**
	 * <p>
	 * 文本正文
	 */
	private Map<String, String> textBody;

	/**
	 * <p>
	 * 二进制的文件
	 */
	private List<BinaryBody> binaryBody;

	/**
	 * <p>
	 * 分隔符
	 */
	private String boundary;

	/**
	 * <p>
	 * 浏览器兼容模式
	 */
	private HttpMultipartMode mode;

	/**
	 * @return the binaryBody
	 */
	public List<BinaryBody> getBinaryBody()
	{
		return binaryBody;
	}

	/**
	 * @return the boundary
	 */
	public String getBoundary()
	{
		return boundary;
	}

	/**
	 * @return the mode
	 */
	public HttpMultipartMode getMode()
	{
		return mode;
	}

	/**
	 * @return the textBody
	 */
	public Map<String, String> getTextBody()
	{
		return textBody;
	}

	/**
	 * @param binaryBody
	 *           the binaryBody to set
	 */
	public void setBinaryBody(final List<BinaryBody> binaryBody)
	{
		this.binaryBody = binaryBody;
	}

	/**
	 * @param boundary
	 *           the boundary to set
	 */
	public void setBoundary(final String boundary)
	{
		this.boundary = boundary;
	}

	/**
	 * @param mode
	 *           the mode to set
	 */
	public void setMode(final HttpMultipartMode mode)
	{
		this.mode = mode;
	}

	/**
	 * @param textBody
	 *           the textBody to set
	 */
	public void setTextBody(final Map<String, String> textBody)
	{
		this.textBody = textBody;
	}
}
