package com.xguerrerov.venues.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueDTO {
    private Long id;

    @NotBlank(message = "Venue name cannot be empty")
    private String name;


}