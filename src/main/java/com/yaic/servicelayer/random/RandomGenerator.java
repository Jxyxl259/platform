package com.yaic.servicelayer.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yaic.servicelayer.util.RandomUtil;


/**
 * @author Qu Dihuai
 *
 *         <p>
 *         生成随机字符串的工具类
 *         </p>
 */
public class RandomGenerator
{
	private static List<Character> CHAR_SRC;
	static
	{
		final List<Character> list = new ArrayList<>();
		// alpha characters
		for (int i = 'a'; i <= 'z'; i++)
		{
			list.add((char) i);
		}
		for (int i = 'A'; i <= 'Z'; i++)
		{
			list.add((char) i);
		}
		// numeric characters
		for (int i = '0'; i <= '9'; i++)
		{
			list.add((char) i);
		}

		Collections.shuffle(list, RandomUtil.SHARED_SEED_RANDOM);
		CHAR_SRC = Collections.unmodifiableList(list);
	}

	/**
	 * @param length
	 *
	 * @return 指定长度的随机数字符串
	 */
	public static String getNumbericString(final int length)
	{
		if (length < 1)
		{
			throw new IllegalArgumentException("length is invalid");
		}

		final StringBuilder builder = new StringBuilder(length);

		for (int i = 0; i < length; i++)
		{
			builder.append(RandomUtil.nextInt(10));
		}

		return builder.toString();
	}

	/**
	 * @param length
	 *
	 * @return 指定长度的随机的数字、字母混合字符串
	 */
	public static String getAlphanumericString(final int length)
	{
		if (length < 1)
		{
			throw new IllegalArgumentException("length is invalid");
		}

		final StringBuilder builder = new StringBuilder(length);
		final int size = CHAR_SRC.size();

		for (int i = 0; i < length; i++)
		{
			builder.append(CHAR_SRC.get(RandomUtil.nextInt(size)));
		}

		return builder.toString();
	}

	/**
	 * @return 随机的字符
	 */
	public static String getRandomChar()
	{
		final int randomIndex = RandomUtil.nextInt(CHAR_SRC.size());
		final Character randomChar = CHAR_SRC.get(randomIndex);
		return String.valueOf(randomChar);
	}
}
