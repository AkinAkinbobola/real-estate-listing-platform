package com.akinbobola.backend.floorPlan;

import com.akinbobola.backend.common.BaseEntity;
import com.akinbobola.backend.listing.Listing;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FloorPlan extends BaseEntity {

    private String planUrl;

    @ManyToOne
    private Listing listing;
}
