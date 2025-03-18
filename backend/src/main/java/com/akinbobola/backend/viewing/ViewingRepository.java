package com.akinbobola.backend.viewing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ViewingRepository extends JpaRepository <Viewing, Integer> {

    @Query("""
            select COUNT(v) > 0
            from Viewing v
            where v.listing.id = :listingId
            and v.date = :date
            and v.agent.id = :agentId
            and (
                    (:startTime between v.startTime and v.endTime)
                    or (:endTime between v.startTime and v.endTime)
                    or (v.startTime between :startTime and :endTime)
                )
            """)
    boolean viewingTimesOverlap (Integer listingId, Integer agentId, LocalDate date, LocalTime startTime, LocalTime endTime);
}