package com.xguerrerov.venues.infrastructure.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class EventsMetrics {
    private final Counter createdEventsCounter;
    private final Counter updatedEventsCounter;
    private final Counter deletedEventsCounter;

    public EventsMetrics(MeterRegistry registry) {
        this.createdEventsCounter = Counter.builder("events_created_total")
                .description("Total de eventos creados")
                .register(registry);

        this.updatedEventsCounter = Counter.builder("events_updated_total")
                .description("Total de eventos actualizados")
                .register(registry);

        this.deletedEventsCounter = Counter.builder("events_deleted_total")
                .description("Total de eventos eliminados")
                .register(registry);
    }

    public void incrementCreated() {
        createdEventsCounter.increment();
    }

    public void incrementUpdated() {
        updatedEventsCounter.increment();
    }

    public void incrementDeleted() {
        deletedEventsCounter.increment();
    }

}
