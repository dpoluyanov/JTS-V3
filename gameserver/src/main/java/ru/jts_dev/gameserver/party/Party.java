package ru.jts_dev.gameserver.party;

import ru.jts_dev.gameserver.model.GameCharacter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Java-man
 * @since 21.01.2016
 */
public class Party {
    private static final int MAX_CHARACTERS_IN_PARTY = 8;

    private List<GameCharacter> members = new ArrayList<>(MAX_CHARACTERS_IN_PARTY);

    public Party(GameCharacter leader) {
        members.add(leader);
    }

    public void joinParty(GameCharacter character) {
        members.add(character);
    }
}
