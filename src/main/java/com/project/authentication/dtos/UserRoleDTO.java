package com.project.authentication.dtos;

import com.project.authentication.entities.User;

import java.util.Set;
import java.util.stream.Collectors;

public record UserRoleDTO(String id, String username, Set<RoleDTO> roles) {

    public static UserRoleDTO from(User user) {

        return new UserRoleDTO(
            user.getId().toString(),
            user.getUsername(),
            user.getRoles().stream()
                .map(RoleDTO::fromEntity)
                .collect(Collectors.toSet())
        );
    }
}