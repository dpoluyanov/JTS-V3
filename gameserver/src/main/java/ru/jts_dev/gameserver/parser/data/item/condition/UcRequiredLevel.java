package ru.jts_dev.gameserver.parser.data.item.condition;

/**
 * @author Camelion
 * @since 14.01.16
 */
public class UcRequiredLevel extends Condition {
    private final int level;

    public UcRequiredLevel(int level) {
        this.level = level;
    }
}
