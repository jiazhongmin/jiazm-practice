package com.jiazm.practice;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author jiazhongmin
 * @Date 2023/10/8 11:14
 * * Description: 配合前端CryptoJS实现加密、解密工作。
 * * CryptoJS 是一个 JavaScript 库，提供了一系列密码学函数和工具，用于加密、解密、生成摘要等任务。
 * * 它支持多种加密算法，包括常见的对称加密算法（如 AES、DES）和非对称加密算法（如 RSA）。
 **/
public class CryptoUtil {
    //需要前端与后端配置一致
    private final static String KEY = "pkvun1zx5hiaiqd4uqwt52wxpo0ozg9s";


    /**
     * AES加密
     *
     * @param data 需要加密的数据
     * @return 加密字符串 加密失败返回null
     */
    public static String encrypt(String data) {
        if (StringUtils.isBlank(data)) {
            return null;
        }
        try {
            byte[] iv = KEY.substring(0, 12).getBytes(StandardCharsets.UTF_8);
            byte[] contentBytes = data.getBytes(StandardCharsets.UTF_8);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec params = new GCMParameterSpec(128, iv);
            SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, params);
            byte[] encryptData = cipher.doFinal(contentBytes);
            return Base64.getEncoder().encodeToString(encryptData);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param data 需要解密的数据
     * @return 解密字符串 解密失败返回null
     */
    public static String decrypt(String data) {
        if (StringUtils.isBlank(data)) {
            return null;
        }
        try {
            byte[] content = Base64.getDecoder().decode(data);
            byte[] iv = KEY.substring(0, 12).getBytes(StandardCharsets.UTF_8);
            GCMParameterSpec params = new GCMParameterSpec(128, iv);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, params);
            byte[] decryptData = cipher.doFinal(content);
            return new String(decryptData, StandardCharsets.UTF_8);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                 | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
        //test
//        String content = "GBsq7sA2AxvAl8nfyh57tVP8dbu/RNQR";
//        String encryptStr = encrypt(content);
//        System.out.println("原始内容：" + content);
//        System.out.println("加密内容：" + encryptStr);
        System.out.println("解密内容：" + decrypt("oShyUf7ochbpixhHxQJwu4gDhPY9dcgc0w=="));
        System.out.println("加密内容: "+encrypt("ld612522"));
    }
}
