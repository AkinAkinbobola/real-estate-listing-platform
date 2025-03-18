package com.akinbobola.backend.listing;

import com.akinbobola.backend.address.Address;
import com.akinbobola.backend.common.PageResponse;
import com.akinbobola.backend.user.User;
import com.akinbobola.backend.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingService {

    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;
    private final UserRepository userRepository;

    public Integer saveListing (ListingRequest request, Authentication connectedUser) {
        User authenticatedUser = (User) connectedUser.getPrincipal();

        User user = userRepository.findByEmail(authenticatedUser.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Address address = Address.builder()
                .street(request.street())
                .city(request.city())
                .state(request.state())
                .postalCode(request.postalCode())
                .build();

        Listing listing = listingMapper.toListing(request);
        listing.setAddress(address);
        listing.setAgent(user);

        return listingRepository.save(listing).getId();
    }

    public PageResponse <ListingResponse> getListings (
            Integer page,
            Integer size,
            Authentication connectedUser
    ) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page <Listing> allListings = listingRepository.findByAgentId(user.getId(), pageRequest);

        List <ListingResponse> listingResponses = allListings.stream()
                .map(listingMapper::toListingResponse)
                .toList();

        return new PageResponse <>(
                listingResponses,
                allListings.getNumber(),
                allListings.getSize(),
                allListings.getTotalElements(),
                allListings.getTotalPages(),
                allListings.isFirst(),
                allListings.isLast()
        );
    }
}
