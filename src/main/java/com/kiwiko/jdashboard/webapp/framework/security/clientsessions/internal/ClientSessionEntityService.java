package com.kiwiko.jdashboard.webapp.framework.security.clientsessions.internal;

import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.dto.ClientSession;
import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.exceptions.ClientSessionRuntimeException;
import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.interfaces.ClientSessionService;
import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.interfaces.parameters.CreateClientSessionResponse;
import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.internal.data.ClientSessionEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.dto.UniversalUniqueIdentifier;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.UniqueIdentifierService;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.parameters.CreateUuidParameters;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientSessionEntityService implements ClientSessionService {

    @Inject private ClientSessionEntityDataFetcher clientSessionEntityDataFetcher;
    @Inject private ClientSessionEntityDataMapper clientSessionEntityDataMapper;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;
    @Inject private UniqueIdentifierService uniqueIdentifierService;

    @Override
    public Optional<ClientSession> get(long id) {
        return crudExecutor.get(id, clientSessionEntityDataFetcher, clientSessionEntityDataMapper);
    }

    @Override
    public Optional<ClientSession> getByUuid(String uuid) {
        return uniqueIdentifierService.getByUuid(uuid)
                .map(UniversalUniqueIdentifier::getReferenceKey)
                .flatMap(this::parseIdFromReferenceKey)
                .flatMap(this::get);
    }

    @Override
    public CreateClientSessionResponse createNewSession() {
        return create(new ClientSession());
    }

    @Override
    public ClientSession endSession(String uuid) {
        ClientSession clientSession = getByUuid(uuid)
                .filter(session -> session.getEndTime() == null)
                .orElseThrow(() -> new ClientSessionRuntimeException(""));

        clientSession.setEndTime(Instant.now());
        return merge(clientSession);
    }

    public CreateClientSessionResponse create(ClientSession clientSession) {
        clientSession.setStartTime(Instant.now());
        clientSession.setEndTime(null);

        ClientSession createdSession = crudExecutor.create(clientSession, clientSessionEntityDataFetcher, clientSessionEntityDataMapper);

        CreateUuidParameters parameters = new CreateUuidParameters(createUuidReferenceKey(createdSession.getId()));
        UniversalUniqueIdentifier identifier = uniqueIdentifierService.create(parameters);

        CreateClientSessionResponse response = new CreateClientSessionResponse();
        response.setClientSession(createdSession);
        response.setUuid(identifier.getUuid());

        return response;
    }

    public ClientSession merge(ClientSession clientSession) {
        return crudExecutor.merge(clientSession, clientSessionEntityDataFetcher, clientSessionEntityDataMapper);
    }

    private String createUuidReferenceKey(long id) {
        return String.format("__jdashboard_internal_client_sessions:%d", id);
    }

    private Optional<Long> parseIdFromReferenceKey(String referenceKey) {
        Pattern pattern = Pattern.compile("^__jdashboard_internal_client_sessions:(?<id>\\d+)$");
        Matcher matcher = pattern.matcher(referenceKey);
        if (!matcher.find()) {
            return Optional.empty();
        }

        return Optional.ofNullable(matcher.group("id"))
                .map(Long::parseLong);
    }
}
