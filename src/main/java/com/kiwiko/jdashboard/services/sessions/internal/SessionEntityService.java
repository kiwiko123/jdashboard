package com.kiwiko.jdashboard.services.sessions.internal;

import com.kiwiko.jdashboard.library.lang.random.TokenGenerator;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.services.sessions.api.interfaces.SessionService;
import com.kiwiko.jdashboard.services.sessions.api.interfaces.errors.SessionException;
import com.kiwiko.jdashboard.services.sessions.api.dto.Session;
import com.kiwiko.jdashboard.services.sessions.api.dto.SessionProperties;
import com.kiwiko.jdashboard.services.sessions.internal.data.SessionEntity;
import com.kiwiko.jdashboard.services.sessions.internal.data.SessionEntityDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SessionEntityService implements SessionService {

    private static final int MAX_TOKEN_GENERATION_ATTEMPTS = 10;

    @Inject private SessionEntityDAO sessionEntityDAO;
    @Inject private SessionEntityMapper mapper;
    @Inject private TokenGenerator tokenHelper;
    @Inject private Logger logger;

    @Transactional
    @Override
    public Session createSessionForUser(long userId) {
        sessionEntityDAO.getByUserId(userId).stream()
                .map(SessionEntity::getId)
                .forEach(this::invalidateSession);

        String token = generateUniqueToken();
        Instant now = Instant.now();

        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setUserId(userId);
        sessionEntity.setToken(token);
        sessionEntity.setStartTime(now);
        sessionEntity.setEndTime(now.plus(SessionProperties.AUTHENTICATION_COOKIE_TIME_TO_LIVE));
        sessionEntity.setCreatedDate(now);
        sessionEntity.setLastUpdatedDate(now);

        SessionEntity managedEntity = sessionEntityDAO.save(sessionEntity);
        return mapper.toDto(managedEntity);
    }

    @Transactional
    @Override
    public Session createSessionCookieForUser(long userId, HttpServletResponse httpServletResponse) {
        int timeToLiveSeconds = (int) SessionProperties.AUTHENTICATION_COOKIE_TIME_TO_LIVE.get(ChronoUnit.SECONDS);

        Session session = createSessionForUser(userId);
        Cookie cookie = new Cookie(SessionProperties.AUTHENTICATION_COOKIE_NAME, session.getToken());
        cookie.setPath("/");
        cookie.setMaxAge(timeToLiveSeconds);

        httpServletResponse.addCookie(cookie);

        return session;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Session> getByToken(String token) {
        Collection<String> tokens = Arrays.asList(token);
        return getByTokens(tokens).stream()
                .findFirst();
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Session> getByTokens(Collection<String> tokens) {
        return sessionEntityDAO.getByTokens(tokens).stream()
                .map(mapper::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Session> getByUser(long userId) {
        return sessionEntityDAO.getByUserId(userId).stream()
                .findFirst()
                .map(mapper::toDto);
    }

    @Transactional
    @Override
    public Session saveSession(Session session) {
        SessionEntity entity = mapper.toEntity(session);
        entity.setLastUpdatedDate(Instant.now());

        SessionEntity managedEntity = sessionEntityDAO.save(entity);
        return mapper.toDto(managedEntity);
    }

    @Transactional
    @Override
    public Optional<Session> endSessionForUser(long userId) {
        Collection<SessionEntity> activeSessions = sessionEntityDAO.getByUserId(userId);
        activeSessions.stream()
                .map(SessionEntity::getId)
                .forEach(this::invalidateSession);

        return activeSessions.stream()
                .max(Comparator.comparing(SessionEntity::getStartTime))
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    private String generateUniqueToken() {
        String token = null;
        boolean isUniqueToken = false;
        int attempts = 0;

        while (!isUniqueToken && attempts++ < MAX_TOKEN_GENERATION_ATTEMPTS) {
            token = tokenHelper.generateToken();
            isUniqueToken = !getByToken(token).isPresent();

            if (attempts > 1) {
                logger.info(String.format("Attempt #%d to generate a unique session token", attempts));
            }
        }

        if (token == null) {
            throw new SessionException("Failed to generate unique token");
        }

        return token;
    }

    @Transactional
    public void invalidateSession(long sessionId) {
        SessionEntity entity = sessionEntityDAO.getById(sessionId)
                .orElseThrow(() -> new SessionException(String.format("No session found with ID %d", sessionId)));
        Instant now = Instant.now();
        entity.setIsRemoved(true);
        entity.setEndTime(now);
        entity.setLastUpdatedDate(now);

        sessionEntityDAO.save(entity);
        logger.debug(String.format("Invalidated session with ID %d for user ID %d", sessionId, entity.getUserId()));
    }

    @Override
    public boolean isExpired(Session session) {
        return session.getEndTime()
                .map(Instant.now()::isAfter)
                .orElse(false);
    }
}
