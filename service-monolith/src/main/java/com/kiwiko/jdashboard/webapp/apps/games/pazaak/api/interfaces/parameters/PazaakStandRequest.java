package com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.parameters;

import lombok.Getter;

@Getter
public class PazaakStandRequest extends PazaakLoadGameParameters {
    private String playerId;
}
