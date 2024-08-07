package com.santoshmane.week3.collegemanagement.configs;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper mapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Customize mappings to avoid cyclic references
//        modelMapper.typeMap(ProfessorEntity.class, ProfessorDto.class).addMappings(mapper -> {
//            mapper.skip(ProfessorDto::setStudents);
//            mapper.skip(ProfessorDto::setSubjects);
//        });

        return modelMapper;
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
