package com.example.bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BffApplication {

	public static void main(String[] args) {
		var procs = Runtime.getRuntime().availableProcessors();
		System.out.println("BffApplication: Active processors: " + procs); 
				
		SpringApplication.run(BffApplication.class, args);
	}

}
