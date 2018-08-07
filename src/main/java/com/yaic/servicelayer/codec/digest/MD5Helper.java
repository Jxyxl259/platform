package com.yaic.servicelayer.codec.digest;

import java.nio.charset.Charset;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.yaic.servicelayer.charset.StandardCharset;
import com.yaic.servicelayer.util.ArrayUtil;
import com.yaic.servicelayer.util.StringUtil;


/**
 * @Description 功能：MD5签名
 * @author wangzhonghua
 *
 */
public class MD5Helper
{
	private static final Logger logger = LoggerFactory.getLogger(MD5Helper.class);

	/**
	 * 加密字符串
	 *
	 * @param text
	 *           需要加密的字符串
	 * @param key
	 *           密钥
	 * @param charset
	 *           编码格式
	 *
	 * @return 加密结果
	 */
	public static String sign(final String text, final String key, final String charset)
	{
		return sign(text, key, StandardCharset.forName(charset));
	}

	/**
	 * 加密字符串
	 *
	 * @param text
	 *           需要加密的字符串
	 * @param key
	 *           密钥
	 * @param charset
	 *           编码格式
	 *
	 * @return 加密结果
	 */
	public static String sign(String text, final String key, final Charset charset)
	{	
		Assert.notNull(charset, "charset must be not null");
		if (StringUtil.isEmpty(text) || StringUtil.isEmpty(key))
		{
			logger.warn("参数不合法,text:[{}],key:[{}]", text, key);
			return null;
		}

		text = text + key;
		return DigestUtils.md5Hex(text.getBytes(charset));
	}

	/**
	 * MD5加密 默认UTF-8编码
	 *
	 * @param text
	 *           需要加密的字符串
	 * @return 加密结果
	 */
	public static String sign(final String text)
	{
		if (StringUtil.isEmpty(text))
		{
			logger.warn("参数不合法,text:[{}]", text);
			return null;
		}
		return DigestUtils.md5Hex(text.getBytes(StandardCharset.UTF_8));
	}

	/**
	 * 加密字符串
	 *
	 * @param text
	 *           需要加密的字符串
	 * @param charset
	 *           编码格式
	 * @return 加密结果
	 */
	public static String sign(final String text, final String charset)
	{
		return sign(text, StandardCharset.forName(charset));
	}
	
	/**
	 * 加密字符串
	 *
	 * @param text
	 *           需要加密的字符数组
	 * @param charset
	 *           编码格式
	 * @return 加密结果
	 */
	public static String sign(final char[] text,final String charset)
	{	
		if(ArrayUtil.isEmpty(text)){
			logger.warn("参数不合法,text:[{}]", text);
			return null;
		}
		return sign(new String(text),charset);
	}
	
	/**
	 * 加密字符串
	 *
	 * @param text
	 *           需要加密的字节数组
	 *           
	 * @return 加密结果
	 */
	public static String sign(byte[] text)
	{
		if(ArrayUtil.isEmpty(text)){
			logger.warn("参数不合法,text:[{}]", text);
			return null;
		}
		return DigestUtils.md5Hex(text);
	}

	/**
	 * 加密字符串
	 *
	 * @param text
	 *           需要加密的字符串
	 * @param charset
	 *           编码格式
	 * @return 加密结果
	 */
	public static String sign(final String text, final Charset charset)
	{
		Assert.notNull(charset, "charset must be not null");
		if (StringUtil.isEmpty(text))
		{
			return text;
		}
		return DigestUtils.md5Hex(text.getBytes(charset));
	}

	/**
	 * 加密字符串
	 *
	 * @param text
	 *           需要加密的字符串
	 * @param sign
	 *           加密结果
	 * @param key
	 *           密钥
	 * @param charset
	 *           编码格式
	 * @return 验签结果
	 */
	public static boolean verify(String text, final String sign, final String key, final Charset charset)
	{	
	    	Assert.notNull(charset, "charset must be not null");
		if (StringUtil.isEmpty(text) || StringUtil.isEmpty(sign) || StringUtil.isEmpty(key))
		{
			logger.warn("参数不合法,text:[{}],sign:[{}],key:[{}]", text, sign, key);
			return false;
		}

		text = text + key;
		final String mysign = DigestUtils.md5Hex(text.getBytes(charset));
		return mysign.equals(sign);
	}
}
