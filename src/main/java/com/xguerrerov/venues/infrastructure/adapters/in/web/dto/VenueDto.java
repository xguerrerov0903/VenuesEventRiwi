package com.xguerrerov.venues.infrastructure.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueDto {

    private Long id;

    @NotBlank(message = "Venue name cannot be empty")
    @Size(min = 3, max = 100, message = "The venue name need at least 3 characters and a maximum of 100 characters")
    private String name;

    @NotBlank(message = "Venue city cannot be empty")
    @Size(min = 3, max = 100, message = "The venue city need at least 3 characters and a maximum of 100 characters")
    private String city;
}
