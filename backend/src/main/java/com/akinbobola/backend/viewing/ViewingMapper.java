package com.akinbobola.backend.viewing;

import com.akinbobola.backend.listing.ViewingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewingMapper {
    public Viewing toViewing (ViewingRequest request) {
        return Viewing.builder()
                .date(request.date())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .build();
    }

    public ViewingResponse toViewingResponse (Viewing viewing) {
        return ViewingResponse.builder()
                .id(viewing.getId())
                .date(viewing.getDate())
                .startTime(viewing.getStartTime())
                .endTime(viewing.getEndTime())
                .status(viewing.getViewingStatus())
                .build();
    }

    public List <ViewingResponse> toViewingResponseList (List <Viewing> viewings) {
        return viewings.stream()
                .map(this::toViewingResponse)
                .toList();
    }
}
