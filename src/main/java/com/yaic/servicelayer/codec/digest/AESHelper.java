package com.yaic.servicelayer.codec.digest;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yaic.servicelayer.charset.StandardCharset;
import com.yaic.servicelayer.codec.binary.Base64Decoder;
import com.yaic.servicelayer.codec.binary.Base64Encoder;
import com.yaic.servicelayer.codec.factory.CodecFactory;
import com.yaic.servicelayer.util.RandomUtil;
import com.yaic.servicelayer.util.StringUtil;


/**
 * @Description AES加密解密
 * @author wangzhonghua
 *
 */
public class AESHelper
{
	private static final Logger logger = LoggerFactory.getLogger(AESHelper.class);
	private static final String KEY_AES = "AES";

	/**
	 * 加密
	 *
	 * @param data
	 *           需要加密的内容
	 * @param key
	 *           加密密码
	 * @return AES加密结果
	 */
	public static String encrypt(final String data, final String key)
	{
		return doAES(data, key, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 解密
	 *
	 * @param data
	 *           待解密内容
	 * @param key
	 *           解密密钥
	 * @return 解密之后的字符串
	 */
	public static String decrypt(final String data, final String key)
	{
		return doAES(data, key, Cipher.DECRYPT_MODE);
	}

	/**
	 * 加密
	 *
	 * @param seed
	 *           加密的种子
	 * @param content
	 *           待加密字符串
	 * @return 解密结果
	 */
	public static String encryptBase64(final String seed, final String content)
	{
		if (StringUtil.isEmpty(seed) || StringUtil.isEmpty(content))
		{
			logger.warn("参数不合法,seed:[{}],content:[{}]", seed, content);
			return null;
		}

		try
		{
			final byte[] rawKey = getRawKey(seed.getBytes(StandardCharset.UTF_8));
			final byte[] result = encrypt(rawKey, content.getBytes(StandardCharset.UTF_8));
			return Base64Encoder.encode(result);
		}
		catch (final Exception e)
		{
			throw new RuntimeException("AES加密base64编码出错", e);
		}
	}

	/**
	 * 解密
	 *
	 * @param seed
	 *           加密的种子
	 * @param encrypted
	 *           待解密字符串
	 * @return 解密结果
	 */
	public static String decryptBase64(final String seed, final String encrypted)
	{
		if (StringUtil.isEmpty(seed) || StringUtil.isEmpty(encrypted))
		{
		    	logger.warn("参数不合法,seed:[{}],encrypted:[{}]", seed, encrypted);
			return null;
		}

		try
		{
			final byte[] rawKey = getRawKey(seed.getBytes(StandardCharset.UTF_8));
			final byte[] enc = Base64Decoder.decode(encrypted);
			final byte[] result = decrypt(rawKey, enc);
			return new String(result, StandardCharset.UTF_8);
		}
		catch (final Exception e)
		{
			throw new RuntimeException("AES解密base64出错", e);
		}
	}

	/**
	 * 加解密
	 *
	 * @param data
	 * @param key
	 * @param mode
	 * @return 加密/解密之后的字符串
	 */
	private static String doAES(final String data, final String key, final int mode)
	{
		if (StringUtil.isEmpty(data) || StringUtil.isEmpty(key))
		{
			logger.warn("参数不合法,data:[{}],key:[{}]", data, key);
			return null;
		}

		final boolean encrypt = mode == Cipher.ENCRYPT_MODE;
		byte[] content;
		if (encrypt)
		{
			content = data.getBytes(StandardCharset.UTF_8);
		}
		else
		{
			content = Base64Decoder.decode(data);
		}

		final MessageDigest md5Digest = CodecFactory.getMD5();
		final SecretKeySpec keySpec = new SecretKeySpec(md5Digest.digest(key.getBytes(StandardCharset.UTF_8)), KEY_AES);
		final Cipher cipher = CodecFactory.getAESCipher();// 创建密码器

		byte[] result;
		try
		{
			cipher.init(mode, keySpec); // 初始化
			result = cipher.doFinal(content);
		}
		catch (final Exception e)
		{
			throw new RuntimeException("AES密文处理异常,密文数据:" + data, e);
		}

		if (encrypt)
		{
			return Base64Encoder.encode(result);
		}
		else
		{
			return new String(result, StandardCharset.UTF_8);
		}
	}
	
	private static byte[] getRawKey(final byte[] seed)
	{
		final KeyGenerator kgen = CodecFactory.getAESKeyGenerator();
		final SecureRandom sr = RandomUtil.getSha1prngRandom(seed);
		kgen.init(128, sr); // 192 and 256 bits may not be available
		final SecretKey skey = kgen.generateKey();
		return skey.getEncoded();
	}

	private static byte[] encrypt(final byte[] raw, final byte[] clear)
			throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException
	{
		final SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		final Cipher cipher = CodecFactory.getAESCipher(); // AES/CBC/PKCS5Padding
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		return cipher.doFinal(clear);
	}

	private static byte[] decrypt(final byte[] raw, final byte[] encrypted)
			throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException
	{
		final SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		final Cipher cipher = CodecFactory.getAESCipher(); // AES/CBC/PKCS5Padding
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		return cipher.doFinal(encrypted);
	}
}
