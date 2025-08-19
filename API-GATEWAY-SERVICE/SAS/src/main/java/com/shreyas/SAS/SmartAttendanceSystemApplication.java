package com.shreyas.SAS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SmartAttendanceSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmartAttendanceSystemApplication.class, args);
	}
}
