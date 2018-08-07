package com.yaic.util;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yaic.servicelayer.charset.StandardCharset;
import com.yaic.servicelayer.codec.binary.Base64Decoder;
import com.yaic.servicelayer.codec.binary.Base64Encoder;
import com.yaic.servicelayer.util.StringUtil;


/**
 * 艺龙网AES加密解密
 * Created by yepei.ye on 2016/4/1.
 * Description:
 */
public class YiLongAESUtil {
    private static final Logger logger = LoggerFactory.getLogger(YiLongAESUtil.class);
    private static final String KEY_AES = "AES";
    private static final String KEY_MD5 = "MD5";

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return
     */
    public static String encrypt(String data, String key) {
        return doAES(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @param key  解密密钥
     * @return
     */
    public static String decrypt(String data, String key) {
        return doAES(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 加解密
     *
     * @param data
     * @param key
     * @param mode
     * @return
     */
    private static String doAES(String data, String key, int mode) {
        try {
            if (StringUtil.isEmpty(data) || StringUtil.isEmpty(key)) {
                return null;
            }
            boolean encrypt = mode == Cipher.ENCRYPT_MODE;
            byte[] content;
            if (encrypt) {
                content = data.getBytes(StandardCharset.UTF_8);
            } else {
                content = Base64Decoder.decode(data);
            }

            MessageDigest md5Digest = MessageDigest.getInstance(KEY_MD5);

            SecretKeySpec keySpec = new SecretKeySpec(md5Digest.digest(key.getBytes(StandardCharset.UTF_8)), KEY_AES);
            Cipher cipher = Cipher.getInstance(KEY_AES);// 创建密码器
            cipher.init(mode, keySpec);// 初始化
            byte[] result = cipher.doFinal(content);
            if (encrypt) {
                return Base64Encoder.encode(result);
            } else {
                return new String(result, StandardCharset.UTF_8);
            }
        } catch (Exception e) {
            logger.error("AES密文处理异常{},密文数据:{}", e, data);
        }
        return null;
    }
}
