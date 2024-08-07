package com.santoshmane.week3.collegemanagement.services.security;

import com.santoshmane.week3.collegemanagement.dtos.LoginDto;
import com.santoshmane.week3.collegemanagement.dtos.SignUpDto;
import com.santoshmane.week3.collegemanagement.dtos.UserDto;
import com.santoshmane.week3.collegemanagement.entities.Role;
import com.santoshmane.week3.collegemanagement.entities.UserEntity;
import com.santoshmane.week3.collegemanagement.exceptions.ResourceNotFoundException;
import com.santoshmane.week3.collegemanagement.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()->
                    new BadCredentialsException("User with email "+username+" not found")
                );
    }

    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id "+ userId +
                " not found"));
    }

    public UserDto signUp(SignUpDto signUpDto) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(signUpDto.getEmail());
        if (userEntity.isPresent()){
            throw new BadCredentialsException("User with email already exists "+signUpDto.getEmail());
        }
        UserEntity toBeCreatedUser = modelMapper.map(signUpDto,UserEntity.class);

        //TODO : Add roles by default to users when sign up
        toBeCreatedUser.setRoles(Set.of(Role.VISITER));

        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));

        UserEntity savedUser = userRepository.save(toBeCreatedUser);
        return modelMapper.map(savedUser,UserDto.class);
    }
    //For login, we are using auth service to avoid circular dependency
}
