package ru.jts_dev.gameserver.parser.data.item.condition;

/**
 * @author Camelion
 * @since 14.01.16
 */
public class EcSubJob implements Condition {
    private final boolean value;

    public EcSubJob(boolean value) {
        this.value = value;
    }
}
