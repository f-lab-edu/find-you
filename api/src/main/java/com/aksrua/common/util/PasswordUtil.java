package com.aksrua.common.util;


import org.apache.commons.codec.digest.DigestUtils;

public class PasswordUtil {
	public static String hash(String rawPassword) {
		return DigestUtils.sha256Hex(rawPassword);
	}

	public static boolean matches(String rawPassword, String password) {
		return hash(rawPassword).equals(password);
	}
}
