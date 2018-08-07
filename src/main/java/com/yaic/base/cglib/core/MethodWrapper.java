package com.yaic.base.cglib.core;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 *
 */
public class MethodWrapper
{
	private static final MethodWrapperKey KEY_FACTORY = (MethodWrapperKey) KeyFactory.create(MethodWrapperKey.class);

	/** Internal interface, only public due to ClassLoader issues. */
	public interface MethodWrapperKey
	{
		/**
		 * @param name
		 * @param parameterTypes
		 * @param returnType
		 * @return new instance
		 */
		public Object newInstance(String name, String[] parameterTypes, String returnType);
	}

	private MethodWrapper()
	{
	}

	/**
	 * @param method
	 * @return new instance
	 */
	public static Object create(final Method method)
	{
		return KEY_FACTORY.newInstance(method.getName(), ReflectUtils.getNames(method.getParameterTypes()),
				method.getReturnType().getName());
	}

	/**
	 * @param methods
	 * @return the set
	 */
	public static Set createSet(final Collection methods)
	{
		final Set<Object> set = new HashSet<>();
		for (final Iterator it = methods.iterator(); it.hasNext();)
		{
			set.add(create((Method) it.next()));
		}
		return set;
	}
}
