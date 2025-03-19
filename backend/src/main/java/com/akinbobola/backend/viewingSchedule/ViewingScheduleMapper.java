package com.akinbobola.backend.viewingSchedule;

import com.akinbobola.backend.user.User;
import com.akinbobola.backend.viewing.Viewing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewingScheduleMapper {
    public ViewingSchedule toViewingSchedule (Viewing viewing, User user) {
        return ViewingSchedule.builder()
                .viewing(viewing)
                .user(user)
                .emailSent(true)
                .status(ViewingScheduleStatus.PENDING)
                .build();
    }
}
