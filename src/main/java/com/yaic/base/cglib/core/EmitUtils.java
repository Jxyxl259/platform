package com.yaic.base.cglib.core;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yaic.base.asm.Label;
import com.yaic.base.asm.Type;
import com.yaic.base.cglib.core.internal.CustomizerRegistry;


/**
 *
 */
public class EmitUtils
{
	private static final Signature CSTRUCT_NULL = TypeUtils.parseConstructor("");
	private static final Signature CSTRUCT_THROWABLE = TypeUtils.parseConstructor("Throwable");

	private static final Signature GET_NAME = TypeUtils.parseSignature("String getName()");
	private static final Signature HASH_CODE = TypeUtils.parseSignature("int hashCode()");
	private static final Signature EQUALS = TypeUtils.parseSignature("boolean equals(Object)");
	private static final Signature STRING_LENGTH = TypeUtils.parseSignature("int length()");
	private static final Signature STRING_CHAR_AT = TypeUtils.parseSignature("char charAt(int)");
	private static final Signature FOR_NAME = TypeUtils.parseSignature("Class forName(String)");
	private static final Signature DOUBLE_TO_LONG_BITS = TypeUtils.parseSignature("long doubleToLongBits(double)");
	private static final Signature FLOAT_TO_INT_BITS = TypeUtils.parseSignature("int floatToIntBits(float)");
	private static final Signature TO_STRING = TypeUtils.parseSignature("String toString()");
	private static final Signature APPEND_STRING = TypeUtils.parseSignature("StringBuffer append(String)");
	private static final Signature APPEND_INT = TypeUtils.parseSignature("StringBuffer append(int)");
	private static final Signature APPEND_DOUBLE = TypeUtils.parseSignature("StringBuffer append(double)");
	private static final Signature APPEND_FLOAT = TypeUtils.parseSignature("StringBuffer append(float)");
	private static final Signature APPEND_CHAR = TypeUtils.parseSignature("StringBuffer append(char)");
	private static final Signature APPEND_LONG = TypeUtils.parseSignature("StringBuffer append(long)");
	private static final Signature APPEND_BOOLEAN = TypeUtils.parseSignature("StringBuffer append(boolean)");
	private static final Signature LENGTH = TypeUtils.parseSignature("int length()");
	private static final Signature SET_LENGTH = TypeUtils.parseSignature("void setLength(int)");
	private static final Signature GET_DECLARED_METHOD = TypeUtils
			.parseSignature("java.lang.reflect.Method getDeclaredMethod(String, Class[])");

	/**
	 *
	 */
	public static final ArrayDelimiters DEFAULT_DELIMITERS = new ArrayDelimiters("{", ", ", "}");

	private EmitUtils()
	{
	}

	/**
	 * @param ce
	 * @param sig
	 */
	public static void factory_method(final ClassEmitter ce, final Signature sig)
	{
		final CodeEmitter e = ce.begin_method(Constants.ACC_PUBLIC, sig, null);
		e.new_instance_this();
		e.dup();
		e.load_args();
		e.invoke_constructor_this(TypeUtils.parseConstructor(sig.getArgumentTypes()));
		e.return_value();
		e.end_method();
	}

	/**
	 * @param ce
	 */
	public static void null_constructor(final ClassEmitter ce)
	{
		final CodeEmitter e = ce.begin_method(Constants.ACC_PUBLIC, CSTRUCT_NULL, null);
		e.load_this();
		e.super_invoke_constructor();
		e.return_value();
		e.end_method();
	}

	/**
	 * Process an array on the stack. Assumes the top item on the stack is an array of the specified type. For each
	 * element in the array, puts the element on the stack and triggers the callback.
	 *
	 * @param e
	 *
	 * @param type
	 *           the type of the array (type.isArray() must be true)
	 * @param callback
	 *           the callback triggered for each element
	 */
	public static void process_array(final CodeEmitter e, final Type type, final ProcessArrayCallback callback)
	{
		final Type componentType = TypeUtils.getComponentType(type);
		final Local array = e.make_local();
		final Local loopvar = e.make_local(Type.INT_TYPE);
		final Label loopbody = e.make_label();
		final Label checkloop = e.make_label();
		e.store_local(array);
		e.push(0);
		e.store_local(loopvar);
		e.goTo(checkloop);

		e.mark(loopbody);
		e.load_local(array);
		e.load_local(loopvar);
		e.array_load(componentType);
		callback.processElement(componentType);
		e.iinc(loopvar, 1);

		e.mark(checkloop);
		e.load_local(loopvar);
		e.load_local(array);
		e.arraylength();
		e.if_icmp(e.LT, loopbody);
	}

	/**
	 * Process two arrays on the stack in parallel. Assumes the top two items on the stack are arrays of the specified
	 * class. The arrays must be the same length. For each pair of elements in the arrays, puts the pair on the stack and
	 * triggers the callback.
	 *
	 * @param e
	 *
	 * @param type
	 *           the type of the arrays (type.isArray() must be true)
	 * @param callback
	 *           the callback triggered for each pair of elements
	 */
	public static void process_arrays(final CodeEmitter e, final Type type, final ProcessArrayCallback callback)
	{
		final Type componentType = TypeUtils.getComponentType(type);
		final Local array1 = e.make_local();
		final Local array2 = e.make_local();
		final Local loopvar = e.make_local(Type.INT_TYPE);
		final Label loopbody = e.make_label();
		final Label checkloop = e.make_label();
		e.store_local(array1);
		e.store_local(array2);
		e.push(0);
		e.store_local(loopvar);
		e.goTo(checkloop);

		e.mark(loopbody);
		e.load_local(array1);
		e.load_local(loopvar);
		e.array_load(componentType);
		e.load_local(array2);
		e.load_local(loopvar);
		e.array_load(componentType);
		callback.processElement(componentType);
		e.iinc(loopvar, 1);

		e.mark(checkloop);
		e.load_local(loopvar);
		e.load_local(array1);
		e.arraylength();
		e.if_icmp(e.LT, loopbody);
	}

	/**
	 * @param e
	 * @param strings
	 * @param switchStyle
	 * @param callback
	 */
	public static void string_switch(final CodeEmitter e, final String[] strings, final int switchStyle,
			final ObjectSwitchCallback callback)
	{
		try
		{
			switch (switchStyle)
			{
				case Constants.SWITCH_STYLE_TRIE:
					string_switch_trie(e, strings, callback);
					break;
				case Constants.SWITCH_STYLE_HASH:
					string_switch_hash(e, strings, callback, false);
					break;
				case Constants.SWITCH_STYLE_HASHONLY:
					string_switch_hash(e, strings, callback, true);
					break;
				default:
					throw new IllegalArgumentException("unknown switch style " + switchStyle);
			}
		}
		catch (final RuntimeException ex)
		{
			throw ex;
		}
		catch (final Error ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			throw new CodeGenerationException(ex);
		}
	}

	private static void string_switch_trie(final CodeEmitter e, final String[] strings, final ObjectSwitchCallback callback)
			throws Exception
	{
		final Label def = e.make_label();
		final Label end = e.make_label();
		final Map buckets = bucket(Arrays.asList(strings), new Transformer()
		{
			public Object transform(final Object value)
			{
				return new Integer(((String) value).length());
			}
		});
		e.dup();
		e.invoke_virtual(Constants.TYPE_STRING, STRING_LENGTH);
		e.process_switch(getSwitchKeys(buckets), new ProcessSwitchCallback()
		{
			public void processCase(final int key, final Label ignore_end) throws Exception
			{
				final List bucket = (List) buckets.get(new Integer(key));
				stringSwitchHelper(e, bucket, callback, def, end, 0);
			}

			public void processDefault()
			{
				e.goTo(def);
			}
		});
		e.mark(def);
		e.pop();
		callback.processDefault();
		e.mark(end);
	}

	private static void stringSwitchHelper(final CodeEmitter e, final List strings, final ObjectSwitchCallback callback,
			final Label def, final Label end, final int index) throws Exception
	{
		final int len = ((String) strings.get(0)).length();
		final Map buckets = bucket(strings, new Transformer()
		{
			public Object transform(final Object value)
			{
				return new Integer(((String) value).charAt(index));
			}
		});
		e.dup();
		e.push(index);
		e.invoke_virtual(Constants.TYPE_STRING, STRING_CHAR_AT);
		e.process_switch(getSwitchKeys(buckets), new ProcessSwitchCallback()
		{
			public void processCase(final int key, final Label ignore_end) throws Exception
			{
				final List bucket = (List) buckets.get(new Integer(key));
				if (index + 1 == len)
				{
					e.pop();
					callback.processCase(bucket.get(0), end);
				}
				else
				{
					stringSwitchHelper(e, bucket, callback, def, end, index + 1);
				}
			}

			public void processDefault()
			{
				e.goTo(def);
			}
		});
	}

	static int[] getSwitchKeys(final Map buckets)
	{
		final int[] keys = new int[buckets.size()];
		int index = 0;
		for (final Iterator it = buckets.keySet().iterator(); it.hasNext();)
		{
			keys[index++] = ((Integer) it.next()).intValue();
		}
		Arrays.sort(keys);
		return keys;
	}

	private static void string_switch_hash(final CodeEmitter e, final String[] strings, final ObjectSwitchCallback callback,
			final boolean skipEquals) throws Exception
	{
		final Map buckets = bucket(Arrays.asList(strings), new Transformer()
		{
			public Object transform(final Object value)
			{
				return new Integer(value.hashCode());
			}
		});
		final Label def = e.make_label();
		final Label end = e.make_label();
		e.dup();
		e.invoke_virtual(Constants.TYPE_OBJECT, HASH_CODE);
		e.process_switch(getSwitchKeys(buckets), new ProcessSwitchCallback()
		{
			public void processCase(final int key, final Label ignore_end) throws Exception
			{
				final List bucket = (List) buckets.get(new Integer(key));
				Label next = null;
				if (skipEquals && bucket.size() == 1)
				{
					if (skipEquals)
					{
						e.pop();
					}
					callback.processCase(bucket.get(0), end);
				}
				else
				{
					for (final Iterator it = bucket.iterator(); it.hasNext();)
					{
						final String string = (String) it.next();
						if (next != null)
						{
							e.mark(next);
						}
						if (it.hasNext())
						{
							e.dup();
						}
						e.push(string);
						e.invoke_virtual(Constants.TYPE_OBJECT, EQUALS);
						if (it.hasNext())
						{
							e.if_jump(e.EQ, next = e.make_label());
							e.pop();
						}
						else
						{
							e.if_jump(e.EQ, def);
						}
						callback.processCase(string, end);
					}
				}
			}

			public void processDefault()
			{
				e.pop();
			}
		});
		e.mark(def);
		callback.processDefault();
		e.mark(end);
	}

	/**
	 * @param e
	 */
	public static void load_class_this(final CodeEmitter e)
	{
		load_class_helper(e, e.getClassEmitter().getClassType());
	}

	/**
	 * @param e
	 * @param type
	 */
	public static void load_class(final CodeEmitter e, final Type type)
	{
		if (TypeUtils.isPrimitive(type))
		{
			if (type == Type.VOID_TYPE)
			{
				throw new IllegalArgumentException("cannot load void type");
			}
			e.getstatic(TypeUtils.getBoxedType(type), "TYPE", Constants.TYPE_CLASS);
		}
		else
		{
			load_class_helper(e, type);
		}
	}

	private static void load_class_helper(final CodeEmitter e, final Type type)
	{
		if (e.isStaticHook())
		{
			// have to fall back on non-optimized load
			e.push(TypeUtils.emulateClassGetName(type));
			e.invoke_static(Constants.TYPE_CLASS, FOR_NAME);
		}
		else
		{
			final ClassEmitter ce = e.getClassEmitter();
			final String typeName = TypeUtils.emulateClassGetName(type);

			// TODO: can end up with duplicated field names when using chained transformers; incorporate static hook # somehow
			final String fieldName = "CGLIB$load_class$" + TypeUtils.escapeType(typeName);
			if (!ce.isFieldDeclared(fieldName))
			{
				ce.declare_field(Constants.PRIVATE_FINAL_STATIC, fieldName, Constants.TYPE_CLASS, null);
				final CodeEmitter hook = ce.getStaticHook();
				hook.push(typeName);
				hook.invoke_static(Constants.TYPE_CLASS, FOR_NAME);
				hook.putstatic(ce.getClassType(), fieldName, Constants.TYPE_CLASS);
			}
			e.getfield(fieldName);
		}
	}

	/**
	 * @param e
	 * @param array
	 */
	public static void push_array(final CodeEmitter e, final Object[] array)
	{
		e.push(array.length);
		e.newarray(Type.getType(remapComponentType(array.getClass().getComponentType())));
		for (int i = 0; i < array.length; i++)
		{
			e.dup();
			e.push(i);
			push_object(e, array[i]);
			e.aastore();
		}
	}

	private static Class remapComponentType(final Class componentType)
	{
		if (componentType.equals(Type.class))
		{
			return Class.class;
		}
		return componentType;
	}

	/**
	 * @param e
	 * @param obj
	 */
	public static void push_object(final CodeEmitter e, final Object obj)
	{
		if (obj == null)
		{
			e.aconst_null();
		}
		else
		{
			final Class type = obj.getClass();
			if (type.isArray())
			{
				push_array(e, (Object[]) obj);
			}
			else if (obj instanceof String)
			{
				e.push((String) obj);
			}
			else if (obj instanceof Type)
			{
				load_class(e, (Type) obj);
			}
			else if (obj instanceof Class)
			{
				load_class(e, Type.getType((Class) obj));
			}
			else if (obj instanceof BigInteger)
			{
				e.new_instance(Constants.TYPE_BIG_INTEGER);
				e.dup();
				e.push(obj.toString());
				e.invoke_constructor(Constants.TYPE_BIG_INTEGER);
			}
			else if (obj instanceof BigDecimal)
			{
				e.new_instance(Constants.TYPE_BIG_DECIMAL);
				e.dup();
				e.push(obj.toString());
				e.invoke_constructor(Constants.TYPE_BIG_DECIMAL);
			}
			else
			{
				throw new IllegalArgumentException("unknown type: " + obj.getClass());
			}
		}
	}

	/**
	 * @param e
	 * @param type
	 * @param multiplier
	 * @param registry
	 */
	public static void hash_code(final CodeEmitter e, final Type type, final int multiplier, final CustomizerRegistry registry)
	{
		if (TypeUtils.isArray(type))
		{
			hash_array(e, type, multiplier, registry);
		}
		else
		{
			e.swap(Type.INT_TYPE, type);
			e.push(multiplier);
			e.math(e.MUL, Type.INT_TYPE);
			e.swap(type, Type.INT_TYPE);
			if (TypeUtils.isPrimitive(type))
			{
				hash_primitive(e, type);
			}
			else
			{
				hash_object(e, type, registry);
			}
			e.math(e.ADD, Type.INT_TYPE);
		}
	}

	private static void hash_array(final CodeEmitter e, final Type type, final int multiplier, final CustomizerRegistry registry)
	{
		final Label skip = e.make_label();
		final Label end = e.make_label();
		e.dup();
		e.ifnull(skip);
		EmitUtils.process_array(e, type, new ProcessArrayCallback()
		{
			public void processElement(final Type type)
			{
				hash_code(e, type, multiplier, registry);
			}
		});
		e.goTo(end);
		e.mark(skip);
		e.pop();
		e.mark(end);
	}

	private static void hash_object(final CodeEmitter e, final Type type, final CustomizerRegistry registry)
	{
		// (f == null) ? 0 : f.hashCode();
		final Label skip = e.make_label();
		final Label end = e.make_label();
		e.dup();
		e.ifnull(skip);
		boolean customHashCode = false;
		for (final HashCodeCustomizer customizer : registry.get(HashCodeCustomizer.class))
		{
			if (customizer.customize(e, type))
			{
				customHashCode = true;
				break;
			}
		}
		if (!customHashCode)
		{
			for (final Customizer customizer : registry.get(Customizer.class))
			{
				customizer.customize(e, type);
			}
			e.invoke_virtual(Constants.TYPE_OBJECT, HASH_CODE);
		}
		e.goTo(end);
		e.mark(skip);
		e.pop();
		e.push(0);
		e.mark(end);
	}

	private static void hash_primitive(final CodeEmitter e, final Type type)
	{
		switch (type.getSort())
		{
			case Type.BOOLEAN:
				// f ? 0 : 1
				e.push(1);
				e.math(e.XOR, Type.INT_TYPE);
				break;
			case Type.FLOAT:
				// Float.floatToIntBits(f)
				e.invoke_static(Constants.TYPE_FLOAT, FLOAT_TO_INT_BITS);
				break;
			case Type.DOUBLE:
				// Double.doubleToLongBits(f), hash_code(Long.TYPE)
				e.invoke_static(Constants.TYPE_DOUBLE, DOUBLE_TO_LONG_BITS);
				// fall through
			case Type.LONG:
				hash_long(e);
		}
	}

	private static void hash_long(final CodeEmitter e)
	{
		// (int)(f ^ (f >>> 32))
		e.dup2();
		e.push(32);
		e.math(e.USHR, Type.LONG_TYPE);
		e.math(e.XOR, Type.LONG_TYPE);
		e.cast_numeric(Type.LONG_TYPE, Type.INT_TYPE);
	}

	/**
	 * Branches to the specified label if the top two items on the stack are not equal. The items must both be of the
	 * specified class. Equality is determined by comparing primitive values directly and by invoking the
	 * <code>equals</code> method for Objects. Arrays are recursively processed in the same manner.
	 *
	 * @param e
	 * @param type
	 * @param notEquals
	 * @param registry
	 */
	public static void not_equals(final CodeEmitter e, final Type type, final Label notEquals, final CustomizerRegistry registry)
	{
		(new ProcessArrayCallback()
		{
			public void processElement(final Type type)
			{
				not_equals_helper(e, type, notEquals, registry, this);
			}
		}).processElement(type);
	}

	private static void not_equals_helper(final CodeEmitter e, final Type type, final Label notEquals,
			final CustomizerRegistry registry, final ProcessArrayCallback callback)
	{
		if (TypeUtils.isPrimitive(type))
		{
			e.if_cmp(type, e.NE, notEquals);
		}
		else
		{
			final Label end = e.make_label();
			nullcmp(e, notEquals, end);
			if (TypeUtils.isArray(type))
			{
				final Label checkContents = e.make_label();
				e.dup2();
				e.arraylength();
				e.swap();
				e.arraylength();
				e.if_icmp(e.EQ, checkContents);
				e.pop2();
				e.goTo(notEquals);
				e.mark(checkContents);
				EmitUtils.process_arrays(e, type, callback);
			}
			else
			{
				final List<Customizer> customizers = registry.get(Customizer.class);
				if (!customizers.isEmpty())
				{
					for (final Customizer customizer : customizers)
					{
						customizer.customize(e, type);
					}
					e.swap();
					for (final Customizer customizer : customizers)
					{
						customizer.customize(e, type);
					}
				}
				e.invoke_virtual(Constants.TYPE_OBJECT, EQUALS);
				e.if_jump(e.EQ, notEquals);
			}
			e.mark(end);
		}
	}

	/**
	 * If both objects on the top of the stack are non-null, does nothing. If one is null, or both are null, both are
	 * popped off and execution branches to the respective label.
	 *
	 * @param oneNull
	 *           label to branch to if only one of the objects is null
	 * @param bothNull
	 *           label to branch to if both of the objects are null
	 */
	private static void nullcmp(final CodeEmitter e, final Label oneNull, final Label bothNull)
	{
		e.dup2();
		final Label nonNull = e.make_label();
		final Label oneNullHelper = e.make_label();
		final Label end = e.make_label();
		e.ifnonnull(nonNull);
		e.ifnonnull(oneNullHelper);
		e.pop2();
		e.goTo(bothNull);

		e.mark(nonNull);
		e.ifnull(oneNullHelper);
		e.goTo(end);

		e.mark(oneNullHelper);
		e.pop2();
		e.goTo(oneNull);

		e.mark(end);
	}

	/**
	 * @param e
	 * @param type
	 * @param delims
	 * @param registry
	 */
	public static void append_string(final CodeEmitter e, final Type type, final ArrayDelimiters delims,
			final CustomizerRegistry registry)
	{
		final ArrayDelimiters d = (delims != null) ? delims : DEFAULT_DELIMITERS;
		final ProcessArrayCallback callback = new ProcessArrayCallback()
		{
			public void processElement(final Type type)
			{
				append_string_helper(e, type, d, registry, this);
				e.push(d.inside);
				e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_STRING);
			}
		};
		append_string_helper(e, type, d, registry, callback);
	}

	private static void append_string_helper(final CodeEmitter e, final Type type, final ArrayDelimiters delims,
			final CustomizerRegistry registry, final ProcessArrayCallback callback)
	{
		final Label skip = e.make_label();
		final Label end = e.make_label();
		if (TypeUtils.isPrimitive(type))
		{
			switch (type.getSort())
			{
				case Type.INT:
				case Type.SHORT:
				case Type.BYTE:
					e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_INT);
					break;
				case Type.DOUBLE:
					e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_DOUBLE);
					break;
				case Type.FLOAT:
					e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_FLOAT);
					break;
				case Type.LONG:
					e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_LONG);
					break;
				case Type.BOOLEAN:
					e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_BOOLEAN);
					break;
				case Type.CHAR:
					e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_CHAR);
					break;
			}
		}
		else if (TypeUtils.isArray(type))
		{
			e.dup();
			e.ifnull(skip);
			e.swap();
			if (delims != null && delims.before != null && !"".equals(delims.before))
			{
				e.push(delims.before);
				e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_STRING);
				e.swap();
			}
			EmitUtils.process_array(e, type, callback);
			shrinkStringBuffer(e, 2);
			if (delims != null && delims.after != null && !"".equals(delims.after))
			{
				e.push(delims.after);
				e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_STRING);
			}
		}
		else
		{
			e.dup();
			e.ifnull(skip);
			for (final Customizer customizer : registry.get(Customizer.class))
			{
				customizer.customize(e, type);
			}
			e.invoke_virtual(Constants.TYPE_OBJECT, TO_STRING);
			e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_STRING);
		}
		e.goTo(end);
		e.mark(skip);
		e.pop();
		e.push("null");
		e.invoke_virtual(Constants.TYPE_STRING_BUFFER, APPEND_STRING);
		e.mark(end);
	}

	private static void shrinkStringBuffer(final CodeEmitter e, final int amt)
	{
		e.dup();
		e.dup();
		e.invoke_virtual(Constants.TYPE_STRING_BUFFER, LENGTH);
		e.push(amt);
		e.math(e.SUB, Type.INT_TYPE);
		e.invoke_virtual(Constants.TYPE_STRING_BUFFER, SET_LENGTH);
	}

	/**
	 *
	 */
	public static class ArrayDelimiters
	{
		private final String before;
		private final String inside;
		private final String after;

		/**
		 * @param before
		 * @param inside
		 * @param after
		 */
		public ArrayDelimiters(final String before, final String inside, final String after)
		{
			this.before = before;
			this.inside = inside;
			this.after = after;
		}
	}

	/**
	 * @param e
	 * @param method
	 */
	public static void load_method(final CodeEmitter e, final MethodInfo method)
	{
		load_class(e, method.getClassInfo().getType());
		e.push(method.getSignature().getName());
		push_object(e, method.getSignature().getArgumentTypes());
		e.invoke_virtual(Constants.TYPE_CLASS, GET_DECLARED_METHOD);
	}

	private interface ParameterTyper
	{
		Type[] getParameterTypes(MethodInfo member);
	}

	/**
	 * @param e
	 * @param methods
	 * @param callback
	 */
	public static void method_switch(final CodeEmitter e, final List methods, final ObjectSwitchCallback callback)
	{
		member_switch_helper(e, methods, callback, true);
	}

	/**
	 * @param e
	 * @param constructors
	 * @param callback
	 */
	public static void constructor_switch(final CodeEmitter e, final List constructors, final ObjectSwitchCallback callback)
	{
		member_switch_helper(e, constructors, callback, false);
	}

	private static void member_switch_helper(final CodeEmitter e, final List members, final ObjectSwitchCallback callback,
			final boolean useName)
	{
		try
		{
			final Map<MethodInfo, Type[]> cache = new HashMap<>();
			final ParameterTyper cached = new ParameterTyper()
			{
				public Type[] getParameterTypes(final MethodInfo member)
				{
					Type[] types = cache.get(member);
					if (types == null)
					{
						cache.put(member, types = member.getSignature().getArgumentTypes());
					}
					return types;
				}
			};
			final Label def = e.make_label();
			final Label end = e.make_label();
			if (useName)
			{
				e.swap();
				final Map<?, ?> buckets = bucket(members, new Transformer()
				{
					public Object transform(final Object value)
					{
						return ((MethodInfo) value).getSignature().getName();
					}
				});
				final String[] names = buckets.keySet().toArray(new String[buckets.size()]);
				EmitUtils.string_switch(e, names, Constants.SWITCH_STYLE_HASH, new ObjectSwitchCallback()
				{
					public void processCase(final Object key, final Label dontUseEnd) throws Exception
					{
						member_helper_size(e, (List) buckets.get(key), callback, cached, def, end);
					}

					public void processDefault() throws Exception
					{
						e.goTo(def);
					}
				});
			}
			else
			{
				member_helper_size(e, members, callback, cached, def, end);
			}
			e.mark(def);
			e.pop();
			callback.processDefault();
			e.mark(end);
		}
		catch (final RuntimeException ex)
		{
			throw ex;
		}
		catch (final Error ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			throw new CodeGenerationException(ex);
		}
	}

	private static void member_helper_size(final CodeEmitter e, final List members, final ObjectSwitchCallback callback,
			final ParameterTyper typer, final Label def, final Label end) throws Exception
	{
		final Map buckets = bucket(members, new Transformer()
		{
			public Object transform(final Object value)
			{
				return new Integer(typer.getParameterTypes((MethodInfo) value).length);
			}
		});
		e.dup();
		e.arraylength();
		e.process_switch(EmitUtils.getSwitchKeys(buckets), new ProcessSwitchCallback()
		{
			public void processCase(final int key, final Label dontUseEnd) throws Exception
			{
				final List bucket = (List) buckets.get(new Integer(key));
				member_helper_type(e, bucket, callback, typer, def, end, new BitSet());
			}

			public void processDefault() throws Exception
			{
				e.goTo(def);
			}
		});
	}

	private static void member_helper_type(final CodeEmitter e, final List members, final ObjectSwitchCallback callback,
			final ParameterTyper typer, final Label def, final Label end, final BitSet checked) throws Exception
	{
		if (members.size() == 1)
		{
			final MethodInfo member = (MethodInfo) members.get(0);
			final Type[] types = typer.getParameterTypes(member);
			// need to check classes that have not already been checked via switches
			for (int i = 0; i < types.length; i++)
			{
				if (checked == null || !checked.get(i))
				{
					e.dup();
					e.aaload(i);
					e.invoke_virtual(Constants.TYPE_CLASS, GET_NAME);
					e.push(TypeUtils.emulateClassGetName(types[i]));
					e.invoke_virtual(Constants.TYPE_OBJECT, EQUALS);
					e.if_jump(e.EQ, def);
				}
			}
			e.pop();
			callback.processCase(member, end);
		}
		else
		{
			// choose the index that has the best chance of uniquely identifying member
			final Type[] example = typer.getParameterTypes((MethodInfo) members.get(0));
			Map<?, ?> buckets = null;
			int index = -1;
			for (int i = 0; i < example.length; i++)
			{
				final int j = i;
				final Map test = bucket(members, new Transformer()
				{
					public Object transform(final Object value)
					{
						return TypeUtils.emulateClassGetName(typer.getParameterTypes((MethodInfo) value)[j]);
					}
				});
				if (buckets == null || test.size() > buckets.size())
				{
					buckets = test;
					index = i;
				}
			}
			if (buckets == null || buckets.size() == 1)
			{
				// TODO: switch by returnType
				// must have two methods with same name, types, and different return types
				e.goTo(def);
			}
			else
			{
				checked.set(index);

				e.dup();
				e.aaload(index);
				e.invoke_virtual(Constants.TYPE_CLASS, GET_NAME);

				final Map fbuckets = buckets;
				final String[] names = buckets.keySet().toArray(new String[buckets.size()]);
				EmitUtils.string_switch(e, names, Constants.SWITCH_STYLE_HASH, new ObjectSwitchCallback()
				{
					public void processCase(final Object key, final Label dontUseEnd) throws Exception
					{
						member_helper_type(e, (List) fbuckets.get(key), callback, typer, def, end, checked);
					}

					public void processDefault() throws Exception
					{
						e.goTo(def);
					}
				});
			}
		}
	}

	/**
	 * @param block
	 * @param wrapper
	 */
	public static void wrap_throwable(final Block block, final Type wrapper)
	{
		final CodeEmitter e = block.getCodeEmitter();
		e.catch_exception(block, Constants.TYPE_THROWABLE);
		e.new_instance(wrapper);
		e.dup_x1();
		e.swap();
		e.invoke_constructor(wrapper, CSTRUCT_THROWABLE);
		e.athrow();
	}

	/**
	 * @param ce
	 * @param names
	 * @param types
	 */
	public static void add_properties(final ClassEmitter ce, final String[] names, final Type[] types)
	{
		for (int i = 0; i < names.length; i++)
		{
			final String fieldName = "$cglib_prop_" + names[i];
			ce.declare_field(Constants.ACC_PRIVATE, fieldName, types[i], null);
			EmitUtils.add_property(ce, names[i], types[i], fieldName);
		}
	}

	/**
	 * @param ce
	 * @param name
	 * @param type
	 * @param fieldName
	 */
	public static void add_property(final ClassEmitter ce, final String name, final Type type, final String fieldName)
	{
		final String property = TypeUtils.upperFirst(name);
		CodeEmitter e;
		e = ce.begin_method(Constants.ACC_PUBLIC, new Signature("get" + property, type, Constants.TYPES_EMPTY), null);
		e.load_this();
		e.getfield(fieldName);
		e.return_value();
		e.end_method();

		e = ce.begin_method(Constants.ACC_PUBLIC, new Signature("set" + property, Type.VOID_TYPE, new Type[]
		{ type }), null);
		e.load_this();
		e.load_arg(0);
		e.putfield(fieldName);
		e.return_value();
		e.end_method();
	}

	/*
	 * generates: } catch (RuntimeException e) { throw e; } catch (Error e) { throw e; } catch (<DeclaredException> e) {
	 * throw e; } catch (Throwable e) { throw new <Wrapper>(e); }
	 */
	/**
	 * @param e
	 * @param handler
	 * @param exceptions
	 * @param wrapper
	 */
	public static void wrap_undeclared_throwable(final CodeEmitter e, final Block handler, final Type[] exceptions,
			final Type wrapper)
	{
		final Set set = (exceptions == null) ? Collections.EMPTY_SET : new HashSet<>(Arrays.asList(exceptions));

		if (set.contains(Constants.TYPE_THROWABLE))
		{
			return;
		}

		boolean needThrow = exceptions != null;
		if (!set.contains(Constants.TYPE_RUNTIME_EXCEPTION))
		{
			e.catch_exception(handler, Constants.TYPE_RUNTIME_EXCEPTION);
			needThrow = true;
		}
		if (!set.contains(Constants.TYPE_ERROR))
		{
			e.catch_exception(handler, Constants.TYPE_ERROR);
			needThrow = true;
		}
		if (exceptions != null)
		{
			for (int i = 0; i < exceptions.length; i++)
			{
				e.catch_exception(handler, exceptions[i]);
			}
		}
		if (needThrow)
		{
			e.athrow();
		}
		// e -> eo -> oeo -> ooe -> o
		e.catch_exception(handler, Constants.TYPE_THROWABLE);
		e.new_instance(wrapper);
		e.dup_x1();
		e.swap();
		e.invoke_constructor(wrapper, CSTRUCT_THROWABLE);
		e.athrow();
	}

	/**
	 * @param e
	 * @param method
	 * @return the begin method
	 */
	public static CodeEmitter begin_method(final ClassEmitter e, final MethodInfo method)
	{
		return begin_method(e, method, method.getModifiers());
	}

	/**
	 * @param e
	 * @param method
	 * @param access
	 * @return the begin method
	 */
	public static CodeEmitter begin_method(final ClassEmitter e, final MethodInfo method, final int access)
	{
		return e.begin_method(access, method.getSignature(), method.getExceptionTypes());
	}

	private static Map bucket(final Collection c, final Transformer t)
	{
		final Map<Object, List> buckets = new HashMap<>();
		for (final Iterator it = c.iterator(); it.hasNext();)
		{
			final Object value = it.next();
			final Object key = t.transform(value);

			@SuppressWarnings("unchecked")
			List<Object> bucket = buckets.get(key);

			if (bucket == null)
			{
				buckets.put(key, bucket = new LinkedList<Object>());
			}
			bucket.add(value);
		}
		return buckets;
	}
}
