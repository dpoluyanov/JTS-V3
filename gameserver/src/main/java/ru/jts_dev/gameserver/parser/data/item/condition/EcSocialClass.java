package ru.jts_dev.gameserver.parser.data.item.condition;

/**
 * @author Camelion
 * @since 14.01.16
 */
public class EcSocialClass implements Condition {
    private final int socialClass;

    public EcSocialClass(int socialClass) {
        this.socialClass = socialClass;
    }
}
