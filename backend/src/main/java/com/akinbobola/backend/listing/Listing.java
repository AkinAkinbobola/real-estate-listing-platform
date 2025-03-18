package com.akinbobola.backend.listing;


import com.akinbobola.backend.address.Address;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateListed;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Integer createdBy;

    @LastModifiedBy
    @Column(insertable = false)
    private Integer lastModifiedBy;
}
