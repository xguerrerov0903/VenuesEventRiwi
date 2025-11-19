package com.xguerrerov.venues.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
public class VenueDTO {

    private Long id;

    @NotBlank(message = "Venue name cannot be empty")
    @Size(min = 3, max = 100, message = "The venue name need at least 3 characters and a maximum of 100 characters")
    private String name;

    @NotBlank(message = "Venue city cannot be empty")
    @Size(min = 3, max = 100, message = "The venue city need at least 3 characters and a maximum of 100 characters")
    private String city;

}