package com.gentle.scw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ScwRegistryCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScwRegistryCenterApplication.class, args);
	}

}
