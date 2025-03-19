package com.akinbobola.backend.listing;

import com.akinbobola.backend.address.Address;
import com.akinbobola.backend.address.AddressRepository;
import com.akinbobola.backend.common.PageResponse;
import com.akinbobola.backend.exceptions.OperationNotPermittedException;
import com.akinbobola.backend.file.FileReaderService;
import com.akinbobola.backend.file.FileStorageService;
import com.akinbobola.backend.listingImage.ListingImage;
import com.akinbobola.backend.listingImage.ListingImageRepository;
import com.akinbobola.backend.user.User;
import com.akinbobola.backend.user.UserRepository;
import com.akinbobola.backend.viewing.Viewing;
import com.akinbobola.backend.viewing.ViewingMapper;
import com.akinbobola.backend.viewing.ViewingRepository;
import com.akinbobola.backend.viewing.ViewingResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListingService {

    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ViewingMapper viewingMapper;
    private final ViewingRepository viewingRepository;
    private final FileStorageService fileStorageService;
    private final ListingImageRepository listingImageRepository;
    private final FileReaderService fileReaderService;

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

        Address savedAddress = addressRepository.save(address);

        Listing listing = listingMapper.toListing(request);
        listing.setAddress(savedAddress);
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

    public void deleteListing (Integer listingId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing id " + listingId + " not found"));

        if (!Objects.equals(listing.getAgent().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not permitted to delete this listing");
        }

        listingRepository.delete(listing);
    }

    public Integer saveViewing (
            Integer listingId,
            @Valid ViewingRequest request,
            Authentication connectedUser
    ) {
        User user = (User) connectedUser.getPrincipal();

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing id " + listingId + " not found"));

        if (!Objects.equals(listing.getAgent().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not permitted to create a viewing for this listing");
        }

        boolean viewingTimesOverlap = viewingRepository.viewingTimesOverlap(
                listingId,
                user.getId(),
                request.date(),
                request.startTime(),
                request.endTime()
        );

        if (viewingTimesOverlap) {
            throw new OperationNotPermittedException("A viewing already exists for this time slot.");
        }

        Viewing viewing = viewingMapper.toViewing(request);
        viewing.setAgent(user);
        viewing.setListing(listing);

        return viewingRepository.save(viewing).getId();
    }

    public PageResponse <ViewingResponse> getViewings (
            Integer listingId,
            Integer page,
            Integer size,
            Authentication connectedUser
    ) {
        User user = (User) connectedUser.getPrincipal();

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing id " + listingId + " not found"));

        if (!Objects.equals(listing.getAgent().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not permitted to view viewings for this listing");
        }

        Pageable pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page <Viewing> viewings = viewingRepository.findByListingId(listingId, user.getId(), pageRequest);

        List <ViewingResponse> viewingResponses = viewings.stream()
                .map(viewingMapper::toViewingResponse)
                .toList();

        return new PageResponse <>(
                viewingResponses,
                viewings.getNumber(),
                viewings.getSize(),
                viewings.getTotalElements(),
                viewings.getTotalPages(),
                viewings.isFirst(),
                viewings.isLast()
        );
    }

    public void deleteViewing (
            Integer listingId,
            Integer viewingId,
            Authentication connectedUser
    ) {
        User user = (User) connectedUser.getPrincipal();

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing id " + listingId + " not found"));

        if (!Objects.equals(listing.getAgent().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not permitted to delete viewings for this listing");
        }

        Viewing viewing = viewingRepository.findById(viewingId)
                .orElseThrow(() -> new EntityNotFoundException("Viewing id " + viewingId + " not found"));

        boolean existsByListingIdAndViewingId = viewingRepository.existsByListingIdAndViewingId(listingId, viewingId);

        if (!existsByListingIdAndViewingId) {
            throw new EntityNotFoundException("Viewing id " + viewingId + " not found for listing id " + listingId);
        }

        viewingRepository.delete(viewing);
    }

    public void saveImages (
            Integer listingId,
            MultipartFile[] images,
            Authentication connectedUser
    ) {
        User user = (User) connectedUser.getPrincipal();

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing id " + listingId + " not found"));

        if (!Objects.equals(listing.getAgent().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not permitted to add images to this listing");
        }

        List <String> imageUrls = fileStorageService.saveImages(listingId, images, user.getId());

        imageUrls.forEach(imageUrl -> {
            ListingImage listingImage = ListingImage.builder()
                    .imageUrl(imageUrl)
                    .isPrimary(false)
                    .listing(listing)
                    .build();

            listingImageRepository.save(listingImage);
        });
    }

    public byte[] getImage (Integer listingId, Integer imageId, Authentication connectedUser) throws IOException {
        User user = (User) connectedUser.getPrincipal();

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("Listing id " + listingId + " not found"));

        if (!Objects.equals(listing.getAgent().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not permitted to view images for this listing");
        }

        ListingImage listingImage = listingImageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image id " + imageId + " not found"));

        return fileReaderService.readFile(listingImage.getImageUrl());
    }
}
