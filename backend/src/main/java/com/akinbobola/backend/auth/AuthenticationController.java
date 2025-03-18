package com.akinbobola.backend.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register-agent")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity <?> registerAgent (
            @Valid @RequestBody RegistrationRequest request
    ) {
        authenticationService.registerUser(request, "AGENT");
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/register-user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity <?> registerUser (
            @Valid @RequestBody RegistrationRequest request
    ) {
        authenticationService.registerUser(request, "USER");
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity <AuthenticationResponse> authenticate (
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
