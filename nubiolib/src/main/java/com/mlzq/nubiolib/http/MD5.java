package com.mlzq.nubiolib.http;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	private MD5() {}
	
	public final static String getMessageDigest(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
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
			return null;
		}
	}
	
	
	public static String[] getUrlParam(String[] keys) {

		for (int i = 0; i < keys.length - 1; i++) {
			for (int j = 0; j < keys.length - i - 1; j++) {
				String pre = keys[j];
				String next = keys[j + 1];
				if (isMoreThan(pre, next)) {
					String temp = pre;
					keys[j] = next;
					keys[j + 1] = temp;
				}
			}
		}
		return keys;
	}

	private static boolean isMoreThan(String pre, String next) {
		if (null == pre || null == next || "".equals(pre) || "".equals(next)) {

			return false;
		}

		char[] c_pre = pre.toCharArray();
		char[] c_next = next.toCharArray();

		int minSize = Math.min(c_pre.length, c_next.length);

		for (int i = 0; i < minSize; i++) {
			if ((int) c_pre[i] > (int) c_next[i]) {
				return true;
			} else if ((int) c_pre[i] < (int) c_next[i]) {
				return false;
			}
		}
		if (c_pre.length > c_next.length) {
			return true;
		}
		return false;
	}


	public static String encryptMD5(String securityStr) {

		byte[] data = securityStr.getBytes();
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(data);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] resultBytes = md5.digest();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < resultBytes.length; i++) {
			if (Integer.toHexString(0xFF & resultBytes[i]).length() == 1) {
				builder.append("0").append(
						Integer.toHexString(0xFF & resultBytes[i]));
			} else {
				builder.append(Integer.toHexString(0xFF & resultBytes[i]));
			}
		}
		return builder.toString();
	}
	
}
