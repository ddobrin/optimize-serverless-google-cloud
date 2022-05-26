package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.springframework.nativex.hint.TypeHint;
import org.springframework.nativex.hint.ResourceHint;

@SpringBootApplication
@TypeHint(types = PostgreSQL95Dialect.class, typeNames = "org.hibernate.dialect.PostgreSQLDialect")
@TypeHint(typeNames = {"org.flywaydb.core.internal.logging.log4j2.Log4j2LogCreator"})
@ResourceHint(patterns = "org/flywaydb/core/internal/version.txt") 
public class QuotesApplication {

  public static void main(String[] args) {
    var procs = Runtime.getRuntime().availableProcessors();
    System.out.println("QuotesApplication: Active processors: " + procs);    

    // SpringApplication app = new SpringApplication(QuotesApplication.class);
    // app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
    SpringApplication.run(QuotesApplication.class, args);
  }
}
