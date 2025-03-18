package com.akinbobola.backend.address;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {
    private String street;
    private String city;
    private String state;
    private String postalCode;
}
