package com.santoshmane.week3.collegemanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "students",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<ProfessorEntity> professors;

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "students",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<SubjectEntity> subjects;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "student",cascade = CascadeType.ALL)
    private AdmissionRecordEntity admissionRecord;

//    @PreRemove
//    private void preRemove() {
//        // Remove associations with professors
//        for (ProfessorEntity professorEntity : professors) {
//            professorEntity.getStudents().remove(this);
//        }
//        // Remove associations with subjects
//        for (SubjectEntity subjectEntity : subjects) {
//            subjectEntity.getStudents().remove(this);
//        }
//    }
}
