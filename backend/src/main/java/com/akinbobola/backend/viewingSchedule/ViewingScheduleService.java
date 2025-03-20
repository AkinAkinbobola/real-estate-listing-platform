package com.akinbobola.backend.viewingSchedule;

import com.akinbobola.backend.common.PageResponse;
import com.akinbobola.backend.exceptions.OperationNotPermittedException;
import com.akinbobola.backend.user.User;
import com.akinbobola.backend.viewing.Viewing;
import com.akinbobola.backend.viewing.ViewingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

        if (viewingSchedule.getStatus() == ViewingScheduleStatus.CANCELLED) {
            throw new OperationNotPermittedException("Viewing schedule is already cancelled");
        }

        viewingSchedule.setStatus(ViewingScheduleStatus.CANCELLED);

        return viewingScheduleRepository.save(viewingSchedule).getId();
    }

    public Integer confirmSchedule (Integer scheduleId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        ViewingSchedule viewingSchedule = viewingScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Viewing schedule not found"));

        if (!Objects.equals(user.getId(), viewingSchedule.getViewing().getListing().getAgent().getId())) {
            throw new OperationNotPermittedException("You are not permitted to confirm this schedule");
        }

        if (viewingSchedule.getStatus() == ViewingScheduleStatus.CONFIRMED) {
            throw new OperationNotPermittedException("Viewing schedule is already confirmed");
        }

        viewingSchedule.setStatus(ViewingScheduleStatus.CONFIRMED);

        return viewingScheduleRepository.save(viewingSchedule).getId();
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

        return new PageResponse<>(
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
