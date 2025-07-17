package com.globalogic.GestorUsuarios.controller;

import com.globalogic.GestorUsuarios.util.dto.SignUpRequestDto;
import com.globalogic.GestorUsuarios.util.dto.SignUpResponseDto;
import com.globalogic.GestorUsuarios.util.helper.TimestampHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @PostMapping(value = "/sign-up", produces = { "application/json" })
    public ResponseEntity<SignUpResponseDto> obtenerTareas(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.ok(SignUpResponseDto.builder()
                        .id(UUID.randomUUID())
                        .created(TimestampHelper.getNowDate())
                        .lastLogin(TimestampHelper.getNowDate())
                        .token("E23423R234R4.4RQ3453F3FESR34ERRF4E343TAERFEERT4TTEF")
                        .isActive(true)
                        .name(signUpRequestDto.getName())
                        .email(signUpRequestDto.getEmail())
                        .password(signUpRequestDto.getPassword())
                        .phones(signUpRequestDto.getPhones())
                .build());
    }

}
