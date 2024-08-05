package com.santoshmane.week3.collegemanagement.configs;

import com.santoshmane.week3.collegemanagement.dtos.ProfessorDto;
import com.santoshmane.week3.collegemanagement.entities.ProfessorEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
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
}
