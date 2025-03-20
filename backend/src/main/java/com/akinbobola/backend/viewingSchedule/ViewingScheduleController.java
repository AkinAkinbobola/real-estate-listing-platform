package com.akinbobola.backend.viewingSchedule;

import com.akinbobola.backend.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("viewing-schedules")
public class ViewingScheduleController {

    private final ViewingScheduleService service;

    @PostMapping("/{viewing-id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity <Integer> createViewingSchedule (
            @PathVariable(name = "viewing-id") Integer viewingId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.saveSchedule(viewingId, connectedUser));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity <PageResponse <ViewingScheduleResponse>> getViewingSchedules (
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.getViewingSchedules(page, size, connectedUser));
    }

    @PatchMapping("/cancel/{schedule-id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Integer> cancelViewingSchedule (
            @PathVariable(name = "schedule-id") Integer scheduleId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.cancelSchedule(scheduleId, connectedUser));
    }

    @PatchMapping("/confirm/{schedule-id}")
    @PreAuthorize("hasAuthority('AGENT')")
    public ResponseEntity<Integer> confirmViewingSchedule (
            @PathVariable(name = "schedule-id") Integer scheduleId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.confirmSchedule(scheduleId, connectedUser));
    }

    @GetMapping("/agent")
    @PreAuthorize("hasAuthority('AGENT')")
    public ResponseEntity <PageResponse <ViewingScheduleResponse>> getAgentViewingSchedules (
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.getAgentViewingSchedules(page, size, connectedUser));
    }
}
