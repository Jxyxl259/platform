package com.yaic.base.cglib.core;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

import com.yaic.base.asm.Type;


/**
 *
 */
public class VisibilityPredicate implements Predicate
{
	private final boolean protectedOk;
	private final String pkg;
	private final boolean samePackageOk;

	/**
	 * @param source
	 * @param protectedOk
	 */
	public VisibilityPredicate(final Class source, final boolean protectedOk)
	{
		this.protectedOk = protectedOk;
		// same package is not ok for the bootstrap loaded classes.  In all other cases we are
		// generating classes in the same classloader
		this.samePackageOk = source.getClassLoader() != null;
		pkg = TypeUtils.getPackageName(Type.getType(source));
	}

	public boolean evaluate(final Object arg)
	{
		final Member member = (Member) arg;
		final int mod = member.getModifiers();
		if (Modifier.isPrivate(mod))
		{
			return false;
		}
		else if (Modifier.isPublic(mod))
		{
			return true;
		}
		else if (Modifier.isProtected(mod) && protectedOk)
		{
			// protected is fine if 'protectedOk' is true (for subclasses)
			return true;
		}
		else
		{
			// protected/package private if the member is in the same package as the source class
			// and we are generating into the same classloader.
			return samePackageOk && pkg.equals(TypeUtils.getPackageName(Type.getType(member.getDeclaringClass())));
		}
	}
}

