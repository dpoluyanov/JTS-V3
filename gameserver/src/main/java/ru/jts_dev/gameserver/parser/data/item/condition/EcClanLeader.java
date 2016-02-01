package ru.jts_dev.gameserver.parser.data.item.condition;

/**
 * @author Camelion
 * @since 14.01.16
 */
public class EcClanLeader implements Condition {
    private final boolean value;

    public EcClanLeader(boolean value) {
        this.value = value;
    }
}
