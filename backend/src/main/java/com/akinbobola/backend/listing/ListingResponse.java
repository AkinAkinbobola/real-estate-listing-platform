package com.akinbobola.backend.listing;

import com.akinbobola.backend.address.AddressResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ListingResponse {
    private Integer id;
    private String title;
    private String description;
    private BigDecimal price;
    private Integer numberOfBedrooms;
    private Integer numberOfBathrooms;
    private BigDecimal size;
    private SizeUnit sizeUnit;
    private ListingStatus status;
    private AddressResponse address;
    private LocalDateTime dateListed;
    private String createdBy;
}
