package com.yaic.servicelayer.codec.digest;

import org.apache.commons.codec.digest.DigestUtils;

import com.yaic.servicelayer.util.StringUtil;


/**
 * @Description sha1加密
 * @author wangzhonghua
 *
 */
public class Sha1Helper
{
	/**
	 * @param str
	 *           sha1加密字符串
	 * @return sha1加密结果
	 */
	public static String sha1Hex(final String str)
	{	
		if (StringUtil.isEmpty(str)) {
			return null;
		}
		return DigestUtils.sha1Hex(str);
	}

}
