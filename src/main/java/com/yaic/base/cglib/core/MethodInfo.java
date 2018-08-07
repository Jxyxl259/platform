package com.yaic.base.cglib.core;

import com.yaic.base.asm.Type;


/**
 *
 */
public abstract class MethodInfo
{
	protected MethodInfo()
	{
	}

	/**
	 * @return the ClassInfo
	 */
	public abstract ClassInfo getClassInfo();

	/**
	 * @return the modifiers
	 */
	public abstract int getModifiers();

	/**
	 * @return the signature
	 */
	public abstract Signature getSignature();

	/**
	 * @return the ExceptionTypes
	 */
	public abstract Type[] getExceptionTypes();

	@Override
	public boolean equals(final Object o)
	{
		if (o == null)
		{
			return false;
		}
		if (!(o instanceof MethodInfo))
		{
			return false;
		}
		return getSignature().equals(((MethodInfo) o).getSignature());
	}

	@Override
	public int hashCode()
	{
		return getSignature().hashCode();
	}

	@Override
	public String toString()
	{
		// TODO: include modifiers, exceptions
		return getSignature().toString();
	}
}
