package com.yaic.base.cglib.core;

/**
 *
 */
public class ClassesKey
{
	private static final Key FACTORY = (Key) KeyFactory.create(Key.class);

	interface Key
	{
		Object newInstance(Object[] array);
	}

	private ClassesKey()
	{
	}

	/**
	 * @param array
	 * @return the key
	 */
	public static Object create(final Object[] array)
	{
		return FACTORY.newInstance(classNames(array));
	}

	private static String[] classNames(final Object[] objects)
	{
		if (objects == null)
		{
			return null;
		}
		final String[] classNames = new String[objects.length];
		for (int i = 0; i < objects.length; i++)
		{
			final Object object = objects[i];
			if (object != null)
			{
				final Class<?> aClass = object.getClass();
				classNames[i] = aClass == null ? null : aClass.getName();
			}
		}
		return classNames;
	}
}
