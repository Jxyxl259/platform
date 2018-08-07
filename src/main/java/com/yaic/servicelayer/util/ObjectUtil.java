package com.yaic.servicelayer.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;


/**
 * @author Qu Dihuai
 */
public class ObjectUtil
{
	private static final Logger LOG = LoggerFactory.getLogger(ObjectUtil.class);

	/**
	 * Determine whether the given object is null.
	 *
	 * @param obj
	 * @return true if obj is null
	 */
	public static boolean isNull(final Object obj)
	{
		return obj == null;
	}

	/**
	 * Determine whether the given object is not null.
	 *
	 * @param obj
	 * @return true if obj is not null
	 */
	public static boolean isNotNull(final Object obj)
	{
		return obj != null;
	}

	/**
	 * <p>
	 * Compares two objects for equality, where either one or both objects may be <code>null</code>.
	 * </p>
	 *
	 * <pre>
	 * ObjectUtils.equals(null, null)                  = true
	 * ObjectUtils.equals(null, "")                    = false
	 * ObjectUtils.equals("", null)                    = false
	 * ObjectUtils.equals("", "")                      = true
	 * ObjectUtils.equals(Boolean.TRUE, null)          = false
	 * ObjectUtils.equals(Boolean.TRUE, "true")        = false
	 * ObjectUtils.equals(Boolean.TRUE, Boolean.TRUE)  = true
	 * ObjectUtils.equals(Boolean.TRUE, Boolean.FALSE) = false
	 * </pre>
	 *
	 * @param object1
	 *           the first object, may be <code>null</code>
	 * @param object2
	 *           the second object, may be <code>null</code>
	 * @return <code>true</code> if the values of both objects are the same
	 */
	public static boolean equals(final Object object1, final Object object2)
	{
		if ((object1 == null) || (object2 == null))
		{
			return false;
		}
		if (object1 == object2)
		{
			return true;
		}
		return object1.equals(object2);
	}

	/**
	 * <p>
	 * Compares two objects for inequality, where either one or both objects may be <code>null</code>.
	 * </p>
	 *
	 * <pre>
	 * ObjectUtils.notEqual(null, null)                  = false
	 * ObjectUtils.notEqual(null, "")                    = true
	 * ObjectUtils.notEqual("", null)                    = true
	 * ObjectUtils.notEqual("", "")                      = false
	 * ObjectUtils.notEqual(Boolean.TRUE, null)          = true
	 * ObjectUtils.notEqual(Boolean.TRUE, "true")        = true
	 * ObjectUtils.notEqual(Boolean.TRUE, Boolean.TRUE)  = false
	 * ObjectUtils.notEqual(Boolean.TRUE, Boolean.FALSE) = true
	 * </pre>
	 *
	 * @param object1
	 *           the first object, may be <code>null</code>
	 * @param object2
	 *           the second object, may be <code>null</code>
	 * @return <code>false</code> if the values of both objects are the same
	 */
	public static boolean notEqual(final Object object1, final Object object2)
	{
		return equals(object1, object2) == false;
	}

	/**
	 * Return the same value as {@link Boolean#hashCode()}}.
	 *
	 * @param bool
	 * @return hashCode
	 *
	 * @see Boolean#hashCode()
	 */
	public static int hashCode(final boolean bool)
	{
		return (bool ? 1231 : 1237);
	}

	/**
	 * Return the same value as {@link Double#hashCode()}}.
	 *
	 * @param dbl
	 * @return int hash code
	 *
	 * @see Double#hashCode()
	 */
	public static int hashCode(final double dbl)
	{
		return hashCode(Double.doubleToLongBits(dbl));
	}

	/**
	 * Return the same value as {@link Float#hashCode()}}.
	 *
	 * @param flt
	 * @return int hash code
	 *
	 * @see Float#hashCode()
	 */
	public static int hashCode(final float flt)
	{
		return Float.floatToIntBits(flt);
	}

	/**
	 * Return the same value as {@link Long#hashCode()}}.
	 *
	 * @param lng
	 * @return int hash code
	 *
	 * @see Long#hashCode()
	 */
	public static int hashCode(final long lng)
	{
		return (int) (lng ^ (lng >>> 32));
	}

	/**
	 * 将两个对象中相同的属性值从源对象赋值给目标对象, 但对源对象的值为null的属性不作此操作。这两个对象的类可以有继承关系, 也可以没有继承关系。
	 *
	 * <p>
	 * Copy the property values of the given source bean into the target bean.
	 * <p>
	 * Note: The source and target classes do not have to match or even be derived from each other, as long as the
	 * properties match. Any bean properties that the source bean exposes but the target bean does not will silently be
	 * ignored.
	 * <p>
	 * This is just a convenience method.
	 *
	 * @param target
	 *           the source bean
	 * @param source
	 *           the target bean
	 */
	public static void copyProperties(final Object target, final Object source)
	{
		final Class<?> targetClazz = target.getClass();
		final Class<?> sourceClazz = source.getClass();

		final PropertyDescriptor[] targetPds = getPropertyDescriptors(targetClazz);
		if (ArrayUtil.isEmpty(targetPds))
		{
			return;
		}

		for (final PropertyDescriptor targetPd : targetPds)
		{
			final Method targetWriteMethod = targetPd.getWriteMethod();
			if (targetWriteMethod == null)
			{
				continue;
			}

			final PropertyDescriptor sourcePd = getPropertyDescriptor(sourceClazz, targetPd);
			if (sourcePd == null)
			{
				continue;
			}

			final Method sourceReadMethod = sourcePd.getReadMethod();
			if (sourceReadMethod == null)
			{
				continue;
			}

			final Class<?> paramTypeForTargetWriteMethod = targetWriteMethod.getParameterTypes()[0];
			final Class<?> returnTypeForSourceReadMethod = sourceReadMethod.getReturnType();
			if (isAssignable(paramTypeForTargetWriteMethod, returnTypeForSourceReadMethod) == false)
			{
				continue;
			}

			try
			{
				if (isNotPublic(sourceReadMethod))
				{
					sourceReadMethod.setAccessible(true);
				}

				final Object value = sourceReadMethod.invoke(source);
				if (value != null)
				{
					if (isNotPublic(targetWriteMethod))
					{
						targetWriteMethod.setAccessible(true);
					}
					targetWriteMethod.invoke(target, value);
				}
			}
			catch (final Throwable ex)
			{
				LOG.error(ex.getMessage(), ex);
			}
		}
	}

	/**
	 * Object转map
	 *
	 * @param object
	 *           object对象
	 * @return Map 转Map的结果
	 */
	public static Map<String, Object> objToMap(final Object object)
	{
		if (object == null)
		{
			return Collections.emptyMap();
		}
		return JSON.parseObject(JSONObject.toJSONString(object, SerializerFeature.WriteNullStringAsEmpty));
	}

	private static PropertyDescriptor[] getPropertyDescriptors(final Class<? extends Object> clazz)
	{
		try
		{
			return BeanUtils.getPropertyDescriptors(clazz);
		}
		catch (final BeansException e)
		{
			LOG.error(e.getMessage(), e);
		}
		return null;
	}


	private static PropertyDescriptor getPropertyDescriptor(final Class<? extends Object> clazz,
			final PropertyDescriptor propDescriptor)
	{
		try
		{
			return BeanUtils.getPropertyDescriptor(clazz, propDescriptor.getName());
		}
		catch (final BeansException e)
		{
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * <p>
	 * Checks if one <code>Class</code> can be assigned to a variable of another <code>Class</code>.
	 * </p>
	 *
	 * <p>
	 * Unlike the {@link Class#isAssignableFrom(java.lang.Class)} method, this method takes into account widenings of
	 * primitive classes and <code>null</code>s.
	 * </p>
	 *
	 * <p>
	 * Primitive widenings allow an int to be assigned to a long, float or double. This method returns the correct result
	 * for these cases.
	 * </p>
	 *
	 * <p>
	 * <code>Null</code> may be assigned to any reference type. This method will return <code>true</code> if
	 * <code>null</code> is passed in and the toClass is non-primitive.
	 * </p>
	 *
	 * <p>
	 * Specifically, this method tests whether the type represented by the specified <code>Class</code> parameter can be
	 * converted to the type represented by this <code>Class</code> object via an identity conversion widening primitive
	 * or widening reference conversion. See <em><a href="http://java.sun.com/docs/books/jls/">The Java Language
	 * Specification</a></em>, sections 5.1.1, 5.1.2 and 5.1.4 for details.
	 * </p>
	 *
	 * @param cls
	 *           the Class to check, may be null
	 * @param toClass
	 *           the Class to try to assign into, returns false if null
	 * @return <code>true</code> if assignment possible
	 */
	private static boolean isAssignable(final Class<?> clazz, final Class<?> toClazz)
	{
		if (toClazz == null)
		{
			return false;
		}

		if (clazz == null)
		{
			return !(toClazz.isPrimitive());
		}

		if (clazz.equals(toClazz))
		{
			return true;
		}

		if (clazz.isPrimitive() == false)
		{
			return toClazz.isAssignableFrom(clazz);
		}

		if (toClazz.isPrimitive() == false)
		{
			return false;
		}

		if (Boolean.TYPE.equals(clazz))
		{
			return false;
		}
		if (Double.TYPE.equals(clazz))
		{
			return false;
		}

		if (Integer.TYPE.equals(clazz))
		{
			return Long.TYPE.equals(toClazz) || Float.TYPE.equals(toClazz) || Double.TYPE.equals(toClazz);
		}
		if (Long.TYPE.equals(clazz))
		{
			return Float.TYPE.equals(toClazz) || Double.TYPE.equals(toClazz);
		}
		if (Float.TYPE.equals(clazz))
		{
			return Double.TYPE.equals(toClazz);
		}
		if (Character.TYPE.equals(clazz))
		{
			return Integer.TYPE.equals(toClazz) || Long.TYPE.equals(toClazz) || Float.TYPE.equals(toClazz)
					|| Double.TYPE.equals(toClazz);
		}
		if (Short.TYPE.equals(clazz))
		{
			return Integer.TYPE.equals(toClazz) || Long.TYPE.equals(toClazz) || Float.TYPE.equals(toClazz)
					|| Double.TYPE.equals(toClazz);
		}
		if (Byte.TYPE.equals(clazz))
		{
			return Short.TYPE.equals(toClazz) || Integer.TYPE.equals(toClazz) || Long.TYPE.equals(toClazz)
					|| Float.TYPE.equals(toClazz) || Double.TYPE.equals(toClazz);
		}

		return false;
	}

	private static boolean isNotPublic(final Method method)
	{
		return Modifier.isPublic(method.getDeclaringClass().getModifiers()) == false;
	}
}
