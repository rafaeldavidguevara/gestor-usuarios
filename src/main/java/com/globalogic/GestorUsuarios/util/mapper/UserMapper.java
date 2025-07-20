package com.globalogic.GestorUsuarios.util.mapper;

import com.globalogic.GestorUsuarios.entity.PhoneEntity;
import com.globalogic.GestorUsuarios.entity.UserEntity;
import com.globalogic.GestorUsuarios.util.dto.PhoneDto;
import com.globalogic.GestorUsuarios.util.dto.SignUpRequestDto;
import com.globalogic.GestorUsuarios.util.dto.SignUpResponseDto;
import com.globalogic.GestorUsuarios.util.helper.TimestampHelper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserEntity toEntity(SignUpRequestDto signUpRequestDto) {
        UserEntity userEntity = createUserEntity(signUpRequestDto);
        userEntity.setPhones(createPhoneEntityList(signUpRequestDto, userEntity));
        return userEntity;
    }

    public SignUpResponseDto toDto(UserEntity userEntity) {
        SignUpResponseDto  signUpResponseDto = createSignUpResponseDto(userEntity);
        signUpResponseDto.setPhones(createPhoneDtoList(userEntity));
        return signUpResponseDto;
    }

    private UserEntity createUserEntity(SignUpRequestDto signUpRequestDto) {
        return UserEntity.builder()
                .id(UUID.randomUUID())
                .created(TimestampHelper.getNowDate())
                .lastLogin(TimestampHelper.getNowDate())
                .token("123123nunujnunu534n5u34534uunu4452348f.34453ni53j53io4toi34foi4")
                .name((signUpRequestDto.getName() == null || signUpRequestDto.getName().isBlank())
                        ? "" : signUpRequestDto.getName().strip())
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .isActive(true)
                .build();
    }

    private List<PhoneEntity> createPhoneEntityList(SignUpRequestDto signUpRequestDto, UserEntity userEntity) {
        return signUpRequestDto.getPhones() != null
                ? signUpRequestDto.getPhones().stream()
                .map((var phoneDto) -> PhoneEntity.builder()
                        .number(phoneDto.getNumber())
                        .countrycode(phoneDto.getCountrycode())
                        .citycode(phoneDto.getCitycode())
                        .user(userEntity)
                        .build())
                .collect(Collectors.toList())
                : List.of();
    }

    private SignUpResponseDto createSignUpResponseDto(UserEntity userEntity) {
        return SignUpResponseDto.builder()
                .id(userEntity.getId())
                .created(userEntity.getCreated())
                .lastLogin(userEntity.getLastLogin())
                .token(userEntity.getToken())
                .isActive(userEntity.isActive())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .build();
    }

    private List<PhoneDto> createPhoneDtoList(UserEntity userEntity) {
        return !userEntity.getPhones().isEmpty()
                ? userEntity.getPhones().stream()
                .map((var phoneEntity) -> PhoneDto.builder()
                        .number(phoneEntity.getNumber())
                        .countrycode(phoneEntity.getCountrycode())
                        .citycode(phoneEntity.getCitycode())
                        .build())
                .collect(Collectors.toList())
                : List.of();
    }
}
