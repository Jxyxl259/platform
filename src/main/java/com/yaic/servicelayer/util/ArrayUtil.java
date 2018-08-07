package com.yaic.servicelayer.util;

import java.lang.reflect.Array;
import java.util.Arrays;


/**
 * @author Qu Dihuai
 */
public class ArrayUtil
{
	/**
	 * An empty immutable {@code Object} array.
	 */
	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	/**
	 * An empty immutable {@code Class} array.
	 */
	public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
	/**
	 * An empty immutable {@code String} array.
	 */
	public static final String[] EMPTY_STRING_ARRAY = new String[0];
	/**
	 * An empty immutable {@code long} array.
	 */
	public static final long[] EMPTY_LONG_ARRAY = new long[0];
	/**
	 * An empty immutable {@code Long} array.
	 */
	public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
	/**
	 * An empty immutable {@code int} array.
	 */
	public static final int[] EMPTY_INT_ARRAY = new int[0];
	/**
	 * An empty immutable {@code Integer} array.
	 */
	public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
	/**
	 * An empty immutable {@code short} array.
	 */
	public static final short[] EMPTY_SHORT_ARRAY = new short[0];
	/**
	 * An empty immutable {@code Short} array.
	 */
	public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
	/**
	 * An empty immutable {@code byte} array.
	 */
	public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
	/**
	 * An empty immutable {@code Byte} array.
	 */
	public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
	/**
	 * An empty immutable {@code double} array.
	 */
	public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
	/**
	 * An empty immutable {@code Double} array.
	 */
	public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
	/**
	 * An empty immutable {@code float} array.
	 */
	public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
	/**
	 * An empty immutable {@code Float} array.
	 */
	public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
	/**
	 * An empty immutable {@code boolean} array.
	 */
	public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
	/**
	 * An empty immutable {@code Boolean} array.
	 */
	public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
	/**
	 * An empty immutable {@code char} array.
	 */
	public static final char[] EMPTY_CHAR_ARRAY = new char[0];
	/**
	 * An empty immutable {@code Character} array.
	 */
	public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];
	/**
	 * The index value when an element is not found in a list or array: <code>-1</code>. This value is returned by
	 * methods in this class and can also be used in comparisons with values returned by various method from
	 * {@link java.util.List}.
	 */
	private static final int INDEX_NOT_FOUND = -1;

	/**
	 * <p>
	 * Checks if an array of Objects is empty or <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 */
	public static boolean isEmpty(final Object[] array)
	{
		return array == null || array.length == 0;
	}

	/**
	 * <p>
	 * Checks if an array of primitive longs is empty or <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 */
	public static boolean isEmpty(final long[] array)
	{
		return array == null || array.length == 0;
	}

	/**
	 * <p>
	 * Checks if an array of primitive ints is empty or <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 */
	public static boolean isEmpty(final int[] array)
	{
		return array == null || array.length == 0;
	}

	/**
	 * <p>
	 * Checks if an array of primitive shorts is empty or <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 */
	public static boolean isEmpty(final short[] array)
	{
		return array == null || array.length == 0;
	}

	/**
	 * <p>
	 * Checks if an array of primitive chars is empty or <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 */
	public static boolean isEmpty(final char[] array)
	{
		return array == null || array.length == 0;
	}

	/**
	 * <p>
	 * Checks if an array of primitive bytes is empty or <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 */
	public static boolean isEmpty(final byte[] array)
	{
		return array == null || array.length == 0;
	}

	/**
	 * <p>
	 * Checks if an array of primitive doubles is empty or <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 */
	public static boolean isEmpty(final double[] array)
	{
		return array == null || array.length == 0;
	}

	/**
	 * <p>
	 * Checks if an array of primitive floats is empty or <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 */
	public static boolean isEmpty(final float[] array)
	{
		return array == null || array.length == 0;
	}

	/**
	 * <p>
	 * Checks if an array of primitive booleans is empty or <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is empty or <code>null</code>
	 */
	public static boolean isEmpty(final boolean[] array)
	{
		return array == null || array.length == 0;
	}

	/**
	 * <p>
	 * Checks if an array of Objects is not empty or not <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is not empty or not <code>null</code>
	 */
	public static boolean isNotEmpty(final Object[] array)
	{
		return (array != null && array.length != 0);
	}

	/**
	 * <p>
	 * Checks if an array of primitive longs is not empty or not <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is not empty or not <code>null</code>
	 */
	public static boolean isNotEmpty(final long[] array)
	{
		return (array != null && array.length != 0);
	}

	/**
	 * <p>
	 * Checks if an array of primitive ints is not empty or not <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is not empty or not <code>null</code>
	 */
	public static boolean isNotEmpty(final int[] array)
	{
		return (array != null && array.length != 0);
	}

	/**
	 * <p>
	 * Checks if an array of primitive shorts is not empty or not <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is not empty or not <code>null</code>
	 */
	public static boolean isNotEmpty(final short[] array)
	{
		return (array != null && array.length != 0);
	}

	/**
	 * <p>
	 * Checks if an array of primitive chars is not empty or not <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is not empty or not <code>null</code>
	 */
	public static boolean isNotEmpty(final char[] array)
	{
		return (array != null && array.length != 0);
	}

	/**
	 * <p>
	 * Checks if an array of primitive bytes is not empty or not <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is not empty or not <code>null</code>
	 */
	public static boolean isNotEmpty(final byte[] array)
	{
		return (array != null && array.length != 0);
	}

	/**
	 * <p>
	 * Checks if an array of primitive doubles is not empty or not <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is not empty or not <code>null</code>
	 */
	public static boolean isNotEmpty(final double[] array)
	{
		return (array != null && array.length != 0);
	}

	/**
	 * <p>
	 * Checks if an array of primitive floats is not empty or not <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is not empty or not <code>null</code>
	 */
	public static boolean isNotEmpty(final float[] array)
	{
		return (array != null && array.length != 0);
	}

	/**
	 * <p>
	 * Checks if an array of primitive booleans is not empty or not <code>null</code>.
	 * </p>
	 *
	 * @param array
	 *           the array to test
	 * @return <code>true</code> if the array is not empty or not <code>null</code>
	 */
	public static boolean isNotEmpty(final boolean[] array)
	{
		return (array != null && array.length != 0);
	}

	/**
	 * <p>
	 * Checks if the object is in the given array.
	 * </p>
	 *
	 * <p>
	 * The method returns <code>false</code> if a <code>null</code> array is passed in.
	 * </p>
	 *
	 * @param array
	 *           the array to search through
	 * @param objectToFind
	 *           the object to find
	 * @return <code>true</code> if the array contains the object
	 */
	public static boolean contains(final Object[] array, final Object objectToFind)
	{
		return indexOf(array, objectToFind) != INDEX_NOT_FOUND;
	}

	/**
	 * <p>
	 * Finds the index of the given object in the array.
	 * </p>
	 *
	 * <p>
	 * This method returns {@link #INDEX_NOT_FOUND} (<code>-1</code>) for a <code>null</code> input array.
	 * </p>
	 *
	 * @param array
	 *           the array to search through for the object, may be <code>null</code>
	 * @param objectToFind
	 *           the object to find, may be <code>null</code>
	 * @return the index of the object within the array, {@link #INDEX_NOT_FOUND} (<code>-1</code>) if not found or
	 *         <code>null</code> array input
	 */
	public static int indexOf(final Object[] array, final Object objectToFind)
	{
		return indexOf(array, objectToFind, 0);
	}

	private static int indexOf(final Object[] array, final Object objectToFind, int startIndex)
	{
		if (array == null)
		{
			return INDEX_NOT_FOUND;
		}

		if (startIndex < 0)
		{
			startIndex = 0;
		}

		if (objectToFind == null)
		{
			for (int i = startIndex, len = array.length; i < len; i++)
			{
				if (array[i] == null)
				{
					return i;
				}
			}
		}
		else if (array.getClass().getComponentType().isInstance(objectToFind))
		{
			for (int i = startIndex, len = array.length; i < len; i++)
			{
				if (objectToFind.equals(array[i]))
				{
					return i;
				}
			}
		}

		return INDEX_NOT_FOUND;
	}

	// Subarrays
	//-----------------------------------------------------------------------
	/**
	 * <p>
	 * Produces a new array containing the elements between the start and end indices.
	 *
	 * <p>
	 * The start index is inclusive, the end index exclusive. Null array input produces null output.
	 *
	 * <p>
	 * The component type of the subarray is always the same as that of the input array. Thus, if the input is an array
	 * of type {@code Date}, the following usage is envisaged:
	 *
	 * <pre>
	 * Date[] someDates = (Date[]) ArrayUtils.subarray(allDates, 2, 5);
	 * </pre>
	 *
	 * @param <T>
	 *           the component type of the array
	 * @param array
	 *           the array
	 * @param startIndexInclusive
	 *           the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an
	 *           empty array.
	 * @param endIndexExclusive
	 *           elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces
	 *           empty array, overvalue (&gt;array.length) is demoted to array length.
	 * @return a new array containing the elements between the start and end indices.
	 * @see Arrays#copyOfRange(Object[], int, int)
	 */
	public static <T> T[] subarray(final T[] array, int startIndexInclusive, int endIndexExclusive)
	{
		if (array == null)
		{
			return null;
		}
		if (startIndexInclusive < 0)
		{
			startIndexInclusive = 0;
		}
		if (endIndexExclusive > array.length)
		{
			endIndexExclusive = array.length;
		}
		final int newSize = endIndexExclusive - startIndexInclusive;
		final Class<?> type = array.getClass().getComponentType();
		if (newSize <= 0)
		{
			@SuppressWarnings("unchecked") // OK, because array is of type T
			final T[] emptyArray = (T[]) Array.newInstance(type, 0);
			return emptyArray;
		}
		@SuppressWarnings("unchecked") // OK, because array is of type T
		final T[] subarray = (T[]) Array.newInstance(type, newSize);
		System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
		return subarray;
	}

	/**
	 * <p>
	 * Produces a new {@code long} array containing the elements between the start and end indices.
	 *
	 * <p>
	 * The start index is inclusive, the end index exclusive. Null array input produces null output.
	 *
	 * @param array
	 *           the array
	 * @param startIndexInclusive
	 *           the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an
	 *           empty array.
	 * @param endIndexExclusive
	 *           elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces
	 *           empty array, overvalue (&gt;array.length) is demoted to array length.
	 * @return a new array containing the elements between the start and end indices.
	 * @see Arrays#copyOfRange(long[], int, int)
	 */
	public static long[] subarray(final long[] array, int startIndexInclusive, int endIndexExclusive)
	{
		if (array == null)
		{
			return null;
		}
		if (startIndexInclusive < 0)
		{
			startIndexInclusive = 0;
		}
		if (endIndexExclusive > array.length)
		{
			endIndexExclusive = array.length;
		}
		final int newSize = endIndexExclusive - startIndexInclusive;
		if (newSize <= 0)
		{
			return EMPTY_LONG_ARRAY;
		}

		final long[] subarray = new long[newSize];
		System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
		return subarray;
	}

	/**
	 * <p>
	 * Produces a new {@code int} array containing the elements between the start and end indices.
	 *
	 * <p>
	 * The start index is inclusive, the end index exclusive. Null array input produces null output.
	 *
	 * @param array
	 *           the array
	 * @param startIndexInclusive
	 *           the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an
	 *           empty array.
	 * @param endIndexExclusive
	 *           elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces
	 *           empty array, overvalue (&gt;array.length) is demoted to array length.
	 * @return a new array containing the elements between the start and end indices.
	 * @see Arrays#copyOfRange(int[], int, int)
	 */
	public static int[] subarray(final int[] array, int startIndexInclusive, int endIndexExclusive)
	{
		if (array == null)
		{
			return null;
		}
		if (startIndexInclusive < 0)
		{
			startIndexInclusive = 0;
		}
		if (endIndexExclusive > array.length)
		{
			endIndexExclusive = array.length;
		}
		final int newSize = endIndexExclusive - startIndexInclusive;
		if (newSize <= 0)
		{
			return EMPTY_INT_ARRAY;
		}

		final int[] subarray = new int[newSize];
		System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
		return subarray;
	}

	/**
	 * <p>
	 * Produces a new {@code short} array containing the elements between the start and end indices.
	 *
	 * <p>
	 * The start index is inclusive, the end index exclusive. Null array input produces null output.
	 *
	 * @param array
	 *           the array
	 * @param startIndexInclusive
	 *           the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an
	 *           empty array.
	 * @param endIndexExclusive
	 *           elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces
	 *           empty array, overvalue (&gt;array.length) is demoted to array length.
	 * @return a new array containing the elements between the start and end indices.
	 * @see Arrays#copyOfRange(short[], int, int)
	 */
	public static short[] subarray(final short[] array, int startIndexInclusive, int endIndexExclusive)
	{
		if (array == null)
		{
			return null;
		}
		if (startIndexInclusive < 0)
		{
			startIndexInclusive = 0;
		}
		if (endIndexExclusive > array.length)
		{
			endIndexExclusive = array.length;
		}
		final int newSize = endIndexExclusive - startIndexInclusive;
		if (newSize <= 0)
		{
			return EMPTY_SHORT_ARRAY;
		}

		final short[] subarray = new short[newSize];
		System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
		return subarray;
	}

	/**
	 * <p>
	 * Produces a new {@code char} array containing the elements between the start and end indices.
	 *
	 * <p>
	 * The start index is inclusive, the end index exclusive. Null array input produces null output.
	 *
	 * @param array
	 *           the array
	 * @param startIndexInclusive
	 *           the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an
	 *           empty array.
	 * @param endIndexExclusive
	 *           elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces
	 *           empty array, overvalue (&gt;array.length) is demoted to array length.
	 * @return a new array containing the elements between the start and end indices.
	 * @see Arrays#copyOfRange(char[], int, int)
	 */
	public static char[] subarray(final char[] array, int startIndexInclusive, int endIndexExclusive)
	{
		if (array == null)
		{
			return null;
		}
		if (startIndexInclusive < 0)
		{
			startIndexInclusive = 0;
		}
		if (endIndexExclusive > array.length)
		{
			endIndexExclusive = array.length;
		}
		final int newSize = endIndexExclusive - startIndexInclusive;
		if (newSize <= 0)
		{
			return EMPTY_CHAR_ARRAY;
		}

		final char[] subarray = new char[newSize];
		System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
		return subarray;
	}

	/**
	 * <p>
	 * Produces a new {@code byte} array containing the elements between the start and end indices.
	 *
	 * <p>
	 * The start index is inclusive, the end index exclusive. Null array input produces null output.
	 *
	 * @param array
	 *           the array
	 * @param startIndexInclusive
	 *           the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an
	 *           empty array.
	 * @param endIndexExclusive
	 *           elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces
	 *           empty array, overvalue (&gt;array.length) is demoted to array length.
	 * @return a new array containing the elements between the start and end indices.
	 * @see Arrays#copyOfRange(byte[], int, int)
	 */
	public static byte[] subarray(final byte[] array, int startIndexInclusive, int endIndexExclusive)
	{
		if (array == null)
		{
			return null;
		}
		if (startIndexInclusive < 0)
		{
			startIndexInclusive = 0;
		}
		if (endIndexExclusive > array.length)
		{
			endIndexExclusive = array.length;
		}
		final int newSize = endIndexExclusive - startIndexInclusive;
		if (newSize <= 0)
		{
			return EMPTY_BYTE_ARRAY;
		}

		final byte[] subarray = new byte[newSize];
		System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
		return subarray;
	}

	/**
	 * <p>
	 * Produces a new {@code double} array containing the elements between the start and end indices.
	 *
	 * <p>
	 * The start index is inclusive, the end index exclusive. Null array input produces null output.
	 *
	 * @param array
	 *           the array
	 * @param startIndexInclusive
	 *           the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an
	 *           empty array.
	 * @param endIndexExclusive
	 *           elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces
	 *           empty array, overvalue (&gt;array.length) is demoted to array length.
	 * @return a new array containing the elements between the start and end indices.
	 * @see Arrays#copyOfRange(double[], int, int)
	 */
	public static double[] subarray(final double[] array, int startIndexInclusive, int endIndexExclusive)
	{
		if (array == null)
		{
			return null;
		}
		if (startIndexInclusive < 0)
		{
			startIndexInclusive = 0;
		}
		if (endIndexExclusive > array.length)
		{
			endIndexExclusive = array.length;
		}
		final int newSize = endIndexExclusive - startIndexInclusive;
		if (newSize <= 0)
		{
			return EMPTY_DOUBLE_ARRAY;
		}

		final double[] subarray = new double[newSize];
		System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
		return subarray;
	}

	/**
	 * <p>
	 * Produces a new {@code float} array containing the elements between the start and end indices.
	 *
	 * <p>
	 * The start index is inclusive, the end index exclusive. Null array input produces null output.
	 *
	 * @param array
	 *           the array
	 * @param startIndexInclusive
	 *           the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an
	 *           empty array.
	 * @param endIndexExclusive
	 *           elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces
	 *           empty array, overvalue (&gt;array.length) is demoted to array length.
	 * @return a new array containing the elements between the start and end indices.
	 * @see Arrays#copyOfRange(float[], int, int)
	 */
	public static float[] subarray(final float[] array, int startIndexInclusive, int endIndexExclusive)
	{
		if (array == null)
		{
			return null;
		}
		if (startIndexInclusive < 0)
		{
			startIndexInclusive = 0;
		}
		if (endIndexExclusive > array.length)
		{
			endIndexExclusive = array.length;
		}
		final int newSize = endIndexExclusive - startIndexInclusive;
		if (newSize <= 0)
		{
			return EMPTY_FLOAT_ARRAY;
		}

		final float[] subarray = new float[newSize];
		System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
		return subarray;
	}

	/**
	 * <p>
	 * Produces a new {@code boolean} array containing the elements between the start and end indices.
	 *
	 * <p>
	 * The start index is inclusive, the end index exclusive. Null array input produces null output.
	 *
	 * @param array
	 *           the array
	 * @param startIndexInclusive
	 *           the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in an
	 *           empty array.
	 * @param endIndexExclusive
	 *           elements up to endIndex-1 are present in the returned subarray. Undervalue (&lt; startIndex) produces
	 *           empty array, overvalue (&gt;array.length) is demoted to array length.
	 * @return a new array containing the elements between the start and end indices.
	 * @see Arrays#copyOfRange(boolean[], int, int)
	 */
	public static boolean[] subarray(final boolean[] array, int startIndexInclusive, int endIndexExclusive)
	{
		if (array == null)
		{
			return null;
		}
		if (startIndexInclusive < 0)
		{
			startIndexInclusive = 0;
		}
		if (endIndexExclusive > array.length)
		{
			endIndexExclusive = array.length;
		}
		final int newSize = endIndexExclusive - startIndexInclusive;
		if (newSize <= 0)
		{
			return EMPTY_BOOLEAN_ARRAY;
		}

		final boolean[] subarray = new boolean[newSize];
		System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
		return subarray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}. When
	 * an array is returned, it is always a new array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(null, null)     = null
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * ArrayUtils.addAll([null], [null]) = [null, null]
	 * ArrayUtils.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
	 * </pre>
	 *
	 * @param <T>
	 *           the component type of the array
	 * @param array1
	 *           the first array whose elements are added to the new array, may be {@code null}
	 * @param array2
	 *           the second array whose elements are added to the new array, may be {@code null}
	 * @return The new array, {@code null} if both arrays are {@code null}. The type of the new array is the type of the
	 *         first array, unless the first array is null, in which case the type is the same as the second array.
	 * @throws IllegalArgumentException
	 *            if the array types are incompatible
	 */
	public static <T> T[] addAll(final T[] array1, @SuppressWarnings("unchecked") final T... array2)
	{
		if (array1 == null)
		{
			return clone(array2);
		}
		else if (array2 == null)
		{
			return clone(array1);
		}
		final Class<?> type1 = array1.getClass().getComponentType();
		@SuppressWarnings("unchecked") // OK, because array is of type T
		final T[] joinedArray = (T[]) Array.newInstance(type1, array1.length + array2.length);
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		try
		{
			System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		}
		catch (final ArrayStoreException ase)
		{
			// Check if problem was due to incompatible types
			/*
			 * We do this here, rather than before the copy because: - it would be a wasted check most of the time - safer,
			 * in case check turns out to be too strict
			 */
			final Class<?> type2 = array2.getClass().getComponentType();
			if (!type1.isAssignableFrom(type2))
			{
				throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type1.getName(), ase);
			}
			throw ase; // No, so rethrow original
		}
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}. When
	 * an array is returned, it is always a new array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *           the first array whose elements are added to the new array.
	 * @param array2
	 *           the second array whose elements are added to the new array.
	 * @return The new boolean[] array.
	 */
	public static boolean[] addAll(final boolean[] array1, final boolean... array2)
	{
		if (array1 == null)
		{
			return clone(array2);
		}
		else if (array2 == null)
		{
			return clone(array1);
		}
		final boolean[] joinedArray = new boolean[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}. When
	 * an array is returned, it is always a new array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *           the first array whose elements are added to the new array.
	 * @param array2
	 *           the second array whose elements are added to the new array.
	 * @return The new char[] array.
	 */
	public static char[] addAll(final char[] array1, final char... array2)
	{
		if (array1 == null)
		{
			return clone(array2);
		}
		else if (array2 == null)
		{
			return clone(array1);
		}
		final char[] joinedArray = new char[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}. When
	 * an array is returned, it is always a new array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *           the first array whose elements are added to the new array.
	 * @param array2
	 *           the second array whose elements are added to the new array.
	 * @return The new byte[] array.
	 */
	public static byte[] addAll(final byte[] array1, final byte... array2)
	{
		if (array1 == null)
		{
			return clone(array2);
		}
		else if (array2 == null)
		{
			return clone(array1);
		}
		final byte[] joinedArray = new byte[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}. When
	 * an array is returned, it is always a new array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *           the first array whose elements are added to the new array.
	 * @param array2
	 *           the second array whose elements are added to the new array.
	 * @return The new short[] array.
	 */
	public static short[] addAll(final short[] array1, final short... array2)
	{
		if (array1 == null)
		{
			return clone(array2);
		}
		else if (array2 == null)
		{
			return clone(array1);
		}
		final short[] joinedArray = new short[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}. When
	 * an array is returned, it is always a new array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *           the first array whose elements are added to the new array.
	 * @param array2
	 *           the second array whose elements are added to the new array.
	 * @return The new int[] array.
	 */
	public static int[] addAll(final int[] array1, final int... array2)
	{
		if (array1 == null)
		{
			return clone(array2);
		}
		else if (array2 == null)
		{
			return clone(array1);
		}
		final int[] joinedArray = new int[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}. When
	 * an array is returned, it is always a new array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *           the first array whose elements are added to the new array.
	 * @param array2
	 *           the second array whose elements are added to the new array.
	 * @return The new long[] array.
	 */
	public static long[] addAll(final long[] array1, final long... array2)
	{
		if (array1 == null)
		{
			return clone(array2);
		}
		else if (array2 == null)
		{
			return clone(array1);
		}
		final long[] joinedArray = new long[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}. When
	 * an array is returned, it is always a new array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *           the first array whose elements are added to the new array.
	 * @param array2
	 *           the second array whose elements are added to the new array.
	 * @return The new float[] array.
	 */
	public static float[] addAll(final float[] array1, final float... array2)
	{
		if (array1 == null)
		{
			return clone(array2);
		}
		else if (array2 == null)
		{
			return clone(array1);
		}
		final float[] joinedArray = new float[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all of the elements {@code array2}. When
	 * an array is returned, it is always a new array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1
	 *           the first array whose elements are added to the new array.
	 * @param array2
	 *           the second array whose elements are added to the new array.
	 * @return The new double[] array.
	 */
	public static double[] addAll(final double[] array1, final double... array2)
	{
		if (array1 == null)
		{
			return clone(array2);
		}
		else if (array2 == null)
		{
			return clone(array1);
		}
		final double[] joinedArray = new double[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	// Clone
	//-----------------------------------------------------------------------
	/**
	 * <p>
	 * Shallow clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * The objects in the array are not cloned, thus there is no special handling for multi-dimensional arrays.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param <T>
	 *           the component type of the array
	 * @param array
	 *           the array to shallow clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static <T> T[] clone(final T[] array)
	{
		if (array == null)
		{
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array
	 *           the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static long[] clone(final long[] array)
	{
		if (array == null)
		{
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array
	 *           the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static int[] clone(final int[] array)
	{
		if (array == null)
		{
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array
	 *           the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static short[] clone(final short[] array)
	{
		if (array == null)
		{
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array
	 *           the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static char[] clone(final char[] array)
	{
		if (array == null)
		{
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array
	 *           the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static byte[] clone(final byte[] array)
	{
		if (array == null)
		{
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array
	 *           the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static double[] clone(final double[] array)
	{
		if (array == null)
		{
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array
	 *           the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static float[] clone(final float[] array)
	{
		if (array == null)
		{
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array
	 *           the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static boolean[] clone(final boolean[] array)
	{
		if (array == null)
		{
			return null;
		}
		return array.clone();
	}
}
