package com.santoshmane.week3.collegemanagement.repositories;

import com.santoshmane.week3.collegemanagement.entities.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorEntity,Long> {
}
