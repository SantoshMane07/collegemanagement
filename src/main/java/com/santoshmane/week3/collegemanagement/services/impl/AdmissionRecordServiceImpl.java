package com.santoshmane.week3.collegemanagement.services.impl;

import com.santoshmane.week3.collegemanagement.dtos.AdmissionRecordDto;
import com.santoshmane.week3.collegemanagement.dtos.StudentDto;
import com.santoshmane.week3.collegemanagement.entities.AdmissionRecordEntity;
import com.santoshmane.week3.collegemanagement.entities.StudentEntity;
import com.santoshmane.week3.collegemanagement.exceptions.ResourceNotFoundException;
import com.santoshmane.week3.collegemanagement.repositories.AdmissionRecordRepository;
import com.santoshmane.week3.collegemanagement.repositories.StudentRepository;
import com.santoshmane.week3.collegemanagement.services.AdmissionRecordService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdmissionRecordServiceImpl implements AdmissionRecordService {

    private final ModelMapper modelMapper;

    private final AdmissionRecordRepository admissionRecordRepository;

    private final StudentRepository studentRepository;

    @Override
    public AdmissionRecordDto enrollStudent(AdmissionRecordDto admissionRecordDto) {
        return modelMapper.map(admissionRecordRepository.save(modelMapper.map(admissionRecordDto, AdmissionRecordEntity.class)),AdmissionRecordDto.class);
    }

    @Override
    public List<AdmissionRecordDto> getAllEnrolledStudents() {
        return admissionRecordRepository.findAll()
                .stream()
                .map(admissionRecordEntity -> modelMapper.map(admissionRecordEntity,AdmissionRecordDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AdmissionRecordDto getStudentEnrollmentDetailsById(Long enrolledId) {
        isExistByIdAdmissionRecord(enrolledId);
        return modelMapper.map(admissionRecordRepository.findById(enrolledId).get(),AdmissionRecordDto.class);
    }

    @Override
    public AdmissionRecordDto updateStudentEnrollmentDetails(Long enrolledId, AdmissionRecordDto admissionRecordDto) {
        isExistByIdAdmissionRecord(enrolledId);
        admissionRecordDto.setId(enrolledId);
        return modelMapper.map(admissionRecordRepository.save(modelMapper.map(admissionRecordDto, AdmissionRecordEntity.class)),AdmissionRecordDto.class);
    }

    @Override
    public void deleteStudentEnrollmentDetails(Long enrolledId) {
        isExistByIdAdmissionRecord(enrolledId);
        admissionRecordRepository.deleteById(enrolledId);
    }
    @Override
    public AdmissionRecordDto assignStudentToRecord(Long enrolledId,Long studentId){
        AdmissionRecordEntity admissionRecordEntity = admissionRecordRepository.findById(enrolledId)
                .orElseThrow(() -> new ResourceNotFoundException("Admission record not found by Id: " + enrolledId));

        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found by Id: " + studentId));

        admissionRecordEntity.setStudent(studentEntity);

        return modelMapper.map(admissionRecordRepository.save(admissionRecordEntity), AdmissionRecordDto.class);
    }

    private void isExistByIdAdmissionRecord(Long id){
        boolean isExist=admissionRecordRepository.existsById(id);
        if(!isExist) throw new ResourceNotFoundException("Admission record not found by id:"+id);
    }

}
