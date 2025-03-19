package com.akinbobola.backend.listing;

import com.akinbobola.backend.common.PageResponse;
import com.akinbobola.backend.viewing.ViewingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/{listing-id}/viewings")
    public ResponseEntity <Integer> saveViewing (
            @PathVariable(name = "listing-id") Integer listingId,
            @Valid @RequestBody ViewingRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.saveViewing(listingId, request, connectedUser));
    }

    @GetMapping("/{listing-id}/viewings")
    public ResponseEntity <PageResponse <ViewingResponse>> getViewings (
            @PathVariable(name = "listing-id") Integer listingId,
            @RequestParam(defaultValue = "0", required = false, name = "page") Integer page,
            @RequestParam(defaultValue = "10", required = false, name = "size") Integer size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.getViewings(listingId, page, size, connectedUser));
    }

    @DeleteMapping("/{listing-id}/viewings/{viewing-id}")
    public ResponseEntity <Integer> deleteViewing (
            @PathVariable(name = "listing-id") Integer listingId,
            @PathVariable(name = "viewing-id") Integer viewingId,
            Authentication connectedUser
    ) {
        service.deleteViewing(listingId, viewingId, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{listing-id}/images", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity <?> saveImages (
            @PathVariable(name = "listing-id") Integer listingId,
            @RequestParam("images") MultipartFile[] images,
            Authentication connectedUser
    ) {
        service.saveImages(listingId, images, connectedUser);
        return ResponseEntity.ok().build();
    }
}
