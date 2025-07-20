package com.globalogic.GestorUsuarios.service;

import com.globalogic.GestorUsuarios.entity.UserEntity;
import com.globalogic.GestorUsuarios.exception.EmailAlreadyRegisteredException;
import com.globalogic.GestorUsuarios.repository.UserRepository;
import com.globalogic.GestorUsuarios.util.dto.SignUpRequestDto;
import com.globalogic.GestorUsuarios.util.dto.SignUpResponseDto;
import com.globalogic.GestorUsuarios.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail()))
            throw new EmailAlreadyRegisteredException("Another user already registered with email: " + signUpRequestDto.getEmail());
        UserEntity userEntity = userMapper.toEntity(signUpRequestDto);
        UserEntity pesistedUserEntity = userRepository.save(userEntity);
        return userMapper.toDto(pesistedUserEntity);
    }

}
