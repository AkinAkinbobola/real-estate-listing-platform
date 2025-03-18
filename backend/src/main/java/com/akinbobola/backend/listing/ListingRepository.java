package com.akinbobola.backend.listing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ListingRepository extends JpaRepository <Listing, Integer> {

    @Query("""
            select listing
            from Listing listing
            where listing.user.id = :agentId
            """)
    Page<Listing> findByAgentId (Integer agentId, Pageable pageRequest);
}