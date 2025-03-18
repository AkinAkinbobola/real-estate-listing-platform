package com.akinbobola.backend.listing;

import com.akinbobola.backend.address.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingService {

    private final ListingRepository listingRepository;

    public Integer saveListing (ListingRequest request) {
        Address address = Address.builder()
                .street(request.street())
                .city(request.city())
                .state(request.state())
                .postalCode(request.postalCode())
                .build();

        Listing listing = Listing.builder()
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .numberOfBedrooms(request.numberOfBedrooms())
                .numberOfBathrooms(request.numberOfBathrooms())
                .size(request.size())
                .status(request.listingStatus())
                .sizeUnit(request.sizeUnit())
                .address(address)
                .build();

        return listingRepository.save(listing).getId();
    }
}
