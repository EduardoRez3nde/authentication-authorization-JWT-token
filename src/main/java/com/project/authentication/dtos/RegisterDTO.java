package com.project.authentication.dtos;

import com.project.authentication.entities.Role;
import com.project.authentication.entities.User;
import com.project.authentication.services.validation.UserInsertValid;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@UserInsertValid
public record RegisterDTO(String username, String password, Set<RoleDTO> roles) {

    public static RegisterDTO from(User entity) {
        return new RegisterDTO(
                entity.getUsername(),
                entity.getPassword(),
                entity.getRoles().stream().map(RoleDTO::fromEntity).collect(Collectors.toSet())
        );
    }
}
