package com.yaic.servicelayer.http.exception;

/**
 * 在请求服务器, 或处理服务器的响应内容时, 数据收发器发生的异常
 *
 * @author Qu Dihuai
 */
public class TransceiverException extends Exception
{
	private static final long serialVersionUID = TransceiverException.class.getName().hashCode();

	/**
	 * Default constructor
	 */
	public TransceiverException()
	{
		super();
	}

	/**
	 * @param message
	 */
	public TransceiverException(final String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public TransceiverException(final Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TransceiverException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public TransceiverException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
