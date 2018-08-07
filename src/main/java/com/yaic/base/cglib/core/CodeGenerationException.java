package com.yaic.base.cglib.core;

/**
 * @version $Id: CodeGenerationException.java,v 1.3 2004/06/24 21:15:21 herbyderby Exp $
 */
public class CodeGenerationException extends RuntimeException
{
	private static final long serialVersionUID = 1420962834701027885L;

	private final Throwable cause;

	/**
	 * @param cause
	 */
	public CodeGenerationException(final Throwable cause)
	{
		super(cause.getClass().getName() + "-->" + cause.getMessage());
		this.cause = cause;
	}

	@Override
	public Throwable getCause()
	{
		return cause;
	}
}
