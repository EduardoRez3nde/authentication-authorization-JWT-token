package com.project.authentication.services;

import com.project.authentication.dtos.RegisterDTO;
import com.project.authentication.dtos.RoleDTO;
import com.project.authentication.dtos.UserRoleDTO;
import com.project.authentication.entities.Role;
import com.project.authentication.entities.User;
import com.project.authentication.repositories.RoleRepository;
import com.project.authentication.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public Page<UserRoleDTO> findAll(Pageable pageable) {
        Page<User> result = userRepository.findAll(pageable);
        return result.map(UserRoleDTO::from);
    }

    @Transactional
    public UserRoleDTO insert(RegisterDTO dto) {

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));

        for (RoleDTO role : dto.roles()) {
            Role r = roleRepository.getReferenceById(UUID.fromString(role.id()));
            user.getRoles().add(new Role(r));
        }
        user = userRepository.save(user);
        return UserRoleDTO.from(user);
    }
}
