package com.akinbobola.backend.auth;

import com.akinbobola.backend.exceptions.OperationNotPermittedException;
import com.akinbobola.backend.role.Role;
import com.akinbobola.backend.role.RoleRepository;
import com.akinbobola.backend.security.JwtService;
import com.akinbobola.backend.user.User;
import com.akinbobola.backend.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public void registerUser (RegistrationRequest request, String roleName) {
        if (userRepository.existsByEmail(request.email())) {
            throw new OperationNotPermittedException("User with email '" + request.email() + "' already exists");
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role '" + roleName + "' not found"));

        User user = User
                .builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .enabled(true)
                .accountLocked(false)
                .firstName(request.firstName()).lastName(request.lastName()).roles(List.of(role)).build();

        userRepository.save(user);
    }

    public AuthenticationResponse authenticate (AuthenticationRequest request) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        User user = (User) auth.getPrincipal();

        var claims = new HashMap <String, Object>();
        claims.put("role", user.getAuthorities());

        String jwtToken = jwtService.generateToken(claims, user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
