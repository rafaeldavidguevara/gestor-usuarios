package com.globalogic.GestorUsuarios.service;

import com.globalogic.GestorUsuarios.entity.UserEntity;
import com.globalogic.GestorUsuarios.util.dto.SignUpRequestDto;
import com.globalogic.GestorUsuarios.util.dto.SignUpResponseDto;
import com.globalogic.GestorUsuarios.util.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        UserEntity userEntity = UserMapper.toEntity(signUpRequestDto);
        //Save
        SignUpResponseDto signUpResponseDto = UserMapper.toDto(userEntity);
        return signUpResponseDto;
    }

}
