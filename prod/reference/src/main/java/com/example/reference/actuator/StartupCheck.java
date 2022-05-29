package com.example.reference.actuator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Endpoint(id="startup")
public class StartupCheck {
    // logger
    private static final Log logger = LogFactory.getLog(StartupCheck.class);

    @ReadOperation
    public CustomData customEndpoint() {
        Map<String, Object> details = new LinkedHashMap<>();
        logger.info("Reference Startup Endpoint: Application is ready to serve traffic !");

        details.put("StartupEndpoint", "Reference Startup Endpoint: Application is ready to serve traffic");

        CustomData data = new CustomData();
        data.setData(details);
        return data;
    }
}