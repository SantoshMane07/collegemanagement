package com.santoshmane.week3.collegemanagement.services;

import com.santoshmane.week3.collegemanagement.dtos.ProfessorDto;
import com.santoshmane.week3.collegemanagement.dtos.StudentDto;
import com.santoshmane.week3.collegemanagement.dtos.SubjectDto;

import java.util.List;

public interface SubjectService {
    public List<SubjectDto> getAllSubjects();
    public SubjectDto getSubjectById(Long subjectId);
    public SubjectDto updateSubjectById(Long subjectId, SubjectDto subjectDto);
    public void deleteSubjectById(Long subjectId);
    public SubjectDto createSubject(SubjectDto subjectDto);
    public ProfessorDto getAssignedProfessor(Long subjectId);
    public List<StudentDto> getAssignedStudents(Long subjectId);
    public SubjectDto assignProfessorToSubject(Long subjectId,Long professorId);
    public SubjectDto assignStudentToSubject(Long subjectId,Long studentId);
}
