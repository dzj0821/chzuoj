package cn.edu.chzu.chzuoj.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author dzj0821
 *
 */
public class MathUtil {
	public static String md5(String string) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return new BigInteger(1, digest.digest()).toString(16);
	}

	public static byte[] sha1(String string) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		digest.update(string.getBytes());
		return digest.digest();
	}
}
