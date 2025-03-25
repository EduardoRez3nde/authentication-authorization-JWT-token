package com.project.authentication.entities;

import com.project.authentication.entities.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_role")
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    public Role() { }
}

