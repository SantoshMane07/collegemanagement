package com.santoshmane.week3.collegemanagement.services.impl;

import com.santoshmane.week3.collegemanagement.dtos.AdmissionRecordDto;
import com.santoshmane.week3.collegemanagement.dtos.ProfessorDto;
import com.santoshmane.week3.collegemanagement.dtos.StudentDto;
import com.santoshmane.week3.collegemanagement.dtos.SubjectDto;
import com.santoshmane.week3.collegemanagement.entities.AdmissionRecordEntity;
import com.santoshmane.week3.collegemanagement.entities.ProfessorEntity;
import com.santoshmane.week3.collegemanagement.entities.StudentEntity;
import com.santoshmane.week3.collegemanagement.entities.SubjectEntity;
import com.santoshmane.week3.collegemanagement.exceptions.ResourceNotFoundException;
import com.santoshmane.week3.collegemanagement.repositories.ProfessorRepository;
import com.santoshmane.week3.collegemanagement.repositories.StudentRepository;
import com.santoshmane.week3.collegemanagement.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ModelMapper modelMapper;

    private final ProfessorRepository professorRepository;

    private final StudentRepository studentRepository;


    @Override
    public List<ProfessorDto> getAllProfessors() {
        return professorRepository.findAll()
                .stream()
                .map(professorEntity -> modelMapper.map(professorEntity, ProfessorDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProfessorDto getProfessorById(Long professorId) {
        isExistByIdProfessor(professorId);
        return modelMapper.map(professorRepository.findById(professorId).get(), ProfessorDto.class);
    }

    @Override
    public ProfessorDto updateProfessorById(Long professorId, ProfessorDto professorDto) {
        isExistByIdProfessor(professorId);
        professorDto.setId(professorId);
        return modelMapper.map(professorRepository.save(modelMapper.map(professorDto, ProfessorEntity.class)), ProfessorDto.class);
    }

    @Override
    public void deleteProfessorById(Long professorId) {
        ProfessorEntity professorEntity = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException("Professor not found by id:"+professorId));
        // Remove the Professor from all student's sets
        for (StudentEntity studentEntity : professorEntity.getStudents()) {
            studentEntity.getProfessors().remove(professorEntity);
            studentRepository.save(studentEntity);
        }
        professorRepository.deleteById(professorId);
    }

    @Override
    public ProfessorDto createProfessor(ProfessorDto professorDto) {
        return modelMapper.map(professorRepository.save(modelMapper.map(professorDto,ProfessorEntity.class)), ProfessorDto.class);
    }

    @Override
    public List<StudentDto> getAssignedStudents(Long professorId) {
        isExistByIdProfessor(professorId);
        List<StudentEntity> studentEntities = professorRepository.findById(professorId).get().getStudents().stream().toList();
        return studentEntities.stream()
                .map(studentEntity -> modelMapper.map(studentEntity,StudentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubjectDto> getAssignedSubjects(Long professorId) {
        isExistByIdProfessor(professorId);
        List<SubjectEntity> subjectEntities = professorRepository.findById(professorId).get().getSubjects().stream().toList();
        return subjectEntities.stream()
                .map(subjectEntity -> modelMapper.map(subjectEntity,SubjectDto.class))
                .collect(Collectors.toList());
    }

    //assign student to professor
    @Override
    public ProfessorDto assignStudentToProfessor(Long professorId, Long studentId){
        ProfessorEntity professorEntity = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException("professor not found by Id: " + professorId));


        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found by Id: " + studentId));

        professorEntity.getStudents().add(studentEntity);

        return modelMapper.map(professorRepository.save(professorEntity), ProfessorDto.class);
    }
    //

    private void isExistByIdProfessor(Long id){
        boolean isExist = professorRepository.existsById(id);
        if(!isExist) throw new ResourceNotFoundException("Professor not found by id:"+id);
    }
}
