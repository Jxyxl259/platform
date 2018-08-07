package com.yaic.base.cglib.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 *
 */
public class FastConstructor extends FastMember
{
	FastConstructor(final FastClass fc, final Constructor constructor)
	{
		super(fc, constructor, fc.getIndex(constructor.getParameterTypes()));
	}

	@Override
	public Class[] getParameterTypes()
	{
		return ((Constructor) member).getParameterTypes();
	}

	@Override
	public Class[] getExceptionTypes()
	{
		return ((Constructor) member).getExceptionTypes();
	}

	/**
	 * @return the newInstance
	 * @throws InvocationTargetException
	 */
	public Object newInstance() throws InvocationTargetException
	{
		return fc.newInstance(index, null);
	}

	/**
	 * @param args
	 * @return the newInstance
	 * @throws InvocationTargetException
	 */
	public Object newInstance(final Object[] args) throws InvocationTargetException
	{
		return fc.newInstance(index, args);
	}

	/**
	 * @return the JavaConstructor
	 */
	public Constructor getJavaConstructor()
	{
		return (Constructor) member;
	}
}
