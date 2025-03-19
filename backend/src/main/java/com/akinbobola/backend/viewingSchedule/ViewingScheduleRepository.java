package com.akinbobola.backend.viewingSchedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ViewingScheduleRepository extends JpaRepository <ViewingSchedule, Integer> {

    @Query("""
            select count (v) > 0
            from ViewingSchedule v
            where v.viewing.id = :viewingId
            """)
    boolean isScheduleConflicting (Integer viewingId);
}