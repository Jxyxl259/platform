package com.yaic.base.cglib.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 *
 */
public class MethodInfoTransformer implements Transformer
{
	private static final MethodInfoTransformer INSTANCE = new MethodInfoTransformer();

	/**
	 * @return the MethodInfoTransformer instance
	 */
	public static MethodInfoTransformer getInstance()
	{
		return INSTANCE;
	}

	public Object transform(final Object value)
	{
		if (value instanceof Method)
		{
			return ReflectUtils.getMethodInfo((Method) value);
		}
		else if (value instanceof Constructor)
		{
			return ReflectUtils.getMethodInfo((Constructor) value);
		}
		else
		{
			throw new IllegalArgumentException("cannot get method info for " + value);
		}
	}
}
