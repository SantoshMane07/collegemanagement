package com.santoshmane.week3.collegemanagement.controllers;

import com.santoshmane.week3.collegemanagement.dtos.ProfessorDto;
import com.santoshmane.week3.collegemanagement.dtos.StudentDto;
import com.santoshmane.week3.collegemanagement.dtos.SubjectDto;
import com.santoshmane.week3.collegemanagement.services.impl.SubjectServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private SubjectServiceImpl subjectService;

    public SubjectController(SubjectServiceImpl subjectService){
        this.subjectService = subjectService;
    }
    //
    @GetMapping
    public ResponseEntity<List<SubjectDto>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }
    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectDto> getSubjectById(@PathVariable Long subjectId) {
        return ResponseEntity.ok(subjectService.getSubjectById(subjectId));
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<SubjectDto> updateSubjectById(@PathVariable Long subjectId,@RequestBody SubjectDto subjectDto) {
        return ResponseEntity.ok(subjectService.updateSubjectById(subjectId,subjectDto));
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<?> deleteSubjectById(@PathVariable Long subjectId) {
        subjectService.deleteSubjectById(subjectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectDto subjectDto) {
        return ResponseEntity.ok(subjectService.createSubject(subjectDto));
    }

    @GetMapping("/{subjectId}/getAssignedProfessor")
    public ResponseEntity<ProfessorDto> getAssignedProfessor(@PathVariable Long subjectId) {
        return ResponseEntity.ok(subjectService.getAssignedProfessor(subjectId));
    }

    @GetMapping("/{subjectId}/getAssignedStudents")
    public ResponseEntity<List<StudentDto>> getAssignedStudents(@PathVariable Long subjectId) {
        return ResponseEntity.ok(subjectService.getAssignedStudents(subjectId));
    }
    //
    @PutMapping("/{subjectId}/assignStudent/{studentId}")
    public ResponseEntity<SubjectDto> assignStudentToSubject(@PathVariable Long subjectId,@PathVariable Long studentId){
        return ResponseEntity.ok(subjectService.assignStudentToSubject(subjectId,studentId));
    }
    //
    @PutMapping("/{subjectId}/assignProfessor/{professorId}")
    public ResponseEntity<SubjectDto> assignProfessorToSubject(@PathVariable Long subjectId,@PathVariable Long professorId){
        return ResponseEntity.ok(subjectService.assignProfessorToSubject(subjectId,professorId));
    }
}
