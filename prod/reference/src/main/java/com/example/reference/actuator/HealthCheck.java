package com.example.reference.actuator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("customHealthCheck")
public class HealthCheck implements HealthIndicator {
    // logger
    private static final Log logger = LogFactory.getLog(HealthCheck.class);

    @Override
    public Health health() {
        int errorCode = check(); // perform some specific health check

        if (errorCode != 0) {
            logger.error("Reference Application: failed health check with error code " + errorCode);
            return Health.down()
                    .withDetail("Custom Health Check Status - failed. Error Code", errorCode).build();
        }

        logger.info("Reference Application: Custom Health Check - passed");
        return Health.up().withDetail("Reference Application: Custom Health Check Status ", "passed").build();
    }

    public int check() {
        // custom logic - check health
        return 0;
    }
}
