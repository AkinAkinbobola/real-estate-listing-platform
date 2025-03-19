package com.akinbobola.backend.listing;

import com.akinbobola.backend.address.AddressResponse;
import com.akinbobola.backend.listingImage.ListingImageMapper;
import com.akinbobola.backend.viewing.ViewingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListingMapper {
    private final ViewingMapper viewingMapper;
    private final ListingImageMapper listingImageMapper;

    public ListingResponse toListingResponse (Listing listing) {
        return ListingResponse.builder()
                .id(listing.getId())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .price(listing.getPrice())
                .numberOfBedrooms(listing.getNumberOfBedrooms())
                .numberOfBathrooms(listing.getNumberOfBathrooms())
                .size(listing.getSize())
                .sizeUnit(listing.getSizeUnit())
                .status(listing.getStatus())
                .address(
                        AddressResponse.builder()
                                .street(listing.getAddress().getStreet())
                                .city(listing.getAddress().getCity())
                                .state(listing.getAddress().getState())
                                .postalCode(listing.getAddress().getPostalCode())
                                .build()
                )
                .viewings(
                        Optional.ofNullable(listing.getViewings())
                                .map(viewingMapper::toViewingResponseList)
                                .orElse(Collections.emptyList())
                )
                .images(
                        Optional.ofNullable(listing.getListingImages())
                                .map(listingImageMapper::toListingImageResponseList)
                                .orElse(Collections.emptyList())
                )
                .dateListed(listing.getCreatedDate())
                .createdBy(listing.getAgent().getEmail())
                .build();
    }

    public Listing toListing (ListingRequest request) {
        return Listing.builder()
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .numberOfBedrooms(request.numberOfBedrooms())
                .numberOfBathrooms(request.numberOfBathrooms())
                .size(request.size())
                .status(request.listingStatus())
                .sizeUnit(request.sizeUnit())
                .build();
    }
}
