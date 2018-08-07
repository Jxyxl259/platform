package com.yaic.base.cglib.core.internal;

/**
 * @param <K>
 * @param <V>
 */
public interface Function<K, V>
{
	/**
	 * @param key
	 * @return value
	 */
	V apply(K key);
}
