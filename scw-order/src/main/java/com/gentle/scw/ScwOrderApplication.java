package com.gentle.scw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@MapperScan(basePackages = "com.gentle.scw.order.mapper")
@SpringBootApplication
public class ScwOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScwOrderApplication.class, args);
	}

}
