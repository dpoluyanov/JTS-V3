package ru.jts_dev.gameserver.constants;

/**
 * @author Camelion
 * @since 17.01.16
 */
public enum CharacterRace {
    HUMAN(0), ELF(1), DARKELF(2), ORC(3), DWARF(4), KAMAEL(5);

    private final int raceId;

    CharacterRace(int raceId) {
        this.raceId = raceId;
    }

    public int getId() {
        return raceId;
    }
}
