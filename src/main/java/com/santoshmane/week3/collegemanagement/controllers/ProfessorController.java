package com.santoshmane.week3.collegemanagement.controllers;

import com.santoshmane.week3.collegemanagement.dtos.ProfessorDto;
import com.santoshmane.week3.collegemanagement.dtos.StudentDto;
import com.santoshmane.week3.collegemanagement.dtos.SubjectDto;
import com.santoshmane.week3.collegemanagement.entities.ProfessorEntity;
import com.santoshmane.week3.collegemanagement.entities.StudentEntity;
import com.santoshmane.week3.collegemanagement.entities.SubjectEntity;
import com.santoshmane.week3.collegemanagement.services.impl.ProfessorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/professors")
public class ProfessorController {

    private ProfessorServiceImpl professorService;

    public ProfessorController(ProfessorServiceImpl professorService){
        this.professorService = professorService;
    }

    @GetMapping
    public ResponseEntity<List<ProfessorDto>> getAllProfessors() {
        return ResponseEntity.ok(professorService.getAllProfessors());
    }

    @GetMapping("/{professorId}")
    public ResponseEntity<ProfessorDto> getProfessorById(@PathVariable Long professorId) {
        return ResponseEntity.ok(professorService.getProfessorById(professorId));
    }

    @PutMapping("/{professorId}")
    public ResponseEntity<ProfessorDto> updateProfessorById(@PathVariable Long professorId, @RequestBody ProfessorDto professorDto) {
        return ResponseEntity.ok(professorService.updateProfessorById(professorId,professorDto));
    }

    @DeleteMapping("/{professorId}")
    public ResponseEntity<?> deleteProfessorById(@PathVariable Long professorId) {
        professorService.deleteProfessorById(professorId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<ProfessorDto> createProfessor(@RequestBody ProfessorDto professorDto) {
        return new ResponseEntity<>(professorService.createProfessor(professorDto),HttpStatus.CREATED);
    }

    @GetMapping("/{professorId}/assignedStudents")
    public ResponseEntity<List<StudentDto>> getAssignedStudents(@PathVariable Long professorId) {
        return ResponseEntity.ok(professorService.getAssignedStudents(professorId));
    }

    @GetMapping("/{professorId}/assignedSubjects")
    public ResponseEntity<List<SubjectDto>> getAssignedSubjects(@PathVariable Long professorId) {
        return ResponseEntity.ok(professorService.getAssignedSubjects(professorId));
    }

    @PutMapping("/{professorId}/assignStudent/{studentId}")
    public ResponseEntity<ProfessorDto> assignStudentToProfessor(@PathVariable Long professorId,@PathVariable Long studentId){
        return ResponseEntity.ok(professorService.assignStudentToProfessor(professorId,studentId));
    }


}
