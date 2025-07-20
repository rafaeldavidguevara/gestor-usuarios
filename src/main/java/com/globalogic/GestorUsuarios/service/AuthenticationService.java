package com.globalogic.GestorUsuarios.service;

import com.globalogic.GestorUsuarios.entity.UserEntity;
import com.globalogic.GestorUsuarios.exception.EmailAlreadyRegisteredException;
import com.globalogic.GestorUsuarios.repository.UserRepository;
import com.globalogic.GestorUsuarios.util.dto.SignUpRequestDto;
import com.globalogic.GestorUsuarios.util.dto.SignUpResponseDto;
import com.globalogic.GestorUsuarios.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail()))
            throw new EmailAlreadyRegisteredException("Another user already registered with email: " + signUpRequestDto.getEmail());
        UserEntity userEntity = userMapper.toEntity(signUpRequestDto);
        userEntity.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        UserEntity pesistedUserEntity = userRepository.save(userEntity);
        SignUpResponseDto signUpResponseDto = userMapper.toDto(pesistedUserEntity);
        signUpResponseDto.setPassword(signUpRequestDto.getPassword());
        signUpResponseDto.setToken(jwtService.generateToken(userEntity));
        return signUpResponseDto;
    }

}
