package com.santoshmane.week3.collegemanagement.services;

import com.santoshmane.week3.collegemanagement.dtos.ProfessorDto;
import com.santoshmane.week3.collegemanagement.dtos.StudentDto;
import com.santoshmane.week3.collegemanagement.dtos.SubjectDto;
import com.santoshmane.week3.collegemanagement.entities.ProfessorEntity;

import java.util.List;

public interface ProfessorService {
    public List<ProfessorDto> getAllProfessors();
    public ProfessorDto getProfessorById(Long professorId);
    public ProfessorDto updateProfessorById(Long professorId,ProfessorDto professorDto);
    public void deleteProfessorById(Long professorId);
    public ProfessorDto createProfessor(ProfessorDto professorDto);
    public List<StudentDto> getAssignedStudents(Long professorId);
    public List<SubjectDto> getAssignedSubjects(Long professorId);
    public ProfessorDto assignStudentToProfessor(Long professorId, Long studentId);
}
