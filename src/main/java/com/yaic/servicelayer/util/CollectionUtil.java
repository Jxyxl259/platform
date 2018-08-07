package com.yaic.servicelayer.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;

/**
 * @author Qu Dihuai
 */
public class CollectionUtil
{
	/**
	 * An empty unmodifiable map.
	 */
	public static final Map<?, ?> EMPTY_MAP = Collections.emptyMap();
	/**
	 * An empty unmodifiable list.
	 */
	public static final List<?> EMPTY_LIST = Collections.emptyList();
	/**
	 * An empty unmodifiable set.
	 */
	public static final Set<?> EMPTY_SET = Collections.emptySet();

	private CollectionUtil()
	{
		super();
	}

	/**
	 * Null-safe check if the specified map is empty.
	 * <p>
	 * Null returns true.
	 *
	 * @param map
	 *           the map to check, may be null
	 * @return true if empty or null
	 */
	public static boolean isEmpty(final Map<?, ?> map)
	{
		return (map == null || map.isEmpty());
	}

	/**
	 * Null-safe check if the specified map is not empty.
	 * <p>
	 * Null returns false.
	 *
	 * @param map
	 *           the map to check, may be null
	 * @return true if non-null and non-empty
	 */
	public static boolean isNotEmpty(final Map<?, ?> map)
	{
		return (map != null && (map.isEmpty() == false));
	}

	/**
	 * Null-safe check if the specified list is empty.
	 * <p>
	 * Null returns true.
	 *
	 * @param list
	 *           the list to check, may be null
	 * @return true if empty or null
	 */
	public static boolean isEmpty(final List<?> list)
	{
		return (list == null || list.isEmpty());
	}

	/**
	 * Null-safe check if the specified list is not empty.
	 * <p>
	 * Null returns false.
	 *
	 * @param list
	 *           the list to check, may be null
	 * @return true if non-null and non-empty
	 */
	public static boolean isNotEmpty(final List<?> list)
	{
		return (list != null && (list.isEmpty() == false));
	}

	/**
	 * Null-safe check if the specified set is empty.
	 * <p>
	 * Null returns true.
	 *
	 * @param set
	 *           the set to check, may be null
	 * @return true if empty or null
	 */
	public static boolean isEmpty(final Set<?> set)
	{
		return (set == null || set.isEmpty());
	}

	/**
	 * Null-safe check if the specified set is not empty.
	 * <p>
	 * Null returns false.
	 *
	 * @param set
	 *           the set to check, may be null
	 * @return true if non-null and non-empty
	 */
	public static boolean isNotEmpty(final Set<?> set)
	{
		return (set != null && (set.isEmpty() == false));
	}

	/**
	 * Returns a synchronized map backed by the given map.
	 * <p>
	 * You must manually synchronize on the returned buffer's iterator to avoid non-deterministic behavior:
	 *
	 * <pre>
	 * Map m = MapUtils.synchronizedMap(myMap);
	 * Set s = m.keySet(); // outside synchronized block
	 * synchronized (m)
	 * { // synchronized on MAP!
	 * 	Iterator i = s.iterator();
	 * 	while (i.hasNext())
	 * 	{
	 * 		process(i.next());
	 * 	}
	 * }
	 * </pre>
	 *
	 * This method uses the implementation in {@link java.util.Collections Collections}.
	 *
	 * @param map
	 *           the map to synchronize, must not be null
	 * @return a synchronized map backed by the given map
	 * @throws IllegalArgumentException
	 *            if the map is null
	 */
	public static Map<?, ?> synchronizedMap(final Map<?, ?> map)
	{
		return Collections.synchronizedMap(map);
	}

	/**
	 * Returns an unmodifiable map backed by the given map.
	 * <p>
	 * This method uses the implementation in the decorators subpackage.
	 *
	 * @param map
	 *           the map to make unmodifiable, must not be null
	 * @return an unmodifiable map backed by the given map
	 * @throws IllegalArgumentException
	 *            if the map is null
	 */
	public static Map<?, ?> unmodifiableMap(final Map<?, ?> map)
	{
		return Collections.unmodifiableMap(map);
	}

	/**
	 * 将List<Object>转化为List<Map<String, Object>>, Object的属性对应转化为Map的键值; 如果属性的值为null, 则转为""
	 *
	 * @param objectList
	 *           object集合
	 * @return List<Map<String, Object>>
	 */
	public static List<Map<String, Object>> convertObjList2MapList(final List<Object> objectList)
	{
		if (objectList == null)
		{
			return null;
		}

		final List<Map<String, Object>> paramMapList = new ArrayList<>(objectList.size());
		for (final Object object : objectList)
		{
			paramMapList.add(ObjectUtil.objToMap(object));
		}

		return paramMapList;
	}

	/**
	 * 将dataList中重复的数据删除
	 *
	 * @param dataList
	 *           集合
	 *
	 * @return List<T> 返回删除重复之后的List
	 */
	public static <T> List<T> distinctElements(final List<T> dataList)
	{
		if (CollectionUtil.isEmpty(dataList))
		{
			return dataList;
		}

		final int capacity = dataList.size();

		final List<T> list = new ArrayList<>(dataList.size());
		if (componentTypeIsPrimitive(dataList))
		{
			final Set<T> set = new HashSet<>(capacity);
			for (final T obj : dataList)
			{
				if (set.add(obj))
				{
					list.add(obj);
				}
			}
			return list;
		}

		final Set<String> set = new HashSet<>(capacity);
		for (final T obj : dataList)
		{
			if (set.add(JSON.toJSONString(obj)))
			{
				list.add(obj);
			}
		}
		return list;
	}

	private static <T> boolean componentTypeIsPrimitive(final List<T> dataList)
	{
		final T obj = dataList.get(0);
		return obj instanceof Integer || obj instanceof Short || obj instanceof Integer || obj instanceof Long
				|| obj instanceof Float || obj instanceof Double || obj instanceof Character || obj instanceof Boolean;
	}

}
