package com.blog.security;


import java.security.MessageDigest;

/**
 * MD5工具类
 *
 * @author lijinzhou
 * @since 2017/12/20 19:16
 */
public class MD5Utils {

    private static String confuse = "!#%&(_";  //密码混肴

    /**
     * MD5加密
     *
     * @author lijinzhou
     * @since 2017/12/20 19:17
     */
    public final static String md5(String s) {
        s = s + confuse;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
