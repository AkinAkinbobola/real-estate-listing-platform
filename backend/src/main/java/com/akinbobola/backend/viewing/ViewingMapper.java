package com.akinbobola.backend.viewing;

import com.akinbobola.backend.listing.ViewingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
