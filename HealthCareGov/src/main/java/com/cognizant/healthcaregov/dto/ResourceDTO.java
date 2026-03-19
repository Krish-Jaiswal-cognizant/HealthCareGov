package com.cognizant.healthcaregov.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDTO {

    @NotNull(message = "Resource ID is required")
    private Integer resourceID;

    @NotNull(message = "Hospital ID is required")
    private Integer hospitalID;

    @NotBlank(message = "Hospital name is required")
    private String hospitalName;

    @NotBlank(message = "Resource type is required")
    private String type;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 10000, message = "Quantity cannot exceed 10,000")
    private Integer quantity;

    @NotBlank(message = "Status is required in text")
    private String status;
}