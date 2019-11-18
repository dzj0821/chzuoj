package cn.edu.chzu.chzuoj.util;

/**
 * 转换工具类
 * @author dzj0821
 *
 */
public class ConvertUtil {
	/**
	 * 
	 * @param bytes
	 * @param upperCase
	 * @return
	 */
	public static String bytesToHexString(byte[] bytes, boolean upperCase) {
		StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() < 2) {
            	stringBuilder.append(0);
            }
            if (upperCase) {
            	hex.toUpperCase();
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
	}
}
