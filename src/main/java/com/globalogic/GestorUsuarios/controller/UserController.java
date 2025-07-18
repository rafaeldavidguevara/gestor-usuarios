package com.globalogic.GestorUsuarios.controller;

import com.globalogic.GestorUsuarios.service.UserService;
import com.globalogic.GestorUsuarios.util.dto.SignUpRequestDto;
import com.globalogic.GestorUsuarios.util.dto.SignUpResponseDto;
import com.globalogic.GestorUsuarios.util.helper.TimestampHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/sign-up", produces = { "application/json" })
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto signUpResponseDto = userService.signUp(signUpRequestDto);
        return ResponseEntity.ok(signUpResponseDto);
    }

}
