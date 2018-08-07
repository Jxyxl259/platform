package com.yaic.servicelayer.util;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;


/**
 * @author Qu Dihuai
 *
 */
public class RandomUtil
{
	/**
	 * To provide the shared seed random
	 */
	public final static Random SHARED_SEED_RANDOM = getSharedSeedRandom();

	private RandomUtil()
	{
		super();
	}

	/**
	 * <p>
	 * Returns the next pseudorandom, uniformly distributed int value from the Math.random() sequence.
	 * </p>
	 * Identical to <code>nextInt(Integer.MAX_VALUE)</code>
	 * <p>
	 * <b>N.B. All values are >= 0.<b>
	 * </p>
	 *
	 * @return the random int
	 */
	public static int nextInt()
	{
		return nextInt(Integer.MAX_VALUE);
	}

	/**
	 * <p>
	 * Returns a pseudorandom, uniformly distributed int value between <code>0</code> (inclusive) and the specified value
	 * (exclusive), from the Math.random() sequence.
	 * </p>
	 *
	 * @param n
	 *           the specified exclusive max-value
	 * @return the random int
	 * @throws IllegalArgumentException
	 *            when <code>n &lt;= 0</code>
	 */
	public static int nextInt(final int n)
	{
		return SHARED_SEED_RANDOM.nextInt(n);
	}

	/**
	 * <p>
	 * Returns the next pseudorandom, uniformly distributed long value from the Math.random() sequence.
	 * </p>
	 * Identical to <code>nextLong(Long.MAX_VALUE)</code>
	 * <p>
	 * <b>N.B. All values are >= 0.<b>
	 * </p>
	 *
	 * @return the random long
	 */
	public static long nextLong()
	{
		return SHARED_SEED_RANDOM.nextLong();
	}

	/**
	 * <p>
	 * Returns the next pseudorandom, uniformly distributed boolean value from the Math.random() sequence.
	 * </p>
	 *
	 * @return the random boolean
	 */
	public static boolean nextBoolean()
	{
		return SHARED_SEED_RANDOM.nextBoolean();
	}

	/**
	 * <p>
	 * Returns the next pseudorandom, uniformly distributed float value between <code>0.0</code> and <code>1.0</code>
	 * from the Math.random() sequence.
	 * </p>
	 *
	 * @return the random float
	 */
	public static float nextFloat()
	{
		return SHARED_SEED_RANDOM.nextFloat();
	}

	/**
	 * <p>
	 * Synonymous to the Math.random() call.
	 * </p>
	 *
	 * @return the random double
	 */
	public static double nextDouble()
	{
		return SHARED_SEED_RANDOM.nextDouble();
	}

	/**
	 * Use SHA1PRNG algorithm and the gived seed to create SecureRandom instance
	 *
	 * @param seed
	 * @return SecureRandom algorithm is SHA1PRNG, seed is gived
	 */
	public static SecureRandom getSha1prngRandom(final byte[] seed)
	{
		try
		{
			final SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(seed);
			return random;
		}
		catch (final NoSuchAlgorithmException nsae)
		{
			// never happens, because I made sure the algorithm exists
			throw new RuntimeException(nsae);
		}
	}

	private static Random getSharedSeedRandom()
	{
		final String guid = UUID.randomUUID().toString();
		final byte[] seed = guid.getBytes(StandardCharsets.UTF_8);
		return getSha1prngRandom(seed);
	}
}
