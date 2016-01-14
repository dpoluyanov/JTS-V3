package ru.jts_dev.gameserver.parser.data.item.condition;

/**
 * @author Camelion
 * @since 14.01.16
 */
public class EcRequiredLevel extends Condition {
    private final int level;

    public EcRequiredLevel(int level) {
        this.level = level;
    }
}
