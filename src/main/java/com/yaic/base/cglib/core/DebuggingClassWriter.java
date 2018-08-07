package com.yaic.base.cglib.core;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;

import com.yaic.base.asm.ClassVisitor;
import com.yaic.base.asm.ClassWriter;
import com.yaic.base.asm.Opcodes;


/**
 *
 */
public class DebuggingClassWriter extends ClassVisitor
{
	private static final String DEBUG_LOCATION_PROPERTY = "cglib.debugLocation";

	private static String debugLocation = System.getProperty(DEBUG_LOCATION_PROPERTY);

	private String className;
	private String superName;

	/**
	 * @param flags
	 */
	public DebuggingClassWriter(final int flags)
	{
		super(Opcodes.ASM6, new ClassWriter(flags));
	}

	@Override
	public void visit(final int version, final int access, final String name, final String signature, final String superName,
			final String[] interfaces)
	{
		className = name.replace('/', '.');
		this.superName = superName.replace('/', '.');
		super.visit(version, access, name, signature, superName, interfaces);
	}

	/**
	 * @return the className
	 */
	public String getClassName()
	{
		return className;
	}

	/**
	 * @return the superName
	 */
	public String getSuperName()
	{
		return superName;
	}

	/**
	 * @return convert to byte array
	 */
	public byte[] toByteArray()
	{
		return (byte[]) AccessController.doPrivileged(new PrivilegedAction<Object>()
		{
			public Object run()
			{
				final byte[] b = ((ClassWriter) DebuggingClassWriter.super.cv).toByteArray();
				if (debugLocation != null)
				{
					final String dirs = className.replace('.', File.separatorChar);
					try
					{
						new File(debugLocation + File.separatorChar + dirs).getParentFile().mkdirs();

						final File file = new File(new File(debugLocation), dirs + ".class");
						try (final OutputStream out = new BufferedOutputStream(new FileOutputStream(file)))
						{
							out.write(b);
						}
					}
					catch (final Exception e)
					{
						throw new CodeGenerationException(e);
					}
				}
				return b;
			}
		});

	}
}
