package com.yaic.base.cglib.core;

import com.yaic.base.asm.Label;


/**
 *
 */
public interface ProcessSwitchCallback
{
	/**
	 * @param key
	 * @param end
	 * @throws Exception
	 */
	void processCase(int key, Label end) throws Exception;

	/**
	 * @throws Exception
	 */
	void processDefault() throws Exception;
}
