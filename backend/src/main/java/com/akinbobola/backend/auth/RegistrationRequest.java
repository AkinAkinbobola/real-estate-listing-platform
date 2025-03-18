package com.akinbobola.backend.auth;

public record RegistrationRequest(
        String email,
        String password,
        String firstName,
        String lastName
) {
}
