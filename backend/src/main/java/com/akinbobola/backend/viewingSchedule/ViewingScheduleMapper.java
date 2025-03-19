package com.akinbobola.backend.viewingSchedule;

import com.akinbobola.backend.listing.ListingMapper;
import com.akinbobola.backend.user.User;
import com.akinbobola.backend.viewing.Viewing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewingScheduleMapper {
    private final ListingMapper listingMapper;

    public ViewingSchedule toViewingSchedule (Viewing viewing, User user) {
        return ViewingSchedule.builder().viewing(viewing).user(user).emailSent(true)
                .status(ViewingScheduleStatus.PENDING).build();
    }

    public ViewingScheduleResponse toViewingScheduleResponse (ViewingSchedule viewingSchedule) {
        return ViewingScheduleResponse.builder()
                .id(viewingSchedule.getId())
                .status(viewingSchedule.getStatus())
                .createdAt(viewingSchedule.getCreatedDate())
                .listing(listingMapper.toListingResponse(viewingSchedule.getViewing().getListing()))
                .build();
    }
}
