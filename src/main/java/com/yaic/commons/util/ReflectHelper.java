package com.yaic.commons.util;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.util.Assert;

import com.yaic.base.cglib.core.ReflectUtils;
import com.yaic.base.cglib.reflect.FastClass;
import com.yaic.base.cglib.reflect.FastMethod;
import com.yaic.commons.util.MapUtil.ValueCreator;
import com.yaic.servicelayer.util.StringUtil;


/**
 * @author Qu Dihuai
 *
 */
public class ReflectHelper
{
	private final static ConcurrentMap<Class<?>, FastClass> fastClassCache = new ConcurrentHashMap<>();

	/**
	 * @param invokeObject
	 * @param methodName
	 * @param args
	 * @return the result invoked from this method
	 * @throws ReflectiveOperationException
	 */
	public static <S, T> T invoke(final S invokeObject, final String methodName, final Object[] args)
			throws ReflectiveOperationException
	{
		Assert.notNull(invokeObject, "Invoked object must be not null");
		Assert.isTrue(StringUtil.isNotEmpty(methodName), "Invoked method name must be not empty");

		final Class<? extends Object> clazz = invokeObject.getClass();
		final Class[] paramTypes = getClasses(args);
		final Method method = findMethod(clazz, methodName, paramTypes);

		return invoke(invokeObject, method, args);
	}

	/**
	 * @param type
	 * @param methodName
	 * @param args
	 * @return the result invoked from this method
	 * @throws ReflectiveOperationException
	 */
	public static <S, T> T invoke(final Class<S> type, final String methodName, final Object[] args)
			throws ReflectiveOperationException
	{
		Assert.isTrue(StringUtil.isNotEmpty(methodName), "Invoked method name must be not empty");

		final Class[] paramTypes = getClasses(args);
		final Method method = findMethod(type, methodName, paramTypes);

		return invoke(type, method, args);
	}

	/**
	 * @param objects
	 * @return classes
	 */
	public static Class[] getClasses(final Object[] objects)
	{
		final Class[] classes = new Class[objects.length];
		for (int i = 0, len = objects.length; i < len; i++)
		{
			classes[i] = objects[i].getClass();
		}
		return classes;
	}

	/**
	 * @param invokeObject
	 * @param method
	 * @param args
	 * @return the result invoked from this method
	 * @throws ReflectiveOperationException
	 */
	@SuppressWarnings("unchecked")
	public static <S, T> T invoke(final S invokeObject, final Method method, final Object[] args)
			throws ReflectiveOperationException
	{
		Assert.notNull(invokeObject, "Invoked object must be not null");
		Assert.notNull(method, "Invoked method must be not null");

		if (Modifier.isPrivate(method.getModifiers()))
		{
			method.setAccessible(true);
			return (T) method.invoke(invokeObject, args);
		}

		return (T) invokeFastMethod(invokeObject, method, args);
	}

	/**
	 * @param type
	 * @param method
	 * @param args
	 * @return the result invoked from this method
	 * @throws ReflectiveOperationException
	 */
	@SuppressWarnings("unchecked")
	public static <S, T> T invoke(final Class<S> type, final Method method, final Object[] args)
			throws ReflectiveOperationException
	{
		Assert.notNull(type, "Type must be not null");
		Assert.notNull(method, "Invoked method must be not null");

		final S invokeObject = (S) ReflectUtils.newInstance(type);

		if (Modifier.isPrivate(method.getModifiers()))
		{
			method.setAccessible(true);
			return (T) method.invoke(invokeObject, args);
		}

		return (T) invokeFastMethod(invokeObject, method, args);
	}

	/**
	 * According to method name and paramter types, find the method in the specialed class
	 *
	 * @param type
	 * @param name
	 * @param paramTypes
	 * @return the method
	 * @throws NoSuchMethodException
	 *            if can not find target method
	 */
	public static <S> Method findMethod(final Class<S> type, final String name, final Class[] paramTypes)
			throws NoSuchMethodException
	{
		final Method method = type.getMethod(name, paramTypes);
		if (method != null)
		{
			return method;
		}

		Class<?> clazz = type;
		while (clazz != null)
		{
			try
			{
				return clazz.getDeclaredMethod(name, paramTypes);
			}
			catch (final NoSuchMethodException e)
			{
				clazz = clazz.getSuperclass();
			}
		}

		throw new NoSuchMethodException(name);
	}

	/**
	 * Use byte-code technology to implement fast reflection
	 *
	 * @param invokeObject
	 * @param method
	 * @param args
	 * @return the result invoked from
	 * @throws InvocationTargetException
	 */
	private static Object invokeFastMethod(final Object invokeObject, final Method method, final Object[] args)
			throws InvocationTargetException
	{
		final Class<?> type = invokeObject.getClass();
		final FastClass fastClass = MapUtil.createIfAbsent(fastClassCache, type, new ValueCreator<FastClass>()
		{
			@Override
			public FastClass execute()
			{
				return FastClass.create(type);
			}
		});

		final FastMethod fastMethod = fastClass.getMethod(method);
		return fastMethod.invoke(invokeObject, args);
	}

}
