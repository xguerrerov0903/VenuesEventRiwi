package com.xguerrerov.venues.Model;


public interface Event {
    Long getId();
    String getName();
    java.time.LocalDate getDateBegin();
    String getCategory();
    String getDescription();
    Long getVenueId();
}