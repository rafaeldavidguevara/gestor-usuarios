package com.globalogic.GestorUsuarios.service;

import com.globalogic.GestorUsuarios.entity.UserEntity;
import com.globalogic.GestorUsuarios.exception.BearerTokenException;
import com.globalogic.GestorUsuarios.exception.UserAuthenticationException;
import com.globalogic.GestorUsuarios.repository.UserRepository;
import com.globalogic.GestorUsuarios.security.JwtService;
import com.globalogic.GestorUsuarios.security.PasswordEncryptor;
import com.globalogic.GestorUsuarios.util.dto.SignUpRequestDto;
import com.globalogic.GestorUsuarios.util.dto.ResponseDto;
import com.globalogic.GestorUsuarios.util.helper.TimestampHelper;
import com.globalogic.GestorUsuarios.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncryptor passwordEncryptor;

    public ResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail()))
            throw new UserAuthenticationException("Another user already registered with given email", HttpStatus.CONFLICT);
        UserEntity userEntity = userMapper.toEntity(signUpRequestDto);
        userEntity.setPassword(passwordEncryptor.encrypt(signUpRequestDto.getPassword()));
        UserEntity pesistedUserEntity = userRepository.save(userEntity);
        pesistedUserEntity.setPassword(signUpRequestDto.getPassword());
        ResponseDto responseDto = userMapper.toDto(pesistedUserEntity);
        responseDto.setToken(jwtService.generateToken(userEntity));
        return responseDto;
    }

    public ResponseDto login(String bearerToken) {
        String userMail = getEmailFromToken(bearerToken);
        UserEntity userEntity = userRepository.findByEmail(userMail)
                .orElseThrow(() -> new UserAuthenticationException("User not found with given token", HttpStatus.NOT_FOUND));
        userEntity.setLastLogin(TimestampHelper.getNowDate());
        userEntity.setPassword(passwordEncryptor.decrypt(userEntity.getPassword()));
        ResponseDto responseDto = userMapper.toDto(userEntity);
        responseDto.setToken(jwtService.generateToken(userEntity));
        return responseDto;
    }

    private String getEmailFromToken(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer "))
            throw new BearerTokenException("Invalid Bearer Token");
        String jwt = bearerToken.substring(7);
        String userMail;
        try {
            userMail = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            throw (new BearerTokenException("Invalid Bearer Token"));
        }
        return userMail;
    }

}
