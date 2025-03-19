package com.akinbobola.backend.listing;


import com.akinbobola.backend.address.Address;
import com.akinbobola.backend.common.BaseEntity;
import com.akinbobola.backend.user.User;
import com.akinbobola.backend.viewing.Viewing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Listing extends BaseEntity {

    private String title;

    private String description;

    private BigDecimal price;

    private Integer numberOfBedrooms;

    private Integer numberOfBathrooms;

    @Enumerated(EnumType.STRING)
    private ListingStatus status;

    private BigDecimal size;

    @Enumerated(EnumType.STRING)
    private SizeUnit sizeUnit;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private User agent;

    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL)
    private List <Viewing> viewings;

    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL)
    private List<ListingImage> listingImages;
}
