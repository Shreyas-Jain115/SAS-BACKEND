package com.shreyas.SAS_Admin_Service;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class SasAdminServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SasAdminServiceApplication.class, args);
	}

}
