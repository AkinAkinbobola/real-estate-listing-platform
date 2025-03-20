package com.akinbobola.backend.viewingSchedule;

import com.akinbobola.backend.common.PageResponse;
import com.akinbobola.backend.exceptions.OperationNotPermittedException;
import com.akinbobola.backend.user.User;
import com.akinbobola.backend.viewing.Viewing;
import com.akinbobola.backend.viewing.ViewingRepository;
import com.akinbobola.backend.viewing.ViewingStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ViewingScheduleService {
    private final ViewingRepository viewingRepository;
    private final ViewingScheduleRepository viewingScheduleRepository;
    private final ViewingScheduleMapper viewingScheduleMapper;

    public Integer saveSchedule (Integer viewingId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        Viewing viewing = viewingRepository.findById(viewingId)
                .orElseThrow(() -> new EntityNotFoundException("Viewing not found"));

        if (viewing.getViewingStatus() == ViewingStatus.UNAVAILABLE) {
            throw new OperationNotPermittedException("This viewing is unavailable");
        }

        boolean scheduleConflicting = viewingScheduleRepository.isScheduleConflicting(viewingId, ViewingScheduleStatus.SCHEDULED);

        if (scheduleConflicting) {
            throw new OperationNotPermittedException("This viewing has already been scheduled");
        }

        ViewingSchedule viewingSchedule = viewingScheduleMapper.toViewingSchedule(viewing, user);
        viewingScheduleRepository.save(viewingSchedule);

        viewing.setViewingStatus(ViewingStatus.UNAVAILABLE);

        viewingRepository.save(viewing);

        return viewingSchedule.getId();
    }

    public PageResponse <ViewingScheduleResponse> getViewingSchedules (
            Integer page,
            Integer size,
            Authentication connectedUser
    ) {
        User user = (User) connectedUser.getPrincipal();

        Pageable pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page <ViewingSchedule> viewingSchedules = viewingScheduleRepository.findByUser(user.getId(), pageRequest);

        List <ViewingScheduleResponse> viewingScheduleResponses = viewingSchedules.stream()
                .map(viewingScheduleMapper::toViewingScheduleResponse)
                .toList();

        return new PageResponse <>(
                viewingScheduleResponses,
                viewingSchedules.getNumber(),
                viewingSchedules.getTotalPages(),
                viewingSchedules.getTotalElements(),
                viewingSchedules.getTotalPages(),
                viewingSchedules.isFirst(),
                viewingSchedules.isLast()
        );
    }

    public Integer cancelSchedule (Integer scheduleId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        ViewingSchedule viewingSchedule = viewingScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Viewing schedule not found"));

        if (!Objects.equals(user.getId(), viewingSchedule.getUser().getId())) {
            throw new OperationNotPermittedException("You are not permitted to cancel this schedule");
        }

        viewingSchedule.setStatus(ViewingScheduleStatus.CANCELLED);

        viewingScheduleRepository.save(viewingSchedule);

        Viewing viewing = viewingSchedule.getViewing();

        boolean hasActiveSchedules = viewingScheduleRepository.hasActiveSchedules(viewing.getId(), ViewingScheduleStatus.SCHEDULED);

        if (!hasActiveSchedules) {
            viewing.setViewingStatus(ViewingStatus.AVAILABLE);
            viewingRepository.save(viewing);
        }

        return viewingSchedule.getId();
    }

    public PageResponse <ViewingScheduleResponse> getAgentViewingSchedules (
            Integer page,
            Integer size,
            Authentication connectedUser
    ) {
        User user = (User) connectedUser.getPrincipal();

        Pageable pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page <ViewingSchedule> viewingSchedules = viewingScheduleRepository.findByAgent(user.getId(), pageRequest);

        List <ViewingScheduleResponse> viewingScheduleResponses = viewingSchedules.stream()
                .map(viewingScheduleMapper::toViewingScheduleResponse)
                .toList();

        return new PageResponse <>(
                viewingScheduleResponses,
                viewingSchedules.getNumber(),
                viewingSchedules.getTotalPages(),
                viewingSchedules.getTotalElements(),
                viewingSchedules.getTotalPages(),
                viewingSchedules.isFirst(),
                viewingSchedules.isLast()
        );
    }
}
