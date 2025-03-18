package com.akinbobola.backend.listing;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Listing {

    @Id
    @GeneratedValue
    private Integer id;

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

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateListed;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;
}
