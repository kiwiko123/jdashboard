package com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.interfaces;

import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.dto.ClientSession;
import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.interfaces.parameters.CreateClientSessionResponse;

import java.util.Optional;

public interface ClientSessionService {

    Optional<ClientSession> get(long id);

    Optional<ClientSession> getByUuid(String uuid);

    CreateClientSessionResponse createNewSession();

    ClientSession endSession(String uuid);
}
