package com.santoshmane.week3.collegemanagement.services;

import com.santoshmane.week3.collegemanagement.dtos.ProfessorDto;
import com.santoshmane.week3.collegemanagement.dtos.StudentDto;
import com.santoshmane.week3.collegemanagement.dtos.SubjectDto;

import java.util.List;

public interface StudentService {
    public List<StudentDto> getAllStudents();
    public StudentDto getStudentById(Long studentId);
    public StudentDto updateStudentById(Long studentId,StudentDto studentDto);
    public void deleteStudentById(Long studentId);
    public StudentDto createStudent(StudentDto studentDto);
    public List<ProfessorDto> getAssignedProfessorsToStudent(Long studentId);
    public List<SubjectDto> getAssignedSubjectsToStudent(Long studentId);
}
