package com.example.reference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
public class ReferenceApplication {
    // logger
    private static final Log logger = LogFactory.getLog(ReferenceApplication.class);

	public static void main(String[] args) {
		Runtime r = Runtime.getRuntime();
		logger.info("ReferenceApplication: Active processors: " + r.availableProcessors()); 
		logger.info("ReferenceApplication: Total memory: " + r.totalMemory()); 
		logger.info("ReferenceApplication: Free memory: " + r.freeMemory()); 
		logger.info("ReferenceApplication: Max memory: " + r.maxMemory()); 


		SpringApplication.run(ReferenceApplication.class, args);
	}

	@PostConstruct
    public void init() {
        logger.info("ReferenceApplication: Post Construct Initializer"); 
    }

	@PreDestroy
	public void shutDown(){
		logger.info(ReferenceApplication.class.getSimpleName() + ": received SIGTERM ==> Shutting down resources !");
	}

}
