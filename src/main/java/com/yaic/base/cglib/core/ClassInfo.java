package com.yaic.base.cglib.core;

import com.yaic.base.asm.Type;


/**
 *
 */
public abstract class ClassInfo
{

	protected ClassInfo()
	{
	}

	/**
	 * @return the type
	 */
	public abstract Type getType();

	/**
	 * @return the super type
	 */
	public abstract Type getSuperType();

	/**
	 * @return the interfaces
	 */
	public abstract Type[] getInterfaces();

	/**
	 * @return the modifiers
	 */
	public abstract int getModifiers();

	@Override
	public boolean equals(final Object o)
	{
		if (o == null)
		{
			return false;
		}
		if (!(o instanceof ClassInfo))
		{
			return false;
		}
		return getType().equals(((ClassInfo) o).getType());
	}

	@Override
	public int hashCode()
	{
		return getType().hashCode();
	}

	@Override
	public String toString()
	{
		// TODO: include modifiers, superType, interfaces
		return getType().getClassName();
	}
}
