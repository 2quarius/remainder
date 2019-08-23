package com.example.trail.Utility.Utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESUtils {
    //算法名称
    private static final String KEY_ALGORITHM = "DES";
    //算法名称/加密模式/填充模式
    //DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    private static final String CIPHER_ALGORITHM = "DES/ECB/NOPadding";
    //key
    private static final String KEY = "jaccount";
    /**
     * 生成密钥key对象
     */
    private static SecretKey keyGenerator(String keyStr) throws Exception {
        byte[] input = HexString2Bytes(keyStr);
        DESKeySpec desKeySpec = new DESKeySpec(input);
        //创建一个密钥工厂 然后用它转换DESKeySpec
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generateSecret(desKeySpec);
    }

    /**
     * 加密数据
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String data) throws Exception {
        Key desKey = keyGenerator(KEY);
        //实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecureRandom random = new SecureRandom();
        //初始化Cipher对象，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, desKey, random);
        byte[] results = cipher.doFinal(data.getBytes());
        // 该部分是为了与加解密在线测试网站（http://tripledes.online-domain-tools.com/）的十六进制结果进行核对
//        for (int i = 0; i < results.length; i++) {
//            System.out.print(results[i] + " ");
//        }
//        System.out.println();
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
        return Base64.getEncoder().encodeToString(results);
    }

    /**
     * 解密数据
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decrypt(String data) throws Exception {
        Key desKey = keyGenerator(KEY);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化Cipher对象，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, desKey);
        //执行解密操作
        return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
    }
    // 从十六进制字符串到字节数组转换
    private static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a') return (c - 'a' + 10) & 0x0f;
        if (c >= 'A') return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }
}
