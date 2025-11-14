package com.xguerrerov.venues.Model;


public interface Event {
    Long getId();
    String getName();
    java.time.LocalDate getDate();
    String getDescription();
    Long getVenueId();
}