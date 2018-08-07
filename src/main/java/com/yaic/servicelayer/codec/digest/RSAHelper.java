package com.yaic.servicelayer.codec.digest;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yaic.servicelayer.charset.StandardCharset;
import com.yaic.servicelayer.codec.binary.Base64Decoder;
import com.yaic.servicelayer.codec.factory.CodecFactory;
import com.yaic.servicelayer.util.ArrayUtil;
import com.yaic.servicelayer.util.StringUtil;


/**
 * @Description RSA签名和加解密
 * @author wangzhonghua
 *
 */
public class RSAHelper
{
	private static final Logger logger = LoggerFactory.getLogger(RSAHelper.class);

	/**
	 * 用私钥对信息生成数字签名MD5withRSA
	 *
	 * @param data
	 *           加密数据
	 * @param privateKey
	 *           私钥
	 * @return MD5withRSA签名结果
	 *
	 * @throws Exception
	 *            异常 加密抛出的异常
	 */
	public static byte[] signByMD5withRSA(final byte[] data, final String privateKey) throws Exception
	{
		if (ArrayUtil.isEmpty(data))
		{
			logger.warn("参数不合法,data is null");
			return new byte[0];
		}
		if (StringUtil.isEmpty(privateKey))
		{
			logger.warn("参数不合法,privateKey:[{}]", privateKey);
			return new byte[0];
		}

		final PrivateKey priKey = getPrivateKey(privateKey);
		// 用私钥对信息生成数字签名
		final Signature signature = CodecFactory.getMD5withRSASignature();
		signature.initSign(priKey);
		signature.update(data);
		return signature.sign();
	}

	/**
	 * 校验数字签名MD5withRSA
	 *
	 * @param data
	 *           加密数据
	 * @param publicKey
	 *           公钥
	 * @param sign
	 *           数字签名
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 *            异常 签名抛出的异常
	 */
	public static boolean verifyByMD5withRSA(final byte[] data, final String publicKey, final byte[] sign) throws Exception
	{
		if (ArrayUtil.isEmpty(data))
		{
			logger.warn("参数不合法,data is null");
			return false;
		}

		if (StringUtil.isEmpty(publicKey))
		{
			logger.warn("参数不合法,publicKey:[{}]", publicKey);
			return false;
		}

		if (ArrayUtil.isEmpty(sign))
		{
			logger.warn("参数不合法,sign is null");
			return false;
		}

		final PublicKey pubKey = getPublicKey(publicKey);
		final Signature signature = CodecFactory.getMD5withRSASignature();
		signature.initVerify(pubKey);
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(sign);
	}


	/**
	 * 解密<br>
	 * 用私钥解密
	 *
	 * @param data
	 *           解密数据
	 * @param key
	 *           私钥
	 * @return 解密结果
	 * @throws Exception
	 *            异常 解密抛出的异常
	 */
	public static String decryptByPrivateKey(final byte[] data, final String key) throws Exception
	{
		if (ArrayUtil.isEmpty(data) || StringUtil.isEmpty(key))
		{
			logger.warn("参数不合法,data is null");
			return null;
		}
		if (StringUtil.isEmpty(key))
		{
			logger.warn("参数不合法,key:[{}]", key);
			return null;
		}

		final Key privateKey = getPrivateKey(key);
		// 对数据解密
		final Cipher cipher = CodecFactory.getRSACipher();
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		// 解密时超过128字节就报错。为此采用分段解密的办法来解密
		final StringBuilder sb = new StringBuilder();
		for (int i = 0, len = data.length; i < len; i += 128)
		{
			final byte[] doFinal = cipher.doFinal(ArrayUtil.subarray(data, i, i + 128));
			sb.append(new String(doFinal, StandardCharset.UTF_8));
		}
		return sb.toString();
	}

	/**
	 * 解密<br>
	 * 用公钥解密
	 *
	 * @param data
	 *           加密数据
	 * @param key
	 *           公钥
	 * @return 解密后数据
	 * @throws Exception
	 *            异常 解密抛出的异常
	 */
	public static String decryptByPublicKey(final byte[] data, final String key) throws Exception
	{
		if (ArrayUtil.isEmpty(data))
		{
			logger.warn("参数不合法,data is null");
			return null;
		}

		if (StringUtil.isEmpty(key))
		{
			logger.warn("参数不合法,key:[{}]", key);
			return null;
		}

		final Key publicKey = getPublicKey(key);
		// 对数据解密
		final Cipher cipher = CodecFactory.getRSACipher();
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		// 解密时超过128字节就报错。为此采用分段解密的办法来解密
		final StringBuilder sb = new StringBuilder();
		for (int i = 0, len = data.length; i < len; i += 128)
		{
			final byte[] doFinal = cipher.doFinal(ArrayUtil.subarray(data, i, i + 128));
			sb.append(new String(doFinal, StandardCharset.UTF_8));
		}
		return sb.toString();
	}

	/**
	 * 加密<br>
	 * 用公钥加密
	 *
	 * @param data
	 *           待加密数据
	 * @param key
	 *           公钥
	 * @return 加密后数据
	 * @throws Exception
	 *            异常 加密抛出的异常
	 */
	public static byte[] encryptByPublicKey(final byte[] data, final String key) throws Exception
	{
		if (ArrayUtil.isEmpty(data))
		{
			logger.warn("参数不合法,data is null", key);
			return new byte[0];
		}
		if (StringUtil.isEmpty(key))
		{
			logger.warn("参数不合法,key:[{}]", key);
			return new byte[0];
		}

		byte[] dataReturn = null;
		final Key publicKey = getPublicKey(key);
		// 对数据加密
		final Cipher cipher = CodecFactory.getRSACipher();
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		// 加密时超过117字节就报错。为此采用分段加密的办法来加密
		for (int i = 0, len = data.length; i < len; i += 100)
		{
			final byte[] doFinal = cipher.doFinal(ArrayUtil.subarray(data, i, i + 100));
			dataReturn = ArrayUtil.addAll(dataReturn, doFinal);
		}
		return dataReturn;
	}

	/**
	 * 加密<br>
	 * 用私钥加密
	 *
	 * @param data
	 *           待加密数据
	 * @param key
	 *           私钥
	 * @return 加密后数据
	 * @throws Exception
	 *            异常 加密抛出的异常
	 */
	public static byte[] encryptByPrivateKey(final byte[] data, final String key) throws Exception
	{
		if (ArrayUtil.isEmpty(data))
		{
			logger.warn("参数不合法,data is null", key);
			return new byte[0];
		}
		if (StringUtil.isEmpty(key))
		{
			logger.warn("参数不合法,key:[{}]", key);
			return new byte[0];
		}

		byte[] dataReturn = null;
		final Key privateKey = getPrivateKey(key);
		// 对数据加密
		final Cipher cipher = CodecFactory.getRSACipher();
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		// 加密时超过117字节就报错。为此采用分段加密的办法来加密
		for (int i = 0, len = data.length; i < len; i += 100)
		{
			final byte[] doFinal = cipher.doFinal(ArrayUtil.subarray(data, i, i + 100));
			dataReturn = ArrayUtil.addAll(dataReturn, doFinal);
		}
		return dataReturn;
	}

	/**
	 * 用私钥对信息生成数字签名SHA256WITHRSA
	 *
	 * @param data
	 *           加密数据
	 * @param privateKey
	 *           私钥
	 * @return MD5withRSA签名结果
	 *
	 * @throws Exception
	 *            异常 加密抛出的异常
	 */
	public static byte[] signBySHA256WITHRSA(final byte[] data, final String privateKey) throws Exception
	{
		if (ArrayUtil.isEmpty(data))
		{
			logger.warn("参数不合法,data is null");
			return new byte[0];
		}
		if (StringUtil.isEmpty(privateKey))
		{
			logger.warn("参数不合法,privateKey:[{}]", privateKey);
			return new byte[0];
		}

		final PrivateKey priKey = getPrivateKey(privateKey);
		// 用私钥对信息生成数字签名
		final Signature signature = CodecFactory.getSHA256WITHRSASignature();
		signature.initSign(priKey);
		signature.update(data);
		return signature.sign();
	}

	/**
	 * 校验数字签名SHA256WITHRSA
	 *
	 * @param data
	 *           加密数据
	 * @param publicKey
	 *           公钥
	 * @param sign
	 *           数字签名
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 *            异常 签名抛出的异常
	 */
	public static boolean verifyBySHA256WITHRSA(final byte[] data, final String publicKey, final byte[] sign) throws Exception
	{
		if (ArrayUtil.isEmpty(data))
		{
			logger.warn("参数不合法,data is null");
			return false;
		}
		if (StringUtil.isEmpty(publicKey))
		{
			logger.warn("参数不合法,publicKey:[{}]", publicKey);
			return false;
		}

		if (ArrayUtil.isEmpty(sign))
		{
			logger.warn("参数不合法,sign is null");
			return false;
		}

		final PublicKey pubKey = getPublicKey(publicKey);
		final Signature signature = CodecFactory.getSHA256WITHRSASignature();
		signature.initVerify(pubKey);
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(sign);
	}

	private static PrivateKey getPrivateKey(final String privateKey) throws InvalidKeySpecException
	{
		// 解密由base64编码的私钥
		final byte[] keyBytes = Base64Decoder.decode(privateKey);
		// 构造PKCS8EncodedKeySpec对象
		final PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		// KEY_ALGORITHM 指定的加密算法
		final KeyFactory keyFactory = CodecFactory.getRSAKeyFactory();
		// 取私钥匙对象
		return keyFactory.generatePrivate(pkcs8KeySpec);
	}

	private static PublicKey getPublicKey(final String publicKey) throws InvalidKeySpecException
	{
		// 解密由base64编码的公钥
		final byte[] keyBytes = Base64Decoder.decode(publicKey);
		// 构造X509EncodedKeySpec对象
		final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		// KEY_ALGORITHM 指定的加密算法
		final KeyFactory keyFactory = CodecFactory.getRSAKeyFactory();
		// 取公钥匙对象
		return keyFactory.generatePublic(keySpec);
	}
}
