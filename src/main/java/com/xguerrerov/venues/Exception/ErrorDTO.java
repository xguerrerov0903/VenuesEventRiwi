package com.xguerrerov.venues.Exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String error;
    private String message;
    private String path;
    private long timestamp;
}
