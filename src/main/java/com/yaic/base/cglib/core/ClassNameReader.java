package com.yaic.base.cglib.core;

import java.util.ArrayList;
import java.util.List;

import com.yaic.base.asm.ClassReader;
import com.yaic.base.asm.ClassVisitor;
import com.yaic.base.asm.Opcodes;


/**
 *
 */
//TODO: optimize (ClassReader buffers entire class before accept)
public class ClassNameReader
{
	private ClassNameReader()
	{
	}

	private static final EarlyExitException EARLY_EXIT = new EarlyExitException();

	private static class EarlyExitException extends RuntimeException
	{
		private static final long serialVersionUID = -2537044423815042391L;
	}

	/**
	 * @param reader
	 * @return the class name
	 */
	public static String getClassName(final ClassReader reader)
	{

		return getClassInfo(reader)[0];

	}

	/**
	 * @param reader
	 * @return the class info
	 */
	public static String[] getClassInfo(final ClassReader reader)
	{
		final List<String> array = new ArrayList<>();
		try
		{
			reader.accept(new ClassVisitor(Opcodes.ASM6, null)
			{
				@Override
				public void visit(final int version, final int access, final String name, final String signature,
						final String superName, final String[] interfaces)
				{
					array.add(name.replace('/', '.'));
					if (superName != null)
					{
						array.add(superName.replace('/', '.'));
					}
					for (int i = 0; i < interfaces.length; i++)
					{
						array.add(interfaces[i].replace('/', '.'));
					}

					throw EARLY_EXIT;
				}
			}, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
		}
		catch (final EarlyExitException e)
		{
		}

		return array.toArray(new String[] {});
	}
}
