package com.akinbobola.backend.listing;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListingService {
    public Integer saveListing (ListingRequest request, Authentication connectedUser) {
        return null;
    }
}
