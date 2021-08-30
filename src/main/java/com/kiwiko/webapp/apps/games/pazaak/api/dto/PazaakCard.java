package com.kiwiko.webapp.apps.games.pazaak.api.dto;

import com.kiwiko.library.persistence.identification.GeneratedLongIdentifiable;

import java.util.Objects;

public class PazaakCard extends GeneratedLongIdentifiable {

    private int modifier;

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PazaakCard card = (PazaakCard) o;
        return modifier == card.modifier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), modifier);
    }
}
