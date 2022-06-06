package com.example.quotes.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Endpoint(id="startup")
public class StartupCheck {
    @ReadOperation
    public CustomData customEndpoint() {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("StartupEndpoint", "Everything looks good at the startup endpoint");

        CustomData data = new CustomData();
        data.setData(details);
        return data;
    }
}