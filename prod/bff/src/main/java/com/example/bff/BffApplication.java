package com.example.bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SpringBootApplication
public class BffApplication {
    // logger
    private static final Log logger = LogFactory.getLog(BffApplication.class);

	public static void main(String[] args) {
		Runtime r = Runtime.getRuntime();
		logger.info("BffApplication: Active processors: " + r.availableProcessors()); 
		logger.info("BffApplication: Total memory: " + r.totalMemory()); 
		logger.info("BffApplication: Free memory: " + r.freeMemory()); 
		logger.info("BffApplication: Max memory: " + r.maxMemory()); 
				
		SpringApplication.run(BffApplication.class, args);
		logger.info("BffApplication: Running app");
	}

	@PostConstruct
    public void init() {
        logger.info("BffApplication: Post Construct Initializer"); 
    }

	@PreDestroy
	public void shutDown(){
		logger.info(BffApplication.class.getSimpleName() + ": received SIGTERM ==> Shutting down resources !");
	}
}
