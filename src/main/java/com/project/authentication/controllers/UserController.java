package com.project.authentication.controllers;

import com.project.authentication.dtos.RegisterDTO;
import com.project.authentication.dtos.UserRoleDTO;
import com.project.authentication.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserRoleDTO>> findAll(Pageable pageable) {
        Page<UserRoleDTO> result = userService.findAll(pageable);
        return ResponseEntity.ok(result);
    }
}
