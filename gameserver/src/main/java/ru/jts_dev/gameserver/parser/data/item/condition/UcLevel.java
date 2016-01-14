package ru.jts_dev.gameserver.parser.data.item.condition;

/**
 * @author Camelion
 * @since 14.01.16
 */
public class UcLevel extends Condition {
    private final int min;
    private final int max;

    public UcLevel(int min, int max) {
        this.min = min;
        this.max = max;
    }
}
