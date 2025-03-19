package com.akinbobola.backend.listingImage;


import com.akinbobola.backend.common.BaseEntity;
import com.akinbobola.backend.listing.Listing;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ListingImage extends BaseEntity {

    private boolean isPrimary;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;
}
