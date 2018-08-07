package com.yaic.base.cglib.core;

import com.yaic.base.asm.ClassVisitor;
import com.yaic.base.asm.Opcodes;


/**
 *
 */
public abstract class ClassTransformer extends ClassVisitor
{
	/**
	 *
	 */
	public ClassTransformer()
	{
		super(Opcodes.ASM6);
	}

	/**
	 * @param opcode
	 */
	public ClassTransformer(final int opcode)
	{
		super(opcode);
	}

	/**
	 * @param target
	 */
	public abstract void setTarget(ClassVisitor target);
}
