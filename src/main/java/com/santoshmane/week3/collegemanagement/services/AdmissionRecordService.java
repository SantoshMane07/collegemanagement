package com.santoshmane.week3.collegemanagement.services;

import com.santoshmane.week3.collegemanagement.dtos.AdmissionRecordDto;
import com.santoshmane.week3.collegemanagement.dtos.StudentDto;

import java.util.List;

public interface AdmissionRecordService {
    public AdmissionRecordDto enrollStudent(AdmissionRecordDto admissionRecordDto);
    public List<AdmissionRecordDto> getAllEnrolledStudents();
    public AdmissionRecordDto getStudentEnrollmentDetailsById(Long enrolledId);
    public AdmissionRecordDto updateStudentEnrollmentDetails(Long enrolledId,AdmissionRecordDto admissionRecordDto);
    public void deleteStudentEnrollmentDetails(Long enrolledId);
    public AdmissionRecordDto assignStudentToRecord(Long enrolledId,Long studentId);
}
