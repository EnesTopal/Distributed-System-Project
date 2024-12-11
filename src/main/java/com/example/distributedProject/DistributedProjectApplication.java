package com.example.distributedProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DistributedProjectApplication {

	public static void main(String[] args) {

		System.out.println("Project working");
		SpringApplication.run(DistributedProjectApplication.class, args);
	}

}
