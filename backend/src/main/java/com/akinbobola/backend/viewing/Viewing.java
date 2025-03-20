package com.akinbobola.backend.viewing;

import com.akinbobola.backend.common.BaseEntity;
import com.akinbobola.backend.listing.Listing;
import com.akinbobola.backend.user.User;
import com.akinbobola.backend.viewingSchedule.ViewingSchedule;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Viewing extends BaseEntity {

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private ViewingStatus viewingStatus;

    @ManyToOne
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private User agent;

    @OneToMany(mappedBy = "viewing")
    private List<ViewingSchedule> viewingSchedules;
}
