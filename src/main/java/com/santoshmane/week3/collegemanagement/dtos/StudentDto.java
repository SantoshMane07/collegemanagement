package com.santoshmane.week3.collegemanagement.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.santoshmane.week3.collegemanagement.entities.ProfessorEntity;
import com.santoshmane.week3.collegemanagement.entities.SubjectEntity;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long id;
    private String name;
    @JsonIgnore
    private Set<ProfessorDto> professors;
    @JsonIgnore
    private Set<SubjectDto> subjects;
    @JsonIgnore
    private AdmissionRecordDto admissionRecord;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentDto that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
