package com.globalogic.GestorUsuarios.service;

import com.globalogic.GestorUsuarios.entity.UserEntity;
import com.globalogic.GestorUsuarios.exception.BearerTokenException;
import com.globalogic.GestorUsuarios.exception.UserAuthenticationException;
import com.globalogic.GestorUsuarios.repository.UserRepository;
import com.globalogic.GestorUsuarios.util.encode.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public ResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail()))
            throw new UserAuthenticationException("Another user already registered with email: "
                    + signUpRequestDto.getEmail(), HttpStatus.CONFLICT);
        UserEntity userEntity = userMapper.toEntity(signUpRequestDto);
        userEntity.setPassword(passwordEncoder.encrypt(signUpRequestDto.getPassword()));
        UserEntity pesistedUserEntity = userRepository.save(userEntity);
        pesistedUserEntity.setPassword(signUpRequestDto.getPassword());
        ResponseDto responseDto = userMapper.toDto(pesistedUserEntity);
        responseDto.setToken(jwtService.generateToken(userEntity));
        return responseDto;
    }

    public ResponseDto login(String bearerToken) {
        String userMail = getEmailFromToken(bearerToken);
        UserEntity userEntity = userRepository.findByEmail(userMail)
                .orElseThrow(() -> new UserAuthenticationException("User not found with email: "
                        + userMail, HttpStatus.NOT_FOUND));
        userEntity.setLastLogin(TimestampHelper.getNowDate());
        userEntity.setPassword(passwordEncoder.decrypt(userEntity.getPassword()));
        ResponseDto responseDto = userMapper.toDto(userEntity);
        responseDto.setToken(jwtService.generateToken(userEntity));
        return responseDto;
    }

    private String getEmailFromToken(String bearerToken) {
        String userMail;
        if (bearerToken == null || !bearerToken.startsWith("Bearer "))
            throw new BearerTokenException("Invalid Bearer Token");
        try {
            String jwt = bearerToken.substring(7);
            userMail = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            throw (new BearerTokenException("Invalid Bearer Token"));
        }
        return userMail;
    }

}
