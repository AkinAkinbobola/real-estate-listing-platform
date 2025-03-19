package com.akinbobola.backend.viewingSchedule;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("viewing-schedule")
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
}
