package com.kiwiko.mvc.security.sessions.internal;

import com.kiwiko.metrics.api.LogService;
import com.kiwiko.mvc.security.sessions.api.SessionService;
import com.kiwiko.mvc.security.sessions.api.errors.SessionException;
import com.kiwiko.mvc.security.sessions.data.Session;
import com.kiwiko.mvc.security.sessions.data.SessionProperties;
import com.kiwiko.mvc.security.sessions.internal.dataAccess.SessionEntity;
import com.kiwiko.mvc.security.sessions.internal.dataAccess.SessionEntityDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class SessionEntityService implements SessionService {

    private static final int MAX_TOKEN_GENERATION_ATTEMPTS = 10;

    @Inject
    private SessionEntityDAO sessionEntityDAO;

    @Inject
    private SessionEntityMapper mapper;

    @Inject
    private SessionTokenHelper tokenHelper;

    @Inject
    private LogService logService;

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

        SessionEntity managedEntity = sessionEntityDAO.save(sessionEntity);
        return mapper.toTargetType(managedEntity);
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
    public Collection<Session> getByTokens(Collection<String> tokens) {
        return sessionEntityDAO.getByTokens(tokens).stream()
                .map(mapper::toTargetType)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Session> getByUser(long userId) {
        return sessionEntityDAO.getByUserId(userId).stream()
                .findFirst()
                .map(mapper::toTargetType);
    }

    @Transactional
    @Override
    public Session saveSession(Session session) {
        SessionEntity entity = mapper.toSourceType(session);
        SessionEntity managedEntity = sessionEntityDAO.save(entity);
        return mapper.toTargetType(managedEntity);
    }

    @Transactional(readOnly = true)
    private String generateUniqueToken() {
        String token = null;
        boolean isUniqueToken = false;
        int attempts = 0;

        while (!isUniqueToken && attempts++ < MAX_TOKEN_GENERATION_ATTEMPTS) {
            token = tokenHelper.generateToken();
            isUniqueToken = !getByToken(token).isPresent();
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

        sessionEntityDAO.save(entity);
        logService.debug(String.format("Invalidated session with ID %d for user ID %d", sessionId, entity.getUserId()));
    }
}
