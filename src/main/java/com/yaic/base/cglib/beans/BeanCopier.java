package com.yaic.base.cglib.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;

import com.yaic.base.asm.ClassVisitor;
import com.yaic.base.asm.Type;
import com.yaic.base.cglib.core.AbstractClassGenerator;
import com.yaic.base.cglib.core.ClassEmitter;
import com.yaic.base.cglib.core.CodeEmitter;
import com.yaic.base.cglib.core.Constants;
import com.yaic.base.cglib.core.Converter;
import com.yaic.base.cglib.core.EmitUtils;
import com.yaic.base.cglib.core.KeyFactory;
import com.yaic.base.cglib.core.Local;
import com.yaic.base.cglib.core.MethodInfo;
import com.yaic.base.cglib.core.ReflectUtils;
import com.yaic.base.cglib.core.Signature;
import com.yaic.base.cglib.core.TypeUtils;


/**
 * @author Chris Nokleberg
 */
public abstract class BeanCopier
{
	private static final BeanCopierKey KEY_FACTORY = (BeanCopierKey) KeyFactory.create(BeanCopierKey.class);
	private static final Type CONVERTER = TypeUtils.parseType("com.yaic.base.cglib.core.Converter");
	private static final Type BEAN_COPIER = TypeUtils.parseType("com.yaic.base.cglib.beans.BeanCopier");
	private static final Signature COPY = new Signature("copy", Type.VOID_TYPE, new Type[]
	{ Constants.TYPE_OBJECT, Constants.TYPE_OBJECT, CONVERTER });
	private static final Signature CONVERT = TypeUtils.parseSignature("Object convert(Object, Class, Object)");

	interface BeanCopierKey
	{
		public Object newInstance(String source, String target, boolean useConverter);
	}

	/**
	 * @param source
	 * @param target
	 * @param useConverter
	 * @return the BeanCopier
	 */
	public static BeanCopier create(final Class source, final Class target, final boolean useConverter)
	{
		final Generator gen = new Generator();
		gen.setSource(source);
		gen.setTarget(target);
		gen.setUseConverter(useConverter);
		return gen.create();
	}

	/**
	 * @param from
	 * @param to
	 * @param converter
	 */
	public abstract void copy(Object from, Object to, Converter converter);

	/**
	 *
	 */
	public static class Generator extends AbstractClassGenerator
	{
		private static final Source SOURCE = new Source(BeanCopier.class.getName());
		private Class source;
		private Class target;
		private boolean useConverter;

		/**
		 *
		 */
		public Generator()
		{
			super(SOURCE);
		}

		/**
		 * @param source
		 */
		public void setSource(final Class source)
		{
			if (!Modifier.isPublic(source.getModifiers()))
			{
				setNamePrefix(source.getName());
			}
			this.source = source;
		}

		/**
		 * @param target
		 */
		public void setTarget(final Class target)
		{
			if (!Modifier.isPublic(target.getModifiers()))
			{
				setNamePrefix(target.getName());
			}

			this.target = target;
		}

		/**
		 * @param useConverter
		 */
		public void setUseConverter(final boolean useConverter)
		{
			this.useConverter = useConverter;
		}

		@Override
		protected ClassLoader getDefaultClassLoader()
		{
			return source.getClassLoader();
		}

		@Override
		protected ProtectionDomain getProtectionDomain()
		{
			return ReflectUtils.getProtectionDomain(source);
		}

		/**
		 * @return the BeanCopier
		 */
		public BeanCopier create()
		{
			final Object key = KEY_FACTORY.newInstance(source.getName(), target.getName(), useConverter);
			return (BeanCopier) super.create(key);
		}

		public void generateClass(final ClassVisitor v)
		{
			final Type sourceType = Type.getType(source);
			final Type targetType = Type.getType(target);
			final ClassEmitter ce = new ClassEmitter(v);
			ce.begin_class(Constants.V1_2, Constants.ACC_PUBLIC, getClassName(), BEAN_COPIER, null, Constants.SOURCE_FILE);

			EmitUtils.null_constructor(ce);
			final CodeEmitter e = ce.begin_method(Constants.ACC_PUBLIC, COPY, null);
			final PropertyDescriptor[] getters = ReflectUtils.getBeanGetters(source);
			final PropertyDescriptor[] setters = ReflectUtils.getBeanSetters(target);

			final Map<String, PropertyDescriptor> names = new HashMap<>();
			for (int i = 0; i < getters.length; i++)
			{
				names.put(getters[i].getName(), getters[i]);
			}

			final Local targetLocal = e.make_local();
			final Local sourceLocal = e.make_local();
			if (useConverter)
			{
				e.load_arg(1);
				e.checkcast(targetType);
				e.store_local(targetLocal);
				e.load_arg(0);
				e.checkcast(sourceType);
				e.store_local(sourceLocal);
			}
			else
			{
				e.load_arg(1);
				e.checkcast(targetType);
				e.load_arg(0);
				e.checkcast(sourceType);
			}
			for (int i = 0; i < setters.length; i++)
			{
				final PropertyDescriptor setter = setters[i];
				final PropertyDescriptor getter = names.get(setter.getName());
				if (getter != null)
				{
					final MethodInfo read = ReflectUtils.getMethodInfo(getter.getReadMethod());
					final MethodInfo write = ReflectUtils.getMethodInfo(setter.getWriteMethod());
					if (useConverter)
					{
						final Type setterType = write.getSignature().getArgumentTypes()[0];
						e.load_local(targetLocal);
						e.load_arg(2);
						e.load_local(sourceLocal);
						e.invoke(read);
						e.box(read.getSignature().getReturnType());
						EmitUtils.load_class(e, setterType);
						e.push(write.getSignature().getName());
						e.invoke_interface(CONVERTER, CONVERT);
						e.unbox_or_zero(setterType);
						e.invoke(write);
					}
					else if (compatible(getter, setter))
					{
						e.dup2();
						e.invoke(read);
						e.invoke(write);
					}
				}
			}
			e.return_value();
			e.end_method();
			ce.end_class();
		}

		private static boolean compatible(final PropertyDescriptor getter, final PropertyDescriptor setter)
		{
			// TODO: allow automatic widening conversions?
			return setter.getPropertyType().isAssignableFrom(getter.getPropertyType());
		}

		@Override
		protected Object firstInstance(final Class type)
		{
			return ReflectUtils.newInstance(type);
		}

		@Override
		protected Object nextInstance(final Object instance)
		{
			return instance;
		}
	}
}
