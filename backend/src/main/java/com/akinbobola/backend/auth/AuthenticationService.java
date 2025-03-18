package com.akinbobola.backend.auth;

import com.akinbobola.backend.exceptions.OperationNotPermittedException;
import com.akinbobola.backend.role.Role;
import com.akinbobola.backend.role.RoleRepository;
import com.akinbobola.backend.user.User;
import com.akinbobola.backend.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    public void registerUser (@Valid RegistrationRequest request, String roleName) {
        if (userRepository.existsByEmail(request.email())) {
            throw new OperationNotPermittedException("User with email '" + request.email() + "' already exists");
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role '" + roleName + "' not found"));

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .firstName(request.firstName())
                .lastName(request.lastName())
                .roles(List.of(role))
                .build();

        userRepository.save(user);
    }
}
