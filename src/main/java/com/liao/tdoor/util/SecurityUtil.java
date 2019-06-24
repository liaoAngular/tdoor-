package com.liao.tdoor.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 廖某某
 * 加密，解密
 */
public class SecurityUtil {
    public static String md5(String password) throws NoSuchAlgorithmException {
        MessageDigest md=MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        return new BigInteger(1,md.digest()).toString(16);
    }
    public static String md5(String username,String password) throws NoSuchAlgorithmException{
        MessageDigest md=MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        md.update(username.getBytes());
        return new BigInteger(1,md.digest()).toString(16);
    }
}
