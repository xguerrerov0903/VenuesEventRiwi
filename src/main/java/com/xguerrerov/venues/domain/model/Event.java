package com.xguerrerov.venues.domain.model;

import java.time.LocalDate;

public class Event {

    private Long id;
    private String name;
    private LocalDate dateBegin;
    private String category;
    private String description;
    private Venue venue;

    public Event() {
    }

    public Event(Long id, String name, LocalDate dateBegin, String category, String description, Venue venue) {
        this.id = id;
        this.name = name;
        this.dateBegin = dateBegin;
        this.category = category;
        this.description = description;
        this.venue = venue;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(LocalDate dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}