package com.globalogic.GestorUsuarios.controller;

import com.globalogic.GestorUsuarios.service.AuthenticationService;
import com.globalogic.GestorUsuarios.util.dto.ResponseDto;
import com.globalogic.GestorUsuarios.util.dto.SignUpRequestDto;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private AuthenticationController testController;

    @Test
    public void givenSignUpRequestDto_whenCallSignUpMethod_thenReturnHttpStatusOK() {
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
        // WHEN
        Mockito.when(authenticationService.signUp(any())).thenReturn(responseDto);
        ResponseEntity<ResponseDto> response = testController.signUp(signUpRequestDto);
        // THEN
        Assert.isTrue(response.getStatusCode().value() == 200, "HTTP status code is not 200 OK");
    }

    @Test
    public void givenBearerToken_whenCallLoginMethod_thenReturnHttpStatusOK() {
        // GIVEN
        String bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGE0NDg4OHNAZ21haWwuY29tIiwiaWF0IjoxNzUzMjI2MDAwLCJleHAiOjE3NTMyMjk2MDB9.QjjD5ARtSWKsXgkS88_tSg9MvM4IorkmIP9twZebylY";
        ResponseDto responseDto = ResponseDto.builder()
                .name("Roberto")
                .email("robert2@pmail.com")
                .password("ps4eSgg5yuw")
                .build();
        // WHEN
        Mockito.when(authenticationService.login(any())).thenReturn(responseDto);
        ResponseEntity<ResponseDto> response = testController.login(bearerToken);
        // THEN
        Assert.isTrue(response.getStatusCode().value() == 200, "HTTP status code is not 200 OK");
    }

}