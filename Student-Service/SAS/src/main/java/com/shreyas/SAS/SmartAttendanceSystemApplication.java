package com.shreyas.SAS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class SmartAttendanceSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmartAttendanceSystemApplication.class, args);
	}
}
