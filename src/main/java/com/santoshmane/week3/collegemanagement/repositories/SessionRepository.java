package com.santoshmane.week3.collegemanagement.repositories;

import com.santoshmane.week3.collegemanagement.entities.SessionEntity;
import com.santoshmane.week3.collegemanagement.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findByUserEntity(UserEntity userEntity);

    Optional<SessionEntity> findByToken(String token);
}
