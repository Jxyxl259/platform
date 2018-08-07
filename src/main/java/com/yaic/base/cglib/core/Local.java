package com.yaic.base.cglib.core;

import com.yaic.base.asm.Type;


/**
 *
 */
public class Local
{
	private final Type type;
	private final int index;

	/**
	 * @param index
	 * @param type
	 */
	public Local(final int index, final Type type)
	{
		this.type = type;
		this.index = index;
	}

	/**
	 * @return the index
	 */
	public int getIndex()
	{
		return index;
	}

	/**
	 * @return the type
	 */
	public Type getType()
	{
		return type;
	}
}
