package com.one.security;

public class Encrypt {

	

	static int key1=0;
	static int key2=0;
	public static String passwordEncyption(String pass) {

		String password = "";
		key1 = pass.length() % 2 + 3;
		key2 = pass.length() / 2;

		for (int i = 0; i < pass.length(); i++) {
			if ((pass.charAt(i) >= 'a' && pass.charAt(i) <= 'z') || (pass.charAt(i) >= 'A' && pass.charAt(i) <= 'Z')) {

				password = password + ((char) (pass.charAt(i) + key1));

			} else
				password = password + ((char) (pass.charAt(i) + key2));
		}
		return password;
	}

}
