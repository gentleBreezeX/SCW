package com.gentle.scw.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gentle.scw.common.templates.OSSTemplate;

@Configuration
public class AppConfig {

	//密码加密
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//阿里OSS上传文件
	@ConfigurationProperties(prefix = "oss")
	@Bean
	public OSSTemplate getOssTemplate() {
		return new OSSTemplate();
	}
	
	
}
