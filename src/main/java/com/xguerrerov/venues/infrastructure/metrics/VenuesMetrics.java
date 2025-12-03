package com.xguerrerov.venues.infrastructure.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class VenuesMetrics {

    private final Counter createdVenuesCounter;
    private final Counter updatedVenuesCounter;
    private final Counter deletedVenuesCounter;

    public VenuesMetrics(MeterRegistry registry) {

        this.createdVenuesCounter = Counter.builder("venues_created_total")
                .description("Total de venues creados")
                .register(registry);

        this.updatedVenuesCounter = Counter.builder("venues_updated_total")
                .description("Total de venues actualizados")
                .register(registry);

        this.deletedVenuesCounter = Counter.builder("venues_deleted_total")
                .description("Total de venues eliminados")
                .register(registry);
    }

    public void incrementCreated() {
        createdVenuesCounter.increment();
    }

    public void incrementUpdated() {
        updatedVenuesCounter.increment();
    }

    public void incrementDeleted() {
        deletedVenuesCounter.increment();
    }
}

