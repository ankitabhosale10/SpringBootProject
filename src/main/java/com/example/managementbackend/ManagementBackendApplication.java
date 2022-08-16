package com.example.managementbackend;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableEncryptableProperties
public class ManagementBackendApplication {
	public static void main(String[] args) {

		SpringApplication.run(ManagementBackendApplication.class, args);

	}
}
