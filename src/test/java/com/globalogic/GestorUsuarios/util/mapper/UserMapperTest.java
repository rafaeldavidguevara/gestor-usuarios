package com.globalogic.GestorUsuarios.util.mapper;

import com.globalogic.GestorUsuarios.entity.PhoneEntity;
import com.globalogic.GestorUsuarios.entity.UserEntity;
import com.globalogic.GestorUsuarios.util.dto.PhoneDto;
import com.globalogic.GestorUsuarios.util.dto.ResponseDto;
import com.globalogic.GestorUsuarios.util.dto.SignUpRequestDto;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

class UserMapperTest {

    private UserMapper userMapper  = new UserMapper();

    @Test
    public void givenUserEntity_whenCallToDtoMethod_thenReturnNotNullUserDto() {
        // GIVEN
        PhoneEntity phoneEntity = PhoneEntity.builder()
                .number(3434234L)
                .citycode(11)
                .countrycode("55")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .name("Roberto")
                .email("robert2@pmail.com")
                .password("nnnklGqz6UvE5hTM6jDLig==")
                .phones(List.of(phoneEntity))
                .build();
        // WHEN
        ResponseDto responseDto = userMapper.toDto(userEntity);
        // THEN
        Assert.notNull(responseDto);
    }

    @Test
    public void givenSignUpRequestDto_whenCallToEntityMethod_thenReturnNotNullUserEntity() {
        // GIVEN
        PhoneDto phoneDto = PhoneDto.builder()
                .number(3434234L)
                .citycode(11)
                .countrycode("55")
                .build();
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .name("Roberto")
                .email("robert2@pmail.com")
                .password("nnnklGqz6UvE5hTM6jDLig==")
                .phones(List.of(phoneDto))
                .build();
        // WHEN
        UserEntity userEntity = userMapper.toEntity(signUpRequestDto);
        // THEN
        Assert.notNull(userEntity);
    }
}