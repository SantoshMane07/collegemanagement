package com.santoshmane.week3.collegemanagement.services.security;

import com.santoshmane.week3.collegemanagement.entities.SessionEntity;
import com.santoshmane.week3.collegemanagement.entities.UserEntity;
import com.santoshmane.week3.collegemanagement.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT = 1;

    public void generateNewSession(UserEntity userEntity, String token) {
        List<SessionEntity> userSessions = sessionRepository.findByUserEntity(userEntity);
        if (userSessions.size() == SESSION_LIMIT) {
            userSessions.sort(Comparator.comparing(SessionEntity::getLastUsedAt));

            SessionEntity leastRecentlyUsedSession = userSessions.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }

        SessionEntity newSession = SessionEntity.builder()
                .userEntity(userEntity)
                .token(token)
                .build();
        sessionRepository.save(newSession);
    }

    public void validateSession(String token) {
        SessionEntity session = sessionRepository.findByToken(token)
                .orElseThrow(() -> new SessionAuthenticationException("Session not found for token: "+token));
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }
}
