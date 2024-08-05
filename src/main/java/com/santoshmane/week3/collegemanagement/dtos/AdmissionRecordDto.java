package com.santoshmane.week3.collegemanagement.dtos;

import com.santoshmane.week3.collegemanagement.entities.StudentEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionRecordDto {
    private Long id;
    @NotNull(message = "Fess should not be null")
    private Integer fees;
    private StudentDto student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdmissionRecordDto that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
