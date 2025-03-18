package com.akinbobola.backend.listing;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ListingRequest(

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than 0")
        BigDecimal price,

        @NotNull(message = "Number of bathrooms is required")
        @Positive(message = "Number of bathrooms must be greater than 0")
        Integer numberOfBathrooms,

        @NotNull(message = "Number of bedrooms is required")
        @Positive(message = "Number of bedrooms must be greater than 0")
        Integer numberOfBedrooms,

        @NotNull(message = "Size is required")
        @Positive(message = "Size must be greater than 0")
        BigDecimal size,

        @NotNull(message = "Size unit is required")
        SizeUnit sizeUnit,

        @NotNull(message = "Status is required")
        ListingStatus listingStatus,

        @NotBlank(message = "Street is required")
        String street,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "State is required")
        String state,

        String postalCode
) {
}
