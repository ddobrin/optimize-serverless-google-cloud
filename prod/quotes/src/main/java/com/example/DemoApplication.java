package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.hibernate.dialect.PostgreSQL95Dialect;
import org.springframework.nativex.hint.TypeHint;
import org.springframework.nativex.hint.ResourceHint;

@SpringBootApplication
@TypeHint(types = PostgreSQL95Dialect.class, typeNames = "org.hibernate.dialect.PostgreSQLDialect")
@TypeHint(typeNames = {"org.flywaydb.core.internal.logging.log4j2.Log4j2LogCreator"})
@ResourceHint(patterns = "org/flywaydb/core/internal/version.txt") 
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
