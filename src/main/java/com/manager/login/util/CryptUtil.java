package com.manager.login.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.SecureRandom;

/**
 * Created with IntelliJ IDEA.
 * User: hzl
 * Date: 12-12-7
 * Time: 上午9:03
 * To change this template use File | Settings | File Templates.
 */
public class CryptUtil {

	/**
	 * 根据key 加密src
	 * @param key
	 * @param src
	 * @return
	 * @throws Exception
	 */
    public static byte[] encrypt(String key,byte[] src)throws Exception {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes("UTF-8"));
            kgen.init(128,secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKey skey=new SecretKeySpec(enCodeFormat,"AES");

            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,skey);
            return cipher.doFinal(src);
        }catch (Exception e) {
            throw new Exception("加密时出现异常！",e);
        }

    }

    /*
     * 根据key解密src
     */
    public static byte[] decrypt(String key,byte[] src)throws Exception{
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes("UTF-8"));
            kgen.init(128,secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] deCodeFormat = secretKey.getEncoded();
            SecretKey secretkey=new SecretKeySpec(deCodeFormat,"AES");
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,secretkey);
            return cipher.doFinal(src);
        }catch (Exception e) {
            throw new Exception("解密时出现异常！",e);
        }
    }

/**
 *  二进制转换成16进制
 * @param b
 * @return
 */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    /**
     * １６进制转换为二进制
     * @param b
     * @return
     */
    public static byte[] hex2byte(byte[] b) throws Exception{
        if((b.length%2)!=0)
            throw new Exception("长度不是偶数");
        byte[] b2 = new byte[b.length/2];
        for (int n = 0; n < b.length; n+=2) {
            String item = new String(b,n,2);
            b2[n/2] = (byte)Integer.parseInt(item,16);
        }
        return b2;
    }

    /**
     * 字节进制转换为长整型
     */
    public static long byte2long(byte[] bytes, int offset) {
        long value = 0;
        for (int i = 7; i > -1; i--) {
            value |= (((long) bytes[offset++]) & 0xFF) << 8 * i;
        }
        return value;
    }

    /**
     * long 转换为byte
     * @param value
     * @param bytes
     * @param offset
     */
    public static void long2byte(long value, byte[] bytes, int offset) {
        for (int i = 7; i > -1; i--) {
            bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);
        }
    }

    /**
     * 获取mac
     * @param addr
     * @return
     * @throws Exception
     */
    public static String getMac(InetAddress addr)throws Exception{
        try{
         byte[] mac= NetworkInterface.getByInetAddress(addr).getHardwareAddress();
            StringBuffer sb=new StringBuffer();
            for(int i=0;i<mac.length;i++){
                 if(i!=0){
                     sb.append("-");
                 }
                //mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length()==1?0+s:s);
            }

            return sb.toString().toUpperCase();

        }catch(Exception e){
            throw new Exception(e);
        }

    }


}
