package com.yaic.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import org.apache.commons.codec.digest.DigestUtils;

import com.yaic.servicelayer.charset.StandardCharset;
import com.yaic.servicelayer.util.StringUtil;

public class MD5 {
	
	
	public static String getMd5Code(String inString,String encodingType) {

        // 要加密的字符串字节型数组
        byte defaultBytes[] = null;
        try {
            defaultBytes = inString.getBytes(encodingType);
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
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


    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
    	text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    
    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param sign 签名结果
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
    	text = text + key;
    	String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
    	if(mysign.equals(sign)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (StringUtil.isEmpty(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

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
	
	/**
	 * 获得MD5加密字符串
	 *
	 * @param source 源字符串
	 * @return 加密后的字符串
	 */
	public static String getMD5(String source) throws Exception {
		String mdString = null;
		if (source != null) {
			// 关键是这句
			mdString = getMD5(source.getBytes(StandardCharset.UTF_8));
		}
		return mdString;
	}

	/**
	 * 获得MD5加密字符串
	 *
	 * @param source 源字节数组
	 * @return 加密后的字符串
	 */
	public static String getMD5(byte[] source) throws Exception {
		String s = null;
		char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		final int temp = 0xf;
		final int arraySize = 32;
		final int strLen = 16;
		final int offset = 4;
	
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(source);
		byte[] tmp = md.digest();
		char[] str = new char[arraySize];
		int k = 0;
		for (int i = 0; i < strLen; i++) {
	    	byte byte0 = tmp[i];
	    	str[k++] = hexDigits[byte0 >>> offset & temp];
	    	str[k++] = hexDigits[byte0 & temp];
		}
		s = new String(str);
		return s;
	}

	/*public static void main(String[] args) throws Exception{
		// *************************  getMd5Code方法  *************************
		//System.out.println(getMd5Code("key" + "content"));
		
		// *************************  getMD5方法  *************************
		// 待签名字符串
		String requestString = "requestString";
		// 签名秘钥
		String signSecretKey = "signSecretKey";
		// 待签名字符串
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(requestString);
		stringBuilder.append(signSecretKey);
		// 计算签名
		String sign = getMD5(stringBuilder.toString());
		System.out.println(sign);
	}*/
	
}
