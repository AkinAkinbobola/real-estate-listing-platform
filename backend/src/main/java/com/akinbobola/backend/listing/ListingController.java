package com.akinbobola.backend.listing;

import com.akinbobola.backend.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("listings")
public class ListingController {

    private final ListingService service;

    @PostMapping
    public ResponseEntity <Integer> saveListing (
            @Valid @RequestBody ListingRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.saveListing(request, connectedUser));
    }

    @GetMapping
    public ResponseEntity <PageResponse <ListingResponse>> getListings (
            @RequestParam(defaultValue = "0", required = false, name = "page") Integer page,
            @RequestParam(defaultValue = "10", required = false, name = "size") Integer size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.getListings(page, size, connectedUser));
    }

    @DeleteMapping("/{listing-id}")
    public ResponseEntity <Integer> deleteListing (
            @PathVariable(name = "listing-id") Integer listingId,
            Authentication connectedUser
    ) {
        service.deleteListing(listingId, connectedUser);
        return ResponseEntity.ok().build();
    }
}
