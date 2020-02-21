package com.kiwiko.scrabble.game.helpers;

import com.google.common.collect.HashMultiset;
import com.kiwiko.scrabble.game.ScrabbleGame;
import com.kiwiko.scrabble.game.ScrabblePlayer;
import com.kiwiko.scrabble.game.ScrabbleTile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScrabbleGameHelper {

    private static final Random random = new Random();

    public ScrabblePlayer createPlayer(String playerId, int numberOfTiles) {
        Collection<ScrabbleTile> availableTiles = makeRandomCharacters(numberOfTiles).stream()
                .map(character -> new ScrabbleTile(character, playerId))
                .collect(Collectors.collectingAndThen(Collectors.toList(), HashMultiset::create));

        return new ScrabblePlayer(playerId, availableTiles, HashMultiset.create(), HashMultiset.create());
    }

    public Optional<ScrabblePlayer> getPlayerById(ScrabbleGame game, String playerId) {
        return Stream.of(game.getPlayer(), game.getOpponent())
                .filter(p -> Objects.equals(p.getId(), playerId))
                .findFirst();
    }

    public boolean placeMove(
            ScrabbleGame game,
            String playerId,
            ScrabbleTile move) {
        return false;
    }

    private List<String> makeRandomCharacters(int size) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<String> randomCharacters = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            int index = random.nextInt(alphabet.length());
            String character = Character.toString(alphabet.charAt(index));
            randomCharacters.add(character);
        }

        return randomCharacters;
    }
}
