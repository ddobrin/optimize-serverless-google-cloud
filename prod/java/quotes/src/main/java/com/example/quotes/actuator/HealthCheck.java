package com.example.quotes.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("customHealthCheck")
public class HealthCheck implements HealthIndicator {

    @Override
    public Health health() {
        int errorCode = check(); // perform some specific health check

        if (errorCode != 0) {
            return Health.down()
                    .withDetail("Custom Health Check Status - failed. Error Code", errorCode).build();
        }
        return Health.up().withDetail("Custom Health Check Status", "passed").build();
    }

    public int check() {
        // custom logic - check health
        return 0;
    }
}
