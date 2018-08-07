package com.yaic.base.cglib.core;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;


/**
 *
 */
public class DuplicatesPredicate implements Predicate
{
	private final Set<Object> unique = new HashSet<>();

	public boolean evaluate(final Object arg)
	{
		return unique.add(MethodWrapper.create((Method) arg));
	}
}
