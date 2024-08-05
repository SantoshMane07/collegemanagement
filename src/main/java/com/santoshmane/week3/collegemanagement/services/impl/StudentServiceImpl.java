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
import com.santoshmane.week3.collegemanagement.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final ModelMapper modelMapper;

    private final StudentRepository studentRepository;

    private final ProfessorRepository professorRepository;

    private final SubjectRepository subjectRepository;


    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentEntity -> modelMapper.map(studentEntity,StudentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto getStudentById(Long studentId) {
        isExistsByIdStudent(studentId);
        return modelMapper.map(studentRepository.findById(studentId).get(),StudentDto.class);
    }

    @Override
    public StudentDto updateStudentById(Long studentId, StudentDto studentDto) {
        isExistsByIdStudent(studentId);
        studentDto.setId(studentId);
        return modelMapper.map(studentRepository.save(modelMapper.map(studentDto, StudentEntity.class)),StudentDto.class);
    }

    @Override
    public void deleteStudentById(Long studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found by id:"+studentId));
        // Remove the student from all professors' sets
        for (ProfessorEntity professorEntity : studentEntity.getProfessors()) {
            professorEntity.getStudents().remove(studentEntity);
            professorRepository.save(professorEntity);
        }
        // Remove the student from all subject's sets
        for (SubjectEntity subjectEntity : studentEntity.getSubjects()) {
            subjectEntity.getStudents().remove(studentEntity);
            subjectRepository.save(subjectEntity);
        }
        studentRepository.deleteById(studentId);
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        return modelMapper.map(studentRepository.save(modelMapper.map(studentDto, StudentEntity.class)),StudentDto.class);
    }

    @Override
    public List<ProfessorDto> getAssignedProfessorsToStudent(Long studentId) {
        isExistsByIdStudent(studentId);
        List<ProfessorEntity> professorEntities = studentRepository.findById(studentId).get().getProfessors().stream().toList();
        return professorEntities.stream().map(professorEntity -> modelMapper.map(professorEntity,ProfessorDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<SubjectDto> getAssignedSubjectsToStudent(Long studentId) {
        isExistsByIdStudent(studentId);
        List<SubjectEntity> subjectEntities = studentRepository.findById(studentId).get().getSubjects().stream().toList();
        return subjectEntities.stream().map(subjectEntity -> modelMapper.map(subjectEntity,SubjectDto.class)).collect(Collectors.toList());
    }

    private void isExistsByIdStudent(Long id){
        boolean isExist = studentRepository.existsById(id);
        if(!isExist) throw new ResourceNotFoundException("Student does not exists by Id:"+id);
    }
}
