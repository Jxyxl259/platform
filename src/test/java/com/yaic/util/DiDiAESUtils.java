package com.yaic.util;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yaic.servicelayer.charset.StandardCharset;
import com.yaic.servicelayer.codec.binary.Base64Decoder;
import com.yaic.servicelayer.codec.binary.Base64Encoder;
import com.yaic.servicelayer.util.RandomUtil;

/**
 * Created by xuyuanchao on 2017/2/8.
 */
public class DiDiAESUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiDiAESUtils.class);

    public static String encrypt(String seed, String content) {
        try {
            byte[] rawKey = getRawKey(seed.getBytes(StandardCharset.UTF_8));
            byte[] result = encrypt(rawKey, content.getBytes(StandardCharset.UTF_8));
            return toHex(result);
        } catch (Exception e) {
            LOGGER.error("AES加密出错", e);
            throw new RuntimeException(e);
        }
    }

    public static String encryptBase64(String seed, String content) {
        try {
            byte[] rawKey = getRawKey(seed.getBytes(StandardCharset.UTF_8));
            byte[] result = encrypt(rawKey, content.getBytes(StandardCharset.UTF_8));
            return Base64Encoder.encode(result);
        } catch (Exception e) {
            LOGGER.error("AES加密base64编码出错", e);
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String seed, String encrypted) {
        try {
            byte[] rawKey = getRawKey(seed.getBytes(StandardCharset.UTF_8));
            byte[] enc = toByte(encrypted);
            byte[] result = decrypt(rawKey, enc);
            return new String(result, StandardCharset.UTF_8);
        } catch (Exception e) {
            LOGGER.error("AES解密出错", e);
            throw new RuntimeException(e);
        }
    }

    public static String decryptBase64(String seed, String encrypted) {
        try {
            byte[] rawKey = getRawKey(seed.getBytes(StandardCharset.UTF_8));
            byte[] enc = Base64Decoder.decode(encrypted);
            byte[] result = decrypt(rawKey, enc);
            return new String(result, StandardCharset.UTF_8);
        } catch (Exception e) {
            LOGGER.error("AES解密base64出错", e);
            throw new RuntimeException(e);
        }
    }

    private static byte[] getRawKey(byte[] seed) throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = RandomUtil.getSha1prngRandom(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES"); // AES/CBC/PKCS5Padding
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES"); // AES/CBC/PKCS5Padding
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return result;
    }

    private static String toHex(byte[] buf) {
        String HEX = "0123456789ABCDEF";
        if (buf == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            result.append(HEX.charAt((buf[i] >> 4) & 0x0f)).append(HEX.charAt(buf[i] & 0x0f));
        }
        return result.toString();
    }
}