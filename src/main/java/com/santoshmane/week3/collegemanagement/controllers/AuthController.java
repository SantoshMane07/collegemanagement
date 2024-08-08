package com.santoshmane.week3.collegemanagement.controllers;


import com.santoshmane.week3.collegemanagement.dtos.LoginDto;
import com.santoshmane.week3.collegemanagement.dtos.SignUpDto;
import com.santoshmane.week3.collegemanagement.dtos.LoginResponseDto;
import com.santoshmane.week3.collegemanagement.dtos.UserDto;
import com.santoshmane.week3.collegemanagement.services.security.AuthService;
import com.santoshmane.week3.collegemanagement.services.security.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpDto signUpDto){
        UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse){
        String token = authService.login(loginDto);

        //Send token in response cookies also
        Cookie cookie = new Cookie("token",token);
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
