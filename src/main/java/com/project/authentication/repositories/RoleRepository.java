package com.project.authentication.repositories;

import com.project.authentication.entities.Role;
import com.project.authentication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {  }
