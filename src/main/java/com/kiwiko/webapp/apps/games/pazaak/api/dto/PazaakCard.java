package com.kiwiko.webapp.apps.games.pazaak.api.dto;

import com.kiwiko.library.persistence.identification.GeneratedLongIdentifiable;

public class PazaakCard extends GeneratedLongIdentifiable {

    private int modifier;

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }
}
