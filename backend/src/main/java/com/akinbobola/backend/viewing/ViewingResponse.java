package com.akinbobola.backend.viewing;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ViewingResponse {
    private Integer id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private ViewingStatus status;
}
