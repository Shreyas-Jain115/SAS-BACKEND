package com.shreyas.SAS_Eureka_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SasEurekaServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(SasEurekaServiceApplication.class, args);
	}

}
