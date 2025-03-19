package com.akinbobola.backend.search;

import com.akinbobola.backend.common.PageResponse;
import com.akinbobola.backend.listing.Listing;
import com.akinbobola.backend.listing.ListingMapper;
import com.akinbobola.backend.listing.ListingRepository;
import com.akinbobola.backend.listing.ListingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;

    public PageResponse <ListingResponse> getListings (
            Double minPrice,
            Double maxPrice,
            String city,
            String state,
            Integer numberOfBedrooms,
            Integer numberOfBathrooms,
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection
    ) {

        Specification <Listing> specification = Specification.where(null);

        if (minPrice != null) {
            specification = specification.or(((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice)));
        }

        if (maxPrice != null) {
            specification = specification.or(((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice)));
        }

        if (numberOfBedrooms != null) {
            specification = specification.or(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("numberOfBedrooms"), numberOfBedrooms)));
        }

        if (numberOfBathrooms != null) {
            specification = specification.or(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("numberOfBathrooms"), numberOfBathrooms)));
        }

        if (city != null) {
            specification = specification.or(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("address")
                    .get("city"), city)));
        }

        if (state != null) {
            specification = specification.or(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("address")
                    .get("state"), state)));
        }

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);

        Pageable pageRequest = PageRequest.of(page, size, sort);

        Page <Listing> listings = listingRepository.findAll(specification, pageRequest);

        List <ListingResponse> listingResponses = listings.stream()
                .map(listingMapper::toListingResponse)
                .toList();

        return new PageResponse <>(
                listingResponses,
                listings.getNumber(),
                listings.getTotalPages(),
                listings.getTotalElements(),
                listings.getTotalPages(),
                listings.isFirst(),
                listings.isLast()
        );
    }
}
