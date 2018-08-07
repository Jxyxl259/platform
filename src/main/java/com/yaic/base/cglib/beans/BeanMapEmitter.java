package com.yaic.base.cglib.beans;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.yaic.base.asm.ClassVisitor;
import com.yaic.base.asm.Label;
import com.yaic.base.asm.Type;
import com.yaic.base.cglib.core.ClassEmitter;
import com.yaic.base.cglib.core.CodeEmitter;
import com.yaic.base.cglib.core.Constants;
import com.yaic.base.cglib.core.EmitUtils;
import com.yaic.base.cglib.core.MethodInfo;
import com.yaic.base.cglib.core.ObjectSwitchCallback;
import com.yaic.base.cglib.core.ReflectUtils;
import com.yaic.base.cglib.core.Signature;
import com.yaic.base.cglib.core.TypeUtils;


class BeanMapEmitter extends ClassEmitter
{
	private static final Type BEAN_MAP = TypeUtils.parseType("com.yaic.base.cglib.beans.BeanMap");
	private static final Type FIXED_KEY_SET = TypeUtils.parseType("com.yaic.base.cglib.beans.FixedKeySet");
	private static final Signature CSTRUCT_OBJECT = TypeUtils.parseConstructor("Object");
	private static final Signature CSTRUCT_STRING_ARRAY = TypeUtils.parseConstructor("String[]");
	private static final Signature BEAN_MAP_GET = TypeUtils.parseSignature("Object get(Object, Object)");
	private static final Signature BEAN_MAP_PUT = TypeUtils.parseSignature("Object put(Object, Object, Object)");
	private static final Signature KEY_SET = TypeUtils.parseSignature("java.util.Set keySet()");
	private static final Signature NEW_INSTANCE = new Signature("newInstance", BEAN_MAP, new Type[]
	{ Constants.TYPE_OBJECT });
	private static final Signature GET_PROPERTY_TYPE = TypeUtils.parseSignature("Class getPropertyType(String)");

	public BeanMapEmitter(final ClassVisitor v, final String className, final Class type, final int require)
	{
		super(v);

		begin_class(Constants.V1_2, Constants.ACC_PUBLIC, className, BEAN_MAP, null, Constants.SOURCE_FILE);
		EmitUtils.null_constructor(this);
		EmitUtils.factory_method(this, NEW_INSTANCE);
		generateConstructor();

		final Map<String, PropertyDescriptor> getters = makePropertyMap(ReflectUtils.getBeanGetters(type));
		final Map<String, PropertyDescriptor> setters = makePropertyMap(ReflectUtils.getBeanSetters(type));
		final Map<String, PropertyDescriptor> allProps = new HashMap<>();
		allProps.putAll(getters);
		allProps.putAll(setters);

		if (require != 0)
		{
			for (final Iterator it = allProps.keySet().iterator(); it.hasNext();)
			{
				final String name = (String) it.next();
				if ((((require & BeanMap.REQUIRE_GETTER) != 0) && !getters.containsKey(name))
						|| (((require & BeanMap.REQUIRE_SETTER) != 0) && !setters.containsKey(name)))
				{
					it.remove();
					getters.remove(name);
					setters.remove(name);
				}
			}
		}
		generateGet(type, getters);
		generatePut(type, setters);

		final String[] allNames = getNames(allProps);
		generateKeySet(allNames);
		generateGetPropertyType(allProps, allNames);
		end_class();
	}

	private Map<String, PropertyDescriptor> makePropertyMap(final PropertyDescriptor[] props)
	{
		final Map<String, PropertyDescriptor> names = new HashMap<>();
		for (int i = 0; i < props.length; i++)
		{
			names.put(props[i].getName(), props[i]);
		}
		return names;
	}

	private String[] getNames(final Map<String, PropertyDescriptor> propertyMap)
	{
		return propertyMap.keySet().toArray(new String[propertyMap.size()]);
	}

	private void generateConstructor()
	{
		final CodeEmitter e = begin_method(Constants.ACC_PUBLIC, CSTRUCT_OBJECT, null);
		e.load_this();
		e.load_arg(0);
		e.super_invoke_constructor(CSTRUCT_OBJECT);
		e.return_value();
		e.end_method();
	}

	private void generateGet(final Class type, final Map<String, PropertyDescriptor> getters)
	{
		final CodeEmitter e = begin_method(Constants.ACC_PUBLIC, BEAN_MAP_GET, null);
		e.load_arg(0);
		e.checkcast(Type.getType(type));
		e.load_arg(1);
		e.checkcast(Constants.TYPE_STRING);
		EmitUtils.string_switch(e, getNames(getters), Constants.SWITCH_STYLE_HASH, new ObjectSwitchCallback()
		{
			public void processCase(final Object key, final Label end)
			{
				final PropertyDescriptor pd = getters.get(key);
				final MethodInfo method = ReflectUtils.getMethodInfo(pd.getReadMethod());
				e.invoke(method);
				e.box(method.getSignature().getReturnType());
				e.return_value();
			}

			public void processDefault()
			{
				e.aconst_null();
				e.return_value();
			}
		});
		e.end_method();
	}

	private void generatePut(final Class type, final Map<String, PropertyDescriptor> setters)
	{
		final CodeEmitter e = begin_method(Constants.ACC_PUBLIC, BEAN_MAP_PUT, null);
		e.load_arg(0);
		e.checkcast(Type.getType(type));
		e.load_arg(1);
		e.checkcast(Constants.TYPE_STRING);
		EmitUtils.string_switch(e, getNames(setters), Constants.SWITCH_STYLE_HASH, new ObjectSwitchCallback()
		{
			public void processCase(final Object key, final Label end)
			{
				final PropertyDescriptor pd = setters.get(key);
				if (pd.getReadMethod() == null)
				{
					e.aconst_null();
				}
				else
				{
					final MethodInfo read = ReflectUtils.getMethodInfo(pd.getReadMethod());
					e.dup();
					e.invoke(read);
					e.box(read.getSignature().getReturnType());
				}
				e.swap(); // move old value behind bean
				e.load_arg(2); // new value
				final MethodInfo write = ReflectUtils.getMethodInfo(pd.getWriteMethod());
				e.unbox(write.getSignature().getArgumentTypes()[0]);
				e.invoke(write);
				e.return_value();
			}

			public void processDefault()
			{
				// fall-through
			}
		});
		e.aconst_null();
		e.return_value();
		e.end_method();
	}

	private void generateKeySet(final String[] allNames)
	{
		// static initializer
		declare_field(Constants.ACC_STATIC | Constants.ACC_PRIVATE, "keys", FIXED_KEY_SET, null);

		CodeEmitter e = begin_static();
		e.new_instance(FIXED_KEY_SET);
		e.dup();
		EmitUtils.push_array(e, allNames);
		e.invoke_constructor(FIXED_KEY_SET, CSTRUCT_STRING_ARRAY);
		e.putfield("keys");
		e.return_value();
		e.end_method();

		// keySet
		e = begin_method(Constants.ACC_PUBLIC, KEY_SET, null);
		e.load_this();
		e.getfield("keys");
		e.return_value();
		e.end_method();
	}

	private void generateGetPropertyType(final Map allProps, final String[] allNames)
	{
		final CodeEmitter e = begin_method(Constants.ACC_PUBLIC, GET_PROPERTY_TYPE, null);
		e.load_arg(0);
		EmitUtils.string_switch(e, allNames, Constants.SWITCH_STYLE_HASH, new ObjectSwitchCallback()
		{
			public void processCase(final Object key, final Label end)
			{
				final PropertyDescriptor pd = (PropertyDescriptor) allProps.get(key);
				EmitUtils.load_class(e, Type.getType(pd.getPropertyType()));
				e.return_value();
			}

			public void processDefault()
			{
				e.aconst_null();
				e.return_value();
			}
		});
		e.end_method();
	}
}
