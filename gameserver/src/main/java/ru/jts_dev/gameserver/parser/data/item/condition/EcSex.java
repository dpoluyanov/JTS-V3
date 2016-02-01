package ru.jts_dev.gameserver.parser.data.item.condition;

/**
 * @author Camelion
 * @since 14.01.16
 */
public class EcSex implements Condition {
    private final int sex;

    public EcSex(int sex) {
        this.sex = sex;
    }
}
