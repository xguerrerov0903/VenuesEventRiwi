package com.xguerrerov.venues.Entity;
import com.xguerrerov.venues.Model.Event;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity implements Event {

    private Long id;
    private String name;
    private LocalDate date;
    private String description;
    private Long venueId;

}