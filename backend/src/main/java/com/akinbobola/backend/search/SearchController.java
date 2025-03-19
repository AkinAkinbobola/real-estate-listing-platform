package com.akinbobola.backend.search;

import com.akinbobola.backend.common.PageResponse;
import com.akinbobola.backend.listing.ListingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity <PageResponse <ListingResponse>> search (
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) Integer numberOfBedrooms,
            @RequestParam(required = false) Integer numberOfBathrooms,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "createdDate") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String sortDirection
    ) {
        return ResponseEntity.ok(
                searchService.getListings(
                        minPrice,
                        maxPrice,
                        city,
                        state,
                        numberOfBedrooms,
                        numberOfBathrooms,
                        page,
                        size,
                        sortBy,
                        sortDirection
                ));
    }
}
