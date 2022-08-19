package com.example.faulty;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

public class FaultyService {

    private final CircuitBreaker circuitBreaker = CircuitBreakerRegistry.ofDefaults().circuitBreaker("faultyService");

    //TODO: add logic to simulate a backing service that is faulty in the following ways:
    // - Leaking resources, gradually slowing response times and triggering a HALF_OPEN state
    // - Unreliable health indicator, requiring retries to maintain an accurate error rate
    // - Limited concurrency, slowing down significantly over X concurrent requests, requiring a bulkhead
    public String getDataFromFaultyBackingService() {
        return circuitBreaker.decorateSupplier(() -> "Working as intended").get();
    }
}
