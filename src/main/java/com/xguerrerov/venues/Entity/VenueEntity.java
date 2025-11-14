package com.xguerrerov.venues.Entity;
import com.xguerrerov.venues.Model.Venue;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueEntity implements Venue {

    private Long id;
    private String name;

}