package com.yaic.base.cglib.reflect;

import java.lang.reflect.Member;


/**
 *
 */
public abstract class FastMember
{
	protected FastClass fc;
	protected Member member;
	protected int index;

	protected FastMember(final FastClass fc, final Member member, final int index)
	{
		this.fc = fc;
		this.member = member;
		this.index = index;
	}

	/**
	 * @return the ParameterTypes
	 */
	public abstract Class[] getParameterTypes();

	/**
	 * @return the ExceptionTypes
	 */
	public abstract Class[] getExceptionTypes();

	/**
	 * @return the index
	 */
	public int getIndex()
	{
		return index;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return member.getName();
	}

	/**
	 * @return the DeclaringClass
	 */
	public Class getDeclaringClass()
	{
		return fc.getJavaClass();
	}

	/**
	 * @return the Modifiers
	 */
	public int getModifiers()
	{
		return member.getModifiers();
	}

	@Override
	public String toString()
	{
		return member.toString();
	}

	@Override
	public int hashCode()
	{
		return member.hashCode();
	}

	@Override
	public boolean equals(final Object o)
	{
		if (o == null || !(o instanceof FastMember))
		{
			return false;
		}
		return member.equals(((FastMember) o).member);
	}
}
