package com.akinbobola.backend.listing;

import java.math.BigDecimal;

public record ListingRequest(
        String title,
        String description,
        BigDecimal price,
        Integer numberOfBathrooms,
        Integer numberOfBedrooms,
        BigDecimal size,
        String sizeUnit,
        String status
) {
}
