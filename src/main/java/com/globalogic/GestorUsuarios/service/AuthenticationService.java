package com.globalogic.GestorUsuarios.service;

import com.globalogic.GestorUsuarios.entity.UserEntity;
import com.globalogic.GestorUsuarios.exception.BearerTokenException;
import com.globalogic.GestorUsuarios.exception.EmailAlreadyRegisteredException;
import com.globalogic.GestorUsuarios.repository.UserRepository;
import com.globalogic.GestorUsuarios.util.dto.SignUpRequestDto;
import com.globalogic.GestorUsuarios.util.dto.ResponseDto;
import com.globalogic.GestorUsuarios.util.helper.TimestampHelper;
import com.globalogic.GestorUsuarios.util.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public ResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail()))
            throw new EmailAlreadyRegisteredException("Another user already registered with email: " + signUpRequestDto.getEmail());
        UserEntity userEntity = userMapper.toEntity(signUpRequestDto);
        userEntity.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        UserEntity pesistedUserEntity = userRepository.save(userEntity);
        ResponseDto responseDto = userMapper.toDto(pesistedUserEntity);
        responseDto.setPassword(signUpRequestDto.getPassword());
        responseDto.setToken(jwtService.generateToken(userEntity));
        return responseDto;
    }

    public ResponseDto login(String bearerToken) {
        String userMail = getEmailFromToken(bearerToken);
        UserEntity userEntity = userRepository.findByEmail(userMail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userMail));
        ResponseDto responseDto = userMapper.toDto(userEntity);
        responseDto.setPassword(userEntity.getPassword());
        responseDto.setLastLogin(TimestampHelper.getNowDate());
        responseDto.setToken(jwtService.generateToken(userEntity));
        return responseDto;
    }

    private String getEmailFromToken(String bearerToken) {
        String userMail;
        try {
            String jwt = bearerToken.substring(7);
            userMail = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            throw (new BearerTokenException("Invalid Bearer Token"));
        }
        return userMail;
    }

}
