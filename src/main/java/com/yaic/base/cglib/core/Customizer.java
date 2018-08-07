package com.yaic.base.cglib.core;

import com.yaic.base.asm.Type;


/**
 * Customizes key types for {@link KeyFactory} when building equals, hashCode, and toString. For customization of field
 * types, use {@link FieldTypeCustomizer}
 *
 * @see KeyFactory#CLASS_BY_NAME
 */
public interface Customizer extends KeyFactoryCustomizer
{
	/**
	 * @param e
	 * @param type
	 */
	void customize(CodeEmitter e, Type type);
}
