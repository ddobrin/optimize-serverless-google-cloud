package com.example.reference.actuator;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id="cloudrunhealth")
public class CloudRunHealthCheck {
    // logger
    private static final Log logger = LogFactory.getLog(StartupCheck.class);

    @ReadOperation
    public CustomData customEndpoint() {
        Map<String, Object> details = new LinkedHashMap<>();        
        logger.info("Reference Application: Custom Health Check - passed");

        details.put("CloudRunHealthCheckEndpoint", "Reference Health Check: passed");

        CustomData data = new CustomData();
        data.setData(details);

        return data;
    }
}