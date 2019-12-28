package com.atguigu.security.config;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.atguigu.security.utils.MD5Util;

public class AppMd5PasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		
		return MD5Util.digest(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String encode = encode(rawPassword);
		
		return encode.equals(encodedPassword);
	}

}
