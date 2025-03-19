package com.akinbobola.backend.viewingSchedule;

import com.akinbobola.backend.listing.ListingResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ViewingScheduleResponse {
    private Integer id;
    private ViewingScheduleStatus status;
    private ListingResponse listing;
    private LocalDateTime createdAt;
}
