package com.yaic.base.cglib.core;

/**
 *
 */
public interface Converter
{
	/**
	 * @param value
	 * @param target
	 * @param context
	 * @return the object
	 */
	Object convert(Object value, Class target, Object context);
}
