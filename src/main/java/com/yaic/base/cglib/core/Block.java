package com.yaic.base.cglib.core;

import com.yaic.base.asm.Label;


/**
 *
 */
public class Block
{
	private final CodeEmitter e;
	private final Label start;
	private Label end;

	/**
	 * @param e
	 */
	public Block(final CodeEmitter e)
	{
		this.e = e;
		start = e.mark();
	}

	/**
	 * @return the CodeEmitter
	 */
	public CodeEmitter getCodeEmitter()
	{
		return e;
	}

	/**
	 *
	 */
	public void end()
	{
		if (end != null)
		{
			throw new IllegalStateException("end of label already set");
		}
		end = e.mark();
	}

	/**
	 * @return the start
	 */
	public Label getStart()
	{
		return start;
	}

	/**
	 * @return the end
	 */
	public Label getEnd()
	{
		return end;
	}
}
