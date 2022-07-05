package com.example.faulty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
public class FaultyApplication {
    // logger
    private static final Log logger = LogFactory.getLog(FaultyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FaultyApplication.class, args);
	}

	@PostConstruct
    public void init() {
        logger.info("FaultyApplication: Post Construct Initializer"); 
    }

	@PreDestroy
	public void shutDown(){
		logger.info(FaultyApplication.class.getSimpleName() + ": received SIGTERM ==> Shutting down resources !");
	}

}
