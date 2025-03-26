package com.project.authentication.dtos;

import com.project.authentication.entities.Role;

public record RoleDTO(String id, String authority) {

    public static RoleDTO fromEntity(Role role) {
        return new RoleDTO(role.getId().toString(), role.getAuthority());
    }
}