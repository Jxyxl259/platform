package com.yaic.servicelayer.codec.factory;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;


/**
 * @Description 获取各种算法的实例
 * @author wangzhonghua
 *
 */
public class CodecFactory
{
	/**
	 * Represents the digest algorithm MD5
	 */
	public final static String MD5 = "MD5";

	/**
	 * Represents the digest algorithm SHA
	 */
	public final static String SHA = "SHA";

	/**
	 * Represents the digest algorithm SHA-1
	 */
	public final static String SHA1 = "SHA-1";

	/**
	 * Represents the digest algorithm SHA-256
	 */
	public final static String SHA256 = "SHA-256";

	/**
	 * Represents the digest algorithm SHA-512
	 */
	public final static String SHA512 = "SHA-512";

	/**
	 *
	 */
	public final static String RSA = "RSA";

	/**
	 *
	 */
	public final static String MD5withRSA = "MD5withRSA";
	
	/**
	 *
	 */
	public final static String SHA256WITHRSA = "SHA256WITHRSA";

	/**
	 *
	 */
	public final static String AES = "AES";

	/**
	 * @return MD5 MessageDigest
	 */
	public static MessageDigest getMD5()
	{
		try
		{
			return MessageDigest.getInstance(MD5);
		}
		catch (final NoSuchAlgorithmException e)
		{
			// never happens, because we made sure the algorithm exists
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return SHA MessageDigest
	 */
	public static MessageDigest getSHA()
	{
		try
		{
			return MessageDigest.getInstance(SHA);
		}
		catch (final NoSuchAlgorithmException e)
		{
			// never happens, because we made sure the algorithm exists
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return SHA-1 MessageDigest
	 */
	public static MessageDigest getSHA1()
	{
		try
		{
			return MessageDigest.getInstance(SHA1);
		}
		catch (final NoSuchAlgorithmException e)
		{
			// never happens, because we made sure the algorithm exists
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return SHA-256 MessageDigest
	 */
	public static MessageDigest getSHA256()
	{
		try
		{
			return MessageDigest.getInstance(SHA256);
		}
		catch (final NoSuchAlgorithmException e)
		{
			// never happens, because we made sure the algorithm exists
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return SHA-512 MessageDigest
	 */
	public static MessageDigest getSHA512()
	{
		try
		{
			return MessageDigest.getInstance(SHA512);
		}
		catch (final NoSuchAlgorithmException e)
		{
			// never happens, because we made sure the algorithm exists
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return RSA KeyFactory instance
	 */
	public static KeyFactory getRSAKeyFactory()
	{
		try
		{
			return KeyFactory.getInstance(RSA);
		}
		catch (final NoSuchAlgorithmException e)
		{
			// never happens, because we made sure the algorithm exists
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return RSA Cipher instance
	 */
	public static Cipher getRSACipher()
	{
		try
		{
			return Cipher.getInstance(RSA);
		}
		catch (final Exception e)
		{
			// never happens, because we made sure the algorithm exists
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return AES Cipher instance
	 */
	public static Cipher getAESCipher()
	{
		try
		{
			return Cipher.getInstance(AES);
		}
		catch (final Exception e)
		{
			// never happens, because we made sure the algorithm exists
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return AES KeyGenerator instance
	 */
	public static KeyGenerator getAESKeyGenerator()
	{
		try
		{
			return KeyGenerator.getInstance(AES);
		}
		catch (final Exception e)
		{
			// never happens, because we made sure the algorithm exists
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return MD5withRSA Signature
	 */
	public static Signature getMD5withRSASignature()
	{
		try
		{
			return Signature.getInstance(MD5withRSA);
		}
		catch (final NoSuchAlgorithmException e)
		{
			// never happens, because we made sure the algorithm exists
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @return SHA256WITHRSA Signature
	 */
	public static Signature getSHA256WITHRSASignature()
	{
		try
		{
			return Signature.getInstance(SHA256WITHRSA);
		}
		catch (final NoSuchAlgorithmException e)
		{
			// never happens, because we made sure the algorithm exists
			throw new RuntimeException(e);
		}
	}

}
