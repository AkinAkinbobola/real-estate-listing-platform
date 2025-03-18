package com.akinbobola.backend.listing;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("listings")
public class ListingController {

    private final ListingService service;

    @PostMapping
    public ResponseEntity <Integer> saveListing (
            @Valid @RequestBody ListingRequest request
    ) {
        return ResponseEntity.ok(service.saveListing(request));
    }

}
