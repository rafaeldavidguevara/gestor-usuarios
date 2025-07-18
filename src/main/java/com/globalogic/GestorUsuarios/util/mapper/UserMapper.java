package com.globalogic.GestorUsuarios.util.mapper;

import com.globalogic.GestorUsuarios.entity.UserEntity;
import com.globalogic.GestorUsuarios.util.dto.SignUpRequestDto;
import com.globalogic.GestorUsuarios.util.dto.SignUpResponseDto;
import com.globalogic.GestorUsuarios.util.helper.TimestampHelper;

import java.util.UUID;

public class UserMapper {
    public static UserEntity toEntity(SignUpRequestDto signUpRequestDto) {
        String userName = (signUpRequestDto.getName() == null
                || signUpRequestDto.getName().isBlank())
                ? "" : signUpRequestDto.getName().strip();
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .created(TimestampHelper.getNowDate())
                .lastLogin(TimestampHelper.getNowDate())
                .token("123123nunujnunu534n5u34534uunu4452348f.34453ni53j53io4toi34foi4")
                .name(userName)
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .isActive(true)
                .build();
        return userEntity;
    }

    public static SignUpResponseDto toDto(UserEntity userEntity) {
        return SignUpResponseDto.builder()
                .id(userEntity.getId())
                .created(userEntity.getCreated())
                .lastLogin(userEntity.getLastLogin())
                .token(userEntity.getToken())
                .isActive(userEntity.isActive())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .phones(null) // Assuming phones are not part of UserEntity
                .build();
    }

}
