package com.gentle.scw.webui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import feign.Retryer;
@Configuration
public class AppWebConfig implements WebMvcConfigurer{

	@Bean
	public Retryer feignRetryer() {
		return Retryer.NEVER_RETRY;
	}
}
