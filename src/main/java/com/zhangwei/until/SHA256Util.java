package com.zhangwei.until;

import java.security.MessageDigest;

public class SHA256Util {

    /**
     * 　　* 利用java原生的摘要实现SHA256加密
     * 　　* @param str 加密后的报文
     * 　　* @return
     */
    public static String getSHA256StrJava(String str, String charsetName) {
        MessageDigest messageDigest;
        String encodeStr = "";

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(charsetName));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encodeStr;
    }

    /**
     * 　　* 将byte转为16进制
     * 　　* @param bytes
     * 　　* @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
