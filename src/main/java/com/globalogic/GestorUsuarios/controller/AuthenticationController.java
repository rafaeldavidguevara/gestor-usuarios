package com.globalogic.GestorUsuarios.controller;

import com.globalogic.GestorUsuarios.service.AuthenticationService;
import com.globalogic.GestorUsuarios.util.dto.SignUpRequestDto;
import com.globalogic.GestorUsuarios.util.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/sign-up", produces = { "application/json" })
    public ResponseEntity<ResponseDto> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        ResponseDto responseDto = authenticationService.signUp(signUpRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping(value = "/login", produces = { "application/json" })
    public ResponseEntity<ResponseDto> login(@RequestHeader("Authorization") String bearerToken) {
        ResponseDto responseDto = authenticationService.login(bearerToken);
        return ResponseEntity.ok(responseDto);
    }

}
