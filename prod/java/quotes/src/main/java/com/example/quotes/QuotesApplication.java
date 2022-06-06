package com.example.quotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.springframework.nativex.hint.TypeHint;
import org.springframework.nativex.hint.ResourceHint;

@SpringBootApplication
@TypeHint(types = PostgreSQL95Dialect.class, typeNames = "org.hibernate.dialect.PostgreSQLDialect")
@TypeHint(typeNames = {"org.flywaydb.core.internal.logging.log4j2.Log4j2LogCreator"})
@ResourceHint(patterns = "org/flywaydb/core/internal/version.txt") 
public class QuotesApplication {

  public static void main(String[] args) {
    Runtime r = Runtime.getRuntime();
    System.out.println("QuotesApplication: Active processors: " + r.availableProcessors());    
		System.out.println("QuotesApplication: Total memory: " + r.totalMemory()); 
		System.out.println("QuotesApplication: Free memory: " + r.freeMemory()); 
		System.out.println("QuotesApplication: Max memory: " + r.maxMemory()); 


    SpringApplication.run(QuotesApplication.class, args);
  }
}
