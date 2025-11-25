package com.xguerrerov.venues.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueDto {

    private Long id;

    @NotBlank(message = "Venue name cannot be empty")
    @Size(min = 3, max = 100, message = "Venue name must have between 3 and 100 characters")
    private String name;

    @NotBlank(message = "Venue city cannot be empty")
    @Size(min = 3, max = 100, message = "City must have between 3 and 100 characters")
    private String city;
}
