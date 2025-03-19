package com.akinbobola.backend.listingImage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingImageMapper {
    public List <String> toListingImageResponseList (List <ListingImage> listingImages) {
        return listingImages.stream()
                .map(listingImage -> String.format(
                        "http://localhost:8080/api/v1/listings/%d/images/%d",
                        listingImage.getListing().getId(),
                        listingImage.getId()
                )).toList();
    }
}
