package com.xguerrerov.venues.Model;
import com.xguerrerov.venues.Entity.VenueEntity;

public interface Event {
    Long getId();
    String getName();
    java.time.LocalDate getDateBegin();
    String getCategory();
    String getDescription();
    VenueEntity getVenue();

}