package com.yaic.base.cglib.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

import com.yaic.base.asm.ClassVisitor;
import com.yaic.base.asm.Type;
import com.yaic.base.cglib.core.AbstractClassGenerator;
import com.yaic.base.cglib.core.Constants;
import com.yaic.base.cglib.core.ReflectUtils;
import com.yaic.base.cglib.core.Signature;


/**
 *
 */
public abstract class FastClass
{
	private Class<?> type;

	protected FastClass()
	{
		throw new Error("Using the FastClass empty constructor--please report to the cglib-devel mailing list");
	}

	protected FastClass(final Class type)
	{
		this.type = type;
	}

	/**
	 * @param type
	 * @return the FastClass instance
	 */
	public static FastClass create(final Class type)
	{
		return create(type.getClassLoader(), type);
	}

	/**
	 * @param loader
	 * @param type
	 * @return the FastClass instance
	 */
	public static FastClass create(final ClassLoader loader, final Class type)
	{
		final Generator gen = new Generator();
		gen.setType(type);
		gen.setClassLoader(loader);
		return gen.create();
	}

	/**
	 *
	 */
	public static class Generator extends AbstractClassGenerator
	{
		private static final Source SOURCE = new Source(FastClass.class.getName());
		private Class type;

		/**
		 *
		 */
		public Generator()
		{
			super(SOURCE);
		}

		/**
		 * @param type
		 */
		public void setType(final Class type)
		{
			this.type = type;
		}

		/**
		 * @return the FastClass instance
		 */
		public FastClass create()
		{
			setNamePrefix(type.getName());
			return (FastClass) super.create(type.getName());
		}

		@Override
		protected ClassLoader getDefaultClassLoader()
		{
			return type.getClassLoader();
		}

		@Override
		protected ProtectionDomain getProtectionDomain()
		{
			return ReflectUtils.getProtectionDomain(type);
		}

		public void generateClass(final ClassVisitor v) throws Exception
		{
			new FastClassEmitter(v, getClassName(), type);
		}

		@Override
		protected Object firstInstance(final Class type)
		{
			return ReflectUtils.newInstance(type, new Class[]
			{ Class.class }, new Object[]
			{ this.type });
		}

		@Override
		protected Object nextInstance(final Object instance)
		{
			return instance;
		}
	}

	/**
	 * @param name
	 * @param parameterTypes
	 * @param obj
	 * @param args
	 * @return the result
	 * @throws InvocationTargetException
	 */
	public Object invoke(final String name, final Class[] parameterTypes, final Object obj, final Object[] args)
			throws InvocationTargetException
	{
		return invoke(getIndex(name, parameterTypes), obj, args);
	}

	/**
	 * @return newInstance
	 * @throws InvocationTargetException
	 */
	public Object newInstance() throws InvocationTargetException
	{
		return newInstance(getIndex(Constants.EMPTY_CLASS_ARRAY), null);
	}

	/**
	 * @param parameterTypes
	 * @param args
	 * @return newInstance
	 * @throws InvocationTargetException
	 */
	public Object newInstance(final Class[] parameterTypes, final Object[] args) throws InvocationTargetException
	{
		return newInstance(getIndex(parameterTypes), args);
	}

	/**
	 * @param method
	 * @return newInstance
	 */
	public FastMethod getMethod(final Method method)
	{
		return new FastMethod(this, method);
	}

	/**
	 * @param constructor
	 * @return FastConstructor instance
	 */
	public FastConstructor getConstructor(final Constructor constructor)
	{
		return new FastConstructor(this, constructor);
	}

	/**
	 * @param name
	 * @param parameterTypes
	 * @return instance
	 */
	public FastMethod getMethod(final String name, final Class[] parameterTypes)
	{
		try
		{
			return getMethod(type.getMethod(name, parameterTypes));
		}
		catch (final NoSuchMethodException e)
		{
			throw new NoSuchMethodError(e.getMessage());
		}
	}

	/**
	 * @param parameterTypes
	 * @return fastConstructor
	 */
	public FastConstructor getConstructor(final Class[] parameterTypes)
	{
		try
		{
			return getConstructor(type.getConstructor(parameterTypes));
		}
		catch (final NoSuchMethodException e)
		{
			throw new NoSuchMethodError(e.getMessage());
		}
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return type.getName();
	}

	/**
	 * @return the class
	 */
	public Class getJavaClass()
	{
		return type;
	}

	@Override
	public String toString()
	{
		return type.toString();
	}

	@Override
	public int hashCode()
	{
		return type.hashCode();
	}

	@Override
	public boolean equals(final Object o)
	{
		if (o == null || !(o instanceof FastClass))
		{
			return false;
		}
		return type.equals(((FastClass) o).type);
	}

	/**
	 * Return the index of the matching method. The index may be used later to invoke the method with less overhead. If
	 * more than one method matches (i.e. they differ by return type only), one is chosen arbitrarily.
	 *
	 * @see #invoke(int, Object, Object[])
	 * @param name
	 *           the method name
	 * @param parameterTypes
	 *           the parameter array
	 * @return the index, or <code>-1</code> if none is found.
	 */
	public abstract int getIndex(String name, Class[] parameterTypes);

	/**
	 * Return the index of the matching constructor. The index may be used later to create a new instance with less
	 * overhead.
	 *
	 * @see #newInstance(int, Object[])
	 * @param parameterTypes
	 *           the parameter array
	 * @return the constructor index, or <code>-1</code> if none is found.
	 */
	public abstract int getIndex(Class[] parameterTypes);

	/**
	 * Invoke the method with the specified index.
	 *
	 * @param index
	 *           the method index
	 * @param obj
	 *           the object the underlying method is invoked from
	 * @param args
	 *           the arguments used for the method call
	 * @return the object
	 * @throws java.lang.reflect.InvocationTargetException
	 *            if the underlying method throws an exception
	 */
	public abstract Object invoke(int index, Object obj, Object[] args) throws InvocationTargetException;

	/**
	 * Create a new instance using the specified constructor index and arguments.
	 *
	 * @param index
	 *           the constructor index
	 * @param args
	 *           the arguments passed to the constructor
	 * @return the object
	 * @throws java.lang.reflect.InvocationTargetException
	 *            if the constructor throws an exception
	 */
	public abstract Object newInstance(int index, Object[] args) throws InvocationTargetException;

	/**
	 * @param sig
	 * @return the index
	 */
	public abstract int getIndex(Signature sig);

	/**
	 * Returns the maximum method index for this class.
	 *
	 * @return the max index
	 */
	public abstract int getMaxIndex();

	protected static String getSignatureWithoutReturnType(final String name, final Class[] parameterTypes)
	{
		final StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append('(');
		for (int i = 0; i < parameterTypes.length; i++)
		{
			sb.append(Type.getDescriptor(parameterTypes[i]));
		}
		sb.append(')');
		return sb.toString();
	}
}
