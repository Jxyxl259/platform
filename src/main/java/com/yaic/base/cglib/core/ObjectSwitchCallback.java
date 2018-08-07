package com.yaic.base.cglib.core;

import com.yaic.base.asm.Label;


/**
 *
 */
public interface ObjectSwitchCallback
{
	/**
	 * @param key
	 * @param end
	 * @throws Exception
	 */
	void processCase(Object key, Label end) throws Exception;

	/**
	 * @throws Exception
	 */
	void processDefault() throws Exception;
}

