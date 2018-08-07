package com.yaic.servicelayer.http.wrapper;

import java.io.File;
import java.io.InputStream;


/**
 * 上传二进制数据的内容体
 *
 * @author Qu Dihuai
 */
public final class BinaryBody
{
	/**
	 * 上传文件的编号名称
	 */
	private String bodyName;

	/**
	 * 上传文件的文件名
	 */
	private String fileName;

	/**
	 * 上传文件的类型为byte[]
	 */
	private byte[] byteArrayBody;

	/**
	 * 上传文件的类型为File
	 */
	private File fileBody;

	/**
	 * 上传文件的类型为InputStream
	 */
	private InputStream streamBody;

	/**
	 * 上传文件的MIME类型
	 */
	private String mimeType;

	/**
	 * @param bodyName
	 * @param fileName
	 * @param byteArrayBody
	 * @param mimeType
	 */
	public BinaryBody(final String bodyName, final String fileName, final byte[] byteArrayBody, final String mimeType)
	{
		this.bodyName = bodyName;
		this.fileName = fileName;
		this.byteArrayBody = byteArrayBody;
		this.mimeType = mimeType;
	}

	/**
	 * @param bodyName
	 * @param fileName
	 * @param fileBody
	 * @param mimeType
	 */
	public BinaryBody(final String bodyName, final String fileName, final File fileBody, final String mimeType)
	{
		this.bodyName = bodyName;
		this.fileName = fileName;
		this.fileBody = fileBody;
		this.mimeType = mimeType;
	}

	/**
	 * @param bodyName
	 * @param fileName
	 * @param streamBody
	 * @param mimeType
	 */
	public BinaryBody(final String bodyName, final String fileName, final InputStream streamBody, final String mimeType)
	{
		this.bodyName = bodyName;
		this.fileName = fileName;
		this.streamBody = streamBody;
		this.mimeType = mimeType;
	}

	@SuppressWarnings("unused")
	private BinaryBody()
	{
		super();
	}

	/**
	 * @return the bodyName
	 */
	public String getBodyName()
	{
		return bodyName;
	}

	/**
	 * @return the byteArrayBody
	 */
	public byte[] getByteArrayBody()
	{
		return byteArrayBody;
	}

	/**
	 * @return the fileBody
	 */
	public File getFileBody()
	{
		return fileBody;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * @return the mimeType
	 */
	public String getMimeType()
	{
		return mimeType;
	}

	/**
	 * @return the streamBody
	 */
	public InputStream getStreamBody()
	{
		return streamBody;
	}
}
