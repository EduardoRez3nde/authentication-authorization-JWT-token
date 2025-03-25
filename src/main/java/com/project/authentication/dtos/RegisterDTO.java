package com.project.authentication.dtos;

import com.project.authentication.entities.Role;

import java.util.Set;

public record RegisterDTO(String username, String password, Set<Role> roles) {  }
