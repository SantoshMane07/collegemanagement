package com.santoshmane.week3.collegemanagement.controllers;

import com.santoshmane.week3.collegemanagement.dtos.AdmissionRecordDto;
import com.santoshmane.week3.collegemanagement.entities.AdmissionRecordEntity;
import com.santoshmane.week3.collegemanagement.services.impl.AdmissionRecordServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admissionRecords")
public class AdmissionRecordController {

    private AdmissionRecordServiceImpl admissionRecordService;

    public AdmissionRecordController(AdmissionRecordServiceImpl admissionRecordService){
        this.admissionRecordService = admissionRecordService;
    }

    @PostMapping
    public ResponseEntity<AdmissionRecordDto> enrollStudent(@Valid @RequestBody AdmissionRecordDto admissionRecordDto) {
        return new ResponseEntity(admissionRecordService.enrollStudent(admissionRecordDto), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<AdmissionRecordDto>> getAllEnrolledStudents() {
        return ResponseEntity.ok(admissionRecordService.getAllEnrolledStudents());
    }
    @GetMapping("/{enrolledId}")
    public ResponseEntity<AdmissionRecordDto> getStudentEnrollmentDetailsById(@PathVariable Long enrolledId) {
        return ResponseEntity.ok(admissionRecordService.getStudentEnrollmentDetailsById(enrolledId));
    }
    @PutMapping("/{enrolledId}")
    public ResponseEntity<AdmissionRecordDto> updateStudentEnrollmentDetails(@Valid @PathVariable Long enrolledId, @RequestBody AdmissionRecordDto admissionRecordDto) {
        return ResponseEntity.ok(admissionRecordService.updateStudentEnrollmentDetails(enrolledId,admissionRecordDto));
    }
    @DeleteMapping("/{enrolledId}")
    public ResponseEntity<?> deleteStudentEnrollmentDetails(@PathVariable Long enrolledId) {
        admissionRecordService.deleteStudentEnrollmentDetails(enrolledId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{enrolledId}/assignStudent/{studentId}")
    public ResponseEntity<AdmissionRecordDto> assignStudentToRecord(@PathVariable Long enrolledId,@PathVariable Long studentId){
        return ResponseEntity.ok(admissionRecordService.assignStudentToRecord(enrolledId,studentId));
    }
}
