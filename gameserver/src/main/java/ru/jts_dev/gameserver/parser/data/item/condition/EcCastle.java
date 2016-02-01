package ru.jts_dev.gameserver.parser.data.item.condition;

/**
 * @author Camelion
 * @since 14.01.16
 */
public class EcCastle implements Condition {
    private final boolean hasCastle;

    public EcCastle(boolean hasCastle) {
        this.hasCastle = hasCastle;
    }
}
