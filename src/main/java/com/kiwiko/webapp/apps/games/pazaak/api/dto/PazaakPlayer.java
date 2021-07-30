package com.kiwiko.webapp.apps.games.pazaak.api.dto;

import com.kiwiko.library.persistence.identification.TypeIdentifiable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PazaakPlayer extends TypeIdentifiable<String> {

    private String id;
    private Set<PazaakCard> handCards = new HashSet<>();
    private List<PazaakCard> placedCards = new LinkedList<>();

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<PazaakCard> getHandCards() {
        return handCards;
    }

    public void setHandCards(Set<PazaakCard> handCards) {
        this.handCards = handCards;
    }

    public List<PazaakCard> getPlacedCards() {
        return placedCards;
    }

    public void setPlacedCards(List<PazaakCard> placedCards) {
        this.placedCards = placedCards;
    }
}
