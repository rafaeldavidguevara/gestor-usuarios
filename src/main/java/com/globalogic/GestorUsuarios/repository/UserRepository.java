package com.globalogic.GestorUsuarios.repository;

import com.globalogic.GestorUsuarios.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);
}
