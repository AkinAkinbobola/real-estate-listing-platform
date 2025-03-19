package com.akinbobola.backend.viewingSchedule;

import com.akinbobola.backend.exceptions.OperationNotPermittedException;
import com.akinbobola.backend.user.User;
import com.akinbobola.backend.viewing.Viewing;
import com.akinbobola.backend.viewing.ViewingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewingScheduleService {
    private final ViewingRepository viewingRepository;
    private final ViewingScheduleRepository viewingScheduleRepository;
    private final ViewingScheduleMapper viewingScheduleMapper;

    public Integer saveSchedule (Integer viewingId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        Viewing viewing = viewingRepository.findById(viewingId)
                .orElseThrow(() -> new EntityNotFoundException("Viewing not found"));

        boolean scheduleConflicting = viewingScheduleRepository.isScheduleConflicting(viewingId);

        if (scheduleConflicting) {
            throw new OperationNotPermittedException("This viewing has already been scheduled");
        }

        ViewingSchedule viewingSchedule = viewingScheduleMapper.toViewingSchedule(viewing, user);

        return viewingScheduleRepository.save(viewingSchedule).getId();
    }
}
