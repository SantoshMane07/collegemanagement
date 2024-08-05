package com.santoshmane.week3.collegemanagement.controllers;

import com.santoshmane.week3.collegemanagement.dtos.ProfessorDto;
import com.santoshmane.week3.collegemanagement.dtos.StudentDto;
import com.santoshmane.week3.collegemanagement.dtos.SubjectDto;
import com.santoshmane.week3.collegemanagement.entities.ProfessorEntity;
import com.santoshmane.week3.collegemanagement.entities.StudentEntity;
import com.santoshmane.week3.collegemanagement.entities.SubjectEntity;
import com.santoshmane.week3.collegemanagement.services.StudentService;
import com.santoshmane.week3.collegemanagement.services.impl.StudentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService){
        this.studentService=studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDto> updateStudentById(@PathVariable Long studentId, @RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.updateStudentById(studentId,studentDto));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudentById(@PathVariable Long studentId) {
        studentService.deleteStudentById(studentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.createStudent(studentDto));
    }

    @GetMapping("/{studentId}/assignedProfessors")
    public ResponseEntity<List<ProfessorDto>> getAssignedProfessorsToStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getAssignedProfessorsToStudent(studentId));
    }

    @GetMapping("/{studentId}/assignedSubjects")
    public ResponseEntity<List<SubjectDto>> getAssignedSubjectsToStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getAssignedSubjectsToStudent(studentId));
    }

}
