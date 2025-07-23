package com.globalogic.GestorUsuarios.service;

import com.globalogic.GestorUsuarios.entity.UserEntity;
import com.globalogic.GestorUsuarios.repository.UserRepository;
import com.globalogic.GestorUsuarios.security.JwtService;
import com.globalogic.GestorUsuarios.security.PasswordEncryptor;
import com.globalogic.GestorUsuarios.util.dto.ResponseDto;
import com.globalogic.GestorUsuarios.util.dto.SignUpRequestDto;
import com.globalogic.GestorUsuarios.util.mapper.UserMapper;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncryptor passwordEncryptor;
    @InjectMocks
    private AuthenticationService testService;

    @Test
    public void givenSignUpRequestDto_whenCallSignUpMethod_thenReturnNotNullResponseDto() {
        // GIVEN
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .name("Roberto")
                .email("robert2@mail.com")
                .password("ps4eSgg5yuw")
                .build();
        ResponseDto responseDto = ResponseDto.builder()
                .name("Roberto")
                .email("robert2@pmail.com")
                .password("ps4eSgg5yuw")
                .build();
        UserEntity userEntity = UserEntity.builder()
                .name("Roberto")
                .email("robert2@mail.com")
                .password("ps4eSgg5yuw")
                .build();
        UserEntity persistedEntity = UserEntity.builder()
                .name("Roberto")
                .email("robert2@mail.com")
                .password("nnnklGqz6UvE5hTM6jDLig==")
                .build();
        String bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGl3d3dzaXM0QGdtYWlsLmNvbSIsImlhdCI6MTc1MzIyOTM3MiwiZXhwIjoxNzUzMjMyOTcyfQ.Uezdg634W9R_Nyutt-_EhGWJKxDZAEtelcDmpoB_kYY";
        Mockito.when(userRepository.existsByEmail(any())).thenReturn(false);
        Mockito.when(userMapper.toEntity(any())).thenReturn(userEntity);
        Mockito.when(passwordEncryptor.encrypt(any())).thenReturn("nnnklGqz6UvE5hTM6jDLig==");
        Mockito.when(userRepository.save(any())).thenReturn(persistedEntity);
        Mockito.when(userMapper.toDto(any())).thenReturn(responseDto);
        Mockito.when(jwtService.generateToken(any())).thenReturn(bearerToken);
        // WHEN
        ResponseDto result = testService.signUp(signUpRequestDto);
        // THEN
        Assert.notNull(result, "ResponseDto is null");
    }

    @Test
    public void givenBearerToken_whenCallLoginMethod_thenReturnNotNullResponseDto() {
        // GIVEN
        String bearerToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGl3d3dzaXM0QGdtYWlsLmNvbSIsImlhdCI6MTc1MzIyOTM3MiwiZXhwIjoxNzUzMjMyOTcyfQ.Uezdg634W9R_Nyutt-_EhGWJKxDZAEtelcDmpoB_kYY";
        ResponseDto responseDto = ResponseDto.builder()
                .name("Roberto")
                .email("robert2@pmail.com")
                .password("ps4eSgg5yuw")
                .build();
        UserEntity userEntity = UserEntity.builder()
                .name("Roberto")
                .email("robert2@pmail.com")
                .password("nnnklGqz6UvE5hTM6jDLig==")
                .build();
        Mockito.when(jwtService.extractUsername(any())).thenReturn("robert2@pmail.com");
        Mockito.when(userRepository.findByEmail(any())).thenReturn(java.util.Optional.of(userEntity));
        Mockito.when(passwordEncryptor.decrypt(any())).thenReturn("ps4eSgg5yuw");
        Mockito.when(userMapper.toDto(userEntity)).thenReturn(responseDto);
        Mockito.when(jwtService.generateToken(any())).thenReturn(bearerToken);
        // WHEN
        ResponseDto result = testService.login(bearerToken);
        // THEN
        Assert.notNull(result, "ResponseDto is null");
    }

}