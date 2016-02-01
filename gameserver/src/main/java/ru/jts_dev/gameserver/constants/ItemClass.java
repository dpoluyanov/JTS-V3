package ru.jts_dev.gameserver.constants;

/**
 * @author Camelion
 * @since 07.01.16
 */
public enum ItemClass {
    WEAPON(0), ARMOR(1), ACCESSARY(2), QUESTITEM(3), ASSET(4), ETCITEM(5);

    private int value;

    ItemClass(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
