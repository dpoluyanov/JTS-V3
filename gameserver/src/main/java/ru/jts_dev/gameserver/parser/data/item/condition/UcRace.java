package ru.jts_dev.gameserver.parser.data.item.condition;

import java.util.List;

/**
 * @author Camelion
 * @since 14.01.16
 */
public class UcRace extends Condition {
    private final List<Integer> races;

    public UcRace(List<Integer> races) {
        this.races = races;
    }
}
