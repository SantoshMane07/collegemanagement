package com.santoshmane.week3.collegemanagement.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.santoshmane.week3.collegemanagement.entities.StudentEntity;
import com.santoshmane.week3.collegemanagement.entities.SubjectEntity;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDto {
    private Long id;
    private String title;
    @JsonIgnore
    private Set<SubjectDto> subjects;

    private Set<StudentDto> students;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfessorDto that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
