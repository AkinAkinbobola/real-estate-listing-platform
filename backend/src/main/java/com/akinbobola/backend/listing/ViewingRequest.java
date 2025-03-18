package com.akinbobola.backend.listing;

import java.time.LocalDate;
import java.time.LocalTime;

public record ViewingRequest(LocalDate date, LocalTime startTime, LocalTime endTime) {
}
