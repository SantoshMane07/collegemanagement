package com.santoshmane.week3.collegemanagement.configs;

import com.santoshmane.week3.collegemanagement.entities.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // Using Default and custom form login
//        httpSecurity
//                .authorizeHttpRequests(auth->auth
//                        .anyRequest().authenticated())
// Custom ->      .formLogin(formLoginConfig -> formLoginConfig.loginPage("/newLogin.html"))
// Default ->     .formLogin(Customizer.withDefaults());
//        return httpSecurity.build();


        // Authenticating End points for particular roles
                httpSecurity
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/subjects").permitAll()
                        .requestMatchers("admissionRecords/**").hasAnyRole(Role.ADMIN.toString())
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();

    }

    //Note to use in memory users remove UserDetailsService implementation which is loading user by username also remove user defined in application.properties
    @Bean
    UserDetailsService inMemoryUserDetailsService(){
        UserDetails adminUser = User
                .withUsername("Prem")
                .password(passwordEncoder().encode("Prem123"))
                .roles(Role.ADMIN.toString())
                .build();

        UserDetails professorUser = User
                .withUsername("Anuj")
                .password(passwordEncoder().encode("Anuj123"))
                .roles(Role.PROFESSOR.toString())
                .build();

        UserDetails studentUser = User
                .withUsername("Parth")
                .password(passwordEncoder().encode("Parth123"))
                .roles(Role.STUDENT.toString())
                .build();

        return new InMemoryUserDetailsManager(studentUser,adminUser,professorUser);
    }

    //Required while creating in memory users
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
