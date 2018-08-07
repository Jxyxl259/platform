package com.yaic.base.cglib.core;

import com.yaic.base.asm.Type;


/**
 *
 */
public interface HashCodeCustomizer extends KeyFactoryCustomizer
{
	/**
	 * Customizes calculation of hashcode
	 *
	 * @param e
	 *           code emitter
	 * @param type
	 *           parameter type
	 * @return true if customize
	 */
	boolean customize(CodeEmitter e, Type type);
}
