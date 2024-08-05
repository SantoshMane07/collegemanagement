package com.santoshmane.week3.collegemanagement.services.impl;

import com.santoshmane.week3.collegemanagement.dtos.ProfessorDto;
import com.santoshmane.week3.collegemanagement.dtos.StudentDto;
import com.santoshmane.week3.collegemanagement.dtos.SubjectDto;
import com.santoshmane.week3.collegemanagement.entities.ProfessorEntity;
import com.santoshmane.week3.collegemanagement.entities.StudentEntity;
import com.santoshmane.week3.collegemanagement.entities.SubjectEntity;
import com.santoshmane.week3.collegemanagement.exceptions.ResourceNotFoundException;
import com.santoshmane.week3.collegemanagement.repositories.ProfessorRepository;
import com.santoshmane.week3.collegemanagement.repositories.StudentRepository;
import com.santoshmane.week3.collegemanagement.repositories.SubjectRepository;
import com.santoshmane.week3.collegemanagement.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService {

    private final ModelMapper modelMapper;

    private final SubjectRepository subjectRepository;

    private final StudentRepository studentRepository;

    private  final ProfessorRepository professorRepository;

    @Override
    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(subjectEntity -> modelMapper.map(subjectEntity, SubjectDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SubjectDto getSubjectById(Long subjectId) {
        isExistsByIdSubject(subjectId);
        return modelMapper.map(subjectRepository.findById(subjectId).get(), SubjectDto.class);
    }

    @Override
    public SubjectDto updateSubjectById(Long subjectId, SubjectDto subjectDto) {
        isExistsByIdSubject(subjectId);
        subjectDto.setId(subjectId);
        return modelMapper.map(subjectRepository.save(modelMapper.map(subjectDto, SubjectEntity.class)),SubjectDto.class);
    }

    @Override
    public void deleteSubjectById(Long subjectId) {
        SubjectEntity subjectEntity = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found by id:"+subjectId));
        // Remove the subject from all student's sets
        for (StudentEntity studentEntity : subjectEntity.getStudents()) {
            studentEntity.getSubjects().remove(subjectEntity);
            studentRepository.save(studentEntity);
        }
        subjectRepository.deleteById(subjectId);
    }

    @Override
    public SubjectDto createSubject(SubjectDto subjectDto) {
        return modelMapper.map(subjectRepository.save(modelMapper.map(subjectDto, SubjectEntity.class)), SubjectDto.class);
    }

    @Override
    public ProfessorDto getAssignedProfessor(Long subjectId) {
        isExistsByIdSubject(subjectId);
        return modelMapper.map(subjectRepository.findById(subjectId).get().getProfessor(), ProfessorDto.class);
    }

    @Override
    public List<StudentDto> getAssignedStudents(Long subjectId) {
        isExistsByIdSubject(subjectId);
        List<StudentEntity> studentEntities = subjectRepository.findById(subjectId).get().getStudents().stream().toList();
        return studentEntities.stream()
                .map(studentEntity -> modelMapper.map(studentEntity,StudentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SubjectDto assignProfessorToSubject(Long subjectId, Long professorId) {
        ProfessorEntity professorEntity = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException("professor not found by Id: " + professorId));

        SubjectEntity subjectEntity = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found by Id: " + subjectId));
        subjectEntity.setProfessor(professorEntity);
        professorEntity.getSubjects().add(subjectEntity);

        return modelMapper.map(subjectRepository.save(subjectEntity), SubjectDto.class);
    }

    @Override
    public SubjectDto assignStudentToSubject(Long subjectId, Long studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found by Id: " + studentId));

        SubjectEntity subjectEntity = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found by Id: " + subjectId));
        subjectEntity.getStudents().add(studentEntity);
        studentEntity.getSubjects().add(subjectEntity);

        return modelMapper.map(subjectRepository.save(subjectEntity), SubjectDto.class);
    }

    private void isExistsByIdSubject(Long subjectId){
        boolean isExist = subjectRepository.existsById(subjectId);
        if(!isExist) throw new ResourceNotFoundException("Subject not found by id:"+subjectId);
    }
}
