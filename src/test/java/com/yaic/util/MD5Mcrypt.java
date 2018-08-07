/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 
 * (the "License"); you may not use this file except in compliance with the License. You may obtain 
 * a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * =================================================================================================
 * 
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 * 
 * +------------------------------------------------------------------------------------------------+
 * | License: http://mcrypt.buession.com.cn/LICENSE 												|
 * | Author: Yong.Teng <webmaster@buession.com> 													|
 * | Copyright @ 2013-2014 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.yaic.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

import com.yaic.servicelayer.charset.StandardCharset;

/**
 * 提供对象 MD5 加密
 */
public final class MD5Mcrypt extends Mcrypt {

	public MD5Mcrypt() {
		super(Mcrypt.MD5);
	}

	/**
	 * @param count
	 *        重复加密次数
	 */
	public MD5Mcrypt(final int count) {
		super(Mcrypt.MD5, count);
	}

	/**
	 * @param count
	 *        重复加密次数
	 * @param provider
	 *        信息摘要对象的提供者
	 */
	public MD5Mcrypt(final int count, final Provider provider) {
		super(Mcrypt.MD5, count, provider);
	}

	/**
	 * @param characterEncoding
	 *        字符编码
	 */
	public MD5Mcrypt(final String characterEncoding) {
		super(Mcrypt.MD5, characterEncoding);
	}

	/**
	 * @param characterEncoding
	 *        字符编码
	 * @param provider
	 *        信息摘要对象的提供者
	 */
	public MD5Mcrypt(final String characterEncoding, Provider provider) {
		super(Mcrypt.MD5, characterEncoding, provider);
	}

	/**
	 * @param characterEncoding
	 *        字符编码
	 * @param count
	 *        重复加密次数
	 */
	public MD5Mcrypt(final String characterEncoding, int count) {
		super(Mcrypt.MD5, characterEncoding, count);
	}

	/**
	 * @param characterEncoding
	 *        字符编码
	 * @param count
	 *        重复加密次数
	 * @param provider
	 *        信息摘要对象的提供者
	 */
	public MD5Mcrypt(final String characterEncoding, final int count, final Provider provider) {
		super(Mcrypt.MD5, characterEncoding, count, provider);
	}

	/**
	 * @param characterEncoding
	 *        字符编码
	 * @param salt
	 *        加密密钥
	 */
	public MD5Mcrypt(final String characterEncoding, final String salt) {
		super(Mcrypt.MD5, characterEncoding, salt);
	}

	/**
	 * @param characterEncoding
	 *        字符编码
	 * @param salt
	 *        加密密钥
	 * @param provider
	 *        信息摘要对象的提供者
	 */
	public MD5Mcrypt(final String characterEncoding, final String salt, final Provider provider) {
		super(Mcrypt.MD5, characterEncoding, salt, provider);
	}

	/**
	 * @param characterEncoding
	 *        字符编码
	 * @param salt
	 *        加密密钥
	 * @param count
	 *        重复加密次数
	 */
	public MD5Mcrypt(final String characterEncoding, final String salt, final int count) {
		super(Mcrypt.MD5, characterEncoding, salt, count);
	}

	/**
	 * @param characterEncoding
	 *        字符编码
	 * @param salt
	 *        加密密钥
	 * @param count
	 *        重复加密次数
	 * @param provider
	 *        信息摘要对象的提供者
	 */
	public MD5Mcrypt(final String characterEncoding, final String salt, final int count,
			final Provider provider) {
		super(Mcrypt.MD5, characterEncoding, salt, count, provider);
	}
	/**
	 * 
	 * <p>User: hguoqing
	 * <p>Date: 2017-3-1
	 * <p>Version: 1.0
	 * @param inString
	 * @return
	 */
    public static String getMd5Code(String inString) {

        // 要加密的字符串字节型数组
        byte defaultBytes[] = inString.getBytes(StandardCharset.UTF_8);
        // 加密后的字符串
        StringBuffer hexString;
        // 得到MessageDigest加密对象
        MessageDigest algorithm = null;
        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 重置上面字节数
        algorithm.reset();
        // 使用指定的字节数组更新
        algorithm.update(defaultBytes);

        byte messageDigest[] = algorithm.digest();
        hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++) {
            String hex = Integer.toHexString(0xff & messageDigest[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    

}