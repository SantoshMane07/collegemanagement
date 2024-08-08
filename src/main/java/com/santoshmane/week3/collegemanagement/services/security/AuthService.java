package com.santoshmane.week3.collegemanagement.services.security;

import com.santoshmane.week3.collegemanagement.dtos.LoginDto;
import com.santoshmane.week3.collegemanagement.entities.SessionEntity;
import com.santoshmane.week3.collegemanagement.entities.UserEntity;
import com.santoshmane.week3.collegemanagement.repositories.SessionRepository;
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

    private final SessionService sessionService;

    public String login(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        String token = jwtService.generateToken(userEntity);
        //Add session in DB and replace with previous recensession
        sessionService.generateNewSession(userEntity,token);
        //
        return token;
    }
}
