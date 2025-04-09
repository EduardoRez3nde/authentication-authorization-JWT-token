package com.project.authentication.controllers;

import com.project.authentication.dtos.AuthenticationDTO;
import com.project.authentication.dtos.LoginResponseDTO;
import com.project.authentication.dtos.RegisterDTO;
import com.project.authentication.dtos.UserRoleDTO;
import com.project.authentication.entities.Role;
import com.project.authentication.entities.User;
import com.project.authentication.repositories.RoleRepository;
import com.project.authentication.repositories.UserRepository;
import com.project.authentication.security.TokenService;
import jakarta.persistence.SecondaryTable;
import jakarta.validation.Valid;
import org.jose4j.http.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthenticationController(
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder, RoleRepository roleRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid final AuthenticationDTO dto) {

        final UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        final Authentication auth = authenticationManager.authenticate(login);

        final String token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserRoleDTO> register(@RequestBody @Valid final RegisterDTO dto) {

        if (userRepository.findByUsername(dto.username()) != null)
            return ResponseEntity.badRequest().build();

        final String encodePassword = passwordEncoder.encode(dto.password());

        Set<Role> roles = dto.roles().stream()
                .map(r -> roleRepository.getReferenceById(UUID.fromString(r.id())))
                .collect(Collectors.toSet());

        User user = new User(dto.username(), encodePassword, roles);
        user = userRepository.save(user);

        return ResponseEntity.ok(UserRoleDTO.from(user));
    }
}







