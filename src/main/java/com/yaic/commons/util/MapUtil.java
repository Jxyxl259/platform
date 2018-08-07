package com.yaic.commons.util;

import java.util.concurrent.ConcurrentMap;


/**
 *
 */
public class MapUtil
{
	/**
	 * 创建Value值的回调函数
	 *
	 * @param <T>
	 *
	 */
	public interface ValueCreator<T>
	{
		/**
		 * 创建对象
		 *
		 * @return the created value
		 */
		T execute();
	}

	/**
	 * 如果Key不存在则创建，返回最后存储在Map中的Value.
	 *
	 * 如果创建对象有一定成本, 直接使用PutIfAbsent可能重复浪费, 则使用此方法, 可以避免浪费
	 *
	 * @param map
	 * @param key
	 * @param creator
	 * @return the created value
	 *
	 */
	public static <K, V> V createIfAbsent(final ConcurrentMap<K, V> map, final K key, final ValueCreator<? extends V> creator)
	{
		final V value = map.get(key);
		if (value == null)
		{
			return putIfAbsent(map, key, creator.execute());
		}
		return value;
	}


	/**
	 * ConcurrentMap的putIfAbsent()返回之前的Value，此方法返回最终存储在Map中的Value
	 *
	 * @param map
	 * @param key
	 * @param value
	 * @return the final value
	 */
	public static <K, V> V putIfAbsent(final ConcurrentMap<K, V> map, final K key, final V value)
	{
		final V result = map.putIfAbsent(key, value);
		if (result != null)
		{
			return result;
		}
		return value;
	}
}
