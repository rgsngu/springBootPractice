package com.example.demorg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
public class DemorgApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemorgApplication.class, args);
	}

}
