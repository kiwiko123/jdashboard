package com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.interfaces.parameters;

import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.dto.ClientSession;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.Set;

@Getter
@Setter
public class CreateClientSessionResponse {
    private ClientSession clientSession;
    private String uuid;
    private @Nullable Long userId;
    private Set<Integer> permissions;
}
