package com.yaic.base.cglib.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.yaic.base.asm.Type;
import com.yaic.base.cglib.core.Signature;


/**
 *
 */
public class FastMethod extends FastMember
{
	FastMethod(final FastClass fc, final Method method)
	{
		super(fc, method, helper(fc, method));
	}

	@Override
	public Class[] getExceptionTypes()
	{
		return ((Method) member).getExceptionTypes();
	}

	/**
	 * @return the JavaMethod
	 */
	public Method getJavaMethod()
	{
		return (Method) member;
	}

	@Override
	public Class[] getParameterTypes()
	{
		return ((Method) member).getParameterTypes();
	}

	/**
	 * @return the ReturnType
	 */
	public Class getReturnType()
	{
		return ((Method) member).getReturnType();
	}

	/**
	 * @param obj
	 * @param args
	 * @return result
	 * @throws InvocationTargetException
	 */
	public Object invoke(final Object obj, final Object[] args) throws InvocationTargetException
	{
		return fc.invoke(index, obj, args);
	}

	private static int helper(final FastClass fc, final Method method)
	{
		final int index = fc.getIndex(new Signature(method.getName(), Type.getMethodDescriptor(method)));
		if (index < 0)
		{
			throw new IllegalArgumentException("Cannot find method " + method);
		}
		return index;
	}
}
