package com.yaic.base.cglib.beans;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * need it for class loading
 */
public class FixedKeySet extends AbstractSet
{
	private final Set set;
	private final int size;

	/**
	 * @param keys
	 */
	public FixedKeySet(final String[] keys)
	{
		size = keys.length;
		set = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(keys)));
	}

	@Override
	public Iterator iterator()
	{
		return set.iterator();
	}

	@Override
	public int size()
	{
		return size;
	}
}
