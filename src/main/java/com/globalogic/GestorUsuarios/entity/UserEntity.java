package com.globalogic.GestorUsuarios.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    private UUID id;
    private String created;
    private String lastLogin;
    private String token;
    private boolean isActive;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
}
