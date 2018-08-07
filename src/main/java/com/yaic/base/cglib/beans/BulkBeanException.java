package com.yaic.base.cglib.beans;

/**
 *
 */
public class BulkBeanException extends RuntimeException
{
	private static final long serialVersionUID = -7386868523987597049L;

	private final int index;
	private Throwable cause;

	/**
	 * @param message
	 * @param index
	 */
	public BulkBeanException(final String message, final int index)
	{
		super(message);
		this.index = index;
	}

	/**
	 * @param cause
	 * @param index
	 */
	public BulkBeanException(final Throwable cause, final int index)
	{
		super(cause.getMessage());
		this.index = index;
		this.cause = cause;
	}

	/**
	 * @return the index
	 */
	public int getIndex()
	{
		return index;
	}

	@Override
	public Throwable getCause()
	{
		return cause;
	}
}
