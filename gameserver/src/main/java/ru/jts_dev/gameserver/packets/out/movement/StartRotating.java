package ru.jts_dev.gameserver.packets.out.movement;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * @author Java-man
 * @since 26.01.2016
 */
public class StartRotating extends OutgoingMessageWrapper {
    private final int charId;
    private final int degree;
    private final int side;
    private final int speed;

    public StartRotating(GameCharacter character, int degree, int side, int speed) {
        charId = character.getObjectId();
        this.degree = degree;
        this.side = side;
        this.speed = speed;
    }

    @Override
    public final void write() {
        putByte(0x7a);
        putInt(charId);
        putInt(degree);
        putInt(side);
        putInt(speed);
    }
}
