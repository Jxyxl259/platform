package com.yaic.base.cglib.core;

import com.yaic.base.asm.ClassVisitor;


/**
 *
 */
public interface ClassGenerator
{
	/**
	 * @param v
	 * @throws Exception
	 */
	void generateClass(ClassVisitor v) throws Exception;
}
