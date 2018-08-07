package com.yaic.base.cglib.core;

import com.yaic.base.asm.Type;


/**
 * A representation of a method signature, containing the method name, return type, and parameter types.
 */
public class Signature
{
	private final String name;
	private final String desc;

	/**
	 * @param name
	 * @param desc
	 */
	public Signature(final String name, final String desc)
	{
		if (name.indexOf('(') >= 0)
		{
			throw new IllegalArgumentException("Name '" + name + "' is invalid");
		}
		this.name = name;
		this.desc = desc;
	}

	/**
	 * @param name
	 * @param returnType
	 * @param argumentTypes
	 */
	public Signature(final String name, final Type returnType, final Type[] argumentTypes)
	{
		this(name, Type.getMethodDescriptor(returnType, argumentTypes));
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the desc
	 */
	public String getDescriptor()
	{
		return desc;
	}

	/**
	 * @return the ReturnType
	 */
	public Type getReturnType()
	{
		return Type.getReturnType(desc);
	}

	/**
	 * @return ArgumentTypes
	 */
	public Type[] getArgumentTypes()
	{
		return Type.getArgumentTypes(desc);
	}

	@Override
	public String toString()
	{
		return name + desc;
	}

	@Override
	public boolean equals(final Object o)
	{
		if (o == null)
		{
			return false;
		}
		if (!(o instanceof Signature))
		{
			return false;
		}
		final Signature other = (Signature) o;
		return name.equals(other.name) && desc.equals(other.desc);
	}

	@Override
	public int hashCode()
	{
		return name.hashCode() ^ desc.hashCode();
	}
}
