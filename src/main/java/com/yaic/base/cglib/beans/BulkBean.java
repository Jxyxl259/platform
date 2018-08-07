package com.yaic.base.cglib.beans;

import java.security.ProtectionDomain;

import com.yaic.base.asm.ClassVisitor;
import com.yaic.base.cglib.core.AbstractClassGenerator;
import com.yaic.base.cglib.core.KeyFactory;
import com.yaic.base.cglib.core.ReflectUtils;


/**
 * @author Juozas Baliuka
 */
public abstract class BulkBean
{
	private static final BulkBeanKey KEY_FACTORY = (BulkBeanKey) KeyFactory.create(BulkBeanKey.class);

	interface BulkBeanKey
	{
		public Object newInstance(String target, String[] getters, String[] setters, String[] types);
	}

	protected Class target;
	protected String[] getters, setters;
	protected Class[] types;

	protected BulkBean()
	{
	}

	/**
	 * @param bean
	 * @param values
	 */
	public abstract void getPropertyValues(Object bean, Object[] values);

	/**
	 * @param bean
	 * @param values
	 */
	public abstract void setPropertyValues(Object bean, Object[] values);

	/**
	 * @param bean
	 * @return the property values
	 */
	public Object[] getPropertyValues(final Object bean)
	{
		final Object[] values = new Object[getters.length];
		getPropertyValues(bean, values);
		return values;
	}

	/**
	 * @return the property types
	 */
	public Class[] getPropertyTypes()
	{
		return types.clone();
	}

	/**
	 * @return the getters
	 */
	public String[] getGetters()
	{
		return getters.clone();
	}

	/**
	 * @return the setters
	 */
	public String[] getSetters()
	{
		return setters.clone();
	}

	/**
	 * @param target
	 * @param getters
	 * @param setters
	 * @param types
	 * @return the BulkBean instance
	 */
	public static BulkBean create(final Class target, final String[] getters, final String[] setters, final Class[] types)
	{
		final Generator gen = new Generator();
		gen.setTarget(target);
		gen.setGetters(getters);
		gen.setSetters(setters);
		gen.setTypes(types);
		return gen.create();
	}

	/**
	 *
	 */
	public static class Generator extends AbstractClassGenerator
	{
		private static final Source SOURCE = new Source(BulkBean.class.getName());
		private Class target;
		private String[] getters;
		private String[] setters;
		private Class[] types;

		/**
		 *
		 */
		public Generator()
		{
			super(SOURCE);
		}

		/**
		 * @param target
		 */
		public void setTarget(final Class target)
		{
			this.target = target;
		}

		/**
		 * @param getters
		 */
		public void setGetters(final String[] getters)
		{
			this.getters = getters;
		}

		/**
		 * @param setters
		 */
		public void setSetters(final String[] setters)
		{
			this.setters = setters;
		}

		/**
		 * @param types
		 */
		public void setTypes(final Class[] types)
		{
			this.types = types;
		}

		@Override
		protected ClassLoader getDefaultClassLoader()
		{
			return target.getClassLoader();
		}

		@Override
		protected ProtectionDomain getProtectionDomain()
		{
			return ReflectUtils.getProtectionDomain(target);
		}

		/**
		 * @return the BulkBean instance
		 */
		public BulkBean create()
		{
			setNamePrefix(target.getName());
			final String targetClassName = target.getName();
			final String[] typeClassNames = ReflectUtils.getNames(types);
			final Object key = KEY_FACTORY.newInstance(targetClassName, getters, setters, typeClassNames);
			return (BulkBean) super.create(key);
		}

		public void generateClass(final ClassVisitor v) throws Exception
		{
			new BulkBeanEmitter(v, getClassName(), target, getters, setters, types);
		}

		@Override
		protected Object firstInstance(final Class type)
		{
			final BulkBean instance = (BulkBean) ReflectUtils.newInstance(type);
			instance.target = target;

			final int length = getters.length;
			instance.getters = new String[length];
			System.arraycopy(getters, 0, instance.getters, 0, length);

			instance.setters = new String[length];
			System.arraycopy(setters, 0, instance.setters, 0, length);

			instance.types = new Class[types.length];
			System.arraycopy(types, 0, instance.types, 0, types.length);

			return instance;
		}

		@Override
		protected Object nextInstance(final Object instance)
		{
			return instance;
		}
	}
}
