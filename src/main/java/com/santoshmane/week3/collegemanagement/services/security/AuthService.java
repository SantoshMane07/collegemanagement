package com.santoshmane.week3.collegemanagement.services.security;

import com.santoshmane.week3.collegemanagement.dtos.LoginDto;
import com.santoshmane.week3.collegemanagement.entities.UserEntity;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String login(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        return jwtService.generateToken(userEntity);
    }
}
