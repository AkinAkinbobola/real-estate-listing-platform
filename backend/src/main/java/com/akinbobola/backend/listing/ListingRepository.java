package com.akinbobola.backend.listing;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository <Listing, Integer> {
}